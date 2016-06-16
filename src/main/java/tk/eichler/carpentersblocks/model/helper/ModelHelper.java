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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Quat4f;

public final class ModelHelper {
    private ModelHelper() {
        // do not instantiate
    }

    //@todo move to math helper, if there are more math actions in the future
    public static final float DEGREE_TO_RADIANT = (float) (Math.PI / 180F);

    public static Quat4f getQuatFromAngle(final int x, final int y, final int z) {
        return TRSRTransformation.quatFromXYZ(
                x * DEGREE_TO_RADIANT,
                y * DEGREE_TO_RADIANT,
                z * DEGREE_TO_RADIANT);
    }

    public static IBakedModel getModel(final ItemStack itemStack) {
        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemStack);
    }
}
