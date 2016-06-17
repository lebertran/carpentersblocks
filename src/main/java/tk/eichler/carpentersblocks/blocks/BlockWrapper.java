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
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.util.BlockHelper;
import tk.eichler.carpentersblocks.util.CarpentersCreativeTab;
import tk.eichler.carpentersblocks.util.ItemHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class BlockWrapper<T extends BaseBlock> extends BlockContainer implements BaseBlock {


    private ItemBlock itemBlock = null;

    public BlockWrapper() {
        super(Material.WOOD);

        setCreativeTab(CarpentersCreativeTab.get());

        setRegistryName(getName());
        setUnlocalizedName(getName());
    }

    @Override
    public TileEntity createNewTileEntity(@Nullable final World worldIn, final int meta) {
        return this.createTileEntity();
    }

    @SuppressWarnings("unchecked")
    public T getActualBlock() {
        return (T) this;
    }

    public ItemBlock getItemBlock() {
        if (itemBlock == null) {
            itemBlock = BlockHelper.createItemBlock(this);
        }

        return this.itemBlock;
    }

    public void onRightClickEvent(final PlayerInteractEvent.RightClickBlock event) {
        final EntityPlayer player = event.getEntityPlayer();
        final ItemStack heldItem = player.getHeldItemMainhand();
        final EnumFacing facing;

        if (event.getFace() == null) {
            facing = EnumFacing.NORTH;
        } else {
            facing = event.getFace();
        }

        if (heldItem == null) {
            return;
        }

        if (this instanceof BlockShapeable && ItemHelper.isCarpentersHammer(heldItem)) { //@TODO replace
            ((BlockShapeable) this).onCarpentersHammerRightClick(event.getWorld(), event.getPos(), facing);
            event.setCanceled(true);
            return;
        }

        if (this instanceof BlockCoverable && BlockHelper.isValidCoverBlock(heldItem)) {
            if (((BlockCoverable) this).onChangeCover(event.getWorld(), event.getPos(), heldItem, facing)) {
                event.setCanceled(true);
            }
        }
    }

    public void onLeftClickEvent(final PlayerInteractEvent.LeftClickBlock event) {
        final ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();

        if (heldItem == null) {
            return;
        }

        if (ItemHelper.isCarpentersHammer(heldItem)) { //@TODO replace
            if ((this instanceof BlockCoverable) && (event.getEntityPlayer().isSneaking())) {
                ((BlockCoverable) this).onRemoveCover(event.getWorld(), event.getPos());
            } else if (this instanceof BlockShapeable) {
                ((BlockShapeable) this).onCarpentersHammerLeftClick(
                        event.getWorld(), event.getPos(), event.getEntityPlayer().getHorizontalFacing());
            }
        }
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, this.getProperties(), this.getUnlistedProperties());
    }

    @Override
    public IBlockState getExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return this.createExtendedState(state, world, pos);
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

        final AxisAlignedBB[] boundingBoxes = this.getCollisionBoxes(state, worldIn, pos);

        for (final AxisAlignedBB box : boundingBoxes) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return this.getMainBoundingBox(state, source, pos);
    }

    @Override
    public int getMetaFromState(@Nullable final IBlockState state) {
        return 0;
    }

    @Override
    public int getLightOpacity(final IBlockState state) {
        if (this instanceof BlockCoverable) {
            return ((BlockCoverable) this).getCoverLightValue(state);
        }

        return Constants.DEFAULT_LIGHT_OPACITY;
    }

    @Override
    public int getLightValue(final IBlockState state) {
        if (this instanceof BlockCoverable) {
            return ((BlockCoverable) this).getCoverLightValue(state);
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
