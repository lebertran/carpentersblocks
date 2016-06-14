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

import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Vector4f;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VertexBuilder {

    private float x;
    private float y;
    private float z;

    private EnumTexCorner corner;

    private final EnumFacing enumFacing;

    public VertexBuilder(final EnumFacing facing) {
        this.enumFacing = facing;
    }

    public VertexBuilder() {
        this.enumFacing = EnumFacing.UP;
    }

    public EnumTexCorner getCorner() {
        return this.corner;
    }

    public Vector4f getVector4f() {
        return new Vector4f(x, y, z, 1);
    }

    public VertexBuilder withTransformation(final Transformation transformation) {
        Vector4f newCoords = this.getVector4f();
        transformation.get().getMatrix().transform(newCoords);

        return this.clone().withCoords(newCoords).withTextureMapping(this.corner);
    }

    public VertexBuilder withCoords(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public VertexBuilder withCoords(final Vector4f vector4f) {
        this.x = vector4f.getX();
        this.y = vector4f.getY();
        this.z = vector4f.getZ();

        return this;
    }

    public VertexBuilder withCoords(final EnumCoords enumCoords) {
        this.x = enumCoords.x;
        this.y = enumCoords.y;
        this.z = enumCoords.z;

        return this;
    }

    public VertexBuilder withTextureMapping(final EnumTexCorner texCorner) {
        this.corner = texCorner;

        return this;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public VertexBuilder clone() {
        return new VertexBuilder(this.enumFacing).withCoords(this.x, this.y, this.z).withTextureMapping(corner);
    }
}
