package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import java.util.Iterator;

public class ComponentRewriter {
   protected final Protocol protocol;

   public ComponentRewriter(Protocol protocol) {
      this.protocol = protocol;
   }

   public ComponentRewriter() {
      this.protocol = null;
   }

   public void registerComponentPacket(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               ComponentRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   public void registerChatMessage(ClientboundPacketType packetType) {
      this.registerComponentPacket(packetType);
   }

   public void registerBossBar(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Type.VAR_INT, 0);
               if (action == 0 || action == 3) {
                  ComponentRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
               }

            });
         }
      });
   }

   public void registerCombatEvent(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               if ((Integer)wrapper.passthrough(Type.VAR_INT) == 2) {
                  wrapper.passthrough(Type.VAR_INT);
                  wrapper.passthrough(Type.INT);
                  ComponentRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
               }

            });
         }
      });
   }

   public void registerTitle(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.passthrough(Type.VAR_INT);
               if (action >= 0 && action <= 2) {
                  ComponentRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
               }

            });
         }
      });
   }

   public JsonElement processText(String value) {
      try {
         JsonElement root = JsonParser.parseString(value);
         this.processText(root);
         return root;
      } catch (JsonSyntaxException var3) {
         if (Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().severe("Error when trying to parse json: " + value);
            throw var3;
         } else {
            return new JsonPrimitive(value);
         }
      }
   }

   public void processText(JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         if (element.isJsonArray()) {
            this.processAsArray(element);
         } else if (element.isJsonPrimitive()) {
            this.handleText(element.getAsJsonPrimitive());
         } else {
            JsonObject object = element.getAsJsonObject();
            JsonPrimitive text = object.getAsJsonPrimitive("text");
            if (text != null) {
               this.handleText(text);
            }

            JsonElement translate = object.get("translate");
            JsonElement extra;
            if (translate != null) {
               this.handleTranslate(object, translate.getAsString());
               extra = object.get("with");
               if (extra != null) {
                  this.processAsArray(extra);
               }
            }

            extra = object.get("extra");
            if (extra != null) {
               this.processAsArray(extra);
            }

            JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
            if (hoverEvent != null) {
               this.handleHoverEvent(hoverEvent);
            }

         }
      }
   }

   protected void handleText(JsonPrimitive text) {
   }

   protected void handleTranslate(JsonObject object, String translate) {
   }

   protected void handleHoverEvent(JsonObject hoverEvent) {
      String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
      if (action.equals("show_text")) {
         JsonElement value = hoverEvent.get("value");
         this.processText(value != null ? value : hoverEvent.get("contents"));
      } else if (action.equals("show_entity")) {
         JsonObject contents = hoverEvent.getAsJsonObject("contents");
         if (contents != null) {
            this.processText(contents.get("name"));
         }
      }

   }

   private void processAsArray(JsonElement element) {
      Iterator var2 = element.getAsJsonArray().iterator();

      while(var2.hasNext()) {
         JsonElement jsonElement = (JsonElement)var2.next();
         this.processText(jsonElement);
      }

   }

   public Protocol getProtocol() {
      return this.protocol;
   }
}
