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

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.blocks.BaseBlock;
import tk.eichler.carpentersblocks.registry.helper.RegistryHelper;

@SideOnly(Side.CLIENT)
public final class ModelRegistry implements BaseRegistry {

    private static ModelRegistry instance;

    public static ModelRegistry getInstance() {
        if (instance == null) {
            instance = new ModelRegistry();
        }

        return instance;
    }

    @Override
    public boolean receivesEvents() {
        return true;
    }


    @Override
    public void onPreInit() {
        registerLocations();
    }

    private void registerLocations() {
        for (BaseBlock block : BlockRegistry.ALL_BLOCKS) {
            RegistryHelper.registerModelLocation(block);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onModelBakeEvent(final ModelBakeEvent event) {
        loadModels(event.getModelRegistry());
    }

    private static void loadModels(final IRegistry<ModelResourceLocation, IBakedModel> modelRegistry) {
        for (BaseBlock block : BlockRegistry.ALL_BLOCKS) {
            modelRegistry.putObject(RegistryHelper.getModelLocation(block, false), block.getModel());
            modelRegistry.putObject(RegistryHelper.getModelLocation(block, true), block.getModel());
        }
    }
}
