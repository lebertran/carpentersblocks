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

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;

public class ModelHelper {

    private ModelHelper() {
        // do not instantiate this helper class
    }

    @Nonnull
    public static Vec3d rotate(@Nonnull Vec3d vec, @Nonnull EnumFacing side) {
        switch (side) {
            case UP:    return new Vec3d( vec.xCoord,  vec.yCoord,  vec.zCoord);
            case DOWN:  return new Vec3d( vec.xCoord, -vec.yCoord, -vec.zCoord);
            case NORTH: return new Vec3d( vec.xCoord,  vec.zCoord, -vec.yCoord);
            case SOUTH: return new Vec3d( vec.xCoord, -vec.zCoord,  vec.yCoord);
            case WEST:  return new Vec3d(-vec.yCoord,  vec.xCoord,  vec.zCoord);
            case EAST:  return new Vec3d( vec.yCoord, -vec.xCoord,  vec.zCoord);
        }

        return vec;
    }

    public static int getFaceShadeColor(EnumFacing facing) {
        float f = getFaceBrightness(facing);

        int i = MathHelper.clamp_int((int) (f * 255.0F), 0, 255);
        return -16777216 | i << 16 | i << 8 | i;
    }

    public static float getFaceBrightness(EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return 0.5F;
            case UP:
                return 1.0F;
            case NORTH:
            case SOUTH:
                return 0.8F;
            case WEST:
            case EAST:
                return 0.6F;
            default:
                return 1.0F;
        }
    }
}
