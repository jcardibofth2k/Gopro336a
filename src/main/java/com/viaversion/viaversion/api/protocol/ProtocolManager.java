package com.viaversion.viaversion.api.protocol;

import com.google.common.collect.Range;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolManager {
   ServerProtocolVersion getServerProtocolVersion();

   @Nullable
   Protocol getProtocol(Class var1);

   @Nullable
   default Protocol getProtocol(ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      return this.getProtocol(clientVersion.getVersion(), serverVersion.getVersion());
   }

   @Nullable
   Protocol getProtocol(int var1, int var2);

   Protocol getBaseProtocol();

   Protocol getBaseProtocol(int var1);

   default boolean isBaseProtocol(Protocol protocol) {
      return protocol.isBaseProtocol();
   }

   void registerProtocol(Protocol var1, ProtocolVersion var2, ProtocolVersion var3);

   void registerProtocol(Protocol var1, List var2, int var3);

   void registerBaseProtocol(Protocol var1, Range var2);

   @Nullable
   List getProtocolPath(int var1, int var2);

   boolean onlyCheckLoweringPathEntries();

   void setOnlyCheckLoweringPathEntries(boolean var1);

   int getMaxProtocolPathSize();

   void setMaxProtocolPathSize(int var1);

   SortedSet getSupportedVersions();

   boolean isWorkingPipe();

   void completeMappingDataLoading(Class var1) throws Exception;

   boolean checkForMappingCompletion();

   void addMappingLoaderFuture(Class var1, Runnable var2);

   void addMappingLoaderFuture(Class var1, Class var2, Runnable var3);

   @Nullable
   CompletableFuture getMappingLoaderFuture(Class var1);

   PacketWrapper createPacketWrapper(int var1, @Nullable ByteBuf var2, UserConnection var3);
}
