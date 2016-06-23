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

package tk.eichler.carpentersblocks.model;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;
import tk.eichler.carpentersblocks.model.helper.ModelHelper;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;


public abstract class BaseModel implements IPerspectiveAwareModel {

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    @Nonnull
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

    /**
     * Refer to assets/minecraft/models/block/block.json
     *
     * @param cameraTransformType Transform type
     * @return Pair with current model and transformation matrix.
     */
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(final ItemCameraTransforms.TransformType cameraTransformType) {
        final TRSRTransformation transformation;


        switch (cameraTransformType) {
            case GUI:
                transformation = new TRSRTransformation(
                        null,
                        null,
                        new Vector3f(0.625F, 0.625F, 0.625F),
                        ModelHelper.getQuatFromAngle(30, 225, 0)
                );
                break;
            case GROUND:
                transformation = new TRSRTransformation(
                        null,
                        null,
                        new Vector3f(0.25F, 0.25F, 0.25F),
                        null
                );
                break;
            case FIXED:
                transformation = new TRSRTransformation(
                        null,
                        null,
                        new Vector3f(0.5F, 0.5F, 0.5F),
                        null
                );
                break;
            case THIRD_PERSON_RIGHT_HAND:
                transformation = new TRSRTransformation(
                        null,
                        null,
                        new Vector3f(0.375F, 0.375F, 0.375F),
                        ModelHelper.getQuatFromAngle(75, 45, 0)
                );
                break;
            case FIRST_PERSON_RIGHT_HAND:
                transformation = new TRSRTransformation(
                        null,
                        ModelHelper.getQuatFromAngle(0, 45, 0),
                        new Vector3f(0.4F, 0.4F, 0.4F),
                        null
                );
                break;
            case FIRST_PERSON_LEFT_HAND:
                transformation = new TRSRTransformation(
                        null,
                        null,
                        new Vector3f(0.4F, 0.4F, 0.4F),
                        ModelHelper.getQuatFromAngle(0, 225, 0)
                );
                break;
            default:
                transformation = null;
        }

        if (transformation != null) {
            return Pair.of(this, transformation.getMatrix());
        }

        return Pair.of(this, null);
    }
}
