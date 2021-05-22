package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import me.darki.konas.command.Logger;

public class Class315 extends Thread
{
    public Socket Field757;
    public DataInputStream Field758;

    @Override
    public void run() {
        while (this.Field757.isConnected()) {
            try {
                if (!this.Field758.readUTF().equals("MESSAGE")) {
                    continue;
                }
                Logger.Method1118("\u00c2§b" + this.Field758.readUTF() + ": " + this.Field758.readUTF());
            }
            catch (IOException ex) {}
        }
    }

    public Class315(final Socket field757, final DataInputStream field758) {
        this.Field757 = field757;
        this.Field758 = field758;
    }
}