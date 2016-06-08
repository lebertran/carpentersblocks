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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FacingData extends ShapeableData {
    public static final String KEY_FACING = "Facing";

    public EnumFacing currentFacing;

    public FacingData(@Nullable ItemStack itemStack, EnumShape currentShape, EnumFacing facing) {
        super(itemStack, currentShape);

        this.currentFacing = facing;
    }

    public static FacingData createInstance() {
        return new FacingData(null, EnumShape.FULL_BLOCK, EnumFacing.NORTH);
    }
}
