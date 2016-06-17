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
import net.minecraft.nbt.NBTTagCompound;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShapeableData extends CoverableData {

    private static final String NBT_SHAPE = "Shape";
    private static final String NBT_ORIENTATION = "Orientation";

    private EnumShape shape;
    private EnumOrientation orientation;

    public ShapeableData(@Nullable final ItemStack itemStack, final EnumShape shape, final EnumOrientation orientation) {
        super(itemStack);

        this.shape = shape;
        this.orientation = orientation;
    }

    @Override
    public void fromNBT(final NBTTagCompound nbt) {
        super.fromNBT(nbt);

        setShape(EnumShape.valueOf(nbt.getString(NBT_SHAPE)));
        setOrientation(EnumOrientation.valueOf(nbt.getString(NBT_ORIENTATION)));
    }

    @Override
    public void toNBT(final NBTTagCompound nbt) {
        super.toNBT(nbt);

        nbt.setString(NBT_SHAPE, shape.name());
        nbt.setString(NBT_ORIENTATION, orientation.name());
    }

    public void setShape(final EnumShape shape) {
        if (this.shape != shape) {
            this.shape = shape;
            setChanged(true);
        }
    }

    public void setOrientation(final EnumOrientation orientation) {
        if (this.orientation != orientation) {
            this.orientation = orientation;
            setChanged(true);
        }
    }

    public EnumShape getShape() {
        return this.shape;
    }

    public EnumOrientation getOrientation() {
        return this.orientation;
    }

    public static ShapeableData createInstance() {
        return new ShapeableData(null, EnumShape.FULL_BLOCK, EnumOrientation.UP);
    }

}
