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

package tk.eichler.carpentersblocks.data.properties;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum EnumOrientation implements IStringSerializable {
    UP("up", null, EnumFacing.UP),
    NORTH_UP("north_up", EnumFacing.NORTH, EnumFacing.UP),
    WEST_UP("west_up", EnumFacing.WEST, EnumFacing.UP),
    SOUTH_UP("south_up", EnumFacing.SOUTH, EnumFacing.UP),
    EAST_UP("east_up", EnumFacing.EAST, EnumFacing.UP),

    DOWN("down", null, EnumFacing.DOWN),
    NORTH_DOWN("north_down", EnumFacing.NORTH, EnumFacing.DOWN),
    WEST_DOWN("west_down", EnumFacing.WEST, EnumFacing.DOWN),
    SOUTH_DOWN("south_down", EnumFacing.SOUTH, EnumFacing.DOWN),
    EAST_DOWN("east_down", EnumFacing.EAST, EnumFacing.DOWN),

    WEST("west", EnumFacing.WEST, null),
    EAST("east", EnumFacing.EAST, null),
    NORTH("north", EnumFacing.NORTH, null),
    SOUTH("south", EnumFacing.SOUTH, null);

    public static final EnumOrientation[] ALL_UP_DOWN = new EnumOrientation[] {
            NORTH_UP, WEST_UP, SOUTH_UP, EAST_UP,
            NORTH_DOWN, WEST_DOWN, SOUTH_DOWN, EAST_DOWN
    };

    final String name;
    private final EnumFacing horizontal;
    private final EnumFacing vertical;

    EnumOrientation(@Nonnull final String name,
                    @Nullable final EnumFacing horizontal, @Nullable final EnumFacing vertical) {
        this.name = name;

        if (horizontal != null && isVertical(horizontal)) {
            throw new IllegalArgumentException("Invalid horizontal facing.");
        }

        if (vertical != null && !isVertical(vertical)) {
            throw new IllegalArgumentException("Invalid vertical facing.");
        }

        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public static EnumOrientation getOrientationFromFacing(final EnumFacing facing) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            return get(null, facing.getOpposite());
        } else {
            return get(facing.getOpposite(), null);
        }
    }

    @Nullable
    public EnumFacing getVertical() {
        return this.vertical;
    }

    @Nullable
    public EnumFacing getHorizontal() {
        return this.horizontal;
    }

    private static boolean isVertical(@Nonnull final EnumFacing facing) {
        return facing.getAxis() == EnumFacing.Axis.Y;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Nonnull
    public static EnumOrientation get(@Nullable final EnumFacing horizontal, @Nullable final EnumFacing vertical) {
        for (EnumOrientation o : EnumOrientation.values()) {
            if (o.horizontal == horizontal && o.vertical == vertical) {
                return o;
            }
        }

        throw new IllegalArgumentException(
                MessageFormat.format("No EnumOrientation with given information: horizontal {0} and vertical {1}", horizontal, vertical));
    }
}
