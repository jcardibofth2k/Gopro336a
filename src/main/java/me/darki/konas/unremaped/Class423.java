package me.darki.konas.unremaped;

import java.util.ArrayList;

import me.darki.konas.module.Module;
import me.darki.konas.module.combat.BowAim;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class Class423 {
    public EntityLivingBase Field1051;
    public Vec3d Field1052;
    public Vec3d Field1053;
    public float Field1054;
    public float Field1055;
    public float Field1056;
    public AxisAlignedBB Field1057;
    public boolean Field1058;
    public RayTraceResult Field1059;
    public Class420 Field1060;
    public Trajectories Field1061;

    public void Method1057(Vec3d vec3d, RayTraceResult rayTraceResult) {
        Entity entity = null;
        RayTraceResult rayTraceResult2 = null;
        double d = 0.0;
        ArrayList arrayList = (ArrayList)Module.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)this.Field1051, this.Field1057.expand(this.Field1053.x, this.Field1053.y, this.Field1053.z).grow(1.0, 1.0, 1.0));
        for (Entity entity2 : arrayList) {
            double d2;
            if (!entity2.canBeCollidedWith()) continue;
            float f = entity2.getCollisionBorderSize();
            AxisAlignedBB axisAlignedBB = entity2.getEntityBoundingBox().expand((double)f, (double)f, (double)f);
            RayTraceResult rayTraceResult3 = axisAlignedBB.calculateIntercept(this.Field1052, vec3d);
            if (rayTraceResult3 == null || !((d2 = this.Field1052.distanceTo(rayTraceResult3.hitVec)) < d) && d != 0.0) continue;
            entity = entity2;
            rayTraceResult2 = rayTraceResult3;
            d = d2;
        }
        this.Field1059 = entity != null ? new RayTraceResult(entity, rayTraceResult2.hitVec) : rayTraceResult;
    }

    public float Method1058() {
        switch (Class528.Field1186[this.Field1060.ordinal()]) {
            case 3: {
                int n = this.Field1051.getHeldItem(EnumHand.MAIN_HAND).getItem().getMaxItemUseDuration(this.Field1051.getHeldItem(EnumHand.MAIN_HAND)) - this.Field1051.getItemInUseCount();
                float f = (float)n / 20.0f;
                f = (f * f + f * 2.0f) / 3.0f;
                if (f > 1.0f) {
                    f = 1.0f;
                }
                return f * 2.0f * this.Field1060.Method1048();
            }
        }
        return this.Field1060.Method1048();
    }

    public void Method1059(double d, double d2, double d3, float f, float f2) {
        this.Field1052 = new Vec3d(d, d2, d3);
        this.Field1054 = f;
        this.Field1055 = f2;
    }

    public void Method1060(Vec3d vec3d) {
        this.Field1052 = new Vec3d(vec3d.x, vec3d.y, vec3d.z);
        double d = (this.Field1060 == Class420.ARROW ? 0.5 : 0.25) / 2.0;
        this.Field1057 = new AxisAlignedBB(vec3d.x - d, vec3d.y - d, vec3d.z - d, vec3d.x + d, vec3d.y + d, vec3d.z + d);
    }

    public Class423(Trajectories trajectories, EntityLivingBase entityLivingBase, Class420 class420) {
        this.Field1061 = trajectories;
        this.Field1051 = entityLivingBase;
        this.Field1060 = class420;
        this.Method1059(this.Field1051.posX, this.Field1051.posY + (double)this.Field1051.getEyeHeight(), this.Field1051.posZ, Trajectories.Method651(this.Field1051) ? (float) BowAim.Field764 : this.Field1051.rotationYaw, Trajectories.Method651(this.Field1051) ? (float) BowAim.Field765 : this.Field1051.rotationPitch);
        this.Field1056 = class420 == Class420.EXPERIENCE ? -20.0f : 0.0f;
        Vec3d vec3d = new Vec3d((double)(MathHelper.cos((float)(this.Field1054 / 180.0f * (float)Math.PI)) * 0.16f), 0.1, (double)(MathHelper.sin((float)(this.Field1054 / 180.0f * (float)Math.PI)) * 0.16f));
        this.Field1052 = this.Field1052.subtract(vec3d);
        this.Method1060(this.Field1052);
        this.Field1053 = new Vec3d((double)(-MathHelper.sin((float)(this.Field1054 / 180.0f * (float)Math.PI)) * MathHelper.cos((float)(this.Field1055 / 180.0f * (float)Math.PI))), (double)(-MathHelper.sin((float)((this.Field1055 + this.Field1056) / 180.0f * (float)Math.PI))), (double)(MathHelper.cos((float)(this.Field1054 / 180.0f * (float)Math.PI)) * MathHelper.cos((float)(this.Field1055 / 180.0f * (float)Math.PI))));
        this.Method1069(this.Field1053, this.Method1058());
    }

    public boolean Method1061() {
        switch (Class528.Field1186[this.Field1060.ordinal()]) {
            case 1: 
            case 2: {
                return true;
            }
        }
        return false;
    }

    public float Method1062() {
        return this.Field1060.Method1046();
    }

    public static boolean Method1063(Class423 class423) {
        return class423.Field1058;
    }

    public void Method1064() {
        Vec3d vec3d = this.Field1052.add(this.Field1053);
        RayTraceResult rayTraceResult = this.Field1051.getEntityWorld().rayTraceBlocks(this.Field1052, vec3d, this.Field1060 == Class420.FISHING_ROD, !this.Method1061(), false);
        if (rayTraceResult != null) {
            vec3d = rayTraceResult.hitVec;
        }
        this.Method1057(vec3d, rayTraceResult);
        if (this.Field1059 != null) {
            this.Field1058 = true;
            this.Method1060(this.Field1059.hitVec);
            return;
        }
        if (this.Field1052.y <= 0.0) {
            this.Field1058 = true;
            return;
        }
        this.Field1052 = this.Field1052.add(this.Field1053);
        float f = 0.99f;
        if (this.Field1051.getEntityWorld().isMaterialInBB(this.Field1057, Material.WATER)) {
            float f2 = f = this.Field1060 == Class420.ARROW ? 0.6f : 0.8f;
        }
        if (this.Field1060 == Class420.FISHING_ROD) {
            f = 0.92f;
        }
        this.Field1053 = this.Field1053.scale((double)f);
        this.Field1053 = this.Field1053.subtract(0.0, (double)this.Method1062(), 0.0);
        this.Method1060(this.Field1052);
    }

    public RayTraceResult Method1065() {
        return this.Field1059;
    }

    public static Vec3d Method1066(Class423 class423) {
        return class423.Field1052;
    }

    public static RayTraceResult Method1067(Class423 class423) {
        return class423.Field1059;
    }

    public boolean Method1068() {
        return this.Field1058;
    }

    public void Method1069(Vec3d vec3d, float f) {
        this.Field1053 = vec3d.scale(1.0 / vec3d.length());
        this.Field1053 = this.Field1053.scale((double)f);
    }
}