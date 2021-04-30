package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import java.util.regex.Matcher;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import java.util.regex.Pattern;

public class Class81
{
    public static Pattern Field273;
    public static Class81 Field274;
    
    @Subscriber(priority = -1)
    public void Method448(final ClientChatReceivedEvent clientChatReceivedEvent) {
        try {
            final Matcher matcher = Class81.Field273.matcher(clientChatReceivedEvent.getMessage().getUnformattedText());
            while (matcher.find()) {
                if (Class484.Method2071(matcher.group(1))) {
                    clientChatReceivedEvent.setCanceled(true);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    static {
        Class81.Field273 = Pattern.compile("^<([a-zA-Z0-9_]{3,16})> (.+)$");
        Class81.Field274 = new Class81();
    }
}