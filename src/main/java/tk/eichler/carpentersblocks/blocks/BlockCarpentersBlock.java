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
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersBlockModel;
import tk.eichler.carpentersblocks.util.BlockHelper;
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static tk.eichler.carpentersblocks.util.EnumShape.FULL_BLOCK;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCarpentersBlock extends BlockShapeable {

    // Registry name
    private static final String registerName = "carpentersblock";

    // Collision boxes
    private static final AxisAlignedBB COLLISION_FULL_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    // Instance
    public static final BlockCarpentersBlock INSTANCE = new BlockCarpentersBlock();

    @Override
    public String getRegisterName() {
        return registerName;
    }


    @Override
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        final IBlockState borderingState = blockAccess.getBlockState(pos.offset(side));
        final ShapeableData data = getShapeableData(blockState);

        if (data.coveringBlock == Blocks.GLASS || data.coveringBlock == Blocks.STAINED_GLASS) {
            if (BlockHelper.doesRenderTransparentSide(blockState, borderingState)) { //@TODO Shape check
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }


    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        final EnumShape shape = state.getValue(PROP_SHAPE);

        switch (shape) {
            case BOTTOM_SLAB:
                return COLLISION_SLAB_BOTTOM;
            case TOP_SLAB:
                return COLLISION_SLAB_TOP;
            case FULL_BLOCK:
                return COLLISION_FULL_BLOCK;
            default:
                FMLLog.severe("Invalid shape");
                return COLLISION_FULL_BLOCK;
        }
    }

    @Override
    public BaseModel getModel() {
        return new CarpentersBlockModel();
    }
}
