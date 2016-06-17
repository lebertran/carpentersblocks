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

package tk.eichler.carpentersblocks.blocks.variants;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import tk.eichler.carpentersblocks.blocks.*;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.data.Properties;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersSlopeModel;
import tk.eichler.carpentersblocks.tileentities.BaseStateTileEntity;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class BlockSlope extends BlockWrapper<BlockSlope> implements BaseBlock, BlockCoverable, BlockShapeable {

    private static final BlockWrapper INSTANCE = new BlockSlope();

    private static final AxisAlignedBB BOUNDING_BOTTOM_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
    private static final AxisAlignedBB BOUNDING_BOTTOM_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB BOUNDING_BOTTOM_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    private static final AxisAlignedBB BOUNDING_BOTTOM_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);

    public static BlockWrapper getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "slope";
    }

    @Override
    public BaseStateTileEntity createTileEntity() {
        return new ShapeableBlockTileEntity();
    }

    @Override
    public BaseModel getModel() {
        return new CarpentersSlopeModel();
    }

    @Override
    public IProperty[] getProperties() {
        return new IProperty[] {
                Properties.SHAPE, Properties.ORIENTATION
        };
    }

    @Override
    public IUnlistedProperty[] getUnlistedProperties() {
        return new IUnlistedProperty[] {
                Properties.COVER_DATA
        };
    }

    @Override
    public AxisAlignedBB[] getCollisionBoxes(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final AxisAlignedBB boundingBox;

        switch (BlockDataHelper.getShape(state, world, pos, EnumShape.NORTH_SLOPE)) {
            case NORTH_SLOPE:
                boundingBox = BOUNDING_BOTTOM_NORTH;
                break;
            case WEST_SLOPE:
                boundingBox = BOUNDING_BOTTOM_WEST;
                break;
            case EAST_SLOPE:
                boundingBox = BOUNDING_BOTTOM_EAST;
                break;
            case SOUTH_SLOPE:
                boundingBox = BOUNDING_BOTTOM_SOUTH;
                break;
            default:
                boundingBox = Block.NULL_AABB;
        }

        return new AxisAlignedBB[] {
                boundingBox
        };
    }

    @Override
    public AxisAlignedBB getMainBoundingBox(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean isFaceRendered(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return false;
    }

    @Override
    public IBlockState createExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        IExtendedBlockState newState = (IExtendedBlockState) state;

        newState = newState.withProperty(Properties.COVER_DATA, BlockDataHelper.getCoverData(state, world, pos));
        newState = (IExtendedBlockState) newState.withProperty(Properties.SHAPE,
                BlockDataHelper.getShape(state, world, pos, EnumShape.FULL_BLOCK));
        newState = (IExtendedBlockState) newState.withProperty(Properties.ORIENTATION,
                BlockDataHelper.getOrientation(state, world, pos, EnumOrientation.DOWN));

        return newState;
    }

    @Override
    public void setupPlacedBlock(final World world, final BlockPos pos, final IBlockState state,
                                 final EntityLivingBase placer, final ItemStack stack) {
        final EnumShape shape;
        switch (placer.getHorizontalFacing()) {
            case NORTH:
                shape = EnumShape.SOUTH_SLOPE;
                break;
            case SOUTH:
                shape = EnumShape.NORTH_SLOPE;
                break;
            case WEST:
                shape = EnumShape.EAST_SLOPE;
                break;
            case EAST:
                shape = EnumShape.WEST_SLOPE;
                break;
            default:
                throw new UnsupportedOperationException("Invalid facing.");
        }

        BlockDataHelper.setShapeData(world, pos, shape, EnumOrientation.NORTH);
    }

    @Override
    public void onCarpentersHammerLeftClick(final World world, final BlockPos pos, final EnumFacing facing) {
        //@TODO: implement
    }

    @Override
    public void onCarpentersHammerRightClick(final World world, final BlockPos pos, final EnumFacing facing) {
        //@TODO: implement
    }

    @Override
    public boolean isSideSolid(final IBlockState baseState, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return false;
    }
}
