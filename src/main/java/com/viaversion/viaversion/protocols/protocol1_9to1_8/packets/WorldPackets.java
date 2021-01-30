package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_8;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.CustomByteType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.Effect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.SoundEffect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_9to1_8Type;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

public class WorldPackets {
   public static void register(Protocol protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, (PacketRemapper)(new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
         }
      }));
      protocol.registerClientbound(ClientboundPackets1_8.EFFECT, (PacketRemapper)(new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.POSITION);
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.INT, 0);
                  id = Effect.getNewId(id);
                  wrapper.set(Type.INT, 0, id);
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.INT, 0);
                  if (id == 2002) {
                     int data = (Integer)wrapper.get(Type.INT, 1);
                     int newData = ItemRewriter.getNewEffectID(data);
                     wrapper.set(Type.INT, 1, newData);
                  }

               }
            });
         }
      }));
      protocol.registerClientbound(ClientboundPackets1_8.NAMED_SOUND, (PacketRemapper)(new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  String name = (String)wrapper.get(Type.STRING, 0);
                  SoundEffect effect = SoundEffect.getByName(name);
                  int catid = 0;
                  String newname = name;
                  if (effect != null) {
                     catid = effect.getCategory().getId();
                     newname = effect.getNewName();
                  }

                  wrapper.set(Type.STRING, 0, newname);
                  wrapper.write(Type.VAR_INT, catid);
                  if (effect != null && effect.isBreaksound()) {
                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                     int x = (Integer)wrapper.passthrough(Type.INT);
                     int y = (Integer)wrapper.passthrough(Type.INT);
                     int z = (Integer)wrapper.passthrough(Type.INT);
                     if (tracker.interactedBlockRecently((int)Math.floor((double)x / 8.0D), (int)Math.floor((double)y / 8.0D), (int)Math.floor((double)z / 8.0D))) {
                        wrapper.cancel();
                     }
                  }

               }
            });
         }
      }));
      protocol.registerClientbound(ClientboundPackets1_8.CHUNK_DATA, (PacketRemapper)(new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientChunks clientChunks = (ClientChunks)wrapper.user().get(ClientChunks.class);
                  Chunk1_9to1_8Type type = new Chunk1_9to1_8Type(clientChunks);
                  Chunk1_8 chunk = (Chunk1_8)wrapper.read(type);
                  if (chunk.isUnloadPacket()) {
                     wrapper.setId(ClientboundPackets1_9.UNLOAD_CHUNK);
                     wrapper.write(Type.INT, chunk.getX());
                     wrapper.write(Type.INT, chunk.getZ());
                     CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
                     provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
                  } else {
                     wrapper.write(type, chunk);
                  }

                  wrapper.read(Type.REMAINING_BYTES);
               }
            });
         }
      }));
      protocol.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, (ClientboundPacketType)null, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               wrapper.cancel();
               boolean skyLight = (Boolean)wrapper.read(Type.BOOLEAN);
               int count = (Integer)wrapper.read(Type.VAR_INT);
               WorldPackets.ChunkBulkSection[] chunks = new WorldPackets.ChunkBulkSection[count];

               for(int i = 0; i < count; ++i) {
                  chunks[i] = new WorldPackets.ChunkBulkSection(wrapper, skyLight);
               }

               ClientChunks clientChunks = (ClientChunks)wrapper.user().get(ClientChunks.class);
               WorldPackets.ChunkBulkSection[] var5 = chunks;
               int var6 = chunks.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  WorldPackets.ChunkBulkSection chunk = var5[var7];
                  CustomByteType customByteType = new CustomByteType(chunk.getLength());
                  chunk.setData((byte[])wrapper.read(customByteType));
                  clientChunks.getBulkChunks().add(ClientChunks.toLong(chunk.getX(), chunk.getZ()));
                  ByteBuf buffer = null;

                  try {
                     buffer = wrapper.user().getChannel().alloc().buffer();
                     Type.INT.write(buffer, chunk.getX());
                     Type.INT.write(buffer, chunk.getZ());
                     Type.BOOLEAN.write(buffer, true);
                     Type.UNSIGNED_SHORT.write(buffer, chunk.getBitMask());
                     Type.VAR_INT.writePrimitive(buffer, chunk.getLength());
                     customByteType.write(buffer, chunk.getData());
                     PacketWrapper chunkPacket = PacketWrapper.create(ClientboundPackets1_8.CHUNK_DATA, buffer, wrapper.user());
                     chunkPacket.send(Protocol1_9To1_8.class, false);
                  } finally {
                     if (buffer != null) {
                        buffer.release();
                     }

                  }
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, (PacketRemapper)(new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.NBT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int action = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  if (action == 1) {
                     CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
                     if (tag != null) {
                        if (tag.contains("EntityId")) {
                           String entity = (String)tag.get("EntityId").getValue();
                           CompoundTag spawnx = new CompoundTag();
                           spawnx.put("id", new StringTag(entity));
                           tag.put("SpawnData", spawnx);
                        } else {
                           CompoundTag spawn = new CompoundTag();
                           spawn.put("id", new StringTag("AreaEffectCloud"));
                           tag.put("SpawnData", spawn);
                        }
                     }
                  }

                  if (action == 2) {
                     CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
                     provider.addOrUpdateBlock(wrapper.user(), (Position)wrapper.get(Type.POSITION, 0), (CompoundTag)wrapper.get(Type.NBT, 0));
                     wrapper.cancel();
                  }

               }
            });
         }
      }));
      protocol.registerClientbound(ClientboundPackets1_8.BLOCK_CHANGE, (PacketRemapper)(new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.VAR_INT);
         }
      }));
      protocol.registerServerbound(ServerboundPackets1_9.UPDATE_SIGN, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.PLAYER_DIGGING, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT, Type.UNSIGNED_BYTE);
            this.map(Type.POSITION);
            this.map(Type.BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int status = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  if (status == 6) {
                     wrapper.cancel();
                  }

               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int status = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  if (status == 5 || status == 4 || status == 3) {
                     EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                     if (entityTracker.isBlocking()) {
                        entityTracker.setBlocking(false);
                        if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                           entityTracker.setSecondHand((Item)null);
                        }
                     }
                  }

               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM, (ServerboundPacketType)null, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int hand = (Integer)wrapper.read(Type.VAR_INT);
                  wrapper.clearInputBuffer();
                  wrapper.setId(8);
                  wrapper.write(Type.POSITION, new Position(-1, (short)-1, -1));
                  wrapper.write(Type.UNSIGNED_BYTE, (short)255);
                  Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
                  if (Via.getConfig().isShieldBlocking()) {
                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                     boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
                     boolean isSword = showShieldWhenSwordInHand ? tracker.hasSwordInHand() : item != null && Protocol1_9To1_8.isSword(item.identifier());
                     if (isSword) {
                        if (hand == 0 && !tracker.isBlocking()) {
                           tracker.setBlocking(true);
                           if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null) {
                              Item shield = new DataItem(442, (byte)1, (short)0, (CompoundTag)null);
                              tracker.setSecondHand(shield);
                           }
                        }

                        boolean blockUsingMainHand = Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand;
                        if (blockUsingMainHand && hand == 1 || !blockUsingMainHand && hand == 0) {
                           wrapper.cancel();
                        }
                     } else {
                        if (!showShieldWhenSwordInHand) {
                           tracker.setSecondHand((Item)null);
                        }

                        tracker.setBlocking(false);
                     }
                  }

                  wrapper.write(Type.ITEM, item);
                  wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short)0));
                  wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short)0));
                  wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short)0));
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.VAR_INT, Type.UNSIGNED_BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int hand = (Integer)wrapper.read(Type.VAR_INT);
                  if (hand != 0) {
                     wrapper.cancel();
                  }

               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
                  wrapper.write(Type.ITEM, item);
               }
            });
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int face = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  if (face != 255) {
                     Position p = (Position)wrapper.get(Type.POSITION, 0);
                     int x = p.getX();
                     int y = p.getY();
                     int z = p.getZ();
                     switch(face) {
                     case 0:
                        --y;
                        break;
                     case 1:
                        ++y;
                        break;
                     case 2:
                        --z;
                        break;
                     case 3:
                        ++z;
                        break;
                     case 4:
                        --x;
                        break;
                     case 5:
                        ++x;
                     }

                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                     tracker.addBlockInteraction(new Position(x, y, z));
                  }
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
                  Position pos = (Position)wrapper.get(Type.POSITION, 0);
                  Optional tag = provider.get(wrapper.user(), pos);
                  if (tag.isPresent()) {
                     PacketWrapper updateBlockEntity = PacketWrapper.create(9, (ByteBuf)null, wrapper.user());
                     updateBlockEntity.write(Type.POSITION, pos);
                     updateBlockEntity.write(Type.UNSIGNED_BYTE, Short.valueOf((short)2));
                     updateBlockEntity.write(Type.NBT, (CompoundTag)tag.get());
                     updateBlockEntity.scheduleSend(Protocol1_9To1_8.class);
                  }

               }
            });
         }
      });
   }

   public static final class ChunkBulkSection {
      // $FF: renamed from: x int
      private final int field_60;
      // $FF: renamed from: z int
      private final int field_61;
      private final int bitMask;
      private final int length;
      private byte[] data;

      public ChunkBulkSection(PacketWrapper wrapper, boolean skylight) throws Exception {
         this.field_60 = (Integer)wrapper.read(Type.INT);
         this.field_61 = (Integer)wrapper.read(Type.INT);
         this.bitMask = (Integer)wrapper.read(Type.UNSIGNED_SHORT);
         int bitCount = Integer.bitCount(this.bitMask);
         this.length = bitCount * 10240 + (skylight ? bitCount * 2048 : 0) + 256;
      }

      public int getX() {
         return this.field_60;
      }

      public int getZ() {
         return this.field_61;
      }

      public int getBitMask() {
         return this.bitMask;
      }

      public int getLength() {
         return this.length;
      }

      @Nullable
      public byte[] getData() {
         return this.data;
      }

      public void setData(byte[] data) {
         this.data = data;
      }
   }
}
