package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

public interface Key extends Comparable, Examinable {
   String MINECRAFT_NAMESPACE = "minecraft";

   @NotNull
   static Key key(@NotNull @Pattern("([a-z0-9_\\-.]+:)?[a-z0-9_\\-./]+") final String string) {
      return key(string, ':');
   }

   @NotNull
   static Key key(@NotNull final String string, final char character) {
      int index = string.indexOf(character);
      String namespace = index >= 1 ? string.substring(0, index) : "minecraft";
      String value = index >= 0 ? string.substring(index + 1) : string;
      return key(namespace, value);
   }

   @NotNull
   static Key key(@NotNull final Namespaced namespaced, @NotNull @Pattern("[a-z0-9_\\-./]+") final String value) {
      return key(namespaced.namespace(), value);
   }

   @NotNull
   static Key key(@NotNull @Pattern("[a-z0-9_\\-.]+") final String namespace, @NotNull @Pattern("[a-z0-9_\\-./]+") final String value) {
      return new KeyImpl(namespace, value);
   }

   @NotNull
   String namespace();

   @NotNull
   String value();

   @NotNull
   String asString();

   @NotNull
   default Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_54("namespace", this.namespace()), ExaminableProperty.method_54("value", this.value()));
   }

   default int compareTo(@NotNull final Key that) {
      int value = this.value().compareTo(that.value());
      return value != 0 ? KeyImpl.clampCompare(value) : KeyImpl.clampCompare(this.namespace().compareTo(that.namespace()));
   }
}
