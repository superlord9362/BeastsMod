/*package random.beasts.common.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigatorGround;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EntityRabbitman extends AgeableEntity implements INpc, IMerchant {

    public static final int VARIANTS = 6;
    private static final Trade[][] TRADES = new Trade[][]{{new MoneyTrade(Items.LEATHER_BOOTS, 3, 1), new MoneyTrade(Items.LEATHER_LEGGINGS, 4, 1), new MoneyTrade(Items.LEATHER_CHESTPLATE, 4, 1), new MoneyTrade(Items.LEATHER_HELMET, 3, 1)}, {new Trade(Items.CARROT, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(10, 30), new EntityVillager.PriceInfo(3, 6)), new MoneyTrade(Items.CARROT, new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(10, 30)), new Trade(Items.WOODEN_HOE, BeastsItems.CARROT_COIN, 1, 2), new MoneyTrade(Items.WOODEN_HOE, 2, 1)}, {new Trade(Items.GOLDEN_CARROT, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(2, 4)), new MoneyTrade(Items.GOLDEN_CARROT, new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(2, 4)), new Trade(Items.BLAZE_ROD, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(5, 10)), new MoneyTrade(Items.GOLDEN_CARROT, new EntityVillager.PriceInfo(5, 10), new EntityVillager.PriceInfo(3, 5)), new MoneyTrade(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.LEAPING), 10, 1)}, {new Trade(Items.PAPER, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(10, 30), new EntityVillager.PriceInfo(3, 6)), new MoneyTrade(Items.PAPER, new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(10, 30)), new Trade(Items.BOOK, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(5, 15), new EntityVillager.PriceInfo(3, 6)), new MoneyTrade(Items.BOOK, new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(5, 15))}, {new Trade(Items.RABBIT, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(2, 6), new EntityVillager.PriceInfo(1, 2)), new MoneyTrade(Items.RABBIT, new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(2, 6)), new Trade(Items.RABBIT_FOOT, BeastsItems.CARROT_COIN, new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(5, 10)), new MoneyTrade(Items.RABBIT_FOOT, new EntityVillager.PriceInfo(5, 10), new EntityVillager.PriceInfo(1, 2))}, {new MoneyTrade(BeastsItems.DIAMOND_CARROT, new EntityVillager.PriceInfo(4, 6), new EntityVillager.PriceInfo(1, 1))}};
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityRabbitman.class, DataSerializers.VARINT);
    private final Inventory inv;
    public boolean hasTarget = false;
    private int randomTickDivider;
    private boolean isMating;
    private PlayerEntity customer;
    private MerchantRecipeList buyingList;
    private int timeUntilReset;
    private boolean needsInitialization;
    private boolean isWillingToMate;
    private int wealth;
    private int trades = 1;
    private Trade[] variantTrades;
    private int ticksSinceHit = 0;

    public EntityRabbitman(EntityType<? extends EntityRabbitman> type, World worldIn) {
        super(type, worldIn);
        setRecipes(new MerchantRecipeList());
        this.inv = new Inventory("Items", false, 8);
        this.setSize(0.6F, 1.95F);
        ((PathNavigatorGround) this.getNavigator()).setBreakDoors(true);
        this.experienceValue = 2;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, EntityOcelot.class, 8.0F, 0.6D, 0.6D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, EntityWolf.class, 8.0F, 0.8D, 0.8D));
        this.goalSelector.addGoal(2, new EntityAIMoveIndoors(this));
        this.goalSelector.addGoal(3, new EntityAIRestrictOpenDoor(this));
        this.goalSelector.addGoal(4, new EntityAIOpenDoor(this, true));
        this.goalSelector.addGoal(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.goalSelector.addGoal(6, new AIMate());
        this.goalSelector.addGoal(9, new LookAtGoal2(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(9, new AIInteract());
        this.goalSelector.addGoal(9, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable ILivingEntityData livingdata) {
        final int variant = rand.nextInt(Math.round(50 / (difficulty.getAdditionalDifficulty() + 1))) == 0 ? 5 : this.rand.nextInt(VARIANTS - 1);
        Trade[] trades = TRADES[variant];
        int currentIndex = 3, randomIndex;
        Trade temporaryValue;
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(variant);
        while (currentIndex != 0) {
            randomIndex = rand.nextInt(currentIndex);
            currentIndex -= 1;
            if (currentIndex < trades.length - 1) {
                temporaryValue = trades[currentIndex];
                trades[currentIndex] = trades[randomIndex];
                trades[randomIndex] = temporaryValue;
            }
        }
        this.variantTrades = trades;
        for (int i = 0; i < 2; i++)
            if (variantTrades.length - 1 >= i) variantTrades[i].addMerchantRecipe(this, buyingList, rand);
        return livingdata;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(@Nonnull AgeableEntity ageable) {
        if (ageable instanceof EntityRabbitman) {
            EntityRabbitman child = new EntityRabbitman(this.world);
            child.onInitialSpawn(this.world.getDifficultyForLocation(child.getPos()), null);
            child.setVariant(new int[]{this.getVariant(), ((EntityRabbitman) ageable).getVariant()}[rand.nextInt(2)]);
            return child;
        }
        return null;
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {
        this.customer = player;
    }

    @Nullable
    @Override
    public MerchantRecipeList getRecipes(@Nonnull PlayerEntity player) {
        return this.buyingList;
    }

    @Override
    public void setRecipes(@Nullable MerchantRecipeList recipeList) {
        this.buyingList = recipeList;
    }

    @Override
    public void useRecipe(@Nonnull MerchantRecipe recipe) {
        recipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound(SoundEvents.ENTITY_RABBIT_JUMP, this.getSoundVolume(), this.getSoundPitch());
        int i = 3 + this.rand.nextInt(4);

        if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            this.timeUntilReset = 40;
            this.needsInitialization = true;
            this.isWillingToMate = true;
            i += 5;
        }

        if (recipe.getItemToBuy().getItem() == BeastsItems.CARROT_COIN) this.wealth += recipe.getItemToBuy().getCount();
        if (recipe.getRewardsExp())
            this.world.addEntity(new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
    }

    @Override
    public void verifySellingItem(@Nonnull ItemStack stack) {
        if (!this.world.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playSound(stack.isEmpty() ? SoundEvents.ENTITY_RABBIT_HURT : SoundEvents.ENTITY_RABBIT_JUMP, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Nonnull
    @Override
    public World getWorld() {
        return this.world;
    }

    @Nonnull
    @Override
    public BlockPos getPos() {
        return this.getPosition();
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    private void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    private boolean getIsWillingToMate(boolean updateFirst) {
        if (!this.isWillingToMate && updateFirst && this.hasEnoughItems(false)) {
            boolean flag = false;
            for (int i = 0; i < this.inv.getSizeInventory(); ++i) {
                ItemStack itemstack = this.inv.getStackInSlot(i);

                if (!itemstack.isEmpty()) {
                    if (itemstack.getItem() == BeastsItems.CARROT_COIN && itemstack.getCount() >= 3) {
                        flag = true;
                        this.inv.decrStackSize(i, 3);
                    }
                }

                if (flag) {
                    this.world.setEntityState(this, (byte) 18);
                    this.isWillingToMate = true;
                    break;
                }
            }
        }

        return this.isWillingToMate;
    }

    private void resetWillingToMate() {
        isWillingToMate = false;
    }

    private boolean isMating() {
        return this.isMating;
    }

    private void setMating(boolean mating) {
        this.isMating = mating;
    }

    private boolean hasEnoughItems(boolean multiple) {
        for (int i = 0; i < this.inv.getSizeInventory(); ++i) {
            ItemStack stack = this.inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == BeastsItems.CARROT_COIN && stack.getCount() >= (multiple ? 6 : 3))
                return true;
        }
        return false;
    }

    private boolean isFarmItemInInventory() {
        for (int i = 0; i < this.inv.getSizeInventory(); ++i) {
            ItemStack stack = this.inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == BeastsItems.CARROT_COIN) return true;
        }
        return false;
    }

    protected void onGrowingAdult() {
        if (getVariant() == 1) this.goalSelector.addGoal(8, new AIHarvestCarrots());
        super.onGrowingAdult();
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18);
    }

    protected void updateAITasks() {
        if (--this.randomTickDivider <= 0) {
            BlockPos blockpos = new BlockPos(this);
            this.world.getVillageCollection().addToVillagerPositionList(blockpos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);
        }

        if (customer == null && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.needsInitialization) {
                    for (MerchantRecipe merchantrecipe : this.buyingList)
                        if (merchantrecipe.isRecipeDisabled())
                            merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                    this.needsInitialization = false;
                }
                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
                trades++;
                for (int i = 2 * (trades - 1); i < 2 * trades; i++)
                    if (variantTrades.length - 1 >= i) variantTrades[i].addMerchantRecipe(this, buyingList, rand);
            }
        }
        super.updateAITasks();
    }

    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.getVariant() == 5 && this.world.getDifficulty() == Difficulty.PEACEFUL)
            this.remove();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!world.isRemote) {
            if (!this.isChild() && this.getAttackTarget() == null && this.getVariant() == 5) {
                List<EntityRabbitman> list = world.getEntitiesWithinAABB(EntityRabbitman.class, this.getBoundingBox().grow(32), target -> target != null && target.getVariant() != 5);
                if (list.isEmpty()) {
                    PlayerEntity player = null;
                    for (PlayerEntity p : world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(26)))
                        if (player == null || (p.isEntityInvulnerable(DamageSource.causeMobDamage(this)) && p.getDistance(this) < player.getDistance(this)))
                            player = p;
                    if (player != null) {
                        this.setAttackTarget(player);
                        this.setHeldItem(Hand.MAIN_HAND, new ItemStack(BeastsItems.DIAMOND_CARROT));
                        List<EntityRabbitman> innocents = world.getEntitiesWithinAABB(EntityRabbitman.class, this.getBoundingBox().grow(36), target -> target != this);
                        for (EntityRabbitman innocent : innocents)
                            innocent.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, EntityRabbitman.class, input -> input == this, 48, 0.25, 0.5));
                    }
                }
            }
            if (this.getAttackTarget() != null) {
                if (getDistance(getAttackTarget()) < 1.2 && ticksSinceHit == 0) {
                    attackEntityAsMob(getAttackTarget());
                    if (!getAttackTarget().isAlive()) {
                        ticksSinceHit = 0;
                        hasTarget = false;
                        setAttackTarget(null);
                    } else ticksSinceHit = 1;
                } else if (!hasTarget || getDistance(getAttackTarget()) > 1.3) {
                    this.navigator.tryMoveToXYZ(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.2);
                    this.hasTarget = true;
                }
            }
            if (ticksSinceHit != 0 && ticksSinceHit < 20) ticksSinceHit++;
            else ticksSinceHit = 0;
        }
    }

    public boolean getCanSpawnHere() {
        return (this.getVariant() != 5 || this.world.getDifficulty() != Difficulty.PEACEFUL) && super.getCanSpawnHere();
    }

    public boolean processInteract(PlayerEntity player, @Nonnull Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (!this.holdingSpawnEggOfClass(itemstack, this.getClass()) && this.isAlive() && this.customer == null && !this.isChild() && !player.isSneaking()) {
            if (hand == Hand.MAIN_HAND) player.addStat(Stats.TALKED_TO_VILLAGER);
            if (!this.world.isRemote && this.getAttackTarget() == null && buyingList != null && buyingList.isEmpty()) {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            } else if (buyingList == null || this.buyingList.isEmpty()) return super.processInteract(player, hand);
            return true;
        } else return super.processInteract(player, hand);
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return BeastsItems.CARROT_COIN;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("variant", this.getVariant());
        compound.putInt("Riches", this.wealth);
        compound.putBoolean("Willing", this.isWillingToMate);
        if (this.buyingList != null) compound.put("Offers", this.buyingList.getRecipiesAsTags());
        ListNBT list = new ListNBT();
        for (int i = 0; i < this.inv.getSizeInventory(); ++i) {
            ItemStack stack = this.inv.getStackInSlot(i);
            if (!stack.isEmpty()) list.add(stack.write(new CompoundNBT()));

        }
        compound.put("Inventory", list);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("variant"));
        this.wealth = compound.getInt("Riches");
        this.isWillingToMate = compound.getBoolean("Willing");
        if (compound.contains("Offers", 10)) {
            CompoundNBT nbttagcompound = compound.getCompound("Offers");
            this.buyingList = new MerchantRecipeList(nbttagcompound);
        }
        ListNBT list = compound.getTagList("Inventory", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            ItemStack stack = new ItemStack(list.getCompoundTagAt(i));
            if (!stack.isEmpty()) this.inv.addItem(stack);
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    public boolean attackEntityAsMob(@Nonnull Entity entityIn) {
        if (this.getVariant() == 5) {
            this.playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
        } else return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    @Nonnull
    public SoundCategory getSoundCategory() {
        return this.getVariant() == 5 ? SoundCategory.HOSTILE : SoundCategory.NEUTRAL;
    }

    public static class Trade implements EntityVillager.ITradeList {
        Item sell;
        ItemStack buy;
        EntityVillager.PriceInfo price;
        EntityVillager.PriceInfo amount;
        private Item buyItem;

        Trade(Item sell, Item buy, EntityVillager.PriceInfo price, EntityVillager.PriceInfo amount) {
            this.price = price;
            this.amount = amount;
            this.sell = sell;
            this.buyItem = buy;
        }

        Trade(Item sell, Item buy, int price, int amount) {
            this(sell, buy, new EntityVillager.PriceInfo(price, price), new EntityVillager.PriceInfo(amount, amount));
        }

        Trade(Item sell, ItemStack buy, int price, int amount) {
            this(sell, null, new EntityVillager.PriceInfo(price, price), new EntityVillager.PriceInfo(amount, amount));
            this.buy = buy;
        }

        public void addMerchantRecipe(@Nonnull IMerchant merchant, @Nonnull MerchantRecipeList recipeList, @Nonnull Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(sell, this.price.getPrice(random)), this.buy == null ? new ItemStack(buyItem, this.amount.getPrice(random)) : buy));
        }
    }

    public static class MoneyTrade extends Trade {

        MoneyTrade(Item buy, EntityVillager.PriceInfo price, EntityVillager.PriceInfo amount) {
            super(BeastsItems.CARROT_COIN, buy, price, amount);
        }

        MoneyTrade(Item buy, int price, int amount) {
            super(BeastsItems.CARROT_COIN, buy, price, amount);
        }

        MoneyTrade(ItemStack buy, int price, int amount) {
            super(BeastsItems.CARROT_COIN, buy, price, amount);
        }
    }

    private class AIMate extends Goal {

        private final EntityRabbitman entity;
        private final World world;
        Village village;
        private EntityRabbitman mate;
        private int matingTimeout;

        AIMate() {
            this.entity = EntityRabbitman.this;
            this.world = entity.world;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean shouldExecute() {
            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos(this.entity), 0);
            EntityRabbitman entity = this.world.findNearestEntityWithinAABB(EntityRabbitman.class, this.entity.getBoundingBox().grow(8.0D, 3.0D, 8.0D), this.entity);
            this.mate = entity;
            return this.entity.getGrowingAge() == 0 && this.entity.getRNG().nextInt(500) == 0 && this.village != null && this.checkDoors() && this.entity.getIsWillingToMate(true) && entity != null && this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(true);
        }

        public void startExecuting() {
            this.matingTimeout = 300;
            this.entity.setMating(true);
        }

        public void resetTask() {
            this.village = null;
            this.mate = null;
            this.entity.setMating(false);
        }

        public boolean shouldContinueExecuting() {
            return this.matingTimeout >= 0 && this.checkDoors() && this.entity.getGrowingAge() == 0 && this.entity.getIsWillingToMate(false);
        }

        public void tick() {
            --this.matingTimeout;
            this.entity.getLookController().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);
            if (this.entity.getDistanceSq(this.mate) > 2.25D)
                this.entity.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
            else if (this.matingTimeout == 0 && this.mate.isMating()) this.giveBirth();
            if (this.entity.getRNG().nextInt(35) == 0) this.world.setEntityState(this.entity, (byte) 12);
        }

        private boolean checkDoors() {
            return this.village.isMatingSeason() && this.village.getNumVillagers() < MathHelper.floor(this.village.getNumVillageDoors() * 0.35);
        }

        private void giveBirth() {
            AgeableEntity child = this.entity.createChild(this.mate);
            this.mate.setGrowingAge(6000);
            this.entity.setGrowingAge(6000);
            this.mate.resetWillingToMate();
            this.entity.resetWillingToMate();
            final BabyEntitySpawnEvent event = new BabyEntitySpawnEvent(entity, mate, child);
            if (MinecraftForge.EVENT_BUS.post(event) || event.getChild() == null) return;
            child = event.getChild();
            child.setGrowingAge(-24000);
            child.setLocationAndAngles(this.entity.posX, this.entity.posY, this.entity.posZ, 0.0F, 0.0F);
            this.world.addEntity(child);
            this.world.setEntityState(child, (byte) 12);
        }
    }

    public class AIInteract extends LookAtGoal2 {
        private final EntityRabbitman rabbitman;
        private int interactionDelay;

        AIInteract() {
            super(EntityRabbitman.this, EntityRabbitman.class, 3.0F, 0.02F);
            this.rabbitman = (EntityRabbitman) entity;
        }

        public void startExecuting() {
            super.startExecuting();

            if (this.rabbitman.hasEnoughItems(true) && this.closestEntity instanceof EntityVillager && ((EntityVillager) this.closestEntity).wantsMoreFood()) {
                this.interactionDelay = 10;
            } else {
                this.interactionDelay = 0;
            }
        }

        public void tick() {
            super.tick();
            if (this.interactionDelay > 0) {
                --this.interactionDelay;
                if (this.interactionDelay == 0) {
                    Inventory inventorybasic = this.rabbitman.inv;
                    for (int i = 0; i < inventorybasic.getSizeInventory(); ++i) {
                        ItemStack stack = inventorybasic.getStackInSlot(i);
                        ItemStack empty = ItemStack.EMPTY;
                        if (!stack.isEmpty()) {
                            Item item = stack.getItem();
                            if (item == BeastsItems.CARROT_COIN && stack.getCount() > 3) {
                                int l = stack.getCount() / 2;
                                stack.shrink(l);
                                empty = new ItemStack(item, l, stack.getMetadata());
                            }
                            if (stack.isEmpty()) inventorybasic.setInventorySlotContents(i, ItemStack.EMPTY);
                        }

                        if (!empty.isEmpty()) {
                            ItemEntity item = new ItemEntity(this.rabbitman.world, this.rabbitman.posX, this.rabbitman.posY - 0.3 + (double) this.rabbitman.getEyeHeight(), this.rabbitman.posZ, empty);
                            float head = this.rabbitman.rotationYawHead;
                            float pitch = this.rabbitman.rotationPitch;
                            item.motionX = (Math.sin(-Math.toRadians(head)) * Math.toRadians(pitch) * 0.3F);
                            item.motionZ = (Math.cos(Math.toRadians(head)) * Math.toRadians(pitch) * 0.3);
                            item.motionY = (Math.sin(-Math.toRadians(pitch)) * 0.3 + 0.1);
                            item.setDefaultPickupDelay();
                            this.rabbitman.world.addEntity(item);
                            break;
                        }
                    }
                }
            }
        }
    }

    private class AIHarvestCarrots extends EntityAIMoveToBlock {
        private final EntityRabbitman entity;
        private boolean hasFarmItem;
        private boolean wantsToReapStuff;
        private int currentTask;

        AIHarvestCarrots() {
            super(EntityRabbitman.this, 0.6, 16);
            this.entity = EntityRabbitman.this;
        }

        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!ForgeEventFactory.getMobGriefingEvent(this.entity.world, this.entity)) return false;
                this.currentTask = -1;
                this.hasFarmItem = this.entity.isFarmItemInInventory();
                this.wantsToReapStuff = this.entity.hasEnoughItems(false);
            }
            return super.shouldExecute();
        }

        public boolean shouldContinueExecuting() {
            return this.currentTask >= 0 && super.shouldContinueExecuting();
        }

        public void tick() {
            super.tick();
            this.entity.getLookController().setLookPosition((double) this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.entity.getVerticalFaceSpeed());

            if (this.getIsAboveDestination()) {
                World world = this.entity.world;
                BlockPos blockpos = this.destinationBlock.up();
                BlockState state = world.getBlockState(blockpos);
                Block block = state.getBlock();

                if (this.currentTask == 0 && block instanceof BlockCrops && ((BlockCrops) block).isMaxAge(state))
                    world.destroyBlock(blockpos, true);
                else if (this.currentTask == 1 && state.getMaterial() == Material.AIR) {
                    Inventory inv = this.entity.inv;
                    for (int i = 0; i < inv.getSizeInventory(); ++i) {
                        ItemStack stack = inv.getStackInSlot(i);
                        if (!stack.isEmpty() && stack.getItem() == Items.CARROT) {
                            world.setBlockState(blockpos, Blocks.CARROTS.getDefaultState(), 3);
                            stack.shrink(1);
                            if (stack.isEmpty()) inv.setInventorySlotContents(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
                this.currentTask = -1;
                this.runDelay = 10;
            }
        }

        protected boolean shouldMoveTo(World worldIn, @Nonnull BlockPos pos) {
            Block block = worldIn.getBlockState(pos).getBlock();

            if (block == Blocks.FARMLAND) {
                pos = pos.up();
                BlockState iblockstate = worldIn.getBlockState(pos);
                block = iblockstate.getBlock();

                if (block instanceof BlockCrops && ((BlockCrops) block).isMaxAge(iblockstate) && this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0)) {
                    this.currentTask = 0;
                    return true;
                }

                if (iblockstate.getMaterial() == Material.AIR && this.hasFarmItem && (this.currentTask == 1 || this.currentTask < 0)) {
                    this.currentTask = 1;
                    return true;
                }
            }

            return false;
        }
    }
}
*/