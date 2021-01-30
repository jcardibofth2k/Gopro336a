package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;

public class RecipeRewriter1_16 extends RecipeRewriter1_14 {
   public RecipeRewriter1_16(Protocol protocol) {
      super(protocol);
      this.recipeHandlers.put("smithing", this::handleSmithing);
   }

   public void handleSmithing(PacketWrapper wrapper) throws Exception {
      Item[] baseIngredients = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      Item[] ingredients = baseIngredients;
      int var4 = baseIngredients.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         Item item = ingredients[var5];
         this.rewrite(item);
      }

      ingredients = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      Item[] var8 = ingredients;
      var5 = ingredients.length;

      for(int var9 = 0; var9 < var5; ++var9) {
         Item item = var8[var9];
         this.rewrite(item);
      }

      this.rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
   }
}
