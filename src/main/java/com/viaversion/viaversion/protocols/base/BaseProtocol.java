package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BaseProtocol extends AbstractSimpleProtocol {
   protected void registerPackets() {
      this.registerServerbound(State.HANDSHAKE, 0, 0, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int protocolVersion = (Integer)wrapper.passthrough(Type.VAR_INT);
               wrapper.passthrough(Type.STRING);
               wrapper.passthrough(Type.UNSIGNED_SHORT);
               int state = (Integer)wrapper.passthrough(Type.VAR_INT);
               ProtocolInfo info = wrapper.user().getProtocolInfo();
               info.setProtocolVersion(protocolVersion);
               VersionProvider versionProvider = (VersionProvider)Via.getManager().getProviders().get(VersionProvider.class);
               if (versionProvider == null) {
                  wrapper.user().setActive(false);
               } else {
                  int serverProtocol = versionProvider.getClosestServerProtocol(wrapper.user());
                  info.setServerProtocolVersion(serverProtocol);
                  List protocolPath = null;
                  if (info.getProtocolVersion() >= serverProtocol || Via.getPlatform().isOldClientsAllowed()) {
                     protocolPath = Via.getManager().getProtocolManager().getProtocolPath(info.getProtocolVersion(), serverProtocol);
                  }

                  ProtocolPipeline pipeline = wrapper.user().getProtocolInfo().getPipeline();
                  if (protocolPath != null) {
                     List protocols = new ArrayList(protocolPath.size());
                     Iterator var9 = protocolPath.iterator();

                     while(var9.hasNext()) {
                        ProtocolPathEntry entry = (ProtocolPathEntry)var9.next();
                        protocols.add(entry.getProtocol());
                        Via.getManager().getProtocolManager().completeMappingDataLoading(entry.getProtocol().getClass());
                     }

                     pipeline.add((Collection)protocols);
                     ProtocolVersion protocol = ProtocolVersion.getProtocol(serverProtocol);
                     wrapper.set(Type.VAR_INT, 0, protocol.getOriginalVersion());
                  }

                  pipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(serverProtocol));
                  if (state == 1) {
                     info.setState(State.STATUS);
                  } else if (state == 2) {
                     info.setState(State.LOGIN);
                  }

               }
            });
         }
      });
   }

   public boolean isBaseProtocol() {
      return true;
   }

   public void init(UserConnection userConnection) {
   }

   public void register(ViaProviders providers) {
      providers.register(VersionProvider.class, new BaseVersionProvider());
   }

   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
      super.transform(direction, state, packetWrapper);
      if (direction == Direction.SERVERBOUND && state == State.HANDSHAKE && packetWrapper.getId() != 0) {
         packetWrapper.user().setActive(false);
      }

   }
}
