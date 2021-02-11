package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

final class HSVLikeImpl implements HSVLike {
   // $FF: renamed from: h float
   private final float field_84;
   // $FF: renamed from: s float
   private final float field_85;
   // $FF: renamed from: v float
   private final float field_86;

   HSVLikeImpl(final float h, final float s, final float v) {
      this.field_84 = h;
      this.field_85 = s;
      this.field_86 = v;
   }

   // $FF: renamed from: h () float
   public float method_1() {
      return this.field_84;
   }

   // $FF: renamed from: s () float
   public float method_2() {
      return this.field_85;
   }

   // $FF: renamed from: v () float
   public float method_3() {
      return this.field_86;
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof HSVLikeImpl)) {
         return false;
      } else {
         HSVLikeImpl that = (HSVLikeImpl)other;
         return ShadyPines.equals(that.field_84, this.field_84) && ShadyPines.equals(that.field_85, this.field_85) && ShadyPines.equals(that.field_86, this.field_86);
      }
   }

   public int hashCode() {
      return Objects.hash(this.field_84, this.field_85, this.field_86);
   }

   public String toString() {
      return (String)this.examine(StringExaminer.simpleEscaping());
   }
}
