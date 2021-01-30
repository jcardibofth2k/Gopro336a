package com.viaversion.viaversion.libs.fastutil;

import java.util.Iterator;

public interface BidirectionalIterator extends Iterator {
   Object previous();

   boolean hasPrevious();
}
