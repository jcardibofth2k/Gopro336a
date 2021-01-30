package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.ServiceLoader;

final class Services0 {
   private Services0() {
   }

   static ServiceLoader loader(final Class type) {
      return ServiceLoader.load(type, type.getClassLoader());
   }
}
