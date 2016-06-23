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
package tk.eichler.carpentersblocks.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import org.junit.Assert;
import org.junit.Test;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;


public class ShapeableDataTest {

    private final ShapeableData shapeableData;

    public ShapeableDataTest() {
        this.shapeableData = new ShapeableData(null, EnumFacing.NORTH, EnumShape.FULL_BLOCK, EnumOrientation.SOUTH);
    }

    @Test
    public void fromNBT() throws Exception {
        final NBTTagCompound nbt = new NBTTagCompound();

        shapeableData.toNBT(nbt);

        final ShapeableData data = new ShapeableData(null, EnumFacing.UP, EnumShape.SLOPE, EnumOrientation.EAST_DOWN);
        data.fromNBT(nbt);

        Assert.assertEquals(data.getItemStack(), this.shapeableData.getItemStack());
        Assert.assertEquals(data.getOrientation(), this.shapeableData.getOrientation());
        Assert.assertEquals(data.getCoverFacing(), this.shapeableData.getCoverFacing());
        Assert.assertEquals(data.getShape(), this.shapeableData.getShape());
    }

    @Test(expected = NullPointerException.class)
    public void setShapeData() throws Exception {
        final ShapeableData data = ShapeableData.createInstance();

        data.setShapeData(EnumShape.SLOPE, EnumOrientation.NORTH);

        Assert.assertTrue(data.getShape().isOrientationValid(data.getOrientation()));
    }

}