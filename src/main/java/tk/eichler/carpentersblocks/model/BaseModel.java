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

package tk.eichler.carpentersblocks.model;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
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

        Pair<? extends IBakedModel, Matrix4f> pair = IPerspectiveAwareModel.MapWrapper.handlePerspective(this,
                IPerspectiveAwareModel.MapWrapper.getTransforms(ItemCameraTransforms.DEFAULT),
                cameraTransformType);


        switch (cameraTransformType) {
            case GUI:
                transformation = new TRSRTransformation(
                        null,
                        new Quat4f(30, 255, 0, 1),
                        new Vector3f(0.625F, 0.625F, 0.625F),
                        null
                );
                break;
            case GROUND:
                transformation = new TRSRTransformation(
                        new Vector3f(0, 3, 0),
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
                        new Vector3f(0, 2.5F, 0),
                        new Quat4f(75, 45, 0, 1),
                        new Vector3f(0.375F, 0.375F, 0.375F),
                        null
                );
                break;
            case FIRST_PERSON_RIGHT_HAND:
                transformation = new TRSRTransformation(
                        null,
                        new Quat4f(0, 45, 0, 1),
                        new Vector3f(0.4F, 0.4F, 0.4F),
                        null
                );
                break;
            case FIRST_PERSON_LEFT_HAND:
                transformation = new TRSRTransformation(
                        null,
                        new Quat4f(0, 255, 0, 1),
                        new Vector3f(0.4F, 0.4F, 0.4F),
                        null
                );

                ItemCameraTransforms.DEFAULT.getTransform(cameraTransformType);
                break;
            default:
                transformation = null;
        }

        if (transformation != null) {
            final TRSRTransformation newTransform = transformation.compose(
                    new TRSRTransformation(pair.getRight())
            );
            return Pair.of(this, newTransform.getMatrix());
        }

        return Pair.of(this, null);
    }
}
