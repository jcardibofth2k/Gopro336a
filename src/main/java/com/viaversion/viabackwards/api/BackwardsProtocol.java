package com.viaversion.viabackwards.api;

import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class BackwardsProtocol extends AbstractProtocol {
   protected BackwardsProtocol() {
   }

   protected BackwardsProtocol(@Nullable Class oldClientboundPacketEnum, @Nullable Class clientboundPacketEnum, @Nullable Class oldServerboundPacketEnum, @Nullable Class serverboundPacketEnum) {
      super(oldClientboundPacketEnum, clientboundPacketEnum, oldServerboundPacketEnum, serverboundPacketEnum);
   }

   protected void executeAsyncAfterLoaded(Class protocolClass, Runnable runnable) {
      Via.getManager().getProtocolManager().addMappingLoaderFuture(this.getClass(), protocolClass, runnable);
   }

   public boolean hasMappingDataToLoad() {
      return false;
   }

   @Nullable
   public BackwardsMappings getMappingData() {
      return null;
   }

   @Nullable
   public TranslatableRewriter getTranslatableRewriter() {
      return null;
   }
}
