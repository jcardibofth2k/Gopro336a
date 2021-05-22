package me.darki.konas.unremaped;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class DiscordRpcUtil {
    public static String Field517 = "32597";
    public String Field518 = "813675760953851924";
    public Minecraft Field519 = Minecraft.getMinecraft();
    public DiscordRPC Field520 = DiscordRPC.INSTANCE;
    public DiscordRichPresence Field521 = new DiscordRichPresence();
    public String Field522;
    public String Field523;
    public boolean Field524 = false;
    public boolean Field525 = false;
    public boolean Field526 = false;

    public void Method587() {
        if (this.Field524) {
            return;
        }
        DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
        discordEventHandlers.disconnected = DiscordRpcUtil::Method590;
        this.Field520.Discord_Initialize("813675760953851924", discordEventHandlers, true, "");
        this.Field521.startTimestamp = System.currentTimeMillis() / 1000L;
        this.Field521.details = "0.10.2" + (this.Field525 ? " (Cute)" : "");
        this.Field521.state = "Main Menu";
        this.Field520.Discord_UpdatePresence(this.Field521);
        new Thread(this::Method588, "Discord-RPC-Callback-Handler").start();
        this.Field524 = true;
    }

    /*
     * Unable to fully structure code
     */
    public void Method588() {
        while (!Thread.currentThread().isInterrupted()) {
            block71: {
                block70: {
                    v0 = this.Field520;
                    v0.Discord_RunCallbacks();
                    v1 = this.Field519;
                    v2 = v1.isIntegratedServerRunning();
                    if (!v2 && this.Field519.world != null) ** GOTO lbl37
                    v3 = this;
                    v4 = v5;
                    v6 = v5;
                    v4();
                    v7 = "0.10.2";
                    v8 = v6.append(v7);
                    if (!this.Field525) ** GOTO lbl28
                    v9 = " (Cute)";
                    break block70;
lbl28:
                    // 1 sources

                    v9 = "";
                }
                v10 = v8.append(v9);
                v11 = v10.toString();
                v3.Field522 = v11;
                ** GOTO lbl64
lbl37:
                // 1 sources

                v12 = this;
                v13 = v14;
                v15 = v14;
                v13();
                v16 = "0.10.2 - Playing";
                v17 = v15.append(v16);
                if (!this.Field525) ** GOTO lbl52
                v18 = " Cute ";
                break block71;
lbl52:
                // 1 sources

                v18 = " ";
            }
            v19 = v17.append(v18);
            v20 = "Multiplayer";
            v21 = v19.append(v20);
            v22 = v21.toString();
            v12.Field522 = v22;
lbl64:
            // 2 sources

            this.Field523 = "";
            v23 = this.Field519;
            v24 = v23.isIntegratedServerRunning();
            if (!v24) ** GOTO lbl76
            v25 = this;
            v26 = "Playing Singleplayer";
            v25.Field523 = v26;
            ** GOTO lbl118
lbl76:
            // 1 sources

            v27 = this.Field519;
            v28 = v27.getCurrentServerData();
            if (v28 == null) ** GOTO lbl113
            v29 = this.Field519;
            v30 = v29.getCurrentServerData();
            v31 = v30.serverIP;
            v32 = "";
            v33 = v31.equals(v32);
            if (v33) ** GOTO lbl118
            v34 = this;
            v35 = v36;
            v37 = v36;
            v35();
            v38 = "Playing on ";
            v39 = v37.append(v38);
            v40 = this.Field519;
            v41 = v40.getCurrentServerData();
            v42 = v41.serverIP;
            v43 = v39.append(v42);
            v44 = v43.toString();
            v34.Field523 = v44;
            ** GOTO lbl118
lbl113:
            // 1 sources

            v45 = this;
            v46 = "Main Menu";
            v45.Field523 = v46;
lbl118:
            // 4 sources

            v47 = this.Field522;
            v48 = this.Field521.details;
            v49 = v47.equals(v48);
            if (!v49) ** GOTO lbl130
            v50 = this.Field523;
            v51 = this.Field521.state;
            v52 = v50.equals(v51);
            if (v52) ** GOTO lbl135
lbl130:
            // 2 sources

            v53 = this.Field521;
            v54 = System.currentTimeMillis();
            v53.startTimestamp = v54 / 1000L;
lbl135:
            // 2 sources

            this.Field521.details = this.Field522;
            this.Field521.state = this.Field523;
            v55 = this.Field520;
            v56 = this.Field521;
            try {
                v55.Discord_UpdatePresence(v56);
            }
            catch (Exception var1_2) {
                var1_2.printStackTrace();
            }
            v57 = 5000L;
            try {
                Thread.sleep(v57);
            }
            catch (InterruptedException var1_3) {
                var1_3.printStackTrace();
            }
        }
    }

    public void Method589(Boolean bl) {
        this.Field526 = bl;
        this.Method592();
        this.Field520.Discord_UpdatePresence(this.Field521);
    }

    public static void Method590(int n, String string) {
        System.out.println("Discord RPC disconnected, var1: " + n + ", var2: " + string);
    }

    public void Method591(Boolean bl) {
        this.Field525 = bl;
        this.Method592();
        this.Method593();
        this.Field520.Discord_UpdatePresence(this.Field521);
    }

    public void Method592() {
        if (this.Field526) {
            this.Field521.largeImageText = "discord.gg/gpVZ4Y6cpq";
            return;
        }
        this.Field521.largeImageText = this.Field525 ? "Cute Konas on top" : "Konas on top";
    }

    public void Method593() {
        this.Field521.largeImageKey = this.Field525 ? "cutekonas" : "konas";
    }

    public void Method594() {
        this.Field520.Discord_Shutdown();
        this.Field524 = false;
    }
}