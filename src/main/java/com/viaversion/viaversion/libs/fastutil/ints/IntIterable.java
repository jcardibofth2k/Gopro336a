package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface IntIterable extends Iterable {
   IntIterator iterator();

   default void forEach(IntConsumer action) {
      Objects.requireNonNull(action);
      IntIterator iterator = this.iterator();

      while(iterator.hasNext()) {
         action.accept(iterator.nextInt());
      }

   }

   /** @deprecated */
   @Deprecated
   default void forEach(Consumer action) {
      Objects.requireNonNull(action);
      this.forEach(action::accept);
   }
}
