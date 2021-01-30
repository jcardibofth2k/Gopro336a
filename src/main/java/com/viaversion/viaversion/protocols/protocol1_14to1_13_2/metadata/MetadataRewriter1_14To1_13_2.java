package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_14;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import io.netty.buffer.ByteBuf;
import java.util.List;

public class MetadataRewriter1_14To1_13_2 extends EntityRewriter {
   public MetadataRewriter1_14To1_13_2(Protocol1_14To1_13_2 protocol) {
      super(protocol);
      this.mapTypes(Entity1_13Types.EntityType.values(), Entity1_14Types.class);
      this.mapEntityType(Entity1_13Types.EntityType.OCELOT, Entity1_14Types.CAT);
   }

   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List metadatas, UserConnection connection) throws Exception {
      metadata.setMetaType(MetaType1_14.byId(metadata.metaType().typeId()));
      EntityTracker1_14 tracker = (EntityTracker1_14)this.tracker(connection);
      int armorType;
      if (metadata.metaType() == MetaType1_14.Slot) {
         ((Protocol1_14To1_13_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == MetaType1_14.BlockID) {
         armorType = (Integer)metadata.getValue();
         metadata.setValue(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(armorType));
      } else if (metadata.metaType() == MetaType1_14.PARTICLE) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (metadata.method_71() > 5) {
            metadata.setId(metadata.method_71() + 1);
         }

         if (metadata.method_71() == 8 && type.isOrHasParent(Entity1_14Types.LIVINGENTITY)) {
            float v = ((Number)metadata.getValue()).floatValue();
            if (Float.isNaN(v) && Via.getConfig().is1_14HealthNaNFix()) {
               metadata.setValue(1.0F);
            }
         }

         if (metadata.method_71() > 11 && type.isOrHasParent(Entity1_14Types.LIVINGENTITY)) {
            metadata.setId(metadata.method_71() + 1);
         }

         if (type.isOrHasParent(Entity1_14Types.ABSTRACT_INSENTIENT) && metadata.method_71() == 13) {
            tracker.setInsentientData(entityId, (byte)(((Number)metadata.getValue()).byteValue() & -5 | tracker.getInsentientData(entityId) & 4));
            metadata.setValue(tracker.getInsentientData(entityId));
         }

         if (type.isOrHasParent(Entity1_14Types.PLAYER)) {
            if (entityId != tracker.clientEntityId()) {
               if (metadata.method_71() == 0) {
                  byte flags = ((Number)metadata.getValue()).byteValue();
                  tracker.setEntityFlags(entityId, flags);
               } else if (metadata.method_71() == 7) {
                  tracker.setRiptide(entityId, (((Number)metadata.getValue()).byteValue() & 4) != 0);
               }

               if (metadata.method_71() == 0 || metadata.method_71() == 7) {
                  metadatas.add(new Metadata(6, MetaType1_14.Pose, recalculatePlayerPose(entityId, tracker)));
               }
            }
         } else if (type.isOrHasParent(Entity1_14Types.ZOMBIE)) {
            if (metadata.method_71() == 16) {
               tracker.setInsentientData(entityId, (byte)(tracker.getInsentientData(entityId) & -5 | ((Boolean)metadata.getValue() ? 4 : 0)));
               metadatas.remove(metadata);
               metadatas.add(new Metadata(13, MetaType1_14.Byte, tracker.getInsentientData(entityId)));
            } else if (metadata.method_71() > 16) {
               metadata.setId(metadata.method_71() - 1);
            }
         }

         if (type.isOrHasParent(Entity1_14Types.MINECART_ABSTRACT)) {
            if (metadata.method_71() == 10) {
               armorType = (Integer)metadata.getValue();
               metadata.setValue(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(armorType));
            }
         } else if (type.method_23(Entity1_14Types.HORSE)) {
            if (metadata.method_71() == 18) {
               metadatas.remove(metadata);
               armorType = (Integer)metadata.getValue();
               Item armorItem = null;
               if (armorType == 1) {
                  armorItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(727), (byte)1, (short)0, (CompoundTag)null);
               } else if (armorType == 2) {
                  armorItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(728), (byte)1, (short)0, (CompoundTag)null);
               } else if (armorType == 3) {
                  armorItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(729), (byte)1, (short)0, (CompoundTag)null);
               }

               PacketWrapper equipmentPacket = PacketWrapper.create(70, (ByteBuf)null, connection);
               equipmentPacket.write(Type.VAR_INT, entityId);
               equipmentPacket.write(Type.VAR_INT, 4);
               equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, armorItem);
               equipmentPacket.scheduleSend(Protocol1_14To1_13_2.class);
            }
         } else if (type.method_23(Entity1_14Types.VILLAGER)) {
            if (metadata.method_71() == 15) {
               metadata.setTypeAndValue(MetaType1_14.VillagerData, new VillagerData(2, getNewProfessionId((Integer)metadata.getValue()), 0));
            }
         } else if (type.method_23(Entity1_14Types.ZOMBIE_VILLAGER)) {
            if (metadata.method_71() == 18) {
               metadata.setTypeAndValue(MetaType1_14.VillagerData, new VillagerData(2, getNewProfessionId((Integer)metadata.getValue()), 0));
            }
         } else if (type.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
            if (metadata.method_71() >= 9) {
               metadata.setId(metadata.method_71() + 1);
            }
         } else if (type.method_23(Entity1_14Types.FIREWORK_ROCKET)) {
            if (metadata.method_71() == 8) {
               metadata.setMetaType(MetaType1_14.OptVarInt);
               if (metadata.getValue().equals(0)) {
                  metadata.setValue((Object)null);
               }
            }
         } else if (type.isOrHasParent(Entity1_14Types.ABSTRACT_SKELETON) && metadata.method_71() == 14) {
            tracker.setInsentientData(entityId, (byte)(tracker.getInsentientData(entityId) & -5 | ((Boolean)metadata.getValue() ? 4 : 0)));
            metadatas.remove(metadata);
            metadatas.add(new Metadata(13, MetaType1_14.Byte, tracker.getInsentientData(entityId)));
         }

         if (type.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) && metadata.method_71() == 14) {
            tracker.setInsentientData(entityId, (byte)(tracker.getInsentientData(entityId) & -5 | (((Number)metadata.getValue()).byteValue() != 0 ? 4 : 0)));
            metadatas.remove(metadata);
            metadatas.add(new Metadata(13, MetaType1_14.Byte, tracker.getInsentientData(entityId)));
         }

         if ((type.method_23(Entity1_14Types.WITCH) || type.method_23(Entity1_14Types.RAVAGER) || type.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE)) && metadata.method_71() >= 14) {
            metadata.setId(metadata.method_71() + 1);
         }

      }
   }

   public EntityType typeFromId(int type) {
      return Entity1_14Types.getTypeFromId(type);
   }

   private static boolean isSneaking(byte flags) {
      return (flags & 2) != 0;
   }

   private static boolean isSwimming(byte flags) {
      return (flags & 16) != 0;
   }

   private static int getNewProfessionId(int old) {
      switch(old) {
      case 0:
         return 5;
      case 1:
         return 9;
      case 2:
         return 4;
      case 3:
         return 1;
      case 4:
         return 2;
      case 5:
         return 11;
      default:
         return 0;
      }
   }

   private static boolean isFallFlying(int entityFlags) {
      return (entityFlags & 128) != 0;
   }

   public static int recalculatePlayerPose(int entityId, EntityTracker1_14 tracker) {
      byte flags = tracker.getEntityFlags(entityId);
      int pose = 0;
      if (isFallFlying(flags)) {
         pose = 1;
      } else if (tracker.isSleeping(entityId)) {
         pose = 2;
      } else if (isSwimming(flags)) {
         pose = 3;
      } else if (tracker.isRiptide(entityId)) {
         pose = 4;
      } else if (isSneaking(flags)) {
         pose = 5;
      }

      return pose;
   }
}
