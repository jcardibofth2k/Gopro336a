package com.viaversion.viaversion.libs.fastutil.objects;

public abstract class AbstractObjectSortedSet extends AbstractObjectSet implements ObjectSortedSet {
   protected AbstractObjectSortedSet() {
   }

   public abstract ObjectBidirectionalIterator iterator();
}
