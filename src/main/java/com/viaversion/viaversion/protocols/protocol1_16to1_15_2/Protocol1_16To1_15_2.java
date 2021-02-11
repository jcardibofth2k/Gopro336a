package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.google.common.base.Joiner;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.TranslationMappings;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Protocol1_16To1_15_2 extends AbstractProtocol {
   private static final UUID ZERO_UUID = new UUID(0L, 0L);
   public static final MappingData MAPPINGS = new MappingData();
   private final EntityRewriter metadataRewriter = new MetadataRewriter1_16To1_15_2(this);
   private final ItemRewriter itemRewriter = new InventoryPackets(this);
   private TagRewriter tagRewriter;

   public Protocol1_16To1_15_2() {
      super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
   }

   protected void registerPackets() {
      this.metadataRewriter.register();
      this.itemRewriter.register();
      EntityPackets.register(this);
      WorldPackets.register(this);
      this.tagRewriter = new TagRewriter(this);
      this.tagRewriter.register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_15.STATISTICS);
      this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               UUID uuid = UUID.fromString((String)wrapper.read(Type.STRING));
               wrapper.write(Type.UUID_INT_ARRAY, uuid);
            });
         }
      });
      this.registerClientbound(State.STATUS, 0, 0, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               String original = (String)wrapper.passthrough(Type.STRING);
               JsonObject object = (JsonObject)GsonUtil.getGson().fromJson(original, JsonObject.class);
               JsonObject players = object.getAsJsonObject("players");
               if (players != null) {
                  JsonArray sample = players.getAsJsonArray("sample");
                  if (sample != null) {
                     JsonArray splitSamples = new JsonArray();
                     Iterator var6 = sample.iterator();

                     while(true) {
                        while(var6.hasNext()) {
                           JsonElement element = (JsonElement)var6.next();
                           JsonObject playerInfo = element.getAsJsonObject();
                           String name = playerInfo.getAsJsonPrimitive("name").getAsString();
                           if (name.indexOf(10) == -1) {
                              splitSamples.add(playerInfo);
                           } else {
                              String id = playerInfo.getAsJsonPrimitive("id").getAsString();
                              String[] var11 = name.split("\n");
                              int var12 = var11.length;

                              for(int var13 = 0; var13 < var12; ++var13) {
                                 String s = var11[var13];
                                 JsonObject newSample = new JsonObject();
                                 newSample.addProperty("name", s);
                                 newSample.addProperty("id", id);
                                 splitSamples.add(newSample);
                              }
                           }
                        }

                        if (splitSamples.size() != sample.size()) {
                           players.add("sample", splitSamples);
                           wrapper.set(Type.STRING, 0, object.toString());
                        }

                        return;
                     }
                  }
               }
            });
         }
      });
      final ComponentRewriter componentRewriter = new TranslationMappings(this);
      this.registerClientbound(ClientboundPackets1_15.CHAT_MESSAGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.COMPONENT);
            this.map(Type.BYTE);
            this.handler((wrapper) -> {
               componentRewriter.processText((JsonElement)wrapper.get(Type.COMPONENT, 0));
               wrapper.write(Type.UUID, Protocol1_16To1_15_2.ZERO_UUID);
            });
         }
      });
      componentRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
      componentRewriter.registerTitle(ClientboundPackets1_15.TITLE);
      componentRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
      SoundRewriter soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
      this.registerServerbound(ServerboundPackets1_16.INTERACT_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Type.VAR_INT);
               int action = (Integer)wrapper.passthrough(Type.VAR_INT);
               if (action == 0 || action == 2) {
                  if (action == 2) {
                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.FLOAT);
                  }

                  wrapper.passthrough(Type.VAR_INT);
               }

               wrapper.read(Type.BOOLEAN);
            });
         }
      });
      if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
         this.registerServerbound(ServerboundPackets1_16.PLUGIN_MESSAGE, new PacketRemapper() {
            public void registerMap() {
               this.handler((wrapper) -> {
                  String channel = (String)wrapper.passthrough(Type.STRING);
                  if (channel.length() > 32) {
                     if (!Via.getConfig().isSuppressConversionWarnings()) {
                        Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel, as it is longer than 32 characters: " + channel);
                     }

                     wrapper.cancel();
                  } else if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                     String[] channels = (new String((byte[])wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
                     List checkedChannels = new ArrayList(channels.length);
                     String[] var4 = channels;
                     int var5 = channels.length;

                     for(int var6 = 0; var6 < var5; ++var6) {
                        String registeredChannel = var4[var6];
                        if (registeredChannel.length() > 32) {
                           if (!Via.getConfig().isSuppressConversionWarnings()) {
                              Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel register of '" + registeredChannel + "', as it is longer than 32 characters");
                           }
                        } else {
                           checkedChannels.add(registeredChannel);
                        }
                     }

                     if (checkedChannels.isEmpty()) {
                        wrapper.cancel();
                        return;
                     }

                     wrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(checkedChannels).getBytes(StandardCharsets.UTF_8));
                  }

               });
            }
         });
      }

      this.registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Type.BYTE);
               wrapper.write(Type.FLOAT, 0.05F);
               wrapper.write(Type.FLOAT, 0.1F);
            });
         }
      });
      this.cancelServerbound(ServerboundPackets1_16.GENERATE_JIGSAW);
      this.cancelServerbound(ServerboundPackets1_16.UPDATE_JIGSAW_BLOCK);
   }

   protected void onMappingDataLoaded() {
      int[] wallPostOverrideTag = new int[47];
      int arrayIndex = 0;
      int var4 = arrayIndex + 1;
      wallPostOverrideTag[arrayIndex] = 140;
      wallPostOverrideTag[var4++] = 179;
      wallPostOverrideTag[var4++] = 264;

      int i;
      for(i = 153; i <= 158; wallPostOverrideTag[var4++] = i++) {
      }

      for(i = 163; i <= 168; wallPostOverrideTag[var4++] = i++) {
      }

      for(i = 408; i <= 439; wallPostOverrideTag[var4++] = i++) {
      }

      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", wallPostOverrideTag);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:beacon_base_blocks", 133, 134, 148, 265);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:climbable", 160, 241, 658);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fire", 142);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:campfires", 679);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fence_gates", 242, 467, 468, 469, 470, 471);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:unstable_bottom_center", 242, 467, 468, 469, 470, 471);
      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wooden_trapdoors", 193, 194, 195, 196, 197, 198);
      this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:wooden_trapdoors", 215, 216, 217, 218, 219, 220);
      this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:beacon_payment_items", 529, 530, 531, 760);
      this.tagRewriter.addTag(RegistryType.ENTITY, "minecraft:impact_projectiles", 2, 72, 71, 37, 69, 79, 83, 15, 93);
      this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:guarded_by_piglins");
      this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_speed_blocks");
      this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_fire_base_blocks");
      this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
      this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:non_flammable_wood");
      this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables", "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers", "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors");
      this.tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons");
      this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers", "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences");
   }

   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER));
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityRewriter getEntityRewriter() {
      return this.metadataRewriter;
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }
}
