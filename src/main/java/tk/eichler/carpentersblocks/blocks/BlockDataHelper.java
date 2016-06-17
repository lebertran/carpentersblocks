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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.data.properties.Properties;
import tk.eichler.carpentersblocks.tileentities.CoverableBlockTileEntity;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class BlockDataHelper {

    private BlockDataHelper() {
        // do not instantiate
    }

    private static CoverableBlockTileEntity getCoverableTileEntity(final IBlockAccess world, final BlockPos pos) {
        final TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof CoverableBlockTileEntity) {
            return (CoverableBlockTileEntity) tileEntity;
        }

        return null;
    }

    public static CoverableData getCoverData(final IBlockState state) {
        final CoverableData data = ((IExtendedBlockState) state).getValue(Properties.COVER_DATA);

        if (data == null) {
            return CoverableData.createInstance();
        }

        return data;
    }

    public static CoverableData getCoverData(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final CoverableBlockTileEntity tileEntity = getCoverableTileEntity(world, pos);

        if (tileEntity == null) {
            if (((IExtendedBlockState) state).getValue(Properties.COVER_DATA) != null) {
                return ((IExtendedBlockState) state).getValue(Properties.COVER_DATA);
            } else {
                return CoverableData.createInstance();
            }
        }

        return tileEntity.getDataInstance();
    }

    public static boolean setCover(final World world, final BlockPos pos,
                                   final ItemStack heldItem, final EnumFacing facing) {
        final CoverableBlockTileEntity tileEntity = getCoverableTileEntity(world, pos);

        if (tileEntity == null) {
            FMLLog.bigWarning("Could not set cover as no suitable tile entity could be found.");
            return false;
        }

        return tileEntity.trySetBlockStack(heldItem);
    }

    public static boolean removeCover(final IBlockAccess world, final BlockPos pos) {
        final CoverableBlockTileEntity tileEntity = getCoverableTileEntity(world, pos);

        if (tileEntity == null) {
            FMLLog.bigWarning("Could not remove cover as no suitable tile entity could be found.");
            return false;
        }

        return tileEntity.removeBlockStack();


    }

    private static ShapeableBlockTileEntity getShapeableTileEntity(final IBlockAccess world, final BlockPos pos) {
        final TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof ShapeableBlockTileEntity) {
            return (ShapeableBlockTileEntity) tileEntity;
        }

        return null;
    }

    @SuppressWarnings("ConstantConditions")
    public static EnumOrientation getOrientation(final IBlockState state, final IBlockAccess world,
                                                 final BlockPos pos, final EnumOrientation fallback) {
        final ShapeableBlockTileEntity te = getShapeableTileEntity(world, pos);

        if (te != null) {
            return te.getShapeableData().getOrientation();
        }

        if (state.getValue(Properties.ORIENTATION) == null) {
            return fallback;
        }

        return state.getValue(Properties.ORIENTATION);
    }

    @SuppressWarnings("ConstantConditions")
    public static EnumShape getShape(final IBlockState state, final IBlockAccess world,
                                     final BlockPos pos, final EnumShape fallback) {
        final ShapeableBlockTileEntity te = getShapeableTileEntity(world, pos);

        if (te != null) {
            return te.getShapeableData().getShape();
        }

        if (state.getValue(Properties.SHAPE) == null) {
            return fallback;
        }

        return state.getValue(Properties.SHAPE);
    }

    public static void setShapeData(final World world, final BlockPos pos, @Nullable final EnumShape shape,
                                    @Nullable final EnumOrientation orientation) {
        final IBlockState currentState = world.getBlockState(pos);
        EnumShape newShape = shape;
        EnumOrientation newOrientation = orientation;

        if (newShape == null) {
            newShape = getShape(currentState, world, pos, EnumShape.FULL_BLOCK);
        }

        if (newOrientation == null) {
            newOrientation = getOrientation(currentState, world, pos, EnumOrientation.NORTH);
        }

        final ShapeableBlockTileEntity te = getShapeableTileEntity(world, pos);
        if (te != null) {
            te.setShapeableData(newShape, newOrientation);
        } else {
            FMLLog.bigWarning("Could not set new shapeable data due to missing tile entity.");
        }
    }

    public static EnumOrientation getOrientationFromFacing(final EnumFacing facing) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            return EnumOrientation.get(null, facing.getOpposite());
        } else {
            return EnumOrientation.get(facing.getOpposite(), null);
        }
    }

    /**
     * Creates an {@link IExtendedBlockState} with cover data, shape and orientation.
     *
     * @param state Current IBlockState
     * @param world Current world
     * @param pos Current block position
     * @return An extended block state with cover, shape and orientation data.
     */
    public static IExtendedBlockState createDefaultExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        IExtendedBlockState newState = (IExtendedBlockState) state;

        newState = newState.withProperty(Properties.COVER_DATA, getCoverData(state, world, pos));
        newState = (IExtendedBlockState) newState.withProperty(Properties.SHAPE,
                getShape(state, world, pos, EnumShape.FULL_BLOCK));
        newState = (IExtendedBlockState) newState.withProperty(Properties.ORIENTATION,
                getOrientation(state, world, pos, EnumOrientation.DOWN));

        return newState;
    }
}
