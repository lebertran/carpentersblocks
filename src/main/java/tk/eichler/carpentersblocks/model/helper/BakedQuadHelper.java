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
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;

import static tk.eichler.carpentersblocks.model.CarpentersBlockModelData.*;
import static tk.eichler.carpentersblocks.model.CarpentersBlockModelData.VERTICES_SLAB_UP;
import static tk.eichler.carpentersblocks.model.CarpentersBlockModelData.verticesToIntArray;
import static tk.eichler.carpentersblocks.model.helper.ModelHelper.rotate;

public class BakedQuadHelper {


    /**
     * FULL BLOCK BAKED QUADS
     */
    @Nonnull
    public static BakedQuad createBakedQuadFull(@Nonnull TextureAtlasSprite texture, EnumFacing facing) {
        final Vec3d v1 = rotate(new Vec3d(-0.5, 0.5, -0.5), facing).addVector(.5, .5, .5);
        final Vec3d v2 = rotate(new Vec3d(-0.5, 0.5, 0.5), facing).addVector(.5, .5, .5);
        final Vec3d v3 = rotate(new Vec3d(0.5, 0.5, 0.5), facing).addVector(.5, .5, .5);
        final Vec3d v4 = rotate(new Vec3d(0.5, 0.5, -0.5), facing).addVector(.5, .5, .5);

        return new BakedQuad(Ints.concat(
                new VertexBuilder(facing).withCoords(v1).withTextureMapping(0, 0).toIntArray(texture),
                new VertexBuilder(facing).withCoords(v2).withTextureMapping(0, 16).toIntArray(texture),
                new VertexBuilder(facing).withCoords(v3).withTextureMapping(16, 16).toIntArray(texture),
                new VertexBuilder(facing).withCoords(v4).withTextureMapping(16, 0).toIntArray(texture)
        ), -1, facing, texture, false, DefaultVertexFormats.ITEM);
    }

    @Nonnull
    public static BakedQuad createBakedQuadFull(@Nonnull BakedQuad bakedQuad) {
        return bakedQuad;
    }


    /**
     * BOTTOM SLAB BLOCK BAKED QUADS
     */
    @Nonnull
    public static BakedQuad createBakedQuadBottomSlab(@Nonnull BakedQuad facing) {
        final EnumFacing side = facing.getFace();
        final TextureAtlasSprite sprite = facing.getSprite();

        final int[] vertices = getBottomSlabVertices(side, sprite);

        return new BakedQuad(vertices, -1, side, sprite, false, DefaultVertexFormats.ITEM);
    }

    @Nonnull
    public static BakedQuad createBakedQuadBottomSlab(@Nonnull TextureAtlasSprite sprite, @Nonnull EnumFacing facing) {
        final int[] vertices = getBottomSlabVertices(facing, sprite);

        return new BakedQuad(vertices, -1, facing, sprite, false, DefaultVertexFormats.ITEM);
    }

    @Nonnull
    private static int[] getBottomSlabVertices(@Nonnull EnumFacing side, @Nonnull TextureAtlasSprite sprite) {
        switch (side) {
            case DOWN:
                return verticesToIntArray(sprite, VERTICES_SLAB_DOWN);
            case WEST:
                return verticesToIntArray(sprite, VERTICES_SLAB_WEST);
            case EAST:
                return verticesToIntArray(sprite, VERTICES_SLAB_EAST);
            case NORTH:
                return verticesToIntArray(sprite, VERTICES_SLAB_NORTH);
            case SOUTH:
                return verticesToIntArray(sprite, VERTICES_SLAB_SOUTH);
            case UP:
            default:
                return verticesToIntArray(sprite, VERTICES_SLAB_UP);
        }
    }
}
