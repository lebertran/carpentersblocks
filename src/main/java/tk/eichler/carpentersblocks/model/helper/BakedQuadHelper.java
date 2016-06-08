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

package tk.eichler.carpentersblocks.model.helper;

import com.google.common.primitives.Ints;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import javax.annotation.ParametersAreNonnullByDefault;

import static tk.eichler.carpentersblocks.model.CarpentersBlockModelData.*;
import static tk.eichler.carpentersblocks.model.helper.ModelHelper.rotate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BakedQuadHelper {


    /**
     * FULL BLOCK BAKED QUADS
     */
    public static BakedQuad createBakedQuadFull(TextureAtlasSprite texture, EnumFacing facing) {
        final Vec3d v1 = rotate(new Vec3d(-0.5, 0.5, -0.5), facing).addVector(.5, .5, .5);
        final Vec3d v2 = rotate(new Vec3d(-0.5, 0.5, 0.5), facing).addVector(.5, .5, .5);
        final Vec3d v3 = rotate(new Vec3d(0.5, 0.5, 0.5), facing).addVector(.5, .5, .5);
        final Vec3d v4 = rotate(new Vec3d(0.5, 0.5, -0.5), facing).addVector(.5, .5, .5);

        return new BakedQuad(Ints.concat(
                new VertexBuilder(facing).withCoords(v1).withTextureMapping(EnumTexCorner.UPPER_LEFT).toIntArray(texture),
                new VertexBuilder(facing).withCoords(v2).withTextureMapping(EnumTexCorner.BOTTOM_LEFT).toIntArray(texture),
                new VertexBuilder(facing).withCoords(v3).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT).toIntArray(texture),
                new VertexBuilder(facing).withCoords(v4).withTextureMapping(EnumTexCorner.UPPER_RIGHT).toIntArray(texture)
        ), -1, facing, texture, false, DefaultVertexFormats.ITEM);
    }

    public static BakedQuad createBakedQuadFull(BakedQuad bakedQuad) {
        return bakedQuad;
    }


    /**
     * BOTTOM SLAB BLOCK BAKED QUADS
     */
    public static BakedQuad createBakedQuadBottomSlab(BakedQuad facing) {
        final EnumFacing side = facing.getFace();
        final TextureAtlasSprite sprite = facing.getSprite();

        final int[] vertices = getBottomSlabVertices(side, sprite);

        return new BakedQuad(vertices, facing.getTintIndex(), side, sprite, false, DefaultVertexFormats.ITEM);
    }

    public static BakedQuad createBakedQuadBottomSlab(TextureAtlasSprite sprite, EnumFacing facing) {
        final int[] vertices = getBottomSlabVertices(facing, sprite);

        return new BakedQuad(vertices, -1, facing, sprite, false, DefaultVertexFormats.ITEM);
    }

    public static BakedQuad createBakedQuadTopSlab(BakedQuad facing) {
        final EnumFacing side = facing.getFace();
        final TextureAtlasSprite sprite = facing.getSprite();

        final int[] vertices = getTopSlabVertices(side, sprite);

        return new BakedQuad(vertices, -1, side, sprite, false, DefaultVertexFormats.ITEM);
    }

    public static BakedQuad createBakedQuadTopSlab(TextureAtlasSprite sprite, EnumFacing facing) {
        final int[] vertices = getTopSlabVertices(facing, sprite);

        return new BakedQuad(vertices, -1, facing, sprite, false, DefaultVertexFormats.ITEM);
    }

    private static int[] getBottomSlabVertices(EnumFacing side, TextureAtlasSprite sprite) {
        switch (side) {
            case DOWN:
                return VertexHelper.verticesToInts(sprite, VERTICES_SLAB_DOWN);
            case WEST:
                return VertexHelper.verticesToInts(sprite, VERTICES_SLAB_WEST);
            case EAST:
                return VertexHelper.verticesToInts(sprite, VERTICES_SLAB_EAST);
            case NORTH:
                return VertexHelper.verticesToInts(sprite, VERTICES_SLAB_NORTH);
            case SOUTH:
                return VertexHelper.verticesToInts(sprite, VERTICES_SLAB_SOUTH);
            case UP:
            default:
                return VertexHelper.verticesToInts(sprite, VERTICES_SLAB_UP);
        }
    }

    private static int[] getTopSlabVertices(EnumFacing side, TextureAtlasSprite sprite) {
        switch (side) {
            case DOWN:
                return VertexHelper.verticesToInts(sprite, VERTICES_TOP_SLAB_DOWN);
            case WEST:
                return VertexHelper.verticesToInts(sprite, VertexBuilder.transformY(VERTICES_SLAB_WEST, 0.5F));
            case EAST:
                return VertexHelper.verticesToInts(sprite, VertexBuilder.transformY(VERTICES_SLAB_EAST, 0.5F));
            case NORTH:
                return VertexHelper.verticesToInts(sprite, VertexBuilder.transformY(VERTICES_SLAB_NORTH, 0.5F));
            case SOUTH:
                return VertexHelper.verticesToInts(sprite, VertexBuilder.transformY(VERTICES_SLAB_SOUTH, 0.5F));
            case UP:
            default:
                return VertexHelper.verticesToInts(sprite, VERTICES_TOP);
        }
    }
}
