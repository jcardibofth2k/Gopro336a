package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Index {
   private final Map keyToValue;
   private final Map valueToKey;

   private Index(final Map keyToValue, final Map valueToKey) {
      this.keyToValue = keyToValue;
      this.valueToKey = valueToKey;
   }

   @NotNull
   public static Index create(final Class type, @NotNull final Function keyFunction) {
      return create(type, keyFunction, (Enum[])type.getEnumConstants());
   }

   @SafeVarargs
   @NotNull
   public static Index create(final Class type, @NotNull final Function keyFunction, @NotNull final Enum... values) {
      return create((Object[])values, (IntFunction)((length) -> {
         return new EnumMap(type);
      }), (Function)keyFunction);
   }

   @SafeVarargs
   @NotNull
   public static Index create(@NotNull final Function keyFunction, @NotNull final Object... values) {
      return create(values, HashMap::new, keyFunction);
   }

   @NotNull
   public static Index create(@NotNull final Function keyFunction, @NotNull final List constants) {
      return create(constants, HashMap::new, keyFunction);
   }

   @NotNull
   private static Index create(final Object[] values, final IntFunction valueToKeyFactory, @NotNull final Function keyFunction) {
      return create(Arrays.asList(values), valueToKeyFactory, keyFunction);
   }

   @NotNull
   private static Index create(final List values, final IntFunction valueToKeyFactory, @NotNull final Function keyFunction) {
      int length = values.size();
      Map keyToValue = new HashMap(length);
      Map valueToKey = (Map)valueToKeyFactory.apply(length);

      for(int i = 0; i < length; ++i) {
         Object value = values.get(i);
         Object key = keyFunction.apply(value);
         if (keyToValue.putIfAbsent(key, value) != null) {
            throw new IllegalStateException(String.format("Key %s already mapped to value %s", key, keyToValue.get(key)));
         }

         if (valueToKey.putIfAbsent(value, key) != null) {
            throw new IllegalStateException(String.format("Value %s already mapped to key %s", value, valueToKey.get(value)));
         }
      }

      return new Index(Collections.unmodifiableMap(keyToValue), Collections.unmodifiableMap(valueToKey));
   }

   @NotNull
   public Set keys() {
      return Collections.unmodifiableSet(this.keyToValue.keySet());
   }

   @Nullable
   public Object key(@NotNull final Object value) {
      return this.valueToKey.get(value);
   }

   @NotNull
   public Set values() {
      return Collections.unmodifiableSet(this.valueToKey.keySet());
   }

   @Nullable
   public Object value(@NotNull final Object key) {
      return this.keyToValue.get(key);
   }
}
