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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.blocks.BlockCoverable;
import tk.eichler.carpentersblocks.blocks.BlockShapeable;
import tk.eichler.carpentersblocks.blocks.BlockWrapper;
import tk.eichler.carpentersblocks.blocks.helpers.CollisionBoxHelper;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersSlopeModel;
import tk.eichler.carpentersblocks.tileentities.BaseStateTileEntity;
import tk.eichler.carpentersblocks.tileentities.variants.SlopeTileEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class BlockSlope extends BlockWrapper<SlopeTileEntity> implements BlockCoverable<SlopeTileEntity>, BlockShapeable {

    private static final BlockWrapper INSTANCE = new BlockSlope();
    public static BlockWrapper getInstance() {
        return INSTANCE;
    }


    @Override
    public String getName() {
        return "slope";
    }

    @Override
    public BaseStateTileEntity createTileEntity() {
        return new SlopeTileEntity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BaseModel getModel() {
        return new CarpentersSlopeModel();
    }

    @Override
    public IProperty[] getProperties() {
        return SlopeTileEntity.PROPERTIES;
    }

    @Override
    public IUnlistedProperty[] getUnlistedProperties() {
        return SlopeTileEntity.UNLISTED_PROPERTIES;
    }

    @Override
    public AxisAlignedBB[] getCollisionBoxes(@Nullable final SlopeTileEntity tileEntity) {
        if (tileEntity == null) {
            FMLLog.severe("TileEntity is null.");
            return new AxisAlignedBB[] {FULL_BLOCK_AABB};
        }

        final EnumOrientation orientation = tileEntity.getDataInstance().getOrientation();

        final AxisAlignedBB mainBox = getMainBoundingBox(tileEntity);
        final AxisAlignedBB quarterBox = CollisionBoxHelper.getQuarterStairs(orientation);

        return new AxisAlignedBB[] {
                mainBox, quarterBox
        };    }

    @Override
    public AxisAlignedBB getMainBoundingBox(@Nullable final SlopeTileEntity tileEntity) {
        if (tileEntity == null) {
            FMLLog.severe("TileEntity is null.");
            return FULL_BLOCK_AABB;
        }

        final EnumShape shape = tileEntity.getDataInstance().getShape();
        final EnumOrientation orientation = tileEntity.getDataInstance().getOrientation();

        if (shape == EnumShape.SLOPE) {
            if (orientation.getVertical() == EnumFacing.UP) {
                return CollisionBoxHelper.COLLISION_SLAB_TOP;
            } else {
                return CollisionBoxHelper.COLLISION_SLAB_BOTTOM;
            }
        }

        return FULL_BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(final IBlockState state, final World worldIn, final BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean isFaceRendered(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return false;
    }

    @Override
    public void setupPlacedBlock(final World world, final BlockPos pos, final IBlockState state,
                                 final EntityLivingBase placer, final ItemStack stack) {
        final EnumShape shape = EnumShape.SLOPE;
        final EnumOrientation orientation;
        switch (placer.getHorizontalFacing()) {
            case NORTH:
                orientation = EnumOrientation.SOUTH_DOWN;
                break;
            case SOUTH:
                orientation = EnumOrientation.NORTH_DOWN;
                break;
            case WEST:
                orientation = EnumOrientation.EAST_DOWN;
                break;
            case EAST:
                orientation = EnumOrientation.WEST_DOWN;
                break;
            default:
                throw new UnsupportedOperationException("Invalid facing.");
        }

        final SlopeTileEntity tileEntity = getTileEntity(world, pos);
        if (tileEntity != null) {
            tileEntity.setShapeableData(shape, orientation);
        }
    }

    @Override
    public boolean isSideSolid(final IBlockState baseState, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return false;
    }
}
