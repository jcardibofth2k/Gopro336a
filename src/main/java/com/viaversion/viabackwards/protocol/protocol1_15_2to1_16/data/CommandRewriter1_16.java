package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16 extends CommandRewriter {
   public CommandRewriter1_16(Protocol protocol) {
      super(protocol);
   }

   @Nullable
   protected String handleArgumentType(String argumentType) {
      return argumentType.equals("minecraft:uuid") ? "minecraft:game_profile" : super.handleArgumentType(argumentType);
   }
}
