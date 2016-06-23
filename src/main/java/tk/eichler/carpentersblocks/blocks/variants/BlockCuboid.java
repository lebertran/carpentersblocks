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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
import tk.eichler.carpentersblocks.model.CarpentersBlockModel;
import tk.eichler.carpentersblocks.tileentities.BaseStateTileEntity;
import tk.eichler.carpentersblocks.tileentities.variants.CuboidTileEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class BlockCuboid extends BlockWrapper<CuboidTileEntity> implements BlockCoverable<CuboidTileEntity>, BlockShapeable {

    private static BlockWrapper instance = new BlockCuboid();

    public static BlockWrapper getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "cuboid";
    }

    @Override
    public BaseStateTileEntity createTileEntity() {
        return new CuboidTileEntity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BaseModel getModel() {
        return new CarpentersBlockModel();
    }


    @Override
    public IProperty[] getProperties() {
        return CuboidTileEntity.PROPERTIES;
    }

    @Override
    public IUnlistedProperty[] getUnlistedProperties() {
        return CuboidTileEntity.UNLISTED_PROPERTIES;
    }




    @Override
    public AxisAlignedBB[] getCollisionBoxes(@Nullable final CuboidTileEntity tileEntity) {
        return new AxisAlignedBB[] {
                getMainBoundingBox(tileEntity)
        };
    }

    @Override
    public AxisAlignedBB getMainBoundingBox(@Nullable final CuboidTileEntity tileEntity) {
        if (tileEntity == null) {
            FMLLog.severe("getMainBoundingBox could not get tile entity.");
            return FULL_BLOCK_AABB;
        }

        final EnumShape shape = tileEntity.getDataInstance().getShape();
        final EnumOrientation orientation = tileEntity.getDataInstance().getOrientation();

        final AxisAlignedBB collisionBox;

        switch (shape) {
            case FULL_BLOCK:
                collisionBox = CollisionBoxHelper.COLLISION_FULL_BLOCK;
                break;
            case SLAB:
                collisionBox = CollisionBoxHelper.getSlabCollisionBox(orientation);
                break;
            default:
                collisionBox = NULL_AABB;
                break;
        }

        return collisionBox;
    }


    @Override
    public boolean isSideSolid(final IBlockState baseState, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return true; //@todo implement
    }

    @Override
    public boolean isFaceRendered(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return false;
    }
}
