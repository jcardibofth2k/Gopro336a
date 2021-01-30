package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import java.util.List;

public class Protocol1_9_3To1_9_1_2 extends AbstractProtocol {
   public static final ValueTransformer ADJUST_PITCH;

   public Protocol1_9_3To1_9_1_2() {
      super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_9.UPDATE_SIGN, (ClientboundPacketType)null, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Position position = (Position)wrapper.read(Type.POSITION);
                  JsonElement[] lines = new JsonElement[4];

                  for(int i = 0; i < 4; ++i) {
                     lines[i] = (JsonElement)wrapper.read(Type.COMPONENT);
                  }

                  wrapper.clearInputBuffer();
                  wrapper.setId(9);
                  wrapper.write(Type.POSITION, position);
                  wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short)9));
                  CompoundTag tag = new CompoundTag();
                  tag.put("id", new StringTag("Sign"));
                  tag.put("x", new IntTag(position.getX()));
                  tag.put("y", new IntTag(position.getY()));
                  tag.put("z", new IntTag(position.getZ()));

                  for(int ix = 0; ix < lines.length; ++ix) {
                     tag.put("Text" + (ix + 1), new StringTag(lines[ix].toString()));
                  }

                  wrapper.write(Type.NBT, tag);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  Chunk1_9_1_2Type type = new Chunk1_9_1_2Type(clientWorld);
                  Chunk chunk = (Chunk)wrapper.passthrough(type);
                  List tags = chunk.getBlockEntities();

                  for(int i = 0; i < chunk.getSections().length; ++i) {
                     ChunkSection section = chunk.getSections()[i];
                     if (section != null) {
                        for(int y = 0; y < 16; ++y) {
                           for(int z = 0; z < 16; ++z) {
                              for(int x = 0; x < 16; ++x) {
                                 int block = section.getBlockWithoutData(x, y, z);
                                 if (FakeTileEntity.hasBlock(block)) {
                                    tags.add(FakeTileEntity.getFromBlock(x + (chunk.getX() << 4), y + (i << 4), z + (chunk.getZ() << 4), block));
                                 }
                              }
                           }
                        }
                     }
                  }

                  wrapper.write(Type.NBT_ARRAY, (CompoundTag[])chunk.getBlockEntities().toArray(new CompoundTag[0]));
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() {
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
      this.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketRemapper() {
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
      this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Protocol1_9_3To1_9_1_2.ADJUST_PITCH);
         }
      });
   }

   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld(user));
      }

   }

   static {
      ADJUST_PITCH = new ValueTransformer(Type.UNSIGNED_BYTE, Type.UNSIGNED_BYTE) {
         public Short transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return (short)Math.round((float)inputValue / 63.5F * 63.0F);
         }
      };
   }
}
