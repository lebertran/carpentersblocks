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
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.model.helper.BakedQuadBuilder;
import tk.eichler.carpentersblocks.model.helper.Transformation;
import tk.eichler.carpentersblocks.model.helper.TransformationHelper;
import tk.eichler.carpentersblocks.model.helper.VertexBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;

import static net.minecraft.util.EnumFacing.NORTH;
import static net.minecraft.util.EnumFacing.UP;
import static tk.eichler.carpentersblocks.model.helper.EnumCoords.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexCorner.*;
import static tk.eichler.carpentersblocks.model.helper.TransformationHelper.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class CarpentersBlockModelData {

    private CarpentersBlockModelData() {
        // do not instantiate
    }

    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_SIDE = new VertexBuilder[] {
            new VertexBuilder(NORTH).withCoords(NORTH_MIDDLELEFT).withTextureMapping(MIDDLE_LEFT),
            new VertexBuilder(NORTH).withCoords(NORTH_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(NORTH).withCoords(NORTH_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(NORTH).withCoords(NORTH_MIDDLERIGHT).withTextureMapping(MIDDLE_RIGHT)
    };

    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_TOP = new VertexBuilder[] {
            new VertexBuilder(UP).withCoords(0, 0.5F, 0).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(UP).withCoords(0, 0.5F, 1).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(UP).withCoords(1, 0.5F, 1).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(UP).withCoords(1, 0.5F, 0).withTextureMapping(UPPER_RIGHT),
    };


    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_NORTH =
            VERTICES_BOTTOM_SLAB_SIDE;
    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_WEST =
            TransformationHelper.transformVertices(VERTICES_BOTTOM_SLAB_SIDE, TransformationHelper.ROTATE_SIDE_270);
    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_SOUTH =
            TransformationHelper.transformVertices(VERTICES_BOTTOM_SLAB_SIDE, TransformationHelper.ROTATE_SIDE_180);
    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_EAST =
            TransformationHelper.transformVertices(VERTICES_BOTTOM_SLAB_SIDE, TransformationHelper.ROTATE_SIDE_90);
    private static final VertexBuilder[] VERTICES_BOTTOM_SLAB_UP = VERTICES_BOTTOM_SLAB_TOP;

    private static VertexBuilder[] getDefaultSlabVertices(final EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return BaseModelData.VERTICES_FULL_DOWN;
            case UP:
                return VERTICES_BOTTOM_SLAB_UP;
            case NORTH:
                return VERTICES_BOTTOM_SLAB_NORTH;
            case SOUTH:
                return VERTICES_BOTTOM_SLAB_SOUTH;
            case WEST:
                return VERTICES_BOTTOM_SLAB_WEST;
            case EAST:
                return VERTICES_BOTTOM_SLAB_EAST;
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
            case SOUTH:
                return TransformationHelper.get(ROTATE_UP);
            case WEST:
                return TransformationHelper.get(ROTATE_UP, ROTATE_SIDE_90);
            case NORTH:
                return TransformationHelper.get(ROTATE_UP, ROTATE_SIDE_180);
            case EAST:
                return TransformationHelper.get(ROTATE_UP, ROTATE_SIDE_270);
            default:
                return TransformationHelper.NO_TRANSFORMS;
        }
    }

    static BakedQuad getBakedQuad(final EnumShape shape, final EnumOrientation orientation,
                                  final EnumFacing facing, final TextureAtlasSprite sprite) {
        final VertexBuilder[] vertices;
        final Transformation[] transforms;

        switch (shape) {
            case FULL_BLOCK:
                vertices = BaseModelData.getFullVertices(facing);
                transforms = NO_TRANSFORMS;
                break;
            case SLAB:
                vertices = getDefaultSlabVertices(facing);
                transforms = getSlabTransformations(orientation);
                break;
            default:
                throw new IllegalArgumentException(MessageFormat.format("Invalid shape {0} in CarpentersBlockModel.", shape));
        }

        return new BakedQuadBuilder(vertices, facing, sprite, transforms).build();
    }
}
