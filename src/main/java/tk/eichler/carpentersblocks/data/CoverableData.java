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
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoverableData extends BaseData {
    public static final String KEY_BLOCK_STACK = "BlockStack";

    public Block coveringBlock = null;
    public int meta = 0;

    public ItemStack itemStack;

    boolean hasChanged;


    public CoverableData(@Nullable ItemStack itemStack) {
        this.setItemStack(itemStack);
    }

    public boolean hasCover() {
        return this.itemStack != null && this.coveringBlock != null;
    }

    public void setItemStack(@Nullable ItemStack itemStack) {
        if (this.itemStack == null && itemStack == null) return;

        if (this.itemStack != null && this.itemStack.isItemEqual(itemStack)) {
            return;
        }

        this.itemStack = itemStack;

        if (itemStack != null) {
            this.coveringBlock = BlockHelper.getBlockFromItemStack(itemStack);
            this.meta = itemStack.getMetadata();
        }

        this.hasChanged = true;
    }

    public void fromNBT(NBTTagCompound nbt) {
        final ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(KEY_BLOCK_STACK));

        setItemStack(itemStack);
    }

    public void toNBT(NBTTagCompound nbt) {
        if (hasCover()) {
            final NBTTagCompound itemStackCompound = new NBTTagCompound();
            itemStack.writeToNBT(itemStackCompound);
            nbt.setTag(KEY_BLOCK_STACK, itemStackCompound);
        }
    }

    public void checkForChanges() {
        if (this.hasChanged) {
            onDataUpdated();

            this.hasChanged = false;
        }
    }

    /**
     * Properties of the covering block.
     */
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
            return 255;
        }

        return this.coveringBlock.getLightOpacity(null);
    }

    @Override
    public String toString() {
        String blockName = (this.coveringBlock == null) ? "none" : this.coveringBlock.getLocalizedName();

        return MessageFormat.format("CoverableData with block %s, meta %s.", blockName, this.meta);
    }
}
