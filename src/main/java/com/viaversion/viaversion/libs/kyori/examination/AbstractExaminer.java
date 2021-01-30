package com.viaversion.viaversion.libs.kyori.examination;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractExaminer implements Examiner {
   @NonNull
   public Object examine(@Nullable final Object value) {
      if (value == null) {
         return this.nil();
      } else if (value instanceof String) {
         return this.examine((String)value);
      } else if (value instanceof Examinable) {
         return this.examine((Examinable)value);
      } else if (value instanceof Collection) {
         return this.collection((Collection)value);
      } else if (value instanceof Map) {
         return this.map((Map)value);
      } else if (value.getClass().isArray()) {
         Class type = value.getClass().getComponentType();
         if (type.isPrimitive()) {
            if (type == Boolean.TYPE) {
               return this.examine((boolean[])value);
            }

            if (type == Byte.TYPE) {
               return this.examine((byte[])value);
            }

            if (type == Character.TYPE) {
               return this.examine((char[])value);
            }

            if (type == Double.TYPE) {
               return this.examine((double[])value);
            }

            if (type == Float.TYPE) {
               return this.examine((float[])value);
            }

            if (type == Integer.TYPE) {
               return this.examine((int[])value);
            }

            if (type == Long.TYPE) {
               return this.examine((long[])value);
            }

            if (type == Short.TYPE) {
               return this.examine((short[])value);
            }
         }

         return this.array((Object[])value);
      } else if (value instanceof Boolean) {
         return this.examine((Boolean)value);
      } else if (value instanceof Character) {
         return this.examine((Character)value);
      } else {
         if (value instanceof Number) {
            if (value instanceof Byte) {
               return this.examine((Byte)value);
            }

            if (value instanceof Double) {
               return this.examine((Double)value);
            }

            if (value instanceof Float) {
               return this.examine((Float)value);
            }

            if (value instanceof Integer) {
               return this.examine((Integer)value);
            }

            if (value instanceof Long) {
               return this.examine((Long)value);
            }

            if (value instanceof Short) {
               return this.examine((Short)value);
            }
         } else if (value instanceof BaseStream) {
            if (value instanceof Stream) {
               return this.stream((Stream)value);
            }

            if (value instanceof DoubleStream) {
               return this.stream((DoubleStream)value);
            }

            if (value instanceof IntStream) {
               return this.stream((IntStream)value);
            }

            if (value instanceof LongStream) {
               return this.stream((LongStream)value);
            }
         }

         return this.scalar(value);
      }
   }

   @NonNull
   private Object array(final Object[] array) {
      return this.array(array, Arrays.stream(array).map(this::examine));
   }

   @NonNull
   protected abstract Object array(final Object[] array, @NonNull final Stream elements);

   @NonNull
   private Object collection(@NonNull final Collection collection) {
      return this.collection(collection, collection.stream().map(this::examine));
   }

   @NonNull
   protected abstract Object collection(@NonNull final Collection collection, @NonNull final Stream elements);

   @NonNull
   public Object examine(@NonNull final String name, @NonNull final Stream properties) {
      return this.examinable(name, properties.map((property) -> {
         return new SimpleImmutableEntry(property.name(), property.examine(this));
      }));
   }

   @NonNull
   protected abstract Object examinable(@NonNull final String name, @NonNull final Stream properties);

   @NonNull
   private Object map(@NonNull final Map map) {
      return this.map(map, map.entrySet().stream().map((entry) -> {
         return new SimpleImmutableEntry(this.examine(entry.getKey()), this.examine(entry.getValue()));
      }));
   }

   @NonNull
   protected abstract Object map(@NonNull final Map map, @NonNull final Stream entries);

   @NonNull
   protected abstract Object nil();

   @NonNull
   protected abstract Object scalar(@NonNull final Object value);

   @NonNull
   protected abstract Object stream(@NonNull final Stream stream);

   @NonNull
   protected abstract Object stream(@NonNull final DoubleStream stream);

   @NonNull
   protected abstract Object stream(@NonNull final IntStream stream);

   @NonNull
   protected abstract Object stream(@NonNull final LongStream stream);
}
