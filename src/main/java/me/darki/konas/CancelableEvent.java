package me.darki.konas;

public class CancelableEvent {
    public boolean cancelled = false;

    public boolean isCanceled() {
        return this.cancelled;
    }

    public void Cancel() {
        this.cancelled = true;
    }

    public void setCanceled(boolean bl) {
        this.cancelled = bl;
    }
}
