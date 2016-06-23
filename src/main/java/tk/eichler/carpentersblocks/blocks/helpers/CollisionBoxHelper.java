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
 *
 */

package tk.eichler.carpentersblocks.blocks.helpers;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.AxisAlignedBB;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class CollisionBoxHelper {
    public static final AxisAlignedBB COLLISION_FULL_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final AxisAlignedBB COLLISION_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    public static final AxisAlignedBB COLLISION_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    private static final AxisAlignedBB COLLISION_SLAB_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
    private static final AxisAlignedBB COLLISION_SLAB_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB COLLISION_SLAB_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    private static final AxisAlignedBB STAIRS_TOP_QUARTER_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
    private static final AxisAlignedBB STAIRS_TOP_QUARTER_WEST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB STAIRS_TOP_QUARTER_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    private static final AxisAlignedBB STAIRS_TOP_QUARTER_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);

    private static final AxisAlignedBB STAIRS_BOTTOM_QUARTER_EAST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
    private static final AxisAlignedBB STAIRS_BOTTOM_QUARTER_WEST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB STAIRS_BOTTOM_QUARTER_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    private static final AxisAlignedBB STAIRS_BOTTOM_QUARTER_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);


    public static AxisAlignedBB getQuarterStairs(final EnumOrientation orientation) {
        switch (orientation) {
            case NORTH_UP:
                return null;
            case WEST_UP:
                return null;
            case SOUTH_UP:
                return null;
            case EAST_UP:
                return null;
            case NORTH_DOWN:
                return STAIRS_BOTTOM_QUARTER_NORTH;
            case WEST_DOWN:
                return STAIRS_BOTTOM_QUARTER_WEST;
            case SOUTH_DOWN:
                return STAIRS_BOTTOM_QUARTER_SOUTH;
            case EAST_DOWN:
                return STAIRS_BOTTOM_QUARTER_EAST;
            default:
                return null;
        }
    }

    public static AxisAlignedBB getSlabCollisionBox(final EnumOrientation orientation) {
        switch (orientation) {
            case UP:
                return CollisionBoxHelper.COLLISION_SLAB_TOP;
            case DOWN:
                return CollisionBoxHelper.COLLISION_SLAB_BOTTOM;
            case SOUTH:
                return CollisionBoxHelper.COLLISION_SLAB_SOUTH;
            case WEST:
                return CollisionBoxHelper.COLLISION_SLAB_WEST;
            case EAST:
                return CollisionBoxHelper.COLLISION_SLAB_EAST;
            case NORTH:
                return CollisionBoxHelper.COLLISION_SLAB_NORTH;
            default:
                break;
        }
        return COLLISION_FULL_BLOCK;
    }

    private CollisionBoxHelper() {
        // do not instantiate
    }


}
