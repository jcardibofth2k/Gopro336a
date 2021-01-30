package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks.BlockEntity;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;

public class Protocol1_9_1_2To1_9_3_4 extends AbstractProtocol {
   public Protocol1_9_1_2To1_9_3_4() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.NBT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  if ((Short)wrapper.get(Type.UNSIGNED_BYTE, 0) == 9) {
                     Position position = (Position)wrapper.get(Type.POSITION, 0);
                     CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
                     wrapper.clearPacket();
                     wrapper.setId(ClientboundPackets1_9.UPDATE_SIGN.ordinal());
                     wrapper.write(Type.POSITION, position);

                     for(int i = 1; i < 5; ++i) {
                        wrapper.write(Type.STRING, (String)tag.get("Text" + i).getValue());
                     }
                  }

               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  Chunk1_9_3_4Type newType = new Chunk1_9_3_4Type(clientWorld);
                  Chunk1_9_1_2Type oldType = new Chunk1_9_1_2Type(clientWorld);
                  Chunk chunk = (Chunk)wrapper.read(newType);
                  wrapper.write(oldType, chunk);
                  BlockEntity.handle(chunk.getBlockEntities(), wrapper.user());
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  int dimensionId = (Integer)wrapper.get(Type.INT, 1);
                  clientChunks.setEnvironment(dimensionId);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  int dimensionId = (Integer)wrapper.get(Type.INT, 0);
                  clientWorld.setEnvironment(dimensionId);
               }
            });
         }
      });
   }

   public void init(UserConnection userConnection) {
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld(userConnection));
      }

   }
}
