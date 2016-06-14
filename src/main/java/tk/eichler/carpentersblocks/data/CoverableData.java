/*
 * This file is part of Carpenter's Blocks.
 *
 * Carpenter's Blocks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Carpenter's Blocks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Carpenter's Blocks.  If not, see <http://www.gnu.org/licenses/>.
 */

package tk.eichler.carpentersblocks.data;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoverableData extends BaseData {
    private static final String NBT_BLOCK_STACK = "BlockStack";

    private ItemStack itemStack;
    private Block coveringBlock = null;

    public CoverableData(@Nullable final ItemStack itemStack) {
        this.setItemStack(itemStack);
    }

    public boolean hasCover() {
        return (this.itemStack != null) && (this.coveringBlock != null);
    }

    public void setItemStack(@Nullable final ItemStack itemStack) {
        if ((this.itemStack == null) && (itemStack == null)) {
            return;
        }
        if ((this.itemStack != null) && (this.itemStack.isItemEqual(itemStack))) {
            return;
        }

        this.itemStack = itemStack;

        if (itemStack != null) {
            this.coveringBlock = BlockHelper.getBlockFromItemStack(itemStack);
        }

        setChanged(true);
    }

    public void fromNBT(final NBTTagCompound nbt) {
        final ItemStack stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(NBT_BLOCK_STACK));

        setItemStack(stack);
    }

    public void toNBT(final NBTTagCompound nbt) {
        if (hasCover()) {
            final NBTTagCompound itemStackCompound = new NBTTagCompound();
            itemStack.writeToNBT(itemStackCompound);
            nbt.setTag(NBT_BLOCK_STACK, itemStackCompound);
        }
    }

    @Nullable
    public Block getCoveringBlock() {
        return this.coveringBlock;
    }

    @Nullable
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @SuppressWarnings("deprecation")
    public int getLightValue() {
        if (this.coveringBlock == null) {
            return 0;
        }

        return this.coveringBlock.getLightValue(null);
    }

    @SuppressWarnings("deprecation")
    public int getLightOpacity() {
        if (this.coveringBlock == null) {
            return Constants.DEFAULT_LIGHT_OPACITY;
        }

        return this.coveringBlock.getLightOpacity(null);
    }

    public static CoverableData createInstance() {
        return new CoverableData(null);
    }
}
