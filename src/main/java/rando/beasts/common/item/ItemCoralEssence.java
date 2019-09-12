package rando.beasts.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import rando.beasts.common.block.BlockCoral;
import rando.beasts.common.block.CoralColor;

public class ItemCoralEssence extends BeastsItem implements ICoralMeta {
    public ItemCoralEssence() {
        super("coral_essence");
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(isInCreativeTab(tab)) for (int i = 0; i < getDamage(); i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + handleMeta(stack.getItemDamage());
    }
}
