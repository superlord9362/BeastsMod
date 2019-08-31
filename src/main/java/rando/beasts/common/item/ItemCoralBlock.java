package rando.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import rando.beasts.common.block.BlockCoral;
import rando.beasts.common.block.CoralColor;

public class ItemCoralBlock extends ItemMultiTexture implements IHandleMeta {
    public ItemCoralBlock(Block block) {
        super(block, block, stack -> CoralColor.values()[stack.getItemDamage()].getName());
    }

    @Override
    public int getDamage() {
        return CoralColor.values().length;
    }

    @Override
    public String handleMeta(int meta) {
        return CoralColor.values()[meta].getName();
    }
}
