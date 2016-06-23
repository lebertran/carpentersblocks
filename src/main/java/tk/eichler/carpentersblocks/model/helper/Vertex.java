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
 *
 */

package tk.eichler.carpentersblocks.model.helper;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Vertex {
    private final float x;
    private final float y;
    private final float z;

    private final EnumTexel corner;

    public Vertex(final float x, final float y, final float z, final EnumTexel corner) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.corner = corner;
    }

    public EnumTexel getTexCorner() {
        return this.corner;
    }

    public Vec3d getVec3d() {
        return new Vec3d(this.x, this.y, this.z);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float get(final EnumFacing.Axis axis) {
        switch (axis) {
            case X:
                return this.x;
            case Y:
                return this.y;
            case Z:
                return this.z;
            default:
                throw new IllegalArgumentException("Illegal axis argument.");
        }
    }
}
