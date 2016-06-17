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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.blocks.BaseBlock;
import tk.eichler.carpentersblocks.blocks.BlockWrapper;
import tk.eichler.carpentersblocks.blocks.BlockCoverable;
import tk.eichler.carpentersblocks.blocks.BlockShapeable;
import tk.eichler.carpentersblocks.blocks.BlockDataHelper;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.data.properties.Properties;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersBlockModel;
import tk.eichler.carpentersblocks.tileentities.BaseStateTileEntity;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class BlockCuboid extends BlockWrapper<BlockCuboid> implements BaseBlock, BlockCoverable, BlockShapeable {

    private static BlockWrapper instance = new BlockCuboid();

    private static final AxisAlignedBB COLLISION_FULL_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    private static final AxisAlignedBB COLLISION_SLAB_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
    private static final AxisAlignedBB COLLISION_SLAB_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);


    public static BlockWrapper getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "cuboid";
    }

    @Override
    public BaseStateTileEntity createTileEntity() {
        return new ShapeableBlockTileEntity();
    }

    @Override
    public BaseModel getModel() {
        return new CarpentersBlockModel();
    }


    @Override
    public IProperty[] getProperties() {
        return new IProperty[] {
                Properties.ORIENTATION, Properties.SHAPE
        };
    }

    @Override
    public IUnlistedProperty[] getUnlistedProperties() {
        return new IUnlistedProperty[] {
                Properties.COVER_DATA
        };
    }

    @Override
    public IBlockState createExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return BlockDataHelper.createDefaultExtendedState(state, world, pos);
    }

    @Override
    public AxisAlignedBB[] getCollisionBoxes(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return new AxisAlignedBB[] {
                getMainBoundingBox(state, world, pos)
        };
    }

    @Override
    public AxisAlignedBB getMainBoundingBox(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final EnumShape shape = BlockDataHelper.getShape(state, world, pos, EnumShape.FULL_BLOCK);
        final EnumOrientation orientation = BlockDataHelper.getOrientation(state, world, pos, EnumOrientation.DOWN);

        AxisAlignedBB collisionBox = Block.NULL_AABB;
        if (shape == EnumShape.FULL_BLOCK) {
            collisionBox = COLLISION_FULL_BLOCK;
        } else if (shape == EnumShape.SLAB) {
            switch (orientation) {
                case UP:
                    collisionBox = COLLISION_SLAB_TOP;
                    break;
                case DOWN:
                    collisionBox = COLLISION_SLAB_BOTTOM;
                    break;
                case SOUTH:
                    collisionBox = COLLISION_SLAB_SOUTH;
                    break;
                case WEST:
                    collisionBox = COLLISION_SLAB_WEST;
                    break;
                case EAST:
                    collisionBox = COLLISION_SLAB_EAST;
                    break;
                case NORTH:
                    collisionBox = COLLISION_SLAB_NORTH;
                    break;
                default:
                    FMLLog.severe("Given orientation %s is unknown.", orientation);
                    break;
            }
        }
        return collisionBox;
    }



    @Override
    public void onCarpentersHammerLeftClick(final World world, final BlockPos pos, final EnumFacing facing) {
        final IBlockState state = world.getBlockState(pos);

        if (!world.isRemote) {
            if (BlockDataHelper.getShape(state, world, pos, EnumShape.FULL_BLOCK) != EnumShape.FULL_BLOCK) {
                BlockDataHelper.setShapeData(world, pos, EnumShape.FULL_BLOCK, null);
            } else {
                BlockDataHelper.setShapeData(world, pos, EnumShape.SLAB,
                        EnumShape.SLAB.getNextOrientation(BlockDataHelper.getOrientation(state, world, pos, EnumOrientation.UP)));
            }
        }
    }

    @Override
    public void onCarpentersHammerRightClick(final World world, final BlockPos pos, final EnumFacing facing) {
        final IBlockState state = world.getBlockState(pos);

        if (!world.isRemote) {
            if (BlockDataHelper.getShape(state, world, pos, EnumShape.FULL_BLOCK) != EnumShape.FULL_BLOCK) {
                BlockDataHelper.setShapeData(world, pos, EnumShape.FULL_BLOCK, null);
            } else {
                BlockDataHelper.setShapeData(world, pos, EnumShape.SLAB, BlockDataHelper.getOrientationFromFacing(facing));
            }
        }
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
