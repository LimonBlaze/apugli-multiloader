package io.github.merchantpug.apugli.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.merchantpug.apugli.Apugli;
import io.github.merchantpug.nibbles.ItemStackFoodComponentAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.UseAction;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class EdibleItemPower extends Power {
    private final Predicate<ItemStack> predicate;
    private final FoodComponent foodComponent;
    private final UseAction useAction;
    private final ItemStack returnStack;
    private final SoundEvent sound;
    private final Consumer<Entity> entityActionWhenEaten;
    private final int tickRate;

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<EdibleItemPower>(Apugli.identifier("edible_item"),
                new SerializableData()
                        .add("item_condition", ApoliDataTypes.ITEM_CONDITION)
                        .add("food_component", SerializableDataTypes.FOOD_COMPONENT)
                        .add("use_action", SerializableDataTypes.USE_ACTION, null)
                        .add("return_stack", SerializableDataTypes.ITEM_STACK, null)
                        .add("sound", SerializableDataTypes.SOUND_EVENT, null)
                        .add("entity_action", ApoliDataTypes.ENTITY_ACTION, null)
                        .add("tick_rate", SerializableDataTypes.INT, 10),
                data ->
                        (type, player) -> new EdibleItemPower(type, player,
                                    (ConditionFactory<ItemStack>.Instance)data.get("item_condition"),
                                    (FoodComponent)data.get("food_component"),
                                    (UseAction)data.get("use_action"),
                                    (ItemStack)data.get("return_stack"),
                                    (SoundEvent)data.get("sound"),
                                    (ActionFactory<Entity>.Instance)data.get("entity_action"),
                                    data.getInt("tick_rate")))
                .allowCondition();
    }

    public EdibleItemPower(PowerType<?> type, LivingEntity entity, Predicate<ItemStack> predicate, FoodComponent foodComponent, UseAction useAction, ItemStack returnStack, SoundEvent sound, Consumer<Entity> entityActionWhenEaten, int tickRate) {
        super(type, entity);
        this.predicate = predicate;
        this.foodComponent = foodComponent;
        if (useAction == UseAction.EAT || useAction ==  UseAction.DRINK) {
            this.useAction = useAction;
        } else this.useAction = null;
        this.returnStack = returnStack;
        this.sound = sound;
        this.entityActionWhenEaten = entityActionWhenEaten;
        this.tickRate = tickRate;
        this.setTicking(true);
    }

    // This will be moved to the tick method as soon as Apace manages the powers on the client as well as the server
    public void tempTick() {
        if (entity.age % tickRate == 0) {
            ItemStack mainhandStack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
            if (mainhandStack != ItemStack.EMPTY) {
                if (this.isActive()) {
                    if (this.predicate.test(mainhandStack)) {
                        ItemStackFoodComponentAPI.setNibbles(mainhandStack, foodComponent, useAction, returnStack, sound);
                    }
                } else {
                    if (this.predicate.test(mainhandStack)) {
                        ItemStackFoodComponentAPI.removeFoodComponent(mainhandStack);
                    }
                }
            }
            ItemStack offhandStack = entity.getEquippedStack(EquipmentSlot.OFFHAND);
            if (offhandStack != ItemStack.EMPTY) {
                if (this.isActive()) {
                    if (this.predicate.test(offhandStack)) {
                        ItemStackFoodComponentAPI.setNibbles(offhandStack, foodComponent, useAction, returnStack, sound);
                    }
                } else {
                    if (this.predicate.test(offhandStack)) {
                        ItemStackFoodComponentAPI.removeFoodComponent(offhandStack);
                    }
                }
            }
        }
    }

    public boolean doesApply(ItemStack stack) {
        return predicate.test(stack);
    }

    public void eat() {
        if(entityActionWhenEaten != null) {
            entityActionWhenEaten.accept(entity);
        }
    }

    // This will changed to the onRemoved method as soon as Apace manages the powers on the client as well as the server
    /* public void tempOnRemoved() {
        if (entity instanceof PlayerEntity) {
            for (int i = 0; i < ((PlayerEntityAccessor) entity).getInventory().main.size(); i++) {
                ItemStack itemStack = ((PlayerEntityAccessor) entity).getInventory().main.get(i);
                if (predicate.test(itemStack)) {
                    ItemStackFoodComponentAPI.removeFoodComponent(itemStack);
                }
            }
            for (int i = 0; i < ((PlayerEntityAccessor) entity).getInventory().armor.size(); i++) {
                ItemStack armorStack = ((PlayerEntityAccessor) entity).getInventory().getArmorStack(i);
                if (predicate.test(armorStack)) {
                    ItemStackFoodComponentAPI.removeFoodComponent(armorStack);
                }
            }
            ItemStack offHandStack = entity.getEquippedStack(EquipmentSlot.OFFHAND);
            if (predicate.test(offHandStack)) {
                ItemStackFoodComponentAPI.removeFoodComponent(offHandStack);
            }
        }
    } */
}