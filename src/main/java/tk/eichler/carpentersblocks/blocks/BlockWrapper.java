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
 *
 */

package tk.eichler.carpentersblocks.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.registry.helper.RegistryHelper;
import tk.eichler.carpentersblocks.tileentities.CoverableBlockTileEntity;
import tk.eichler.carpentersblocks.util.CarpentersCreativeTab;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;
import java.util.List;

/**
 * Block wrapper class, handles rendering and placement logic using information from the corresponding Tile Entity.
 * @param <T> Corresponding tile entity.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class BlockWrapper<T extends CoverableBlockTileEntity> extends BlockContainer implements BaseBlock<T> {

    private ItemBlock itemBlock = null;

    public BlockWrapper() {
        super(Material.WOOD);

        setCreativeTab(CarpentersCreativeTab.get());

        setRegistryName(getName());
        setUnlocalizedName(getName());

        setHardness(2.0F);
    }

    @Override
    public TileEntity createNewTileEntity(@Nullable final World worldIn, final int meta) {
        return this.createTileEntity();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public T getTileEntity(final IBlockAccess world, final BlockPos pos) {
        return (T) world.getTileEntity(pos);
    }

    @Override
    public String getUnlocalizedName() {
        return MessageFormat.format("{0}.{1}", Constants.MOD_ID, super.getUnlocalizedName());
    }

    public ItemBlock getItemBlock() {
        if (itemBlock == null) {
            itemBlock = RegistryHelper.createItemBlock(this);
        }

        return this.itemBlock;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, this.getProperties(), this.getUnlistedProperties());
    }

    @Override
    public IBlockState getExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return this.getTileEntity(world, pos).createBlockState(state);
    }

    @Override
    public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
                                final EntityLivingBase placer, final ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        this.setupPlacedBlock(worldIn, pos, state, placer, stack);
    }

    @Override
    public void addCollisionBoxToList(final IBlockState state, final World worldIn, final BlockPos pos,
                                      final AxisAlignedBB entityBox, final List<AxisAlignedBB> collidingBoxes,
                                      @Nullable final Entity entityIn) {
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn);

        final AxisAlignedBB[] boundingBoxes = this.getCollisionBoxes(
                this.getTileEntity(worldIn, pos)
        );

        for (final AxisAlignedBB box : boundingBoxes) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return this.getMainBoundingBox(
                this.getTileEntity(source, pos)
        );
    }

    @Override
    public int getMetaFromState(@Nullable final IBlockState state) {
        return 0;
    }


    @Override
    public int getLightOpacity(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        if (this instanceof BlockCoverable) {
            return ((BlockCoverable<T>) this).getCoverLightOpacity(getTileEntity(world, pos));
        }

        return Constants.DEFAULT_LIGHT_OPACITY;
    }

    @Override
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        if (this instanceof BlockCoverable) {
            return ((BlockCoverable<T>) this).getCoverLightValue(getTileEntity(world, pos));
        }

        return 0;
    }

    @Override
    public boolean isSideSolid(final IBlockState baseState, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return super.isSideSolid(baseState, world, pos, side); //@todo implement
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public boolean isOpaqueCube(final IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(final IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(@Nullable final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
