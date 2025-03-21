package ru.lemoncraft.lemonorigins.action;

import ru.lemoncraft.lemonorigins.Constants;
import ru.lemoncraft.lemonorigins.LemonOrigins;
import ru.lemoncraft.lemonorigins.entity.ISummon;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;




public class TransferItemAction {
    public static ActionFactory<Tuple<Entity, Entity>> getFactory() {
        return new ActionFactory<>(LemonOrigins.loc("transfer_item"), new SerializableData(), TransferItemAction::executeAction);
    }
    public static void executeAction(SerializableData.Instance data, Tuple<Entity, Entity> entities) {
        Entity actor = entities.getA();
        Entity target = entities.getB();

        if (actor instanceof LivingEntity livingActor && target instanceof LivingEntity livingTarget) {
            ItemStack actorItem = livingActor.getMainHandItem().copy();
            ItemStack targetItem = livingTarget.getMainHandItem().copy();

            if (!actorItem.isEmpty() || !targetItem.isEmpty()) {
                boolean shouldTransfer;

                if (target instanceof ISummon summon) {
                    shouldTransfer = livingActor.getUUID().equals(summon.getOwnerUUID());
                    Constants.LOG.info("Should Transfer:" + shouldTransfer);
                    if (shouldTransfer) summon.setWeapon(actorItem);
                } else {
                    livingTarget.setItemInHand(InteractionHand.MAIN_HAND, actorItem);
                    shouldTransfer = true;
                }

                if (shouldTransfer) {
                    livingActor.setItemInHand(InteractionHand.MAIN_HAND, targetItem);
                }
            }
        }
    }
}
