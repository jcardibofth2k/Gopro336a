package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16_2 extends CommandRewriter {
   public CommandRewriter1_16_2(Protocol protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:angle", (wrapper) -> {
         wrapper.write(Type.VAR_INT, 0);
      });
   }

   @Nullable
   protected String handleArgumentType(String argumentType) {
      return argumentType.equals("minecraft:angle") ? "brigadier:string" : super.handleArgumentType(argumentType);
   }
}
