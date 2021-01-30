package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;

public abstract class AbstractParticleType extends Type {
   protected final Int2ObjectMap readers = new Int2ObjectOpenHashMap();

   protected AbstractParticleType() {
      super("Particle", Particle.class);
   }

   public void write(ByteBuf buffer, Particle object) throws Exception {
      Type.VAR_INT.writePrimitive(buffer, object.getId());
      Iterator var3 = object.getArguments().iterator();

      while(var3.hasNext()) {
         Particle.ParticleData data = (Particle.ParticleData)var3.next();
         data.getType().write(buffer, data.getValue());
      }

   }

   public Particle read(ByteBuf buffer) throws Exception {
      int type = Type.VAR_INT.readPrimitive(buffer);
      Particle particle = new Particle(type);
      AbstractParticleType.ParticleReader reader = (AbstractParticleType.ParticleReader)this.readers.get(type);
      if (reader != null) {
         reader.read(buffer, particle);
      }

      return particle;
   }

   protected AbstractParticleType.ParticleReader blockHandler() {
      return (buf, particle) -> {
         particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf)));
      };
   }

   protected AbstractParticleType.ParticleReader itemHandler(Type itemType) {
      return (buf, particle) -> {
         particle.getArguments().add(new Particle.ParticleData(itemType, itemType.read(buf)));
      };
   }

   protected AbstractParticleType.ParticleReader dustHandler() {
      return (buf, particle) -> {
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
      };
   }

   protected AbstractParticleType.ParticleReader dustTransitionHandler() {
      return (buf, particle) -> {
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buf)));
      };
   }

   protected AbstractParticleType.ParticleReader vibrationHandler(Type positionType) {
      return (buf, particle) -> {
         particle.getArguments().add(new Particle.ParticleData(positionType, positionType.read(buf)));
         String resourceLocation = (String)Type.STRING.read(buf);
         if (resourceLocation.equals("block")) {
            particle.getArguments().add(new Particle.ParticleData(positionType, positionType.read(buf)));
         } else if (resourceLocation.equals("entity")) {
            particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf)));
         } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
         }

         particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf)));
      };
   }

   @FunctionalInterface
   public interface ParticleReader {
      void read(ByteBuf var1, Particle var2) throws Exception;
   }
}
