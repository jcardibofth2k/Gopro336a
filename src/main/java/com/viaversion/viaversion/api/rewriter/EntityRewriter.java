package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.List;

public interface EntityRewriter extends Rewriter {
   EntityType typeFromId(int var1);

   default EntityType objectTypeFromId(int type) {
      return this.typeFromId(type);
   }

   int newEntityId(int var1);

   void handleMetadata(int var1, List var2, UserConnection var3);

   default EntityTracker tracker(UserConnection connection) {
      return connection.getEntityTracker(this.protocol().getClass());
   }
}
