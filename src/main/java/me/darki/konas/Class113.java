package me.darki.konas;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;

public class Class113
extends CancelableEvent {
    public EntityRenderer Field2449;
    public Entity Field2450;
    public IBlockState Field2451;
    public double Field2452;

    public Class113(EntityRenderer entityRenderer, Entity entity, IBlockState iBlockState, double d) {
        this.Field2449 = entityRenderer;
        this.Field2450 = entity;
        this.Field2451 = iBlockState;
        this.Field2452 = d;
    }

    public double Method216() {
        return this.Field2452;
    }

    public EntityRenderer Method2111() {
        return this.Field2449;
    }

    public IBlockState Method348() {
        return this.Field2451;
    }

    public Entity Method286() {
        return this.Field2450;
    }
}
