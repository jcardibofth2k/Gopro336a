package me.darki.konas.unremaped;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import me.darki.konas.command.Logger;

public class Class315
extends Thread {
    public Socket Field757;
    public DataInputStream Field758;

    @Override
    public void run() {
        while (this.Field757.isConnected()) {
            StringBuilder stringBuilder;
            DataInputStream dataInputStream;
            try {
                dataInputStream = this.Field758;
            }
            catch (IOException object) {}
            String string = dataInputStream.readUTF();
            Object object = object = string;
            String string2 = "MESSAGE";
            boolean bl = ((String)object).equals(string2);
            if (!bl) continue;
            DataInputStream dataInputStream2 = this.Field758;
            String string3 = dataInputStream2.readUTF();
            String string4 = string3;
            DataInputStream dataInputStream3 = this.Field758;
            String string5 = dataInputStream3.readUTF();
            String string6 = string5;
            StringBuilder stringBuilder2 = stringBuilder;
            StringBuilder stringBuilder3 = stringBuilder;
            stringBuilder2();
            String string7 = "\u00c2\u00a7b";
            StringBuilder stringBuilder4 = stringBuilder3.append(string7);
            String string8 = string4;
            StringBuilder stringBuilder5 = stringBuilder4.append(string8);
            String string9 = ": ";
            StringBuilder stringBuilder6 = stringBuilder5.append(string9);
            String string10 = string6;
            StringBuilder stringBuilder7 = stringBuilder6.append(string10);
            String string11 = stringBuilder7.toString();
            Logger.Method1118(string11);
        }
    }

    public Class315(Socket socket, DataInputStream dataInputStream) {
        this.Field757 = socket;
        this.Field758 = dataInputStream;
    }
}
