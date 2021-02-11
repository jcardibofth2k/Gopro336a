package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import java.util.Iterator;
import java.util.UUID;

public class InventoryPackets extends ItemRewriter {
   public InventoryPackets(Protocol1_16To1_15_2 protocol) {
      super(protocol);
   }

   public void registerPackets() {
      final PacketHandler cursorRemapper = (wrapper) -> {
         PacketWrapper clearPacket = wrapper.create(ClientboundPackets1_16.SET_SLOT);
         clearPacket.write(Type.UNSIGNED_BYTE, Short.valueOf((short)-1));
         clearPacket.write(Type.SHORT, Short.valueOf((short)-1));
         clearPacket.write(Type.FLAT_VAR_INT_ITEM, null);
         clearPacket.send(Protocol1_16To1_15_2.class);
      };
      this.protocol.registerClientbound(ClientboundPackets1_15.OPEN_WINDOW, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.COMPONENT);
            this.handler((wrapper) -> {
               int windowType = (Integer)wrapper.get(Type.VAR_INT, 1);
               if (windowType >= 20) {
                  ++windowType;
                  wrapper.set(Type.VAR_INT, 1, windowType);
               }

            });
            this.handler(cursorRemapper);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.CLOSE_WINDOW, new PacketRemapper() {
         public void registerMap() {
            this.handler(cursorRemapper);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.WINDOW_PROPERTY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler((wrapper) -> {
               short property = (Short)wrapper.get(Type.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  short enchantmentId = (Short)wrapper.get(Type.SHORT, 1);
                  if (enchantmentId >= 11) {
                     ++enchantmentId;
                     wrapper.set(Type.SHORT, 1, enchantmentId);
                  }
               }

            });
         }
      });
      this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      this.registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      this.protocol.registerClientbound(ClientboundPackets1_15.ENTITY_EQUIPMENT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int slot = (Integer)wrapper.read(Type.VAR_INT);
               wrapper.write(Type.BYTE, (byte)slot);
               InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            });
         }
      });
      (new RecipeRewriter1_14(this.protocol)).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
      this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
      this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      this.protocol.registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               InventoryPackets.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            });
         }
      });
      this.registerSpawnParticle(ClientboundPackets1_15.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
   }

   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         if (item.identifier() == 771 && item.tag() != null) {
            CompoundTag tag = item.tag();
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof StringTag) {
                  UUID id = UUID.fromString((String)idTag.getValue());
                  ownerCompundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(id)));
               }
            }
         }

         oldToNewAttributes(item);
         item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.identifier()));
         return item;
      }
   }

   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.identifier()));
         if (item.identifier() == 771 && item.tag() != null) {
            CompoundTag tag = item.tag();
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof IntArrayTag) {
                  UUID id = UUIDIntArrayType.uuidFromIntArray((int[])idTag.getValue());
                  ownerCompundTag.put("Id", new StringTag(id.toString()));
               }
            }
         }

         newToOldAttributes(item);
         return item;
      }
   }

   public static void oldToNewAttributes(Item item) {
      if (item.tag() != null) {
         ListTag attributes = (ListTag)item.tag().get("AttributeModifiers");
         if (attributes != null) {
            Iterator var2 = attributes.iterator();

            while(var2.hasNext()) {
               Tag tag = (Tag)var2.next();
               CompoundTag attribute = (CompoundTag)tag;
               rewriteAttributeName(attribute, "AttributeName", false);
               rewriteAttributeName(attribute, "Name", false);
               Tag leastTag = attribute.get("UUIDLeast");
               if (leastTag != null) {
                  Tag mostTag = attribute.get("UUIDMost");
                  int[] uuidIntArray = UUIDIntArrayType.bitsToIntArray(((NumberTag)leastTag).asLong(), ((NumberTag)mostTag).asLong());
                  attribute.put("UUID", new IntArrayTag(uuidIntArray));
               }
            }

         }
      }
   }

   public static void newToOldAttributes(Item item) {
      if (item.tag() != null) {
         ListTag attributes = (ListTag)item.tag().get("AttributeModifiers");
         if (attributes != null) {
            Iterator var2 = attributes.iterator();

            while(var2.hasNext()) {
               Tag tag = (Tag)var2.next();
               CompoundTag attribute = (CompoundTag)tag;
               rewriteAttributeName(attribute, "AttributeName", true);
               rewriteAttributeName(attribute, "Name", true);
               IntArrayTag uuidTag = (IntArrayTag)attribute.get("UUID");
               if (uuidTag != null && uuidTag.getValue().length == 4) {
                  UUID uuid = UUIDIntArrayType.uuidFromIntArray(uuidTag.getValue());
                  attribute.put("UUIDLeast", new LongTag(uuid.getLeastSignificantBits()));
                  attribute.put("UUIDMost", new LongTag(uuid.getMostSignificantBits()));
               }
            }

         }
      }
   }

   public static void rewriteAttributeName(CompoundTag compoundTag, String entryName, boolean inverse) {
      StringTag attributeNameTag = (StringTag)compoundTag.get(entryName);
      if (attributeNameTag != null) {
         String attributeName = attributeNameTag.getValue();
         if (inverse && !attributeName.startsWith("minecraft:")) {
            attributeName = "minecraft:" + attributeName;
         }

         String mappedAttribute = (String)(inverse ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse() : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get(attributeName);
         if (mappedAttribute != null) {
            attributeNameTag.setValue(mappedAttribute);
         }
      }
   }
}
