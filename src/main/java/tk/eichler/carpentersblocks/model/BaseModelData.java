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

package tk.eichler.carpentersblocks.model;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.EnumFacing;
import tk.eichler.carpentersblocks.model.helper.*;

import javax.annotation.ParametersAreNonnullByDefault;

import static tk.eichler.carpentersblocks.model.helper.EnumCoordinates.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexel.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class BaseModelData {

    private BaseModelData() {
        // do not instantiate
    }

    private static final Polygon POLYGON_UP = new Polygon()
            .putVertex(0, TOP_UPPERLEFT, UPPER_LEFT)
            .putVertex(1, TOP_BOTTOMLEFT, BOTTOM_LEFT)
            .putVertex(2, TOP_BOTTOMRIGHT, BOTTOM_RIGHT)
            .putVertex(3, TOP_UPPERRIGHT, UPPER_RIGHT);

    private static final Polygon POLYGON_DOWN = POLYGON_UP.createWithTransformation(TransformationHelper.ROTATE_UP_180);

    private static final Polygon POLYGON_SIDE_SOUTH = POLYGON_UP.createWithTransformation(TransformationHelper.ROTATE_UP_270);

    private static final Polygon POLYGON_SIDE_EAST = POLYGON_SIDE_SOUTH.createWithTransformation(TransformationHelper.ROTATE_SIDE_270);
    private static final Polygon POLYGON_SIDE_WEST = POLYGON_SIDE_SOUTH.createWithTransformation(TransformationHelper.ROTATE_SIDE_90);
    private static final Polygon POLYGON_SIDE_NORTH = POLYGON_SIDE_SOUTH.createWithTransformation(TransformationHelper.ROTATE_SIDE_180);


    static Polygon getFullPolygon(final EnumFacing facing) {
        switch (facing) {
            case UP:
                return POLYGON_UP;
            case DOWN:
                return POLYGON_DOWN;
            case NORTH:
                return POLYGON_SIDE_NORTH;
            case SOUTH:
                return POLYGON_SIDE_SOUTH;
            case WEST:
                return POLYGON_SIDE_WEST;
            case EAST:
                return POLYGON_SIDE_EAST;
            default:
                throw new IllegalArgumentException("Invalid facing.");
        }
    }
}
