package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class StorageNBTComponentImpl extends NBTComponentImpl implements StorageNBTComponent {
   private final Key storage;

   StorageNBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final Key storage) {
      super(children, style, nbtPath, interpret, separator);
      this.storage = storage;
   }

   @NotNull
   public StorageNBTComponent nbtPath(@NotNull final String nbtPath) {
      return Objects.equals(this.nbtPath, nbtPath) ? this : new StorageNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.storage);
   }

   @NotNull
   public StorageNBTComponent interpret(final boolean interpret) {
      return this.interpret == interpret ? this : new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.storage);
   }

   @Nullable
   public Component separator() {
      return this.separator;
   }

   @NotNull
   public StorageNBTComponent separator(@Nullable final ComponentLike separator) {
      return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.storage);
   }

   @NotNull
   public Key storage() {
      return this.storage;
   }

   @NotNull
   public StorageNBTComponent storage(@NotNull final Key storage) {
      return Objects.equals(this.storage, storage) ? this : new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, storage);
   }

   @NotNull
   public StorageNBTComponent children(@NotNull final List children) {
      return new StorageNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.storage);
   }

   @NotNull
   public StorageNBTComponent style(@NotNull final Style style) {
      return new StorageNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.storage);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof StorageNBTComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         StorageNBTComponentImpl that = (StorageNBTComponentImpl)other;
         return Objects.equals(this.storage, that.storage());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.storage.hashCode();
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_53("storage", this.storage)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public StorageNBTComponent.Builder toBuilder() {
      return new StorageNBTComponentImpl.BuilderImpl(this);
   }

   static class BuilderImpl extends NBTComponentImpl.BuilderImpl implements StorageNBTComponent.Builder {
      @Nullable
      private Key storage;

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final StorageNBTComponent component) {
         super(component);
         this.storage = component.storage();
      }

      @NotNull
      public StorageNBTComponent.Builder storage(@NotNull final Key storage) {
         this.storage = storage;
         return this;
      }

      @NotNull
      public StorageNBTComponent build() {
         if (this.nbtPath == null) {
            throw new IllegalStateException("nbt path must be set");
         } else if (this.storage == null) {
            throw new IllegalStateException("storage must be set");
         } else {
            return new StorageNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.storage);
         }
      }
   }
}
