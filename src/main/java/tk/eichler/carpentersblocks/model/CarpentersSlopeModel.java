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

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.data.properties.Properties;
import tk.eichler.carpentersblocks.model.texture.TextureMapPool;
import tk.eichler.carpentersblocks.registry.TextureRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CarpentersSlopeModel extends BaseModel {

    @Override
    public List<BakedQuad> getQuads(@Nullable final IBlockState state, @Nullable final EnumFacing side, final long rand) {
        final List<BakedQuad> ret = new ArrayList<>();

        CoverableData coverData = CoverableData.createInstance();
        EnumShape shape = EnumShape.SLOPE;
        EnumOrientation orientation = EnumOrientation.NORTH_DOWN;

        // noinspection ConstantConditions
        if (state != null || state instanceof IExtendedBlockState) {
            final IExtendedBlockState eState = (IExtendedBlockState) state;

            coverData = eState.getValue(Properties.COVER_DATA);
            shape = state.getValue(Properties.SHAPE);
            orientation = state.getValue(Properties.ORIENTATION);

            if (coverData == null) {
                coverData = CoverableData.createInstance();
            }
        }


        final Map<EnumFacing, TextureAtlasSprite> textureMap;

        if (state != null && coverData.hasCover()) {
            TextureMapPool.getInstance().addTextureMap(coverData, state);
            textureMap = TextureMapPool.getInstance().getTextureMap(coverData.getBlockId());
        } else {
            textureMap = TextureMapPool.getInstance().getTextureMap(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
        }

        for (final EnumFacing face : EnumFacing.values()) {
            ret.add(CarpentersSlopeModelData.getBakedQuad(face, shape, orientation, textureMap));
        }

        return ret;
    }


    @Override
    public TextureAtlasSprite getParticleTexture() {
        return TextureMapPool.getInstance().getTextureMap(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString()).get(EnumFacing.UP);
    }
}
