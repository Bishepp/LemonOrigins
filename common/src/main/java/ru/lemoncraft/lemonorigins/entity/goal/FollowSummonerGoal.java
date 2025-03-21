package ru.lemoncraft.lemonorigins.entity.goal;

import ru.lemoncraft.lemonorigins.entity.IFollowingSummon;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

public class FollowSummonerGoal extends Goal {
    /*
     * Originally based off of Ars Nouveau, which is under the LGPL-v3.0 license
     */

    protected final IFollowingSummon summon;
    public final LevelReader world;
    private final double followSpeed;
    private final PathNavigation navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;

    public FollowSummonerGoal(IFollowingSummon mobEntity, LivingEntity owner, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.summon = mobEntity;
        this.world = mobEntity.getWorld();
        this.followSpeed = followSpeedIn;
        this.navigator = mobEntity.getPathNav();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;

        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(mobEntity.getPathNav() instanceof GroundPathNavigation) && !(mobEntity.getPathNav() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        LivingEntity livingentity = summon.getSummoner();
        if (livingentity == null) {
            return false;
        } else if (livingentity instanceof Player && livingentity.isSpectator()) {
            return false;
        } else if (this.summon instanceof TamableAnimal && ((TamableAnimal) this.summon).isOrderedToSit()) {
            return false;
        } else
            return !(this.summon.getSelfEntity().distanceToSqr(livingentity) < (double) (this.minDist * this.minDist));
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {

        boolean flag = true;
        if (this.summon instanceof TamableAnimal)
            flag = !((TamableAnimal) this.summon).isOrderedToSit();

        if (this.summon.getSummoner() == null)
            return false;

        return !this.navigator.isDone() && this.summon.getSelfEntity().distanceToSqr(this.summon.getSummoner()) > (double) (this.maxDist * this.maxDist) && flag;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {

        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.summon.getSelfEntity().getPathfindingMalus(BlockPathTypes.WATER);
        this.summon.getSelfEntity().setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {

        this.navigator.stop();
        this.summon.getSelfEntity().setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }


    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {

        if (this.summon.getSummoner() == null) {

            return;
        }
        this.summon.getSelfEntity().getLookControl().setLookAt(this.summon.getSummoner(), 10.0F, (float) this.summon.getSelfEntity().getMaxHeadXRot());
        if (this.summon instanceof TamableAnimal && ((TamableAnimal) this.summon).isOrderedToSit())
            return;

        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;

            if (!this.navigator.moveTo(this.summon.getSummoner(), this.followSpeed)) {

                if (!(this.summon.getSelfEntity().distanceToSqr(this.summon.getSummoner()) < 144.0D)) {
                    int i = Mth.floor(this.summon.getSummoner().getX()) - 2;
                    int j = Mth.floor(this.summon.getSummoner().getZ()) - 2;
                    int k = Mth.floor(this.summon.getSummoner().getBoundingBox().minY);

                    for (int l = 0; l <= 4; ++l) {
                        for (int i1 = 0; i1 <= 4; ++i1) {
                            if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i + l, k - 1, j + i1))) {
                                this.summon.getSelfEntity().moveTo((float) (i + l) + 0.5F, k, (float) (j + i1) + 0.5F, this.summon.getSelfEntity().getYRot(), this.summon.getSelfEntity().getXRot());
                                this.navigator.stop();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean canTeleportToBlock(BlockPos pos) {
        BlockState blockstate = this.world.getBlockState(pos);
        return blockstate.isValidSpawn(this.world, pos, this.summon.getSelfEntity().getType()) && this.world.isEmptyBlock(pos.above()) && this.world.isEmptyBlock(pos.above(2));
    }
}