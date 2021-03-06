package random.beasts.common.entity.monster;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.init.BeastsTriggers;

import java.util.List;
import java.util.function.Predicate;

public class EntityGiantGardenEel extends MonsterEntity {

    private LivingEntity targetedEntity;

    private float slamTimer = 250;
    private int lastSlam = 0;

    public EntityGiantGardenEel(EntityType<? extends EntityGiantGardenEel> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 3;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(0, new LookRandomlyGoal(this));
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
        getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1000);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return BeastsSounds.GARDEN_EEL_HURT;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getSlamTimer() {
        return this.slamTimer;
    }

    @Override
    public void tick() {
        super.tick();

        lastSlam++;
        if (slamTimer < 250) slamTimer += 10;
        if (lastSlam > 25) {
            Predicate<Entity> predicate = e -> EntityPredicates.NOT_SPECTATING.test(e) && e instanceof LivingEntity && !(e instanceof EntityGiantGardenEel) && (!(e instanceof PlayerEntity) || !((PlayerEntity) e).abilities.isCreativeMode);
            List<Entity> entitiesInRange = world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(3), predicate);
            entitiesInRange.forEach(e -> {
                if (targetedEntity == null || (getDistanceSq(targetedEntity) > getDistanceSq(e) && e instanceof LivingEntity))
                    targetedEntity = (LivingEntity) e;
            });
            if (!entitiesInRange.contains(targetedEntity)) targetedEntity = null;
            if (targetedEntity != null && (!(targetedEntity instanceof MobEntity) || !((MobEntity) targetedEntity).isAIDisabled())) {
                this.getLookController().setLookPositionWithEntity(targetedEntity, 10.0F, 10.0F);
                if ((slamTimer -= 50) < 0) slamTimer = 0;
                if (slamTimer == 0) {
                    if (targetedEntity.isAlive()) {
                        targetedEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 4.0F);
                        if (targetedEntity instanceof ServerPlayerEntity)
                            BeastsTriggers.HAMMERTIME.trigger((ServerPlayerEntity) targetedEntity);
                    }
                    slamTimer = 0;
                    lastSlam = 0;
                }
            }
        }
    }
}
