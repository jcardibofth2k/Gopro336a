package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public final class HoverEvent implements Examinable, HoverEventSource, StyleBuilderApplicable {
   private final HoverEvent.Action action;
   private final Object value;

   @NotNull
   public static HoverEvent showText(@NotNull final ComponentLike text) {
      return showText(text.asComponent());
   }

   @NotNull
   public static HoverEvent showText(@NotNull final Component text) {
      return new HoverEvent(HoverEvent.Action.SHOW_TEXT, text);
   }

   @NotNull
   public static HoverEvent showItem(@NotNull final Key item, @Range(from = 0L,to = 2147483647L) final int count) {
      return showItem(item, count, null);
   }

   @NotNull
   public static HoverEvent showItem(@NotNull final Keyed item, @Range(from = 0L,to = 2147483647L) final int count) {
      return showItem(item, count, null);
   }

   @NotNull
   public static HoverEvent showItem(@NotNull final Key item, @Range(from = 0L,to = 2147483647L) final int count, @Nullable final BinaryTagHolder nbt) {
      return showItem(HoverEvent.ShowItem.method_416(item, count, nbt));
   }

   @NotNull
   public static HoverEvent showItem(@NotNull final Keyed item, @Range(from = 0L,to = 2147483647L) final int count, @Nullable final BinaryTagHolder nbt) {
      return showItem(HoverEvent.ShowItem.method_417(item, count, nbt));
   }

   @NotNull
   public static HoverEvent showItem(@NotNull final HoverEvent.ShowItem item) {
      return new HoverEvent(HoverEvent.Action.SHOW_ITEM, item);
   }

   @NotNull
   public static HoverEvent showEntity(@NotNull final Key type, @NotNull final UUID id) {
      return showEntity(type, id, null);
   }

   @NotNull
   public static HoverEvent showEntity(@NotNull final Keyed type, @NotNull final UUID id) {
      return showEntity(type, id, null);
   }

   @NotNull
   public static HoverEvent showEntity(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
      return showEntity(HoverEvent.ShowEntity.method_2670(type, id, name));
   }

   @NotNull
   public static HoverEvent showEntity(@NotNull final Keyed type, @NotNull final UUID id, @Nullable final Component name) {
      return showEntity(HoverEvent.ShowEntity.method_2671(type, id, name));
   }

   @NotNull
   public static HoverEvent showEntity(@NotNull final HoverEvent.ShowEntity entity) {
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, entity);
   }

   @NotNull
   public static HoverEvent hoverEvent(@NotNull final HoverEvent.Action action, @NotNull final Object value) {
      return new HoverEvent(action, value);
   }

   private HoverEvent(@NotNull final HoverEvent.Action action, @NotNull final Object value) {
      this.action = Objects.requireNonNull(action, "action");
      this.value = Objects.requireNonNull(value, "value");
   }

   @NotNull
   public HoverEvent.Action action() {
      return this.action;
   }

   @NotNull
   public Object value() {
      return this.value;
   }

   @NotNull
   public HoverEvent value(@NotNull final Object value) {
      return new HoverEvent(this.action, value);
   }

   @NotNull
   public HoverEvent withRenderedValue(@NotNull final ComponentRenderer renderer, @NotNull final Object context) {
      Object oldValue = this.value;
      Object newValue = this.action.renderer.render(renderer, context, oldValue);
      return newValue != oldValue ? new HoverEvent(this.action, newValue) : this;
   }

   @NotNull
   public HoverEvent asHoverEvent() {
      return this;
   }

   @NotNull
   public HoverEvent asHoverEvent(@NotNull final UnaryOperator op) {
      return op == UnaryOperator.identity() ? this : new HoverEvent(this.action, op.apply(this.value));
   }

   public void styleApply(@NotNull final Style.Builder style) {
      style.hoverEvent(this);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         HoverEvent that = (HoverEvent)other;
         return this.action == that.action && this.value.equals(that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.action.hashCode();
      result = 31 * result + this.value.hashCode();
      return result;
   }

   @NotNull
   public Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_53("action", this.action), ExaminableProperty.method_53("value", this.value));
   }

   public String toString() {
      return (String)this.examine(StringExaminer.simpleEscaping());
   }

   public static final class Action {
      public static final HoverEvent.Action SHOW_TEXT = new HoverEvent.Action("show_text", Component.class, true, new HoverEvent.Action.Renderer() {
         @NotNull
         public Component render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Component value) {
            return renderer.render(value, context);
         }
      });
      public static final HoverEvent.Action SHOW_ITEM = new HoverEvent.Action("show_item", HoverEvent.ShowItem.class, true, new HoverEvent.Action.Renderer() {
         @NotNull
         public HoverEvent.ShowItem render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final HoverEvent.ShowItem value) {
            return value;
         }
      });
      public static final HoverEvent.Action SHOW_ENTITY = new HoverEvent.Action("show_entity", HoverEvent.ShowEntity.class, true, new HoverEvent.Action.Renderer() {
         @NotNull
         public HoverEvent.ShowEntity render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final HoverEvent.ShowEntity value) {
            return value.name == null ? value : value.name(renderer.render(value.name, context));
         }
      });
      public static final Index NAMES;
      private final String name;
      private final Class type;
      private final boolean readable;
      private final HoverEvent.Action.Renderer renderer;

      Action(final String name, final Class type, final boolean readable, final HoverEvent.Action.Renderer renderer) {
         this.name = name;
         this.type = type;
         this.readable = readable;
         this.renderer = renderer;
      }

      @NotNull
      public Class type() {
         return this.type;
      }

      public boolean readable() {
         return this.readable;
      }

      @NotNull
      public String toString() {
         return this.name;
      }

      static {
         NAMES = Index.create((constant) -> {
            return constant.name;
         }, SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY))
      }

      @FunctionalInterface
      interface Renderer {
         @NotNull
         Object render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Object value);
      }
   }

   public static final class ShowItem implements Examinable {
      private final Key item;
      private final int count;
      @Nullable
      private final BinaryTagHolder nbt;

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Key, int) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowItem
      @NotNull
      public static HoverEvent.ShowItem method_414(@NotNull final Key item, @Range(from = 0L,to = 2147483647L) final int count) {
         return method_416(item, count, null);
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Keyed, int) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowItem
      @NotNull
      public static HoverEvent.ShowItem method_415(@NotNull final Keyed item, @Range(from = 0L,to = 2147483647L) final int count) {
         return method_417(item, count, null);
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Key, int, com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowItem
      @NotNull
      public static HoverEvent.ShowItem method_416(@NotNull final Key item, @Range(from = 0L,to = 2147483647L) final int count, @Nullable final BinaryTagHolder nbt) {
         return new HoverEvent.ShowItem(Objects.requireNonNull(item, "item"), count, nbt);
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Keyed, int, com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowItem
      @NotNull
      public static HoverEvent.ShowItem method_417(@NotNull final Keyed item, @Range(from = 0L,to = 2147483647L) final int count, @Nullable final BinaryTagHolder nbt) {
         return new HoverEvent.ShowItem(Objects.requireNonNull(item, "item").key(), count, nbt);
      }

      private ShowItem(@NotNull final Key item, @Range(from = 0L,to = 2147483647L) final int count, @Nullable final BinaryTagHolder nbt) {
         this.item = item;
         this.count = count;
         this.nbt = nbt;
      }

      @NotNull
      public Key item() {
         return this.item;
      }

      @NotNull
      public HoverEvent.ShowItem item(@NotNull final Key item) {
         return Objects.requireNonNull(item, "item").equals(this.item) ? this : new HoverEvent.ShowItem(item, this.count, this.nbt);
      }

      @Range(
         from = 0L,
         to = 2147483647L
      )
      public int count() {
         return this.count;
      }

      @NotNull
      public HoverEvent.ShowItem count(@Range(from = 0L,to = 2147483647L) final int count) {
         return count == this.count ? this : new HoverEvent.ShowItem(this.item, count, this.nbt);
      }

      @Nullable
      public BinaryTagHolder nbt() {
         return this.nbt;
      }

      @NotNull
      public HoverEvent.ShowItem nbt(@Nullable final BinaryTagHolder nbt) {
         return Objects.equals(nbt, this.nbt) ? this : new HoverEvent.ShowItem(this.item, this.count, nbt);
      }

      public boolean equals(@Nullable final Object other) {
         if (this == other) {
            return true;
         } else if (other != null && this.getClass() == other.getClass()) {
            HoverEvent.ShowItem that = (HoverEvent.ShowItem)other;
            return this.item.equals(that.item) && this.count == that.count && Objects.equals(this.nbt, that.nbt);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.item.hashCode();
         result = 31 * result + Integer.hashCode(this.count);
         result = 31 * result + Objects.hashCode(this.nbt);
         return result;
      }

      @NotNull
      public Stream examinableProperties() {
         return Stream.of(ExaminableProperty.method_53("item", this.item), ExaminableProperty.method_65("count", this.count), ExaminableProperty.method_53("nbt", this.nbt));
      }

      public String toString() {
         return (String)this.examine(StringExaminer.simpleEscaping());
      }
   }

   public static final class ShowEntity implements Examinable {
      private final Key type;
      // $FF: renamed from: id java.util.UUID
      private final UUID field_3215;
      private final Component name;

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Key, java.util.UUID) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowEntity
      @NotNull
      public static HoverEvent.ShowEntity method_2668(@NotNull final Key type, @NotNull final UUID id) {
         return method_2670(type, id, null);
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Keyed, java.util.UUID) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowEntity
      @NotNull
      public static HoverEvent.ShowEntity method_2669(@NotNull final Keyed type, @NotNull final UUID id) {
         return method_2671(type, id, null);
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Key, java.util.UUID, com.viaversion.viaversion.libs.kyori.adventure.text.Component) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowEntity
      @NotNull
      public static HoverEvent.ShowEntity method_2670(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
         return new HoverEvent.ShowEntity(Objects.requireNonNull(type, "type"), Objects.requireNonNull(id, "id"), name);
      }

      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.key.Keyed, java.util.UUID, com.viaversion.viaversion.libs.kyori.adventure.text.Component) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowEntity
      @NotNull
      public static HoverEvent.ShowEntity method_2671(@NotNull final Keyed type, @NotNull final UUID id, @Nullable final Component name) {
         return new HoverEvent.ShowEntity(Objects.requireNonNull(type, "type").key(), Objects.requireNonNull(id, "id"), name);
      }

      private ShowEntity(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
         this.type = type;
         this.field_3215 = id;
         this.name = name;
      }

      @NotNull
      public Key type() {
         return this.type;
      }

      @NotNull
      public HoverEvent.ShowEntity type(@NotNull final Key type) {
         return Objects.requireNonNull(type, "type").equals(this.type) ? this : new HoverEvent.ShowEntity(type, this.field_3215, this.name);
      }

      @NotNull
      public HoverEvent.ShowEntity type(@NotNull final Keyed type) {
         return this.type(Objects.requireNonNull(type, "type").key());
      }

      // $FF: renamed from: id () java.util.UUID
      @NotNull
      public UUID method_2672() {
         return this.field_3215;
      }

      // $FF: renamed from: id (java.util.UUID) com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent$ShowEntity
      @NotNull
      public HoverEvent.ShowEntity method_2673(@NotNull final UUID id) {
         return Objects.requireNonNull(id).equals(this.field_3215) ? this : new HoverEvent.ShowEntity(this.type, id, this.name);
      }

      @Nullable
      public Component name() {
         return this.name;
      }

      @NotNull
      public HoverEvent.ShowEntity name(@Nullable final Component name) {
         return Objects.equals(name, this.name) ? this : new HoverEvent.ShowEntity(this.type, this.field_3215, name);
      }

      public boolean equals(@Nullable final Object other) {
         if (this == other) {
            return true;
         } else if (other != null && this.getClass() == other.getClass()) {
            HoverEvent.ShowEntity that = (HoverEvent.ShowEntity)other;
            return this.type.equals(that.type) && this.field_3215.equals(that.field_3215) && Objects.equals(this.name, that.name);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.type.hashCode();
         result = 31 * result + this.field_3215.hashCode();
         result = 31 * result + Objects.hashCode(this.name);
         return result;
      }

      @NotNull
      public Stream examinableProperties() {
         return Stream.of(ExaminableProperty.method_53("type", this.type), ExaminableProperty.method_53("id", this.field_3215), ExaminableProperty.method_53("name", this.name));
      }

      public String toString() {
         return (String)this.examine(StringExaminer.simpleEscaping());
      }
   }
}
