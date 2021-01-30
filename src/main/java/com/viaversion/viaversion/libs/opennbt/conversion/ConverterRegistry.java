package com.viaversion.viaversion.libs.opennbt.conversion;

import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ByteArrayTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ByteTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.CompoundTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.DoubleTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.FloatTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.IntArrayTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.IntTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ListTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.LongArrayTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.LongTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ShortTagConverter;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.StringTagConverter;
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
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConverterRegistry {
   private static final Map tagToConverter = new HashMap();
   private static final Map typeToConverter = new HashMap();

   public static void register(Class tag, Class type, TagConverter converter) throws ConverterRegisterException {
      if (tagToConverter.containsKey(tag)) {
         throw new ConverterRegisterException("Type conversion to tag " + tag.getName() + " is already registered.");
      } else if (typeToConverter.containsKey(type)) {
         throw new ConverterRegisterException("Tag conversion to type " + type.getName() + " is already registered.");
      } else {
         tagToConverter.put(tag, converter);
         typeToConverter.put(type, converter);
      }
   }

   public static void unregister(Class tag, Class type) {
      tagToConverter.remove(tag);
      typeToConverter.remove(type);
   }

   public static Object convertToValue(Tag tag) throws ConversionException {
      if (tag != null && tag.getValue() != null) {
         if (!tagToConverter.containsKey(tag.getClass())) {
            throw new ConversionException("Tag type " + tag.getClass().getName() + " has no converter.");
         } else {
            TagConverter converter = (TagConverter)tagToConverter.get(tag.getClass());
            return converter.convert(tag);
         }
      } else {
         return null;
      }
   }

   public static Tag convertToTag(Object value) throws ConversionException {
      if (value == null) {
         return null;
      } else {
         TagConverter converter = (TagConverter)typeToConverter.get(value.getClass());
         if (converter == null) {
            Iterator var2 = getAllClasses(value.getClass()).iterator();

            label31:
            while(true) {
               Class clazz;
               do {
                  if (!var2.hasNext()) {
                     break label31;
                  }

                  clazz = (Class)var2.next();
               } while(!typeToConverter.containsKey(clazz));

               try {
                  converter = (TagConverter)typeToConverter.get(clazz);
                  break;
               } catch (ClassCastException var5) {
               }
            }
         }

         if (converter == null) {
            throw new ConversionException("Value type " + value.getClass().getName() + " has no converter.");
         } else {
            return converter.convert(value);
         }
      }
   }

   private static Set getAllClasses(Class clazz) {
      Set ret = new LinkedHashSet();

      for(Class c = clazz; c != null; c = c.getSuperclass()) {
         ret.add(c);
         ret.addAll(getAllSuperInterfaces(c));
      }

      if (ret.contains(Serializable.class)) {
         ret.remove(Serializable.class);
         ret.add(Serializable.class);
      }

      return ret;
   }

   private static Set getAllSuperInterfaces(Class clazz) {
      Set ret = new HashSet();
      Class[] var2 = clazz.getInterfaces();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         ret.add(c);
         ret.addAll(getAllSuperInterfaces(c));
      }

      return ret;
   }

   static {
      register(ByteTag.class, Byte.class, new ByteTagConverter());
      register(ShortTag.class, Short.class, new ShortTagConverter());
      register(IntTag.class, Integer.class, new IntTagConverter());
      register(LongTag.class, Long.class, new LongTagConverter());
      register(FloatTag.class, Float.class, new FloatTagConverter());
      register(DoubleTag.class, Double.class, new DoubleTagConverter());
      register(ByteArrayTag.class, byte[].class, new ByteArrayTagConverter());
      register(StringTag.class, String.class, new StringTagConverter());
      register(ListTag.class, List.class, new ListTagConverter());
      register(CompoundTag.class, Map.class, new CompoundTagConverter());
      register(IntArrayTag.class, int[].class, new IntArrayTagConverter());
      register(LongArrayTag.class, long[].class, new LongArrayTagConverter());
   }
}
