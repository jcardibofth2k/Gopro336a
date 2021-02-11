package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter.1;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyBlockItemRewriter extends ItemRewriterBase {
   private static final Map LEGACY_MAPPINGS = new HashMap();
   protected final Int2ObjectMap replacementData;

   protected LegacyBlockItemRewriter(BackwardsProtocol protocol) {
      super(protocol, false);
      this.replacementData = (Int2ObjectMap)LEGACY_MAPPINGS.get(protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
   }

   @Nullable
   public Item handleItemToClient(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         MappedLegacyBlockItem data = (MappedLegacyBlockItem)this.replacementData.get(item.identifier());
         if (data == null) {
            return super.handleItemToClient(item);
         } else {
            short originalData = item.data();
            item.setIdentifier(data.getId());
            if (data.getData() != -1) {
               item.setData(data.getData());
            }

            if (data.getName() != null) {
               if (item.tag() == null) {
                  item.setTag(new CompoundTag());
               }

               CompoundTag display = (CompoundTag)item.tag().get("display");
               if (display == null) {
                  item.tag().put("display", display = new CompoundTag());
               }

               StringTag nameTag = (StringTag)display.get("Name");
               if (nameTag == null) {
                  display.put("Name", nameTag = new StringTag(data.getName()));
                  display.put(this.nbtTagName + "|customName", new ByteTag());
               }

               String value = nameTag.getValue();
               if (value.contains("%vb_color%")) {
                  display.put("Name", new StringTag(value.replace("%vb_color%", BlockColors.get(originalData))));
               }
            }

            return item;
         }
      }
   }

   public int handleBlockID(int idx) {
      int type = idx >> 4;
      int meta = idx & 15;
      Block b = this.handleBlock(type, meta);
      return b == null ? idx : b.getId() << 4 | b.getData() & 15;
   }

   @Nullable
   public Block handleBlock(int blockId, int data) {
      MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(blockId);
      if (settings != null && settings.isBlock()) {
         Block block = settings.getBlock();
         return block.getData() == -1 ? block.withData(data) : block;
      } else {
         return null;
      }
   }

   protected void handleChunk(Chunk chunk) {
      Map tags = new HashMap();
      Iterator var3 = chunk.getBlockEntities().iterator();

      int btype;
      int meta;
      MappedLegacyBlockItem settings;
      while(var3.hasNext()) {
         CompoundTag tag = (CompoundTag)var3.next();
         Tag xTag;
         Tag yTag;
         Tag zTag;
         if ((xTag = tag.get("x")) != null && (yTag = tag.get("y")) != null && (zTag = tag.get("z")) != null) {
            LegacyBlockItemRewriter.Pos pos = new LegacyBlockItemRewriter.Pos(((NumberTag)xTag).asInt() & 15, ((NumberTag)yTag).asInt(), ((NumberTag)zTag).asInt() & 15, (1)null)
            tags.put(pos, tag);
            if (pos.getY() >= 0 && pos.getY() <= 255) {
               ChunkSection section = chunk.getSections()[pos.getY() >> 4];
               if (section != null) {
                  btype = section.getFlatBlock(pos.getX(), pos.getY() & 15, pos.getZ());
                  meta = btype >> 4;
                  settings = (MappedLegacyBlockItem)this.replacementData.get(meta);
                  if (settings != null && settings.hasBlockEntityHandler()) {
                     settings.getBlockEntityHandler().handleOrNewCompoundTag(btype, tag);
                  }
               }
            }
         }
      }

      for(int i = 0; i < chunk.getSections().length; ++i) {
         ChunkSection section = chunk.getSections()[i];
         if (section != null) {
            boolean hasBlockEntityHandler = false;

            int x;
            int y;
            int z;
            int block;
            for(x = 0; x < section.getPaletteSize(); ++x) {
               y = section.getPaletteEntry(x);
               z = y >> 4;
               block = y & 15;
               Block b = this.handleBlock(z, block);
               if (b != null) {
                  section.setPaletteEntry(x, b.getId() << 4 | b.getData() & 15);
               }

               if (!hasBlockEntityHandler) {
                  MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(z);
                  if (settings != null && settings.hasBlockEntityHandler()) {
                     hasBlockEntityHandler = true;
                  }
               }
            }

            if (hasBlockEntityHandler) {
               for(x = 0; x < 16; ++x) {
                  for(y = 0; y < 16; ++y) {
                     for(z = 0; z < 16; ++z) {
                        block = section.getFlatBlock(x, y, z);
                        btype = block >> 4;
                        meta = block & 15;
                        settings = (MappedLegacyBlockItem)this.replacementData.get(btype);
                        if (settings != null && settings.hasBlockEntityHandler()) {
                           LegacyBlockItemRewriter.Pos pos = new LegacyBlockItemRewriter.Pos(x, y + (i << 4), z, (1)null)
                           if (!tags.containsKey(pos)) {
                              CompoundTag tag = new CompoundTag();
                              tag.put("x", new IntTag(x + (chunk.getX() << 4)));
                              tag.put("y", new IntTag(y + (i << 4)));
                              tag.put("z", new IntTag(z + (chunk.getZ() << 4)));
                              settings.getBlockEntityHandler().handleOrNewCompoundTag(block, tag);
                              chunk.getBlockEntities().add(tag);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   protected CompoundTag getNamedTag(String text) {
      CompoundTag tag = new CompoundTag();
      tag.put("display", new CompoundTag());
      text = "§r" + text;
      ((CompoundTag)tag.get("display")).put("Name", new StringTag(this.jsonNameFormat ? ChatRewriter.legacyTextToJsonString(text) : text));
      return tag;
   }

   static {
      JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");
      Iterator var1 = jsonObject.entrySet().iterator();

      label62:
      while(var1.hasNext()) {
         Entry entry = (Entry)var1.next();
         Int2ObjectMap mappings = new Int2ObjectOpenHashMap(8);
         LEGACY_MAPPINGS.put(entry.getKey(), mappings);
         Iterator var4 = ((JsonElement)entry.getValue()).getAsJsonObject().entrySet().iterator();

         while(true) {
            while(true) {
               while(true) {
                  if (!var4.hasNext()) {
                     continue label62;
                  }

                  Entry dataEntry = (Entry)var4.next();
                  JsonObject object = ((JsonElement)dataEntry.getValue()).getAsJsonObject();
                  int id = object.getAsJsonPrimitive("id").getAsInt();
                  JsonPrimitive jsonData = object.getAsJsonPrimitive("data");
                  short data = jsonData != null ? jsonData.getAsShort() : 0;
                  String name = object.getAsJsonPrimitive("name").getAsString();
                  JsonPrimitive blockField = object.getAsJsonPrimitive("block");
                  boolean block = blockField != null && blockField.getAsBoolean();
                  if (((String)dataEntry.getKey()).indexOf(45) != -1) {
                     String[] split = ((String)dataEntry.getKey()).split("-", 2);
                     int from = Integer.parseInt(split[0]);
                     int to = Integer.parseInt(split[1]);
                     if (name.contains("%color%")) {
                        for(int i = from; i <= to; ++i) {
                           mappings.put(i, new MappedLegacyBlockItem(id, data, name.replace("%color%", BlockColors.get(i - from)), block));
                        }
                     } else {
                        MappedLegacyBlockItem mappedBlockItem = new MappedLegacyBlockItem(id, data, name, block);

                        for(int i = from; i <= to; ++i) {
                           mappings.put(i, mappedBlockItem);
                        }
                     }
                  } else {
                     mappings.put(Integer.parseInt((String)dataEntry.getKey()), new MappedLegacyBlockItem(id, data, name, block));
                  }
               }
            }
         }
      }

   }

   private static final class Pos {
      // $FF: renamed from: x int
      private final int field_136;
      // $FF: renamed from: y short
      private final short field_137;
      // $FF: renamed from: z int
      private final int field_138;

      private Pos(int x, int y, int z) {
         this.field_136 = x;
         this.field_137 = (short)y;
         this.field_138 = z;
      }

      public int getX() {
         return this.field_136;
      }

      public int getY() {
         return this.field_137;
      }

      public int getZ() {
         return this.field_138;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            LegacyBlockItemRewriter.Pos pos = (LegacyBlockItemRewriter.Pos)o;
            if (this.field_136 != pos.field_136) {
               return false;
            } else if (this.field_137 != pos.field_137) {
               return false;
            } else {
               return this.field_138 == pos.field_138;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.field_136;
         result = 31 * result + this.field_137;
         result = 31 * result + this.field_138;
         return result;
      }

      public String toString() {
         return "Pos{x=" + this.field_136 + ", y=" + this.field_137 + ", z=" + this.field_138 + '}';
      }

      // $FF: synthetic method
      Pos(int x0, int x1, int x2, 1 x3) {
         this(x0, x1, x2);
      }
   }
}
