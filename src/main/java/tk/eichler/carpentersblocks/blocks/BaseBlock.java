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

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.CarpentersBlocks;
import tk.eichler.carpentersblocks.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Initializes a generic block without any properties.
 */
public abstract class BaseBlock extends BlockContainer implements IGenericBlock {

    // Holds an instance of the corresponding ItemBlock
    private ItemBlock itemBlock = null;


    BaseBlock(Material materialIn) {
        super(materialIn);

        setCreativeTab(CarpentersBlocks.creativeTabs);

        setRegistryName(getRegisterName());
        setUnlocalizedName(getRegisterName());
    }

    @Nonnull
    public abstract String getRegisterName();

    @Override
    @Nonnull
    public abstract TileEntity createNewTileEntity(@Nonnull World worldIn, int meta);


    /**
     * Forge initialization methods
     */
    @Override
    public void registerBlock() {
        GameRegistry.register(this);
        GameRegistry.register(getItemBlock());
    }

    public ModelResourceLocation getBlockModelLocation() {
        return new ModelResourceLocation(new ResourceLocation(Constants.MOD_ID, getRegisterName()), "normal");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initCustomModelLocations() {
        final ResourceLocation location = new ResourceLocation(Constants.MOD_ID, getRegisterName());
        final ModelResourceLocation locationItem = new ModelResourceLocation(location, "inventory");

        ModelLoader.setCustomModelResourceLocation(getItemBlock(), 0, locationItem);
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nullable IBlockState state) {
                return getBlockModelLocation();
            }
        });
    }

    @Override
    public abstract void registerTileEntity();

    /**
     * Helper methods
     */
    @Nonnull
    public ItemBlock getItemBlock() {
        if (itemBlock == null) {
            itemBlock = createItemBlock();
        }

        return itemBlock;
    }

    @Nonnull
    private ItemBlock createItemBlock() {
        final ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getRegisterName());
        itemBlock.setUnlocalizedName(getUnlocalizedName());
        return itemBlock;
    }

    /**
     * Default implementations
     */
    @Override
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean getUseNeighborBrightness(IBlockState state) {
        return true;
    }
}
