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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.tileentities.CoverableBlockTileEntity;
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BlockCoverable<T extends CoverableData> extends BaseBlock {

    protected BlockCoverable(final Material material) {
        super(material);
    }

    @Override
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

        if (heldItem.getItem() == Items.STICK) { //@TODO replace
            onCarpentersHammerInteract(event.getWorld(), event.getPos(), facing, true);
            event.setCanceled(true);
            return;
        }

        if (BlockHelper.isValidCoverBlock(heldItem)) {
            if (onCarpenterChangeBlock(event.getWorld(), event.getPos(), heldItem, facing)) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onLeftClickEvent(final PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCanceled() || event.getHand() == EnumHand.OFF_HAND) {
            return;
        }

        event.setCanceled(false);
        event.setUseBlock(Event.Result.ALLOW);
        event.setUseItem(Event.Result.DENY);
    }
    @Override
    public void onBlockClicked(final World worldIn, final BlockPos pos, final EntityPlayer player) {
        super.onBlockClicked(worldIn, pos, player);

        final ItemStack heldItem = player.getHeldItemMainhand();

        if (heldItem == null) {
            return;
        }

        if (heldItem.getItem() == Items.STICK) { //@TODO replace

            if (player.isSneaking()) {
                onCarpentersHammerRemove(worldIn, pos);

            } else {
                onCarpentersHammerInteract(worldIn, pos, player.getHorizontalFacing(), false);
            }
        }
    }

    private boolean onCarpenterChangeBlock(final World world,
                                           final BlockPos pos,
                                           final ItemStack heldItem,
                                           final EnumFacing facing) {
        final CoverableBlockTileEntity cbte = getTileEntity(world, pos);

        return cbte != null && cbte.trySetBlockStack(heldItem);
    }

    protected boolean onCarpentersHammerInteract(final World world,
                                                 final BlockPos pos,
                                                 final EnumFacing facing,
                                                 final boolean isRightClick) {
        return false;
    }

    private boolean onCarpentersHammerRemove(final IBlockAccess world, final BlockPos pos) {
        final CoverableBlockTileEntity cbte = getTileEntity(world, pos);

        return cbte != null && cbte.removeBlockStack();
    }


    public abstract DataProperty<T> getDataProperty();

    @Nullable
    protected CoverableBlockTileEntity getTileEntity(final IBlockAccess world, final BlockPos pos) {
        final TileEntity te = world.getTileEntity(pos);

        if (!(te instanceof CoverableBlockTileEntity)) {
            return null;
        }

        return (CoverableBlockTileEntity) te;
    }

    public CoverableData getCoverableData(final IBlockState state) {
        final CoverableData data = ((IExtendedBlockState) state).getValue(getDataProperty());

        if (data == null) {
            return new CoverableData(null);
        }

        return data;
    }

    @Override
    public IBlockState getExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final CoverableBlockTileEntity tileEntity = getTileEntity(world, pos);

        IExtendedBlockState newState;

        if ((tileEntity != null) && (tileEntity.getState() != null)) {
            newState = tileEntity.getState();
        } else {
            newState = (IExtendedBlockState) state;
        }

        if (newState.getValue(DataProperty.COVERABLE_DATA) == null) {
            newState = newState.withProperty(DataProperty.COVERABLE_DATA, CoverableData.createInstance());
        }

        return newState;
    }

    @Override
    public IUnlistedProperty[] getUnlistedProperties() {
        return new IUnlistedProperty[] {
                DataProperty.COVERABLE_DATA
        };
    }


    /**
     * Default rendering implementations
     *
     * Normally, a carpenter's block is partly transparent.
     */
    @Override
    public int getLightOpacity(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return getCoverableData(state).getLightOpacity();
    }

    @Override
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return getCoverableData(state).getLightValue();
    }

    @Override
    public boolean isSideSolid(final IBlockState base_state, final IBlockAccess world,
                               final BlockPos pos, final EnumFacing side) {
        return getCoverableData(base_state).hasCover();
    }
}
