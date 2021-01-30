package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ItemRewriterBase extends com.viaversion.viaversion.rewriter.ItemRewriter {
   protected final String nbtTagName;
   protected final boolean jsonNameFormat;

   protected ItemRewriterBase(BackwardsProtocol protocol, boolean jsonNameFormat) {
      super(protocol);
      this.jsonNameFormat = jsonNameFormat;
      this.nbtTagName = "VB|" + protocol.getClass().getSimpleName();
   }

   @Nullable
   public Item handleItemToServer(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToServer(item);
         this.restoreDisplayTag(item);
         return item;
      }
   }

   protected boolean hasBackupTag(CompoundTag displayTag, String tagName) {
      return displayTag.contains(this.nbtTagName + "|o" + tagName);
   }

   protected void saveStringTag(CompoundTag displayTag, StringTag original, String name) {
      String backupName = this.nbtTagName + "|o" + name;
      if (!displayTag.contains(backupName)) {
         displayTag.put(backupName, new StringTag(original.getValue()));
      }

   }

   protected void saveListTag(CompoundTag displayTag, ListTag original, String name) {
      String backupName = this.nbtTagName + "|o" + name;
      if (!displayTag.contains(backupName)) {
         ListTag listTag = new ListTag();
         Iterator var6 = original.getValue().iterator();

         while(var6.hasNext()) {
            Tag tag = (Tag)var6.next();
            listTag.add(tag.clone());
         }

         displayTag.put(backupName, listTag);
      }

   }

   protected void restoreDisplayTag(Item item) {
      if (item.tag() != null) {
         CompoundTag display = (CompoundTag)item.tag().get("display");
         if (display != null) {
            if (display.remove(this.nbtTagName + "|customName") != null) {
               display.remove("Name");
            } else {
               this.restoreStringTag(display, "Name");
            }

            this.restoreListTag(display, "Lore");
         }

      }
   }

   protected void restoreStringTag(CompoundTag tag, String tagName) {
      StringTag original = (StringTag)tag.remove(this.nbtTagName + "|o" + tagName);
      if (original != null) {
         tag.put(tagName, new StringTag(original.getValue()));
      }

   }

   protected void restoreListTag(CompoundTag tag, String tagName) {
      ListTag original = (ListTag)tag.remove(this.nbtTagName + "|o" + tagName);
      if (original != null) {
         tag.put(tagName, new ListTag(original.getValue()));
      }

   }

   public String getNbtTagName() {
      return this.nbtTagName;
   }
}
