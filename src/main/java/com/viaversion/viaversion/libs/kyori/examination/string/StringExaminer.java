package com.viaversion.viaversion.libs.kyori.examination.string;

import com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StringExaminer extends AbstractExaminer {
   private static final Function DEFAULT_ESCAPER = (string) -> {
      return string.replace("\"", "\\\"").replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
   };
   private static final Collector COMMA_CURLY = Collectors.joining(", ", "{", "}");
   private static final Collector COMMA_SQUARE = Collectors.joining(", ", "[", "]");
   private final Function escaper;

   @NonNull
   public static StringExaminer simpleEscaping() {
      return com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer.Instances.SIMPLE_ESCAPING;
   }

   public StringExaminer(@NonNull final Function escaper) {
      this.escaper = escaper;
   }

   @NonNull
   protected String array(final Object[] array, @NonNull final Stream elements) {
      return (String)elements.collect(COMMA_SQUARE);
   }

   @NonNull
   protected String collection(@NonNull final Collection collection, @NonNull final Stream elements) {
      return (String)elements.collect(COMMA_SQUARE);
   }

   @NonNull
   protected String examinable(@NonNull final String name, @NonNull final Stream properties) {
      return name + (String)properties.map((property) -> {
         return (String)property.getKey() + '=' + (String)property.getValue();
      }).collect(COMMA_CURLY);
   }

   @NonNull
   protected String map(@NonNull final Map map, @NonNull final Stream entries) {
      return (String)entries.map((entry) -> {
         return (String)entry.getKey() + '=' + (String)entry.getValue();
      }).collect(COMMA_CURLY);
   }

   @NonNull
   protected String nil() {
      return "null";
   }

   @NonNull
   protected String scalar(@NonNull final Object value) {
      return String.valueOf(value);
   }

   @NonNull
   public String examine(final boolean value) {
      return String.valueOf(value);
   }

   @NonNull
   public String examine(@Nullable final boolean[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final byte value) {
      return String.valueOf(value);
   }

   @NonNull
   public String examine(@Nullable final byte[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final char value) {
      return '\'' + (String)this.escaper.apply(String.valueOf(value)) + '\'';
   }

   @NonNull
   public String examine(@Nullable final char[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final double value) {
      return withSuffix(String.valueOf(value), 'd');
   }

   @NonNull
   public String examine(@Nullable final double[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final float value) {
      return withSuffix(String.valueOf(value), 'f');
   }

   @NonNull
   public String examine(@Nullable final float[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final int value) {
      return String.valueOf(value);
   }

   @NonNull
   public String examine(@Nullable final int[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final long value) {
      return String.valueOf(value);
   }

   @NonNull
   public String examine(@Nullable final long[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   public String examine(final short value) {
      return String.valueOf(value);
   }

   @NonNull
   public String examine(@Nullable final short[] values) {
      return values == null ? this.nil() : array(values.length, (index) -> {
         return this.examine(values[index]);
      });
   }

   @NonNull
   protected String stream(@NonNull final Stream stream) {
      return (String)stream.map(this::examine).collect(COMMA_SQUARE);
   }

   @NonNull
   protected String stream(@NonNull final DoubleStream stream) {
      return (String)stream.mapToObj(this::examine).collect(COMMA_SQUARE);
   }

   @NonNull
   protected String stream(@NonNull final IntStream stream) {
      return (String)stream.mapToObj(this::examine).collect(COMMA_SQUARE);
   }

   @NonNull
   protected String stream(@NonNull final LongStream stream) {
      return (String)stream.mapToObj(this::examine).collect(COMMA_SQUARE);
   }

   @NonNull
   public String examine(@Nullable final String value) {
      return value == null ? this.nil() : '"' + (String)this.escaper.apply(value) + '"';
   }

   @NonNull
   private static String withSuffix(final String string, final char suffix) {
      return string + suffix;
   }

   @NonNull
   private static String array(final int length, final IntFunction value) {
      StringBuilder sb = new StringBuilder();
      sb.append('[');

      for(int i = 0; i < length; ++i) {
         sb.append((String)value.apply(i));
         if (i + 1 < length) {
            sb.append(", ");
         }
      }

      sb.append(']');
      return sb.toString();
   }

   // $FF: synthetic method
   static Function access$000() {
      return DEFAULT_ESCAPER;
   }
}
