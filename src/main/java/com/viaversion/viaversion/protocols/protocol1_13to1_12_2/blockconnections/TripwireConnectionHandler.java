package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.TripwireConnectionHandler.1;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TripwireConnectionHandler extends ConnectionHandler {
   private static final Map tripwireDataMap = new HashMap();
   private static final Map connectedBlocks = new HashMap();
   private static final Map tripwireHooks = new HashMap();

   static ConnectionData.ConnectorInitAction init() {
      TripwireConnectionHandler connectionHandler = new TripwireConnectionHandler();
      return (blockData) -> {
         if (blockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
            tripwireHooks.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
         } else if (blockData.getMinecraftKey().equals("minecraft:tripwire")) {
            TripwireConnectionHandler.TripwireData tripwireData = new TripwireConnectionHandler.TripwireData(blockData.getValue("attached").equals("true"), blockData.getValue("disarmed").equals("true"), blockData.getValue("powered").equals("true"), (1)null)
            tripwireDataMap.put(blockData.getSavedBlockStateId(), tripwireData);
            connectedBlocks.put(getStates(blockData), blockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
         }

      };
   }

   private static byte getStates(WrappedBlockData blockData) {
      byte b = 0;
      if (blockData.getValue("attached").equals("true")) {
         b = (byte)(b | 1);
      }

      if (blockData.getValue("disarmed").equals("true")) {
         b = (byte)(b | 2);
      }

      if (blockData.getValue("powered").equals("true")) {
         b = (byte)(b | 4);
      }

      if (blockData.getValue("east").equals("true")) {
         b = (byte)(b | 8);
      }

      if (blockData.getValue("north").equals("true")) {
         b = (byte)(b | 16);
      }

      if (blockData.getValue("south").equals("true")) {
         b = (byte)(b | 32);
      }

      if (blockData.getValue("west").equals("true")) {
         b = (byte)(b | 64);
      }

      return b;
   }

   public int connect(UserConnection user, Position position, int blockState) {
      TripwireConnectionHandler.TripwireData tripwireData = (TripwireConnectionHandler.TripwireData)tripwireDataMap.get(blockState);
      if (tripwireData == null) {
         return blockState;
      } else {
         byte b = 0;
         if (tripwireData.isAttached()) {
            b = (byte)(b | 1);
         }

         if (tripwireData.isDisarmed()) {
            b = (byte)(b | 2);
         }

         if (tripwireData.isPowered()) {
            b = (byte)(b | 4);
         }

         int east = this.getBlockData(user, position.getRelative(BlockFace.EAST));
         int north = this.getBlockData(user, position.getRelative(BlockFace.NORTH));
         int south = this.getBlockData(user, position.getRelative(BlockFace.SOUTH));
         int west = this.getBlockData(user, position.getRelative(BlockFace.WEST));
         if (tripwireDataMap.containsKey(east) || tripwireHooks.get(east) == BlockFace.WEST) {
            b = (byte)(b | 8);
         }

         if (tripwireDataMap.containsKey(north) || tripwireHooks.get(north) == BlockFace.SOUTH) {
            b = (byte)(b | 16);
         }

         if (tripwireDataMap.containsKey(south) || tripwireHooks.get(south) == BlockFace.NORTH) {
            b = (byte)(b | 32);
         }

         if (tripwireDataMap.containsKey(west) || tripwireHooks.get(west) == BlockFace.EAST) {
            b = (byte)(b | 64);
         }

         Integer newBlockState = (Integer)connectedBlocks.get(b);
         return newBlockState == null ? blockState : newBlockState;
      }
   }

   private static final class TripwireData {
      private final boolean attached;
      private final boolean disarmed;
      private final boolean powered;

      private TripwireData(boolean attached, boolean disarmed, boolean powered) {
         this.attached = attached;
         this.disarmed = disarmed;
         this.powered = powered;
      }

      public boolean isAttached() {
         return this.attached;
      }

      public boolean isDisarmed() {
         return this.disarmed;
      }

      public boolean isPowered() {
         return this.powered;
      }

      // $FF: synthetic method
      TripwireData(boolean x0, boolean x1, boolean x2, 1 x3) {
         this(x0, x1, x2);
      }
   }
}
