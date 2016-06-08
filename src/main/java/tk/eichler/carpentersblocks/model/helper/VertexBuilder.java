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
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VertexBuilder {
    public static final int[] EMPTY = new int[28];

    private float x;
    private float y;
    private float z;

    public EnumTexCorner corner;

    private final int color;
    private final EnumFacing enumFacing;

    public VertexBuilder(EnumFacing facing) {
        this.color = ModelHelper.getFaceShadeColor(facing);
        this.enumFacing = facing;
    }

    public VertexBuilder withNewFacing(EnumFacing newFacing) {
        if (this.enumFacing == newFacing) {
            return this.clone();
        }

        final EnumCoords coords = EnumCoords.getCoords(newFacing, this.corner);

        if (coords == null) {
            throw new UnsupportedOperationException("Invalid facing or corner.");
        }

        return new VertexBuilder(newFacing).withCoords(coords).withTextureMapping(this.corner);
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

    public VertexBuilder withCoords(EnumCoords enumCoords) {
        this.x = enumCoords.x;
        this.y = enumCoords.y;
        this.z = enumCoords.z;

        return this;
    }

    public VertexBuilder withTextureMapping(EnumTexCorner corner) {
        this.corner = corner;

        return this;
    }

    public VertexBuilder addY(float y) {
        return clone().withCoords(this.x, this.y + y, this.z);
    }

    public int[] toIntArray(TextureAtlasSprite sprite) {
        return new int[]{
                Float.floatToRawIntBits(x),
                Float.floatToRawIntBits(y),
                Float.floatToRawIntBits(z),
                color,
                Float.floatToRawIntBits(sprite.getInterpolatedU(corner.x)),
                Float.floatToRawIntBits(sprite.getInterpolatedV(corner.y)),
                0
        };
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public VertexBuilder clone() {
        return new VertexBuilder(this.enumFacing).withCoords(this.x, this.y, this.z).withTextureMapping(corner);
    }

    public static VertexBuilder[] transformY(VertexBuilder[] vertices, float length) { //@todo clean code
        final VertexBuilder[] result = new VertexBuilder[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            result[i] = vertices[i].addY(length);
        }

        return result;
    }
}
