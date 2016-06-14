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

package tk.eichler.carpentersblocks.proxy;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tk.eichler.carpentersblocks.registry.BaseRegistry;
import tk.eichler.carpentersblocks.registry.BlockRegistry;
import tk.eichler.carpentersblocks.registry.EventHandlerRegistry;

import java.util.List;

/**
 * Initialization proxy, run on both client and server.
 */
public class CommonProxy {

    /**
     * Registries, that run on both logical client and server.
     */
    private static final List<BaseRegistry> COMMON_REGISTRIES = ImmutableList.of(
            BlockRegistry.INSTANCE,
            EventHandlerRegistry.getInstance()
    );

    /**
     * Register all registries that have custom events in MinecraftForge's EventBus.
     */
    public void registerRegistries() {
        COMMON_REGISTRIES.stream()
                .filter(BaseRegistry::receivesEvents)
                .forEach(MinecraftForge.EVENT_BUS::register);
    }

    public void preInit(final FMLPreInitializationEvent event) {
        COMMON_REGISTRIES.forEach(BaseRegistry::onPreInit);
    }

    public void init(final FMLInitializationEvent event) {
        COMMON_REGISTRIES.forEach(BaseRegistry::onInit);
    }

    public void postInit(final FMLPostInitializationEvent event) {
    }
}
