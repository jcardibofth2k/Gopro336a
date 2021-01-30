package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class DateTypeAdapter extends TypeAdapter {
   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken typeToken) {
         return typeToken.getRawType() == Date.class ? new DateTypeAdapter() : null;
      }
   };
   private final List dateFormats = new ArrayList();

   public DateTypeAdapter() {
      this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
      }

      if (JavaVersion.isJava9OrLater()) {
         this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
      }

   }

   public Date read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         return this.deserializeToDate(in.nextString());
      }
   }

   private synchronized Date deserializeToDate(String json) {
      Iterator var2 = this.dateFormats.iterator();

      while(var2.hasNext()) {
         DateFormat dateFormat = (DateFormat)var2.next();

         try {
            return dateFormat.parse(json);
         } catch (ParseException var6) {
         }
      }

      try {
         return ISO8601Utils.parse(json, new ParsePosition(0));
      } catch (ParseException var5) {
         throw new JsonSyntaxException(json, var5);
      }
   }

   public synchronized void write(JsonWriter out, Date value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         String dateFormatAsString = ((DateFormat)this.dateFormats.get(0)).format(value);
         out.value(dateFormatAsString);
      }
   }
}
