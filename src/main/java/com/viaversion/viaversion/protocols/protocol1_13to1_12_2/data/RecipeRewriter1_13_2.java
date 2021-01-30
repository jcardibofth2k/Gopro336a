package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class RecipeRewriter1_13_2 extends RecipeRewriter {
   public RecipeRewriter1_13_2(Protocol protocol) {
      super(protocol);
      this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
      this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
      this.recipeHandlers.put("smelting", this::handleSmelting);
   }

   public void handleSmelting(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.STRING);
      Item[] items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      Item[] var3 = items;
      int var4 = items.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Item item = var3[var5];
         this.rewrite(item);
      }

      this.rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
      wrapper.passthrough(Type.FLOAT);
      wrapper.passthrough(Type.VAR_INT);
   }

   public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
      int ingredientsNo = (Integer)wrapper.passthrough(Type.VAR_INT) * (Integer)wrapper.passthrough(Type.VAR_INT);
      wrapper.passthrough(Type.STRING);

      for(int j = 0; j < ingredientsNo; ++j) {
         Item[] items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
         Item[] var5 = items;
         int var6 = items.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Item item = var5[var7];
            this.rewrite(item);
         }
      }

      this.rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
   }

   public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.STRING);
      int ingredientsNo = (Integer)wrapper.passthrough(Type.VAR_INT);

      for(int j = 0; j < ingredientsNo; ++j) {
         Item[] items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
         Item[] var5 = items;
         int var6 = items.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Item item = var5[var7];
            this.rewrite(item);
         }
      }

      this.rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
   }
}
