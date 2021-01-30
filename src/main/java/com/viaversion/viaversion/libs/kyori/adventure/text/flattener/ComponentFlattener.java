package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentFlattener extends Buildable {
   @NotNull
   static ComponentFlattener.Builder builder() {
      return new ComponentFlattenerImpl.BuilderImpl();
   }

   @NotNull
   static ComponentFlattener basic() {
      return ComponentFlattenerImpl.BASIC;
   }

   @NotNull
   static ComponentFlattener textOnly() {
      return ComponentFlattenerImpl.TEXT_ONLY;
   }

   void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener);

   public interface Builder extends Buildable.Builder {
      @NotNull
      ComponentFlattener.Builder mapper(@NotNull final Class type, @NotNull final Function converter);

      @NotNull
      ComponentFlattener.Builder complexMapper(@NotNull final Class type, @NotNull final BiConsumer converter);

      @NotNull
      ComponentFlattener.Builder unknownMapper(@Nullable final Function converter);
   }
}
