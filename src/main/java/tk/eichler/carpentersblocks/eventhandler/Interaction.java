/**
 * This file is part of Carpenter's Blocks.
 * <p>
 * Carpenter's Blocks is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * <p>
 * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with Foobar.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.eichler.carpentersblocks.eventhandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tk.eichler.carpentersblocks.util.BlockHelper;
import tk.eichler.carpentersblocks.util.ItemHelper;

public enum Interaction {
    COVER_REMOVE,
    COVER_ADD,
    SHAPE_TOGGLE_RIGHT_CLICK,
    SHAPE_TOGGLE_LEFT_CLICK,
    NONE;

    static Interaction identifyInteraction(final ItemStack heldStack, final EntityPlayer player, final boolean rightClick) {
        if (rightClick) {
            if (ItemHelper.isCarpentersHammer(heldStack)) {
                return SHAPE_TOGGLE_RIGHT_CLICK;
            }

            if (BlockHelper.isValidCoverBlock(heldStack)) {
                return COVER_ADD;
            }

            return NONE;
        } else {
            if (ItemHelper.isCarpentersHammer(heldStack)) {
                if (player.isSneaking()) {
                    return COVER_REMOVE;
                }

                return SHAPE_TOGGLE_LEFT_CLICK;
            }

            return NONE;
        }
    }
}
