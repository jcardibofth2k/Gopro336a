package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PacketWrapperImpl implements PacketWrapper {
   private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
   private final ByteBuf inputBuffer;
   private final UserConnection userConnection;
   private boolean send = true;
   // $FF: renamed from: id int
   private int field_399 = -1;
   private final Deque readableObjects = new ArrayDeque();
   private final List packetValues = new ArrayList();

   public PacketWrapperImpl(int packetId, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
      this.field_399 = packetId;
      this.inputBuffer = inputBuffer;
      this.userConnection = userConnection;
   }

   public Object get(Type type, int index) throws Exception {
      int currentIndex = 0;
      Iterator var4 = this.packetValues.iterator();

      while(var4.hasNext()) {
         Pair packetValue = (Pair)var4.next();
         if (packetValue.getKey() == type) {
            if (currentIndex == index) {
               return packetValue.getValue();
            }

            ++currentIndex;
         }
      }

      Exception e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
      throw (new InformativeException(e)).set("Type", type.getTypeName()).set("Index", index).set("Packet ID", this.getId()).set("Data", this.packetValues);
   }

   // $FF: renamed from: is (com.viaversion.viaversion.api.type.Type, int) boolean
   public boolean method_27(Type type, int index) {
      int currentIndex = 0;
      Iterator var4 = this.packetValues.iterator();

      while(var4.hasNext()) {
         Pair packetValue = (Pair)var4.next();
         if (packetValue.getKey() == type) {
            if (currentIndex == index) {
               return true;
            }

            ++currentIndex;
         }
      }

      return false;
   }

   public boolean isReadable(Type type, int index) {
      int currentIndex = 0;
      Iterator var4 = this.readableObjects.iterator();

      while(var4.hasNext()) {
         Pair packetValue = (Pair)var4.next();
         if (((Type)packetValue.getKey()).getBaseClass() == type.getBaseClass()) {
            if (currentIndex == index) {
               return true;
            }

            ++currentIndex;
         }
      }

      return false;
   }

   public void set(Type type, int index, Object value) throws Exception {
      int currentIndex = 0;
      Iterator var5 = this.packetValues.iterator();

      while(var5.hasNext()) {
         Pair packetValue = (Pair)var5.next();
         if (packetValue.getKey() == type) {
            if (currentIndex == index) {
               packetValue.setValue(this.attemptTransform(type, value));
               return;
            }

            ++currentIndex;
         }
      }

      Exception e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
      throw (new InformativeException(e)).set("Type", type.getTypeName()).set("Index", index).set("Packet ID", this.getId());
   }

   public Object read(Type type) throws Exception {
      if (type == Type.NOTHING) {
         return null;
      } else if (this.readableObjects.isEmpty()) {
         Preconditions.checkNotNull(this.inputBuffer, "This packet does not have an input buffer.");

         try {
            return type.read(this.inputBuffer);
         } catch (Exception var5) {
            throw (new InformativeException(var5)).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Data", this.packetValues);
         }
      } else {
         Pair read = (Pair)this.readableObjects.poll();
         Type rtype = (Type)read.getKey();
         if (rtype == type || type.getBaseClass() == rtype.getBaseClass() && type.getOutputClass() == rtype.getOutputClass()) {
            return read.getValue();
         } else if (rtype == Type.NOTHING) {
            return this.read(type);
         } else {
            Exception e = new IOException("Unable to read type " + type.getTypeName() + ", found " + ((Type)read.getKey()).getTypeName());
            throw (new InformativeException(e)).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Data", this.packetValues);
         }
      }
   }

   public void write(Type type, Object value) {
      this.packetValues.add(new Pair(type, this.attemptTransform(type, value)));
   }

   @Nullable
   private Object attemptTransform(Type expectedType, @Nullable Object value) {
      if (value != null && !expectedType.getOutputClass().isAssignableFrom(value.getClass())) {
         if (expectedType instanceof TypeConverter) {
            return ((TypeConverter)expectedType).from(value);
         }

         Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + expectedType.getOutputClass());
      }

      return value;
   }

   public Object passthrough(Type type) throws Exception {
      Object value = this.read(type);
      this.write(type, value);
      return value;
   }

   public void passthroughAll() throws Exception {
      this.packetValues.addAll(this.readableObjects);
      this.readableObjects.clear();
      if (this.inputBuffer.isReadable()) {
         this.passthrough(Type.REMAINING_BYTES);
      }

   }

   public void writeToBuffer(ByteBuf buffer) throws Exception {
      if (this.field_399 != -1) {
         Type.VAR_INT.writePrimitive(buffer, this.field_399);
      }

      if (!this.readableObjects.isEmpty()) {
         this.packetValues.addAll(this.readableObjects);
         this.readableObjects.clear();
      }

      int index = 0;

      for(Iterator var3 = this.packetValues.iterator(); var3.hasNext(); ++index) {
         Pair packetValue = (Pair)var3.next();

         try {
            ((Type)packetValue.getKey()).write(buffer, packetValue.getValue());
         } catch (Exception var6) {
            throw (new InformativeException(var6)).set("Index", index).set("Type", ((Type)packetValue.getKey()).getTypeName()).set("Packet ID", this.getId()).set("Data", this.packetValues);
         }
      }

      this.writeRemaining(buffer);
   }

   public void clearInputBuffer() {
      if (this.inputBuffer != null) {
         this.inputBuffer.clear();
      }

      this.readableObjects.clear();
   }

   public void clearPacket() {
      this.clearInputBuffer();
      this.packetValues.clear();
   }

   private void writeRemaining(ByteBuf output) {
      if (this.inputBuffer != null) {
         output.writeBytes(this.inputBuffer);
      }

   }

   public void send(Class protocol, boolean skipCurrentPipeline) throws Exception {
      this.send0(protocol, skipCurrentPipeline, true);
   }

   public void scheduleSend(Class protocol, boolean skipCurrentPipeline) throws Exception {
      this.send0(protocol, skipCurrentPipeline, false);
   }

   private void send0(Class protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
      if (!this.isCancelled()) {
         try {
            ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
            if (currentThread) {
               this.user().sendRawPacket(output);
            } else {
               this.user().scheduleSendRawPacket(output);
            }
         } catch (Exception var5) {
            if (!PipelineUtil.containsCause(var5, CancelException.class)) {
               throw var5;
            }
         }

      }
   }

   private ByteBuf constructPacket(Class packetProtocol, boolean skipCurrentPipeline, Direction direction) throws Exception {
      Protocol[] protocols = (Protocol[])this.user().getProtocolInfo().getPipeline().pipes().toArray(PROTOCOL_ARRAY);
      boolean reverse = direction == Direction.CLIENTBOUND;
      int index = -1;

      for(int i = 0; i < protocols.length; ++i) {
         if (protocols[i].getClass() == packetProtocol) {
            index = i;
            break;
         }
      }

      if (index == -1) {
         throw new NoSuchElementException(packetProtocol.getCanonicalName());
      } else {
         if (skipCurrentPipeline) {
            index = reverse ? index - 1 : index + 1;
         }

         this.resetReader();
         this.apply(direction, this.user().getProtocolInfo().getState(), index, protocols, reverse);
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         ByteBuf var8;
         try {
            this.writeToBuffer(output);
            var8 = output.retain();
         } finally {
            output.release();
         }

         return var8;
      }
   }

   public ChannelFuture sendFuture(Class packetProtocol) throws Exception {
      if (!this.isCancelled()) {
         ByteBuf output = this.constructPacket(packetProtocol, true, Direction.CLIENTBOUND);
         return this.user().sendRawPacketFuture(output);
      } else {
         return this.user().getChannel().newFailedFuture(new Exception("Cancelled packet"));
      }
   }

   /** @deprecated */
   @Deprecated
   public void send() throws Exception {
      if (!this.isCancelled()) {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         try {
            this.writeToBuffer(output);
            this.user().sendRawPacket(output.retain());
         } finally {
            output.release();
         }

      }
   }

   public PacketWrapperImpl create(int packetId) {
      return new PacketWrapperImpl(packetId, (ByteBuf)null, this.user());
   }

   public PacketWrapperImpl create(int packetId, PacketHandler handler) throws Exception {
      PacketWrapperImpl wrapper = this.create(packetId);
      handler.handle(wrapper);
      return wrapper;
   }

   public PacketWrapperImpl apply(Direction direction, State state, int index, List pipeline, boolean reverse) throws Exception {
      Protocol[] array = (Protocol[])pipeline.toArray(PROTOCOL_ARRAY);
      return this.apply(direction, state, reverse ? array.length - 1 : index, array, reverse);
   }

   public PacketWrapperImpl apply(Direction direction, State state, int index, List pipeline) throws Exception {
      return this.apply(direction, state, index, (Protocol[])pipeline.toArray(PROTOCOL_ARRAY), false);
   }

   private PacketWrapperImpl apply(Direction direction, State state, int index, Protocol[] pipeline, boolean reverse) throws Exception {
      int i;
      if (reverse) {
         for(i = index; i >= 0; --i) {
            pipeline[i].transform(direction, state, this);
            this.resetReader();
         }
      } else {
         for(i = index; i < pipeline.length; ++i) {
            pipeline[i].transform(direction, state, this);
            this.resetReader();
         }
      }

      return this;
   }

   public void cancel() {
      this.send = false;
   }

   public boolean isCancelled() {
      return !this.send;
   }

   public UserConnection user() {
      return this.userConnection;
   }

   public void resetReader() {
      for(int i = this.packetValues.size() - 1; i >= 0; --i) {
         this.readableObjects.addFirst((Pair)this.packetValues.get(i));
      }

      this.packetValues.clear();
   }

   /** @deprecated */
   @Deprecated
   public void sendToServer() throws Exception {
      if (!this.isCancelled()) {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         try {
            this.writeToBuffer(output);
            this.user().sendRawPacketToServer(output.retain());
         } finally {
            output.release();
         }

      }
   }

   public void sendToServer(Class protocol, boolean skipCurrentPipeline) throws Exception {
      this.sendToServer0(protocol, skipCurrentPipeline, true);
   }

   public void scheduleSendToServer(Class protocol, boolean skipCurrentPipeline) throws Exception {
      this.sendToServer0(protocol, skipCurrentPipeline, false);
   }

   private void sendToServer0(Class protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
      if (!this.isCancelled()) {
         try {
            ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
            if (currentThread) {
               this.user().sendRawPacketToServer(output);
            } else {
               this.user().scheduleSendRawPacketToServer(output);
            }
         } catch (Exception var5) {
            if (!PipelineUtil.containsCause(var5, CancelException.class)) {
               throw var5;
            }
         }

      }
   }

   public int getId() {
      return this.field_399;
   }

   public void setId(int id) {
      this.field_399 = id;
   }

   @Nullable
   public ByteBuf getInputBuffer() {
      return this.inputBuffer;
   }

   public String toString() {
      return "PacketWrapper{packetValues=" + this.packetValues + ", readableObjects=" + this.readableObjects + ", id=" + this.field_399 + '}';
   }
}
