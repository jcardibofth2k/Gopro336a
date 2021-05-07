package me.darki.konas.unremaped;

import me.darki.konas.*;
import com.google.common.base.Charsets;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.util.UUIDTypeAdapter;
import java.net.InetAddress;
import java.util.UUID;

import me.darki.konas.mixin.mixins.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Class540 {
    public static YggdrasilAuthenticationService Field1068 = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), UUID.randomUUID().toString());
    public static YggdrasilUserAuthentication Field1069 = (YggdrasilUserAuthentication)Field1068.createUserAuthentication(Agent.MINECRAFT);
    public static YggdrasilMinecraftSessionService Field1070 = (YggdrasilMinecraftSessionService)Field1068.createMinecraftSessionService();

    public static boolean Method1092() {
        return Minecraft.getMinecraft().getSession().getProfile().getId().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + Minecraft.getMinecraft().getSession().getUsername()).getBytes(Charsets.UTF_8)));
    }

    public static boolean Method1093(Class68 class68, String string, String string2) {
        Class512 class512 = Class509.Method1356(string, string2);
        if (class512 == null) {
            return false;
        }
        class68.Method310(class512.Method1305());
        class68.Method304(class512.Method1307());
        class68.Method306(class512.Method1310());
        ((IMinecraft)Minecraft.getMinecraft()).setSession(new Session(class512.Method1307(), class512.Method1310(), class512.Method1305(), "mojang"));
        return true;
    }

    public static boolean Method1094(String string) throws IllegalArgumentException {
        UUID uUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(Charsets.UTF_8));
        ((IMinecraft)Minecraft.getMinecraft()).setSession(new Session(string, uUID.toString(), "invalid", "legacy"));
        return true;
    }

    public static boolean Method1095() {
        return Field1068.getClientToken() != null;
    }

    public static boolean Method1096() {
        Minecraft minecraft;
        try {
            minecraft = Minecraft.getMinecraft();
        }
        catch (Exception exception) {
            return false;
        }
        Session session = minecraft.getSession();
        GameProfile gameProfile = session.getProfile();
        GameProfile gameProfile2 = gameProfile;
        Minecraft minecraft2 = Minecraft.getMinecraft();
        Session session2 = minecraft2.getSession();
        String string = session2.getToken();
        String string2 = string;
        UUID uUID = UUID.randomUUID();
        String string3 = uUID.toString();
        String string4 = string3;
        YggdrasilMinecraftSessionService yggdrasilMinecraftSessionService = Field1070;
        GameProfile gameProfile3 = gameProfile2;
        String string5 = string2;
        String string6 = string4;
        yggdrasilMinecraftSessionService.joinServer(gameProfile3, string5, string6);
        YggdrasilMinecraftSessionService yggdrasilMinecraftSessionService2 = Field1070;
        GameProfile gameProfile4 = gameProfile2;
        String string7 = string4;
        InetAddress inetAddress = null;
        GameProfile gameProfile5 = yggdrasilMinecraftSessionService2.hasJoinedServer(gameProfile4, string7, inetAddress);
        boolean bl = gameProfile5.isComplete();
        if (bl) {
            return true;
        }
        return false;
    }

    public static boolean Method1097(Class68 class68, String string, String string2) {
        YggdrasilUserAuthentication yggdrasilUserAuthentication;
        Field1069.setUsername(string);
        Field1069.setPassword(string2);
        try {
            yggdrasilUserAuthentication = Field1069;
        }
        catch (AuthenticationException authenticationException) {
            return false;
        }
        yggdrasilUserAuthentication.logIn();
        String string3 = Field1069.getSelectedProfile().getName();
        String string4 = UUIDTypeAdapter.fromUUID((UUID)Field1069.getSelectedProfile().getId());
        String string5 = Field1069.getAuthenticatedToken();
        String string6 = Field1069.getUserType().getName();
        class68.Method306(string4);
        class68.Method310(string5);
        class68.Method304(string3);
        ((IMinecraft)Minecraft.getMinecraft()).setSession(new Session(string3, string4, string5, string6));
        Field1069.logOut();
        return true;
    }
}