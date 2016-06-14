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
import tk.eichler.carpentersblocks.model.helper.VertexBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.util.EnumFacing.*;
import static tk.eichler.carpentersblocks.model.BaseModelData.*;
import static tk.eichler.carpentersblocks.model.helper.EnumCoords.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexCorner.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
final class CarpentersSlopeModelData {
    private CarpentersSlopeModelData() {
        // do not instantiate
    }

    private static final VertexBuilder[] VERTICES_SLOPE_FRONT_NORTH = new VertexBuilder[] {
            new VertexBuilder(NORTH).withCoords(TOP_BOTTOMRIGHT).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(NORTH).withCoords(BOTTOM_BOTTOMRIGHT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(NORTH).withCoords(BOTTOM_BOTTOMLEFT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(NORTH).withCoords(TOP_BOTTOMLEFT).withTextureMapping(UPPER_RIGHT),
    };

    private static final VertexBuilder[] VERTICES_SLOPE_SIDE_WEST = new VertexBuilder[] {
            new VertexBuilder(WEST).withCoords(WEST_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(WEST).withCoords(WEST_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(WEST).withCoords(WEST_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(WEST).withCoords(WEST_UPPERRIGHT).withTextureMapping(UPPER_RIGHT),
    };

    private static final VertexBuilder[] VERTICES_SLOPE_SIDE_MIRRORED_EAST = new VertexBuilder[] {
            new VertexBuilder(EAST).withCoords(EAST_UPPERLEFT).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(EAST).withCoords(EAST_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(EAST).withCoords(EAST_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(EAST).withCoords(EAST_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
    };

    static VertexBuilder[] getVerticesDefaultSlope(final EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return VERTICES_FULL_DOWN;
            case WEST:
                return VERTICES_SLOPE_SIDE_WEST;
            case EAST:
                return VERTICES_SLOPE_SIDE_MIRRORED_EAST;
            case NORTH:
                return VERTICES_SLOPE_FRONT_NORTH;
            case SOUTH:
                return VERTICES_FULL_SOUTH;
            case UP:
            default:
                return VERTICES_EMPTY;
        }
    }
}
