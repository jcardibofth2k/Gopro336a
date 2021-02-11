package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TextDecoration implements StyleBuilderApplicable, TextFormat {
   OBFUSCATED("obfuscated"),
   BOLD("bold"),
   STRIKETHROUGH("strikethrough"),
   UNDERLINED("underlined"),
   ITALIC("italic");

   public static final Index NAMES = Index.create(TextDecoration.class, (constant) -> {
      return constant.name;
   });
   private final String name;

   TextDecoration(final String name) {
      this.name = name;
   }

   // $FF: renamed from: as (boolean) com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndState
   @NotNull
   public final TextDecorationAndState method_51(final boolean state) {
      return this.method_52(TextDecoration.State.byBoolean(state));
   }

   // $FF: renamed from: as (com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration$State) com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndState
   @NotNull
   public final TextDecorationAndState method_52(@NotNull final TextDecoration.State state) {
      return new TextDecorationAndStateImpl(this, state);
   }

   public void styleApply(@NotNull final Style.Builder style) {
      style.decorate(this);
   }

   @NotNull
   public String toString() {
      return this.name;
   }

   // $FF: synthetic method
   private static TextDecoration[] $values() {
      return new TextDecoration[]{OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINED, ITALIC};
   }

   public
   enum State {
      NOT_SET("not_set"),
      FALSE("false"),
      TRUE("true");

      private final String name;

      State(final String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      @NotNull
      public static TextDecoration.State byBoolean(final boolean flag) {
         return flag ? TRUE : FALSE;
      }

      @NotNull
      public static TextDecoration.State byBoolean(@Nullable final Boolean flag) {
         return flag == null ? NOT_SET : byBoolean(flag);
      }

      // $FF: synthetic method
      private static TextDecoration.State[] $values() {
         return new TextDecoration.State[]{NOT_SET, FALSE, TRUE};
      }
   }
}
