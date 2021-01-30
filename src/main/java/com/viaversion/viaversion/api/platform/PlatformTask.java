package com.viaversion.viaversion.api.platform;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface PlatformTask {
   @Nullable
   Object getObject();

   void cancel();
}
