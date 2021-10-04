package io.github.merchantpug.apugli.registry;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.merchantpug.apugli.condition.bientity.RotationCondition;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class ApugliBiEntityConditions {
    public static void register() {
        register(RotationCondition.getFactory());
    }

    private static void register(ConditionFactory<Pair<Entity, Entity>> conditionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
