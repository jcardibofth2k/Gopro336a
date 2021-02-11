package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage.ResourcePackTracker;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Protocol1_10To1_9_3_4 extends AbstractProtocol {
   public static final ValueTransformer TO_NEW_PITCH;
   public static final ValueTransformer TRANSFORM_METADATA;
   private final ItemRewriter itemRewriter = new InventoryPackets(this);

   public Protocol1_10To1_9_3_4() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   protected void registerPackets() {
      this.itemRewriter.register();
      this.registerClientbound(State.PLAY, 25, 25, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
         }
      });
      this.registerClientbound(State.PLAY, 70, 70, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.VAR_INT, 0);
                  wrapper.set(Type.VAR_INT, 0, Protocol1_10To1_9_3_4.this.getNewSoundId(id));
               }
            });
         }
      });
      this.registerClientbound(State.PLAY, 57, 57, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
         }
      });
      this.registerClientbound(State.PLAY, 3, 3, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
         }
      });
      this.registerClientbound(State.PLAY, 5, 5, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
         }
      });
      this.registerClientbound(State.PLAY, 50, 50, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ResourcePackTracker tracker = (ResourcePackTracker)wrapper.user().get(ResourcePackTracker.class);
                  tracker.setLastHash((String)wrapper.get(Type.STRING, 1));
               }
            });
         }
      });
      this.registerServerbound(State.PLAY, 22, 22, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ResourcePackTracker tracker = (ResourcePackTracker)wrapper.user().get(ResourcePackTracker.class);
                  wrapper.write(Type.STRING, tracker.getLastHash());
                  wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT));
               }
            });
         }
      });
   }

   public int getNewSoundId(int id) {
      int newId = id;
      if (id >= 24) {
         newId = id + 1;
      }

      if (id >= 248) {
         newId += 4;
      }

      if (id >= 296) {
         newId += 6;
      }

      if (id >= 354) {
         newId += 4;
      }

      if (id >= 372) {
         newId += 4;
      }

      return newId;
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new ResourcePackTracker());
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }

   static {
      TO_NEW_PITCH = new ValueTransformer(Type.FLOAT) {
         public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return (float)inputValue / 63.0F;
         }
      };
      TRANSFORM_METADATA = new ValueTransformer(Types1_9.METADATA_LIST) {
         public List transform(PacketWrapper wrapper, List inputValue) throws Exception {
            List metaList = new CopyOnWriteArrayList(inputValue);
            Iterator var4 = metaList.iterator();

            while(var4.hasNext()) {
               Metadata m = (Metadata)var4.next();
               if (m.method_71() >= 5) {
                  m.setId(m.method_71() + 1);
               }
            }

            return metaList;
         }
      };
   }
}
