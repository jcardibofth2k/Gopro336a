package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.Set;

public class ClientChunks extends StoredObject {
   private final Set loadedChunks = Sets.newConcurrentHashSet();
   private final Set bulkChunks = Sets.newConcurrentHashSet();

   public ClientChunks(UserConnection connection) {
      super(connection);
   }

   public static long toLong(int msw, int lsw) {
      return ((long)msw << 32) + (long)lsw - -2147483648L;
   }

   public Set getLoadedChunks() {
      return this.loadedChunks;
   }

   public Set getBulkChunks() {
      return this.bulkChunks;
   }
}
