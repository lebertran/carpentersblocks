/**
 * This file is part of Carpenter's Blocks.
 * <p>
 * Carpenter's Blocks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package tk.eichler.carpentersblocks.model.helper;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Vector4f;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Transformation {
    private final TRSRTransformation transformation;
    private final ModelRotation rotation;

    Transformation(final ModelRotation modelRotation) {
        this.transformation = new TRSRTransformation(modelRotation);
        this.rotation = modelRotation;
    }

    Transformation(final TRSRTransformation trsrTransformation) {
        this.transformation = trsrTransformation;
        this.rotation = null;
    }

    public TRSRTransformation get() {
        return this.transformation;
    }

    public EnumFacing transformFacing(final EnumFacing previousFacing) {
        if (this.rotation != null) {
            return this.rotation.rotate(previousFacing);
        }

        return previousFacing;
    }

    public Vertex createTransformedVertex(final Vertex vertex) {
        final Vector4f newCoords = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);
        this.transformation.getMatrix().transform(newCoords);

        return new Vertex(newCoords.getX(), newCoords.getY(), newCoords.getZ(), vertex.getTexCorner());
    }
}
