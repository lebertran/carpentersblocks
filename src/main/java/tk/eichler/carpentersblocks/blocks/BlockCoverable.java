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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tk.eichler.carpentersblocks.tileentities.CarpentersBlockTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A base block that can handle cover and state changes.
 */
public abstract class BlockCoverable extends BaseBlock {

    // Block property, which holds an ItemStack of the covering block.
    public static final IUnlistedProperty<ItemStack> PROP_COVER_BLOCK = new IUnlistedProperty<ItemStack>() {
        @Override
        public String getName() {
            return "block";
        }

        @Override
        public boolean isValid(ItemStack value) {
            return true;
        }

        @Override
        public Class<ItemStack> getType() {
            return ItemStack.class;
        }

        @Override
        public String valueToString(ItemStack value) {
            return value.getItem().getRegistryName().toString();
        }
    };


    public BlockCoverable(Material material) {
        super(material);

        setLightOpacity(255);
    }


    /**
     * Should return an {@link IProperty}, defining the form of the block.
     * @return IProperty
     */
    public abstract IProperty<?> getShapeProperty();



    /**
     * Triggered if a {@link BlockCoverable} is clicked
     */
    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        if (worldIn.isRemote) {
            return;
        }

        if (playerIn == null || playerIn.getHeldItemMainhand() == null) return;

        if (playerIn.isSneaking() && playerIn.getHeldItemMainhand().getItem() == Items.STICK) { //@TODO replace
            onCarpentersHammerInteract(true, worldIn, pos, worldIn.getBlockState(pos));
        }
    }

    /**
     * Triggered if a {@link BlockCoverable} is right-clicked and if the executing {@link EntityPlayer} is not sneaking.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.OFF_HAND) return false;

        if (heldItem == null) return false;

        if (BlockHelper.getBlockFromItemStack(heldItem) != null) {
            return onCarpenterChangeBlock(worldIn, pos, state, heldItem, side);
        }

        if (heldItem.getItem() == Items.STICK) {//@TODO replace
            return onCarpentersHammerInteract(false, worldIn, pos, state);
        }

        return false;
    }

    /**
     * Triggered when a {@link BlockCoverable} is right-clicked holding a block.
     */
    protected boolean onCarpenterChangeBlock(World world, BlockPos pos, IBlockState state, ItemStack heldItem, EnumFacing side) {
        final TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null || !(tileEntity instanceof CarpentersBlockTileEntity)) return false;
        final CarpentersBlockTileEntity cbte = (CarpentersBlockTileEntity) tileEntity;

        if (cbte.getBlock() == null && BlockHelper.isValidCarpentersProp(heldItem)) { //@TODO move check to TileEntity
            cbte.setBlockStack(heldItem);
            return true;
        }


        return false;
    }

    /**
     * Triggered if a {@link BlockCoverable} is right-clicked holding a Carpenter's Hammer.
     */
    protected boolean onCarpentersHammerInteract(boolean isSneaking, World world, BlockPos pos, IBlockState state) {
        final TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null || !(tileEntity instanceof CarpentersBlockTileEntity)) return false;
        final CarpentersBlockTileEntity cbte = (CarpentersBlockTileEntity) tileEntity;

        if (isSneaking && cbte.getBlock() != null) {
            cbte.setBlockStack(null);
            return true;
        }

        if (!isSneaking) {
            cbte.toggleShapes();
            return true;
        }

        return false;
    }



    /**
     * Default rendering implementations
     *
     * Normally, a carpenter's block is partly transparent.
     */
    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public void registerTileEntity() {
        GameRegistry.registerTileEntity(CarpentersBlockTileEntity.class, getRegisterName() + ":tile_entity");
    }

    /**
     * Data implementations
     */
    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new CarpentersBlockTileEntity();
    }

    @Nonnull
    public CarpentersBlockTileEntity getCarpentersBlockTileEntity(IBlockAccess world, BlockPos pos) {
        final TileEntity te = world.getTileEntity(pos);

        if (te instanceof CarpentersBlockTileEntity) {
            return (CarpentersBlockTileEntity) te;
        }

        FMLLog.severe("The tile entity at position %s is invalid", pos.toString());
        return (CarpentersBlockTileEntity) te; // This will result in an exception, which is intended to avoid further damage.
    }

    @Override
    @Nonnull
    public IBlockState getExtendedState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
        final CarpentersBlockTileEntity tileEntity = getCarpentersBlockTileEntity(world, pos);

        return (tileEntity.getState() != null) ? tileEntity.getState() : state;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[] {getShapeProperty()}, new IUnlistedProperty[] {
                BlockCoverable.PROP_COVER_BLOCK
        });
    }
}
