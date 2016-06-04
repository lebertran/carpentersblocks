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

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BaseRegistry {

    public static final BaseRegistry[] ALL_REGISTRIES = new BaseRegistry[] {
            BlockRegistry.INSTANCE,
            TextureRegistry.INSTANCE
    };


    public BaseRegistry() {
        if (receivesEvents()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    public void onPreInit() {}

    @SideOnly(Side.CLIENT)
    public void onPreInitClient() {}

    public void onInit() {}

    @SideOnly(Side.CLIENT)
    public void onInitClient() {}

    /**
     * Override this method to return true if the registry has its own EventHandler methods
     * @return boolean
     */
    public boolean receivesEvents() {
        return false;
    }
}
