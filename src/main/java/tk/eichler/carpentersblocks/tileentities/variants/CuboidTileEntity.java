/*
 * *
 *  * This file is part of Carpenter's Blocks.
 *  * <p>
 *  * Carpenter's Blocks is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 *  * option) any later version.
 *  * <p>
 *  * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *  * <p>
 *  * You should have received a copy of the GNU General Public License along with Foobar.  If not, see
 *  * <http://www.gnu.org/licenses/>.
 *
 */

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

package tk.eichler.carpentersblocks.tileentities.variants;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.EnumFacing;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.eventhandler.InteractionEvent;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CuboidTileEntity extends ShapeableBlockTileEntity {
    private final ShapeableData shapeableData = ShapeableData.createInstance();

    public CuboidTileEntity() {
        this.getDataInstance().addUpdateListener(this);
    }

    @Override
    public ShapeableData getDataInstance() {
        return shapeableData;
    }

    private void toggleShapeByFace(final EnumFacing facing) {
        if (getDataInstance().getShape() != EnumShape.FULL_BLOCK) {
            getDataInstance().setShapeData(EnumShape.FULL_BLOCK, null);
        } else {
            getDataInstance().setShapeData(EnumShape.SLAB,
                    EnumOrientation.getOrientationFromFacing(facing));
        }
        getDataInstance().checkForChanges();
    }

    private void toggleShapeAll() {
        if (getDataInstance().getShape() != EnumShape.FULL_BLOCK) {
            getDataInstance().setShapeData(EnumShape.FULL_BLOCK, null);
        } else {
            getDataInstance().setShapeData(EnumShape.SLAB,
                    EnumShape.SLAB.getNextOrientation(getDataInstance().getOrientation()));
        }
        getDataInstance().checkForChanges();
    }

    @Override
    public void handleInteractionEvent(final InteractionEvent event) {
        if (event.isRemote()) {
            return;
        }

        switch (event.getInteraction()) {
            case COVER_REMOVE:
                event.setSuccessful(
                        this.removeBlockStack()
                );
                break;
            case COVER_ADD:
                event.setSuccessful(
                        this.setBlockStack(event.getItemStack(), event.getFacing(EnumFacing.UP))
                );
                break;
            case SHAPE_TOGGLE_RIGHT_CLICK:
                toggleShapeByFace(event.getFacing(EnumFacing.UP));
                event.setSuccessful(true);
                break;
            case SHAPE_TOGGLE_LEFT_CLICK:
                toggleShapeAll();
                event.setSuccessful(true);
                break;
            case NONE:
                break;
        }
    }

}
