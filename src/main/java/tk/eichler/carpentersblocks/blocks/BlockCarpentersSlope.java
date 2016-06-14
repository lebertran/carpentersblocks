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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersSlopeModel;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCarpentersSlope extends BlockShapeable {
    private static final String REGISTER_NAME = "carpentersslope";

    private static final AxisAlignedBB BOUNDING_BOTTOM_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
    private static final AxisAlignedBB BOUNDING_BOTTOM_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB BOUNDING_BOTTOM_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    private static final AxisAlignedBB BOUNDING_BOTTOM_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);

    // Instance
    public static final BlockCarpentersSlope INSTANCE = new BlockCarpentersSlope();


    @Override
    protected EnumShape getDefaultShape() {
        return EnumShape.NORTH_SLOPE;
    }

    @Override
    protected EnumOrientation getDefaultOrientation() {
        return EnumOrientation.NORTH;
    }

    @Override
    public String getRegisterName() {
        return REGISTER_NAME;
    }

    @Override
    public BaseModel getModel() {
        return new CarpentersSlopeModel();
    }

    @Override
    public TileEntity getTileEntity() {
        return new ShapeableBlockTileEntity();
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(final IBlockState blockState, final World worldIn, final BlockPos pos) {
        switch (getShape(blockState, worldIn, pos)) {
            case NORTH_SLOPE:
                return BOUNDING_BOTTOM_NORTH;
            case WEST_SLOPE:
                return BOUNDING_BOTTOM_WEST;
            case EAST_SLOPE:
                return BOUNDING_BOTTOM_EAST;
            case SOUTH_SLOPE:
                return BOUNDING_BOTTOM_SOUTH;
            default:
                return NULL_AABB;
        }
    }


    @Override
    public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
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

        setShapeData(worldIn, pos, shape, EnumOrientation.NORTH);
    }

    @Override
    protected void onToggleShape(final World world, final BlockPos pos, final EnumFacing facing) {

    }

    @Override
    protected void onToggleShapeAll(final World world, final BlockPos pos, final EnumFacing fascing) {

    }
}
