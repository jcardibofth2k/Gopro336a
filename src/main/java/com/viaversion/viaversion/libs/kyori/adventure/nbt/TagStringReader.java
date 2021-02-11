package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag.Builder;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

final class TagStringReader {
   private static final int MAX_DEPTH = 512;
   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   private static final long[] EMPTY_LONG_ARRAY = new long[0];
   private final CharBuffer buffer;
   private boolean acceptLegacy;
   private int depth;

   TagStringReader(final CharBuffer buffer) {
      this.buffer = buffer;
   }

   public CompoundBinaryTag compound() throws StringTagParseException {
      this.buffer.expect('{');
      if (this.buffer.takeIf('}')) {
         return CompoundBinaryTag.empty();
      } else {
         CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();

         do {
            if (!this.buffer.hasMore()) {
               throw this.buffer.makeError("Unterminated compound tag!");
            }

            builder.put(this.key(), this.tag());
         } while(!this.separatorOrCompleteWith('}'));

         return builder.build();
      }
   }

   public ListBinaryTag list() throws StringTagParseException {
      Builder builder = ListBinaryTag.builder();
      this.buffer.expect('[');
      boolean prefixedIndex = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
      if (!prefixedIndex && this.buffer.takeIf(']')) {
         return ListBinaryTag.empty();
      } else {
         do {
            if (!this.buffer.hasMore()) {
               throw this.buffer.makeError("Reached end of file without end of list tag!");
            }

            if (prefixedIndex) {
               this.buffer.takeUntil(':');
            }

            BinaryTag next = this.tag();
            builder.add(next);
         } while(!this.separatorOrCompleteWith(']'));

         return builder.build();
      }
   }

   public BinaryTag array(char elementType) throws StringTagParseException {
      this.buffer.expect('[').expect(elementType).expect(';');
      elementType = Character.toLowerCase(elementType);
      if (elementType == 'b') {
         return ByteArrayBinaryTag.method_12(this.byteArray());
      } else if (elementType == 'i') {
         return IntArrayBinaryTag.method_13(this.intArray());
      } else if (elementType == 'l') {
         return LongArrayBinaryTag.method_11(this.longArray());
      } else {
         throw this.buffer.makeError("Type " + elementType + " is not a valid element type in an array!");
      }
   }

   private byte[] byteArray() throws StringTagParseException {
      if (this.buffer.takeIf(']')) {
         return EMPTY_BYTE_ARRAY;
      } else {
         ArrayList bytes = new ArrayList();

         while(this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('b');

            try {
               bytes.add(Byte.valueOf(value.toString()));
            } catch (NumberFormatException var5) {
               throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }

            if (this.separatorOrCompleteWith(']')) {
               byte[] result = new byte[bytes.size()];

               for(int i = 0; i < bytes.size(); ++i) {
                  result[i] = (Byte)bytes.get(i);
               }

               return result;
            }
         }

         throw this.buffer.makeError("Reached end of document without array close");
      }
   }

   private int[] intArray() throws StringTagParseException {
      if (this.buffer.takeIf(']')) {
         return EMPTY_INT_ARRAY;
      } else {
         java.util.stream.IntStream.Builder builder = IntStream.builder();

         do {
            if (!this.buffer.hasMore()) {
               throw this.buffer.makeError("Reached end of document without array close");
            }

            BinaryTag value = this.tag();
            if (!(value instanceof IntBinaryTag)) {
               throw this.buffer.makeError("All elements of an int array must be ints!");
            }

            builder.add(((IntBinaryTag)value).intValue());
         } while(!this.separatorOrCompleteWith(']'));

         return builder.build().toArray();
      }
   }

   private long[] longArray() throws StringTagParseException {
      if (this.buffer.takeIf(']')) {
         return EMPTY_LONG_ARRAY;
      } else {
         java.util.stream.LongStream.Builder longs = LongStream.builder();

         while(this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('l');

            try {
               longs.add(Long.parseLong(value.toString()));
            } catch (NumberFormatException var4) {
               throw this.buffer.makeError("All elements of a long array must be longs!");
            }

            if (this.separatorOrCompleteWith(']')) {
               return longs.build().toArray();
            }
         }

         throw this.buffer.makeError("Reached end of document without array close");
      }
   }

   public String key() throws StringTagParseException {
      this.buffer.skipWhitespace();
      char starChar = this.buffer.peek();

      try {
         if (starChar == '\'' || starChar == '"') {
            String var7 = unescape(this.buffer.takeUntil(this.buffer.take()).toString());
            return var7;
         } else {
            StringBuilder builder = new StringBuilder();

            while(true) {
               if (this.buffer.hasMore()) {
                  char peek = this.buffer.peek();
                  if (Tokens.id(peek)) {
                     builder.append(this.buffer.take());
                     continue;
                  }

                  if (this.acceptLegacy) {
                     if (peek == '\\') {
                        this.buffer.take();
                        continue;
                     }

                     if (peek != ':') {
                        builder.append(this.buffer.take());
                        continue;
                     }
                  }
               }

               String var8 = builder.toString();
               return var8;
            }
         }
      } finally {
         this.buffer.expect(':');
      }
   }

   public BinaryTag tag() throws StringTagParseException {
      if (this.depth++ > 512) {
         throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
      } else {
         try {
            char startToken = this.buffer.skipWhitespace().peek();
            BinaryTag var7;
            switch(startToken) {
            case '"':
            case '\'':
               this.buffer.advance();
               StringBinaryTag var8 = StringBinaryTag.method_10(unescape(this.buffer.takeUntil(startToken).toString()));
               return var8;
            case '[':
               if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
                  var7 = this.array(this.buffer.peek(1));
                  return var7;
               }

               ListBinaryTag var6 = this.list();
               return var6;
            case '{':
               CompoundBinaryTag var2 = this.compound();
               return var2;
            default:
               var7 = this.scalar();
               return var7;
            }
         } finally {
            --this.depth;
         }
      }
   }

   private BinaryTag scalar() {
      StringBuilder builder = new StringBuilder();
      boolean possiblyNumeric = true;

      while(this.buffer.hasMore()) {
         char current = this.buffer.peek();
         if (possiblyNumeric && !Tokens.numeric(current) && builder.length() != 0) {
            Object result = null;

            try {
               switch(Character.toLowerCase(current)) {
               case 'b':
                  result = ByteBinaryTag.method_14(Byte.parseByte(builder.toString()));
                  break;
               case 'd':
                  result = DoubleBinaryTag.method_16(Double.parseDouble(builder.toString()));
                  break;
               case 'f':
                  result = FloatBinaryTag.method_18(Float.parseFloat(builder.toString()));
                  break;
               case 'l':
                  result = LongBinaryTag.method_17(Long.parseLong(builder.toString()));
                  break;
               case 's':
                  result = ShortBinaryTag.method_19(Short.parseShort(builder.toString()));
               }
            } catch (NumberFormatException var6) {
               possiblyNumeric = false;
            }

            if (result != null) {
               this.buffer.take();
               return (BinaryTag)result;
            }
         }

         if (current == '\\') {
            this.buffer.advance();
            builder.append(this.buffer.take());
         } else {
            if (!Tokens.id(current)) {
               break;
            }

            builder.append(this.buffer.take());
         }
      }

      String built = builder.toString();
      if (possiblyNumeric) {
         try {
            return IntBinaryTag.method_15(Integer.parseInt(built));
         } catch (NumberFormatException var8) {
            try {
               return DoubleBinaryTag.method_16(Double.parseDouble(built));
            } catch (NumberFormatException var7) {
            }
         }
      }

      if (built.equalsIgnoreCase("true")) {
         return ByteBinaryTag.ONE;
      } else {
         return built.equalsIgnoreCase("false") ? ByteBinaryTag.ZERO : StringBinaryTag.method_10(built);
      }
   }

   private boolean separatorOrCompleteWith(final char endCharacter) throws StringTagParseException {
      if (this.buffer.takeIf(endCharacter)) {
         return true;
      } else {
         this.buffer.expect(',');
         return false;
      }
   }

   private static String unescape(final String withEscapes) {
      int escapeIdx = withEscapes.indexOf(92);
      if (escapeIdx == -1) {
         return withEscapes;
      } else {
         int lastEscape = 0;
         StringBuilder output = new StringBuilder(withEscapes.length());

         do {
            output.append(withEscapes, lastEscape, escapeIdx);
            lastEscape = escapeIdx + 1;
         } while((escapeIdx = withEscapes.indexOf(92, lastEscape + 1)) != -1);

         output.append(withEscapes.substring(lastEscape));
         return output.toString();
      }
   }

   public void legacy(final boolean acceptLegacy) {
      this.acceptLegacy = acceptLegacy;
   }
}
