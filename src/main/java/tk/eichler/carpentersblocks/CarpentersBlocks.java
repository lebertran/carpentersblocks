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

package tk.eichler.carpentersblocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tk.eichler.carpentersblocks.proxy.CommonProxy;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Carpenter's Block mod entry point.
 *
 * Invokes preInit, init and postInit in the appropriate proxy.
 */
@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION)
public final class CarpentersBlocks {

    /**
     * Holds a mod instance.
     */
    @Instance(Constants.MOD_ID)
    public static CarpentersBlocks instance;

    /**
     * Holds a proxy depending on the current side.
     */
    @SidedProxy(serverSide = Constants.SERVER_PROXY, clientSide = Constants.CLIENT_PROXY)
    public static CommonProxy proxy;

    /**
     * Triggers preInit, refer to {@link FMLPreInitializationEvent}.
     *
     * @param event FMLPreInitializationEvent.
     */
    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        proxy.registerRegistries();
        proxy.preInit(event);
    }

    /**
     * Triggers init, refer to {@link FMLInitializationEvent}.
     *
     * @param event FMLInitializationEvent.
     */
    @EventHandler
    public void init(final FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * Triggers postInit, refer to {@link FMLPostInitializationEvent}.
     *
     * @param event FMLPostInitializationEvent.
     */
    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
