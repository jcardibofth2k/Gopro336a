package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Protocol {
   default void registerServerbound(State state, int oldPacketID, int newPacketID) {
      this.registerServerbound(state, oldPacketID, newPacketID, (PacketRemapper)null);
   }

   default void registerServerbound(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper) {
      this.registerServerbound(state, oldPacketID, newPacketID, packetRemapper, false);
   }

   void registerServerbound(State var1, int var2, int var3, PacketRemapper var4, boolean var5);

   void cancelServerbound(State var1, int var2, int var3);

   default void cancelServerbound(State state, int newPacketID) {
      this.cancelServerbound(state, -1, newPacketID);
   }

   default void registerClientbound(State state, int oldPacketID, int newPacketID) {
      this.registerClientbound(state, oldPacketID, newPacketID, (PacketRemapper)null);
   }

   default void registerClientbound(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper) {
      this.registerClientbound(state, oldPacketID, newPacketID, packetRemapper, false);
   }

   void cancelClientbound(State var1, int var2, int var3);

   default void cancelClientbound(State state, int oldPacketID) {
      this.cancelClientbound(state, oldPacketID, -1);
   }

   void registerClientbound(State var1, int var2, int var3, PacketRemapper var4, boolean var5);

   void registerClientbound(ClientboundPacketType var1, @Nullable PacketRemapper var2);

   void registerClientbound(ClientboundPacketType var1, ClientboundPacketType var2, @Nullable PacketRemapper var3);

   default void registerClientbound(ClientboundPacketType packetType, @Nullable ClientboundPacketType mappedPacketType) {
      this.registerClientbound(packetType, mappedPacketType, (PacketRemapper)null);
   }

   void cancelClientbound(ClientboundPacketType var1);

   void registerServerbound(ServerboundPacketType var1, @Nullable PacketRemapper var2);

   void registerServerbound(ServerboundPacketType var1, @Nullable ServerboundPacketType var2, @Nullable PacketRemapper var3);

   void cancelServerbound(ServerboundPacketType var1);

   boolean hasRegisteredClientbound(State var1, int var2);

   boolean hasRegisteredServerbound(State var1, int var2);

   void transform(Direction var1, State var2, PacketWrapper var3) throws Exception;

   @Nullable
   Object get(Class var1);

   void put(Object var1);

   void initialize();

   boolean hasMappingDataToLoad();

   void loadMappingData();

   default void register(ViaProviders providers) {
   }

   default void init(UserConnection userConnection) {
   }

   @Nullable
   default MappingData getMappingData() {
      return null;
   }

   @Nullable
   default EntityRewriter getEntityRewriter() {
      return null;
   }

   @Nullable
   default ItemRewriter getItemRewriter() {
      return null;
   }

   default boolean isBaseProtocol() {
      return false;
   }
}
