package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import io.netty.buffer.ByteBuf;

public class BungeeMovementTransmitter extends MovementTransmitterProvider {
   public Object getFlyingPacket() {
      return null;
   }

   public Object getGroundPacket() {
      return null;
   }

   public void sendPlayer(UserConnection userConnection) {
      if (userConnection.getProtocolInfo().getState() == State.PLAY) {
         PacketWrapper wrapper = PacketWrapper.create(3, null, userConnection);
         wrapper.write(Type.BOOLEAN, ((MovementTracker)userConnection.get(MovementTracker.class)).isGround());

         try {
            wrapper.scheduleSendToServer(Protocol1_9To1_8.class);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }
}
