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

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;
import tk.eichler.carpentersblocks.data.CoverableData;

public final class Properties {
    private Properties() {
        // do not instantiate
    }

    public static final DataProperty<CoverableData> COVER_DATA = new DataProperty<>(CoverableData.class);

    public static final PropertyEnum<EnumShape> SHAPE =
            PropertyEnum.create("shape", EnumShape.class);

    public static final PropertyEnum<EnumOrientation> ORIENTATION =
            PropertyEnum.create("orientation", EnumOrientation.class);

    public static final PropertyEnum<EnumFacing> COVER_FACING =
            PropertyEnum.create("cover_facing", EnumFacing.class);
}
