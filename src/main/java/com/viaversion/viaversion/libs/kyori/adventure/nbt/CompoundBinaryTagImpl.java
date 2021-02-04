package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Debug.Renderer;

@Renderer(
   text = "\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"",
   childrenArray = "this.tags.entrySet().toArray()",
   hasChildren = "!this.tags.isEmpty()"
)
final class CompoundBinaryTagImpl extends AbstractBinaryTag implements CompoundBinaryTag {
   static final CompoundBinaryTag EMPTY = new CompoundBinaryTagImpl(Collections.emptyMap());
   private final Map tags;
   private final int hashCode;

   CompoundBinaryTagImpl(final Map tags) {
      this.tags = Collections.unmodifiableMap(tags);
      this.hashCode = tags.hashCode();
   }

   public boolean contains(@NotNull final String key, @NotNull final BinaryTagType type) {
      BinaryTag tag = (BinaryTag)this.tags.get(key);
      return tag != null && type.test(tag.type());
   }

   @NotNull
   public Set keySet() {
      return Collections.unmodifiableSet(this.tags.keySet());
   }

   @Nullable
   public BinaryTag get(final String key) {
      return (BinaryTag)this.tags.get(key);
   }

   @NotNull
   public CompoundBinaryTag put(@NotNull final String key, @NotNull final BinaryTag tag) {
      return this.edit((map) -> {
         map.put(key, tag);
      });
   }

   @NotNull
   public CompoundBinaryTag put(@NotNull final CompoundBinaryTag tag) {
      return this.edit((map) -> {
         Iterator var2 = tag.keySet().iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            map.put(key, tag.get(key));
         }

      });
   }

   @NotNull
   public CompoundBinaryTag put(@NotNull final Map tags) {
      return this.edit((map) -> {
         map.putAll(tags);
      });
   }

   @NotNull
   public CompoundBinaryTag remove(@NotNull final String key, @Nullable final Consumer removed) {
      return !this.tags.containsKey(key) ? this : this.edit((map) -> {
         BinaryTag tag = (BinaryTag)map.remove(key);
         if (removed != null) {
            removed.accept(tag);
         }

      });
   }

   public byte getByte(@NotNull final String key, final byte defaultValue) {
      return this.contains(key, BinaryTagTypes.BYTE) ? ((NumberBinaryTag)this.tags.get(key)).byteValue() : defaultValue;
   }

   public short getShort(@NotNull final String key, final short defaultValue) {
      return this.contains(key, BinaryTagTypes.SHORT) ? ((NumberBinaryTag)this.tags.get(key)).shortValue() : defaultValue;
   }

   public int getInt(@NotNull final String key, final int defaultValue) {
      return this.contains(key, BinaryTagTypes.INT) ? ((NumberBinaryTag)this.tags.get(key)).intValue() : defaultValue;
   }

   public long getLong(@NotNull final String key, final long defaultValue) {
      return this.contains(key, BinaryTagTypes.LONG) ? ((NumberBinaryTag)this.tags.get(key)).longValue() : defaultValue;
   }

   public float getFloat(@NotNull final String key, final float defaultValue) {
      return this.contains(key, BinaryTagTypes.FLOAT) ? ((NumberBinaryTag)this.tags.get(key)).floatValue() : defaultValue;
   }

   public double getDouble(@NotNull final String key, final double defaultValue) {
      return this.contains(key, BinaryTagTypes.DOUBLE) ? ((NumberBinaryTag)this.tags.get(key)).doubleValue() : defaultValue;
   }

   @NotNull
   public byte[] getByteArray(@NotNull final String key) {
      return this.contains(key, BinaryTagTypes.BYTE_ARRAY) ? ((ByteArrayBinaryTag)this.tags.get(key)).value() : new byte[0];
   }

   @NotNull
   public byte[] getByteArray(@NotNull final String key, @NotNull final byte[] defaultValue) {
      return this.contains(key, BinaryTagTypes.BYTE_ARRAY) ? ((ByteArrayBinaryTag)this.tags.get(key)).value() : defaultValue;
   }

   @NotNull
   public String getString(@NotNull final String key, @NotNull final String defaultValue) {
      return this.contains(key, BinaryTagTypes.STRING) ? ((StringBinaryTag)this.tags.get(key)).value() : defaultValue;
   }

   @NotNull
   public ListBinaryTag getList(@NotNull final String key, @NotNull final ListBinaryTag defaultValue) {
      return this.contains(key, BinaryTagTypes.LIST) ? (ListBinaryTag)this.tags.get(key) : defaultValue;
   }

   @NotNull
   public ListBinaryTag getList(@NotNull final String key, @NotNull final BinaryTagType expectedType, @NotNull final ListBinaryTag defaultValue) {
      if (this.contains(key, BinaryTagTypes.LIST)) {
         ListBinaryTag tag = (ListBinaryTag)this.tags.get(key);
         if (expectedType.test(tag.elementType())) {
            return tag;
         }
      }

      return defaultValue;
   }

   @NotNull
   public CompoundBinaryTag getCompound(@NotNull final String key, @NotNull final CompoundBinaryTag defaultValue) {
      return this.contains(key, BinaryTagTypes.COMPOUND) ? (CompoundBinaryTag)this.tags.get(key) : defaultValue;
   }

   @NotNull
   public int[] getIntArray(@NotNull final String key) {
      return this.contains(key, BinaryTagTypes.INT_ARRAY) ? ((IntArrayBinaryTag)this.tags.get(key)).value() : new int[0];
   }

   @NotNull
   public int[] getIntArray(@NotNull final String key, @NotNull final int[] defaultValue) {
      return this.contains(key, BinaryTagTypes.INT_ARRAY) ? ((IntArrayBinaryTag)this.tags.get(key)).value() : defaultValue;
   }

   @NotNull
   public long[] getLongArray(@NotNull final String key) {
      return this.contains(key, BinaryTagTypes.LONG_ARRAY) ? ((LongArrayBinaryTag)this.tags.get(key)).value() : new long[0];
   }

   @NotNull
   public long[] getLongArray(@NotNull final String key, @NotNull final long[] defaultValue) {
      return this.contains(key, BinaryTagTypes.LONG_ARRAY) ? ((LongArrayBinaryTag)this.tags.get(key)).value() : defaultValue;
   }

   private CompoundBinaryTag edit(final Consumer consumer) {
      Map tags = new HashMap(this.tags);
      consumer.accept(tags);
      return new CompoundBinaryTagImpl(tags);
   }

   public boolean equals(final Object that) {
      return this == that || that instanceof CompoundBinaryTagImpl && this.tags.equals(((CompoundBinaryTagImpl)that).tags);
   }

   public int hashCode() {
      return this.hashCode;
   }

   @NotNull
   public Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_53("tags", this.tags));
   }

   @NotNull
   public Iterator iterator() {
      return this.tags.entrySet().iterator();
   }

   public void forEach(@NotNull final Consumer action) {
      this.tags.entrySet().forEach(Objects.requireNonNull(action, "action"));
   }
}
