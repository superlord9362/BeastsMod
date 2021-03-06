package random.beasts.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;

public class BeastsAxe extends AxeItem {

    private BeastsToolSet kit;

    public BeastsAxe(IItemTier material, String name, @Nullable BeastsToolSet kit) {
        super(material, 8.0F, 3.2F, new Item.Properties().group(BeastsUtils.getRegistryTab()));
        this.kit = kit;
        BeastsUtils.addToRegistry(this, name);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
