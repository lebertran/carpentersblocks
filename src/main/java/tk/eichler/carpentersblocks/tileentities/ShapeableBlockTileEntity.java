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

package tk.eichler.carpentersblocks.tileentities;

import mcp.MethodsReturnNonnullByDefault;
import tk.eichler.carpentersblocks.blocks.BlockShapeable;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.data.ShapeableData;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShapeableBlockTileEntity extends CoverableBlockTileEntity {

    private final ShapeableData shapeableData = ShapeableData.createInstance();

    public ShapeableBlockTileEntity() {
        this.getDataInstance().addUpdateListener(this);
    }

    @Override
    protected ShapeableData getDataInstance() {
        return shapeableData;
    }

    public void setShapeableData(@Nonnull final EnumShape shape, @Nonnull final EnumOrientation orientation) {
        getDataInstance().setShape(shape);
        getDataInstance().setOrientation(orientation);

        this.shapeableData.checkForChanges();
    }

    public ShapeableData getShapeableData() {
        return this.shapeableData;
    }

    @Override
    public void onDataUpdate() {
        updateState(DataProperty.COVERABLE_DATA, getDataInstance());
        updateState(BlockShapeable.PROP_SHAPE, getDataInstance().getShape());
        updateState(BlockShapeable.PROP_ORIENTATION, getDataInstance().getOrientation());

        triggerStateUpdate();
    }
}
