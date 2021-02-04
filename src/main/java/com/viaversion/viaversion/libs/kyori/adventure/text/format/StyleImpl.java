package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class StyleImpl implements Style {
   static final StyleImpl EMPTY;
   private static final TextDecoration[] DECORATIONS;
   @Nullable
   private final Key font;
   @Nullable
   private final TextColor color;
   private final TextDecoration.State obfuscated;
   private final TextDecoration.State bold;
   private final TextDecoration.State strikethrough;
   private final TextDecoration.State underlined;
   private final TextDecoration.State italic;
   @Nullable
   private final ClickEvent clickEvent;
   @Nullable
   private final HoverEvent hoverEvent;
   @Nullable
   private final String insertion;

   static void decorate(final Style.Builder builder, final TextDecoration[] decorations) {
      int i = 0;

      for(int length = decorations.length; i < length; ++i) {
         TextDecoration decoration = decorations[i];
         builder.decoration(decoration, true);
      }

   }

   StyleImpl(@Nullable final Key font, @Nullable final TextColor color, final TextDecoration.State obfuscated, final TextDecoration.State bold, final TextDecoration.State strikethrough, final TextDecoration.State underlined, final TextDecoration.State italic, @Nullable final ClickEvent clickEvent, @Nullable final HoverEvent hoverEvent, @Nullable final String insertion) {
      this.font = font;
      this.color = color;
      this.obfuscated = obfuscated;
      this.bold = bold;
      this.strikethrough = strikethrough;
      this.underlined = underlined;
      this.italic = italic;
      this.clickEvent = clickEvent;
      this.hoverEvent = hoverEvent;
      this.insertion = insertion;
   }

   @Nullable
   public Key font() {
      return this.font;
   }

   @NotNull
   public Style font(@Nullable final Key font) {
      return Objects.equals(this.font, font) ? this : new StyleImpl(font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
   }

   @Nullable
   public TextColor color() {
      return this.color;
   }

   @NotNull
   public Style color(@Nullable final TextColor color) {
      return Objects.equals(this.color, color) ? this : new StyleImpl(this.font, color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
   }

   @NotNull
   public Style colorIfAbsent(@Nullable final TextColor color) {
      return this.color == null ? this.color(color) : this;
   }

   @NotNull
   public TextDecoration.State decoration(@NotNull final TextDecoration decoration) {
      if (decoration == TextDecoration.BOLD) {
         return this.bold;
      } else if (decoration == TextDecoration.ITALIC) {
         return this.italic;
      } else if (decoration == TextDecoration.UNDERLINED) {
         return this.underlined;
      } else if (decoration == TextDecoration.STRIKETHROUGH) {
         return this.strikethrough;
      } else if (decoration == TextDecoration.OBFUSCATED) {
         return this.obfuscated;
      } else {
         throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
      }
   }

   @NotNull
   public Style decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
      Objects.requireNonNull(state, "state");
      if (decoration == TextDecoration.BOLD) {
         return new StyleImpl(this.font, this.color, this.obfuscated, state, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      } else if (decoration == TextDecoration.ITALIC) {
         return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, state, this.clickEvent, this.hoverEvent, this.insertion);
      } else if (decoration == TextDecoration.UNDERLINED) {
         return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, state, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      } else if (decoration == TextDecoration.STRIKETHROUGH) {
         return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, state, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      } else if (decoration == TextDecoration.OBFUSCATED) {
         return new StyleImpl(this.font, this.color, state, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      } else {
         throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
      }
   }

   @NotNull
   public Map decorations() {
      Map decorations = new EnumMap(TextDecoration.class);
      int i = 0;

      for(int length = DECORATIONS.length; i < length; ++i) {
         TextDecoration decoration = DECORATIONS[i];
         TextDecoration.State value = this.decoration(decoration);
         decorations.put(decoration, value);
      }

      return decorations;
   }

   @NotNull
   public Style decorations(@NotNull final Map decorations) {
      TextDecoration.State obfuscated = (TextDecoration.State)decorations.getOrDefault(TextDecoration.OBFUSCATED, this.obfuscated);
      TextDecoration.State bold = (TextDecoration.State)decorations.getOrDefault(TextDecoration.BOLD, this.bold);
      TextDecoration.State strikethrough = (TextDecoration.State)decorations.getOrDefault(TextDecoration.STRIKETHROUGH, this.strikethrough);
      TextDecoration.State underlined = (TextDecoration.State)decorations.getOrDefault(TextDecoration.UNDERLINED, this.underlined);
      TextDecoration.State italic = (TextDecoration.State)decorations.getOrDefault(TextDecoration.ITALIC, this.italic);
      return new StyleImpl(this.font, this.color, obfuscated, bold, strikethrough, underlined, italic, this.clickEvent, this.hoverEvent, this.insertion);
   }

   @Nullable
   public ClickEvent clickEvent() {
      return this.clickEvent;
   }

   @NotNull
   public Style clickEvent(@Nullable final ClickEvent event) {
      return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, event, this.hoverEvent, this.insertion);
   }

   @Nullable
   public HoverEvent hoverEvent() {
      return this.hoverEvent;
   }

   @NotNull
   public Style hoverEvent(@Nullable final HoverEventSource source) {
      return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, HoverEventSource.unbox(source), this.insertion);
   }

   @Nullable
   public String insertion() {
      return this.insertion;
   }

   @NotNull
   public Style insertion(@Nullable final String insertion) {
      return Objects.equals(this.insertion, insertion) ? this : new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, insertion);
   }

   @NotNull
   public Style merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Set merges) {
      if (!that.isEmpty() && strategy != Style.Merge.Strategy.NEVER && !merges.isEmpty()) {
         if (this.isEmpty() && Style.Merge.hasAll(merges)) {
            return that;
         } else {
            Style.Builder builder = this.toBuilder();
            builder.merge(that, strategy, merges);
            return builder.build();
         }
      } else {
         return this;
      }
   }

   public boolean isEmpty() {
      return this == EMPTY;
   }

   @NotNull
   public Style.Builder toBuilder() {
      return new StyleImpl.BuilderImpl(this);
   }

   @NotNull
   public Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_53("color", this.color), ExaminableProperty.method_53("obfuscated", this.obfuscated), ExaminableProperty.method_53("bold", this.bold), ExaminableProperty.method_53("strikethrough", this.strikethrough), ExaminableProperty.method_53("underlined", this.underlined), ExaminableProperty.method_53("italic", this.italic), ExaminableProperty.method_53("clickEvent", this.clickEvent), ExaminableProperty.method_53("hoverEvent", this.hoverEvent), ExaminableProperty.method_54("insertion", this.insertion), ExaminableProperty.method_53("font", this.font));
   }

   @NotNull
   public String toString() {
      return (String)this.examine(StringExaminer.simpleEscaping());
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof StyleImpl)) {
         return false;
      } else {
         StyleImpl that = (StyleImpl)other;
         return Objects.equals(this.color, that.color) && this.obfuscated == that.obfuscated && this.bold == that.bold && this.strikethrough == that.strikethrough && this.underlined == that.underlined && this.italic == that.italic && Objects.equals(this.clickEvent, that.clickEvent) && Objects.equals(this.hoverEvent, that.hoverEvent) && Objects.equals(this.insertion, that.insertion) && Objects.equals(this.font, that.font);
      }
   }

   public int hashCode() {
      int result = Objects.hashCode(this.color);
      result = 31 * result + this.obfuscated.hashCode();
      result = 31 * result + this.bold.hashCode();
      result = 31 * result + this.strikethrough.hashCode();
      result = 31 * result + this.underlined.hashCode();
      result = 31 * result + this.italic.hashCode();
      result = 31 * result + Objects.hashCode(this.clickEvent);
      result = 31 * result + Objects.hashCode(this.hoverEvent);
      result = 31 * result + Objects.hashCode(this.insertion);
      result = 31 * result + Objects.hashCode(this.font);
      return result;
   }

   static {
      EMPTY = new StyleImpl(null, null, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
      DECORATIONS = TextDecoration.values();
   }

   static final class BuilderImpl implements Style.Builder {
      @Nullable
      Key font;
      @Nullable
      TextColor color;
      TextDecoration.State obfuscated;
      TextDecoration.State bold;
      TextDecoration.State strikethrough;
      TextDecoration.State underlined;
      TextDecoration.State italic;
      @Nullable
      ClickEvent clickEvent;
      @Nullable
      HoverEvent hoverEvent;
      @Nullable
      String insertion;

      BuilderImpl() {
         this.obfuscated = TextDecoration.State.NOT_SET;
         this.bold = TextDecoration.State.NOT_SET;
         this.strikethrough = TextDecoration.State.NOT_SET;
         this.underlined = TextDecoration.State.NOT_SET;
         this.italic = TextDecoration.State.NOT_SET;
      }

      BuilderImpl(@NotNull final StyleImpl style) {
         this.obfuscated = TextDecoration.State.NOT_SET;
         this.bold = TextDecoration.State.NOT_SET;
         this.strikethrough = TextDecoration.State.NOT_SET;
         this.underlined = TextDecoration.State.NOT_SET;
         this.italic = TextDecoration.State.NOT_SET;
         this.color = style.color;
         this.obfuscated = style.obfuscated;
         this.bold = style.bold;
         this.strikethrough = style.strikethrough;
         this.underlined = style.underlined;
         this.italic = style.italic;
         this.clickEvent = style.clickEvent;
         this.hoverEvent = style.hoverEvent;
         this.insertion = style.insertion;
         this.font = style.font;
      }

      @NotNull
      public Style.Builder font(@Nullable final Key font) {
         this.font = font;
         return this;
      }

      @NotNull
      public Style.Builder color(@Nullable final TextColor color) {
         this.color = color;
         return this;
      }

      @NotNull
      public Style.Builder colorIfAbsent(@Nullable final TextColor color) {
         if (this.color == null) {
            this.color = color;
         }

         return this;
      }

      @NotNull
      public Style.Builder decorate(@NotNull final TextDecoration decoration) {
         return this.decoration(decoration, TextDecoration.State.TRUE);
      }

      @NotNull
      public Style.Builder decorate(@NotNull final TextDecoration... decorations) {
         int i = 0;

         for(int length = decorations.length; i < length; ++i) {
            this.decorate(decorations[i]);
         }

         return this;
      }

      @NotNull
      public Style.Builder decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
         Objects.requireNonNull(state, "state");
         if (decoration == TextDecoration.BOLD) {
            this.bold = state;
            return this;
         } else if (decoration == TextDecoration.ITALIC) {
            this.italic = state;
            return this;
         } else if (decoration == TextDecoration.UNDERLINED) {
            this.underlined = state;
            return this;
         } else if (decoration == TextDecoration.STRIKETHROUGH) {
            this.strikethrough = state;
            return this;
         } else if (decoration == TextDecoration.OBFUSCATED) {
            this.obfuscated = state;
            return this;
         } else {
            throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
         }
      }

      @NotNull
      Style.Builder decorationIfAbsent(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
         Objects.requireNonNull(state, "state");
         if (decoration == TextDecoration.BOLD && this.bold == TextDecoration.State.NOT_SET) {
            this.bold = state;
            return this;
         } else if (decoration == TextDecoration.ITALIC && this.italic == TextDecoration.State.NOT_SET) {
            this.italic = state;
            return this;
         } else if (decoration == TextDecoration.UNDERLINED && this.underlined == TextDecoration.State.NOT_SET) {
            this.underlined = state;
            return this;
         } else if (decoration == TextDecoration.STRIKETHROUGH && this.strikethrough == TextDecoration.State.NOT_SET) {
            this.strikethrough = state;
            return this;
         } else if (decoration == TextDecoration.OBFUSCATED && this.obfuscated == TextDecoration.State.NOT_SET) {
            this.obfuscated = state;
            return this;
         } else {
            throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
         }
      }

      @NotNull
      public Style.Builder clickEvent(@Nullable final ClickEvent event) {
         this.clickEvent = event;
         return this;
      }

      @NotNull
      public Style.Builder hoverEvent(@Nullable final HoverEventSource source) {
         this.hoverEvent = HoverEventSource.unbox(source);
         return this;
      }

      @NotNull
      public Style.Builder insertion(@Nullable final String insertion) {
         this.insertion = insertion;
         return this;
      }

      @NotNull
      public Style.Builder merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Set merges) {
         if (strategy != Style.Merge.Strategy.NEVER && !that.isEmpty() && !merges.isEmpty()) {
            Merger merger = merger(strategy);
            if (merges.contains(Style.Merge.COLOR)) {
               TextColor color = that.color();
               if (color != null) {
                  merger.mergeColor(this, color);
               }
            }

            if (merges.contains(Style.Merge.DECORATIONS)) {
               int i = 0;

               for(int length = StyleImpl.DECORATIONS.length; i < length; ++i) {
                  TextDecoration decoration = StyleImpl.DECORATIONS[i];
                  TextDecoration.State state = that.decoration(decoration);
                  if (state != TextDecoration.State.NOT_SET) {
                     merger.mergeDecoration(this, decoration, state);
                  }
               }
            }

            if (merges.contains(Style.Merge.EVENTS)) {
               ClickEvent clickEvent = that.clickEvent();
               if (clickEvent != null) {
                  merger.mergeClickEvent(this, clickEvent);
               }

               HoverEvent hoverEvent = that.hoverEvent();
               if (hoverEvent != null) {
                  merger.mergeHoverEvent(this, hoverEvent);
               }
            }

            if (merges.contains(Style.Merge.INSERTION)) {
               String insertion = that.insertion();
               if (insertion != null) {
                  merger.mergeInsertion(this, insertion);
               }
            }

            if (merges.contains(Style.Merge.FONT)) {
               Key font = that.font();
               if (font != null) {
                  merger.mergeFont(this, font);
               }
            }

            return this;
         } else {
            return this;
         }
      }

      private static Merger merger(final Style.Merge.Strategy strategy) {
         if (strategy == Style.Merge.Strategy.ALWAYS) {
            return AlwaysMerger.INSTANCE;
         } else if (strategy == Style.Merge.Strategy.NEVER) {
            throw new UnsupportedOperationException();
         } else if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
            return IfAbsentOnTargetMerger.INSTANCE;
         } else {
            throw new IllegalArgumentException(strategy.name());
         }
      }

      @NotNull
      public StyleImpl build() {
         return this.isEmpty() ? StyleImpl.EMPTY : new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      }

      private boolean isEmpty() {
         return this.color == null && this.obfuscated == TextDecoration.State.NOT_SET && this.bold == TextDecoration.State.NOT_SET && this.strikethrough == TextDecoration.State.NOT_SET && this.underlined == TextDecoration.State.NOT_SET && this.italic == TextDecoration.State.NOT_SET && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null;
      }
   }
}
