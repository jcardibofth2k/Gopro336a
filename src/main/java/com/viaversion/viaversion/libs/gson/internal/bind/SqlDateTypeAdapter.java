package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class SqlDateTypeAdapter extends TypeAdapter {
   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken typeToken) {
         return typeToken.getRawType() == Date.class ? new SqlDateTypeAdapter() : null;
      }
   };
   private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");

   public synchronized Date read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         try {
            long utilDate = this.format.parse(in.nextString()).getTime();
            return new Date(utilDate);
         } catch (ParseException var4) {
            throw new JsonSyntaxException(var4);
         }
      }
   }

   public synchronized void write(JsonWriter out, Date value) throws IOException {
      out.value(value == null ? null : this.format.format(value));
   }
}
