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
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.model.helper.BakedQuadBuilder;
import tk.eichler.carpentersblocks.model.helper.Polygon;
import tk.eichler.carpentersblocks.model.helper.Transformation;
import tk.eichler.carpentersblocks.model.helper.TransformationHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;
import java.util.Map;

import static tk.eichler.carpentersblocks.model.helper.EnumCoordinates.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexel.*;
import static tk.eichler.carpentersblocks.model.helper.TransformationHelper.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class CarpentersBlockModelData {

    private CarpentersBlockModelData() {
        // do not instantiate
    }

    private static final Polygon POLYGON_BOTTOM_SLAB_TOP = new Polygon()
            .putVertex(0, CENTERHORIZ_UPPERLEFT, UPPER_LEFT)
            .putVertex(1, CENTERHORIZ_BOTTOMLEFT, BOTTOM_LEFT)
            .putVertex(2, CENTERHORIZ_BOTTOMRIGHT, BOTTOM_RIGHT)
            .putVertex(3, CENTERHORIZ_UPPERRIGHT, UPPER_RIGHT);

    private static final Polygon POLYGON_BOTTOM_SLAB_SIDE_NORTH = new Polygon()
            .putVertex(0, NORTH_MIDDLELEFT, MIDDLE_LEFT)
            .putVertex(1, NORTH_BOTTOMLEFT, BOTTOM_LEFT)
            .putVertex(2, NORTH_BOTTOMRIGHT, BOTTOM_RIGHT)
            .putVertex(3, NORTH_MIDDLERIGHT, MIDDLE_RIGHT);

    private static final Polygon POLYGON_BOTTOM_SLAB_SIDE_SOUTH =
            POLYGON_BOTTOM_SLAB_SIDE_NORTH.createWithTransformation(TransformationHelper.ROTATE_SIDE_180);

    private static final Polygon POLYGON_BOTTOM_SLAB_SIDE_WEST =
            POLYGON_BOTTOM_SLAB_SIDE_NORTH.createWithTransformation(TransformationHelper.ROTATE_SIDE_270);

    private static final Polygon POLYGON_BOTTOM_SLAB_SIDE_EAST =
            POLYGON_BOTTOM_SLAB_SIDE_NORTH.createWithTransformation(TransformationHelper.ROTATE_SIDE_90);

    private static Polygon getDefaultSlabPolygon(final EnumFacing facing) {
        switch (facing) {
            case UP:
                return POLYGON_BOTTOM_SLAB_TOP;
            case DOWN:
                return BaseModelData.getFullPolygon(EnumFacing.DOWN);
            case NORTH:
                return POLYGON_BOTTOM_SLAB_SIDE_NORTH;
            case EAST:
                return POLYGON_BOTTOM_SLAB_SIDE_EAST;
            case SOUTH:
                return POLYGON_BOTTOM_SLAB_SIDE_SOUTH;
            case WEST:
                return POLYGON_BOTTOM_SLAB_SIDE_WEST;
            default:
                throw new UnsupportedOperationException("Invalid facing");
        }
    }

    private static Transformation[] getSlabTransformations(final EnumOrientation orientation) {
        switch (orientation) {
            case UP:
                return TransformationHelper.get(TRANSLATE_Y_HALF);
            case DOWN:
                return TransformationHelper.NO_TRANSFORMS;
            case NORTH:
                return TransformationHelper.get(ROTATE_UP_270);
            case EAST:
                return TransformationHelper.get(ROTATE_UP_270, ROTATE_SIDE_90);
            case SOUTH:
                return TransformationHelper.get(ROTATE_UP_270, ROTATE_SIDE_180);
            case WEST:
                return TransformationHelper.get(ROTATE_UP_270, ROTATE_SIDE_270);
            default:
                return TransformationHelper.NO_TRANSFORMS;
        }
    }

    static BakedQuad createQuad(final EnumShape shape, final EnumOrientation orientation,
                                final EnumFacing facing, final Map<EnumFacing, TextureAtlasSprite> textureMap) {
        final Polygon polygon;
        final Transformation[] transforms;

        switch (shape) {
            case FULL_BLOCK:
                polygon = BaseModelData.getFullPolygon(facing);
                transforms = NO_TRANSFORMS;
                break;
            case SLAB:
                polygon = getDefaultSlabPolygon(facing);
                transforms = getSlabTransformations(orientation);
                break;
            default:
                throw new IllegalArgumentException(MessageFormat.format("Invalid shape {0} in CarpentersBlockModel.", shape));
        }

        return new BakedQuadBuilder(polygon, facing, textureMap, transforms).build();
    }
}
