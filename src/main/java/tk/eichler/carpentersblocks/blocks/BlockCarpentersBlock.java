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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersBlockModel;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCarpentersBlock extends BlockShapeable {

    // Registry name
    private static final String REGISTER_NAME = "carpentersblock";

    // Collision boxes
    private static final AxisAlignedBB COLLISION_FULL_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    private static final AxisAlignedBB COLLISION_SLAB_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
    private static final AxisAlignedBB COLLISION_SLAB_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    // Instance
    public static final BlockCarpentersBlock INSTANCE = new BlockCarpentersBlock();


    @Override
    public String getRegisterName() {
        return REGISTER_NAME;
    }

    @Override
    protected EnumShape getDefaultShape() {
        return EnumShape.FULL_BLOCK;
    }

    @Override
    protected EnumOrientation getDefaultOrientation() {
        return EnumOrientation.UP;
    }

    @Override
    public BaseModel getModel() {
        return new CarpentersBlockModel();
    }

    @Override
    public TileEntity getTileEntity() {
        return new ShapeableBlockTileEntity();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess,
                                        final BlockPos pos, final EnumFacing side) {
        final IBlockState borderingState = blockAccess.getBlockState(pos.offset(side));
        final Block coveringBlock = getCoverableData(blockState).getCoveringBlock();

        if ((coveringBlock != null) && (coveringBlock == Blocks.GLASS || coveringBlock == Blocks.STAINED_GLASS)) {
            if (BlockHelper.doesRenderTransparentSide(blockState, borderingState)) { //@TODO Shape check
                return false;
            }
        }

        return true;
    }


    @Override
    public boolean doesSideBlockRendering(final IBlockState state, final IBlockAccess world,
                                          final BlockPos pos, final EnumFacing face) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumShape shape = getShape(state, source, pos);
        final EnumOrientation orientation = getOrientation(state, source, pos);

        if (shape == EnumShape.FULL_BLOCK) {
            return COLLISION_FULL_BLOCK;
        }

        if (shape == EnumShape.SLAB) {
            switch (orientation) {
                case UP:
                    return COLLISION_SLAB_TOP;
                case DOWN:
                    return COLLISION_SLAB_BOTTOM;
                case SOUTH:
                    return COLLISION_SLAB_SOUTH;
                case WEST:
                    return COLLISION_SLAB_WEST;
                case EAST:
                    return COLLISION_SLAB_EAST;
                case NORTH:
                    return COLLISION_SLAB_NORTH;
                default:
                    FMLLog.severe("Given orientation %s is unknown.", orientation);
            }
        }

        FMLLog.severe("Given shape %s is unknown.", shape);
        return NULL_AABB;
    }

    @Override
    protected void onToggleShape(final World world, final BlockPos pos, final EnumFacing facing) {
        final IBlockState state = world.getBlockState(pos);

        if (!world.isRemote) {
            if (getShape(state, world, pos) != EnumShape.FULL_BLOCK) {
                setShapeData(world, pos, EnumShape.FULL_BLOCK, null);
            } else {
                setShapeData(world, pos, EnumShape.SLAB, getOrientationFromFacing(facing));
            }
        }
    }

    @Override
    protected void onToggleShapeAll(final World world, final BlockPos pos, final EnumFacing facing) {
        final IBlockState state = world.getBlockState(pos);

        if (!world.isRemote) {
            if (getShape(state, world, pos) != EnumShape.FULL_BLOCK) {
                setShapeData(world, pos, EnumShape.FULL_BLOCK, null);
            } else {
                setShapeData(world, pos, EnumShape.SLAB,
                        EnumShape.SLAB.getNextOrientation(getOrientation(state, world, pos)));
            }
        }
    }

    private static EnumOrientation getOrientationFromFacing(final EnumFacing facing) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            return EnumOrientation.get(null, facing.getOpposite());
        } else {
            return EnumOrientation.get(facing.getOpposite(), null);
        }
    }
}
