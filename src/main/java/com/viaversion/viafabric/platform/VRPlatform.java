package com.viaversion.viafabric.platform;

import com.viaversion.viafabric.ViaFabric;
import com.viaversion.viafabric.util.FutureTaskId;
import com.viaversion.viafabric.util.JLoggerToLog4j;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

public class VRPlatform implements ViaPlatform {
   public Logger logger = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
   public VRViaConfig config;
   public File dataFolder;
   public ViaAPI api;

   public boolean isOldClientsAllowed() {
      Object var10000 = null;
      return true;
   }

   public ConfigurationProvider getConfigurationProvider() {
      Object var10000 = null;
      return this.config;
   }

   public FutureTaskId runSync(Runnable runnable) {
      Object var10000 = null;
      return new FutureTaskId(ViaFabric.getInstance().getEventLoop().submit(runnable).addListener(this.errorLogger()));
   }

   public static Void lambda$runAsync$0(Throwable throwable) {
      Object var10000 = null;
      if (!(throwable instanceof CancellationException)) {
         throwable.printStackTrace();
      }

      return null;
   }

   public PlatformTask runSync(Runnable runnable, long ticks) {
      Object var10000 = null;
      return new FutureTaskId(ViaFabric.getInstance().getEventLoop().schedule(this::lambda$runSync$1, ticks * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
   }

   public String getPlatformVersion() {
      Object var10000 = null;
      return "340";
   }

   public FutureTaskId runAsync(Runnable runnable) {
      Object var10000 = null;
      return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaFabric.getInstance().getAsyncExecutor()).exceptionally(VRPlatform::lambda$runAsync$0));
   }

   public String getPlatformName() {
      Object var10000 = null;
      return "ViaFabric";
   }

   public boolean kickPlayer(UUID var1, String var2) {
      Object var10000 = null;
      return false;
   }

   public void onReload() {
      Object var10000 = null;
   }

   public File getDataFolder() {
      Object var10000 = null;
      return this.dataFolder;
   }

   public PlatformTask runSync(Runnable var1) {
      Object var10000 = null;
      return this.runSync(var1);
   }

   public ViaVersionConfig getConf() {
      Object var10000 = null;
      return this.config;
   }

   public ViaAPI getApi() {
      Object var10000 = null;
      return this.api;
   }

   public void lambda$runRepeatingSync$2(Runnable runnable) {
      Object var10000 = null;
      this.runSync(runnable);
   }

   public void sendMessage(UUID var1, String var2) {
      Object var10000 = null;
   }

   public GenericFutureListener errorLogger() {
      Object var10000 = null;
      return VRPlatform::lambda$errorLogger$3;
   }

   public static void lambda$errorLogger$3(Future future) throws Exception {
      Object var10000 = null;
      if (!future.isCancelled() && future.cause() != null) {
         future.cause().printStackTrace();
      }

   }

   public ViaCommandSender[] getServerPlayers() {
      Object var10000 = null;
      return new ViaCommandSender[1337];
   }

   public PlatformTask runAsync(Runnable var1) {
      Object var10000 = null;
      return this.runAsync(var1);
   }

   public FutureTaskId lambda$runSync$1(Runnable runnable) throws Exception {
      Object var10000 = null;
      return this.runSync(runnable);
   }

   public VRPlatform(File dataFolder) {
      Path configDir = dataFolder.toPath().resolve("ViaVersion");
      this.config = new VRViaConfig(configDir.resolve("viaversion.yml").toFile());
      this.dataFolder = configDir.toFile();
      this.api = new VRViaAPI();
   }

   public Logger getLogger() {
      Object var10000 = null;
      return this.logger;
   }

   public static String legacyToJson(String legacy) {
      Object var10000 = null;
      return (String)GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(legacy));
   }

   public ViaCommandSender[] getOnlinePlayers() {
      Object var10000 = null;
      return new ViaCommandSender[1337];
   }

   public boolean isPluginEnabled() {
      Object var10000 = null;
      return true;
   }

   public String getPluginVersion() {
      Object var10000 = null;
      return "4.0.0";
   }

   public JsonObject getDump() {
      Object var10000 = null;
      JsonObject var1 = new JsonObject();
      return var1;
   }

   public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
      Object var10000 = null;
      return new FutureTaskId(ViaFabric.getInstance().getEventLoop().scheduleAtFixedRate(this::lambda$runRepeatingSync$2, 0L, ticks * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
   }
}
