package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl.NBTLegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ComponentRewriter1_13;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public final class ChatRewriter {
   public static final GsonComponentSerializer HOVER_GSON_SERIALIZER = GsonComponentSerializer.builder().emitLegacyHoverEvent().legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.get()).build();
   private static final ComponentRewriter COMPONENT_REWRITER = new ComponentRewriter1_13();

   public static String legacyTextToJsonString(String message, boolean itemData) {
      Component component = Component.text((builder) -> {
         if (itemData) {
            builder.decoration(TextDecoration.ITALIC, false);
         }

         builder.append(LegacyComponentSerializer.legacySection().deserialize(message));
      });
      return (String)GsonComponentSerializer.gson().serialize(component);
   }

   public static String legacyTextToJsonString(String legacyText) {
      return legacyTextToJsonString(legacyText, false);
   }

   public static JsonElement legacyTextToJson(String legacyText) {
      return JsonParser.parseString(legacyTextToJsonString(legacyText, false));
   }

   public static String jsonToLegacyText(String value) {
      try {
         Component component = HOVER_GSON_SERIALIZER.deserialize(value);
         return LegacyComponentSerializer.legacySection().serialize(component);
      } catch (Exception var2) {
         Via.getPlatform().getLogger().warning("Error converting json text to legacy: " + value);
         var2.printStackTrace();
         return "";
      }
   }

   public static void processTranslate(JsonElement value) {
      COMPONENT_REWRITER.processText(value);
   }
}
