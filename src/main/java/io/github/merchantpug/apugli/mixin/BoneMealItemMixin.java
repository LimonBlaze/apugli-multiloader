package io.github.merchantpug.apugli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.merchantpug.apugli.power.ActionOnBoneMealPower;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult;success(Z)Lnet/minecraft/util/ActionResult;"))
    private void executeBoneMealAction(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PowerHolderComponent.getPowers(context.getPlayer(), ActionOnBoneMealPower.class)
                .stream()
                .filter(p -> p.doesApply(new CachedBlockPosition(context.getWorld(), context.getBlockPos(), true)))
                .forEach(p -> p.executeActions(context.getWorld(), context.getBlockPos(), context.getSide()));
    }
}
