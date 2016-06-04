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

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tk.eichler.carpentersblocks.tileentities.CarpentersBlockTileEntity;

import javax.annotation.Nonnull;

/**
 * A cubic block that can be transformed into various slabs.
 */
public class BlockCarpentersBlock extends BlockCoverable {

    // Registry name
    public static final String registerName = "carpentersblock";

    public static final PropertyEnum<EnumShape> PROP_ENUM_SHAPE = PropertyEnum.create("states", EnumShape.class);

    // Collision boxes
    protected static final AxisAlignedBB COLLISION_FULL_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB COLLISION_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    // Instance
    public static final BlockCarpentersBlock INSTANCE = new BlockCarpentersBlock();


    // Constructor
    private BlockCarpentersBlock() {
        super(Material.WOOD);

        setDefaultState(getDefaultState().withProperty(PROP_ENUM_SHAPE, EnumShape.FULL_BLOCK));
        this.useNeighborBrightness = true;
    }

    @Override
    public boolean getUseNeighborBrightness(IBlockState state) {
        return true;
    }

    /**
     * Override methods
     */
    @Nonnull
    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Override
    public IProperty<?> getShapeProperty() {
        return PROP_ENUM_SHAPE;
    }

    @Override
    public void registerTileEntity() {
        GameRegistry.registerTileEntity(CarpentersBlockTileEntity.class, BlockCarpentersBlock.registerName + ":tile_entity");
    }


    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull  BlockPos pos, EnumFacing side) {
        return true;
    }

    /**
     * Rendering implementations
     */


    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        final EnumShape enumState = state.getValue(PROP_ENUM_SHAPE);

        if (enumState == EnumShape.FULL_BLOCK) {
            return COLLISION_FULL_BLOCK;
        } else if (enumState == EnumShape.BOTTOM_SLAB){
            return COLLISION_SLAB_BOTTOM;
        }

        return COLLISION_FULL_BLOCK;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        if (state.getValue(PROP_ENUM_SHAPE) == EnumShape.FULL_BLOCK) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        if (base_state.getValue(PROP_ENUM_SHAPE) == EnumShape.FULL_BLOCK) {
            return true;
        }

        return false;
    }

    /**
     * Data implementations
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PROP_ENUM_SHAPE).ordinal();
    }
}
