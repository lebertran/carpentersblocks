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

package tk.eichler.carpentersblocks.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tk.eichler.carpentersblocks.blocks.BlockCoverable;
import tk.eichler.carpentersblocks.blocks.BlockDataHelper;
import tk.eichler.carpentersblocks.blocks.BlockWrapper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class BlockHelper {

    private BlockHelper() {
        // do not instantiate
    }

    @Nullable
    @SuppressWarnings("ConstantConditions")
    public static Block getBlockFromItemStack(final ItemStack stack) {
        if (stack.getItem() == null) {
            return null;
        }

        return Block.getBlockFromItem(stack.getItem());
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isValidCoverBlock(@Nullable final ItemStack blockProp) {
        if (blockProp == null) {
            return false;
        }


        final Block block = Block.getBlockFromItem(blockProp.getItem());

        if (block == null || block instanceof BlockWrapper) {
            return false;
        }

        return block.getDefaultState().getMaterial().isSolid();

    }

    public static boolean doesRenderTransparentSide(final IBlockState blockState, final IBlockState borderingState) { //@TODO
        return isGlassyBlock(borderingState) && isGlassyBlock(blockState);
    }

    public static boolean isGlassyBlock(final IBlockState blockState) {
        final Block block = blockState.getBlock();

        if (block == Blocks.GLASS || block == Blocks.STAINED_GLASS) {
            return true;
        }

        if (block instanceof BlockCoverable) {
            final Block coveringBlock = BlockDataHelper.getCoverData(blockState).getCoveringBlock();

            if (coveringBlock == Blocks.GLASS || coveringBlock == Blocks.STAINED_GLASS) {
                return true;
            }
        }
        return false;
    }

    public static ItemBlock createItemBlock(final BlockWrapper wrapper) {
        final ItemBlock itemBlock = new ItemBlock(wrapper);
        itemBlock.setRegistryName(wrapper.getRegistryName());
        itemBlock.setUnlocalizedName(wrapper.getUnlocalizedName());
        return itemBlock;
    }
}
