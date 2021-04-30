package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;

public class Class508 {
    public long Field1353;
    public ArrayList<Vec3d> Field1354;

    public void Method1362(long l) {
        this.Field1353 = l;
    }

    public long Method1363() {
        return this.Field1353;
    }

    public ArrayList<Vec3d> Method1364() {
        return this.Field1354;
    }

    public void Method1365(ArrayList<Vec3d> arrayList) {
        this.Field1354 = arrayList;
    }

    public Class508(long l, ArrayList<Vec3d> arrayList) {
        this.Field1353 = l;
        this.Field1354 = arrayList;
    }
}