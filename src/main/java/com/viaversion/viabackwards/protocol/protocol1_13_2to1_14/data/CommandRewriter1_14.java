package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_14 extends CommandRewriter {
   public CommandRewriter1_14(Protocol protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:nbt_tag", (wrapper) -> {
         wrapper.write(Type.VAR_INT, 2);
      });
      this.parserHandlers.put("minecraft:time", (wrapper) -> {
         wrapper.write(Type.BYTE, (byte)1);
         wrapper.write(Type.INT, 0);
      });
   }

   @Nullable
   protected String handleArgumentType(String argumentType) {
      byte var3 = -1;
      switch(argumentType.hashCode()) {
      case -1006391174:
         if (argumentType.equals("minecraft:time")) {
            var3 = 2;
         }
         break;
      case -783223342:
         if (argumentType.equals("minecraft:nbt_compound_tag")) {
            var3 = 0;
         }
         break;
      case 543170382:
         if (argumentType.equals("minecraft:nbt_tag")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         return "minecraft:nbt";
      case 1:
         return "brigadier:string";
      case 2:
         return "brigadier:integer";
      default:
         return super.handleArgumentType(argumentType);
      }
   }
}
