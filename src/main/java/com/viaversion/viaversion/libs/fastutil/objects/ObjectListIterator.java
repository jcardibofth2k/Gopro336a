package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.ListIterator;

public interface ObjectListIterator extends ObjectBidirectionalIterator, ListIterator {
   default void set(Object k) {
      throw new UnsupportedOperationException();
   }

   default void add(Object k) {
      throw new UnsupportedOperationException();
   }

   default void remove() {
      throw new UnsupportedOperationException();
   }
}
