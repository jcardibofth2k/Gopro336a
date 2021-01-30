package com.viaversion.viaversion.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class InformativeException extends Exception {
   private final Map info = new HashMap();
   private int sources;

   public InformativeException(Throwable cause) {
      super(cause);
   }

   public InformativeException set(String key, Object value) {
      this.info.put(key, value);
      return this;
   }

   public InformativeException addSource(Class sourceClazz) {
      return this.set("Source " + this.sources++, this.getSource(sourceClazz));
   }

   private String getSource(Class sourceClazz) {
      return sourceClazz.isAnonymousClass() ? sourceClazz.getName() + " (Anonymous)" : sourceClazz.getName();
   }

   public String getMessage() {
      StringBuilder builder = new StringBuilder();
      builder.append("Please post this error to https://github.com/ViaVersion/ViaVersion/issues and follow the issue template\n{");
      int i = 0;

      for(Iterator var3 = this.info.entrySet().iterator(); var3.hasNext(); ++i) {
         Entry entry = (Entry)var3.next();
         builder.append(i == 0 ? "" : ", ").append((String)entry.getKey()).append(": ").append(entry.getValue().toString());
      }

      builder.append("}\nActual Error: ");
      return builder.toString();
   }

   public synchronized Throwable fillInStackTrace() {
      return this;
   }
}
