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

package tk.eichler.carpentersblocks.registry.helper;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.blocks.BlockWrapper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class RegistryHelper {
    private RegistryHelper() {
        // do not instantiate
    }

    public static void registerBlock(final BlockWrapper block) {
        GameRegistry.register(block);
        GameRegistry.register(block.getItemBlock());
    }

    public static void registerTileEntity(final BlockWrapper block) {
        GameRegistry.registerTileEntity(block.createTileEntity().getClass(),
                block.getName() + ":tile_entity");
    }

    public static ItemBlock createItemBlock(final BlockWrapper wrapper) {
        final ItemBlock itemBlock = new ItemBlock(wrapper);
        itemBlock.setRegistryName(wrapper.getRegistryName());
        itemBlock.setUnlocalizedName(wrapper.getUnlocalizedName());
        return itemBlock;
    }

    @SideOnly(Side.CLIENT)
    public static void registerModelLocation(final BlockWrapper block) {
        final ModelResourceLocation blockLocation = getModelLocation(block, false);

        ModelLoader.setCustomModelResourceLocation(block.getItemBlock(), 0, getModelLocation(block, true));
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nullable final IBlockState state) {
                return blockLocation;
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public static ModelResourceLocation getModelLocation(final BlockWrapper block, final boolean isItem) {
        final String meta;

        if (isItem) {
            meta = "inventory";
        } else {
            meta = "normal";
        }

        return new ModelResourceLocation(new ResourceLocation(Constants.MOD_ID, block.getName()), meta);
    }
}
