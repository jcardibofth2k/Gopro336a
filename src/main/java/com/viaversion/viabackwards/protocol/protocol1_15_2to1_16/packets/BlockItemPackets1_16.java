package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewrites;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;

public class BlockItemPackets1_16 extends ItemRewriter {
   private EnchantmentRewriter enchantmentRewriter;

   public BlockItemPackets1_16(Protocol1_15_2To1_16 protocol, TranslatableRewriter translatableRewriter) {
      super(protocol, translatableRewriter);
   }

   protected void registerPackets() {
      BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
      final RecipeRewriter1_14 recipeRewriter = new RecipeRewriter1_14(this.protocol);
      this.protocol.registerClientbound(ClientboundPackets1_16.DECLARE_RECIPES, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int size = (Integer)wrapper.passthrough(Type.VAR_INT);
               int newSize = size;

               for(int i = 0; i < size; ++i) {
                  String originalType = (String)wrapper.read(Type.STRING);
                  String type = originalType.replace("minecraft:", "");
                  if (type.equals("smithing")) {
                     --newSize;
                     wrapper.read(Type.STRING);
                     wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                     wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                     wrapper.read(Type.FLAT_VAR_INT_ITEM);
                  } else {
                     wrapper.write(Type.STRING, originalType);
                     String id = (String)wrapper.passthrough(Type.STRING);
                     recipeRewriter.handle(wrapper, type);
                  }
               }

               wrapper.set(Type.VAR_INT, 0, newSize);
            });
         }
      });
      this.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      this.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      this.registerTradeList(ClientboundPackets1_16.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      this.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
      blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
      this.protocol.registerClientbound(ClientboundPackets1_16.ENTITY_EQUIPMENT, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.passthrough(Type.VAR_INT);
               ArrayList equipmentData = new ArrayList();

               byte slot;
               int i;
               do {
                  slot = (Byte)wrapper.read(Type.BYTE);
                  Item item = BlockItemPackets1_16.this.handleItemToClient((Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                  i = slot & 127;
                  equipmentData.add(new BlockItemPackets1_16.EquipmentData(i, item));
               } while((slot & -128) != 0);

               BlockItemPackets1_16.EquipmentData firstData = (BlockItemPackets1_16.EquipmentData)equipmentData.get(0);
               wrapper.write(Type.VAR_INT, firstData.slot);
               wrapper.write(Type.FLAT_VAR_INT_ITEM, firstData.item);

               for(i = 1; i < equipmentData.size(); ++i) {
                  PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT);
                  BlockItemPackets1_16.EquipmentData data = (BlockItemPackets1_16.EquipmentData)equipmentData.get(i);
                  equipmentPacket.write(Type.VAR_INT, entityId);
                  equipmentPacket.write(Type.VAR_INT, data.slot);
                  equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, data.item);
                  equipmentPacket.send(Protocol1_15_2To1_16.class);
               }

            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.UPDATE_LIGHT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN, Type.NOTHING);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               Chunk chunk = (Chunk)wrapper.read(new Chunk1_16Type());
               wrapper.write(new Chunk1_15Type(), chunk);

               int biome;
               for(int i = 0; i < chunk.getSections().length; ++i) {
                  ChunkSection section = chunk.getSections()[i];
                  if (section != null) {
                     for(biome = 0; biome < section.getPaletteSize(); ++biome) {
                        int old = section.getPaletteEntry(biome);
                        section.setPaletteEntry(biome, ((Protocol1_15_2To1_16)BlockItemPackets1_16.this.protocol).getMappingData().getNewBlockStateId(old));
                     }
                  }
               }

               CompoundTag heightMaps = chunk.getHeightMap();
               Iterator var9 = heightMaps.values().iterator();

               while(var9.hasNext()) {
                  Tag heightMapTag = (Tag)var9.next();
                  LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                  int[] heightMapData = new int[256];
                  CompactArrayUtil.iterateCompactArrayWithPadding(9, heightMapData.length, heightMap.getValue(), (ixx, v) -> {
                     heightMapData[ixx] = v;
                  });
                  heightMap.setValue(CompactArrayUtil.createCompactArray(9, heightMapData.length, (ixx) -> {
                     return heightMapData[ixx];
                  }));
               }

               if (chunk.isBiomeData()) {
                  int ix = 0;

                  while(ix < 1024) {
                     biome = chunk.getBiomeData()[ix];
                     switch(biome) {
                     case 170:
                     case 171:
                     case 172:
                     case 173:
                        chunk.getBiomeData()[ix] = 8;
                     default:
                        ++ix;
                     }
                  }
               }

               if (chunk.getBlockEntities() != null) {
                  var9 = chunk.getBlockEntities().iterator();

                  while(var9.hasNext()) {
                     CompoundTag blockEntity = (CompoundTag)var9.next();
                     BlockItemPackets1_16.this.handleBlockEntity(blockEntity);
                  }

               }
            });
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
      this.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
      this.protocol.registerClientbound(ClientboundPackets1_16.WINDOW_PROPERTY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler((wrapper) -> {
               short property = (Short)wrapper.get(Type.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  short enchantmentId = (Short)wrapper.get(Type.SHORT, 1);
                  if (enchantmentId > 11) {
                     --enchantmentId;
                     wrapper.set(Type.SHORT, 1, enchantmentId);
                  } else if (enchantmentId == 11) {
                     wrapper.set(Type.SHORT, 1, Short.valueOf((short)9));
                  }
               }

            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.MAP_DATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.BLOCK_ENTITY_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               Position position = (Position)wrapper.passthrough(Type.POSITION1_14);
               short action = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);
               CompoundTag tag = (CompoundTag)wrapper.passthrough(Type.NBT);
               BlockItemPackets1_16.this.handleBlockEntity(tag);
            });
         }
      });
      this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
      this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      this.protocol.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               BlockItemPackets1_16.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            });
         }
      });
   }

   private void handleBlockEntity(CompoundTag tag) {
      StringTag idTag = (StringTag)tag.get("id");
      if (idTag != null) {
         String id = idTag.getValue();
         Tag skullOwnerTag;
         if (id.equals("minecraft:conduit")) {
            skullOwnerTag = tag.remove("Target");
            if (!(skullOwnerTag instanceof IntArrayTag)) {
               return;
            }

            UUID targetUuid = UUIDIntArrayType.uuidFromIntArray((int[])skullOwnerTag.getValue());
            tag.put("target_uuid", new StringTag(targetUuid.toString()));
         } else if (id.equals("minecraft:skull")) {
            skullOwnerTag = tag.remove("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
               return;
            }

            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            Tag ownerUuidTag = skullOwnerCompoundTag.remove("Id");
            if (ownerUuidTag instanceof IntArrayTag) {
               UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[])ownerUuidTag.getValue());
               skullOwnerCompoundTag.put("Id", new StringTag(ownerUuid.toString()));
            }

            CompoundTag ownerTag = new CompoundTag();
            Iterator var8 = skullOwnerCompoundTag.iterator();

            while(var8.hasNext()) {
               Entry entry = (Entry)var8.next();
               ownerTag.put((String)entry.getKey(), (Tag)entry.getValue());
            }

            tag.put("Owner", ownerTag);
         }

      }
   }

   protected void registerRewrites() {
      this.enchantmentRewriter = new EnchantmentRewriter(this);
      this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "§7Soul Speed");
   }

   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToClient(item);
         CompoundTag tag = item.tag();
         if (item.identifier() == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof IntArrayTag) {
                  UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[])idTag.getValue());
                  ownerCompundTag.put("Id", new StringTag(ownerUuid.toString()));
               }
            }
         }

         InventoryPackets.newToOldAttributes(item);
         this.enchantmentRewriter.handleToClient(item);
         return item;
      }
   }

   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         int identifier = item.identifier();
         super.handleItemToServer(item);
         CompoundTag tag = item.tag();
         if (identifier == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof StringTag) {
                  UUID ownerUuid = UUID.fromString((String)idTag.getValue());
                  ownerCompundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(ownerUuid)));
               }
            }
         }

         InventoryPackets.oldToNewAttributes(item);
         this.enchantmentRewriter.handleToServer(item);
         return item;
      }
   }

   private static final class EquipmentData {
      private final int slot;
      private final Item item;

      private EquipmentData(int slot, Item item) {
         this.slot = slot;
         this.item = item;
      }

      // $FF: synthetic method
      EquipmentData(int x0, Item x1, Object x2) {
         this(x0, x1);
      }
   }
}
