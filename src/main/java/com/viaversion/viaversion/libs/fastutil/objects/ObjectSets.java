package com.viaversion.viaversion.libs.fastutil.objects;

public final class ObjectSets {
   public static final com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.EmptySet EMPTY_SET = new com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.EmptySet();

   private ObjectSets() {
   }

   public static ObjectSet emptySet() {
      return EMPTY_SET;
   }

   public static ObjectSet singleton(Object element) {
      return new com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.Singleton(element);
   }

   public static ObjectSet synchronize(ObjectSet s) {
      return new com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.SynchronizedSet(s);
   }

   public static ObjectSet synchronize(ObjectSet s, Object sync) {
      return new com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.SynchronizedSet(s, sync);
   }

   public static ObjectSet unmodifiable(ObjectSet s) {
      return new com.viaversion.viaversion.libs.fastutil.objects.ObjectSets.UnmodifiableSet(s);
   }
}
