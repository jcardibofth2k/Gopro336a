package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BinaryTagIO {
   private BinaryTagIO() {
   }

   @NonNull
   public static CompoundTag readPath(@NonNull Path path) throws IOException {
      return readInputStream(Files.newInputStream(path));
   }

   @NonNull
   public static CompoundTag readInputStream(@NonNull InputStream input) throws IOException {
      DataInputStream dis = new DataInputStream(input);

      CompoundTag var2;
      try {
         var2 = readDataInput(dis);
      } catch (Throwable var5) {
         try {
            dis.close();
         } catch (Throwable var4) {
            var5.addSuppressed(var4);
         }

         throw var5;
      }

      dis.close();
      return var2;
   }

   @NonNull
   public static CompoundTag readCompressedPath(@NonNull Path path) throws IOException {
      return readCompressedInputStream(Files.newInputStream(path));
   }

   @NonNull
   public static CompoundTag readCompressedInputStream(@NonNull InputStream input) throws IOException {
      DataInputStream dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(input)));

      CompoundTag var2;
      try {
         var2 = readDataInput(dis);
      } catch (Throwable var5) {
         try {
            dis.close();
         } catch (Throwable var4) {
            var5.addSuppressed(var4);
         }

         throw var5;
      }

      dis.close();
      return var2;
   }

   @NonNull
   public static CompoundTag readDataInput(@NonNull DataInput input) throws IOException {
      byte type = input.readByte();
      if (type != 10) {
         throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", type));
      } else {
         input.skipBytes(input.readUnsignedShort());
         CompoundTag compoundTag = new CompoundTag();
         compoundTag.read(input);
         return compoundTag;
      }
   }

   public static void writePath(@NonNull CompoundTag tag, @NonNull Path path) throws IOException {
      writeOutputStream(tag, Files.newOutputStream(path));
   }

   public static void writeOutputStream(@NonNull CompoundTag tag, @NonNull OutputStream output) throws IOException {
      DataOutputStream dos = new DataOutputStream(output);

      try {
         writeDataOutput(tag, dos);
      } catch (Throwable var6) {
         try {
            dos.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      dos.close();
   }

   public static void writeCompressedPath(@NonNull CompoundTag tag, @NonNull Path path) throws IOException {
      writeCompressedOutputStream(tag, Files.newOutputStream(path));
   }

   public static void writeCompressedOutputStream(@NonNull CompoundTag tag, @NonNull OutputStream output) throws IOException {
      DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(output));

      try {
         writeDataOutput(tag, dos);
      } catch (Throwable var6) {
         try {
            dos.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      dos.close();
   }

   public static void writeDataOutput(@NonNull CompoundTag tag, @NonNull DataOutput output) throws IOException {
      output.writeByte(10);
      output.writeUTF("");
      tag.write(output);
   }

   @NonNull
   public static CompoundTag readString(@NonNull String input) throws IOException {
      try {
         CharBuffer buffer = new CharBuffer(input);
         TagStringReader parser = new TagStringReader(buffer);
         CompoundTag tag = parser.compound();
         if (buffer.skipWhitespace().hasMore()) {
            throw new IOException("Document had trailing content after first CompoundTag");
         } else {
            return tag;
         }
      } catch (StringTagParseException var4) {
         throw new IOException(var4);
      }
   }

   @NonNull
   public static String writeString(@NonNull CompoundTag tag) throws IOException {
      StringBuilder sb = new StringBuilder();
      TagStringWriter emit = new TagStringWriter(sb);

      try {
         emit.writeTag(tag);
      } catch (Throwable var6) {
         try {
            emit.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      emit.close();
      return sb.toString();
   }
}
