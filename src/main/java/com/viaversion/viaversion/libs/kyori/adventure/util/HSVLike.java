package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface HSVLike extends Examinable {
   // $FF: renamed from: of (float, float, float) com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike
   @NotNull
   static HSVLike method_0(final float h, final float s, final float v) {
      return new HSVLikeImpl(h, s, v);
   }

   @NotNull
   static HSVLike fromRGB(@Range(from = 0L,to = 255L) final int red, @Range(from = 0L,to = 255L) final int green, @Range(from = 0L,to = 255L) final int blue) {
      float r = (float)red / 255.0F;
      float g = (float)green / 255.0F;
      float b = (float)blue / 255.0F;
      float min = Math.min(r, Math.min(g, b));
      float max = Math.max(r, Math.max(g, b));
      float delta = max - min;
      float s;
      if (max != 0.0F) {
         s = delta / max;
      } else {
         s = 0.0F;
      }

      if (s == 0.0F) {
         return new HSVLikeImpl(0.0F, s, max);
      } else {
         float h;
         if (r == max) {
            h = (g - b) / delta;
         } else if (g == max) {
            h = 2.0F + (b - r) / delta;
         } else {
            h = 4.0F + (r - g) / delta;
         }

         h *= 60.0F;
         if (h < 0.0F) {
            h += 360.0F;
         }

         return new HSVLikeImpl(h / 360.0F, s, max);
      }
   }

   // $FF: renamed from: h () float
   float method_1();

   // $FF: renamed from: s () float
   float method_2();

   // $FF: renamed from: v () float
   float method_3();

   @NotNull
   default Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_63("h", this.method_1()), ExaminableProperty.method_63("s", this.method_2()), ExaminableProperty.method_63("v", this.method_3()));
   }
}
