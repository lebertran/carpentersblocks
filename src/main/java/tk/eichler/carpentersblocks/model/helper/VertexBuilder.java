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

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public class VertexBuilder {
    private float x;
    private float y;
    private float z;

    private float u;
    private float v;

    private EnumFacing facing;

    public VertexBuilder(EnumFacing facing) {
        this.facing = facing;
    }

    public VertexBuilder withCoords(Vec3d vec3d) {
        this.x = (float) vec3d.xCoord;
        this.y = (float) vec3d.yCoord;
        this.z = (float) vec3d.zCoord;

        return this;
    }

    public VertexBuilder withCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public VertexBuilder withTextureMapping(float u, float v) {
        this.u = u;
        this.v = v;

        return this;
    }

    public VertexBuilder withTextureMapping(EnumTexCorner corner) {
        this.u = corner.x;
        this.v = corner.y;

        return this;
    }

    public int[] toIntArray(TextureAtlasSprite sprite) {
        return new int[]{
                Float.floatToRawIntBits(x),
                Float.floatToRawIntBits(y),
                Float.floatToRawIntBits(z),
                ModelHelper.getFaceShadeColor(facing),
                Float.floatToRawIntBits(sprite.getInterpolatedU(u)),
                Float.floatToRawIntBits(sprite.getInterpolatedV(v)),
                0
        };
    }
}
