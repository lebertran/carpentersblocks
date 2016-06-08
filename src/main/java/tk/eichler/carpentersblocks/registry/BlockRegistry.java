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

package tk.eichler.carpentersblocks.registry;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.blocks.BaseBlock;
import tk.eichler.carpentersblocks.blocks.BlockCarpentersBlock;
import tk.eichler.carpentersblocks.blocks.BlockCarpentersSlope;
import tk.eichler.carpentersblocks.model.CarpentersBlockModel;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockRegistry extends BaseRegistry {

    public static final BaseBlock[] ALL_BLOCKS = new BaseBlock[] {
            BlockCarpentersBlock.INSTANCE,
            BlockCarpentersSlope.INSTANCE
    };

    public static final BlockRegistry INSTANCE = new BlockRegistry();


    @Override
    public boolean receivesEvents() {
        return true;
    }

    @Override
    public void onPreInit() {
        registerBlocks();
        registerTileEntities();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onPreInitClient() {
        registerLocations();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onLeftClickEvent(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getHand() == EnumHand.OFF_HAND) return;

        final Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if (block instanceof BaseBlock) {
            ((BaseBlock) block).onLeftClickEvent(event);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() == EnumHand.OFF_HAND) return;

        final Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if (block instanceof BaseBlock) {
            ((BaseBlock) block).onRightClickEvent(event);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unused")
    public void onModelBakeEvent(ModelBakeEvent event) {
        loadModels(event.getModelRegistry());
    }

    @SideOnly(Side.CLIENT)
    private static void loadModels(IRegistry<ModelResourceLocation, IBakedModel> modelRegistry) {
        for (BaseBlock block : BlockRegistry.ALL_BLOCKS) {
            modelRegistry.putObject(block.getBlockModelLocation(), block.getModel());
        }
    }

    private void registerBlocks() {
        for (BaseBlock block : ALL_BLOCKS) {
            block.registerBlock();
        }
    }

    private void registerLocations() {
        for (BaseBlock block : ALL_BLOCKS) {
            block.initCustomModelLocations();
        }
    }

    private void registerTileEntities() {
        for (BaseBlock block : BlockRegistry.ALL_BLOCKS) {
            block.registerTileEntity();
        }
    }
}
