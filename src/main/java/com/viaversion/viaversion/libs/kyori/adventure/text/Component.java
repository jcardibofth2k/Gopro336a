package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;

@NonExtendable
public interface Component extends ComponentBuilderApplicable, ComponentLike, Examinable, HoverEventSource {
   BiPredicate EQUALS = Objects::equals;
   BiPredicate EQUALS_IDENTITY = (a, b) -> {
      return a == b;
   };

   @NotNull
   static TextComponent empty() {
      return TextComponentImpl.EMPTY;
   }

   @NotNull
   static TextComponent newline() {
      return TextComponentImpl.NEWLINE;
   }

   @NotNull
   static TextComponent space() {
      return TextComponentImpl.SPACE;
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent join(@NotNull final ComponentLike separator, @NotNull final ComponentLike... components) {
      return join(separator, Arrays.asList(components));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent join(@NotNull final ComponentLike separator, final Iterable components) {
      Iterator it = components.iterator();
      if (!it.hasNext()) {
         return empty();
      } else {
         TextComponent.Builder builder = text();

         while(it.hasNext()) {
            builder.append((ComponentLike)it.next());
            if (it.hasNext()) {
               builder.append(separator);
            }
         }

         return (TextComponent)builder.build();
      }
   }

   @NotNull
   static Collector toComponent() {
      return toComponent(empty());
   }

   @NotNull
   static Collector toComponent(@NotNull final Component separator) {
      return Collector.of(Component::text, (builder, add) -> {
         if (separator != empty() && !builder.children().isEmpty()) {
            builder.append(separator);
         }

         builder.append(add);
      }, (a, b) -> {
         List aChildren = a.children();
         TextComponent.Builder ret = (TextComponent.Builder)text().append(aChildren);
         if (!aChildren.isEmpty()) {
            ret.append(separator);
         }

         ret.append(b.children());
         return ret;
      }, ComponentBuilder::build);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static BlockNBTComponent.Builder blockNBT() {
      return new BlockNBTComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static BlockNBTComponent blockNBT(@NotNull final Consumer consumer) {
      return (BlockNBTComponent)Buildable.configureAndBuild(blockNBT(), consumer);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static BlockNBTComponent blockNBT(@NotNull final String nbtPath, @NotNull final BlockNBTComponent.Pos pos) {
      return blockNBT(nbtPath, false, pos);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static BlockNBTComponent blockNBT(@NotNull final String nbtPath, final boolean interpret, @NotNull final BlockNBTComponent.Pos pos) {
      return blockNBT(nbtPath, interpret, null, pos);
   }

   @Contract(
      value = "_, _, _, _ -> new",
      pure = true
   )
   @NotNull
   static BlockNBTComponent blockNBT(@NotNull final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final BlockNBTComponent.Pos pos) {
      return new BlockNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, pos);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static EntityNBTComponent.Builder entityNBT() {
      return new EntityNBTComponentImpl$BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static EntityNBTComponent entityNBT(@NotNull final Consumer consumer) {
      return (EntityNBTComponent)Buildable.configureAndBuild(entityNBT(), consumer);
   }

   @Contract("_, _ -> new")
   @NotNull
   static EntityNBTComponent entityNBT(@NotNull final String nbtPath, @NotNull final String selector) {
      return (EntityNBTComponent)((EntityNBTComponent.Builder)entityNBT().nbtPath(nbtPath)).selector(selector).build();
   }

   @Contract(
      pure = true
   )
   @NotNull
   static KeybindComponent.Builder keybind() {
      return new KeybindComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static KeybindComponent keybind(@NotNull final Consumer consumer) {
      return (KeybindComponent)Buildable.configureAndBuild(keybind(), consumer);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static KeybindComponent keybind(@NotNull final String keybind) {
      return keybind(keybind, Style.empty());
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static KeybindComponent keybind(@NotNull final String keybind, @NotNull final Style style) {
      return new KeybindComponentImpl(Collections.emptyList(), style, keybind);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color) {
      return keybind(keybind, Style.style(color));
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return keybind(keybind, Style.style(color, decorations));
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color, @NotNull final Set decorations) {
      return keybind(keybind, Style.style(color, decorations));
   }

   @Contract(
      pure = true
   )
   @NotNull
   static ScoreComponent.Builder score() {
      return new ScoreComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static ScoreComponent score(@NotNull final Consumer consumer) {
      return (ScoreComponent)Buildable.configureAndBuild(score(), consumer);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static ScoreComponent score(@NotNull final String name, @NotNull final String objective) {
      return score(name, objective, null);
   }

   /** @deprecated */
   @Deprecated
   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static ScoreComponent score(@NotNull final String name, @NotNull final String objective, @Nullable final String value) {
      return new ScoreComponentImpl(Collections.emptyList(), Style.empty(), name, objective, value);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static SelectorComponent.Builder selector() {
      return new SelectorComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static SelectorComponent selector(@NotNull final Consumer consumer) {
      return (SelectorComponent)Buildable.configureAndBuild(selector(), consumer);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static SelectorComponent selector(@NotNull final String pattern) {
      return selector(pattern, null);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static SelectorComponent selector(@NotNull final String pattern, @Nullable final ComponentLike separator) {
      return new SelectorComponentImpl(Collections.emptyList(), Style.empty(), pattern, separator);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static StorageNBTComponent.Builder storageNBT() {
      return new StorageNBTComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static StorageNBTComponent storageNBT(@NotNull final Consumer consumer) {
      return (StorageNBTComponent)Buildable.configureAndBuild(storageNBT(), consumer);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static StorageNBTComponent storageNBT(@NotNull final String nbtPath, @NotNull final Key storage) {
      return storageNBT(nbtPath, false, storage);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static StorageNBTComponent storageNBT(@NotNull final String nbtPath, final boolean interpret, @NotNull final Key storage) {
      return storageNBT(nbtPath, interpret, null, storage);
   }

   @Contract(
      value = "_, _, _, _ -> new",
      pure = true
   )
   @NotNull
   static StorageNBTComponent storageNBT(@NotNull final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final Key storage) {
      return new StorageNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, storage);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static TextComponent.Builder text() {
      return new TextComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static TextComponent text(@NotNull final Consumer consumer) {
      return (TextComponent)Buildable.configureAndBuild(text(), consumer);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(@NotNull final String content) {
      return content.isEmpty() ? empty() : new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(@NotNull final String content, @NotNull final Style style) {
      return new TextComponentImpl(Collections.emptyList(), style, content);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(@NotNull final String content, @Nullable final TextColor color) {
      return new TextComponentImpl(Collections.emptyList(), Style.style(color), content);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(@NotNull final String content, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(@NotNull final String content, @Nullable final TextColor color, @NotNull final Set decorations) {
      return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final boolean value) {
      return text(String.valueOf(value));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final boolean value, @NotNull final Style style) {
      return text(String.valueOf(value), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final boolean value, @Nullable final TextColor color) {
      return text(String.valueOf(value), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final boolean value, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final boolean value, @Nullable final TextColor color, @NotNull final Set decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static TextComponent text(final char value) {
      if (value == '\n') {
         return newline();
      } else {
         return value == ' ' ? space() : text(String.valueOf(value));
      }
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final char value, @NotNull final Style style) {
      return text(String.valueOf(value), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final char value, @Nullable final TextColor color) {
      return text(String.valueOf(value), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final char value, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final char value, @Nullable final TextColor color, @NotNull final Set decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final double value) {
      return text(String.valueOf(value));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final double value, @NotNull final Style style) {
      return text(String.valueOf(value), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final double value, @Nullable final TextColor color) {
      return text(String.valueOf(value), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final double value, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final double value, @Nullable final TextColor color, @NotNull final Set decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final float value) {
      return text(String.valueOf(value));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final float value, @NotNull final Style style) {
      return text(String.valueOf(value), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final float value, @Nullable final TextColor color) {
      return text(String.valueOf(value), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final float value, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final float value, @Nullable final TextColor color, @NotNull final Set decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final int value) {
      return text(String.valueOf(value));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final int value, @NotNull final Style style) {
      return text(String.valueOf(value), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final int value, @Nullable final TextColor color) {
      return text(String.valueOf(value), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final int value, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final int value, @Nullable final TextColor color, @NotNull final Set decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final long value) {
      return text(String.valueOf(value));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final long value, @NotNull final Style style) {
      return text(String.valueOf(value), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final long value, @Nullable final TextColor color) {
      return text(String.valueOf(value), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final long value, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TextComponent text(final long value, @Nullable final TextColor color, @NotNull final Set decorations) {
      return text(String.valueOf(value), color, decorations);
   }

   @Contract(
      pure = true
   )
   @NotNull
   static TranslatableComponent.Builder translatable() {
      return new TranslatableComponentImpl.BuilderImpl();
   }

   @Contract("_ -> new")
   @NotNull
   static TranslatableComponent translatable(@NotNull final Consumer consumer) {
      return (TranslatableComponent)Buildable.configureAndBuild(translatable(), consumer);
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key) {
      return translatable(key, Style.empty());
   }

   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), Style.empty());
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style) {
      return new TranslatableComponentImpl(Collections.emptyList(), style, key, Collections.emptyList());
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color) {
      return translatable(key, Style.style(color));
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return translatable(key, Style.style(color, decorations));
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final TextDecoration... decorations) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set decorations) {
      return translatable(key, Style.style(color, decorations));
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set decorations) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @NotNull final ComponentLike... args) {
      return translatable(key, Style.empty(), args);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final ComponentLike... args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style, @NotNull final ComponentLike... args) {
      return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style, @NotNull final ComponentLike... args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style, args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final ComponentLike... args) {
      return translatable(key, Style.style(color), args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final ComponentLike... args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, args);
   }

   @Contract(
      value = "_, _, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final ComponentLike... args) {
      return translatable(key, Style.style(color, decorations), args);
   }

   @Contract(
      value = "_, _, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final ComponentLike... args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations, args);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @NotNull final List args) {
      return new TranslatableComponentImpl(Collections.emptyList(), Style.empty(), key, args);
   }

   @Contract(
      value = "_, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final List args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style, @NotNull final List args) {
      return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style, @NotNull final List args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style, args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final List args) {
      return translatable(key, Style.style(color), args);
   }

   @Contract(
      value = "_, _, _ -> new",
      pure = true
   )
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final List args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, args);
   }

   @Contract(
      value = "_, _, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final List args) {
      return translatable(key, Style.style(color, decorations), args);
   }

   @Contract(
      value = "_, _, _, _ -> new",
      pure = true
   )
   @NotNull
   static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final List args) {
      return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations, args);
   }

   @NotNull
   @Unmodifiable
   List children();

   @Contract(
      pure = true
   )
   @NotNull
   Component children(@NotNull final List children);

   default boolean contains(@NotNull final Component that) {
      return this.contains(that, EQUALS_IDENTITY);
   }

   default boolean contains(@NotNull final Component that, @NotNull final BiPredicate equals) {
      if (equals.test(this, that)) {
         return true;
      } else {
         Iterator var3 = this.children().iterator();

         while(var3.hasNext()) {
            Component child = (Component)var3.next();
            if (child.contains(that, equals)) {
               return true;
            }
         }

         HoverEvent hoverEvent = this.hoverEvent();
         if (hoverEvent != null) {
            Object value = hoverEvent.value();
            Component component = null;
            if (value instanceof Component) {
               component = (Component)hoverEvent.value();
            } else if (value instanceof HoverEvent.ShowEntity) {
               component = ((HoverEvent.ShowEntity)value).name();
            }

            if (component != null) {
               if (equals.test(that, component)) {
                  return true;
               }

               Iterator var6 = component.children().iterator();

               while(var6.hasNext()) {
                  Component child = (Component)var6.next();
                  if (child.contains(that, equals)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   default void detectCycle(@NotNull final Component that) {
      if (that.contains(this)) {
         throw new IllegalStateException("Component cycle detected between " + this + " and " + that);
      }
   }

   @Contract(
      pure = true
   )
   @NotNull
   Component append(@NotNull final Component component);

   @NotNull
   default Component append(@NotNull final ComponentLike component) {
      return this.append(component.asComponent());
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component append(@NotNull final ComponentBuilder builder) {
      return this.append(builder.build());
   }

   @NotNull
   Style style();

   @Contract(
      pure = true
   )
   @NotNull
   Component style(@NotNull final Style style);

   @Contract(
      pure = true
   )
   @NotNull
   default Component style(@NotNull final Consumer consumer) {
      return this.style(this.style().edit(consumer));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component style(@NotNull final Consumer consumer, @NotNull final Style.Merge.Strategy strategy) {
      return this.style(this.style().edit(consumer, strategy));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component style(@NotNull final Style.Builder style) {
      return this.style(style.build());
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component mergeStyle(@NotNull final Component that) {
      return this.mergeStyle(that, Style.Merge.all());
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component mergeStyle(@NotNull final Component that, @NotNull final Style.Merge... merges) {
      return this.mergeStyle(that, Style.Merge.method_437(merges));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component mergeStyle(@NotNull final Component that, @NotNull final Set merges) {
      return this.style(this.style().merge(that.style(), merges));
   }

   @Nullable
   default TextColor color() {
      return this.style().color();
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component color(@Nullable final TextColor color) {
      return this.style(this.style().color(color));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component colorIfAbsent(@Nullable final TextColor color) {
      return this.color() == null ? this.color(color) : this;
   }

   default boolean hasDecoration(@NotNull final TextDecoration decoration) {
      return this.decoration(decoration) == TextDecoration.State.TRUE;
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component decorate(@NotNull final TextDecoration decoration) {
      return this.decoration(decoration, TextDecoration.State.TRUE);
   }

   @NotNull
   default TextDecoration.State decoration(@NotNull final TextDecoration decoration) {
      return this.style().decoration(decoration);
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component decoration(@NotNull final TextDecoration decoration, final boolean flag) {
      return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
      return this.style(this.style().decoration(decoration, state));
   }

   @NotNull
   default Map decorations() {
      return this.style().decorations();
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component decorations(@NotNull final Map decorations) {
      return this.style(this.style().decorations(decorations));
   }

   @Nullable
   default ClickEvent clickEvent() {
      return this.style().clickEvent();
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component clickEvent(@Nullable final ClickEvent event) {
      return this.style(this.style().clickEvent(event));
   }

   @Nullable
   default HoverEvent hoverEvent() {
      return this.style().hoverEvent();
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component hoverEvent(@Nullable final HoverEventSource source) {
      return this.style(this.style().hoverEvent(source));
   }

   @Nullable
   default String insertion() {
      return this.style().insertion();
   }

   @Contract(
      pure = true
   )
   @NotNull
   default Component insertion(@Nullable final String insertion) {
      return this.style(this.style().insertion(insertion));
   }

   default boolean hasStyling() {
      return !this.style().isEmpty();
   }

   @Contract(
      pure = true
   )
   @NotNull
   Component replaceText(@NotNull final Consumer configurer);

   @Contract(
      pure = true
   )
   @NotNull
   Component replaceText(@NotNull final TextReplacementConfig config);

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement) {
      return this.replaceText((b) -> {
         b.matchLiteral(search).replacement(replacement);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function replacement) {
      return this.replaceText((b) -> {
         b.match(pattern).replacement(replacement);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceFirstText(@NotNull final String search, @Nullable final ComponentLike replacement) {
      return this.replaceText((b) -> {
         b.matchLiteral(search).once().replacement(replacement);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceFirstText(@NotNull final Pattern pattern, @NotNull final Function replacement) {
      return this.replaceText((b) -> {
         b.match(pattern).once().replacement(replacement);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement, final int numberOfReplacements) {
      return this.replaceText((b) -> {
         b.matchLiteral(search).times(numberOfReplacements).replacement(replacement);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function replacement, final int numberOfReplacements) {
      return this.replaceText((b) -> {
         b.match(pattern).times(numberOfReplacements).replacement(replacement);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement, @NotNull final IntFunction2 fn) {
      return this.replaceText((b) -> {
         b.matchLiteral(search).replacement(replacement).condition(fn);
      });
   }

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      pure = true
   )
   @NotNull
   default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function replacement, @NotNull final IntFunction2 fn) {
      return this.replaceText((b) -> {
         b.match(pattern).replacement(replacement).condition(fn);
      });
   }

   default void componentBuilderApply(@NotNull final ComponentBuilder component) {
      component.append(this);
   }

   @NotNull
   default Component asComponent() {
      return this;
   }

   @NotNull
   default HoverEvent asHoverEvent(@NotNull final UnaryOperator op) {
      return HoverEvent.showText((Component)op.apply(this));
   }
}
