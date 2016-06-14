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
import tk.eichler.carpentersblocks.model.helper.TransformationHelper;
import tk.eichler.carpentersblocks.model.helper.VertexBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.util.EnumFacing.UP;
import static tk.eichler.carpentersblocks.model.helper.EnumCoords.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexCorner.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
final class BaseModelData {

    private BaseModelData() {
        // do not instantiate
    }

    private static final VertexBuilder[] VERTICES_FULL_UP = new VertexBuilder[] {
            new VertexBuilder(UP).withCoords(TOP_UPPERLEFT).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(UP).withCoords(TOP_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(UP).withCoords(TOP_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(UP).withCoords(TOP_UPPERRIGHT).withTextureMapping(UPPER_RIGHT),
    };

    static final VertexBuilder[] VERTICES_FULL_DOWN = TransformationHelper.transformVertices(VERTICES_FULL_UP, TransformationHelper.ROTATE_UP_180);
    static final VertexBuilder[] VERTICES_FULL_SOUTH = TransformationHelper.transformVertices(VERTICES_FULL_UP, TransformationHelper.ROTATE_UP_270);

    static final VertexBuilder[] VERTICES_FULL_EAST = TransformationHelper.transformVertices(VERTICES_FULL_SOUTH, TransformationHelper.ROTATE_SIDE_270);
    static final VertexBuilder[] VERTICES_FULL_WEST = TransformationHelper.transformVertices(VERTICES_FULL_SOUTH, TransformationHelper.ROTATE_SIDE_180);
    static final VertexBuilder[] VERTICES_FULL_NORTH = TransformationHelper.transformVertices(VERTICES_FULL_SOUTH, TransformationHelper.ROTATE_SIDE_90);

    static final VertexBuilder[] VERTICES_EMPTY = new VertexBuilder[] {
        new VertexBuilder().withTextureMapping(UPPER_LEFT),
        new VertexBuilder().withTextureMapping(UPPER_LEFT),
        new VertexBuilder().withTextureMapping(UPPER_LEFT),
        new VertexBuilder().withTextureMapping(UPPER_LEFT)
    };

    static VertexBuilder[] getFullVertices(final EnumFacing facing) {
        switch (facing) {
            case UP:
                return VERTICES_FULL_UP;
            case DOWN:
                return VERTICES_FULL_DOWN;
            case NORTH:
                return VERTICES_FULL_NORTH;
            case SOUTH:
                return VERTICES_FULL_SOUTH;
            case WEST:
                return VERTICES_FULL_WEST;
            case EAST:
                return VERTICES_FULL_EAST;
            default:
                throw new UnsupportedOperationException("Invalid facing.");
        }
    }
}
