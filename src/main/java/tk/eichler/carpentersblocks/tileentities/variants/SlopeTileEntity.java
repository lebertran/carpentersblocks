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

package tk.eichler.carpentersblocks.tileentities.variants;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.EnumFacing;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.eventhandler.InteractionEvent;
import tk.eichler.carpentersblocks.tileentities.ShapeableBlockTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SlopeTileEntity extends ShapeableBlockTileEntity {
    private final ShapeableData shapeableData = ShapeableData.createInstance();

    public SlopeTileEntity() {
        this.getDataInstance().addUpdateListener(this);
    }


    @Override
    public ShapeableData getDataInstance() {
        return shapeableData;
    }

    private void toggleShapeByFace(final EnumFacing facing) {
    }

    private void toggleShapeAll() {
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
                event.setSuccessful(false);
                break;
        }
    }
}
