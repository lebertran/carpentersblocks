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

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.util.EnumFacing.*;
import static tk.eichler.carpentersblocks.model.helper.EnumTexCorner.*;

/**
 * Nomenclature: LAYER_CORNER
 *
 * LAYER: TOP (EFGH), CENTERVERT (FG, EH, AD, BC), CENTERHORIZ (BF, CG, DH, AE), BOTTOM (ABCD)
 * CORNER: Corner when looking directly on the visible side of the layer. If layer is a center layer,
 * it is the corner when looking from above or from west.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum EnumCoords {

    BOTTOM_UPPERLEFT(0, 0, 1, DOWN, UPPER_LEFT), // B
    BOTTOM_UPPERRIGHT(1, 0, 1, DOWN, UPPER_RIGHT), // C
    BOTTOM_BOTTOMLEFT(0, 0, 0, DOWN, BOTTOM_LEFT), // A
    BOTTOM_BOTTOMRIGHT(1, 0, 0, DOWN, BOTTOM_RIGHT), // D

    CENTERHORIZ_UPPERLEFT(0, 0.5F, 0, UP, UPPER_LEFT), // AE
    CENTERHORIZ_BOTTOMLEFT(0, 0.5F, 1, UP, BOTTOM_LEFT), // BF
    CENTERHORIZ_BOTTOMRIGHT(1, 0.5F, 1, UP, BOTTOM_RIGHT), // CG
    CENTERHORIZ_UPPERRIGHT(1, 0.5F, 0, UP, UPPER_RIGHT), // DH

    CENTERVERT_UPPERLEFT(0.5F, 1, 0, WEST, UPPER_LEFT), // EH
    CENTERVERT_BOTTOMLEFT(0.5F, 0, 0, WEST, BOTTOM_LEFT), // AD
    CENTERVERT_BOTTOMRIGHT(0.5F, 0, 1, WEST, BOTTOM_RIGHT), // BC
    CENTERVERT_UPPERRIGHT(0.5F, 1, 1, WEST, UPPER_RIGHT), // FG

    CENTER_WEST(0, 0.5F, 0.5F, WEST, CENTER),
    CENTER_EAST(1, 0.5F, 0.5F, EAST, CENTER),

    TOP_UPPERLEFT(0, 1, 0, UP, UPPER_LEFT), //E
    TOP_BOTTOMLEFT(0, 1, 1, UP, BOTTOM_LEFT), // F
    TOP_UPPERRIGHT(1, 1, 0, UP, UPPER_RIGHT), // H
    TOP_BOTTOMRIGHT(1, 1, 1, UP, BOTTOM_RIGHT), // G

    // Synonyms
    NORTH_UPPERLEFT(TOP_UPPERRIGHT, NORTH, UPPER_LEFT),
    NORTH_UPPERRIGHT(TOP_UPPERLEFT, NORTH, UPPER_RIGHT),
    NORTH_BOTTOMLEFT(BOTTOM_BOTTOMRIGHT, NORTH, BOTTOM_LEFT),
    NORTH_BOTTOMRIGHT(BOTTOM_BOTTOMLEFT, NORTH, BOTTOM_RIGHT),
    
    SOUTH_UPPERLEFT(TOP_BOTTOMLEFT, SOUTH, UPPER_LEFT),
    SOUTH_UPPERRIGHT(TOP_BOTTOMRIGHT, SOUTH, UPPER_RIGHT),
    SOUTH_BOTTOMLEFT(BOTTOM_UPPERLEFT, SOUTH, BOTTOM_LEFT),
    SOUTH_BOTTOMRIGHT(BOTTOM_UPPERRIGHT, SOUTH, BOTTOM_RIGHT),

    EAST_UPPERLEFT(TOP_BOTTOMRIGHT, EAST, UPPER_LEFT),
    EAST_UPPERRIGHT(TOP_UPPERRIGHT, EAST, UPPER_RIGHT),
    EAST_BOTTOMLEFT(BOTTOM_UPPERRIGHT, EAST, BOTTOM_LEFT),
    EAST_BOTTOMRIGHT(BOTTOM_BOTTOMRIGHT, EAST, BOTTOM_RIGHT),

    WEST_UPPERLEFT(TOP_UPPERLEFT, WEST, UPPER_LEFT),
    WEST_UPPERRIGHT(TOP_BOTTOMLEFT, WEST, UPPER_RIGHT),
    WEST_BOTTOMLEFT(BOTTOM_BOTTOMLEFT, WEST, BOTTOM_LEFT),
    WEST_BOTTOMRIGHT(BOTTOM_UPPERLEFT, WEST, BOTTOM_RIGHT);

    public final float x;
    public final float y;
    public final float z;

    public final EnumFacing facing;
    public final EnumTexCorner defaultCorner;

    EnumCoords(float x, float y, float z, EnumFacing facing, EnumTexCorner defaultCorner) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.facing = facing;
        this.defaultCorner = defaultCorner;
    }

    EnumCoords(EnumCoords enumCoords, EnumFacing facing, EnumTexCorner defaultCorner) {
        this.x = enumCoords.x;
        this.y = enumCoords.y;
        this.z = enumCoords.z;

        this.facing = facing;
        this.defaultCorner = defaultCorner;
    }

    @Nullable
    public static EnumCoords getCoords(EnumFacing facing, EnumTexCorner defaultCorner) {
        for (EnumCoords coords : EnumCoords.values()) {
            if (coords.facing == facing && coords.defaultCorner == defaultCorner) {
                return coords;
            }
        }

        return null;
    }
}
