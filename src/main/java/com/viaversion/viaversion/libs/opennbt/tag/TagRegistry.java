package com.viaversion.viaversion.libs.opennbt.tag;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class TagRegistry {
   private static final Int2ObjectMap idToTag = new Int2ObjectOpenHashMap();
   private static final Object2IntMap tagToId = new Object2IntOpenHashMap();
   private static final Int2ObjectMap instanceSuppliers = new Int2ObjectOpenHashMap();

   public static void register(int id, Class tag, Supplier supplier) throws TagRegisterException {
      if (idToTag.containsKey(id)) {
         throw new TagRegisterException("Tag ID \"" + id + "\" is already in use.");
      } else if (tagToId.containsKey(tag)) {
         throw new TagRegisterException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
      } else {
         instanceSuppliers.put(id, supplier);
         idToTag.put(id, tag);
         tagToId.put(tag, id);
      }
   }

   public static void unregister(int id) {
      tagToId.removeInt(getClassFor(id));
      idToTag.remove(id);
   }

   @Nullable
   public static Class getClassFor(int id) {
      return (Class)idToTag.get(id);
   }

   public static int getIdFor(Class clazz) {
      return tagToId.getInt(clazz);
   }

   public static Tag createInstance(int id) throws TagCreateException {
      Supplier supplier = (Supplier)instanceSuppliers.get(id);
      if (supplier == null) {
         throw new TagCreateException("Could not find tag with ID \"" + id + "\".");
      } else {
         return (Tag)supplier.get();
      }
   }

   static {
      tagToId.defaultReturnValue(-1);
      register(1, ByteTag.class, ByteTag::new);
      register(2, ShortTag.class, ShortTag::new);
      register(3, IntTag.class, IntTag::new);
      register(4, LongTag.class, LongTag::new);
      register(5, FloatTag.class, FloatTag::new);
      register(6, DoubleTag.class, DoubleTag::new);
      register(7, ByteArrayTag.class, ByteArrayTag::new);
      register(8, StringTag.class, StringTag::new);
      register(9, ListTag.class, ListTag::new);
      register(10, CompoundTag.class, CompoundTag::new);
      register(11, IntArrayTag.class, IntArrayTag::new);
      register(12, LongArrayTag.class, LongArrayTag::new);
   }
}
