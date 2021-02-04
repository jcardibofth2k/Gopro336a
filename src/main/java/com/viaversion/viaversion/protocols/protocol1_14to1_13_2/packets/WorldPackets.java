package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;

public class WorldPackets {
   public static final int SERVERSIDE_VIEW_DISTANCE = 64;
   private static final byte[] FULL_LIGHT = new byte[2048];
   public static int air;
   public static int voidAir;
   public static int caveAir;

   public static void register(final Protocol1_14To1_13_2 protocol) {
      BlockRewriter blockRewriter = new BlockRewriter(protocol, null);
      protocol.registerClientbound(ClientboundPackets1_13.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION, Type.POSITION1_14);
            this.map(Type.BYTE);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION, Type.POSITION1_14);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.BLOCK_ACTION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION, Type.POSITION1_14);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  wrapper.set(Type.VAR_INT, 0, protocol.getMappingData().getNewBlockId((Integer)wrapper.get(Type.VAR_INT, 0)));
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION, Type.POSITION1_14);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.VAR_INT, 0);
                  wrapper.set(Type.VAR_INT, 0, protocol.getMappingData().getNewBlockStateId(id));
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SERVER_DIFFICULTY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  wrapper.write(Type.BOOLEAN, false);
               }
            });
         }
      });
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
      protocol.registerClientbound(ClientboundPackets1_13.EXPLOSION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  for(int i = 0; i < 3; ++i) {
                     float coord = (Float)wrapper.get(Type.FLOAT, i);
                     if (coord < 0.0F) {
                        coord = (float)((int)coord);
                        wrapper.set(Type.FLOAT, i, coord);
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  Chunk chunk = (Chunk)wrapper.read(new Chunk1_13Type(clientWorld));
                  wrapper.write(new Chunk1_14Type(), chunk);
                  int[] motionBlocking = new int[256];
                  int[] worldSurface = new int[256];

                  int nonAirBlockCount;
                  int x;
                  int y;
                  int z;
                  for(int s = 0; s < chunk.getSections().length; ++s) {
                     ChunkSection sectionx = chunk.getSections()[s];
                     if (sectionx != null) {
                        boolean hasBlock = false;

                        for(nonAirBlockCount = 0; nonAirBlockCount < sectionx.getPaletteSize(); ++nonAirBlockCount) {
                           x = sectionx.getPaletteEntry(nonAirBlockCount);
                           y = protocol.getMappingData().getNewBlockStateId(x);
                           if (!hasBlock && y != WorldPackets.air && y != WorldPackets.voidAir && y != WorldPackets.caveAir) {
                              hasBlock = true;
                           }

                           sectionx.setPaletteEntry(nonAirBlockCount, y);
                        }

                        if (!hasBlock) {
                           sectionx.setNonAirBlocksCount(0);
                        } else {
                           nonAirBlockCount = 0;

                           for(x = 0; x < 16; ++x) {
                              for(y = 0; y < 16; ++y) {
                                 for(z = 0; z < 16; ++z) {
                                    int id = sectionx.getFlatBlock(x, y, z);
                                    if (id != WorldPackets.air && id != WorldPackets.voidAir && id != WorldPackets.caveAir) {
                                       ++nonAirBlockCount;
                                       worldSurface[x + z * 16] = y + s * 16 + 1;
                                    }

                                    if (protocol.getMappingData().getMotionBlocking().contains(id)) {
                                       motionBlocking[x + z * 16] = y + s * 16 + 1;
                                    }

                                    if (Via.getConfig().isNonFullBlockLightFix() && protocol.getMappingData().getNonFullBlocks().contains(id)) {
                                       WorldPackets.setNonFullLight(chunk, sectionx, s, x, y, z);
                                    }
                                 }
                              }
                           }

                           sectionx.setNonAirBlocksCount(nonAirBlockCount);
                        }
                     }
                  }

                  CompoundTag heightMap = new CompoundTag();
                  heightMap.put("MOTION_BLOCKING", new LongArrayTag(WorldPackets.encodeHeightMap(motionBlocking)));
                  heightMap.put("WORLD_SURFACE", new LongArrayTag(WorldPackets.encodeHeightMap(worldSurface)));
                  chunk.setHeightMap(heightMap);
                  PacketWrapper lightPacket = wrapper.create(ClientboundPackets1_14.UPDATE_LIGHT);
                  lightPacket.write(Type.VAR_INT, chunk.getX());
                  lightPacket.write(Type.VAR_INT, chunk.getZ());
                  int skyLightMask = chunk.isFullChunk() ? 262143 : 0;
                  nonAirBlockCount = 0;

                  for(x = 0; x < chunk.getSections().length; ++x) {
                     ChunkSection sec = chunk.getSections()[x];
                     if (sec != null) {
                        if (!chunk.isFullChunk() && sec.getLight().hasSkyLight()) {
                           skyLightMask |= 1 << x + 1;
                        }

                        nonAirBlockCount |= 1 << x + 1;
                     }
                  }

                  lightPacket.write(Type.VAR_INT, skyLightMask);
                  lightPacket.write(Type.VAR_INT, nonAirBlockCount);
                  lightPacket.write(Type.VAR_INT, 0);
                  lightPacket.write(Type.VAR_INT, 0);
                  if (chunk.isFullChunk()) {
                     lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.FULL_LIGHT);
                  }

                  ChunkSection[] var20 = chunk.getSections();
                  y = var20.length;

                  ChunkSection section;
                  for(z = 0; z < y; ++z) {
                     section = var20[z];
                     if (section != null && section.getLight().hasSkyLight()) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, section.getLight().getSkyLight());
                     } else if (chunk.isFullChunk()) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.FULL_LIGHT);
                     }
                  }

                  if (chunk.isFullChunk()) {
                     lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.FULL_LIGHT);
                  }

                  var20 = chunk.getSections();
                  y = var20.length;

                  for(z = 0; z < y; ++z) {
                     section = var20[z];
                     if (section != null) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, section.getLight().getBlockLight());
                     }
                  }

                  EntityTracker1_14 entityTracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                  y = Math.abs(entityTracker.getChunkCenterX() - chunk.getX());
                  z = Math.abs(entityTracker.getChunkCenterZ() - chunk.getZ());
                  if (entityTracker.isForceSendCenterChunk() || y >= 64 || z >= 64) {
                     PacketWrapper fakePosLook = wrapper.create(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
                     fakePosLook.write(Type.VAR_INT, chunk.getX());
                     fakePosLook.write(Type.VAR_INT, chunk.getZ());
                     fakePosLook.send(Protocol1_14To1_13_2.class);
                     entityTracker.setChunkCenterX(chunk.getX());
                     entityTracker.setChunkCenterZ(chunk.getZ());
                  }

                  lightPacket.send(Protocol1_14To1_13_2.class);
                  ChunkSection[] var25 = chunk.getSections();
                  int var14 = var25.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     ChunkSection sectionxx = var25[var15];
                     if (sectionxx != null) {
                        sectionxx.setLight(null);
                     }
                  }

               }

               private Byte[] fromPrimitiveArray(byte[] bytes) {
                  Byte[] newArray = new Byte[bytes.length];

                  for(int i = 0; i < bytes.length; ++i) {
                     newArray[i] = bytes[i];
                  }

                  return newArray;
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.POSITION, Type.POSITION1_14);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.INT, 0);
                  int data = (Integer)wrapper.get(Type.INT, 1);
                  if (id == 1010) {
                     wrapper.set(Type.INT, 1, protocol.getMappingData().getNewItemId(data));
                  } else if (id == 2001) {
                     wrapper.set(Type.INT, 1, protocol.getMappingData().getNewBlockStateId(data));
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  int dimensionId = (Integer)wrapper.get(Type.INT, 1);
                  clientChunks.setEnvironment(dimensionId);
                  int entityId = (Integer)wrapper.get(Type.INT, 0);
                  Entity1_14Types entType = Entity1_14Types.PLAYER;
                  EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                  tracker.addEntity(entityId, entType);
                  tracker.setClientEntityId(entityId);
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  short difficulty = (Short)wrapper.read(Type.UNSIGNED_BYTE);
                  PacketWrapper difficultyPacket = wrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                  difficultyPacket.write(Type.UNSIGNED_BYTE, difficulty);
                  difficultyPacket.write(Type.BOOLEAN, false);
                  difficultyPacket.scheduleSend(protocol.getClass());
                  wrapper.passthrough(Type.UNSIGNED_BYTE);
                  wrapper.passthrough(Type.STRING);
                  wrapper.write(Type.VAR_INT, 64);
               }
            });
            this.handler((wrapper) -> {
               wrapper.send(Protocol1_14To1_13_2.class);
               wrapper.cancel();
               WorldPackets.sendViewDistancePacket(wrapper.user());
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.MAP_DATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  wrapper.write(Type.BOOLEAN, false);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  int dimensionId = (Integer)wrapper.get(Type.INT, 0);
                  clientWorld.setEnvironment(dimensionId);
                  EntityTracker1_14 entityTracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                  entityTracker.setForceSendCenterChunk(true);
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  short difficulty = (Short)wrapper.read(Type.UNSIGNED_BYTE);
                  PacketWrapper difficultyPacket = wrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                  difficultyPacket.write(Type.UNSIGNED_BYTE, difficulty);
                  difficultyPacket.write(Type.BOOLEAN, false);
                  difficultyPacket.scheduleSend(protocol.getClass());
               }
            });
            this.handler((wrapper) -> {
               wrapper.send(Protocol1_14To1_13_2.class);
               wrapper.cancel();
               WorldPackets.sendViewDistancePacket(wrapper.user());
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_POSITION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION, Type.POSITION1_14);
         }
      });
   }

   private static void sendViewDistancePacket(UserConnection connection) throws Exception {
      PacketWrapper setViewDistance = PacketWrapper.create(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE, null, connection);
      setViewDistance.write(Type.VAR_INT, 64);
      setViewDistance.send(Protocol1_14To1_13_2.class);
   }

   private static long[] encodeHeightMap(int[] heightMap) {
      return CompactArrayUtil.createCompactArray(9, heightMap.length, (i) -> {
         return heightMap[i];
      });
   }

   private static void setNonFullLight(Chunk chunk, ChunkSection section, int ySection, int x, int y, int z) {
      int skyLight = 0;
      int blockLight = 0;
      BlockFace[] var8 = BlockFace.values();
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         BlockFace blockFace = var8[var10];
         NibbleArray skyLightArray = section.getLight().getSkyLightNibbleArray();
         NibbleArray blockLightArray = section.getLight().getBlockLightNibbleArray();
         int neighbourX = x + blockFace.getModX();
         int neighbourY = y + blockFace.getModY();
         int neighbourZ = z + blockFace.getModZ();
         if (blockFace.getModX() != 0) {
            if (neighbourX == 16 || neighbourX == -1) {
               continue;
            }
         } else if (blockFace.getModY() != 0) {
            if (neighbourY == 16 || neighbourY == -1) {
               if (neighbourY == 16) {
                  ++ySection;
                  neighbourY = 0;
               } else {
                  --ySection;
                  neighbourY = 15;
               }

               if (ySection == 16 || ySection == -1) {
                  continue;
               }

               ChunkSection newSection = chunk.getSections()[ySection];
               if (newSection == null) {
                  continue;
               }

               skyLightArray = newSection.getLight().getSkyLightNibbleArray();
               blockLightArray = newSection.getLight().getBlockLightNibbleArray();
            }
         } else if (blockFace.getModZ() != 0 && (neighbourZ == 16 || neighbourZ == -1)) {
            continue;
         }

         byte neighbourSkyLight;
         if (blockLightArray != null && blockLight != 15) {
            neighbourSkyLight = blockLightArray.get(neighbourX, neighbourY, neighbourZ);
            if (neighbourSkyLight == 15) {
               blockLight = 14;
            } else if (neighbourSkyLight > blockLight) {
               blockLight = neighbourSkyLight - 1;
            }
         }

         if (skyLightArray != null && skyLight != 15) {
            neighbourSkyLight = skyLightArray.get(neighbourX, neighbourY, neighbourZ);
            if (neighbourSkyLight == 15) {
               if (blockFace.getModY() == 1) {
                  skyLight = 15;
               } else {
                  skyLight = 14;
               }
            } else if (neighbourSkyLight > skyLight) {
               skyLight = neighbourSkyLight - 1;
            }
         }
      }

      if (skyLight != 0) {
         if (!section.getLight().hasSkyLight()) {
            byte[] newSkyLight = new byte[2028];
            section.getLight().setSkyLight(newSkyLight);
         }

         section.getLight().getSkyLightNibbleArray().set(x, y, z, skyLight);
      }

      if (blockLight != 0) {
         section.getLight().getBlockLightNibbleArray().set(x, y, z, blockLight);
      }

   }

   private static long getChunkIndex(int x, int z) {
      return ((long)x & 67108863L) << 38 | (long)z & 67108863L;
   }

   static {
      Arrays.fill(FULL_LIGHT, (byte)-1);
   }
}
