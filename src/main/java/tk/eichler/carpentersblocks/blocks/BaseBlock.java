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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.CarpentersBlocks;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.model.BaseModel;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A generic block without any properties.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BaseBlock extends BlockContainer {

    private ItemBlock itemBlock = null;

    BaseBlock(Material materialIn) {
        super(materialIn);

        setCreativeTab(CarpentersBlocks.creativeTabs);

        setRegistryName(getRegisterName());
        setUnlocalizedName(getRegisterName());
    }


    public abstract String getRegisterName();

    public abstract BaseModel getModel();

    @Override
    public abstract TileEntity createNewTileEntity(World worldIn, int meta);

    public abstract void registerTileEntity();

    public void onRightClickEvent(PlayerInteractEvent.RightClickBlock event) {}

    public void onLeftClickEvent(PlayerInteractEvent.LeftClickBlock event) {}


    public void registerBlock() {
        GameRegistry.register(this);
        GameRegistry.register(getItemBlock());
    }

    public ModelResourceLocation getBlockModelLocation() {
        return new ModelResourceLocation(new ResourceLocation(Constants.MOD_ID, getRegisterName()), "normal");
    }

    @SideOnly(Side.CLIENT)
    public void initCustomModelLocations() {
        final ResourceLocation location = new ResourceLocation(Constants.MOD_ID, getRegisterName());
        final ModelResourceLocation locationItem = new ModelResourceLocation(location, "inventory");

        ModelLoader.setCustomModelResourceLocation(getItemBlock(), 0, locationItem);
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nullable IBlockState state) {
                return getBlockModelLocation();
            }
        });
    }

    public ItemBlock getItemBlock() {
        if (itemBlock == null) {
            itemBlock = createItemBlock();
        }

        return itemBlock;
    }

    private ItemBlock createItemBlock() {
        final ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getRegisterName());
        itemBlock.setUnlocalizedName(getUnlocalizedName());
        return itemBlock;
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
