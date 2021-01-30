package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.TranslateRewriter.1;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class TranslateRewriter {
   private static final ComponentRewriter achievementTextRewriter = new 1();

   public static void toClient(JsonElement element, UserConnection user) {
      if (element instanceof JsonObject) {
         JsonObject obj = (JsonObject)element;
         JsonElement translate = obj.get("translate");
         if (translate != null && translate.getAsString().startsWith("chat.type.achievement")) {
            achievementTextRewriter.processText((JsonElement)obj);
         }
      }

   }
}
