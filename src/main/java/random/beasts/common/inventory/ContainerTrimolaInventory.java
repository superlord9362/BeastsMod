package random.beasts.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import random.beasts.common.entity.monster.EntityTrimola;
import random.beasts.common.init.BeastsContainers;

public class ContainerTrimolaInventory extends Container {
    public EntityTrimola trimola;

    public ContainerTrimolaInventory(int id, PlayerInventory inventory) {
        super(BeastsContainers.TRIMOLA, id);
    }

    public ContainerTrimolaInventory(int id, EntityTrimola trimola, PlayerEntity player) {
        this(id, player.inventory);
        this.trimola = trimola;
        trimola.inventory.openInventory(player);
        this.addSlot(new Slot(trimola.inventory, 0, 54, 18) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.getHasStack() && trimola.getSaddle().isEmpty();
            }

            public int getSlotStackLimit() {
                return 1;
            }
        });
        for (int i1 = 0; i1 < 3; ++i1)
            for (int k1 = 0; k1 < 9; ++k1)
                this.addSlot(new Slot(player.inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
        for (int j1 = 0; j1 < 9; ++j1) this.addSlot(new Slot(player.inventory, j1, 8 + j1 * 18, 142));
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        //slot.getSlot is causing the game to freeze by never existing this method
        /*ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 1)
                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) return ItemStack.EMPTY;
                else if (this.getSlot(0).isItemValid(itemstack1))
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) return ItemStack.EMPTY;

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }*/

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return trimola.isTamed() && trimola.isOwner(playerIn);
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.trimola.inventory.closeInventory(playerIn);
    }
}
