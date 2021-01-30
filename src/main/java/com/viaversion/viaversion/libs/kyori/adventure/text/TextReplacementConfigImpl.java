package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementRenderer.State;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class TextReplacementConfigImpl implements TextReplacementConfig {
   private final Pattern matchPattern;
   private final BiFunction replacement;
   private final com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig.Condition continuer;

   TextReplacementConfigImpl(final com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfigImpl.Builder builder) {
      this.matchPattern = builder.matchPattern;
      this.replacement = builder.replacement;
      this.continuer = builder.continuer;
   }

   @NotNull
   public Pattern matchPattern() {
      return this.matchPattern;
   }

   State createState() {
      return new State(this.matchPattern, this.replacement, this.continuer);
   }

   @NotNull
   public TextReplacementConfig.Builder toBuilder() {
      return new com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfigImpl.Builder(this);
   }

   @NotNull
   public Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_53("matchPattern", this.matchPattern), ExaminableProperty.method_53("replacement", this.replacement), ExaminableProperty.method_53("continuer", this.continuer));
   }

   public String toString() {
      return (String)this.examine(StringExaminer.simpleEscaping());
   }

   // $FF: synthetic method
   static Pattern access$000(TextReplacementConfigImpl x0) {
      return x0.matchPattern;
   }

   // $FF: synthetic method
   static BiFunction access$100(TextReplacementConfigImpl x0) {
      return x0.replacement;
   }

   // $FF: synthetic method
   static com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig.Condition access$200(TextReplacementConfigImpl x0) {
      return x0.continuer;
   }
}
