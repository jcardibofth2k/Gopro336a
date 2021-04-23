package me.darki.konas.command.commands;

import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import me.darki.konas.util.ChatUtil;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.config.Config;
import java.io.File;
import me.darki.konas.command.Command;

public class bookCommand extends Command
{
    public static File Field2501;
    
    public String[] Method2157(final String s, final int n) {
        final String[] array = new String[(int)Math.ceil(s.length() / (double)n)];
        int n2 = 0;
        final int n3 = array.length - 1;
        for (int i = 0; i < n3; ++i) {
            array[i] = s.substring(n2, n2 + n);
            n2 += n;
        }
        array[n3] = s.substring(n2);
        return array;
    }
    
    static {
        bookCommand.Field2501 = new File(Config.KONAS_FOLDER, "books");
    }
    
    public bookCommand() {
        super("book", "Lets you sign a book with the text in a file", new SyntaxChunk("<file>"));
    }
    
    @Override
    public void Method174(final String[] array) {
        if (array.length != this.Method189().size() + 1) {
            ChatUtil.Method1034(this.Method191());
            return;
        }
        if (!bookCommand.Field2501.exists()) {
            bookCommand.Field2501.mkdir();
        }
        final File file = new File(bookCommand.Field2501, array[1].replaceAll(".txt", "") + ".txt");
        if (file.exists()) {
            String concat = "";
            Label_0309: {
                IOException ex;
                try {
                    String line;
                    while ((line = new BufferedReader(new FileReader(file)).readLine()) != null) {
                        concat = concat.concat(line);
                    }
                    break Label_0309;
                }
                catch (IOException ex2) {
                    ex = ex2;
                }
                ex.printStackTrace();
            }
            if (bookCommand.Field123.player.getHeldItemMainhand().getItem() == Items.WRITABLE_BOOK) {
                final ItemStack heldItemMainhand = bookCommand.Field123.player.getHeldItemMainhand();
                bookCommand.Field123.player.openBook(heldItemMainhand, EnumHand.MAIN_HAND);
                final NBTTagList list = new NBTTagList();
                final String[] method2157 = this.Method2157(concat, 254);
                for (int length = method2157.length, i = 0; i < length; ++i) {
                    list.appendTag(new NBTTagString(method2157[i]));
                }
                if (heldItemMainhand.hasTagCompound()) {
                    heldItemMainhand.getTagCompound().setTag("pages", list);
                }
                else {
                    heldItemMainhand.setTagInfo("pages", list);
                }
                heldItemMainhand.setTagInfo("author", new NBTTagString(bookCommand.Field123.player.getName()));
                heldItemMainhand.setTagInfo("title", new NBTTagString(array[1].trim()));
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.writeItemStack(heldItemMainhand);
                bookCommand.Field123.getConnection().sendPacket(new CPacketCustomPayload("MC|BSign", packetBuffer));
                bookCommand.Field123.displayGuiScreen(null);
                ChatUtil.Method1033("Signed book with name (h)%s(r)!", array[1]);
            }
            else {
                ChatUtil.Method1034("You need to be holding a book and quill.");
            }
        }
        else {
            ChatUtil.Method1034("File (h)%s(r) does not exist.", array[1]);
        }
    }
}
