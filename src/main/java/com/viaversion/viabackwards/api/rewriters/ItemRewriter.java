package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ItemRewriter extends ItemRewriterBase {
   private final TranslatableRewriter translatableRewriter;

   protected ItemRewriter(BackwardsProtocol protocol, @Nullable TranslatableRewriter translatableRewriter) {
      super(protocol, true);
      this.translatableRewriter = translatableRewriter;
   }

   @Nullable
   public Item handleItemToClient(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag display = item.tag() != null ? (CompoundTag)item.tag().get("display") : null;
         if (this.translatableRewriter != null && display != null) {
            StringTag name = (StringTag)display.get("Name");
            if (name != null) {
               String newValue = this.translatableRewriter.processText(name.getValue()).toString();
               if (!newValue.equals(name.getValue())) {
                  this.saveStringTag(display, name, "Name");
               }

               name.setValue(newValue);
            }

            ListTag lore = (ListTag)display.get("Lore");
            if (lore != null) {
               boolean changed = false;
               Iterator var6 = lore.iterator();

               while(var6.hasNext()) {
                  Tag loreEntryTag = (Tag)var6.next();
                  if (loreEntryTag instanceof StringTag) {
                     StringTag loreEntry = (StringTag)loreEntryTag;
                     String newValue = this.translatableRewriter.processText(loreEntry.getValue()).toString();
                     if (!changed && !newValue.equals(loreEntry.getValue())) {
                        changed = true;
                        this.saveListTag(display, lore, "Lore");
                     }

                     loreEntry.setValue(newValue);
                  }
               }
            }
         }

         MappedItem data = ((BackwardsProtocol)this.protocol).getMappingData().getMappedItem(item.identifier());
         if (data == null) {
            return super.handleItemToClient(item);
         } else {
            if (item.tag() == null) {
               item.setTag(new CompoundTag());
            }

            item.tag().put(this.nbtTagName + "|id", new IntTag(item.identifier()));
            item.setIdentifier(data.getId());
            if (display == null) {
               item.tag().put("display", display = new CompoundTag());
            }

            if (!display.contains("Name")) {
               display.put("Name", new StringTag(data.getJsonName()));
               display.put(this.nbtTagName + "|customName", new ByteTag());
            }

            return item;
         }
      }
   }

   @Nullable
   public Item handleItemToServer(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToServer(item);
         if (item.tag() != null) {
            IntTag originalId = (IntTag)item.tag().remove(this.nbtTagName + "|id");
            if (originalId != null) {
               item.setIdentifier(originalId.asInt());
            }
         }

         return item;
      }
   }
}
