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

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public enum EnumShape implements IStringSerializable {
    FULL_BLOCK("full_block", EnumOrientation.values()),
    SLAB("slab", EnumOrientation.UP, EnumOrientation.DOWN,
            EnumOrientation.WEST, EnumOrientation.EAST, EnumOrientation.SOUTH, EnumOrientation.NORTH),

    SLOPE("slope", EnumOrientation.ALL_UP_DOWN);

    private final String name;

    private List<EnumOrientation> validOrientations;

    EnumShape(final String name, final EnumOrientation... validOrientations) {
        if (validOrientations.length <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("EnumShape {0} has no valid orientations.", name));
        }

        this.name = name;
        this.validOrientations = ImmutableList.copyOf(validOrientations);
    }

    public EnumOrientation getNextOrientation(final EnumOrientation current) {
        for (int i = 0; i < validOrientations.size(); i++) {
            if (validOrientations.get(i) == current) {

                if (i + 1 >= validOrientations.size()) {
                    return validOrientations.get(0);
                }

                return validOrientations.get(i + 1);

            }
        }

        throw new IllegalArgumentException("Given orientation is not a valid orientation");
    }

    public boolean isOrientationValid(final EnumOrientation orientation) {
        return this.validOrientations.contains(orientation);
    }

    public List<EnumOrientation> getValidOrientations() {
        return this.validOrientations;
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

}
