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

package tk.eichler.carpentersblocks.data.properties;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public enum EnumShape implements IStringSerializable {
    FULL_BLOCK("full_block"),
    SLAB("slab", EnumOrientation.UP, EnumOrientation.DOWN,
            EnumOrientation.WEST, EnumOrientation.EAST, EnumOrientation.SOUTH, EnumOrientation.NORTH),

    SLOPE("slope", EnumOrientation.ALL_UP_DOWN),
    NORTH_SLOPE("north_slope"),
    WEST_SLOPE("west_slope"),
    EAST_SLOPE("east_slope"),
    SOUTH_SLOPE("south_slope"),

    NORTH_TOP_SLOPE("north_top_slope"),
    SOUTH_TOP_SLOPE("south_top_slope"),
    WEST_TOP_SLOPE("west_top_slope"),
    EAST_TOP_SLOPE("east_top_slope");

    private final String name;

    private EnumOrientation[] validOrientations;

    EnumShape(final String name, final EnumOrientation... validOrientations) {
        this.name = name;

        this.validOrientations = validOrientations;

    }

    public EnumOrientation getNextOrientation(final EnumOrientation current) {
        for (int i = 0; i < validOrientations.length; i++) {
            if (validOrientations[i] == current) {

                if (i + 1 >= validOrientations.length) {
                    return validOrientations[0];
                }

                return validOrientations[i + 1];

            }
        }

        throw new IllegalArgumentException("Given orientation is not a valid orientation");
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

}
