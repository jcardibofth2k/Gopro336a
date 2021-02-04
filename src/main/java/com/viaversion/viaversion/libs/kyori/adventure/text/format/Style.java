package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Style extends Buildable, Examinable {
   Key DEFAULT_FONT = Key.key("default");

   @NotNull
   static Style empty() {
      return StyleImpl.EMPTY;
   }

   @NotNull
   static Style.Builder style() {
      return new StyleImpl.BuilderImpl();
   }

   @NotNull
   static Style style(@NotNull final Consumer consumer) {
      return (Style)Buildable.configureAndBuild(style(), consumer);
   }

   @NotNull
   static Style style(@Nullable final TextColor color) {
      return color == null ? empty() : new StyleImpl(null, color, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
   }

   @NotNull
   static Style style(@NotNull final TextDecoration decoration) {
      return style().decoration(decoration, true).build();
   }

   @NotNull
   static Style style(@Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      Style.Builder builder = style();
      builder.color(color);
      StyleImpl.decorate(builder, decorations);
      return builder.build();
   }

   @NotNull
   static Style style(@Nullable final TextColor color, final Set decorations) {
      Style.Builder builder = style();
      builder.color(color);
      if (!decorations.isEmpty()) {
         Iterator var3 = decorations.iterator();

         while(var3.hasNext()) {
            TextDecoration decoration = (TextDecoration)var3.next();
            builder.decoration(decoration, true);
         }
      }

      return builder.build();
   }

   @NotNull
   static Style style(@NotNull final StyleBuilderApplicable... applicables) {
      if (applicables.length == 0) {
         return empty();
      } else {
         Style.Builder builder = style();
         int i = 0;

         for(int length = applicables.length; i < length; ++i) {
            applicables[i].styleApply(builder);
         }

         return builder.build();
      }
   }

   @NotNull
   static Style style(@NotNull final Iterable applicables) {
      Style.Builder builder = style();
      Iterator var2 = applicables.iterator();

      while(var2.hasNext()) {
         StyleBuilderApplicable applicable = (StyleBuilderApplicable)var2.next();
         applicable.styleApply(builder);
      }

      return builder.build();
   }

   @NotNull
   default Style edit(@NotNull final Consumer consumer) {
      return this.edit(consumer, Style.Merge.Strategy.ALWAYS);
   }

   @NotNull
   default Style edit(@NotNull final Consumer consumer, @NotNull final Style.Merge.Strategy strategy) {
      return style((style) -> {
         if (strategy == Style.Merge.Strategy.ALWAYS) {
            style.merge(this, strategy);
         }

         consumer.accept(style);
         if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
            style.merge(this, strategy);
         }

      });
   }

   @Nullable
   Key font();

   @NotNull
   Style font(@Nullable final Key font);

   @Nullable
   TextColor color();

   @NotNull
   Style color(@Nullable final TextColor color);

   @NotNull
   Style colorIfAbsent(@Nullable final TextColor color);

   default boolean hasDecoration(@NotNull final TextDecoration decoration) {
      return this.decoration(decoration) == TextDecoration.State.TRUE;
   }

   @NotNull
   TextDecoration.State decoration(@NotNull final TextDecoration decoration);

   @NotNull
   default Style decorate(@NotNull final TextDecoration decoration) {
      return this.decoration(decoration, TextDecoration.State.TRUE);
   }

   @NotNull
   default Style decoration(@NotNull final TextDecoration decoration, final boolean flag) {
      return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
   }

   @NotNull
   Style decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state);

   @NotNull
   @Unmodifiable
   Map decorations();

   @NotNull
   Style decorations(@NotNull final Map decorations);

   @Nullable
   ClickEvent clickEvent();

   @NotNull
   Style clickEvent(@Nullable final ClickEvent event);

   @Nullable
   HoverEvent hoverEvent();

   @NotNull
   Style hoverEvent(@Nullable final HoverEventSource source);

   @Nullable
   String insertion();

   @NotNull
   Style insertion(@Nullable final String insertion);

   @NotNull
   default Style merge(@NotNull final Style that) {
      return this.merge(that, Style.Merge.all());
   }

   @NotNull
   default Style merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy) {
      return this.merge(that, strategy, Style.Merge.all());
   }

   @NotNull
   default Style merge(@NotNull final Style that, @NotNull final Style.Merge merge) {
      return this.merge(that, Collections.singleton(merge));
   }

   @NotNull
   default Style merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Style.Merge merge) {
      return this.merge(that, strategy, Collections.singleton(merge));
   }

   @NotNull
   default Style merge(@NotNull final Style that, @NotNull final Style.Merge... merges) {
      return this.merge(that, Style.Merge.method_437(merges));
   }

   @NotNull
   default Style merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Style.Merge... merges) {
      return this.merge(that, strategy, Style.Merge.method_437(merges));
   }

   @NotNull
   default Style merge(@NotNull final Style that, @NotNull final Set merges) {
      return this.merge(that, Style.Merge.Strategy.ALWAYS, merges);
   }

   @NotNull
   Style merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Set merges);

   boolean isEmpty();

   @NotNull
   Style.Builder toBuilder();

   interface Builder extends Buildable.Builder {
      @Contract("_ -> this")
      @NotNull
      Style.Builder font(@Nullable final Key font);

      @Contract("_ -> this")
      @NotNull
      Style.Builder color(@Nullable final TextColor color);

      @Contract("_ -> this")
      @NotNull
      Style.Builder colorIfAbsent(@Nullable final TextColor color);

      @Contract("_ -> this")
      @NotNull
      default Style.Builder decorate(@NotNull final TextDecoration decoration) {
         return this.decoration(decoration, TextDecoration.State.TRUE);
      }

      @Contract("_ -> this")
      @NotNull
      default Style.Builder decorate(@NotNull final TextDecoration... decorations) {
         int i = 0;

         for(int length = decorations.length; i < length; ++i) {
            this.decorate(decorations[i]);
         }

         return this;
      }

      @Contract("_, _ -> this")
      @NotNull
      default Style.Builder decoration(@NotNull final TextDecoration decoration, final boolean flag) {
         return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
      }

      @Contract("_, _ -> this")
      @NotNull
      Style.Builder decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state);

      @Contract("_ -> this")
      @NotNull
      Style.Builder clickEvent(@Nullable final ClickEvent event);

      @Contract("_ -> this")
      @NotNull
      Style.Builder hoverEvent(@Nullable final HoverEventSource source);

      @Contract("_ -> this")
      @NotNull
      Style.Builder insertion(@Nullable final String insertion);

      @Contract("_ -> this")
      @NotNull
      default Style.Builder merge(@NotNull final Style that) {
         return this.merge(that, Style.Merge.all());
      }

      @Contract("_, _ -> this")
      @NotNull
      default Style.Builder merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy) {
         return this.merge(that, strategy, Style.Merge.all());
      }

      @Contract("_, _ -> this")
      @NotNull
      default Style.Builder merge(@NotNull final Style that, @NotNull final Style.Merge... merges) {
         return merges.length == 0 ? this : this.merge(that, Style.Merge.method_437(merges));
      }

      @Contract("_, _, _ -> this")
      @NotNull
      default Style.Builder merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Style.Merge... merges) {
         return merges.length == 0 ? this : this.merge(that, strategy, Style.Merge.method_437(merges));
      }

      @Contract("_, _ -> this")
      @NotNull
      default Style.Builder merge(@NotNull final Style that, @NotNull final Set merges) {
         return this.merge(that, Style.Merge.Strategy.ALWAYS, merges);
      }

      @Contract("_, _, _ -> this")
      @NotNull
      Style.Builder merge(@NotNull final Style that, @NotNull final Style.Merge.Strategy strategy, @NotNull final Set merges);

      @Contract("_ -> this")
      @NotNull
      default Style.Builder apply(@NotNull final StyleBuilderApplicable applicable) {
         applicable.styleApply(this);
         return this;
      }

      @NotNull
      Style build();
   }

   enum Merge {
      COLOR,
      DECORATIONS,
      EVENTS,
      INSERTION,
      FONT;

      static final Set ALL = method_437(values());
      static final Set COLOR_AND_DECORATIONS = method_437(COLOR, DECORATIONS);

      @NotNull
      @Unmodifiable
      public static Set all() {
         return ALL;
      }

      @NotNull
      @Unmodifiable
      public static Set colorAndDecorations() {
         return COLOR_AND_DECORATIONS;
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.text.format.Style$Merge[]) java.util.Set
      @NotNull
      @Unmodifiable
      public static Set method_437(@NotNull final Style.Merge... merges) {
         return MonkeyBars.enumSet(Style.Merge.class, merges);
      }

      static boolean hasAll(@NotNull final Set merges) {
         return merges.size() == ALL.size();
      }

      // $FF: synthetic method
      private static Style.Merge[] $values() {
         return new Style.Merge[]{COLOR, DECORATIONS, EVENTS, INSERTION, FONT};
      }

      public
      enum Strategy {
         ALWAYS,
         NEVER,
         IF_ABSENT_ON_TARGET;

         // $FF: synthetic method
         private static Style.Merge.Strategy[] $values() {
            return new Style.Merge.Strategy[]{ALWAYS, NEVER, IF_ABSENT_ON_TARGET};
         }
      }
   }
}
