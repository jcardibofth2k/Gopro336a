package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;

public abstract class RewriterBase implements Rewriter {
   protected final Protocol protocol;

   protected RewriterBase(Protocol protocol) {
      this.protocol = protocol;
   }

   public final void register() {
      this.registerPackets();
      this.registerRewrites();
   }

   protected void registerPackets() {
   }

   protected void registerRewrites() {
   }

   public Protocol protocol() {
      return this.protocol;
   }
}
