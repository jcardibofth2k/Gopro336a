package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;

public class InventoryPackets1_13_2 {
   public static void register(Protocol1_13_1To1_13_2 protocol) {
      protocol.registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.FLAT_VAR_INT_ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  String channel = (String)wrapper.get(Type.STRING, 0);
                  if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
                     wrapper.passthrough(Type.INT);
                     int size = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);

                     for(int i = 0; i < size; ++i) {
                        wrapper.write(Type.FLAT_ITEM, (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                        wrapper.write(Type.FLAT_ITEM, (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                        boolean secondItem = (Boolean)wrapper.passthrough(Type.BOOLEAN);
                        if (secondItem) {
                           wrapper.write(Type.FLAT_ITEM, (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
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
      protocol.registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int recipesNo = (Integer)wrapper.passthrough(Type.VAR_INT);

                  for(int i = 0; i < recipesNo; ++i) {
                     wrapper.passthrough(Type.STRING);
                     String type = (String)wrapper.passthrough(Type.STRING);
                     int ingredientsNo;
                     int i1;
                     if (type.equals("crafting_shapeless")) {
                        wrapper.passthrough(Type.STRING);
                        ingredientsNo = (Integer)wrapper.passthrough(Type.VAR_INT);

                        for(i1 = 0; i1 < ingredientsNo; ++i1) {
                           wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, (Item[])wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
                        }

                        wrapper.write(Type.FLAT_ITEM, (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                     } else if (!type.equals("crafting_shaped")) {
                        if (type.equals("smelting")) {
                           wrapper.passthrough(Type.STRING);
                           wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, (Item[])wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
                           wrapper.write(Type.FLAT_ITEM, (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                           wrapper.passthrough(Type.FLOAT);
                           wrapper.passthrough(Type.VAR_INT);
                        }
                     } else {
                        ingredientsNo = (Integer)wrapper.passthrough(Type.VAR_INT) * (Integer)wrapper.passthrough(Type.VAR_INT);
                        wrapper.passthrough(Type.STRING);

                        for(i1 = 0; i1 < ingredientsNo; ++i1) {
                           wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, (Item[])wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
                        }

                        wrapper.write(Type.FLAT_ITEM, (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                     }
                  }

               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.VAR_INT);
            this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.SHORT);
            this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
         }
      });
   }
}
