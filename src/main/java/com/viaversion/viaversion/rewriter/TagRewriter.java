package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TagRewriter {
   private static final int[] EMPTY_ARRAY = new int[0];
   private final Protocol protocol;
   private final Map newTags = new EnumMap(RegistryType.class);

   public TagRewriter(Protocol protocol) {
      this.protocol = protocol;
   }

   public void loadFromMappingData() {
      RegistryType[] var1 = RegistryType.getValues();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         RegistryType type = var1[var3];
         List tags = this.protocol.getMappingData().getTags(type);
         if (tags != null) {
            this.getOrComputeNewTags(type).addAll(tags);
         }
      }

   }

   public void addEmptyTag(RegistryType tagType, String tagId) {
      this.getOrComputeNewTags(tagType).add(new TagData(tagId, EMPTY_ARRAY));
   }

   public void addEmptyTags(RegistryType tagType, String... tagIds) {
      List tagList = this.getOrComputeNewTags(tagType);
      String[] var4 = tagIds;
      int var5 = tagIds.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String id = var4[var6];
         tagList.add(new TagData(id, EMPTY_ARRAY));
      }

   }

   public void addEntityTag(String tagId, EntityType... entities) {
      int[] ids = new int[entities.length];

      for(int i = 0; i < entities.length; ++i) {
         ids[i] = entities[i].getId();
      }

      this.addTagRaw(RegistryType.ENTITY, tagId, ids);
   }

   public void addTag(RegistryType tagType, String tagId, int... unmappedIds) {
      List newTags = this.getOrComputeNewTags(tagType);
      IdRewriteFunction rewriteFunction = this.getRewriter(tagType);

      for(int i = 0; i < unmappedIds.length; ++i) {
         int oldId = unmappedIds[i];
         unmappedIds[i] = rewriteFunction.rewrite(oldId);
      }

      newTags.add(new TagData(tagId, unmappedIds));
   }

   public void addTagRaw(RegistryType tagType, String tagId, int... ids) {
      this.getOrComputeNewTags(tagType).add(new TagData(tagId, ids));
   }

   public void register(ClientboundPacketType packetType, @Nullable final RegistryType readUntilType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler(TagRewriter.this.getHandler(readUntilType));
         }
      });
   }

   public void registerGeneric(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler(TagRewriter.this.getGenericHandler());
         }
      });
   }

   public PacketHandler getHandler(@Nullable RegistryType readUntilType) {
      return (wrapper) -> {
         RegistryType[] var3 = RegistryType.getValues();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            RegistryType type = var3[var5];
            this.handle(wrapper, this.getRewriter(type), this.getNewTags(type));
            if (type == readUntilType) {
               break;
            }
         }

      };
   }

   public PacketHandler getGenericHandler() {
      return (wrapper) -> {
         int length = (Integer)wrapper.passthrough(Type.VAR_INT);

         for(int i = 0; i < length; ++i) {
            String registryKey = (String)wrapper.passthrough(Type.STRING);
            if (registryKey.startsWith("minecraft:")) {
               registryKey = registryKey.substring(10);
            }

            RegistryType type = RegistryType.getByKey(registryKey);
            if (type != null) {
               this.handle(wrapper, this.getRewriter(type), this.getNewTags(type));
            } else {
               this.handle(wrapper, (IdRewriteFunction)null, (List)null);
            }
         }

      };
   }

   public void handle(PacketWrapper wrapper, @Nullable IdRewriteFunction rewriteFunction, @Nullable List newTags) throws Exception {
      int tagsSize = (Integer)wrapper.read(Type.VAR_INT);
      wrapper.write(Type.VAR_INT, newTags != null ? tagsSize + newTags.size() : tagsSize);

      for(int i = 0; i < tagsSize; ++i) {
         wrapper.passthrough(Type.STRING);
         int[] ids = (int[])wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
         if (rewriteFunction == null) {
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, ids);
         } else {
            IntList idList = new IntArrayList(ids.length);
            int[] var8 = ids;
            int var9 = ids.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               int id = var8[var10];
               int mappedId = rewriteFunction.rewrite(id);
               if (mappedId != -1) {
                  idList.add(mappedId);
               }
            }

            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, idList.toArray(EMPTY_ARRAY));
         }
      }

      if (newTags != null) {
         Iterator var13 = newTags.iterator();

         while(var13.hasNext()) {
            TagData tag = (TagData)var13.next();
            wrapper.write(Type.STRING, tag.identifier());
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.entries());
         }
      }

   }

   @Nullable
   public List getNewTags(RegistryType tagType) {
      return (List)this.newTags.get(tagType);
   }

   public List getOrComputeNewTags(RegistryType tagType) {
      return (List)this.newTags.computeIfAbsent(tagType, (type) -> {
         return new ArrayList();
      });
   }

   @Nullable
   public IdRewriteFunction getRewriter(RegistryType tagType) {
      MappingData mappingData = this.protocol.getMappingData();
      IdRewriteFunction var10000;
      switch(tagType) {
      case BLOCK:
         if (mappingData != null && mappingData.getBlockMappings() != null) {
            Objects.requireNonNull(mappingData);
            var10000 = mappingData::getNewBlockId;
         } else {
            var10000 = null;
         }

         return var10000;
      case ITEM:
         if (mappingData != null && mappingData.getItemMappings() != null) {
            Objects.requireNonNull(mappingData);
            var10000 = mappingData::getNewItemId;
         } else {
            var10000 = null;
         }

         return var10000;
      case ENTITY:
         return this.protocol.getEntityRewriter() != null ? (id) -> {
            return this.protocol.getEntityRewriter().newEntityId(id);
         } : null;
      case FLUID:
      case GAME_EVENT:
      default:
         return null;
      }
   }
}
