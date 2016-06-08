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

package tk.eichler.carpentersblocks.data;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DataProperty<T extends CoverableData> implements IUnlistedProperty<T> {

    private static final String NAME = "DataProperty";
    private final Class<T> clazz;

    public static final DataProperty<ShapeableData> SHAPEABLE_DATA = new DataProperty<>(ShapeableData.class);

    private DataProperty(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isValid(T value) {
        return true;
    }

    @Override
    public Class<T> getType() {
        return clazz;
    }

    @Override
    public String valueToString(T value) {
        return value.toString();
    }
}