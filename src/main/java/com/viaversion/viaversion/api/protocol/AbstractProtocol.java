package com.viaversion.viaversion.api.protocol;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractProtocol implements Protocol {
   private final Map serverbound;
   private final Map clientbound;
   private final Map storedObjects;
   protected final Class oldClientboundPacketEnum;
   protected final Class newClientboundPacketEnum;
   protected final Class oldServerboundPacketEnum;
   protected final Class newServerboundPacketEnum;
   private boolean initialized;

   protected AbstractProtocol() {
      this(null, null, null, null);
   }

   protected AbstractProtocol(@Nullable Class oldClientboundPacketEnum, @Nullable Class clientboundPacketEnum, @Nullable Class oldServerboundPacketEnum, @Nullable Class serverboundPacketEnum) {
      this.serverbound = new HashMap();
      this.clientbound = new HashMap();
      this.storedObjects = new HashMap();
      this.oldClientboundPacketEnum = oldClientboundPacketEnum;
      this.newClientboundPacketEnum = clientboundPacketEnum;
      this.oldServerboundPacketEnum = oldServerboundPacketEnum;
      this.newServerboundPacketEnum = serverboundPacketEnum;
   }

   public final void initialize() {
      Preconditions.checkArgument(!this.initialized);
      this.initialized = true;
      this.registerPackets();
      if (this.oldClientboundPacketEnum != null && this.newClientboundPacketEnum != null && this.oldClientboundPacketEnum != this.newClientboundPacketEnum) {
         this.registerClientboundChannelIdChanges();
      }

      if (this.oldServerboundPacketEnum != null && this.newServerboundPacketEnum != null && this.oldServerboundPacketEnum != this.newServerboundPacketEnum) {
         this.registerServerboundChannelIdChanges();
      }

   }

   protected void registerClientboundChannelIdChanges() {
      ClientboundPacketType[] newConstants = (ClientboundPacketType[])this.newClientboundPacketEnum.getEnumConstants();
      Map newClientboundPackets = new HashMap(newConstants.length);
      ClientboundPacketType[] var3 = newConstants;
      int var4 = newConstants.length;

      int var5;
      ClientboundPacketType packet;
      for(var5 = 0; var5 < var4; ++var5) {
         packet = var3[var5];
         newClientboundPackets.put(packet.getName(), packet);
      }

      var3 = (ClientboundPacketType[])this.oldClientboundPacketEnum.getEnumConstants();
      var4 = var3.length;

      for(var5 = 0; var5 < var4; ++var5) {
         packet = var3[var5];
         ClientboundPacketType mappedPacket = (ClientboundPacketType)newClientboundPackets.get(packet.getName());
         int oldId = packet.getId();
         if (mappedPacket == null) {
            Preconditions.checkArgument(this.hasRegisteredClientbound(State.PLAY, oldId), "Packet " + packet + " in " + this.getClass().getSimpleName() + " has no mapping - it needs to be manually cancelled or remapped!");
         } else {
            int newId = mappedPacket.getId();
            if (!this.hasRegisteredClientbound(State.PLAY, oldId)) {
               this.registerClientbound(State.PLAY, oldId, newId);
            }
         }
      }

   }

   protected void registerServerboundChannelIdChanges() {
      ServerboundPacketType[] oldConstants = (ServerboundPacketType[])this.oldServerboundPacketEnum.getEnumConstants();
      Map oldServerboundConstants = new HashMap(oldConstants.length);
      ServerboundPacketType[] var3 = oldConstants;
      int var4 = oldConstants.length;

      int var5;
      ServerboundPacketType packet;
      for(var5 = 0; var5 < var4; ++var5) {
         packet = var3[var5];
         oldServerboundConstants.put(packet.getName(), packet);
      }

      var3 = (ServerboundPacketType[])this.newServerboundPacketEnum.getEnumConstants();
      var4 = var3.length;

      for(var5 = 0; var5 < var4; ++var5) {
         packet = var3[var5];
         ServerboundPacketType mappedPacket = (ServerboundPacketType)oldServerboundConstants.get(packet.getName());
         int newId = packet.getId();
         if (mappedPacket == null) {
            Preconditions.checkArgument(this.hasRegisteredServerbound(State.PLAY, newId), "Packet " + packet + " in " + this.getClass().getSimpleName() + " has no mapping - it needs to be manually cancelled or remapped!");
         } else {
            int oldId = mappedPacket.getId();
            if (!this.hasRegisteredServerbound(State.PLAY, newId)) {
               this.registerServerbound(State.PLAY, oldId, newId);
            }
         }
      }

   }

   protected void registerPackets() {
   }

   public final void loadMappingData() {
      this.getMappingData().load();
      this.onMappingDataLoaded();
   }

   protected void onMappingDataLoaded() {
   }

   protected void addEntityTracker(UserConnection connection, EntityTracker tracker) {
      connection.addEntityTracker(this.getClass(), tracker);
   }

   public void registerServerbound(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper, boolean override) {
      AbstractProtocol.ProtocolPacket protocolPacket = new AbstractProtocol.ProtocolPacket(state, oldPacketID, newPacketID, packetRemapper);
      AbstractProtocol.Packet packet = new AbstractProtocol.Packet(state, newPacketID);
      if (!override && this.serverbound.containsKey(packet)) {
         Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      this.serverbound.put(packet, protocolPacket);
   }

   public void cancelServerbound(State state, int oldPacketID, int newPacketID) {
      this.registerServerbound(state, oldPacketID, newPacketID, new PacketRemapper() {
         public void registerMap() {
            this.handler(PacketWrapper::cancel);
         }
      });
   }

   public void cancelClientbound(State state, int oldPacketID, int newPacketID) {
      this.registerClientbound(state, oldPacketID, newPacketID, new PacketRemapper() {
         public void registerMap() {
            this.handler(PacketWrapper::cancel);
         }
      });
   }

   public void registerClientbound(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper, boolean override) {
      AbstractProtocol.ProtocolPacket protocolPacket = new AbstractProtocol.ProtocolPacket(state, oldPacketID, newPacketID, packetRemapper);
      AbstractProtocol.Packet packet = new AbstractProtocol.Packet(state, oldPacketID);
      if (!override && this.clientbound.containsKey(packet)) {
         Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      this.clientbound.put(packet, protocolPacket);
   }

   public void registerClientbound(ClientboundPacketType packetType, @Nullable PacketRemapper packetRemapper) {
      this.checkPacketType(packetType, packetType.getClass() == this.oldClientboundPacketEnum);
      ClientboundPacketType mappedPacket = this.oldClientboundPacketEnum == this.newClientboundPacketEnum ? packetType : Arrays.stream((ClientboundPacketType[])this.newClientboundPacketEnum.getEnumConstants()).filter((en) -> {
         return en.getName().equals(packetType.getName());
      }).findAny().orElse(null);
      Preconditions.checkNotNull(mappedPacket, "Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " could not be automatically mapped!");
      int oldId = packetType.getId();
      int newId = mappedPacket.getId();
      this.registerClientbound(State.PLAY, oldId, newId, packetRemapper);
   }

   public void registerClientbound(ClientboundPacketType packetType, @Nullable ClientboundPacketType mappedPacketType, @Nullable PacketRemapper packetRemapper) {
      this.checkPacketType(packetType, packetType.getClass() == this.oldClientboundPacketEnum);
      this.checkPacketType(mappedPacketType, mappedPacketType == null || mappedPacketType.getClass() == this.newClientboundPacketEnum);
      this.registerClientbound(State.PLAY, packetType.getId(), mappedPacketType != null ? mappedPacketType.getId() : -1, packetRemapper);
   }

   public void cancelClientbound(ClientboundPacketType packetType) {
      this.cancelClientbound(State.PLAY, packetType.getId(), packetType.getId());
   }

   public void registerServerbound(ServerboundPacketType packetType, @Nullable PacketRemapper packetRemapper) {
      this.checkPacketType(packetType, packetType.getClass() == this.newServerboundPacketEnum);
      ServerboundPacketType mappedPacket = this.oldServerboundPacketEnum == this.newServerboundPacketEnum ? packetType : Arrays.stream((ServerboundPacketType[])this.oldServerboundPacketEnum.getEnumConstants()).filter((en) -> {
         return en.getName().equals(packetType.getName());
      }).findAny().orElse(null);
      Preconditions.checkNotNull(mappedPacket, "Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " could not be automatically mapped!");
      int oldId = mappedPacket.getId();
      int newId = packetType.getId();
      this.registerServerbound(State.PLAY, oldId, newId, packetRemapper);
   }

   public void registerServerbound(ServerboundPacketType packetType, @Nullable ServerboundPacketType mappedPacketType, @Nullable PacketRemapper packetRemapper) {
      this.checkPacketType(packetType, packetType.getClass() == this.newServerboundPacketEnum);
      this.checkPacketType(mappedPacketType, mappedPacketType == null || mappedPacketType.getClass() == this.oldServerboundPacketEnum);
      this.registerServerbound(State.PLAY, mappedPacketType != null ? mappedPacketType.getId() : -1, packetType.getId(), packetRemapper);
   }

   public void cancelServerbound(ServerboundPacketType packetType) {
      Preconditions.checkArgument(packetType.getClass() == this.newServerboundPacketEnum);
      this.cancelServerbound(State.PLAY, -1, packetType.getId());
   }

   public boolean hasRegisteredClientbound(State state, int oldPacketID) {
      AbstractProtocol.Packet packet = new AbstractProtocol.Packet(state, oldPacketID);
      return this.clientbound.containsKey(packet);
   }

   public boolean hasRegisteredServerbound(State state, int newPacketId) {
      AbstractProtocol.Packet packet = new AbstractProtocol.Packet(state, newPacketId);
      return this.serverbound.containsKey(packet);
   }

   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
      AbstractProtocol.Packet statePacket = new AbstractProtocol.Packet(state, packetWrapper.getId());
      Map packetMap = direction == Direction.CLIENTBOUND ? this.clientbound : this.serverbound;
      AbstractProtocol.ProtocolPacket protocolPacket = (AbstractProtocol.ProtocolPacket)packetMap.get(statePacket);
      if (protocolPacket != null) {
         int oldId = packetWrapper.getId();
         int newId = direction == Direction.CLIENTBOUND ? protocolPacket.getNewID() : protocolPacket.getOldID();
         packetWrapper.setId(newId);
         PacketRemapper remapper = protocolPacket.getRemapper();
         if (remapper != null) {
            try {
               remapper.remap(packetWrapper);
            } catch (InformativeException var11) {
               this.throwRemapError(direction, state, oldId, newId, var11);
               return;
            }

            if (packetWrapper.isCancelled()) {
               throw CancelException.generate();
            }
         }

      }
   }

   private void throwRemapError(Direction direction, State state, int oldId, int newId, InformativeException e) throws InformativeException {
      if (state == State.HANDSHAKE) {
         throw e;
      } else {
         Class packetTypeClass = state == State.PLAY ? (direction == Direction.CLIENTBOUND ? this.oldClientboundPacketEnum : this.newServerboundPacketEnum) : null;
         if (packetTypeClass != null) {
            PacketType[] enumConstants = (PacketType[])packetTypeClass.getEnumConstants();
            PacketType packetType = oldId < enumConstants.length && oldId >= 0 ? enumConstants[oldId] : null;
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + packetType + " (" + this.toNiceHex(oldId) + ")");
         } else {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + this.toNiceHex(oldId) + "->" + this.toNiceHex(newId));
         }

         throw e;
      }
   }

   private String toNiceHex(int id) {
      String hex = Integer.toHexString(id).toUpperCase();
      return (hex.length() == 1 ? "0x0" : "0x") + hex;
   }

   private void checkPacketType(PacketType packetType, boolean isValid) {
      if (!isValid) {
         throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " is taken from the wrong enum");
      }
   }

   @Nullable
   public Object get(Class objectClass) {
      return this.storedObjects.get(objectClass);
   }

   public void put(Object object) {
      this.storedObjects.put(object.getClass(), object);
   }

   public boolean hasMappingDataToLoad() {
      return this.getMappingData() != null;
   }

   public String toString() {
      return "Protocol:" + this.getClass().getSimpleName();
   }

   public static final class ProtocolPacket {
      private final State state;
      private final int oldID;
      private final int newID;
      private final PacketRemapper remapper;

      public ProtocolPacket(State state, int oldID, int newID, @Nullable PacketRemapper remapper) {
         this.state = state;
         this.oldID = oldID;
         this.newID = newID;
         this.remapper = remapper;
      }

      public State getState() {
         return this.state;
      }

      public int getOldID() {
         return this.oldID;
      }

      public int getNewID() {
         return this.newID;
      }

      @Nullable
      public PacketRemapper getRemapper() {
         return this.remapper;
      }
   }

   public static final class Packet {
      private final State state;
      private final int packetId;

      public Packet(State state, int packetId) {
         this.state = state;
         this.packetId = packetId;
      }

      public State getState() {
         return this.state;
      }

      public int getPacketId() {
         return this.packetId;
      }

      public String toString() {
         return "Packet{state=" + this.state + ", packetId=" + this.packetId + '}';
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            AbstractProtocol.Packet that = (AbstractProtocol.Packet)o;
            return this.packetId == that.packetId && this.state == that.state;
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.state != null ? this.state.hashCode() : 0;
         result = 31 * result + this.packetId;
         return result;
      }
   }
}
