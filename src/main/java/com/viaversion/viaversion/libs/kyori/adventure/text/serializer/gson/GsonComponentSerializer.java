package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface GsonComponentSerializer extends ComponentSerializer, Buildable {
   @NotNull
   static GsonComponentSerializer gson() {
      return GsonComponentSerializerImpl.Instances.INSTANCE;
   }

   @NotNull
   static GsonComponentSerializer colorDownsamplingGson() {
      return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
   }

   static GsonComponentSerializer.Builder builder() {
      return new GsonComponentSerializerImpl.BuilderImpl();
   }

   @NotNull
   Gson serializer();

   @NotNull
   UnaryOperator populator();

   @NotNull
   Component deserializeFromTree(@NotNull final JsonElement input);

   @NotNull
   JsonElement serializeToTree(@NotNull final Component component);

   @Internal
   public interface Provider {
      @Internal
      @NotNull
      GsonComponentSerializer gson();

      @Internal
      @NotNull
      GsonComponentSerializer gsonLegacy();

      @Internal
      @NotNull
      Consumer builder();
   }

   public interface Builder extends Buildable.Builder {
      @NotNull
      GsonComponentSerializer.Builder downsampleColors();

      @NotNull
      GsonComponentSerializer.Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer);

      @NotNull
      GsonComponentSerializer.Builder emitLegacyHoverEvent();

      @NotNull
      GsonComponentSerializer build();
   }
}
