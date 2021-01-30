package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleRewriter {
   private static final List particles = new ArrayList();

   public static Particle rewriteParticle(int particleId, Integer[] data) {
      if (particleId >= particles.size()) {
         Via.getPlatform().getLogger().severe("Failed to transform particles with id " + particleId + " and data " + Arrays.toString(data));
         return null;
      } else {
         com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.NewParticle rewrite = (com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.NewParticle)particles.get(particleId);
         return rewrite.handle(new Particle(rewrite.getId()), data);
      }
   }

   private static void add(int newId) {
      particles.add(new com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.NewParticle(newId, (com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler)null));
   }

   private static void add(int newId, com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler dataHandler) {
      particles.add(new com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.NewParticle(newId, dataHandler));
   }

   private static com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler reddustHandler() {
      return new 1();
   }

   private static boolean randomBool() {
      return ThreadLocalRandom.current().nextBoolean();
   }

   private static com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler iconcrackHandler() {
      return new 2();
   }

   private static com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler blockHandler() {
      return new 3();
   }

   // $FF: synthetic method
   static boolean access$000() {
      return randomBool();
   }

   static {
      add(34);
      add(19);
      add(18);
      add(21);
      add(4);
      add(43);
      add(22);
      add(42);
      add(42);
      add(6);
      add(14);
      add(37);
      add(30);
      add(12);
      add(26);
      add(17);
      add(0);
      add(44);
      add(10);
      add(9);
      add(1);
      add(24);
      add(32);
      add(33);
      add(35);
      add(15);
      add(23);
      add(31);
      add(-1);
      add(5);
      add(11, reddustHandler());
      add(29);
      add(34);
      add(28);
      add(25);
      add(2);
      add(27, iconcrackHandler());
      add(3, blockHandler());
      add(3, blockHandler());
      add(36);
      add(-1);
      add(13);
      add(8);
      add(16);
      add(7);
      add(40);
      add(20, blockHandler());
      add(41);
      add(38);
   }
}
