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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.ParametersAreNonnullByDefault;

import static tk.eichler.carpentersblocks.util.EnumShape.FULL_BLOCK;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BlockShapeable extends BlockCoverable<ShapeableData> {

    // Workaround property: when world loads, the collision boxes do not load properly when using IUnlistedProperty.
    // Until there is a fix for that, we need an additional property saving the shape.
    public static final PropertyEnum<EnumShape> PROP_SHAPE = PropertyEnum.create("shape", EnumShape.class);

    protected BlockShapeable() {
        super(Material.WOOD);

        this.useNeighborBrightness = true;
    }

    @Override
    public DataProperty<ShapeableData> getDataProperty() {
        return DataProperty.SHAPEABLE_DATA;
    }

    protected ShapeableData getShapeableData(IBlockState state) {
        ShapeableData data = ((IExtendedBlockState) state).getValue(DataProperty.SHAPEABLE_DATA);

        if (data == null) data = ShapeableData.createInstance();

        return data;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PROP_SHAPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[] { PROP_SHAPE }, new IUnlistedProperty[] { getDataProperty() });
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return getShapeableData(baseState).isShape(FULL_BLOCK) && getShapeableData(baseState).hasCover();
    }
}
