package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import java.util.UUID;

public class EntityPackets {
   private static final PacketHandler DIMENSION_HANDLER = (wrapper) -> {
      int dimension = (Integer)wrapper.read(Type.INT);
      String dimensionName;
      switch(dimension) {
      case -1:
         dimensionName = "minecraft:the_nether";
         break;
      case 0:
         dimensionName = "minecraft:overworld";
         break;
      case 1:
         dimensionName = "minecraft:the_end";
         break;
      default:
         Via.getPlatform().getLogger().warning("Invalid dimension id: " + dimension);
         dimensionName = "minecraft:overworld";
      }

      wrapper.write(Type.STRING, dimensionName);
      wrapper.write(Type.STRING, dimensionName);
   };
   public static final CompoundTag DIMENSIONS_TAG = new CompoundTag();
   private static final String[] WORLD_NAMES = new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

   private static CompoundTag createOverworldEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("name", new StringTag("minecraft:overworld"));
      tag.put("has_ceiling", new ByteTag((byte)0));
      addSharedOverwaldEntries(tag);
      return tag;
   }

   private static CompoundTag createOverworldCavesEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("name", new StringTag("minecraft:overworld_caves"));
      tag.put("has_ceiling", new ByteTag((byte)1));
      addSharedOverwaldEntries(tag);
      return tag;
   }

   private static void addSharedOverwaldEntries(CompoundTag tag) {
      tag.put("piglin_safe", new ByteTag((byte)0));
      tag.put("natural", new ByteTag((byte)1));
      tag.put("ambient_light", new FloatTag(0.0F));
      tag.put("infiniburn", new StringTag("minecraft:infiniburn_overworld"));
      tag.put("respawn_anchor_works", new ByteTag((byte)0));
      tag.put("has_skylight", new ByteTag((byte)1));
      tag.put("bed_works", new ByteTag((byte)1));
      tag.put("has_raids", new ByteTag((byte)1));
      tag.put("logical_height", new IntTag(256));
      tag.put("shrunk", new ByteTag((byte)0));
      tag.put("ultrawarm", new ByteTag((byte)0));
   }

   private static CompoundTag createNetherEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("piglin_safe", new ByteTag((byte)1));
      tag.put("natural", new ByteTag((byte)0));
      tag.put("ambient_light", new FloatTag(0.1F));
      tag.put("infiniburn", new StringTag("minecraft:infiniburn_nether"));
      tag.put("respawn_anchor_works", new ByteTag((byte)1));
      tag.put("has_skylight", new ByteTag((byte)0));
      tag.put("bed_works", new ByteTag((byte)0));
      tag.put("fixed_time", new LongTag(18000L));
      tag.put("has_raids", new ByteTag((byte)0));
      tag.put("name", new StringTag("minecraft:the_nether"));
      tag.put("logical_height", new IntTag(128));
      tag.put("shrunk", new ByteTag((byte)1));
      tag.put("ultrawarm", new ByteTag((byte)1));
      tag.put("has_ceiling", new ByteTag((byte)1));
      return tag;
   }

   private static CompoundTag createEndEntry() {
      CompoundTag tag = new CompoundTag();
      tag.put("piglin_safe", new ByteTag((byte)0));
      tag.put("natural", new ByteTag((byte)0));
      tag.put("ambient_light", new FloatTag(0.0F));
      tag.put("infiniburn", new StringTag("minecraft:infiniburn_end"));
      tag.put("respawn_anchor_works", new ByteTag((byte)0));
      tag.put("has_skylight", new ByteTag((byte)0));
      tag.put("bed_works", new ByteTag((byte)0));
      tag.put("fixed_time", new LongTag(6000L));
      tag.put("has_raids", new ByteTag((byte)1));
      tag.put("name", new StringTag("minecraft:the_end"));
      tag.put("logical_height", new IntTag(256));
      tag.put("shrunk", new ByteTag((byte)0));
      tag.put("ultrawarm", new ByteTag((byte)0));
      tag.put("has_ceiling", new ByteTag((byte)0));
      return tag;
   }

   public static void register(final Protocol1_16To1_15_2 protocol) {
      MetadataRewriter1_16To1_15_2 metadataRewriter = (MetadataRewriter1_16To1_15_2)protocol.get(MetadataRewriter1_16To1_15_2.class);
      protocol.registerClientbound(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_16.SPAWN_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.passthrough(Type.VAR_INT);
               wrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(entityId, Entity1_16Types.LIGHTNING_BOLT);
               wrapper.write(Type.UUID, UUID.randomUUID());
               wrapper.write(Type.VAR_INT, Entity1_16Types.LIGHTNING_BOLT.getId());
               wrapper.read(Type.BYTE);
               wrapper.passthrough(Type.DOUBLE);
               wrapper.passthrough(Type.DOUBLE);
               wrapper.passthrough(Type.DOUBLE);
               wrapper.write(Type.BYTE, (byte)0);
               wrapper.write(Type.BYTE, (byte)0);
               wrapper.write(Type.INT, 0);
               wrapper.write(Type.SHORT, Short.valueOf((short)0));
               wrapper.write(Type.SHORT, Short.valueOf((short)0));
               wrapper.write(Type.SHORT, Short.valueOf((short)0));
            });
         }
      });
      metadataRewriter.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_16Types.FALLING_BLOCK);
      metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_MOB);
      metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_PLAYER, Entity1_16Types.PLAYER);
      metadataRewriter.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_16.METADATA_LIST);
      metadataRewriter.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
      protocol.registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketRemapper() {
         public void registerMap() {
            this.handler(EntityPackets.DIMENSION_HANDLER);
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               wrapper.write(Type.BYTE, -1);
               String levelType = (String)wrapper.read(Type.STRING);
               wrapper.write(Type.BOOLEAN, false);
               wrapper.write(Type.BOOLEAN, levelType.equals("flat"));
               wrapper.write(Type.BOOLEAN, true);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               wrapper.write(Type.BYTE, -1);
               wrapper.write(Type.STRING_ARRAY, EntityPackets.WORLD_NAMES);
               wrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG);
            });
            this.handler(EntityPackets.DIMENSION_HANDLER);
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               wrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity((Integer)wrapper.get(Type.INT, 0), Entity1_16Types.PLAYER);
               String type = (String)wrapper.read(Type.STRING);
               wrapper.passthrough(Type.VAR_INT);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.write(Type.BOOLEAN, false);
               wrapper.write(Type.BOOLEAN, type.equals("flat"));
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Type.VAR_INT);
               int size = (Integer)wrapper.passthrough(Type.INT);
               int actualSize = size;

               label40:
               for(int i = 0; i < size; ++i) {
                  String key = (String)wrapper.read(Type.STRING);
                  String attributeIdentifier = (String)protocol.getMappingData().getAttributeMappings().get(key);
                  int modifierSize;
                  int j;
                  if (attributeIdentifier == null) {
                     attributeIdentifier = "minecraft:" + key;
                     if (!MappingData.isValid1_13Channel(attributeIdentifier)) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                           Via.getPlatform().getLogger().warning("Invalid attribute: " + key);
                        }

                        --actualSize;
                        wrapper.read(Type.DOUBLE);
                        modifierSize = (Integer)wrapper.read(Type.VAR_INT);
                        j = 0;

                        while(true) {
                           if (j >= modifierSize) {
                              continue label40;
                           }

                           wrapper.read(Type.UUID);
                           wrapper.read(Type.DOUBLE);
                           wrapper.read(Type.BYTE);
                           ++j;
                        }
                     }
                  }

                  wrapper.write(Type.STRING, attributeIdentifier);
                  wrapper.passthrough(Type.DOUBLE);
                  modifierSize = (Integer)wrapper.passthrough(Type.VAR_INT);

                  for(j = 0; j < modifierSize; ++j) {
                     wrapper.passthrough(Type.UUID);
                     wrapper.passthrough(Type.DOUBLE);
                     wrapper.passthrough(Type.BYTE);
                  }
               }

               if (size != actualSize) {
                  wrapper.set(Type.INT, 0, actualSize);
               }

            });
         }
      });
   }

   static {
      ListTag list = new ListTag(CompoundTag.class);
      list.add(createOverworldEntry());
      list.add(createOverworldCavesEntry());
      list.add(createNetherEntry());
      list.add(createEndEntry());
      DIMENSIONS_TAG.put("dimension", list);
   }
}
