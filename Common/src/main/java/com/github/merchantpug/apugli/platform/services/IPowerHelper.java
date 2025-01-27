package com.github.merchantpug.apugli.platform.services;

import com.github.merchantpug.apugli.power.factory.SpecialPowerFactory;
import com.github.merchantpug.apugli.power.factory.SimplePowerFactory;
import com.github.merchantpug.apugli.registry.services.RegistryObject;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

public interface IPowerHelper<T> {
    
    Object unwrapSimpleFactory(PowerFactory<?> simplePowerFactory);
    
    <F extends SimplePowerFactory<?>> Supplier<F> registerFactory(F factory);
    
    <F extends SpecialPowerFactory<?>> Supplier<F> registerFactory(String name, Class<F> factoryClass);
    
    <P extends Power> List<P> getPowers(LivingEntity entity, SimplePowerFactory<P> factory);
    
    <P> List<P> getPowers(LivingEntity entity, SpecialPowerFactory<P> factory);
    
    <P extends Power> boolean hasPower(LivingEntity entity, SimplePowerFactory<P> factory);
    
    <P> boolean hasPower(LivingEntity entity, SpecialPowerFactory<P> factory);
    
    SerializableDataType<T> getPowerTypeDataType();
    
    OptionalInt getResource(LivingEntity entity, T powerType);
    
    default OptionalInt getResource(LivingEntity entity, SerializableData.Instance data, String fieldName) {
        return getResource(entity, data.get(fieldName));
    }
    
    OptionalInt setResource(LivingEntity entity, T powerType, int value);
    
    default OptionalInt setResource(LivingEntity entity, SerializableData.Instance data, String fieldName, int value) {
        return setResource(entity, data.get(fieldName), value);
    }
    
}
