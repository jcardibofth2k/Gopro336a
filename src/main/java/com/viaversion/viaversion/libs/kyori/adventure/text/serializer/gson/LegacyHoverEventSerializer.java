package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface LegacyHoverEventSerializer {
   @NotNull
   HoverEvent.ShowItem deserializeShowItem(@NotNull final Component input) throws IOException;

   @NotNull
   HoverEvent.ShowEntity deserializeShowEntity(@NotNull final Component input, final Codec.Decoder componentDecoder) throws IOException;

   @NotNull
   Component serializeShowItem(@NotNull final HoverEvent.ShowItem input) throws IOException;

   @NotNull
   Component serializeShowEntity(@NotNull final HoverEvent.ShowEntity input, final Codec.Encoder componentEncoder) throws IOException;
}
