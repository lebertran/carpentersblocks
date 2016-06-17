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

package tk.eichler.carpentersblocks.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface BlockCoverable {
    default boolean onChangeCover(final World world, final BlockPos pos, ItemStack itemStack, EnumFacing facing) {
        return BlockDataHelper.setCover(world, pos, itemStack, facing);
    }

    default void onRemoveCover(final World world, final BlockPos pos) {
        BlockDataHelper.removeCover(world, pos);
    }

    default int getCoverLightValue(final IBlockState state) {
        return BlockDataHelper.getCoverData(state).getLightValue();
    }
    default int getCoverLightOpacity(final IBlockState state) {
        return BlockDataHelper.getCoverData(state).getLightOpacity();
    }
}
