package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class WorldPackets {
   private static final IntSet VALID_BIOMES = new IntOpenHashSet(70, 1.0F);

   public static void register(Protocol protocol) {
      protocol.registerClientbound(ClientboundPackets1_12_1.SPAWN_PAINTING, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  PaintingProvider provider = (PaintingProvider)Via.getManager().getProviders().get(PaintingProvider.class);
                  String motive = (String)wrapper.read(Type.STRING);
                  Optional id = provider.getIntByIdentifier(motive);
                  if (!id.isPresent() && (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug())) {
                     Via.getPlatform().getLogger().warning("Could not find painting motive: " + motive + " falling back to default (0)");
                  }

                  wrapper.write(Type.VAR_INT, id.orElse(0));
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.NBT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Position position = (Position)wrapper.get(Type.POSITION, 0);
                  short action = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
                  BlockEntityProvider provider = (BlockEntityProvider)Via.getManager().getProviders().get(BlockEntityProvider.class);
                  int newId = provider.transform(wrapper.user(), position, tag, true);
                  if (newId != -1) {
                     BlockStorage storage = (BlockStorage)wrapper.user().get(BlockStorage.class);
                     BlockStorage.ReplacementData replacementData = storage.get(position);
                     if (replacementData != null) {
                        replacementData.setReplacement(newId);
                     }
                  }

                  if (action == 5) {
                     wrapper.cancel();
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_ACTION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Position pos = (Position)wrapper.get(Type.POSITION, 0);
                  short action = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  short param = (Short)wrapper.get(Type.UNSIGNED_BYTE, 1);
                  int blockId = (Integer)wrapper.get(Type.VAR_INT, 0);
                  if (blockId == 25) {
                     blockId = 73;
                  } else if (blockId == 33) {
                     blockId = 99;
                  } else if (blockId == 29) {
                     blockId = 92;
                  } else if (blockId == 54) {
                     blockId = 142;
                  } else if (blockId == 146) {
                     blockId = 305;
                  } else if (blockId == 130) {
                     blockId = 249;
                  } else if (blockId == 138) {
                     blockId = 257;
                  } else if (blockId == 52) {
                     blockId = 140;
                  } else if (blockId == 209) {
                     blockId = 472;
                  } else if (blockId >= 219 && blockId <= 234) {
                     blockId = blockId - 219 + 483;
                  }

                  if (blockId == 73) {
                     PacketWrapper blockChange = wrapper.create(11);
                     blockChange.write(Type.POSITION, new Position(pos));
                     blockChange.write(Type.VAR_INT, 249 + action * 24 * 2 + param * 2);
                     blockChange.send(Protocol1_13To1_12_2.class);
                  }

                  wrapper.set(Type.VAR_INT, 0, blockId);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Position position = (Position)wrapper.get(Type.POSITION, 0);
                  int newId = WorldPackets.toNewId((Integer)wrapper.get(Type.VAR_INT, 0));
                  UserConnection userConnection = wrapper.user();
                  if (Via.getConfig().isServersideBlockConnections()) {
                     ConnectionData.updateBlockStorage(userConnection, position.getX(), position.getY(), position.getZ(), newId);
                     newId = ConnectionData.connect(userConnection, position, newId);
                  }

                  wrapper.set(Type.VAR_INT, 0, WorldPackets.checkStorage(wrapper.user(), position, newId));
                  if (Via.getConfig().isServersideBlockConnections()) {
                     wrapper.send(Protocol1_13To1_12_2.class);
                     wrapper.cancel();
                     ConnectionData.update(userConnection, position);
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.MULTI_BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int chunkX = (Integer)wrapper.get(Type.INT, 0);
                  int chunkZ = (Integer)wrapper.get(Type.INT, 1);
                  UserConnection userConnection = wrapper.user();
                  BlockChangeRecord[] records = (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                  BlockChangeRecord[] var6 = records;
                  int var7 = records.length;

                  int var8;
                  BlockChangeRecord record;
                  int blockState;
                  Position position;
                  for(var8 = 0; var8 < var7; ++var8) {
                     record = var6[var8];
                     blockState = WorldPackets.toNewId(record.getBlockId());
                     position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                     if (Via.getConfig().isServersideBlockConnections()) {
                        ConnectionData.updateBlockStorage(userConnection, position.getX(), position.getY(), position.getZ(), blockState);
                     }

                     record.setBlockId(WorldPackets.checkStorage(wrapper.user(), position, blockState));
                  }

                  if (Via.getConfig().isServersideBlockConnections()) {
                     var6 = records;
                     var7 = records.length;

                     for(var8 = 0; var8 < var7; ++var8) {
                        record = var6[var8];
                        blockState = record.getBlockId();
                        position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                        ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
                        if (handler != null) {
                           blockState = handler.connect(userConnection, position, blockState);
                           record.setBlockId(blockState);
                        }
                     }

                     wrapper.send(Protocol1_13To1_12_2.class);
                     wrapper.cancel();
                     var6 = records;
                     var7 = records.length;

                     for(var8 = 0; var8 < var7; ++var8) {
                        record = var6[var8];
                        Position positionx = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                        ConnectionData.update(userConnection, positionx);
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.EXPLOSION, new PacketRemapper() {
         public void registerMap() {
            if (Via.getConfig().isServersideBlockConnections()) {
               this.map(Type.FLOAT);
               this.map(Type.FLOAT);
               this.map(Type.FLOAT);
               this.map(Type.FLOAT);
               this.map(Type.INT);
               this.handler(new PacketHandler() {
                  public void handle(PacketWrapper wrapper) throws Exception {
                     UserConnection userConnection = wrapper.user();
                     int x = (int)Math.floor((double)(Float)wrapper.get(Type.FLOAT, 0));
                     int y = (int)Math.floor((double)(Float)wrapper.get(Type.FLOAT, 1));
                     int z = (int)Math.floor((double)(Float)wrapper.get(Type.FLOAT, 2));
                     int recordCount = (Integer)wrapper.get(Type.INT, 0);
                     Position[] records = new Position[recordCount];

                     int i;
                     for(i = 0; i < recordCount; ++i) {
                        Position position = new Position(x + (Byte)wrapper.passthrough(Type.BYTE), (short)(y + (Byte)wrapper.passthrough(Type.BYTE)), z + (Byte)wrapper.passthrough(Type.BYTE));
                        records[i] = position;
                        ConnectionData.updateBlockStorage(userConnection, position.getX(), position.getY(), position.getZ(), 0);
                     }

                     wrapper.send(Protocol1_13To1_12_2.class);
                     wrapper.cancel();

                     for(i = 0; i < recordCount; ++i) {
                        ConnectionData.update(userConnection, records[i]);
                     }

                  }
               });
            }
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.UNLOAD_CHUNK, new PacketRemapper() {
         public void registerMap() {
            if (Via.getConfig().isServersideBlockConnections()) {
               this.handler(new PacketHandler() {
                  public void handle(PacketWrapper wrapper) throws Exception {
                     int x = (Integer)wrapper.passthrough(Type.INT);
                     int z = (Integer)wrapper.passthrough(Type.INT);
                     ConnectionData.blockConnectionProvider.unloadChunk(wrapper.user(), x, z);
                  }
               });
            }

         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.NAMED_SOUND, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  String sound = ((String)wrapper.get(Type.STRING, 0)).replace("minecraft:", "");
                  String newSoundId = NamedSoundRewriter.getNewId(sound);
                  wrapper.set(Type.STRING, 0, newSoundId);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  BlockStorage storage = (BlockStorage)wrapper.user().get(BlockStorage.class);
                  Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                  Chunk1_13Type type1_13 = new Chunk1_13Type(clientWorld);
                  Chunk chunk = (Chunk)wrapper.read(type);
                  wrapper.write(type1_13, chunk);

                  int i;
                  int biome;
                  int newId;
                  int y;
                  int z;
                  int x;
                  for(i = 0; i < chunk.getSections().length; ++i) {
                     ChunkSection sectionx = chunk.getSections()[i];
                     if (sectionx != null) {
                        for(biome = 0; biome < sectionx.getPaletteSize(); ++biome) {
                           newId = sectionx.getPaletteEntry(biome);
                           y = WorldPackets.toNewId(newId);
                           sectionx.setPaletteEntry(biome, y);
                        }

                        boolean willSaveToStorage = false;

                        for(newId = 0; newId < sectionx.getPaletteSize(); ++newId) {
                           y = sectionx.getPaletteEntry(newId);
                           if (storage.isWelcome(y)) {
                              willSaveToStorage = true;
                              break;
                           }
                        }

                        boolean willSaveConnection = false;
                        if (Via.getConfig().isServersideBlockConnections() && ConnectionData.needStoreBlocks()) {
                           for(y = 0; y < sectionx.getPaletteSize(); ++y) {
                              z = sectionx.getPaletteEntry(y);
                              if (ConnectionData.isWelcome(z)) {
                                 willSaveConnection = true;
                                 break;
                              }
                           }
                        }

                        int block;
                        if (willSaveToStorage) {
                           for(y = 0; y < 16; ++y) {
                              for(z = 0; z < 16; ++z) {
                                 for(x = 0; x < 16; ++x) {
                                    block = sectionx.getFlatBlock(x, y, z);
                                    if (storage.isWelcome(block)) {
                                       storage.store(new Position(x + (chunk.getX() << 4), (short)(y + (i << 4)), z + (chunk.getZ() << 4)), block);
                                    }
                                 }
                              }
                           }
                        }

                        if (willSaveConnection) {
                           for(y = 0; y < 16; ++y) {
                              for(z = 0; z < 16; ++z) {
                                 for(x = 0; x < 16; ++x) {
                                    block = sectionx.getFlatBlock(x, y, z);
                                    if (ConnectionData.isWelcome(block)) {
                                       ConnectionData.blockConnectionProvider.storeBlock(wrapper.user(), x + (chunk.getX() << 4), y + (i << 4), z + (chunk.getZ() << 4), block);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  int ix;
                  if (chunk.isBiomeData()) {
                     i = Integer.MIN_VALUE;

                     for(ix = 0; ix < 256; ++ix) {
                        biome = chunk.getBiomeData()[ix];
                        if (!WorldPackets.VALID_BIOMES.contains(biome)) {
                           if (biome != 255 && i != biome) {
                              if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                 Via.getPlatform().getLogger().warning("Received invalid biome id " + biome);
                              }

                              i = biome;
                           }

                           chunk.getBiomeData()[ix] = 1;
                        }
                     }
                  }

                  BlockEntityProvider provider = (BlockEntityProvider)Via.getManager().getProviders().get(BlockEntityProvider.class);
                  Iterator var18 = chunk.getBlockEntities().iterator();

                  while(var18.hasNext()) {
                     CompoundTag tag = (CompoundTag)var18.next();
                     newId = provider.transform(wrapper.user(), null, tag, false);
                     if (newId != -1) {
                        y = ((NumberTag)tag.get("x")).asInt();
                        z = ((NumberTag)tag.get("y")).asInt();
                        x = ((NumberTag)tag.get("z")).asInt();
                        Position position = new Position(y, (short)z, x);
                        BlockStorage.ReplacementData replacementData = storage.get(position);
                        if (replacementData != null) {
                           replacementData.setReplacement(newId);
                        }

                        chunk.getSections()[z >> 4].setFlatBlock(y & 15, z & 15, x & 15, newId);
                     }
                  }

                  if (Via.getConfig().isServersideBlockConnections()) {
                     ConnectionData.connectBlocks(wrapper.user(), chunk);
                     wrapper.send(Protocol1_13To1_12_2.class);
                     wrapper.cancel();

                     for(ix = 0; ix < chunk.getSections().length; ++ix) {
                        ChunkSection section = chunk.getSections()[ix];
                        if (section != null) {
                           ConnectionData.updateChunkSectionNeighbours(wrapper.user(), chunk.getX(), chunk.getZ(), ix);
                        }
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_12_1.SPAWN_PARTICLE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int particleId = (Integer)wrapper.get(Type.INT, 0);
                  int dataCount = 0;
                  if (particleId != 37 && particleId != 38 && particleId != 46) {
                     if (particleId == 36) {
                        dataCount = 2;
                     }
                  } else {
                     dataCount = 1;
                  }

                  Integer[] data = new Integer[dataCount];

                  for(int i = 0; i < data.length; ++i) {
                     data[i] = (Integer)wrapper.read(Type.VAR_INT);
                  }

                  Particle particle = ParticleRewriter.rewriteParticle(particleId, data);
                  if (particle != null && particle.getId() != -1) {
                     if (particle.getId() == 11) {
                        int count = (Integer)wrapper.get(Type.INT, 1);
                        float speed = (Float)wrapper.get(Type.FLOAT, 6);
                        if (count == 0) {
                           wrapper.set(Type.INT, 1, 1);
                           wrapper.set(Type.FLOAT, 6, 0.0F);
                           List arguments = particle.getArguments();

                           for(int ix = 0; ix < 3; ++ix) {
                              float colorValue = (Float)wrapper.get(Type.FLOAT, ix + 3) * speed;
                              if (colorValue == 0.0F && ix == 0) {
                                 colorValue = 1.0F;
                              }

                              ((Particle.ParticleData)arguments.get(ix)).setValue(colorValue);
                              wrapper.set(Type.FLOAT, ix + 3, 0.0F);
                           }
                        }
                     }

                     wrapper.set(Type.INT, 0, particle.getId());
                     Iterator var12 = particle.getArguments().iterator();

                     while(var12.hasNext()) {
                        Particle.ParticleData particleData = (Particle.ParticleData)var12.next();
                        wrapper.write(particleData.getType(), particleData.getValue());
                     }

                  } else {
                     wrapper.cancel();
                  }
               }
            });
         }
      });
   }

   public static int toNewId(int oldId) {
      if (oldId < 0) {
         oldId = 0;
      }

      int newId = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId);
      if (newId != -1) {
         return newId;
      } else {
         newId = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId & -16);
         if (newId != -1) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               Via.getPlatform().getLogger().warning("Missing block " + oldId);
            }

            return newId;
         } else {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               Via.getPlatform().getLogger().warning("Missing block completely " + oldId);
            }

            return 1;
         }
      }
   }

   private static int checkStorage(UserConnection user, Position position, int newId) {
      BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
      if (storage.contains(position)) {
         BlockStorage.ReplacementData data = storage.get(position);
         if (data.getOriginal() == newId) {
            if (data.getReplacement() != -1) {
               return data.getReplacement();
            }
         } else {
            storage.remove(position);
            if (storage.isWelcome(newId)) {
               storage.store(position, newId);
            }
         }
      } else if (storage.isWelcome(newId)) {
         storage.store(position, newId);
      }

      return newId;
   }

   static {
      int i;
      for(i = 0; i < 50; ++i) {
         VALID_BIOMES.add(i);
      }

      VALID_BIOMES.add(127);

      for(i = 129; i <= 134; ++i) {
         VALID_BIOMES.add(i);
      }

      VALID_BIOMES.add(140);
      VALID_BIOMES.add(149);
      VALID_BIOMES.add(151);

      for(i = 155; i <= 158; ++i) {
         VALID_BIOMES.add(i);
      }

      for(i = 160; i <= 167; ++i) {
         VALID_BIOMES.add(i);
      }

   }
}
