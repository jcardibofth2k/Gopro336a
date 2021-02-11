package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringIO.1;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public final class TagStringIO {
   private static final TagStringIO INSTANCE = new TagStringIO(new TagStringIO.Builder());
   private final boolean acceptLegacy;
   private final boolean emitLegacy;
   private final String indent;

   @NotNull
   public static TagStringIO get() {
      return INSTANCE;
   }

   @NotNull
   public static TagStringIO.Builder builder() {
      return new TagStringIO.Builder();
   }

   private TagStringIO(@NotNull final TagStringIO.Builder builder) {
      this.acceptLegacy = builder.acceptLegacy;
      this.emitLegacy = builder.emitLegacy;
      this.indent = builder.indent;
   }

   public CompoundBinaryTag asCompound(final String input) throws IOException {
      try {
         CharBuffer buffer = new CharBuffer(input);
         TagStringReader parser = new TagStringReader(buffer);
         parser.legacy(this.acceptLegacy);
         CompoundBinaryTag tag = parser.compound();
         if (buffer.skipWhitespace().hasMore()) {
            throw new IOException("Document had trailing content after first CompoundTag");
         } else {
            return tag;
         }
      } catch (StringTagParseException var5) {
         throw new IOException(var5);
      }
   }

   public String asString(final CompoundBinaryTag input) throws IOException {
      StringBuilder sb = new StringBuilder();
      TagStringWriter emit = new TagStringWriter(sb, this.indent);

      try {
         emit.legacy(this.emitLegacy);
         emit.writeTag(input);
      } catch (Throwable var7) {
         try {
            emit.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
         }

         throw var7;
      }

      emit.close();
      return sb.toString();
   }

   public void toWriter(final CompoundBinaryTag input, final Writer dest) throws IOException {
      TagStringWriter emit = new TagStringWriter(dest, this.indent);

      try {
         emit.legacy(this.emitLegacy);
         emit.writeTag(input);
      } catch (Throwable var7) {
         try {
            emit.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
         }

         throw var7;
      }

      emit.close();
   }

   // $FF: synthetic method
   TagStringIO(TagStringIO.Builder x0, 1 x1) {
      this(x0);
   }

   public static class Builder {
      private boolean acceptLegacy = true;
      private boolean emitLegacy = false;
      private String indent = "";

      Builder() {
      }

      @NotNull
      public TagStringIO.Builder indent(final int spaces) {
         if (spaces == 0) {
            this.indent = "";
         } else if (this.indent.length() > 0 && this.indent.charAt(0) != ' ' || spaces != this.indent.length()) {
            char[] indent = new char[spaces];
            Arrays.fill(indent, ' ');
            this.indent = String.copyValueOf(indent);
         }

         return this;
      }

      @NotNull
      public TagStringIO.Builder indentTab(final int tabs) {
         if (tabs == 0) {
            this.indent = "";
         } else if (this.indent.length() > 0 && this.indent.charAt(0) != '\t' || tabs != this.indent.length()) {
            char[] indent = new char[tabs];
            Arrays.fill(indent, '\t');
            this.indent = String.copyValueOf(indent);
         }

         return this;
      }

      @NotNull
      public TagStringIO.Builder acceptLegacy(final boolean legacy) {
         this.acceptLegacy = legacy;
         return this;
      }

      @NotNull
      public TagStringIO.Builder emitLegacy(final boolean legacy) {
         this.emitLegacy = legacy;
         return this;
      }

      @NotNull
      public TagStringIO build() {
         return new TagStringIO(this, (1)null)
      }
   }
}
