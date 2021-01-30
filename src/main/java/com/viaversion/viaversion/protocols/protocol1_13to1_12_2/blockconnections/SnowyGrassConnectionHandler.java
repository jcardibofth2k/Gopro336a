package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SnowyGrassConnectionHandler extends ConnectionHandler {
   private static final Map grassBlocks = new HashMap();
   private static final Set snows = new HashSet();

   static ConnectionData.ConnectorInitAction init() {
      Set snowyGrassBlocks = new HashSet();
      snowyGrassBlocks.add("minecraft:grass_block");
      snowyGrassBlocks.add("minecraft:podzol");
      snowyGrassBlocks.add("minecraft:mycelium");
      SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
      return (blockData) -> {
         if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
            blockData.set("snowy", "true");
            grassBlocks.put(new Pair(blockData.getSavedBlockStateId(), true), blockData.getBlockStateId());
            blockData.set("snowy", "false");
            grassBlocks.put(new Pair(blockData.getSavedBlockStateId(), false), blockData.getBlockStateId());
         }

         if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
            snows.add(blockData.getSavedBlockStateId());
         }

      };
   }

   public int connect(UserConnection user, Position position, int blockState) {
      int blockUpId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
      Integer newId = (Integer)grassBlocks.get(new Pair(blockState, snows.contains(blockUpId)));
      return newId != null ? newId : blockState;
   }
}
