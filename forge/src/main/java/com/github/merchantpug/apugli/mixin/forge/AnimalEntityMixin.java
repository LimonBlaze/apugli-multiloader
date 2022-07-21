package com.github.merchantpug.apugli.mixin.forge;

import com.github.merchantpug.apugli.powers.ModifyBreedingCooldownPower;
import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {
    @Unique private AnimalEntity apugli$otherAnimalEntity;
    @Unique private ServerPlayerEntity apugli$serverPlayerEntity;

    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "breed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;getLovingPlayer()Lnet/minecraft/server/network/ServerPlayerEntity;", ordinal = 1, shift = At.Shift.BY, by = -2), locals = LocalCapture.CAPTURE_FAILHARD)
    private void captureBreedLocals(ServerWorld world, AnimalEntity other, CallbackInfo ci, PassiveEntity ageableEntity, BabyEntitySpawnEvent event, boolean cancelled, ServerPlayerEntity serverPlayerEntity) {
        this.apugli$otherAnimalEntity = other;
        this.apugli$serverPlayerEntity = serverPlayerEntity;
    }

    @ModifyArg(method = "breed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;setBreedingAge(I)V", ordinal = 2))
    private int modifyThisAnimalBreed(int age) {
        return (int)OriginComponent.modify(apugli$serverPlayerEntity, ModifyBreedingCooldownPower.class, age, p -> p.doesApply(this));
    }

    @ModifyArg(method = "breed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;setBreedingAge(I)V", ordinal = 3))
    private int modifyOtherAnimalBreed(int age) {
        return (int)OriginComponent.modify(apugli$serverPlayerEntity, ModifyBreedingCooldownPower.class, age, p -> p.doesApply(apugli$otherAnimalEntity));
    }
}