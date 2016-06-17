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

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.MinecraftForge;
import tk.eichler.carpentersblocks.eventhandler.EventHandler;
import tk.eichler.carpentersblocks.eventhandler.InteractionHandler;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Registers custom event handlers in the default {@link net.minecraftforge.fml.common.eventhandler.EventBus}.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EventHandlerRegistry implements BaseRegistry {

    private static final List<EventHandler> EVENT_HANDLERS = ImmutableList.of(InteractionHandler.getInstance());

    private static EventHandlerRegistry instance;

    public static EventHandlerRegistry getInstance() {
        if (instance == null) {
            instance = new EventHandlerRegistry();
        }

        return instance;
    }

    @Override
    public boolean receivesEvents() {
        return false;
    }

    @Override
    public void onInit() {
        EVENT_HANDLERS.stream().forEach(MinecraftForge.EVENT_BUS::register);
    }
}
