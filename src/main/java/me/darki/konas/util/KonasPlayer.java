package me.darki.konas.util;

import java.util.Collection;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class KonasPlayer
extends EntityPlayerSP {
    public Minecraft Field252 = Minecraft.getMinecraft();
    public boolean Field253;
    public boolean Field254;
    public float Field255;
    public float Field256;

    public int getTotalArmorValue() {
        return this.Field252.player.getTotalArmorValue();
    }

    public boolean Method399(@NotNull World world, @NotNull Block block, @NotNull BlockPos blockPos, float f) {
        return false;
    }

    public void Method400(boolean bl) {
        this.Field253 = bl;
    }

    public void doBlockCollisions() {
    }

    public boolean Method401() {
        return this.Field253;
    }

    public void Method402(float f) {
        this.Field256 = f;
    }

    public boolean canTriggerWalking() {
        return false;
    }

    public boolean hasNoGravity() {
        return true;
    }

    public float Method403() {
        return this.Field255;
    }

    @NotNull
    public Map<Potion, PotionEffect> getActivePotionMap() {
        return this.Field252.player.getActivePotionMap();
    }

    public boolean Method404() {
        return this.Field254;
    }

    public boolean getIsInvulnerable() {
        return true;
    }

    public boolean canRenderOnFire() {
        return false;
    }

    public boolean isInsideOfMaterial(@NotNull Material material) {
        return this.Field252.player.isInsideOfMaterial(material);
    }

    public boolean attackEntityFrom(@NotNull DamageSource damageSource, float f) {
        return false;
    }

    public void Method405(float f, float f2, float f3) {
        float f4 = f * f + f2 * f2 + f3 * f3;
        if (f4 >= 1.0E-4f) {
            if ((f4 = MathHelper.sqrt(f4)) < 1.0f) {
                f4 = 1.0f;
            }
            float f5 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180));
            float f6 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180));
            this.motionX = ((f *= (f4 /= 2.0f)) * f6 - (f3 *= f4) * f5) * this.Field255;
            this.motionY = (double)(f2 *= f4) * (double)this.Field256;
            this.motionZ = (f3 * f6 + f * f5) * this.Field255;
        }
    }

    public void updateFallState(double d, boolean bl, @NotNull IBlockState iBlockState, @NotNull BlockPos blockPos) {
    }

    public float getAbsorptionAmount() {
        return this.Field252.player.getAbsorptionAmount();
    }

    public AxisAlignedBB getCollisionBox(Entity entity) {
        return null;
    }

    @NotNull
    public AxisAlignedBB getEntityBoundingBox() {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public void Method406(float f) {
        this.Field255 = f;
    }

    public void readEntityFromNBT(@NotNull NBTTagCompound nBTTagCompound) {
    }

    public void writeEntityToNBT(@NotNull NBTTagCompound nBTTagCompound) {
    }

    @NotNull
    public FoodStats getFoodStats() {
        return this.Field252.player.getFoodStats();
    }

    public void Method407(boolean bl) {
        this.Field254 = bl;
    }

    public boolean canBePushed() {
        return false;
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    public boolean isPotionActive(@NotNull Potion potion) {
        return this.Field252.player.isPotionActive(potion);
    }

    public boolean canBeRidden(@NotNull Entity entity) {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }

    @NotNull
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.Field252.player.getActivePotionEffects();
    }

    public KonasPlayer(World world) {
        super(Minecraft.getMinecraft(), world, Minecraft.getMinecraft().getConnection(), Minecraft.getMinecraft().player.getStatFileWriter(), Minecraft.getMinecraft().player.getRecipeBook());
    }

    public void applyEntityCollision(@NotNull Entity entity) {
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public KonasPlayer(boolean bl, boolean bl2, float f, float f2) {
        super(Minecraft.getMinecraft(), Minecraft.getMinecraft().world, Minecraft.getMinecraft().getConnection(), Minecraft.getMinecraft().player.getStatFileWriter(), Minecraft.getMinecraft().player.getRecipeBook());
        this.Field253 = bl;
        this.Field254 = bl2;
        this.Field255 = f;
        this.Field256 = f2;
        this.noClip = true;
        this.setHealth(this.Field252.player.getHealth());
        this.posX = this.Field252.player.posX;
        this.posY = this.Field252.player.posY;
        this.posZ = this.Field252.player.posZ;
        this.prevPosX = this.Field252.player.prevPosX;
        this.prevPosY = this.Field252.player.prevPosY;
        this.prevPosZ = this.Field252.player.prevPosZ;
        this.lastTickPosX = this.Field252.player.lastTickPosX;
        this.lastTickPosY = this.Field252.player.lastTickPosY;
        this.lastTickPosZ = this.Field252.player.lastTickPosZ;
        this.rotationYaw = this.Field252.player.rotationYaw;
        this.rotationPitch = this.Field252.player.rotationPitch;
        this.rotationYawHead = this.Field252.player.rotationYawHead;
        this.prevRotationYaw = this.Field252.player.prevRotationYaw;
        this.prevRotationPitch = this.Field252.player.prevRotationPitch;
        this.prevRotationYawHead = this.Field252.player.prevRotationYawHead;
        if (this.Field253) {
            this.inventory = this.Field252.player.inventory;
            this.inventoryContainer = this.Field252.player.inventoryContainer;
            this.setHeldItem(EnumHand.MAIN_HAND, this.Field252.player.getHeldItemMainhand());
            this.setHeldItem(EnumHand.OFF_HAND, this.Field252.player.getHeldItemOffhand());
        }
        NBTTagCompound nBTTagCompound = new NBTTagCompound();
        this.Field252.player.capabilities.writeCapabilitiesToNBT(nBTTagCompound);
        this.capabilities.readCapabilitiesFromNBT(nBTTagCompound);
        this.capabilities.isFlying = true;
        this.attackedAtYaw = this.Field252.player.attackedAtYaw;
        this.movementInput = new MovementInputFromOptions(this.Field252.gameSettings);
    }

    public float Method408() {
        return this.Field256;
    }

    public void onLivingUpdate() {
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.movementInput.updatePlayerMoveState();
        float f = this.movementInput.jump ? 1.0f : (this.movementInput.sneak ? -1.0f : 0.0f);
        this.Method405(this.movementInput.moveStrafe, f, this.movementInput.moveForward);
        if (this.Field252.gameSettings.keyBindSprint.isKeyDown()) {
            this.motionX *= 2.0;
            this.motionY *= 2.0;
            this.motionZ *= 2.0;
            this.setSprinting(true);
        } else {
            this.setSprinting(false);
        }
        if (this.Field254) {
            if (Math.abs(this.motionX) <= (double)1.0E-8f) {
                this.posX += this.Field252.player.posX - this.Field252.player.prevPosX;
            }
            if (Math.abs(this.motionY) <= (double)1.0E-8f) {
                this.motionY += this.Field252.player.posY - this.Field252.player.prevPosY;
            }
            if (Math.abs(this.motionZ) <= (double)1.0E-8f) {
                this.motionZ += this.Field252.player.posZ - this.Field252.player.prevPosZ;
            }
        }
        this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    }

    @NotNull
    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.IGNORE;
    }

    public PotionEffect getActivePotionEffect(@NotNull Potion potion) {
        return this.Field252.player.getActivePotionEffect(potion);
    }
}