package me.darki.konas.gui.screen;

import java.io.IOException;
import java.util.ArrayList;

import me.darki.konas.command.Command;
import me.darki.konas.managers.CommandManager;
import me.darki.konas.mixin.mixins.IGuiTextField;
import me.darki.konas.util.SyntaxChunk;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.opengl.GL11;

public class KonasGuiChat
extends GuiChat {
    public boolean Field1379 = true;

    public void drawScreen(int n, int n2, float f) {
        block2: {
            KonasGuiChat.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
            int n3 = ((IGuiTextField)this.inputField).getFontRenderer().getStringWidth(this.inputField.getText() + "") + 4;
            int n4 = this.inputField.getEnableBackgroundDrawing() ? this.inputField.y + (this.inputField.height - 8) / 2 : this.inputField.y;
            ((IGuiTextField)this.inputField).getFontRenderer().drawStringWithShadow(this.Method1450(this.inputField.getText()), (float)n3, (float)n4, 0x606060);
            this.inputField.drawTextBox();
            if (!this.Field1379) {
                return;
            }
            boolean bl = GL11.glIsEnabled(3042);
            boolean bl2 = GL11.glIsEnabled(3553);
            GL11.glDisable(3042);
            GL11.glDisable(3553);
            GL11.glColor3f(1.0f, 0.33333334f, 1.0f);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(1);
            int n5 = this.inputField.x - 2;
            int n6 = this.inputField.y - 2;
            int n7 = this.inputField.width;
            int n8 = this.inputField.height;
            GL11.glVertex2d(n5, n6);
            GL11.glVertex2d(n5 + n7, n6);
            GL11.glVertex2d(n5 + n7, n6);
            GL11.glVertex2d(n5 + n7, n6 + n8);
            GL11.glVertex2d(n5 + n7, n6 + n8);
            GL11.glVertex2d(n5, n6 + n8);
            GL11.glVertex2d(n5, n6 + n8);
            GL11.glVertex2d(n5, n6);
            GL11.glEnd();
            if (bl) {
                GL11.glEnable(3042);
            }
            if (!bl2) break block2;
            GL11.glEnable(3553);
        }
    }

    public KonasGuiChat(String string) {
        super(string);
    }

    public String Method1450(String string) {
        if (this.inputField.getText().length() < 1) {
            return "";
        }
        if (!string.startsWith(Command.Method190())) {
            return "";
        }
        String[] stringArray = this.inputField.getText().split(" ");
        if (stringArray.length > 1 || this.inputField.getText().length() > 2 && this.inputField.getText().endsWith(" ")) {
            Command command = CommandManager.Method208(stringArray[0]);
            if (command != null) {
                ArrayList<SyntaxChunk> arrayList = command.Method189();
                StringBuilder stringBuilder = new StringBuilder();
                int n = 0;
                for (SyntaxChunk syntaxChunk : arrayList) {
                    if (n == stringArray.length - 2) {
                        String string2 = syntaxChunk.Method940(stringArray[n + 1]);
                        int n2 = stringArray[n + 1].length();
                        String string3 = string2;
                        int n3 = n2;
                        int n4 = 0;
                        int n5 = Math.max(n3, n4);
                        String string4 = string3.substring(n5);
                        try {
                            string2 = string4;
                        }
                        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                            stringIndexOutOfBoundsException.printStackTrace();
                        }
                        stringBuilder.append(string2);
                    } else if (n >= stringArray.length - 1) {
                        stringBuilder.append(" " + syntaxChunk.Method941());
                    }
                    ++n;
                }
                return stringBuilder.toString();
            }
        } else if (stringArray.length == 1) {
            for (Command command : CommandManager.Method207()) {
                if (!command.Method186().toLowerCase().startsWith(stringArray[0].substring(1).toLowerCase())) continue;
                String string5 = command.Method186();
                string5 = string5.substring(stringArray[0].substring(1).length());
                return string5;
            }
        }
        return "";
    }

    public void keyTyped(char c, int n) throws IOException {
        if (n == 1) {
            this.mc.displayGuiScreen(null);
        } else if (n != 28 && n != 156) {
            if (n == 200) {
                this.getSentHistory(-1);
            } else if (n == 208) {
                this.getSentHistory(1);
            } else if (n == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            } else if (n == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            } else if (n == 15) {
                if (this.inputField.getText().length() > 1) {
                    String[] stringArray = this.inputField.getText().replaceAll("([\\s])\\1+", "$1").split(" ");
                    System.out.println(stringArray.length);
                    if (stringArray.length > 1) {
                        SyntaxChunk syntaxChunk;
                        Command command = CommandManager.Method208(stringArray[0]);
                        if (command != null && stringArray.length - 2 <= command.Method189().size() - 1 && (syntaxChunk = command.Method189().get(stringArray.length - 2)) != null) {
                            String string = stringArray[stringArray.length - 1];
                            String string2 = syntaxChunk.Method940(string);
                            String string3 = this.inputField.getText();
                            string3 = string3.substring(0, string3.length() - string.length());
                            string3 = string3.concat(string2);
                            this.inputField.setText(string3);
                        }
                    } else if (stringArray.length == 1) {
                        for (Command command : CommandManager.Method207()) {
                            String string = command.Method198(stringArray[0].substring(1).toLowerCase());
                            if (string == null) continue;
                            this.inputField.setText(Command.Method190() + string);
                            break;
                        }
                    }
                }
            } else {
                this.inputField.textboxKeyTyped(c, n);
            }
        } else {
            String string = this.inputField.getText().trim();
            if (!string.isEmpty()) {
                this.sendChatMessage(string);
                this.mc.ingameGUI.getChatGUI().addToSentMessages(string);
            }
            this.mc.displayGuiScreen(null);
        }
        this.Field1379 = this.inputField.getText().replaceAll(" ", "").startsWith(Command.Method190());
    }
}