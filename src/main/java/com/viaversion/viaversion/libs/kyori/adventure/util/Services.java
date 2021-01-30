package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import org.jetbrains.annotations.NotNull;

public final class Services {
   private static final boolean SERVICE_LOAD_FAILURES_ARE_FATAL = Boolean.parseBoolean(System.getProperty(String.join(".", "net", "kyori", "adventure", "serviceLoadFailuresAreFatal"), String.valueOf(true)));

   private Services() {
   }

   @NotNull
   public static Optional service(@NotNull final Class type) {
      ServiceLoader loader = Services0.loader(type);
      Iterator it = loader.iterator();

      while(true) {
         if (it.hasNext()) {
            Object instance;
            try {
               instance = it.next();
            } catch (Throwable var5) {
               if (!SERVICE_LOAD_FAILURES_ARE_FATAL) {
                  continue;
               }

               throw new IllegalStateException("Encountered an exception loading service " + type, var5);
            }

            if (it.hasNext()) {
               throw new IllegalStateException("Expected to find one service " + type + ", found multiple");
            }

            return Optional.of(instance);
         }

         return Optional.empty();
      }
   }
}
