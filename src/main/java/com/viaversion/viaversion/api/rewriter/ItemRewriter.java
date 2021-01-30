package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemRewriter extends Rewriter {
   @Nullable
   Item handleItemToClient(@Nullable Item var1);

   @Nullable
   Item handleItemToServer(@Nullable Item var1);
}
