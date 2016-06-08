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
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShapeableData extends CoverableData {

    public static final String KEY_SHAPE = "Shape";

    public EnumShape currentShape;

    protected ShapeableData(@Nullable ItemStack itemStack, EnumShape currentShape) {
        super(itemStack);

        this.currentShape = currentShape;
    }

    public static ShapeableData createInstance() {
        return new ShapeableData(null, EnumShape.FULL_BLOCK);
    }

    public void setShape(EnumShape currentShape) {
        if (this.currentShape == currentShape) {
            return;
        }

        this.currentShape = currentShape;
        this.hasChanged = true;
    }

    public boolean isShape(EnumShape enumShape) {
        return this.currentShape == enumShape;
    }

    public void fromNBT(NBTTagCompound nbt) {
        super.fromNBT(nbt);

        EnumShape currentShape = EnumShape.FULL_BLOCK;
        if (nbt.hasKey(KEY_SHAPE) && !nbt.getString(KEY_SHAPE).equals("")) {
            currentShape = EnumShape.valueOf(nbt.getString(KEY_SHAPE));
        }

        setShape(currentShape);

        checkForChanges();
    }

    @Override
    public void toNBT(NBTTagCompound nbt) {
        super.toNBT(nbt);

        nbt.setString(KEY_SHAPE, this.currentShape.name());
    }

    @Override
    public String toString() {
        String blockName = (this.coveringBlock == null) ? "none" : this.coveringBlock.getLocalizedName();

        return MessageFormat.format("ShapeableData with block %s, meta %s and shape %s.", blockName, this.meta, this.currentShape.getName());
    }
}
