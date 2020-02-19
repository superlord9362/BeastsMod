package random.beasts.common.entity.passive;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.common.init.BeastsItems;

public class EntityAnglerPup extends EntityTameable {

    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityAnglerPup.class, DataSerializers.VARINT);
    private static final DataParameter<Float> THREAT_TIME = EntityDataManager.createKey(EntityAnglerPup.class, DataSerializers.FLOAT);
    private int bounces = 0;
    private BlockPos jukeboxPosition;
    private boolean partyPufferfishDog;
    
	public EntityAnglerPup(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 0.5F);
	}

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(2, aiSit);
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIFollowOwner(this, 0.5, 2f, 5f));
        this.tasks.addTask(2, new EntityAIWander(this, 0.5, 50) {
            @Override
            public boolean shouldExecute() {
                return !isSitting() && super.shouldExecute();
            }
        });
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
	
	@Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8d);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(COLLAR_COLOR, EnumDyeColor.RED.getDyeDamage());
        this.dataManager.register(THREAT_TIME, 0.0f);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("collarColor", this.getCollarColor().getDyeDamage());
        compound.setBoolean("sitting", this.isSitting());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCollarColor(EnumDyeColor.byDyeDamage(compound.getInteger("collarColor")));
        this.setSitting(compound.getBoolean("sitting"));
    }

    @Override
    public void onLivingUpdate() {

        if (this.jukeboxPosition == null || this.jukeboxPosition.distanceSq(this.posX, this.posY, this.posZ) > 12.0D || this.world.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.partyPufferfishDog = false;
            this.jukeboxPosition = null;
        }

        if (!world.isRemote) {
            if (isInWater()) this.setAir(300);
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean processInteract(EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.isTamed()) {
            if (!stack.isEmpty() && stack.getItem() == Items.DYE) {
                EnumDyeColor color = EnumDyeColor.byDyeDamage(stack.getMetadata());
                if (color != this.getCollarColor()) {
                    this.setCollarColor(color);
                    if (!player.capabilities.isCreativeMode) stack.shrink(1);
                    return true;
                }
            }
            if (this.isOwner(player) && !this.world.isRemote && stack.isEmpty()) {
                this.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                return true;
            }
        } else if (stack.getItem() == BeastsItems.LEAFY_BONE) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.isJumping = false;
                this.navigator.clearPath();
                this.motionX = 0;
                this.motionZ = 0;
                this.setTamedBy(player);
                this.setHealth(16.0F);
                this.setSitting(true);
                this.playTameEffect(true);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.playTameEffect(false);
                this.world.setEntityState(this, (byte) 6);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }
    
    @Override
    public boolean canMateWith(@Nonnull EntityAnimal animal) {
        if (animal == this || !this.isTamed() || !(animal instanceof EntityPufferfishDog)) {
            return false;
        } else {
            EntityPufferfishDog entity = (EntityPufferfishDog) animal;
            return !(!entity.isTamed() || entity.isSitting()) && this.isInLove() && entity.isInLove();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SPIDER_EYE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPartying(BlockPos pos, boolean p_191987_2_) {
        this.jukeboxPosition = pos;
        this.partyPufferfishDog = p_191987_2_;
    }

    @SideOnly(Side.CLIENT)
    public boolean isPartying() {
        return this.partyPufferfishDog;
    }

    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataManager.get(COLLAR_COLOR) & 15);
    }

    private void setCollarColor(EnumDyeColor color) {
        this.dataManager.set(COLLAR_COLOR, color.getDyeDamage());
    }

    private float getThreatTime() {
        return this.dataManager.get(THREAT_TIME);
    }

    private void setThreatTime(float time) {
        this.dataManager.set(THREAT_TIME, time);
    }

}