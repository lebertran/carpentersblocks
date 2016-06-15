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
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Vector3f;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class TransformationHelper {

    private TransformationHelper() {
        // do not instantiate
    }

    // Defined clockwise
    public static final Transformation ROTATE_SIDE_90  = new Transformation(ModelRotation.X0_Y90);
    public static final Transformation ROTATE_SIDE_180 = new Transformation(ModelRotation.X0_Y180);
    public static final Transformation ROTATE_SIDE_270 = new Transformation(ModelRotation.X0_Y270);

    public static final Transformation ROTATE_UP = new Transformation(ModelRotation.X90_Y0);
    public static final Transformation ROTATE_UP_180 = new Transformation(ModelRotation.X180_Y0);
    public static final Transformation ROTATE_UP_270 = new Transformation(ModelRotation.X270_Y0);

    public static final Transformation TRANSLATE_Y_HALF = new Transformation(new TRSRTransformation(new Vector3f(0, 0.5F, 0), null, null, null));

    // Helpers
    public static final Transformation[] NO_TRANSFORMS = new Transformation[0];

    public static Transformation[] get(final Transformation... transformations) {
        return transformations;
    }
}
