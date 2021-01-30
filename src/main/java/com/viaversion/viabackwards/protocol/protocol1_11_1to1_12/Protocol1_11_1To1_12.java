package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;

public class Protocol1_11_1To1_12 extends BackwardsProtocol {
   private final EntityPackets1_12 entityPackets = new EntityPackets1_12(this);
   private final BlockItemPackets1_12 blockItemPackets = new BlockItemPackets1_12(this);

   public Protocol1_11_1To1_12() {
      super(ClientboundPackets1_12.class, ClientboundPackets1_9_3.class, ServerboundPackets1_12.class, ServerboundPackets1_9_3.class);
   }

   protected void registerPackets() {
      this.blockItemPackets.register();
      this.entityPackets.register();
      (new SoundPackets1_12(this)).register();
      (new ChatPackets1_12(this)).register();
      this.registerClientbound(ClientboundPackets1_12.TITLE, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.passthrough(Type.VAR_INT);
               if (action >= 0 && action <= 2) {
                  JsonElement component = (JsonElement)wrapper.read(Type.COMPONENT);
                  wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(component.toString()));
               }

            });
         }
      });
      this.cancelClientbound(ClientboundPackets1_12.ADVANCEMENTS);
      this.cancelClientbound(ClientboundPackets1_12.UNLOCK_RECIPES);
      this.cancelClientbound(ClientboundPackets1_12.SELECT_ADVANCEMENTS_TAB);
   }

   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld(user));
      }

      user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_12Types.EntityType.PLAYER, true));
      user.put(new ShoulderTracker(user));
   }

   public EntityPackets1_12 getEntityRewriter() {
      return this.entityPackets;
   }

   public BlockItemPackets1_12 getItemRewriter() {
      return this.blockItemPackets;
   }
}
