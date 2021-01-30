package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public class SpawnPackets {
   public static final ValueTransformer toNewDouble;

   public static void register(final Protocol1_9To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
               }
            });
            this.map(Type.BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  int typeID = (Byte)wrapper.get(Type.BYTE, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addEntity(entityID, Entity1_10Types.getTypeFromId(typeID, true));
                  tracker.sendMetadataBuffer(entityID);
               }
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int data = (Integer)wrapper.get(Type.INT, 0);
                  short vX = 0;
                  short vY = 0;
                  short vZ = 0;
                  if (data > 0) {
                     vX = (Short)wrapper.read(Type.SHORT);
                     vY = (Short)wrapper.read(Type.SHORT);
                     vZ = (Short)wrapper.read(Type.SHORT);
                  }

                  wrapper.write(Type.SHORT, vX);
                  wrapper.write(Type.SHORT, vY);
                  wrapper.write(Type.SHORT, vZ);
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  final int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  final int data = (Integer)wrapper.get(Type.INT, 0);
                  int typeID = (Byte)wrapper.get(Type.BYTE, 0);
                  if (Entity1_10Types.getTypeFromId(typeID, true) == Entity1_10Types.EntityType.SPLASH_POTION) {
                     PacketWrapper metaPacket = wrapper.create(57, new PacketHandler() {
                        public void handle(PacketWrapper wrapper) throws Exception {
                           wrapper.write(Type.VAR_INT, entityID);
                           List meta = new ArrayList();
                           Item item = new DataItem(373, (byte)1, (short)data, (CompoundTag)null);
                           ItemRewriter.toClient(item);
                           Metadata potion = new Metadata(5, MetaType1_9.Slot, item);
                           meta.add(potion);
                           wrapper.write(Types1_9.METADATA_LIST, meta);
                        }
                     });
                     metaPacket.scheduleSend(Protocol1_9To1_8.class);
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addEntity(entityID, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                  tracker.sendMetadataBuffer(entityID);
               }
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.SHORT);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addEntity(entityID, Entity1_10Types.EntityType.LIGHTNING);
                  tracker.sendMetadataBuffer(entityID);
               }
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
               }
            });
            this.map(Type.UNSIGNED_BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  int typeID = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addEntity(entityID, Entity1_10Types.getTypeFromId(typeID, false));
                  tracker.sendMetadataBuffer(entityID);
               }
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  List metadataList = (List)wrapper.get(Types1_9.METADATA_LIST, 0);
                  int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  if (tracker.hasEntity(entityId)) {
                     ((MetadataRewriter1_9To1_8)protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
                  } else {
                     Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                     metadataList.clear();
                  }

               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  List metadataList = (List)wrapper.get(Types1_9.METADATA_LIST, 0);
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.handleMetadata(entityID, metadataList);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addEntity(entityID, Entity1_10Types.EntityType.PAINTING);
                  tracker.sendMetadataBuffer(entityID);
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
               }
            });
            this.map(Type.STRING);
            this.map(Type.POSITION);
            this.map(Type.BYTE);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addEntity(entityID, Entity1_10Types.EntityType.PLAYER);
                  tracker.sendMetadataBuffer(entityID);
               }
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  short item = (Short)wrapper.read(Type.SHORT);
                  if (item != 0) {
                     PacketWrapper packet = PacketWrapper.create(60, (ByteBuf)null, wrapper.user());
                     packet.write(Type.VAR_INT, (Integer)wrapper.get(Type.VAR_INT, 0));
                     packet.write(Type.VAR_INT, 0);
                     packet.write(Type.ITEM, new DataItem(item, (byte)1, (short)0, (CompoundTag)null));

                     try {
                        packet.send(Protocol1_9To1_8.class);
                     } catch (Exception var5) {
                        var5.printStackTrace();
                     }
                  }

               }
            });
            this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  List metadataList = (List)wrapper.get(Types1_9.METADATA_LIST, 0);
                  int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  if (tracker.hasEntity(entityId)) {
                     ((MetadataRewriter1_9To1_8)protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
                  } else {
                     Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                     metadataList.clear();
                  }

               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  List metadataList = (List)wrapper.get(Types1_9.METADATA_LIST, 0);
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.handleMetadata(entityID, metadataList);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int[] entities = (int[])wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
                  int[] var3 = entities;
                  int var4 = entities.length;

                  for(int var5 = 0; var5 < var4; ++var5) {
                     int entity = var3[var5];
                     wrapper.user().getEntityTracker(Protocol1_9To1_8.class).removeEntity(entity);
                  }

               }
            });
         }
      });
   }

   static {
      toNewDouble = new ValueTransformer(Type.DOUBLE) {
         public Double transform(PacketWrapper wrapper, Integer inputValue) {
            return (double)inputValue / 32.0D;
         }
      };
   }
}
