package com.github.merchantpug.apugli.mixin.xplatforn.common;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import the.great.migration.merchantpug.apugli.power.ActionOnDurabilityChange;

import java.util.Map;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbEntityMixin {
    @Inject(method = "repairPlayerGears", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void executeActionOnDurabilityIncrease(Player player, int amount, CallbackInfoReturnable<Integer> cir, Map.Entry<EquipmentSlot, ItemStack> entry) {
        PowerHolderComponent.getPowers(player, ActionOnDurabilityChange.class).stream().filter(p -> p.doesApply(entry.getValue())).forEach(ActionOnDurabilityChange::executeIncreaseAction);
    }
}
