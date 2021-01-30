package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;

public class ProtocolInfoImpl implements ProtocolInfo {
   private final UserConnection connection;
   private State state;
   private int protocolVersion;
   private int serverProtocolVersion;
   private String username;
   private UUID uuid;
   private ProtocolPipeline pipeline;

   public ProtocolInfoImpl(UserConnection connection) {
      this.state = State.HANDSHAKE;
      this.protocolVersion = -1;
      this.serverProtocolVersion = -1;
      this.connection = connection;
   }

   public State getState() {
      return this.state;
   }

   public void setState(State state) {
      this.state = state;
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public void setProtocolVersion(int protocolVersion) {
      ProtocolVersion protocol = ProtocolVersion.getProtocol(protocolVersion);
      this.protocolVersion = protocol.getVersion();
   }

   public int getServerProtocolVersion() {
      return this.serverProtocolVersion;
   }

   public void setServerProtocolVersion(int serverProtocolVersion) {
      ProtocolVersion protocol = ProtocolVersion.getProtocol(serverProtocolVersion);
      this.serverProtocolVersion = protocol.getVersion();
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public void setUuid(UUID uuid) {
      this.uuid = uuid;
   }

   public ProtocolPipeline getPipeline() {
      return this.pipeline;
   }

   public void setPipeline(ProtocolPipeline pipeline) {
      this.pipeline = pipeline;
   }

   public UserConnection getUser() {
      return this.connection;
   }

   public String toString() {
      return "ProtocolInfo{state=" + this.state + ", protocolVersion=" + this.protocolVersion + ", serverProtocolVersion=" + this.serverProtocolVersion + ", username='" + this.username + '\'' + ", uuid=" + this.uuid + '}';
   }
}
