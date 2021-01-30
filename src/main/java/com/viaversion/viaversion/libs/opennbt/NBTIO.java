package com.viaversion.viaversion.libs.opennbt;

import com.viaversion.viaversion.libs.opennbt.NBTIO.1;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTIO {
   public static CompoundTag readFile(String path) throws IOException {
      return readFile(new File(path));
   }

   public static CompoundTag readFile(File file) throws IOException {
      return readFile(file, true, false);
   }

   public static CompoundTag readFile(String path, boolean compressed, boolean littleEndian) throws IOException {
      return readFile(new File(path), compressed, littleEndian);
   }

   public static CompoundTag readFile(File file, boolean compressed, boolean littleEndian) throws IOException {
      InputStream in = new FileInputStream(file);
      if (compressed) {
         in = new GZIPInputStream((InputStream)in);
      }

      Tag tag = readTag((InputStream)in, littleEndian);
      if (!(tag instanceof CompoundTag)) {
         throw new IOException("Root tag is not a CompoundTag!");
      } else {
         return (CompoundTag)tag;
      }
   }

   public static void writeFile(CompoundTag tag, String path) throws IOException {
      writeFile(tag, new File(path));
   }

   public static void writeFile(CompoundTag tag, File file) throws IOException {
      writeFile(tag, file, true, false);
   }

   public static void writeFile(CompoundTag tag, String path, boolean compressed, boolean littleEndian) throws IOException {
      writeFile(tag, new File(path), compressed, littleEndian);
   }

   public static void writeFile(CompoundTag tag, File file, boolean compressed, boolean littleEndian) throws IOException {
      if (!file.exists()) {
         if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
         }

         file.createNewFile();
      }

      OutputStream out = new FileOutputStream(file);
      if (compressed) {
         out = new GZIPOutputStream((OutputStream)out);
      }

      writeTag((OutputStream)out, tag, littleEndian);
      ((OutputStream)out).close();
   }

   public static CompoundTag readTag(InputStream in) throws IOException {
      return readTag(in, false);
   }

   public static CompoundTag readTag(InputStream in, boolean littleEndian) throws IOException {
      return readTag((DataInput)((DataInput)(littleEndian ? new com.viaversion.viaversion.libs.opennbt.NBTIO.LittleEndianDataInputStream(in, (1)null) : new DataInputStream(in))));
   }

   public static CompoundTag readTag(DataInput in) throws IOException {
      int id = in.readByte();
      if (id != 10) {
         throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", Integer.valueOf(id)));
      } else {
         in.skipBytes(in.readUnsignedShort());
         CompoundTag tag = new CompoundTag();
         tag.read(in);
         return tag;
      }
   }

   public static void writeTag(OutputStream out, CompoundTag tag) throws IOException {
      writeTag(out, tag, false);
   }

   public static void writeTag(OutputStream out, CompoundTag tag, boolean littleEndian) throws IOException {
      writeTag((DataOutput)((DataOutput)(littleEndian ? new com.viaversion.viaversion.libs.opennbt.NBTIO.LittleEndianDataOutputStream(out, (1)null) : new DataOutputStream(out))), tag);
   }

   public static void writeTag(DataOutput out, CompoundTag tag) throws IOException {
      out.writeByte(10);
      out.writeUTF("");
      tag.write(out);
   }
}
