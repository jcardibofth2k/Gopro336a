package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ComponentFlattenerImpl implements ComponentFlattener {
   static final ComponentFlattener BASIC = (ComponentFlattener)(new ComponentFlattenerImpl.BuilderImpl()).mapper(KeybindComponent.class, (component) -> {
      return component.keybind();
   }).mapper(ScoreComponent.class, ScoreComponent::value).mapper(SelectorComponent.class, SelectorComponent::pattern).mapper(TextComponent.class, TextComponent::content).mapper(TranslatableComponent.class, TranslatableComponent::key).build();
   static final ComponentFlattener TEXT_ONLY = (ComponentFlattener)(new ComponentFlattenerImpl.BuilderImpl()).mapper(TextComponent.class, TextComponent::content).build();
   private static final int MAX_DEPTH = 512;
   private final Map flatteners;
   private final Map complexFlatteners;
   private final ConcurrentMap propagatedFlatteners = new ConcurrentHashMap();
   private final Function unknownHandler;

   ComponentFlattenerImpl(final Map flatteners, final Map complexFlatteners, @Nullable final Function unknownHandler) {
      this.flatteners = Collections.unmodifiableMap(new HashMap(flatteners));
      this.complexFlatteners = Collections.unmodifiableMap(new HashMap(complexFlatteners));
      this.unknownHandler = unknownHandler;
   }

   public void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener) {
      this.flatten0(input, listener, 0);
   }

   private void flatten0(@NotNull final Component input, @NotNull final FlattenerListener listener, final int depth) {
      Objects.requireNonNull(input, "input");
      Objects.requireNonNull(listener, "listener");
      if (input != Component.empty()) {
         if (depth > 512) {
            throw new IllegalStateException("Exceeded maximum depth of 512 while attempting to flatten components!");
         } else {
            ComponentFlattenerImpl.Handler flattener = this.flattener(input);
            Style inputStyle = input.style();
            listener.pushStyle(inputStyle);

            try {
               if (flattener != null) {
                  flattener.handle(input, listener, depth + 1);
               }

               if (!input.children().isEmpty()) {
                  Iterator var6 = input.children().iterator();

                  while(var6.hasNext()) {
                     Component child = (Component)var6.next();
                     this.flatten0(child, listener, depth + 1);
                  }
               }
            } finally {
               listener.popStyle(inputStyle);
            }

         }
      }
   }

   @Nullable
   private ComponentFlattenerImpl.Handler flattener(final Component test) {
      ComponentFlattenerImpl.Handler flattener = (ComponentFlattenerImpl.Handler)this.propagatedFlatteners.computeIfAbsent(test.getClass(), (key) -> {
         Function value = (Function)this.flatteners.get(key);
         if (value != null) {
            return (component, listener, depth) -> {
               listener.component((String)value.apply(component));
            };
         } else {
            Iterator var3 = this.flatteners.entrySet().iterator();

            Entry entry;
            do {
               if (!var3.hasNext()) {
                  BiConsumer complexValue = (BiConsumer)this.complexFlatteners.get(key);
                  if (complexValue != null) {
                     return (component, listener, depth) -> {
                        complexValue.accept(component, (c) -> {
                           this.flatten0(c, listener, depth);
                        });
                     };
                  }

                  Iterator var7 = this.complexFlatteners.entrySet().iterator();

                  Entry entryx;
                  do {
                     if (!var7.hasNext()) {
                        return ComponentFlattenerImpl.Handler.NONE;
                     }

                     entryx = (Entry)var7.next();
                  } while(!((Class)entryx.getKey()).isAssignableFrom(key));

                  return (component, listener, depth) -> {
                     ((BiConsumer)entryx.getValue()).accept(component, (c) -> {
                        this.flatten0(c, listener, depth);
                     });
                  };
               }

               entry = (Entry)var3.next();
            } while(!((Class)entry.getKey()).isAssignableFrom(key));

            return (component, listener, depth) -> {
               listener.component((String)((Function)entry.getValue()).apply(component));
            };
         }
      });
      if (flattener == ComponentFlattenerImpl.Handler.NONE) {
         return this.unknownHandler == null ? null : (component, listener, depth) -> {
            this.unknownHandler.apply(component);
         };
      } else {
         return flattener;
      }
   }

   @NotNull
   public ComponentFlattener.Builder toBuilder() {
      return new ComponentFlattenerImpl.BuilderImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
   }

   @FunctionalInterface
   interface Handler {
      ComponentFlattenerImpl.Handler NONE = (input, listener, depth) -> {
      };

      void handle(final Component input, final FlattenerListener listener, final int depth);
   }

   static final class BuilderImpl implements ComponentFlattener.Builder {
      private final Map flatteners;
      private final Map complexFlatteners;
      @Nullable
      private Function unknownHandler;

      BuilderImpl() {
         this.flatteners = new HashMap();
         this.complexFlatteners = new HashMap();
      }

      BuilderImpl(final Map flatteners, final Map complexFlatteners, @Nullable final Function unknownHandler) {
         this.flatteners = new HashMap(flatteners);
         this.complexFlatteners = new HashMap(complexFlatteners);
         this.unknownHandler = unknownHandler;
      }

      @NotNull
      public ComponentFlattener build() {
         return new ComponentFlattenerImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
      }

      @NotNull
      public ComponentFlattener.Builder mapper(@NotNull final Class type, @NotNull final Function converter) {
         this.validateNoneInHierarchy((Class)Objects.requireNonNull(type, "type"));
         this.flatteners.put(type, (Function)Objects.requireNonNull(converter, "converter"));
         this.complexFlatteners.remove(type);
         return this;
      }

      @NotNull
      public ComponentFlattener.Builder complexMapper(@NotNull final Class type, @NotNull final BiConsumer converter) {
         this.validateNoneInHierarchy((Class)Objects.requireNonNull(type, "type"));
         this.complexFlatteners.put(type, (BiConsumer)Objects.requireNonNull(converter, "converter"));
         this.flatteners.remove(type);
         return this;
      }

      private void validateNoneInHierarchy(final Class beingRegistered) {
         Iterator var2 = this.flatteners.keySet().iterator();

         Class clazz;
         while(var2.hasNext()) {
            clazz = (Class)var2.next();
            testHierarchy(clazz, beingRegistered);
         }

         var2 = this.complexFlatteners.keySet().iterator();

         while(var2.hasNext()) {
            clazz = (Class)var2.next();
            testHierarchy(clazz, beingRegistered);
         }

      }

      private static void testHierarchy(final Class existing, final Class beingRegistered) {
         if (!existing.equals(beingRegistered) && (existing.isAssignableFrom(beingRegistered) || beingRegistered.isAssignableFrom(existing))) {
            throw new IllegalArgumentException("Conflict detected between already registered type " + existing + " and newly registered type " + beingRegistered + "! Types in a component flattener must not share a common hierachy!");
         }
      }

      @NotNull
      public ComponentFlattener.Builder unknownMapper(@Nullable final Function converter) {
         this.unknownHandler = converter;
         return this;
      }
   }
}
