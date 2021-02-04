package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectionData {
   private static final BlockChangeRecord1_8[] EMPTY_RECORDS = new BlockChangeRecord1_8[0];
   public static BlockConnectionProvider blockConnectionProvider;
   static Int2ObjectMap idToKey = new Int2ObjectOpenHashMap(8582, 1.0F);
   static Map keyToId = new HashMap(8582, 1.0F);
   static Int2ObjectMap connectionHandlerMap = new Int2ObjectOpenHashMap(1);
   static Int2ObjectMap blockConnectionData = new Int2ObjectOpenHashMap(1);
   static IntSet occludingStates = new IntOpenHashSet(377, 1.0F);

   public static void update(UserConnection user, Position position) {
      BlockFace[] var2 = BlockFace.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         BlockFace face = var2[var4];
         Position pos = position.getRelative(face);
         int blockState = blockConnectionProvider.getBlockData(user, pos.getX(), pos.getY(), pos.getZ());
         ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
         if (handler != null) {
            int newBlockState = handler.connect(user, pos, blockState);
            PacketWrapper blockUpdatePacket = PacketWrapper.create(11, null, user);
            blockUpdatePacket.write(Type.POSITION, pos);
            blockUpdatePacket.write(Type.VAR_INT, newBlockState);

            try {
               blockUpdatePacket.send(Protocol1_13To1_12_2.class);
            } catch (Exception var12) {
               var12.printStackTrace();
            }
         }
      }

   }

   public static void updateChunkSectionNeighbours(UserConnection user, int chunkX, int chunkZ, int chunkSectionY) {
      for(int chunkDeltaX = -1; chunkDeltaX <= 1; ++chunkDeltaX) {
         for(int chunkDeltaZ = -1; chunkDeltaZ <= 1; ++chunkDeltaZ) {
            if (Math.abs(chunkDeltaX) + Math.abs(chunkDeltaZ) != 0) {
               List updates = new ArrayList();
               int blockY;
               if (Math.abs(chunkDeltaX) + Math.abs(chunkDeltaZ) == 2) {
                  for(blockY = chunkSectionY * 16; blockY < chunkSectionY * 16 + 16; ++blockY) {
                     int blockPosX = chunkDeltaX == 1 ? 0 : 15;
                     int blockPosZ = chunkDeltaZ == 1 ? 0 : 15;
                     updateBlock(user, new Position((chunkX + chunkDeltaX << 4) + blockPosX, (short)blockY, (chunkZ + chunkDeltaZ << 4) + blockPosZ), updates);
                  }
               } else {
                  for(blockY = chunkSectionY * 16; blockY < chunkSectionY * 16 + 16; ++blockY) {
                     byte zStart;
                     byte zEnd;
                     byte xStart;
                     byte xEnd;
                     if (chunkDeltaX == 1) {
                        xStart = 0;
                        xEnd = 2;
                        zStart = 0;
                        zEnd = 16;
                     } else if (chunkDeltaX == -1) {
                        xStart = 14;
                        xEnd = 16;
                        zStart = 0;
                        zEnd = 16;
                     } else if (chunkDeltaZ == 1) {
                        xStart = 0;
                        xEnd = 16;
                        zStart = 0;
                        zEnd = 2;
                     } else {
                        xStart = 0;
                        xEnd = 16;
                        zStart = 14;
                        zEnd = 16;
                     }

                     for(int blockX = xStart; blockX < xEnd; ++blockX) {
                        for(int blockZ = zStart; blockZ < zEnd; ++blockZ) {
                           updateBlock(user, new Position((chunkX + chunkDeltaX << 4) + blockX, (short)blockY, (chunkZ + chunkDeltaZ << 4) + blockZ), updates);
                        }
                     }
                  }
               }

               if (!updates.isEmpty()) {
                  PacketWrapper wrapper = PacketWrapper.create(15, null, user);
                  wrapper.write(Type.INT, chunkX + chunkDeltaX);
                  wrapper.write(Type.INT, chunkZ + chunkDeltaZ);
                  wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, updates.toArray(EMPTY_RECORDS));

                  try {
                     wrapper.send(Protocol1_13To1_12_2.class);
                  } catch (Exception var14) {
                     var14.printStackTrace();
                  }
               }
            }
         }
      }

   }

   public static void updateBlock(UserConnection user, Position pos, List records) {
      int blockState = blockConnectionProvider.getBlockData(user, pos.getX(), pos.getY(), pos.getZ());
      ConnectionHandler handler = getConnectionHandler(blockState);
      if (handler != null) {
         int newBlockState = handler.connect(user, pos, blockState);
         records.add(new BlockChangeRecord1_8(pos.getX() & 15, pos.getY(), pos.getZ() & 15, newBlockState));
      }
   }

   public static void updateBlockStorage(UserConnection userConnection, int x, int y, int z, int blockState) {
      if (needStoreBlocks()) {
         if (isWelcome(blockState)) {
            blockConnectionProvider.storeBlock(userConnection, x, y, z, blockState);
         } else {
            blockConnectionProvider.removeBlock(userConnection, x, y, z);
         }

      }
   }

   public static void clearBlockStorage(UserConnection connection) {
      if (needStoreBlocks()) {
         blockConnectionProvider.clearStorage(connection);
      }
   }

   public static boolean needStoreBlocks() {
      return blockConnectionProvider.storesBlocks();
   }

   public static void connectBlocks(UserConnection user, Chunk chunk) {
      long xOff = chunk.getX() << 4;
      long zOff = chunk.getZ() << 4;

      for(int i = 0; i < chunk.getSections().length; ++i) {
         ChunkSection section = chunk.getSections()[i];
         if (section != null) {
            boolean willConnect = false;

            for(int p = 0; p < section.getPaletteSize(); ++p) {
               int id = section.getPaletteEntry(p);
               if (connects(id)) {
                  willConnect = true;
                  break;
               }
            }

            if (willConnect) {
               long yOff = i << 4;

               for(int y = 0; y < 16; ++y) {
                  for(int z = 0; z < 16; ++z) {
                     for(int x = 0; x < 16; ++x) {
                        int block = section.getFlatBlock(x, y, z);
                        ConnectionHandler handler = getConnectionHandler(block);
                        if (handler != null) {
                           block = handler.connect(user, new Position((int)(xOff + (long)x), (short)((int)(yOff + (long)y)), (int)(zOff + (long)z)), block);
                           section.setFlatBlock(x, y, z, block);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public static void init() {
      if (Via.getConfig().isServersideBlockConnections()) {
         Via.getPlatform().getLogger().info("Loading block connection mappings ...");
         JsonObject mapping1_13 = MappingDataLoader.loadData("mapping-1.13.json", true);
         JsonObject blocks1_13 = mapping1_13.getAsJsonObject("blockstates");
         Iterator var2 = blocks1_13.entrySet().iterator();

         while(var2.hasNext()) {
            Entry blockState = (Entry)var2.next();
            int id = Integer.parseInt((String)blockState.getKey());
            String key = ((JsonElement)blockState.getValue()).getAsString();
            idToKey.put(id, key);
            keyToId.put(key, id);
         }

         connectionHandlerMap = new Int2ObjectOpenHashMap(3650, 1.0F);
         JsonObject mappingBlockConnections;
         if (!Via.getConfig().isReduceBlockStorageMemory()) {
            blockConnectionData = new Int2ObjectOpenHashMap(1146, 1.0F);
            mappingBlockConnections = MappingDataLoader.loadData("blockConnections.json");

            BlockData blockData;
            int id;
            for(Iterator var18 = mappingBlockConnections.entrySet().iterator(); var18.hasNext(); blockConnectionData.put(id, blockData)) {
               Entry entry = (Entry)var18.next();
               id = (Integer)keyToId.get(entry.getKey());
               blockData = new BlockData();
               Iterator var7 = ((JsonElement)entry.getValue()).getAsJsonObject().entrySet().iterator();

               while(var7.hasNext()) {
                  Entry type = (Entry)var7.next();
                  String name = (String)type.getKey();
                  JsonObject object = ((JsonElement)type.getValue()).getAsJsonObject();
                  boolean[] data = new boolean[6];
                  BlockFace[] var12 = BlockFace.values();
                  int var13 = var12.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     BlockFace value = var12[var14];
                     String face = value.toString().toLowerCase(Locale.ROOT);
                     if (object.has(face)) {
                        data[value.ordinal()] = object.getAsJsonPrimitive(face).getAsBoolean();
                     }
                  }

                  blockData.put(name, data);
               }

               if (((String)entry.getKey()).contains("stairs")) {
                  blockData.put("allFalseIfStairPre1_12", new boolean[6]);
               }
            }
         }

         mappingBlockConnections = MappingDataLoader.loadData("blockData.json");
         JsonArray occluding = mappingBlockConnections.getAsJsonArray("occluding");
         Iterator var21 = occluding.iterator();

         while(var21.hasNext()) {
            JsonElement jsonElement = (JsonElement)var21.next();
            occludingStates.add((Integer)keyToId.get(jsonElement.getAsString()));
         }

         List initActions = new ArrayList();
         initActions.add(PumpkinConnectionHandler.init());
         initActions.addAll(BasicFenceConnectionHandler.init());
         initActions.add(NetherFenceConnectionHandler.init());
         initActions.addAll(WallConnectionHandler.init());
         initActions.add(MelonConnectionHandler.init());
         initActions.addAll(GlassConnectionHandler.init());
         initActions.add(ChestConnectionHandler.init());
         initActions.add(DoorConnectionHandler.init());
         initActions.add(RedstoneConnectionHandler.init());
         initActions.add(StairConnectionHandler.init());
         initActions.add(FlowerConnectionHandler.init());
         initActions.addAll(ChorusPlantConnectionHandler.init());
         initActions.add(TripwireConnectionHandler.init());
         initActions.add(SnowyGrassConnectionHandler.init());
         initActions.add(FireConnectionHandler.init());
         if (Via.getConfig().isVineClimbFix()) {
            initActions.add(VineConnectionHandler.init());
         }

         Iterator var25 = keyToId.keySet().iterator();

         while(var25.hasNext()) {
            String key = (String)var25.next();
            WrappedBlockData wrappedBlockData = WrappedBlockData.fromString(key);
            Iterator var28 = initActions.iterator();

            while(var28.hasNext()) {
               ConnectionData.ConnectorInitAction action = (ConnectionData.ConnectorInitAction)var28.next();
               action.check(wrappedBlockData);
            }
         }

         if (Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("packet")) {
            blockConnectionProvider = new PacketBlockConnectionProvider();
            Via.getManager().getProviders().register(BlockConnectionProvider.class, blockConnectionProvider);
         }

      }
   }

   public static boolean isWelcome(int blockState) {
      return blockConnectionData.containsKey(blockState) || connectionHandlerMap.containsKey(blockState);
   }

   public static boolean connects(int blockState) {
      return connectionHandlerMap.containsKey(blockState);
   }

   public static int connect(UserConnection user, Position position, int blockState) {
      ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
      return handler != null ? handler.connect(user, position, blockState) : blockState;
   }

   public static ConnectionHandler getConnectionHandler(int blockstate) {
      return (ConnectionHandler)connectionHandlerMap.get(blockstate);
   }

   public static int getId(String key) {
      return (Integer)keyToId.getOrDefault(key, -1);
   }

   public static String getKey(int id) {
      return (String)idToKey.get(id);
   }

   @FunctionalInterface
   interface ConnectorInitAction {
      void check(WrappedBlockData var1);
   }
}
