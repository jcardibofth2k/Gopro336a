package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.1;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.2;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping.3;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import java.util.List;

public class ParticleMapping {
   private static final ParticleMapping.ParticleData[] particles;

   public static ParticleMapping.ParticleData getMapping(int id) {
      return particles[id];
   }

   private static ParticleMapping.ParticleData rewrite(int replacementId) {
      return new ParticleMapping.ParticleData(replacementId, (1)null)
   }

   private static ParticleMapping.ParticleData rewrite(int replacementId, ParticleMapping.ParticleHandler handler) {
      return new ParticleMapping.ParticleData(replacementId, handler, (1)null)
   }

   static {
      ParticleMapping.ParticleHandler blockHandler = new 1();
      particles = new ParticleMapping.ParticleData[]{rewrite(16), rewrite(20), rewrite(35), rewrite(37, blockHandler), rewrite(4), rewrite(29), rewrite(9), rewrite(44), rewrite(42), rewrite(19), rewrite(18), rewrite(30, new 2()), rewrite(13), rewrite(41), rewrite(10), rewrite(25), rewrite(43), rewrite(15), rewrite(2), rewrite(1), rewrite(46, blockHandler), rewrite(3), rewrite(6), rewrite(26), rewrite(21), rewrite(34), rewrite(14), rewrite(36, new 3()), rewrite(33), rewrite(31), rewrite(12), rewrite(27), rewrite(22), rewrite(23), rewrite(0), rewrite(24), rewrite(39), rewrite(11), rewrite(48), rewrite(12), rewrite(45), rewrite(47), rewrite(7), rewrite(5), rewrite(17), rewrite(4), rewrite(4), rewrite(4), rewrite(18), rewrite(18)}
   }

   public static final class ParticleData {
      private final int historyId;
      private final ParticleMapping.ParticleHandler handler;

      private ParticleData(int historyId, ParticleMapping.ParticleHandler handler) {
         this.historyId = historyId;
         this.handler = handler;
      }

      private ParticleData(int historyId) {
         this(historyId, (ParticleMapping.ParticleHandler)null);
      }

      public int[] rewriteData(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
         return this.handler == null ? null : this.handler.rewrite(protocol, wrapper);
      }

      public int[] rewriteMeta(Protocol1_12_2To1_13 protocol, List data) {
         return this.handler == null ? null : this.handler.rewrite(protocol, data);
      }

      public int getHistoryId() {
         return this.historyId;
      }

      public ParticleMapping.ParticleHandler getHandler() {
         return this.handler;
      }

      // $FF: synthetic method
      ParticleData(int x0, 1 x1) {
         this(x0);
      }

      // $FF: synthetic method
      ParticleData(int x0, ParticleMapping.ParticleHandler x1, 1 x2) {
         this(x0, x1);
      }
   }

   public interface ParticleHandler {
      int[] rewrite(Protocol1_12_2To1_13 var1, PacketWrapper var2) throws Exception;

      int[] rewrite(Protocol1_12_2To1_13 var1, List var2);

      default boolean isBlockHandler() {
         return false;
      }
   }
}
