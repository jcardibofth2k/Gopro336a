package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Collection;

public interface ObjectCollection extends Collection, ObjectIterable {
   ObjectIterator iterator();
}
