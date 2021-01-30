package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EntityNBTComponentImpl$BuilderImpl extends NBTComponentImpl.BuilderImpl implements EntityNBTComponent.Builder {
   @Nullable
   private String selector;

   EntityNBTComponentImpl$BuilderImpl() {
   }

   EntityNBTComponentImpl$BuilderImpl(@NotNull final EntityNBTComponent component) {
      super(component);
      this.selector = component.selector();
   }

   @NotNull
   public EntityNBTComponent.Builder selector(@NotNull final String selector) {
      this.selector = selector;
      return this;
   }

   @NotNull
   public EntityNBTComponent build() {
      if (this.nbtPath == null) {
         throw new IllegalStateException("nbt path must be set");
      } else if (this.selector == null) {
         throw new IllegalStateException("selector must be set");
      } else {
         return new EntityNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.selector);
      }
   }
}
