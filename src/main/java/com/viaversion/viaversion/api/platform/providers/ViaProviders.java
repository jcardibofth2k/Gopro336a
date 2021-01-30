package com.viaversion.viaversion.api.platform.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ViaProviders {
   private final Map providers = new HashMap();
   private final List lonelyProviders = new ArrayList();

   public void require(Class provider) {
      this.lonelyProviders.add(provider);
   }

   public void register(Class provider, Provider value) {
      this.providers.put(provider, value);
   }

   public void use(Class provider, Provider value) {
      this.lonelyProviders.remove(provider);
      this.providers.put(provider, value);
   }

   @Nullable
   public Provider get(Class provider) {
      Provider rawProvider = (Provider)this.providers.get(provider);
      if (rawProvider != null) {
         return rawProvider;
      } else if (this.lonelyProviders.contains(provider)) {
         throw new IllegalStateException("There was no provider for " + provider + ", one is required!");
      } else {
         return null;
      }
   }
}
