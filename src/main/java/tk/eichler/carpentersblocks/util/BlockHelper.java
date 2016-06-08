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
import net.minecraft.item.ItemStack;
import tk.eichler.carpentersblocks.blocks.BaseBlock;
import tk.eichler.carpentersblocks.blocks.BlockCoverable;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockHelper {
    @Nullable
    public static Block getBlockFromItemStack(ItemStack stack) {
        //noinspection ConstantConditions
        if (stack.getItem() == null) return null;

        return Block.getBlockFromItem(stack.getItem());
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isValidCoverBlock(@Nullable ItemStack blockProp){
        if (blockProp == null) return false;


        final Block block = Block.getBlockFromItem(blockProp.getItem());
        if (block == null) return false; // Block.getBlockFromItem is nullable!

        if (block instanceof BaseBlock) return false;

        return block.getDefaultState().getMaterial().isSolid();

    }

    public static boolean doesRenderTransparentSide(IBlockState blockState, IBlockState borderingState) { //@TODO
        return isGlassyBlock(borderingState) && isGlassyBlock(blockState);
    }

    public static boolean isGlassyBlock(IBlockState blockState) {
        final Block block = blockState.getBlock();

        if (block == Blocks.GLASS || block == Blocks.STAINED_GLASS) {
            return true;
        }

        if (block instanceof BlockCoverable) {
            final Block coveringBlock = ((BlockCoverable) block).getCoverableData(blockState).coveringBlock;

            if (coveringBlock == Blocks.GLASS || coveringBlock == Blocks.STAINED_GLASS) {
                return true;
            }
        }
        return false;
    }
}
