package me.darki.konas.util;

import net.minecraft.client.Minecraft;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import com.google.gson.stream.JsonToken;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.util.Collections;
import me.darki.konas.command.Logger;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.util.Set;

public class FriendSyncUtil
{
    public static String Field2644;
    public static String Field2645;
    public static String Field2646;
    public static String Field2647;
    
    public static Set<String> Method2311() {
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(FriendSyncUtil.Field2645));
        }
        catch (FileNotFoundException ex) {
            Logger.Method1119("Unable to load Future friends file: " + ex.getMessage());
            return Collections.emptySet();
        }
        final HashSet<String> set = new HashSet<String>();
        try {
            jsonReader.beginArray();
            while (jsonReader.peek() != JsonToken.END_ARRAY) {
                jsonReader.beginObject();
                if (jsonReader.nextName().equals("friend-label")) {
                    set.add(jsonReader.nextString());
                    jsonReader.nextName();
                    jsonReader.nextString();
                }
                jsonReader.endObject();
            }
            jsonReader.endArray();
        }
        catch (IOException ex2) {
            Logger.Method1119("Error while loading Future friends file: " + ex2.getMessage());
            return Collections.emptySet();
        }
        return set;
    }
    
    public static boolean Method2312(final Set<String> set) {
        return false;
    }
    
    public static Set<String> Method2313() {
        final JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject;
        try {
            jsonObject = (JsonObject)jsonParser.parse(new FileReader(FriendSyncUtil.Field2646));
        }
        catch (Exception ex) {
            Logger.Method1119("Unable to load Pyro friends file: " + ex.getMessage());
            return Collections.emptySet();
        }
        final HashSet<String> set = new HashSet<String>();
        try {
            final Iterator iterator = jsonObject.getAsJsonArray("friends").iterator();
            while (iterator.hasNext()) {
                set.add(iterator.next().getAsJsonObject().getAsJsonPrimitive("c").getAsString());
            }
        }
        catch (Exception ex2) {
            Logger.Method1119("Error while loading Pyro friends file: " + ex2.getMessage());
            return Collections.emptySet();
        }
        return set;
    }
    
    static {
        FriendSyncUtil.Field2644 = System.getProperty("file.separator");
        FriendSyncUtil.Field2645 = System.getProperty("user.home") + FriendSyncUtil.Field2644 + "Future" + FriendSyncUtil.Field2644 + "friends.json";
        FriendSyncUtil.Field2646 = Minecraft.getMinecraft().mcDataDir + FriendSyncUtil.Field2644 + "pyro" + FriendSyncUtil.Field2644 + "friends.json";
        FriendSyncUtil.Field2647 = Minecraft.getMinecraft().mcDataDir + FriendSyncUtil.Field2644 + "rusherhack" + FriendSyncUtil.Field2644 + "friends.json";
    }
    
    public static Set<String> Method2314() {
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(FriendSyncUtil.Field2647));
        }
        catch (FileNotFoundException ex) {
            Logger.Method1119("Unable to load Rusherhack friends file: " + ex.getMessage());
            return Collections.emptySet();
        }
        final HashSet<String> set = new HashSet<String>();
        try {
            jsonReader.beginArray();
            while (jsonReader.peek() != JsonToken.END_ARRAY) {
                jsonReader.beginObject();
                if (jsonReader.nextName().equals("name")) {
                    set.add(jsonReader.nextString());
                }
                jsonReader.endObject();
            }
            jsonReader.endArray();
        }
        catch (IOException ex2) {
            Logger.Method1119("Error while loading Rusherhack friends file: " + ex2.getMessage());
            return Collections.emptySet();
        }
        return set;
    }
}