package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;

public class Particle1_13_2Type extends Type {
   public Particle1_13_2Type() {
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
      switch(type) {
      case 3:
      case 20:
         particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(buffer)));
         break;
      case 11:
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buffer)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buffer)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buffer)));
         particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(buffer)));
         break;
      case 27:
         particle.getArguments().add(new Particle.ParticleData(Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM.read(buffer)));
      }

      return particle;
   }
}
