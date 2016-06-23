/**
 * This file is part of Carpenter's Blocks.
 * <p>
 * Carpenter's Blocks is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * <p>
 * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with Foobar.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package eichler.tests;

import net.minecraft.util.math.Vec3d;

public class Plane {

    public final Vec3d normalVec;
    public final float d;

    public Plane(final Vec3d a, final Vec3d b, final Vec3d c) {
        this.normalVec = (a.subtract(b)).crossProduct(a.subtract(c));
        this.d = (float) ((normalVec.xCoord * a.xCoord) + (normalVec.yCoord * a.yCoord) + (normalVec.zCoord * a.zCoord));
    }

    public boolean isInPlane(final Vec3d vec) {
        final float difference =
                (float) ((normalVec.xCoord * vec.xCoord) + (normalVec.yCoord * vec.yCoord) + (normalVec.zCoord * vec.zCoord) - d);

        return Math.abs(difference) <= 1E-7;
    }
}
