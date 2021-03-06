package random.beasts.common.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerBossInfo;
import net.minecraft.world.World;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.entity.passive.EntityAnglerPup;
import random.beasts.common.init.BeastsEntities;

import java.util.EnumSet;
import java.util.Optional;

public class EntityAnglerQueen extends MonsterEntity {
    private static final DataParameter<Boolean> CHARGING_BEAM = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> USING_BEAM = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> LASER_PITCH = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> LASER_YAW = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PREV_LASER_PITCH = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PREV_LASER_YAW = EntityDataManager.createKey(EntityAnglerQueen.class, DataSerializers.FLOAT);

    private final ServerBossInfo bossInfo = (ServerBossInfo) new ServerBossInfo(getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS).setCreateFog(true).setDarkenSky(true);

    public EntityAnglerQueen(EntityType<? extends EntityAnglerQueen> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 30;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        //this.goalSelector.addGoal(3, new EntityAnglerQueen.AIStompAttack(this));
        this.goalSelector.addGoal(4, new EntityAnglerQueen.AIMinionAttack(this));
        this.goalSelector.addGoal(5, new EntityAnglerQueen.AIBeamAttack(this));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 1.05d));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CHARGING_BEAM, false);
        this.dataManager.register(USING_BEAM, false);
        this.dataManager.register(LASER_PITCH, (float) 0);
        this.dataManager.register(LASER_YAW, (float) 0);
        this.dataManager.register(PREV_LASER_PITCH, (float) 0);
        this.dataManager.register(PREV_LASER_YAW, (float) 0);
    }

    public float getLaserPitch() {
        return this.dataManager.get(LASER_PITCH);
    }

    private void setLaserPitch(float laserPitch) {
        this.dataManager.set(LASER_PITCH, laserPitch);
    }

    public float getLaserYaw() {
        return this.dataManager.get(LASER_YAW);
    }

    private void setLaserYaw(float laserYaw) {
        this.dataManager.set(LASER_YAW, laserYaw);
    }

    public float getPrevLaserPitch() {
        return this.dataManager.get(PREV_LASER_PITCH);
    }

    private void setPrevLaserPitch(float prevLaserPitch) {
        this.dataManager.set(PREV_LASER_PITCH, prevLaserPitch);
    }

    public float getPrevLaserYaw() {
        return this.dataManager.get(PREV_LASER_YAW);
    }

    private void setPrevLaserYaw(float prevLaserYaw) {
        this.dataManager.set(PREV_LASER_YAW, prevLaserYaw);
    }

    public boolean isUsingBeam() {
        return this.dataManager.get(USING_BEAM);
    }

    private void setUsingBeam(boolean isUsingBeam) {
        this.dataManager.set(USING_BEAM, isUsingBeam);
    }

    public boolean isChargingBeam() {
        return this.dataManager.get(CHARGING_BEAM);
    }

    private void setChargingBeam(boolean isChargingBeam) {
        this.dataManager.set(CHARGING_BEAM, isChargingBeam);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        bossInfo.setPercent(getHealth() / getMaxHealth());
    }

    @Override
    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    static class AIBeamAttack extends Goal {
        private final EntityAnglerQueen queen;
        private LivingEntity target;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 300;

        public AIBeamAttack(EntityAnglerQueen queen) {
            this.queen = queen;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public static boolean canSeeEntity(Vec3d observer, Entity subject) {
            if (observer == null || subject == null)
                return false;

            AxisAlignedBB axisalignedbb = subject.getBoundingBox().grow(0.30000001192092896D);
            Vec3d subjectLocation = new Vec3d(subject.posX, subject.posY + subject.getEyeHeight(), subject.posZ);
            RayTraceResult traceToBlocks = subject.world.rayTraceBlocks(new RayTraceContext(observer, subjectLocation, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, subject));
            if (traceToBlocks.getType() != RayTraceResult.Type.MISS)
                subjectLocation = traceToBlocks.getHitVec();
            Optional<Vec3d> traceToEntity = axisalignedbb.rayTrace(observer, subjectLocation);
            return traceToEntity.isPresent();
        }

        public boolean shouldExecute() {
            target = this.queen.getAttackTarget();
            return target != null && target.isAlive() && queen.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
            if (tickCounter > this.attackTick || !super.shouldContinueExecuting()) {
                cooldown = queen.ticksExisted + 1200;
                this.queen.setChargingBeam(false);
                this.queen.setUsingBeam(false);
                return false;
            }
            return super.shouldContinueExecuting();
        }

        public void startExecuting() {
            this.tickCounter = -45;
            this.queen.setChargingBeam(true);
            this.queen.playSound(BeastsSounds.ANGLER_QUEEN_BEAM_CHARGE, 1f, 1f);
            this.queen.getNavigator().clearPath();
            this.queen.getLookController().setLookPositionWithEntity(target, 90.0F, 90.0F);
            this.queen.isAirBorne = true;
        }

        private float updateRotation(float currentAngle, float targetAngle, float maxChange) {
            float change = MathHelper.wrapDegrees(targetAngle - currentAngle);
            return MathHelper.wrapDegrees(currentAngle + queen.rand.nextFloat() * 1.5f - 0.75f + change / maxChange);
        }
        
        private void updateLaser() {
            if (this.queen.world != null) {
                if (queen.getAttackTarget() != null) {
                    queen.setPrevLaserPitch(queen.getLaserPitch());
                    queen.setPrevLaserYaw(queen.getLaserYaw());
                    double targetX = queen.getAttackTarget().posX;
                    double targetY = queen.getAttackTarget().posY + queen.getAttackTarget().getHeight() / 2d;
                    double targetZ = queen.getAttackTarget().posZ;
                    double rot = queen.renderYawOffset * 0.01745329238474369D + (Math.PI / 2D);
                    double lureX = Math.cos(rot) * (double) (queen.getWidth() + 1f) + queen.posX;
                    double lureY = queen.getHeight() + 1f + queen.posY;
                    double lureZ = Math.sin(rot) * (double) (queen.getWidth() + 1f) + queen.posZ;
                    Vec3d lureVec = new Vec3d(lureX, lureY, lureZ);

                    double d0 = targetX - lureX;
                    double d1 = targetY - lureY;
                    double d2 = targetZ - lureZ;
                    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                    float targetYaw = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                    float targetPitch = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
                    queen.setLaserPitch(this.updateRotation(queen.getLaserPitch(), targetPitch, 35f - this.queen.world.getDifficulty().getId() * 2f));
                    queen.setLaserYaw(this.updateRotation(queen.getLaserYaw(), targetYaw, 20f - this.queen.world.getDifficulty().getId() * 2f));

                    Vec3d laserAngle = Vec3d.fromPitchYaw(queen.getLaserPitch(), queen.getLaserYaw());
                    double range = 30d;
                    Vec3d hitVec = lureVec.add(laserAngle.scale(range));

                    RayTraceResult trace = queen.world.rayTraceBlocks(new RayTraceContext(lureVec, hitVec, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, queen));
                    if (trace.getType() != RayTraceResult.Type.MISS) {
                        hitVec = trace.getHitVec();
                    }

                    float f = 1.0F;
                    if (this.queen.world.getDifficulty() == Difficulty.HARD) {
                        f += 2.0F;
                    }

                    LivingEntity base = null;
                    for (LivingEntity entity : queen.world.getEntitiesWithinAABB(LivingEntity.class, queen.getBoundingBox().grow(30))) {
                        AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(0.30000001192092896D);
                        Optional<Vec3d> traceToEntity = axisalignedbb.rayTrace(lureVec, hitVec);
                        if (traceToEntity.isPresent() && canSeeEntity(lureVec, entity) && entity != queen && (base == null || queen.getDistance(entity) < queen.getDistance(base)))
                            base = entity;
                    }

                    if (base != null) {
                        base.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.queen, this.queen), f);
                        base.attackEntityFrom(DamageSource.causeMobDamage(this.queen), (float) this.queen.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue());
                    }

                    if (this.queen.ticksExisted % 15 == 0)
                        this.queen.playSound(BeastsSounds.ANGLER_QUEEN_BEAM, 1f, 1f);
                }
        	}
        }

        public void tick() {
            this.queen.getNavigator().clearPath();
            this.queen.getLookController().setLookPositionWithEntity(target, 90.0F, 90.0F);

            if (this.tickCounter == 0) {
                this.queen.setChargingBeam(false);
                this.queen.setUsingBeam(true);
                //this entity state update tries to play a guardian sound, so i removed it
                //this.queen.world.setEntityState(this.queen, (byte)21);
            } else if (this.tickCounter < attackTick) {
                this.updateLaser();
            }

            ++this.tickCounter;
            super.tick();
        }
    }

    static class AIMinionAttack extends Goal {
        private final EntityAnglerQueen queen;
        private LivingEntity target;
        private int tickCounter;
        private int cooldown;
        private int attackTick = 60;

        public AIMinionAttack(EntityAnglerQueen queen) {
            this.queen = queen;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean shouldExecute() {
            target = this.queen.getAttackTarget();
            return target != null && target.isAlive() && queen.ticksExisted > cooldown;
        }

        public boolean shouldContinueExecuting() {
            if (tickCounter > this.attackTick) {
                cooldown = queen.ticksExisted + 600;
                return false;
            }
            return super.shouldContinueExecuting() && this.queen.getDistanceSq(target) > 4.0D;
        }

        public void startExecuting() {
            this.tickCounter = 0;
            this.queen.getNavigator().clearPath();
            this.queen.getLookController().setLookPositionWithEntity(target, 90.0F, 90.0F);
            this.queen.isAirBorne = true;
        }

        public void tick() {
            ++tickCounter;
            if (tickCounter == this.attackTick) {
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockpos = (new BlockPos(queen)).add(-2 + queen.getRNG().nextInt(5), 1, -2 + queen.getRNG().nextInt(5));
                    while (!queen.getEntityWorld().isAirBlock(blockpos))
                        blockpos = (new BlockPos(queen)).add(-2 + queen.getRNG().nextInt(5), 1, -2 + queen.getRNG().nextInt(5));
                    EntityAnglerPup entity = BeastsEntities.ANGLER_PUP.create(queen.world);
                    entity.setPosition(blockpos.getX() + 0.5d, blockpos.getY(), blockpos.getZ() + 0.5d);
                    entity.setAttackTarget(queen.getAttackTarget());
                    if (!queen.world.isRemote) queen.world.addEntity(entity);
                }
            }
        }
    }
}
