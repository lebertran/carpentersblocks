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
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.model.helper.*;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Map;

import static tk.eichler.carpentersblocks.model.helper.EnumCoordinates.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexel.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
final class CarpentersSlopeModelData {
    private CarpentersSlopeModelData() {
        // do not instantiate
    }

    private static final Polygon POLYGON_SLOPE_FRONT_NORTH = new Polygon()
            .putVertex(0, TOP_BOTTOMRIGHT, UPPER_LEFT)
            .putVertex(1, BOTTOM_BOTTOMRIGHT, BOTTOM_LEFT)
            .putVertex(2, BOTTOM_BOTTOMLEFT, BOTTOM_RIGHT)
            .putVertex(3, TOP_BOTTOMLEFT, UPPER_RIGHT);

    private static final Polygon POLYGON_SLOPE_SIDE_WEST = new Polygon()
            .putVertex(0, WEST_BOTTOMLEFT, BOTTOM_LEFT)
            .putVertex(1, WEST_BOTTOMLEFT, BOTTOM_LEFT)
            .putVertex(2, WEST_BOTTOMRIGHT, BOTTOM_RIGHT)
            .putVertex(3, WEST_UPPERRIGHT, UPPER_RIGHT);

    private static final Polygon POLYGON_SLOPE_SIDE_MIRRORED_EAST = new Polygon()
            .putVertex(0, EAST_UPPERLEFT, UPPER_LEFT)
            .putVertex(1, EAST_BOTTOMLEFT, BOTTOM_LEFT)
            .putVertex(2, EAST_BOTTOMRIGHT, BOTTOM_RIGHT)
            .putVertex(3, EAST_BOTTOMRIGHT, BOTTOM_RIGHT);

    static Polygon getDefaultSlopePolygon(final EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return BaseModelData.getFullPolygon(EnumFacing.DOWN);
            case WEST:
                return POLYGON_SLOPE_SIDE_WEST;
            case EAST:
                return POLYGON_SLOPE_SIDE_MIRRORED_EAST;
            case NORTH:
                return POLYGON_SLOPE_FRONT_NORTH;
            case SOUTH:
                return BaseModelData.getFullPolygon(EnumFacing.SOUTH);
            case UP:
            default:
                return Polygon.POLYGON_EMPTY;
        }
    }

    static Transformation[] getTransformations(final EnumShape shape) {
        switch (shape) {
            case NORTH_SLOPE:
                return TransformationHelper.NO_TRANSFORMS;
            case SOUTH_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_180);
            case WEST_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_270);
            case EAST_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_90);
            case NORTH_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_UP);
            case SOUTH_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_180, TransformationHelper.ROTATE_UP);
            case WEST_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_270, TransformationHelper.ROTATE_UP);
            case EAST_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_90, TransformationHelper.ROTATE_UP);
            default:
                return TransformationHelper.NO_TRANSFORMS;
        }
    }

    static BakedQuad getBakedQuad(final EnumFacing facing, final EnumShape shape, final Map<EnumFacing, TextureAtlasSprite> sprite) {
        final Transformation[] transformations = getTransformations(shape);

        return new BakedQuadBuilder(getDefaultSlopePolygon(facing), facing, sprite, transformations).build();
    }
}
