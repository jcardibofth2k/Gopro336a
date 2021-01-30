package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractComponentBuilder implements ComponentBuilder {
   protected List children = Collections.emptyList();
   @Nullable
   private Style style;
   @Nullable
   private Style.Builder styleBuilder;

   protected AbstractComponentBuilder() {
   }

   protected AbstractComponentBuilder(@NotNull final BuildableComponent component) {
      List children = component.children();
      if (!children.isEmpty()) {
         this.children = new ArrayList(children);
      }

      if (component.hasStyling()) {
         this.style = component.style();
      }

   }

   @NotNull
   public ComponentBuilder append(@NotNull final Component component) {
      if (component == Component.empty()) {
         return this;
      } else {
         this.prepareChildren();
         this.children.add(component);
         return this;
      }
   }

   @NotNull
   public ComponentBuilder append(@NotNull final Component... components) {
      boolean prepared = false;
      int i = 0;

      for(int length = components.length; i < length; ++i) {
         Component component = components[i];
         if (component != Component.empty()) {
            if (!prepared) {
               this.prepareChildren();
               prepared = true;
            }

            this.children.add(component);
         }
      }

      return this;
   }

   @NotNull
   public ComponentBuilder append(@NotNull final ComponentLike... components) {
      boolean prepared = false;
      int i = 0;

      for(int length = components.length; i < length; ++i) {
         Component component = components[i].asComponent();
         if (component != Component.empty()) {
            if (!prepared) {
               this.prepareChildren();
               prepared = true;
            }

            this.children.add(component);
         }
      }

      return this;
   }

   @NotNull
   public ComponentBuilder append(@NotNull final Iterable components) {
      boolean prepared = false;
      Iterator var3 = components.iterator();

      while(var3.hasNext()) {
         ComponentLike like = (ComponentLike)var3.next();
         Component component = like.asComponent();
         if (component != Component.empty()) {
            if (!prepared) {
               this.prepareChildren();
               prepared = true;
            }

            this.children.add(component);
         }
      }

      return this;
   }

   private void prepareChildren() {
      if (this.children == Collections.emptyList()) {
         this.children = new ArrayList();
      }

   }

   @NotNull
   public ComponentBuilder applyDeep(@NotNull final Consumer consumer) {
      this.apply(consumer);
      if (this.children == Collections.emptyList()) {
         return this;
      } else {
         ListIterator it = this.children.listIterator();

         while(it.hasNext()) {
            Component child = (Component)it.next();
            if (child instanceof BuildableComponent) {
               ComponentBuilder childBuilder = ((BuildableComponent)child).toBuilder();
               childBuilder.applyDeep(consumer);
               it.set(childBuilder.build());
            }
         }

         return this;
      }
   }

   @NotNull
   public ComponentBuilder mapChildren(@NotNull final Function function) {
      if (this.children == Collections.emptyList()) {
         return this;
      } else {
         ListIterator it = this.children.listIterator();

         while(it.hasNext()) {
            Component child = (Component)it.next();
            if (child instanceof BuildableComponent) {
               BuildableComponent mappedChild = (BuildableComponent)function.apply((BuildableComponent)child);
               if (child != mappedChild) {
                  it.set(mappedChild);
               }
            }
         }

         return this;
      }
   }

   @NotNull
   public ComponentBuilder mapChildrenDeep(@NotNull final Function function) {
      if (this.children == Collections.emptyList()) {
         return this;
      } else {
         ListIterator it = this.children.listIterator();

         while(it.hasNext()) {
            Component child = (Component)it.next();
            if (child instanceof BuildableComponent) {
               BuildableComponent mappedChild = (BuildableComponent)function.apply((BuildableComponent)child);
               if (mappedChild.children().isEmpty()) {
                  if (child != mappedChild) {
                     it.set(mappedChild);
                  }
               } else {
                  ComponentBuilder builder = mappedChild.toBuilder();
                  builder.mapChildrenDeep(function);
                  it.set(builder.build());
               }
            }
         }

         return this;
      }
   }

   @NotNull
   public List children() {
      return Collections.unmodifiableList(this.children);
   }

   @NotNull
   public ComponentBuilder style(@NotNull final Style style) {
      this.style = style;
      this.styleBuilder = null;
      return this;
   }

   @NotNull
   public ComponentBuilder style(@NotNull final Consumer consumer) {
      consumer.accept(this.styleBuilder());
      return this;
   }

   @NotNull
   public ComponentBuilder font(@Nullable final Key font) {
      this.styleBuilder().font(font);
      return this;
   }

   @NotNull
   public ComponentBuilder color(@Nullable final TextColor color) {
      this.styleBuilder().color(color);
      return this;
   }

   @NotNull
   public ComponentBuilder colorIfAbsent(@Nullable final TextColor color) {
      this.styleBuilder().colorIfAbsent(color);
      return this;
   }

   @NotNull
   public ComponentBuilder decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
      this.styleBuilder().decoration(decoration, state);
      return this;
   }

   @NotNull
   public ComponentBuilder clickEvent(@Nullable final ClickEvent event) {
      this.styleBuilder().clickEvent(event);
      return this;
   }

   @NotNull
   public ComponentBuilder hoverEvent(@Nullable final HoverEventSource source) {
      this.styleBuilder().hoverEvent(source);
      return this;
   }

   @NotNull
   public ComponentBuilder insertion(@Nullable final String insertion) {
      this.styleBuilder().insertion(insertion);
      return this;
   }

   @NotNull
   public ComponentBuilder mergeStyle(@NotNull final Component that, @NotNull final Set merges) {
      this.styleBuilder().merge(that.style(), merges);
      return this;
   }

   @NotNull
   public ComponentBuilder resetStyle() {
      this.style = null;
      this.styleBuilder = null;
      return this;
   }

   @NotNull
   private Style.Builder styleBuilder() {
      if (this.styleBuilder == null) {
         if (this.style != null) {
            this.styleBuilder = this.style.toBuilder();
            this.style = null;
         } else {
            this.styleBuilder = Style.style();
         }
      }

      return this.styleBuilder;
   }

   protected final boolean hasStyle() {
      return this.styleBuilder != null || this.style != null;
   }

   @NotNull
   protected Style buildStyle() {
      if (this.styleBuilder != null) {
         return this.styleBuilder.build();
      } else {
         return this.style != null ? this.style : Style.empty();
      }
   }
}
