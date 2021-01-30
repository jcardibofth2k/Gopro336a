package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.internal.Streams.AppendableWriter.CurrentWrite;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;

public final class Streams {
   private Streams() {
      throw new UnsupportedOperationException();
   }

   public static JsonElement parse(JsonReader reader) throws JsonParseException {
      boolean isEmpty = true;

      try {
         reader.peek();
         isEmpty = false;
         return (JsonElement)TypeAdapters.JSON_ELEMENT.read(reader);
      } catch (EOFException var3) {
         if (isEmpty) {
            return JsonNull.INSTANCE;
         } else {
            throw new JsonSyntaxException(var3);
         }
      } catch (MalformedJsonException var4) {
         throw new JsonSyntaxException(var4);
      } catch (IOException var5) {
         throw new JsonIOException(var5);
      } catch (NumberFormatException var6) {
         throw new JsonSyntaxException(var6);
      }
   }

   public static void write(JsonElement element, JsonWriter writer) throws IOException {
      TypeAdapters.JSON_ELEMENT.write(writer, element);
   }

   public static Writer writerForAppendable(Appendable appendable) {
      return (Writer)(appendable instanceof Writer ? (Writer)appendable : new Streams.AppendableWriter(appendable));
   }

   private static final class AppendableWriter extends Writer {
      private final Appendable appendable;
      private final CurrentWrite currentWrite = new CurrentWrite();

      AppendableWriter(Appendable appendable) {
         this.appendable = appendable;
      }

      public void write(char[] chars, int offset, int length) throws IOException {
         this.currentWrite.chars = chars;
         this.appendable.append(this.currentWrite, offset, offset + length);
      }

      public void write(int i) throws IOException {
         this.appendable.append((char)i);
      }

      public void flush() {
      }

      public void close() {
      }
   }
}
