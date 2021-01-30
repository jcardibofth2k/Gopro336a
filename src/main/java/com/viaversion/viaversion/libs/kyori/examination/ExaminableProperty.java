package com.viaversion.viaversion.libs.kyori.examination;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.1;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.10;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.11;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.12;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.13;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.14;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.15;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.16;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.17;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.18;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.2;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.3;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.4;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.5;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.6;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.7;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.8;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.9;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ExaminableProperty {
   private ExaminableProperty() {
   }

   @NonNull
   public abstract String name();

   @NonNull
   public abstract Object examine(@NonNull final Examiner examiner);

   public String toString() {
      return "ExaminableProperty{" + this.name() + "}";
   }

   // $FF: renamed from: of (java.lang.String, java.lang.Object) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_53(@NonNull final String name, @Nullable final Object value) {
      return new 1(name, value);
   }

   // $FF: renamed from: of (java.lang.String, java.lang.String) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_54(@NonNull final String name, @Nullable final String value) {
      return new 2(name, value);
   }

   // $FF: renamed from: of (java.lang.String, boolean) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_55(@NonNull final String name, final boolean value) {
      return new 3(name, value);
   }

   // $FF: renamed from: of (java.lang.String, boolean[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_56(@NonNull final String name, final boolean[] value) {
      return new 4(name, value);
   }

   // $FF: renamed from: of (java.lang.String, byte) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_57(@NonNull final String name, final byte value) {
      return new 5(name, value);
   }

   // $FF: renamed from: of (java.lang.String, byte[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_58(@NonNull final String name, final byte[] value) {
      return new 6(name, value);
   }

   // $FF: renamed from: of (java.lang.String, char) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_59(@NonNull final String name, final char value) {
      return new 7(name, value);
   }

   // $FF: renamed from: of (java.lang.String, char[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_60(@NonNull final String name, final char[] value) {
      return new 8(name, value);
   }

   // $FF: renamed from: of (java.lang.String, double) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_61(@NonNull final String name, final double value) {
      return new 9(name, value);
   }

   // $FF: renamed from: of (java.lang.String, double[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_62(@NonNull final String name, final double[] value) {
      return new 10(name, value);
   }

   // $FF: renamed from: of (java.lang.String, float) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_63(@NonNull final String name, final float value) {
      return new 11(name, value);
   }

   // $FF: renamed from: of (java.lang.String, float[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_64(@NonNull final String name, final float[] value) {
      return new 12(name, value);
   }

   // $FF: renamed from: of (java.lang.String, int) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_65(@NonNull final String name, final int value) {
      return new 13(name, value);
   }

   // $FF: renamed from: of (java.lang.String, int[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_66(@NonNull final String name, final int[] value) {
      return new 14(name, value);
   }

   // $FF: renamed from: of (java.lang.String, long) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_67(@NonNull final String name, final long value) {
      return new 15(name, value);
   }

   // $FF: renamed from: of (java.lang.String, long[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_68(@NonNull final String name, final long[] value) {
      return new 16(name, value);
   }

   // $FF: renamed from: of (java.lang.String, short) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_69(@NonNull final String name, final short value) {
      return new 17(name, value);
   }

   // $FF: renamed from: of (java.lang.String, short[]) com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
   @NonNull
   public static ExaminableProperty method_70(@NonNull final String name, final short[] value) {
      return new 18(name, value);
   }

   // $FF: synthetic method
   ExaminableProperty(1 x0) {
      this();
   }
}
