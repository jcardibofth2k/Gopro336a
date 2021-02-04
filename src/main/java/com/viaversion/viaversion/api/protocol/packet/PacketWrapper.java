package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketWrapper {
   int PASSTHROUGH_ID = 1000;

   static PacketWrapper create(PacketType packetType, UserConnection connection) {
      return create(packetType.getId(), null, connection);
   }

   static PacketWrapper create(PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection connection) {
      return create(packetType.getId(), inputBuffer, connection);
   }

   static PacketWrapper create(int packetId, @Nullable ByteBuf inputBuffer, UserConnection connection) {
      return Via.getManager().getProtocolManager().createPacketWrapper(packetId, inputBuffer, connection);
   }

   Object get(Type var1, int var2) throws Exception;

   // $FF: renamed from: is (com.viaversion.viaversion.api.type.Type, int) boolean
   boolean method_27(Type var1, int var2);

   boolean isReadable(Type var1, int var2);

   void set(Type var1, int var2, Object var3) throws Exception;

   Object read(Type var1) throws Exception;

   void write(Type var1, Object var2);

   Object passthrough(Type var1) throws Exception;

   void passthroughAll() throws Exception;

   void writeToBuffer(ByteBuf var1) throws Exception;

   void clearInputBuffer();

   void clearPacket();

   default void send(Class protocol) throws Exception {
      this.send(protocol, true);
   }

   void send(Class var1, boolean var2) throws Exception;

   default void scheduleSend(Class protocol) throws Exception {
      this.scheduleSend(protocol, true);
   }

   void scheduleSend(Class var1, boolean var2) throws Exception;

   ChannelFuture sendFuture(Class var1) throws Exception;

   /** @deprecated */
   @Deprecated
   void send() throws Exception;

   default PacketWrapper create(PacketType packetType) {
      return this.create(packetType.getId());
   }

   default PacketWrapper create(PacketType packetType, PacketHandler handler) throws Exception {
      return this.create(packetType.getId(), handler);
   }

   PacketWrapper create(int var1);

   PacketWrapper create(int var1, PacketHandler var2) throws Exception;

   PacketWrapper apply(Direction var1, State var2, int var3, List var4, boolean var5) throws Exception;

   PacketWrapper apply(Direction var1, State var2, int var3, List var4) throws Exception;

   void cancel();

   boolean isCancelled();

   UserConnection user();

   void resetReader();

   /** @deprecated */
   @Deprecated
   void sendToServer() throws Exception;

   default void sendToServer(Class protocol) throws Exception {
      this.sendToServer(protocol, true);
   }

   void sendToServer(Class var1, boolean var2) throws Exception;

   default void scheduleSendToServer(Class protocol) throws Exception {
      this.scheduleSendToServer(protocol, true);
   }

   void scheduleSendToServer(Class var1, boolean var2) throws Exception;

   int getId();

   default void setId(PacketType packetType) {
      this.setId(packetType.getId());
   }

   void setId(int var1);
}
