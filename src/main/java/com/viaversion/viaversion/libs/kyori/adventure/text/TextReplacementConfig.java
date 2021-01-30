package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TextReplacementConfig extends Buildable, Examinable {
   @NotNull
   static TextReplacementConfig.Builder builder() {
      return new com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfigImpl.Builder();
   }

   @NotNull
   Pattern matchPattern();

   public interface Builder extends Buildable.Builder {
      @Contract("_ -> this")
      default TextReplacementConfig.Builder matchLiteral(final String literal) {
         return this.match(Pattern.compile(literal, 16));
      }

      @Contract("_ -> this")
      @NotNull
      default TextReplacementConfig.Builder match(@NotNull @RegExp final String pattern) {
         return this.match(Pattern.compile(pattern));
      }

      @Contract("_ -> this")
      @NotNull
      TextReplacementConfig.Builder match(@NotNull final Pattern pattern);

      @NotNull
      default TextReplacementConfig.Builder once() {
         return this.times(1);
      }

      @Contract("_ -> this")
      @NotNull
      default TextReplacementConfig.Builder times(final int times) {
         return this.condition((index, replaced) -> {
            return replaced < times ? PatternReplacementResult.REPLACE : PatternReplacementResult.STOP;
         });
      }

      @Contract("_ -> this")
      @NotNull
      default TextReplacementConfig.Builder condition(@NotNull final IntFunction2 condition) {
         return this.condition((result, matchCount, replaced) -> {
            return (PatternReplacementResult)condition.apply(matchCount, replaced);
         });
      }

      @Contract("_ -> this")
      @NotNull
      TextReplacementConfig.Builder condition(@NotNull final com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig.Condition condition);

      @Contract("_ -> this")
      @NotNull
      default TextReplacementConfig.Builder replacement(@NotNull final String replacement) {
         Objects.requireNonNull(replacement, "replacement");
         return this.replacement((builder) -> {
            return builder.content(replacement);
         });
      }

      @Contract("_ -> this")
      @NotNull
      default TextReplacementConfig.Builder replacement(@Nullable final ComponentLike replacement) {
         Component baked = replacement == null ? null : replacement.asComponent();
         return this.replacement((result, input) -> {
            return baked;
         });
      }

      @Contract("_ -> this")
      @NotNull
      default TextReplacementConfig.Builder replacement(@NotNull final Function replacement) {
         Objects.requireNonNull(replacement, "replacement");
         return this.replacement((result, input) -> {
            return (ComponentLike)replacement.apply(input);
         });
      }

      @Contract("_ -> this")
      @NotNull
      TextReplacementConfig.Builder replacement(@NotNull final BiFunction replacement);
   }
}
