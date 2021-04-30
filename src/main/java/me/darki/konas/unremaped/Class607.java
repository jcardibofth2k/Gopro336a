package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import me.darki.konas.module.ModuleManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Class607
extends Thread {
    public Socket Field124;
    public DataInputStream Field125;
    public DataOutputStream Field126;
    @Nullable
    public String Field127;
    @NotNull
    public static Class607 Field128;
    public static Class608 Field129;

    @Override
    public void run() {
        ModuleManager.Field1691 = "cool";
    }

    public String Method199(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = ((CharSequence)string).length();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append((char)(string.charAt(i) ^ string2.charAt(i % string2.length())));
        }
        return stringBuilder.toString();
    }

    public static void Method200(Class607 class607) {
        Field128 = class607;
    }

    @Nullable
    public String Method201() {
        return this.Field127;
    }

    public void Method202(@Nullable String string) {
        this.Field127 = string;
    }

    static {
        Field129 = new Class608(null);
        Field128 = new Class607();
    }

    @NotNull
    public String Method203(@NotNull String string) {
        String string2;
        block0: {
            string2 = this.Field127;
            if (string2 != null) break block0;
            Intrinsics.throwNpe();
        }
        return this.Method199(string, string2);
    }

    public static Class607 Method204() {
        return Field128;
    }

    @NotNull
    public String Method205(@NotNull String string) {
        String string2 = string;
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Base64.Encoder encoder = Base64.getEncoder();
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string2.getBytes(charset);
        return encoder.encodeToString(messageDigest.digest(byArray));
    }
}