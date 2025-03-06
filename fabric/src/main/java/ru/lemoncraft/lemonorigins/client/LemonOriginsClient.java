package ru.lemoncraft.lemonorigins.client;

import ru.lemoncraft.lemonorigins.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Environment(EnvType.CLIENT)
public class LemonOriginsClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("lemonorigins");
    @Override
    public void onInitializeClient() {
        LOGGER.info("Loading LemonOrigins Client Resources");
        EntityRendererRegistry.register(ModEntities.SUMMON_SKELETON, SkeletonRenderer::new);
        EntityRendererRegistry.register(ModEntities.SUMMON_ZOMBIE, ZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.SUMMON_WITHER_SKELETON, WitherSkeletonRenderer::new);
    }
}