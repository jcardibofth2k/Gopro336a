package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
import io.netty.buffer.ByteBuf;

public class Metadata1_8Type extends MetaTypeTemplate {
   public Metadata read(ByteBuf buffer) throws Exception {
      byte item = buffer.readByte();
      if (item == 127) {
         return null;
      } else {
         int typeID = (item & 224) >> 5;
         MetaType1_8 type = MetaType1_8.byId(typeID);
         int id = item & 31;
         return new Metadata(id, type, type.type().read(buffer));
      }
   }

   public void write(ByteBuf buffer, Metadata meta) throws Exception {
      byte item = (byte)(meta.metaType().typeId() << 5 | meta.method_71() & 31);
      buffer.writeByte(item);
      meta.metaType().type().write(buffer, meta.getValue());
   }
}
