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
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.model.helper.VertexBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.util.EnumFacing.*;
import static tk.eichler.carpentersblocks.model.helper.EnumCoords.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexCorner.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CarpentersBlockModelData {

    public static final VertexBuilder[] VERTICES_TOP = new VertexBuilder[] {
            new VertexBuilder(UP).withCoords(TOP_UPPERLEFT).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(UP).withCoords(TOP_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(UP).withCoords(TOP_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(UP).withCoords(TOP_UPPERRIGHT).withTextureMapping(UPPER_RIGHT),
    };

    public static final VertexBuilder[] VERTICES_DOWN = new VertexBuilder[] {
            new VertexBuilder(DOWN).withCoords(BOTTOM_UPPERLEFT).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(DOWN).withCoords(BOTTOM_BOTTOMLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(DOWN).withCoords(BOTTOM_BOTTOMRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(DOWN).withCoords(BOTTOM_UPPERRIGHT).withTextureMapping(UPPER_RIGHT),
    };

    public static final VertexBuilder[] VERTICES_TOP_SLAB_DOWN = new VertexBuilder[] {
            new VertexBuilder(DOWN).withCoords(CENTERHORIZ_UPPERLEFT).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(DOWN).withCoords(CENTERHORIZ_UPPERRIGHT).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(DOWN).withCoords(CENTERHORIZ_BOTTOMRIGHT).withTextureMapping(UPPER_RIGHT),
            new VertexBuilder(DOWN).withCoords(CENTERHORIZ_BOTTOMLEFT).withTextureMapping(UPPER_LEFT),
    };


    public static final VertexBuilder[] VERTICES_SLAB_UP = new VertexBuilder[] {
            new VertexBuilder(UP).withCoords(0, 0.5F, 0).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(UP).withCoords(0, 0.5F, 1).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(UP).withCoords(1, 0.5F, 1).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(UP).withCoords(1, 0.5F, 0).withTextureMapping(UPPER_RIGHT),
    };

    public static final VertexBuilder[] VERTICES_SLAB_DOWN = new VertexBuilder[] {
            new VertexBuilder(DOWN).withCoords(0, 0, 1).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(DOWN).withCoords(0, 0, 0).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(DOWN).withCoords(1, 0, 0).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(DOWN).withCoords(1, 0, 1).withTextureMapping(UPPER_RIGHT),
    };

    public static final VertexBuilder[] VERTICES_SLAB_NORTH = new VertexBuilder[] {
            new VertexBuilder(NORTH).withCoords(1, 0.5F, 0).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(NORTH).withCoords(1, 0, 0).withTextureMapping(MIDDLE_LEFT),
            new VertexBuilder(NORTH).withCoords(0, 0, 0).withTextureMapping(MIDDLE_RIGHT),
            new VertexBuilder(NORTH).withCoords(0, 0.5F, 0).withTextureMapping(UPPER_RIGHT)
    };

    public static final VertexBuilder[] VERTICES_SLAB_SOUTH = new VertexBuilder[] {
            new VertexBuilder(SOUTH).withCoords(0, 0.5F, 1).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(SOUTH).withCoords(0, 0, 1).withTextureMapping(MIDDLE_LEFT),
            new VertexBuilder(SOUTH).withCoords(1, 0, 1).withTextureMapping(MIDDLE_RIGHT),
            new VertexBuilder(SOUTH).withCoords(1, 0.5F, 1).withTextureMapping(UPPER_RIGHT)
    };

    public static final VertexBuilder[] VERTICES_SLAB_EAST = new VertexBuilder[] {
            new VertexBuilder(EAST).withCoords(0, 0.5F, 0).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(EAST).withCoords(0, 0, 0).withTextureMapping(MIDDLE_LEFT),
            new VertexBuilder(EAST).withCoords(0, 0, 1).withTextureMapping(MIDDLE_RIGHT),
            new VertexBuilder(EAST).withCoords(0, 0.5F, 1).withTextureMapping(UPPER_RIGHT)
    };

    public static final VertexBuilder[] VERTICES_SLAB_WEST = new VertexBuilder[] {
            new VertexBuilder(WEST).withCoords(1, 0.5F, 1).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(WEST).withCoords(1, 0, 1).withTextureMapping(MIDDLE_LEFT),
            new VertexBuilder(WEST).withCoords(1, 0, 0).withTextureMapping(MIDDLE_RIGHT),
            new VertexBuilder(WEST).withCoords(1, 0.5F, 0).withTextureMapping(UPPER_RIGHT)
    };
    
    
    

    public static final VertexBuilder[] VERTICES_FULL_NORTH = new VertexBuilder[] {
            new VertexBuilder(NORTH).withCoords(1, 1, 0).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(NORTH).withCoords(1, 0, 0).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(NORTH).withCoords(0, 0, 0).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(NORTH).withCoords(0, 1, 0).withTextureMapping(UPPER_RIGHT)
    };

    public static final VertexBuilder[] VERTICES_FULL_SOUTH = new VertexBuilder[] {
            new VertexBuilder(SOUTH).withCoords(0, 1, 1).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(SOUTH).withCoords(0, 0, 1).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(SOUTH).withCoords(1, 0, 1).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(SOUTH).withCoords(1, 1, 1).withTextureMapping(UPPER_RIGHT)
    };

    public static final VertexBuilder[] VERTICES_FULL_EAST = new VertexBuilder[] {
            new VertexBuilder(EAST).withCoords(0, 1, 0).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(EAST).withCoords(0, 0, 0).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(EAST).withCoords(0, 0, 1).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(EAST).withCoords(0, 1, 1).withTextureMapping(UPPER_RIGHT)
    };

    public static final VertexBuilder[] VERTICES_FULL_WEST = new VertexBuilder[] {
            new VertexBuilder(WEST).withCoords(1, 1, 1).withTextureMapping(UPPER_LEFT),
            new VertexBuilder(WEST).withCoords(1, 0, 1).withTextureMapping(BOTTOM_LEFT),
            new VertexBuilder(WEST).withCoords(1, 0, 0).withTextureMapping(BOTTOM_RIGHT),
            new VertexBuilder(WEST).withCoords(1, 1, 0).withTextureMapping(UPPER_RIGHT)
    };



    //Bitte nicht löschen!
    /*public static final VertexBuilder[] VERTICES_SLAB_UP = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.UP).withCoords(0, 0.5F, 0).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
            new VertexBuilder(EnumFacing.UP).withCoords(0, 0.5F, 1).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
            new VertexBuilder(EnumFacing.UP).withCoords(1, 0.5F, 1).withTextureMapping(EnumTexCorner.UPPER_LEFT),
            new VertexBuilder(EnumFacing.UP).withCoords(1, 0.5F, 0).withTextureMapping(EnumTexCorner.UPPER_RIGHT),
    };

    public static final VertexBuilder[] VERTICES_SLAB_DOWN = new VertexBuilder[] {
            new VertexBuilder(EnumFacing.DOWN).withCoords(0, 0, 1).withTextureMapping(EnumTexCorner.UPPER_LEFT),
            new VertexBuilder(EnumFacing.DOWN).withCoords(0, 0, 0).withTextureMapping(EnumTexCorner.UPPER_RIGHT),
            new VertexBuilder(EnumFacing.DOWN).withCoords(1, 0, 0).withTextureMapping(EnumTexCorner.BOTTOM_RIGHT),
            new VertexBuilder(EnumFacing.DOWN).withCoords(1, 0, 1).withTextureMapping(EnumTexCorner.BOTTOM_LEFT),
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
    };*/

}
