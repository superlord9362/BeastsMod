package rando.beasts.common.entity.passive;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import rando.beasts.common.init.BeastsBlocks;

public class EntityHermitTurtle extends EntityCreature {
    private static final DataParameter<Boolean> OUT = EntityDataManager.createKey(EntityHermitTurtle.class, DataSerializers.BOOLEAN);

    public EntityHermitTurtle(World worldIn) {
        super(worldIn);
        this.tasks.addTask(0, new EntityAIWander(this, 0.2, 200) {
            @Override
            public boolean shouldExecute() {
                return isOut() && super.shouldExecute();
            }
        });
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OUT, false);
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if(rand.nextInt(16) == 0) this.dropItem(Item.getItemFromBlock(BeastsBlocks.HERMIT_SHELL), 1);
    }

    public boolean isOut() {
        return this.dataManager.get(OUT);
    }

    private void setOut(boolean out) {
        this.dataManager.set(OUT, out);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12);
    }
}