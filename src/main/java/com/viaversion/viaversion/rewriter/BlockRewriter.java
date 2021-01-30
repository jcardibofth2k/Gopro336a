package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

public class BlockRewriter {
   private final Protocol protocol;
   private final Type positionType;

   public BlockRewriter(Protocol protocol, Type positionType) {
      this.protocol = protocol;
      this.positionType = positionType;
   }

   public void registerBlockAction(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(BlockRewriter.this.positionType);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Type.VAR_INT, 0);
               int mappedId = BlockRewriter.this.protocol.getMappingData().getNewBlockId(id);
               if (mappedId == -1) {
                  wrapper.cancel();
               } else {
                  wrapper.set(Type.VAR_INT, 0, mappedId);
               }
            });
         }
      });
   }

   public void registerBlockChange(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(BlockRewriter.this.positionType);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               wrapper.set(Type.VAR_INT, 0, BlockRewriter.this.protocol.getMappingData().getNewBlockStateId((Integer)wrapper.get(Type.VAR_INT, 0)));
            });
         }
      });
   }

   public void registerMultiBlockChange(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.handler((wrapper) -> {
               BlockChangeRecord[] var2 = (BlockChangeRecord[])wrapper.passthrough(Type.BLOCK_CHANGE_RECORD_ARRAY);
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  BlockChangeRecord record = var2[var4];
                  record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
               }

            });
         }
      });
   }

   public void registerVarLongMultiBlockChange(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.LONG);
            this.map(Type.BOOLEAN);
            this.handler((wrapper) -> {
               BlockChangeRecord[] var2 = (BlockChangeRecord[])wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  BlockChangeRecord record = var2[var4];
                  record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
               }

            });
         }
      });
   }

   public void registerAcknowledgePlayerDigging(ClientboundPacketType packetType) {
      this.registerBlockChange(packetType);
   }

   public void registerEffect(ClientboundPacketType packetType, final int playRecordId, final int blockBreakId) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(BlockRewriter.this.positionType);
            this.map(Type.INT);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Type.INT, 0);
               int data = (Integer)wrapper.get(Type.INT, 1);
               if (id == playRecordId) {
                  wrapper.set(Type.INT, 1, BlockRewriter.this.protocol.getMappingData().getNewItemId(data));
               } else if (id == blockBreakId) {
                  wrapper.set(Type.INT, 1, BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(data));
               }

            });
         }
      });
   }
}
