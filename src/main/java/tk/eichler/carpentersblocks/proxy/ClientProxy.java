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
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tk.eichler.carpentersblocks.registry.BaseRegistry;
import tk.eichler.carpentersblocks.registry.ModelRegistry;
import tk.eichler.carpentersblocks.registry.TextureRegistry;

import java.util.List;

/**
 * Initialization proxy, run on the client only.
 */
@SuppressWarnings("unused")
public final class ClientProxy extends CommonProxy {
    /**
     * Registries, that run on the client only.
     */
    private static final List<BaseRegistry> CLIENT_REGISTRIES = ImmutableList.of(
            ModelRegistry.getInstance(),
            TextureRegistry.getInstance());

    @Override
    public void registerRegistries() {
        super.registerRegistries();

        CLIENT_REGISTRIES.stream()
                .filter(BaseRegistry::receivesEvents)
                .forEach(MinecraftForge.EVENT_BUS::register);
    }

    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);

        CLIENT_REGISTRIES.forEach(BaseRegistry::onPreInit);
    }

    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);

        CLIENT_REGISTRIES.forEach(BaseRegistry::onInit);
    }
}
