package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class MonkeyBars {
   private MonkeyBars() {
   }

   @SafeVarargs
   @NotNull
   public static Set enumSet(final Class type, @NotNull final Enum... constants) {
      Set set = EnumSet.noneOf(type);
      Collections.addAll(set, constants);
      return Collections.unmodifiableSet(set);
   }

   @NotNull
   public static List addOne(@NotNull final List oldList, final Object newElement) {
      if (oldList.isEmpty()) {
         return Collections.singletonList(newElement);
      } else {
         List newList = new ArrayList(oldList.size() + 1);
         newList.addAll(oldList);
         newList.add(newElement);
         return Collections.unmodifiableList(newList);
      }
   }
}
