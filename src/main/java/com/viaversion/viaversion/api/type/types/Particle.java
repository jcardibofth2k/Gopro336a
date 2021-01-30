package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import java.util.LinkedList;
import java.util.List;

public class Particle {
   // $FF: renamed from: id int
   private int field_3152;
   private List arguments = new LinkedList();

   public Particle(int id) {
      this.field_3152 = id;
   }

   public int getId() {
      return this.field_3152;
   }

   public void setId(int id) {
      this.field_3152 = id;
   }

   public List getArguments() {
      return this.arguments;
   }

   public void setArguments(List arguments) {
      this.arguments = arguments;
   }

   public static class ParticleData {
      private Type type;
      private Object value;

      public ParticleData(Type type, Object value) {
         this.type = type;
         this.value = value;
      }

      public Type getType() {
         return this.type;
      }

      public void setType(Type type) {
         this.type = type;
      }

      public Object getValue() {
         return this.value;
      }

      public Object get() {
         return this.value;
      }

      public void setValue(Object value) {
         this.value = value;
      }
   }
}
