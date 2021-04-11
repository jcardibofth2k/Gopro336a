package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class133;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.settingEnums.ExtraTabMode;
import me.darki.konas.command.Command;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ExtraTab
extends Module {
    public static Setting<Integer> maxSize = new Setting<>("MaxSize", 200, 400, 10, 10);
    public static Setting<ExtraTabMode> sortMode = new Setting<>("SortMode", ExtraTabMode.VANILLA);
    public static Setting<Boolean> onlyFriends = new Setting<>("OnlyFriends", false);

    @Subscriber
    public void Method1444(Class133 class133) {
        block0: {
            if (!Class492.Method1989(class133.Method1953())) break block0;
            class133.Method1954(Command.Field122 + "b" + class133.Method1953());
        }
    }

    public static boolean Method1445(NetworkPlayerInfo networkPlayerInfo) {
        return onlyFriends.getValue() == false || Class492.Method1988(networkPlayerInfo.getGameProfile().getId().toString());
    }

    public static boolean Method1446(NetworkPlayerInfo networkPlayerInfo) {
        return onlyFriends.getValue() == false || Class492.Method1988(networkPlayerInfo.getGameProfile().getId().toString());
    }

    public ExtraTab() {
        super("ExtraTab", Category.RENDER, "TabPlus", "MoreTab");
    }

    public static Integer Method1447(NetworkPlayerInfo networkPlayerInfo) {
        return networkPlayerInfo.getGameProfile().getName().length();
    }

    public static List<NetworkPlayerInfo> Method1448(List<NetworkPlayerInfo> list, List<NetworkPlayerInfo> list2) {
        if (ModuleManager.getModuleByClass(ExtraTab.class).isEnabled()) {
            if (sortMode.getValue() == ExtraTabMode.VANILLA) {
                return list.stream().filter(ExtraTab::Method1446).limit(maxSize.getValue().intValue()).collect(Collectors.toList());
            }
            if (sortMode.getValue() == ExtraTabMode.PING) {
                return list.stream().filter(ExtraTab::Method1449).sorted(Comparator.comparing(NetworkPlayerInfo::func_178853_c)).limit(maxSize.getValue().intValue()).collect(Collectors.toList());
            }
            return list.stream().filter(ExtraTab::Method1445).sorted(Comparator.comparing(ExtraTab::Method1447)).limit(maxSize.getValue().intValue()).collect(Collectors.toList());
        }
        return list2;
    }

    public static boolean Method1449(NetworkPlayerInfo networkPlayerInfo) {
        return onlyFriends.getValue() == false || Class492.Method1988(networkPlayerInfo.getGameProfile().getId().toString());
    }
}