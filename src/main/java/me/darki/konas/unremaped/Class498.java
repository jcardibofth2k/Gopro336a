package me.darki.konas;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class Class498
extends BlockPos {
    public boolean Field2057;

    public Class498(BlockPos blockPos, boolean bl) {
        super(blockPos);
        this.Field2057 = bl;
    }

    public Class498(BlockPos blockPos) {
        super(blockPos);
        this.Field2057 = false;
    }

    public boolean Method930() {
        return this.Field2057;
    }
}
