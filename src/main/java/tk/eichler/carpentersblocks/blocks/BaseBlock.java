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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import tk.eichler.carpentersblocks.CarpentersBlocks;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A basic block with default implementations for Minecraft rendering and block logic.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BaseBlock extends BlockContainer {
    private ItemBlock itemBlock = null;

    BaseBlock(final Material materialIn) {
        super(materialIn);

        setCreativeTab(CarpentersBlocks.CREATIVE_TAB);

        setRegistryName(getRegisterName());
        setUnlocalizedName(getRegisterName());
    }

    protected abstract IProperty[] getProperties();
    protected abstract IUnlistedProperty[] getUnlistedProperties();


    public abstract String getRegisterName();
    public abstract BaseModel getModel();
    public abstract TileEntity getTileEntity();

    public abstract void onRightClickEvent(PlayerInteractEvent.RightClickBlock event);
    public abstract void onLeftClickEvent(PlayerInteractEvent.LeftClickBlock event);


    public ItemBlock getItemBlock() {
        if (itemBlock == null) {
            itemBlock = BlockHelper.createItemBlock(this);
        }

        return itemBlock;
    }

    @Override
    public TileEntity createNewTileEntity(@Nullable final World worldIn, final int meta) {
        return getTileEntity();
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, getProperties(), getUnlistedProperties());
    }


    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(final IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(final IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
