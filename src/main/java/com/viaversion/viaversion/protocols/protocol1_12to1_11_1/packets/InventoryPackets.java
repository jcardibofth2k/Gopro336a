package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.BedRewriter;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class InventoryPackets extends ItemRewriter {
   public InventoryPackets(Protocol1_12To1_11_1 protocol) {
      super(protocol);
   }

   public void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
      this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
      this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
      ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                     wrapper.passthrough(Type.INT);
                     int size = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);

                     for(int i = 0; i < size; ++i) {
                        BedRewriter.toClientItem((Item)wrapper.passthrough(Type.ITEM));
                        BedRewriter.toClientItem((Item)wrapper.passthrough(Type.ITEM));
                        boolean secondItem = (Boolean)wrapper.passthrough(Type.BOOLEAN);
                        if (secondItem) {
                           BedRewriter.toClientItem((Item)wrapper.passthrough(Type.ITEM));
                        }

                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                     }
                  }

               }
            });
         }
      });
      ((Protocol1_12To1_11_1)this.protocol).registerServerbound(ServerboundPackets1_12.CLICK_WINDOW, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Item item = (Item)wrapper.get(Type.ITEM, 0);
                  if (!Via.getConfig().is1_12QuickMoveActionFix()) {
                     BedRewriter.toServerItem(item);
                  } else {
                     byte button = (Byte)wrapper.get(Type.BYTE, 0);
                     int mode = (Integer)wrapper.get(Type.VAR_INT, 0);
                     if (mode == 1 && button == 0 && item == null) {
                        short windowId = (Short)wrapper.get(Type.UNSIGNED_BYTE, 0);
                        short slotId = (Short)wrapper.get(Type.SHORT, 0);
                        short actionId = (Short)wrapper.get(Type.SHORT, 1);
                        InventoryQuickMoveProvider provider = (InventoryQuickMoveProvider)Via.getManager().getProviders().get(InventoryQuickMoveProvider.class);
                        boolean succeed = provider.registerQuickMoveAction(windowId, slotId, actionId, wrapper.user());
                        if (succeed) {
                           wrapper.cancel();
                        }
                     } else {
                        BedRewriter.toServerItem(item);
                     }

                  }
               }
            });
         }
      });
      this.registerCreativeInvAction(ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, Type.ITEM);
   }

   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         BedRewriter.toServerItem(item);
         boolean newItem = item.identifier() >= 235 && item.identifier() <= 252;
         newItem |= item.identifier() == 453;
         if (newItem) {
            item.setIdentifier(1);
            item.setData((short)0);
         }

         return item;
      }
   }
}
