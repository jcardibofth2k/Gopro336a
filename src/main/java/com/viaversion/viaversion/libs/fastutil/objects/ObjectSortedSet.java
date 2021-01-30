package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.SortedSet;

public interface ObjectSortedSet extends ObjectSet, SortedSet, ObjectBidirectionalIterable {
   ObjectBidirectionalIterator iterator(Object var1);

   ObjectBidirectionalIterator iterator();

   ObjectSortedSet subSet(Object var1, Object var2);

   ObjectSortedSet headSet(Object var1);

   ObjectSortedSet tailSet(Object var1);
}
