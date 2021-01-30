package com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

public class Protocol1_14_3To1_14_2 extends AbstractProtocol {
   public Protocol1_14_3To1_14_2() {
      super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, (Class)null, (Class)null);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  wrapper.passthrough(Type.VAR_INT);
                  int size = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);

                  for(int i = 0; i < size; ++i) {
                     wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                     wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                     if ((Boolean)wrapper.passthrough(Type.BOOLEAN)) {
                        wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                     }

                     wrapper.passthrough(Type.BOOLEAN);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.FLOAT);
                  }

                  wrapper.passthrough(Type.VAR_INT);
                  wrapper.passthrough(Type.VAR_INT);
                  boolean regularVillager = (Boolean)wrapper.passthrough(Type.BOOLEAN);
                  wrapper.write(Type.BOOLEAN, regularVillager);
               }
            });
         }
      });
   }
}
