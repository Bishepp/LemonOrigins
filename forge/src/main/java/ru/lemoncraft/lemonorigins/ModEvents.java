package ru.lemoncraft.lemonorigins;

import ru.lemoncraft.lemonorigins.entity.ModEntities;
import ru.lemoncraft.lemonorigins.entity.SummonedSkeleton;
import ru.lemoncraft.lemonorigins.entity.SummonedZombie;
import ru.lemoncraft.lemonorigins.entity.SummonedWitherSkeleton;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LemonOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUMMON_SKELETON.get(), SummonedSkeleton.createAttributes().build());
        event.put(ModEntities.SUMMON_ZOMBIE.get(), SummonedZombie.createAttributes().build());
        event.put(ModEntities.SUMMON_WITHER_SKELETON.get(), SummonedWitherSkeleton.createAttributes().build());
    }

}
