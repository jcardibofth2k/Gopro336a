package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.block.Block;

public class Class443 {
    public List<Block> Field624 = new ArrayList<Block>();
    public List<String> Field625 = new ArrayList<String>();

    public boolean Method677(String string) {
        return this.Field625.remove(string.toUpperCase(Locale.ENGLISH));
    }

    public Class443(String ... stringArray) {
        for (String string : stringArray) {
            if (this.Field625.contains(string.toUpperCase(Locale.ENGLISH)) || Block.getBlockFromName((String)string) == null) continue;
            this.Field625.add(string.toUpperCase(Locale.ENGLISH));
        }
    }

    public static void Method678(List list, Block block) {
        String string = block.getRegistryName().getPath();
        if (string != null) {
            list.add(string);
        }
    }

    public void Method679(ArrayList<String> arrayList) {
        for (String string : arrayList) {
            if (this.Field625.contains(string.toUpperCase(Locale.ENGLISH)) || Block.getBlockFromName((String)string) == null) continue;
            this.Field625.add(string.toUpperCase(Locale.ENGLISH));
        }
    }

    public void Method680() {
        this.Field624.clear();
        this.Field625.forEach(this::Method683);
    }

    public boolean Method681(String string) {
        if (!this.Field625.contains(string.toUpperCase(Locale.ENGLISH)) && Block.getBlockFromName((String)string) != null) {
            this.Field625.add(string.toUpperCase(Locale.ENGLISH));
            return true;
        }
        return false;
    }

    public Class443(ArrayList<String> arrayList) {
        for (String string : arrayList) {
            if (this.Field625.contains(string.toUpperCase(Locale.ENGLISH)) || Block.getBlockFromName((String)string) == null) continue;
            this.Field625.add(string.toUpperCase(Locale.ENGLISH));
        }
    }

    public List<Block> Method682() {
        return this.Field624;
    }

    public void Method683(String string) {
        Block block = Block.getBlockFromName((String)string);
        if (block != null) {
            this.Field624.add(block);
        }
    }

    public List<String> Method684() {
        ArrayList<String> arrayList = new ArrayList<String>();
        this.Field624.forEach(arg_0 -> Class443.Method678(arrayList, arg_0));
        return arrayList;
    }
}