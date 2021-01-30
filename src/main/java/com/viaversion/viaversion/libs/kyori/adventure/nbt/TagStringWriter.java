package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map.Entry;

final class TagStringWriter implements AutoCloseable {
   private final Appendable out;
   private final String indent;
   private int level;
   private boolean needsSeparator;
   private boolean legacy;

   TagStringWriter(final Appendable out, final String indent) {
      this.out = out;
      this.indent = indent;
   }

   public TagStringWriter legacy(final boolean legacy) {
      this.legacy = legacy;
      return this;
   }

   public TagStringWriter writeTag(final BinaryTag tag) throws IOException {
      BinaryTagType type = tag.type();
      if (type == BinaryTagTypes.COMPOUND) {
         return this.writeCompound((CompoundBinaryTag)tag);
      } else if (type == BinaryTagTypes.LIST) {
         return this.writeList((ListBinaryTag)tag);
      } else if (type == BinaryTagTypes.BYTE_ARRAY) {
         return this.writeByteArray((ByteArrayBinaryTag)tag);
      } else if (type == BinaryTagTypes.INT_ARRAY) {
         return this.writeIntArray((IntArrayBinaryTag)tag);
      } else if (type == BinaryTagTypes.LONG_ARRAY) {
         return this.writeLongArray((LongArrayBinaryTag)tag);
      } else if (type == BinaryTagTypes.STRING) {
         return this.value(((StringBinaryTag)tag).value(), '\u0000');
      } else if (type == BinaryTagTypes.BYTE) {
         return this.value(Byte.toString(((ByteBinaryTag)tag).value()), 'b');
      } else if (type == BinaryTagTypes.SHORT) {
         return this.value(Short.toString(((ShortBinaryTag)tag).value()), 's');
      } else if (type == BinaryTagTypes.INT) {
         return this.value(Integer.toString(((IntBinaryTag)tag).value()), 'i');
      } else if (type == BinaryTagTypes.LONG) {
         return this.value(Long.toString(((LongBinaryTag)tag).value()), Character.toUpperCase('l'));
      } else if (type == BinaryTagTypes.FLOAT) {
         return this.value(Float.toString(((FloatBinaryTag)tag).value()), 'f');
      } else if (type == BinaryTagTypes.DOUBLE) {
         return this.value(Double.toString(((DoubleBinaryTag)tag).value()), 'd');
      } else {
         throw new IOException("Unknown tag type: " + type);
      }
   }

   private TagStringWriter writeCompound(final CompoundBinaryTag tag) throws IOException {
      this.beginCompound();
      Iterator var2 = tag.iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         this.key((String)entry.getKey());
         this.writeTag((BinaryTag)entry.getValue());
      }

      this.endCompound();
      return this;
   }

   private TagStringWriter writeList(final ListBinaryTag tag) throws IOException {
      this.beginList();
      int idx = 0;
      boolean lineBreaks = this.prettyPrinting() && this.breakListElement(tag.elementType());

      BinaryTag el;
      for(Iterator var4 = tag.iterator(); var4.hasNext(); this.writeTag(el)) {
         el = (BinaryTag)var4.next();
         this.printAndResetSeparator(!lineBreaks);
         if (lineBreaks) {
            this.newlineIndent();
         }

         if (this.legacy) {
            this.out.append(String.valueOf(idx++));
            this.appendSeparator(':');
         }
      }

      this.endList(lineBreaks);
      return this;
   }

   private TagStringWriter writeByteArray(final ByteArrayBinaryTag tag) throws IOException {
      if (this.legacy) {
         throw new IOException("Legacy Mojangson only supports integer arrays!");
      } else {
         this.beginArray('b');
         char byteArrayType = Character.toUpperCase('b');
         byte[] value = ByteArrayBinaryTagImpl.value(tag);
         int i = 0;

         for(int length = value.length; i < length; ++i) {
            this.printAndResetSeparator(true);
            this.value(Byte.toString(value[i]), byteArrayType);
         }

         this.endArray();
         return this;
      }
   }

   private TagStringWriter writeIntArray(final IntArrayBinaryTag tag) throws IOException {
      if (this.legacy) {
         this.beginList();
      } else {
         this.beginArray('i');
      }

      int[] value = IntArrayBinaryTagImpl.value(tag);
      int i = 0;

      for(int length = value.length; i < length; ++i) {
         this.printAndResetSeparator(true);
         this.value(Integer.toString(value[i]), 'i');
      }

      this.endArray();
      return this;
   }

   private TagStringWriter writeLongArray(final LongArrayBinaryTag tag) throws IOException {
      if (this.legacy) {
         throw new IOException("Legacy Mojangson only supports integer arrays!");
      } else {
         this.beginArray('l');
         long[] value = LongArrayBinaryTagImpl.value(tag);
         int i = 0;

         for(int length = value.length; i < length; ++i) {
            this.printAndResetSeparator(true);
            this.value(Long.toString(value[i]), 'l');
         }

         this.endArray();
         return this;
      }
   }

   public TagStringWriter beginCompound() throws IOException {
      this.printAndResetSeparator(false);
      ++this.level;
      this.out.append('{');
      return this;
   }

   public TagStringWriter endCompound() throws IOException {
      --this.level;
      this.newlineIndent();
      this.out.append('}');
      this.needsSeparator = true;
      return this;
   }

   public TagStringWriter key(final String key) throws IOException {
      this.printAndResetSeparator(false);
      this.newlineIndent();
      this.writeMaybeQuoted(key, false);
      this.appendSeparator(':');
      return this;
   }

   public TagStringWriter value(final String value, final char valueType) throws IOException {
      if (valueType == 0) {
         this.writeMaybeQuoted(value, true);
      } else {
         this.out.append(value);
         if (valueType != 'i') {
            this.out.append(valueType);
         }
      }

      this.needsSeparator = true;
      return this;
   }

   public TagStringWriter beginList() throws IOException {
      this.printAndResetSeparator(false);
      ++this.level;
      this.out.append('[');
      return this;
   }

   public TagStringWriter endList(final boolean lineBreak) throws IOException {
      --this.level;
      if (lineBreak) {
         this.newlineIndent();
      }

      this.out.append(']');
      this.needsSeparator = true;
      return this;
   }

   private TagStringWriter beginArray(final char type) throws IOException {
      this.beginList().out.append(Character.toUpperCase(type)).append(';');
      if (this.prettyPrinting()) {
         this.out.append(' ');
      }

      return this;
   }

   private TagStringWriter endArray() throws IOException {
      return this.endList(false);
   }

   private void writeMaybeQuoted(final String content, boolean requireQuotes) throws IOException {
      if (!requireQuotes) {
         for(int i = 0; i < content.length(); ++i) {
            if (!Tokens.id(content.charAt(i))) {
               requireQuotes = true;
               break;
            }
         }
      }

      if (requireQuotes) {
         this.out.append('"');
         this.out.append(escape(content, '"'));
         this.out.append('"');
      } else {
         this.out.append(content);
      }

   }

   private static String escape(final String content, final char quoteChar) {
      StringBuilder output = new StringBuilder(content.length());

      for(int i = 0; i < content.length(); ++i) {
         char c = content.charAt(i);
         if (c == quoteChar || c == '\\') {
            output.append('\\');
         }

         output.append(c);
      }

      return output.toString();
   }

   private void printAndResetSeparator(final boolean pad) throws IOException {
      if (this.needsSeparator) {
         this.out.append(',');
         if (pad && this.prettyPrinting()) {
            this.out.append(' ');
         }

         this.needsSeparator = false;
      }

   }

   private boolean breakListElement(final BinaryTagType type) {
      return type == BinaryTagTypes.COMPOUND || type == BinaryTagTypes.LIST || type == BinaryTagTypes.BYTE_ARRAY || type == BinaryTagTypes.INT_ARRAY || type == BinaryTagTypes.LONG_ARRAY;
   }

   private boolean prettyPrinting() {
      return this.indent.length() > 0;
   }

   private void newlineIndent() throws IOException {
      if (this.prettyPrinting()) {
         this.out.append(Tokens.NEWLINE);

         for(int i = 0; i < this.level; ++i) {
            this.out.append(this.indent);
         }
      }

   }

   private Appendable appendSeparator(final char separatorChar) throws IOException {
      this.out.append(separatorChar);
      if (this.prettyPrinting()) {
         this.out.append(' ');
      }

      return this.out;
   }

   public void close() throws IOException {
      if (this.level != 0) {
         throw new IllegalStateException("Document finished with unbalanced start and end objects");
      } else {
         if (this.out instanceof Writer) {
            ((Writer)this.out).flush();
         }

      }
   }
}
