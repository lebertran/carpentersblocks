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

import com.google.common.primitives.Ints;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.model.helper.EnumTexCorner;
import tk.eichler.carpentersblocks.model.helper.VertexBuilder;

public class CarpentersBlockModelData {

    public static final VertexBuilder[] VERTICES_SLAB_UP = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.UP).withCoords(0, 0.5F, 0).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.UP).withCoords(0, 0.5F, 1).withTextureMapping(EnumTexCorner.UPPER_LEFT),
            new VertexBuilder(EnumFacing.UP).withCoords(1, 0.5F, 1).withTextureMapping(EnumTexCorner.UPPER_RIGHT),
            new VertexBuilder(EnumFacing.UP).withCoords(1, 0.5F, 0).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
    };

    public static final VertexBuilder[] VERTICES_SLAB_DOWN = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.DOWN).withCoords(0, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.DOWN).withCoords(0, 0, 0).withTextureMapping(EnumTexCorner.UPPER_LEFT),
            new VertexBuilder(EnumFacing.DOWN).withCoords(1, 0, 0).withTextureMapping(EnumTexCorner.UPPER_RIGHT),
            new VertexBuilder(EnumFacing.DOWN).withCoords(1, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
    };
    
    public static final VertexBuilder[] VERTICES_SLAB_WEST = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.WEST).withCoords(1, 0.5F, 0).withTextureMapping(EnumTexCorner.MIDDLE_RIGHT),
            new VertexBuilder(EnumFacing.WEST).withCoords(1, 0, 0).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
            new VertexBuilder(EnumFacing.WEST).withCoords(0, 0, 0).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.WEST).withCoords(0, 0.5F, 0).withTextureMapping(EnumTexCorner.MIDDLE_LEFT)
    };

    public static final VertexBuilder[] VERTICES_SLAB_EAST = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.EAST).withCoords(0, 0.5F, 1).withTextureMapping(EnumTexCorner.MIDDLE_RIGHT),
            new VertexBuilder(EnumFacing.EAST).withCoords(0, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
            new VertexBuilder(EnumFacing.EAST).withCoords(1, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.EAST).withCoords(1, 0.5F, 1).withTextureMapping(EnumTexCorner.MIDDLE_LEFT)
    };

    public static final VertexBuilder[] VERTICES_SLAB_NORTH = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.NORTH).withCoords(0, 0.5F, 0).withTextureMapping(EnumTexCorner.MIDDLE_RIGHT),
            new VertexBuilder(EnumFacing.NORTH).withCoords(0, 0, 0).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
            new VertexBuilder(EnumFacing.NORTH).withCoords(0, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.NORTH).withCoords(0, 0.5F, 1).withTextureMapping(EnumTexCorner.MIDDLE_LEFT)
    };

    public static final VertexBuilder[] VERTICES_SLAB_SOUTH = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.SOUTH).withCoords(1, 0.5F, 1).withTextureMapping(EnumTexCorner.MIDDLE_RIGHT),
            new VertexBuilder(EnumFacing.SOUTH).withCoords(1, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
            new VertexBuilder(EnumFacing.SOUTH).withCoords(1, 0, 0).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.SOUTH).withCoords(1, 0.5F, 0).withTextureMapping(EnumTexCorner.MIDDLE_LEFT)
    };

    public static int[] verticesToIntArray(TextureAtlasSprite sprite, VertexBuilder[] vertices) {
        if (vertices.length != 4) {
            FMLLog.severe("Invalid amount of vertices.");
            return new int[] {};
        }

        return Ints.concat(
                vertices[0].toIntArray(sprite),
                vertices[1].toIntArray(sprite),
                vertices[2].toIntArray(sprite),
                vertices[3].toIntArray(sprite)
        );
    }
}
