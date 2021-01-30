package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_14 extends CommandRewriter {
   public CommandRewriter1_14(Protocol protocol) {
      super(protocol);
   }

   @Nullable
   protected String handleArgumentType(String argumentType) {
      return argumentType.equals("minecraft:nbt") ? "minecraft:nbt_compound_tag" : super.handleArgumentType(argumentType);
   }
}
