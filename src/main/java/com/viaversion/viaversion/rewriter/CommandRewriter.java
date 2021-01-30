package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class CommandRewriter {
   protected final Protocol protocol;
   protected final Map parserHandlers = new HashMap();

   protected CommandRewriter(Protocol protocol) {
      this.protocol = protocol;
      this.parserHandlers.put("brigadier:double", (wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.DOUBLE);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.DOUBLE);
         }

      });
      this.parserHandlers.put("brigadier:float", (wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.FLOAT);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.FLOAT);
         }

      });
      this.parserHandlers.put("brigadier:integer", (wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.INT);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.INT);
         }

      });
      this.parserHandlers.put("brigadier:long", (wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.LONG);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.LONG);
         }

      });
      this.parserHandlers.put("brigadier:string", (wrapper) -> {
         wrapper.passthrough(Type.VAR_INT);
      });
      this.parserHandlers.put("minecraft:entity", (wrapper) -> {
         wrapper.passthrough(Type.BYTE);
      });
      this.parserHandlers.put("minecraft:score_holder", (wrapper) -> {
         wrapper.passthrough(Type.BYTE);
      });
   }

   public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
      CommandRewriter.CommandArgumentConsumer handler = (CommandRewriter.CommandArgumentConsumer)this.parserHandlers.get(argumentType);
      if (handler != null) {
         handler.accept(wrapper);
      }

   }

   public void registerDeclareCommands(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int size = (Integer)wrapper.passthrough(Type.VAR_INT);

               for(int i = 0; i < size; ++i) {
                  byte flags = (Byte)wrapper.passthrough(Type.BYTE);
                  wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                  if ((flags & 8) != 0) {
                     wrapper.passthrough(Type.VAR_INT);
                  }

                  byte nodeType = (byte)(flags & 3);
                  if (nodeType == 1 || nodeType == 2) {
                     wrapper.passthrough(Type.STRING);
                  }

                  if (nodeType == 2) {
                     String argumentType = (String)wrapper.read(Type.STRING);
                     String newArgumentType = CommandRewriter.this.handleArgumentType(argumentType);
                     if (newArgumentType != null) {
                        wrapper.write(Type.STRING, newArgumentType);
                     }

                     CommandRewriter.this.handleArgument(wrapper, argumentType);
                  }

                  if ((flags & 16) != 0) {
                     wrapper.passthrough(Type.STRING);
                  }
               }

               wrapper.passthrough(Type.VAR_INT);
            });
         }
      });
   }

   @Nullable
   protected String handleArgumentType(String argumentType) {
      return argumentType;
   }

   @FunctionalInterface
   public interface CommandArgumentConsumer {
      void accept(PacketWrapper var1) throws Exception;
   }
}
