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

package tk.eichler.carpentersblocks.eventhandler;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tk.eichler.carpentersblocks.blocks.BlockWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class InteractionHandler implements EventHandler {

    private static InteractionHandler instance;

    public static InteractionHandler getInstance() {
        if (instance == null) {
            instance = new InteractionHandler();
        }

        return instance;
    }

    // @workaround: onLeftClickEvent is triggered multiple times. Until this is fixed, we have to do the following:
    private boolean hasTriggered = false;

    @SubscribeEvent
    public void onLeftClickEvent(final PlayerInteractEvent.LeftClickBlock event) {
        if (event.getHand() == EnumHand.OFF_HAND || event.isCanceled()) {
            return;
        }

        if (!hasTriggered) {
            final Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
            if (block instanceof BlockWrapper) {
                ((BlockWrapper) block).onLeftClickEvent(event);
            }

            hasTriggered = true;
        } else {
            hasTriggered = false;
        }
    }

    @SubscribeEvent
    public void onRightClickEvent(final PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() == EnumHand.OFF_HAND) {
            return;
        }

        final Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if (block instanceof BlockWrapper) {
            ((BlockWrapper) block).onRightClickEvent(event);
        }
    }
}
