package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;

public class WorldPackets {
   public static void register(final Protocol1_16To1_15_2 protocol) {
      BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
      blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
      protocol.registerClientbound(ClientboundPackets1_15.UPDATE_LIGHT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               wrapper.write(Type.BOOLEAN, true);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_15.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               Chunk chunk = (Chunk)wrapper.read(new Chunk1_15Type());
               wrapper.write(new Chunk1_16Type(), chunk);
               chunk.setIgnoreOldLightData(chunk.isFullChunk());

               for(int s = 0; s < chunk.getSections().length; ++s) {
                  ChunkSection section = chunk.getSections()[s];
                  if (section != null) {
                     for(int i = 0; i < section.getPaletteSize(); ++i) {
                        int old = section.getPaletteEntry(i);
                        section.setPaletteEntry(i, protocol.getMappingData().getNewBlockStateId(old));
                     }
                  }
               }

               CompoundTag heightMaps = chunk.getHeightMap();
               Iterator var9 = heightMaps.values().iterator();

               while(var9.hasNext()) {
                  Tag heightMapTag = (Tag)var9.next();
                  LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                  int[] heightMapData = new int[256];
                  CompactArrayUtil.iterateCompactArray(9, heightMapData.length, heightMap.getValue(), (ix, v) -> {
                     heightMapData[ix] = v;
                  });
                  heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, (ix) -> {
                     return heightMapData[ix];
                  }));
               }

               if (chunk.getBlockEntities() != null) {
                  var9 = chunk.getBlockEntities().iterator();

                  while(var9.hasNext()) {
                     CompoundTag blockEntity = (CompoundTag)var9.next();
                     WorldPackets.handleBlockEntity(blockEntity);
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_15.BLOCK_ENTITY_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               Position position = (Position)wrapper.passthrough(Type.POSITION1_14);
               short action = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);
               CompoundTag tag = (CompoundTag)wrapper.passthrough(Type.NBT);
               WorldPackets.handleBlockEntity(tag);
            });
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
   }

   private static void handleBlockEntity(CompoundTag compoundTag) {
      StringTag idTag = (StringTag)compoundTag.get("id");
      if (idTag != null) {
         String id = idTag.getValue();
         if (id.equals("minecraft:conduit")) {
            Tag targetUuidTag = compoundTag.remove("target_uuid");
            if (!(targetUuidTag instanceof StringTag)) {
               return;
            }

            UUID targetUuid = UUID.fromString((String)targetUuidTag.getValue());
            compoundTag.put("Target", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(targetUuid)));
         } else if (id.equals("minecraft:skull") && compoundTag.get("Owner") instanceof CompoundTag) {
            CompoundTag ownerTag = (CompoundTag)compoundTag.remove("Owner");
            StringTag ownerUuidTag = (StringTag)ownerTag.remove("Id");
            if (ownerUuidTag != null) {
               UUID ownerUuid = UUID.fromString(ownerUuidTag.getValue());
               ownerTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(ownerUuid)));
            }

            CompoundTag skullOwnerTag = new CompoundTag();
            Iterator var6 = ownerTag.entrySet().iterator();

            while(var6.hasNext()) {
               Entry entry = (Entry)var6.next();
               skullOwnerTag.put((String)entry.getKey(), (Tag)entry.getValue());
            }

            compoundTag.put("SkullOwner", skullOwnerTag);
         }

      }
   }
}
