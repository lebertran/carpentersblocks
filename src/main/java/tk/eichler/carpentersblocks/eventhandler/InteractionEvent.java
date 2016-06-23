/*
 * *
 *  * This file is part of Carpenter's Blocks.
 *  * <p>
 *  * Carpenter's Blocks is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 *  * option) any later version.
 *  * <p>
 *  * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *  * <p>
 *  * You should have received a copy of the GNU General Public License along with Foobar.  If not, see
 *  * <http://www.gnu.org/licenses/>.
 *  
 */

package tk.eichler.carpentersblocks.eventhandler;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import tk.eichler.carpentersblocks.util.ItemHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

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
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class InteractionEvent {

    private final World world;
    private final BlockPos blockPos;
    private final EntityPlayer player;
    private final ItemStack itemStack;
    private final EnumFacing facing;
    private final Vec3d hitVec;
    private final PlayerInteractEvent event;

    private final Interaction interaction;

    public static InteractionEvent createEvent(final PlayerInteractEvent.LeftClickBlock event) {
        return new InteractionEvent(
                event.getWorld(),
                event.getPos(),
                event.getHitVec(),
                event,
                event.getEntityPlayer(),
                event.getItemStack(),
                event.getFace(),
                false
        );
    }

    public static InteractionEvent createEvent(final PlayerInteractEvent.RightClickBlock event) {
        return new InteractionEvent(
                event.getWorld(),
                event.getPos(),
                event.getHitVec(),
                event,
                event.getEntityPlayer(),
                event.getItemStack(),
                event.getFace(),
                true
        );
    }

    private InteractionEvent(final World world, final BlockPos blockPos, final Vec3d hitVec, final PlayerInteractEvent event,
                             final EntityPlayer player, @Nullable final ItemStack heldStack,
                             @Nullable final EnumFacing facing, final boolean isRightClick) {
        this.world = world;
        this.blockPos = blockPos;
        this.hitVec = hitVec;
        this.itemStack = heldStack;
        this.facing = facing;
        this.player = player;
        this.event = event;

        this.interaction = Interaction.identifyInteraction(itemStack, player, isRightClick);
    }

    public Interaction getInteraction() {
        return this.interaction;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    @Nullable
    public ItemStack getItemStack() {
        return itemStack;
    }

    public EnumFacing getFacing(final EnumFacing fallbackFacing) {
        if (this.facing == null) {
            return fallbackFacing;
        }

        return facing;
    }

    public Vec3d getHitVec() {
        return hitVec;
    }

    public boolean isHammer() {
        return ItemHelper.isCarpentersHammer(getItemStack());
    }

    public boolean isRemote() {
        return this.world.isRemote;
    }

    public IBlockState getState() {
        return this.getWorld().getBlockState(this.getBlockPos());
    }

    public void setSuccessful(final boolean isSuccessful) {
        if (isSuccessful) {
            this.event.setCanceled(true);
        } else {
            this.event.setCanceled(false);
        }
    }
}
