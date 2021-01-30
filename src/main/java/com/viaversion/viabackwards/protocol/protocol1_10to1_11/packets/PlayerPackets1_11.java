package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class PlayerPackets1_11 {
   private static final ValueTransformer TO_NEW_FLOAT;

   public void register(Protocol1_10To1_11 protocol) {
      protocol.registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Type.VAR_INT, 0);
               if (action == 2) {
                  JsonElement messagex = (JsonElement)wrapper.read(Type.COMPONENT);
                  wrapper.clearPacket();
                  wrapper.setId(ClientboundPackets1_9_3.CHAT_MESSAGE.ordinal());
                  String legacy = LegacyComponentSerializer.legacySection().serialize(GsonComponentSerializer.gson().deserialize(messagex.toString()));
                  JsonElement message = new JsonObject();
                  message.getAsJsonObject().addProperty("text", legacy);
                  wrapper.write(Type.COMPONENT, message);
                  wrapper.write(Type.BYTE, (byte)2);
               } else if (action > 2) {
                  wrapper.set(Type.VAR_INT, 0, action - 1);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               wrapper.read(Type.VAR_INT);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
            this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
            this.map(Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
         }
      });
   }

   static {
      TO_NEW_FLOAT = new ValueTransformer(Type.FLOAT) {
         public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return (float)inputValue / 15.0F;
         }
      };
   }
}
