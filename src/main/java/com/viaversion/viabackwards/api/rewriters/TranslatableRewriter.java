package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TranslatableRewriter extends ComponentRewriter {
   private static final Map TRANSLATABLES = new HashMap();
   protected final Map newTranslatables;

   public static void loadTranslatables() {
      JsonObject jsonObject = VBMappingDataLoader.loadData("translation-mappings.json");
      Iterator var1 = jsonObject.entrySet().iterator();

      while(var1.hasNext()) {
         Entry entry = (Entry)var1.next();
         Map versionMappings = new HashMap();
         TRANSLATABLES.put((String)entry.getKey(), versionMappings);
         Iterator var4 = ((JsonElement)entry.getValue()).getAsJsonObject().entrySet().iterator();

         while(var4.hasNext()) {
            Entry translationEntry = (Entry)var4.next();
            versionMappings.put((String)translationEntry.getKey(), ((JsonElement)translationEntry.getValue()).getAsString());
         }
      }

   }

   public TranslatableRewriter(BackwardsProtocol protocol) {
      this(protocol, protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
   }

   public TranslatableRewriter(BackwardsProtocol protocol, String sectionIdentifier) {
      super(protocol);
      Map newTranslatables = (Map)TRANSLATABLES.get(sectionIdentifier);
      if (newTranslatables == null) {
         ViaBackwards.getPlatform().getLogger().warning("Error loading " + sectionIdentifier + " translatables!");
         this.newTranslatables = new HashMap();
      } else {
         this.newTranslatables = newTranslatables;
      }

   }

   public void registerPing() {
      this.protocol.registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   public void registerDisconnect(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   public void registerChatMessage(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   public void registerLegacyOpenWindow(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.handler((wrapper) -> {
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   public void registerOpenWindow(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   public void registerTabList(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
               TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
            });
         }
      });
   }

   protected void handleTranslate(JsonObject root, String translate) {
      String newTranslate = (String)this.newTranslatables.get(translate);
      if (newTranslate != null) {
         root.addProperty("translate", newTranslate);
      }

   }
}
