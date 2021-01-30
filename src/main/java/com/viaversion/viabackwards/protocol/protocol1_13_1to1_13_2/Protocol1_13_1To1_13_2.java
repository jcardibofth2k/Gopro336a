package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.EntityPackets1_13_2;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.InventoryPackets1_13_2;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.WorldPackets1_13_2;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;

public class Protocol1_13_1To1_13_2 extends BackwardsProtocol {
   public Protocol1_13_1To1_13_2() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
   }

   protected void registerPackets() {
      InventoryPackets1_13_2.register(this);
      WorldPackets1_13_2.register(this);
      EntityPackets1_13_2.register(this);
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
         }
      });
      this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  wrapper.passthrough(Type.BOOLEAN);
                  int size = (Integer)wrapper.passthrough(Type.VAR_INT);

                  for(int i = 0; i < size; ++i) {
                     wrapper.passthrough(Type.STRING);
                     if ((Boolean)wrapper.passthrough(Type.BOOLEAN)) {
                        wrapper.passthrough(Type.STRING);
                     }

                     int array;
                     if ((Boolean)wrapper.passthrough(Type.BOOLEAN)) {
                        wrapper.passthrough(Type.COMPONENT);
                        wrapper.passthrough(Type.COMPONENT);
                        Item icon = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
                        wrapper.write(Type.FLAT_ITEM, icon);
                        wrapper.passthrough(Type.VAR_INT);
                        array = (Integer)wrapper.passthrough(Type.INT);
                        if ((array & 1) != 0) {
                           wrapper.passthrough(Type.STRING);
                        }

                        wrapper.passthrough(Type.FLOAT);
                        wrapper.passthrough(Type.FLOAT);
                     }

                     wrapper.passthrough(Type.STRING_ARRAY);
                     int arrayLength = (Integer)wrapper.passthrough(Type.VAR_INT);

                     for(array = 0; array < arrayLength; ++array) {
                        wrapper.passthrough(Type.STRING_ARRAY);
                     }
                  }

               }
            });
         }
      });
   }
}
