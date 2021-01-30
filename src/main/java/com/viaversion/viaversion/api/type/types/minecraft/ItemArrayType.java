package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import io.netty.buffer.ByteBuf;

public class ItemArrayType extends BaseItemArrayType {
   public ItemArrayType() {
      super("Item Array");
   }

   public Item[] read(ByteBuf buffer) throws Exception {
      int amount = SHORT.readPrimitive(buffer);
      Item[] array = new Item[amount];

      for(int i = 0; i < amount; ++i) {
         array[i] = (Item)ITEM.read(buffer);
      }

      return array;
   }

   public void write(ByteBuf buffer, Item[] object) throws Exception {
      SHORT.writePrimitive(buffer, (short)object.length);
      Item[] var3 = object;
      int var4 = object.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Item o = var3[var5];
         ITEM.write(buffer, o);
      }

   }
}
