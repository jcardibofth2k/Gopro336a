package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import org.jetbrains.annotations.NotNull;

public class Class532
extends TextComponentBase {
    public String Field1179;

    public Class532(String string) {
        Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
        Matcher matcher = pattern.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String string2 = "\u00a7" + matcher.group().substring(1);
            matcher.appendReplacement(stringBuffer, string2);
        }
        matcher.appendTail(stringBuffer);
        this.Field1179 = stringBuffer.toString();
    }

    @NotNull
    public ITextComponent createCopy() {
        return new Class532(this.Field1179);
    }

    @NotNull
    public String getUnformattedComponentText() {
        return this.Field1179;
    }
}