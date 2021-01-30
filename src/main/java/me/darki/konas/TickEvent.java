package me.darki.konas;

public class TickEvent {
    public static TickEvent Field198 = new TickEvent();
    public net.minecraftforge.fml.common.gameevent.TickEvent.Phase Field199;

    public net.minecraftforge.fml.common.gameevent.TickEvent.Phase Method324() {
        return this.Field199;
    }

    public static TickEvent Method325(net.minecraftforge.fml.common.gameevent.TickEvent.Phase phase) {
        TickEvent.Field198.Field199 = phase;
        return Field198;
    }
}
