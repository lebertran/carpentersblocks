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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A base block that can handle cover and state changes.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BlockCoverable<T extends CoverableData> extends BaseBlock {


    protected BlockCoverable(Material material) {
        super(material);
    }


    public abstract DataProperty<T> getDataProperty();

    @Override
    public void onRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        final EntityPlayer player = event.getEntityPlayer();
        final ItemStack heldItem = player.getHeldItemMainhand();

        if (heldItem == null) {
            return;
        }

        if (heldItem.getItem() == Items.STICK) { //@TODO replace
            if (player.isSneaking()) {
                if( onCarpentersHammerRemove(event.getWorld(), event.getPos()) ) {
                    event.setUseItem(Event.Result.ALLOW);
                }
                event.setCanceled(true);
                return;
            }

            onCarpentersHammerInteract(event.getWorld(), event.getPos(), event.getFace());
            event.setCanceled(true);
            return;
        }

        if (BlockHelper.isValidCoverBlock(heldItem)) {
            if ( onCarpenterChangeBlock(event.getWorld(), event.getPos(), heldItem, event.getFace()) ) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onLeftClickEvent(PlayerInteractEvent.LeftClickBlock event) {
        final EntityPlayer player = event.getEntityPlayer();
        final ItemStack heldItem = player.getHeldItemMainhand();

        if (heldItem == null) {
            return;
        }

        if (heldItem.getItem() == Items.STICK && player.isSneaking()) { //@TODO replace
            if( onCarpentersHammerRemove(event.getWorld(), event.getPos()) ) {
                event.setUseItem(Event.Result.ALLOW);
            }
            event.setCanceled(true);
        }
    }


    private boolean onCarpenterChangeBlock(World world, BlockPos pos, ItemStack heldItem, EnumFacing facing) {
        final ShapeableBlockTileEntity cbte = getCarpentersBlockTileEntity(world, pos);

        return cbte != null && cbte.trySetBlockStack(heldItem);
    }

    private boolean onCarpentersHammerInteract(World world, BlockPos pos, EnumFacing facing) {
        final ShapeableBlockTileEntity cbte = getCarpentersBlockTileEntity(world, pos);

        return cbte != null && cbte.setShape(facing);
    }

    private boolean onCarpentersHammerRemove(IBlockAccess world, BlockPos pos) {
        final ShapeableBlockTileEntity cbte = getCarpentersBlockTileEntity(world, pos);

        return cbte != null && cbte.removeBlockStack();
    }



    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ShapeableBlockTileEntity();
    }

    @Override
    public void registerTileEntity() {
        GameRegistry.registerTileEntity(ShapeableBlockTileEntity.class, getRegisterName() + ":tile_entity");
    }

    @Nullable
    private ShapeableBlockTileEntity getCarpentersBlockTileEntity(IBlockAccess world, BlockPos pos) {
        final TileEntity te = world.getTileEntity(pos);

        if (! (te instanceof ShapeableBlockTileEntity)) {
            return null;
        }

        return (ShapeableBlockTileEntity) te;
    }


    public CoverableData getCoverableData(IBlockState state) {
        final CoverableData data = ((IExtendedBlockState) state).getValue(getDataProperty());

        if (data == null) {
            return new CoverableData(null);
        }

        return data;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        final ShapeableBlockTileEntity tileEntity = getCarpentersBlockTileEntity(world, pos);

        return (tileEntity != null && tileEntity.getState() != null) ? tileEntity.getState() : state;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] {
                getDataProperty()
        });
    }

    /**
     * Default rendering implementations
     *
     * Normally, a carpenter's block is partly transparent.
     */
    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getCoverableData(state).getLightOpacity();
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getCoverableData(state).getLightValue();
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
