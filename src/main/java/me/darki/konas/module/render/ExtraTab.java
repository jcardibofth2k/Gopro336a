package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.darki.konas.Category;
import me.darki.konas.Class133;
import me.darki.konas.Class167;
import me.darki.konas.Class492;
import me.darki.konas.Class514;
import me.darki.konas.command.Command;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ExtraTab
extends Module {
    public static Setting<Integer> Field1376 = new Setting<>("MaxSize", 200, 400, 10, 10);
    public static Setting<Class514> Field1377 = new Setting<>("SortMode", Class514.VANILLA);
    public static Setting<Boolean> Field1378 = new Setting<>("OnlyFriends", false);

    @Subscriber
    public void Method1444(Class133 class133) {
        block0: {
            if (!Class492.Method1989(class133.Method1953())) break block0;
            class133.Method1954(Command.Field122 + "b" + class133.Method1953());
        }
    }

    public static boolean Method1445(NetworkPlayerInfo networkPlayerInfo) {
        return (Boolean)Field1378.getValue() == false || Class492.Method1988(networkPlayerInfo.getGameProfile().getId().toString());
    }

    public static boolean Method1446(NetworkPlayerInfo networkPlayerInfo) {
        return (Boolean)Field1378.getValue() == false || Class492.Method1988(networkPlayerInfo.getGameProfile().getId().toString());
    }

    public ExtraTab() {
        super("ExtraTab", Category.RENDER, "TabPlus", "MoreTab");
    }

    public static Integer Method1447(NetworkPlayerInfo networkPlayerInfo) {
        return networkPlayerInfo.getGameProfile().getName().length();
    }

    public static List<NetworkPlayerInfo> Method1448(List<NetworkPlayerInfo> list, List<NetworkPlayerInfo> list2) {
        if (Class167.Method1610(ExtraTab.class).Method1651()) {
            if (Field1377.getValue() == Class514.VANILLA) {
                return list.stream().filter(ExtraTab::Method1446).limit(((Integer)Field1376.getValue()).intValue()).collect(Collectors.toList());
            }
            if (Field1377.getValue() == Class514.PING) {
                return list.stream().filter(ExtraTab::Method1449).sorted(Comparator.comparing(NetworkPlayerInfo::func_178853_c)).limit(((Integer)Field1376.getValue()).intValue()).collect(Collectors.toList());
            }
            return list.stream().filter(ExtraTab::Method1445).sorted(Comparator.comparing(ExtraTab::Method1447)).limit(((Integer)Field1376.getValue()).intValue()).collect(Collectors.toList());
        }
        return list2;
    }

    public static boolean Method1449(NetworkPlayerInfo networkPlayerInfo) {
        return (Boolean)Field1378.getValue() == false || Class492.Method1988(networkPlayerInfo.getGameProfile().getId().toString());
    }
}
