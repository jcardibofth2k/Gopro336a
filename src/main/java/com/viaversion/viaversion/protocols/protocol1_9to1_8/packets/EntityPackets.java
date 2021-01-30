package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.google.common.collect.ImmutableList;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.Triple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class EntityPackets {
   public static final ValueTransformer toNewShort;

   public static void register(final Protocol1_9To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.ATTACH_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BOOLEAN, new ValueTransformer(Type.NOTHING) {
               public Void transform(PacketWrapper wrapper, Boolean inputValue) throws Exception {
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  if (!inputValue) {
                     int passenger = (Integer)wrapper.get(Type.INT, 0);
                     int vehicle = (Integer)wrapper.get(Type.INT, 1);
                     wrapper.cancel();
                     PacketWrapper passengerPacket = wrapper.create(ClientboundPackets1_9.SET_PASSENGERS);
                     if (vehicle == -1) {
                        if (!tracker.getVehicleMap().containsKey(passenger)) {
                           return null;
                        }

                        passengerPacket.write(Type.VAR_INT, (Integer)tracker.getVehicleMap().remove(passenger));
                        passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                     } else {
                        passengerPacket.write(Type.VAR_INT, vehicle);
                        passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{passenger});
                        tracker.getVehicleMap().put(passenger, vehicle);
                     }

                     passengerPacket.send(Protocol1_9To1_8.class);
                  }

                  return null;
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  if (Via.getConfig().isHologramPatch()) {
                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                     if (tracker.getKnownHolograms().contains(entityID)) {
                        Double newValue = (Double)wrapper.get(Type.DOUBLE, 1);
                        newValue = newValue + Via.getConfig().getHologramYOffset();
                        wrapper.set(Type.DOUBLE, 1, newValue);
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BOOLEAN);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.SHORT, new ValueTransformer(Type.VAR_INT) {
               public Integer transform(PacketWrapper wrapper, Short slot) throws Exception {
                  int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
                  int receiverId = wrapper.user().getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
                  return entityId == receiverId ? slot.intValue() + 2 : slot > 0 ? slot.intValue() + 1 : slot.intValue();
               }
            });
            this.map(Type.ITEM);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Item stack = (Item)wrapper.get(Type.ITEM, 0);
                  ItemRewriter.toClient(stack);
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  int entityID = (Integer)wrapper.get(Type.VAR_INT, 0);
                  Item stack = (Item)wrapper.get(Type.ITEM, 0);
                  if (stack != null && Protocol1_9To1_8.isSword(stack.identifier())) {
                     entityTracker.getValidBlocking().add(entityID);
                  } else {
                     entityTracker.getValidBlocking().remove(entityID);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_METADATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  List metadataList = (List)wrapper.get(Types1_9.METADATA_LIST, 0);
                  int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  if (tracker.hasEntity(entityId)) {
                     ((MetadataRewriter1_9To1_8)protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
                  } else {
                     tracker.addMetadataToBuffer(entityId, metadataList);
                     wrapper.cancel();
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
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  List metadataList = (List)wrapper.get(Types1_9.METADATA_LIST, 0);
                  if (metadataList.isEmpty()) {
                     wrapper.cancel();
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_EFFECT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  boolean showParticles = (Boolean)wrapper.read(Type.BOOLEAN);
                  boolean newEffect = Via.getConfig().isNewEffectIndicator();
                  wrapper.write(Type.BYTE, (byte)(showParticles ? (newEffect ? 2 : 1) : 0));
               }
            });
         }
      });
      protocol.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
      protocol.registerClientbound(ClientboundPackets1_8.COMBAT_EVENT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  if ((Integer)wrapper.get(Type.VAR_INT, 0) == 2) {
                     wrapper.passthrough(Type.VAR_INT);
                     wrapper.passthrough(Type.INT);
                     Protocol1_9To1_8.FIX_JSON.write(wrapper, (String)wrapper.read(Type.STRING));
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  if (Via.getConfig().isMinimizeCooldown()) {
                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                     if ((Integer)wrapper.get(Type.VAR_INT, 0) == tracker.getProvidedEntityId()) {
                        int propertiesToRead = (Integer)wrapper.read(Type.INT);
                        Map properties = new HashMap(propertiesToRead);

                        for(int i = 0; i < propertiesToRead; ++i) {
                           String key = (String)wrapper.read(Type.STRING);
                           Double value = (Double)wrapper.read(Type.DOUBLE);
                           int modifiersToRead = (Integer)wrapper.read(Type.VAR_INT);
                           List modifiers = new ArrayList(modifiersToRead);

                           for(int j = 0; j < modifiersToRead; ++j) {
                              modifiers.add(new Triple((UUID)wrapper.read(Type.UUID), (Double)wrapper.read(Type.DOUBLE), (Byte)wrapper.read(Type.BYTE)));
                           }

                           properties.put(key, new Pair(value, modifiers));
                        }

                        properties.put("generic.attackSpeed", new Pair(15.9D, ImmutableList.of(new Triple(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), 0.0D, (byte)0), new Triple(UUID.fromString("AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3"), 0.0D, (byte)2), new Triple(UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386"), 0.0D, (byte)2))));
                        wrapper.write(Type.INT, properties.size());
                        Iterator var11 = properties.entrySet().iterator();

                        while(var11.hasNext()) {
                           Entry entry = (Entry)var11.next();
                           wrapper.write(Type.STRING, (String)entry.getKey());
                           wrapper.write(Type.DOUBLE, (Double)((Pair)entry.getValue()).getKey());
                           wrapper.write(Type.VAR_INT, ((List)((Pair)entry.getValue()).getValue()).size());
                           Iterator var13 = ((List)((Pair)entry.getValue()).getValue()).iterator();

                           while(var13.hasNext()) {
                              Triple modifier = (Triple)var13.next();
                              wrapper.write(Type.UUID, (UUID)modifier.getFirst());
                              wrapper.write(Type.DOUBLE, (Double)modifier.getSecond());
                              wrapper.write(Type.BYTE, (Byte)modifier.getThird());
                           }
                        }

                     }
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_ANIMATION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  if ((Short)wrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                     wrapper.cancel();
                  }

               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.ENTITY_ACTION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int action = (Integer)wrapper.get(Type.VAR_INT, 1);
                  if (action == 6 || action == 8) {
                     wrapper.cancel();
                  }

                  if (action == 7) {
                     wrapper.set(Type.VAR_INT, 1, 6);
                  }

               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.INTERACT_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int type = (Integer)wrapper.get(Type.VAR_INT, 1);
                  if (type == 2) {
                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.FLOAT);
                  }

                  if (type == 0 || type == 2) {
                     int hand = (Integer)wrapper.read(Type.VAR_INT);
                     if (hand == 1) {
                        wrapper.cancel();
                     }
                  }

               }
            });
         }
      });
   }

   static {
      toNewShort = new ValueTransformer(Type.SHORT) {
         public Short transform(PacketWrapper wrapper, Byte inputValue) {
            return (short)(inputValue * 128);
         }
      };
   }
}
