package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import org.jetbrains.annotations.NotNull;

public class Class544
extends TextComponentBase {
    public String Field1062;

    @NotNull
    public ITextComponent createCopy() {
        return new Class532(this.Field1062);
    }

    @NotNull
    public String getUnformattedComponentText() {
        return this.Field1062;
    }

    public Class544(String string) {
        Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
        Matcher matcher = pattern.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String string2 = "\u00a7" + matcher.group().substring(1);
            matcher.appendReplacement(stringBuffer, string2);
        }
        matcher.appendTail(stringBuffer);
        this.Field1062 = stringBuffer.toString();
    }
}