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
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static tk.eichler.carpentersblocks.data.EnumShape.FULL_BLOCK;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BlockShapeable extends BlockCoverable<CoverableData> {

    //@todo move to api package
    public static final PropertyEnum<EnumShape> PROP_SHAPE =
            PropertyEnum.create("shape", EnumShape.class);
    public static final PropertyEnum<EnumOrientation> PROP_ORIENTATION =
            PropertyEnum.create("orientation", EnumOrientation.class);

    protected BlockShapeable() {
        super(Material.WOOD);

        this.useNeighborBrightness = true;

        this.setDefaultState(getDefaultState()
                .withProperty(PROP_SHAPE, getDefaultShape())
                .withProperty(PROP_ORIENTATION, getDefaultOrientation()));
    }

    protected abstract void onToggleShape(World world, BlockPos pos, EnumFacing facing);
    protected abstract void onToggleShapeAll(World world, BlockPos pos, EnumFacing facing);

    protected abstract EnumShape getDefaultShape();
    protected abstract EnumOrientation getDefaultOrientation();


    @Override
    public DataProperty<CoverableData> getDataProperty() {
        return DataProperty.COVERABLE_DATA;
    }

    @Override
    public IProperty[] getProperties() {
        return new IProperty[] {
                PROP_ORIENTATION, PROP_SHAPE
        };
    }

    @Override
    protected boolean onCarpentersHammerInteract(final World world, final BlockPos pos,
                                                 @Nullable final EnumFacing facing, final boolean isRightClick) {
        EnumFacing newFacing = facing;

        if (newFacing == null) {
            newFacing = EnumFacing.NORTH;
        }

        if (isRightClick) {
            onToggleShape(world, pos, newFacing);
        } else {
            onToggleShapeAll(world, pos, newFacing);
        }

        return true;
    }

    @Override
    public boolean isSideSolid(final IBlockState baseState, final IBlockAccess world,
                               final BlockPos pos, final EnumFacing side) {
        return super.isSideSolid(baseState, world, pos, side) && getShape(baseState, world, pos) == FULL_BLOCK;
    }



    public EnumShape getShape(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final ShapeableBlockTileEntity te = getShapeableTileEntity(world, pos);

        if (te != null) {
            return te.getShapeableData().getShape();
        }

        return state.getValue(PROP_SHAPE);
    }

    public EnumOrientation getOrientation(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final ShapeableBlockTileEntity te = getShapeableTileEntity(world, pos);

        if (te != null) {
            return te.getShapeableData().getOrientation();
        }

        return state.getValue(PROP_ORIENTATION);
    }

    public void setShapeData(final World world, final BlockPos pos,
                             @Nullable final EnumShape shape, @Nullable final EnumOrientation orientation) {
        final IBlockState currentState = world.getBlockState(pos);
        EnumShape newShape = shape;
        EnumOrientation newOrientation = orientation;

        if (newShape == null) {
            newShape = getShape(currentState, world, pos);
        }

        if (newOrientation == null) {
            newOrientation = getOrientation(currentState, world, pos);
        }

        final ShapeableBlockTileEntity te = getShapeableTileEntity(world, pos);
        if (te != null) {
            te.setShapeableData(newShape, newOrientation);
        }
    }

    @Nullable
    public ShapeableBlockTileEntity getShapeableTileEntity(final IBlockAccess world, final BlockPos pos) {
        final TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof ShapeableBlockTileEntity) {
            return (ShapeableBlockTileEntity) tileEntity;
        }

        return null;
    }

    @Override
    public IBlockState getExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return super.getExtendedState(state, world, pos)
                .withProperty(PROP_SHAPE, getShape(state, world, pos))
                .withProperty(PROP_ORIENTATION, getOrientation(state, world, pos));
    }
}
