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

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import tk.eichler.carpentersblocks.blocks.BlockShapeable;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
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

    private CoverableData coverData = CoverableData.createInstance();
    private EnumShape shape = EnumShape.SLOPE;
    private EnumOrientation orientation = EnumOrientation.NORTH;

    @Override
    public List<BakedQuad> getQuads(@Nullable final IBlockState state, @Nullable final EnumFacing side, final long rand) {
        final List<BakedQuad> ret = new ArrayList<>();

        getDataFromState(state);

        Map<EnumFacing, TextureAtlasSprite> textureMap;

        if (state != null && this.coverData.hasCover()) {
            TextureMapPool.getInstance().addTextureMap(coverData, state);
            textureMap = TextureMapPool.getInstance().getTextureMap(coverData.getBlockId());
        } else {
            textureMap = TextureMapPool.getInstance().getTextureMap(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
        }

        for (final EnumFacing face : EnumFacing.values()) {
            ret.add(CarpentersSlopeModelData.getBakedQuad(face, shape, textureMap));
        }

        return ret;
    }

    public void getDataFromState(@Nullable final IBlockState state) {
        if (state == null || !(state instanceof IExtendedBlockState)) {
            return;
        }

        final IExtendedBlockState eState = (IExtendedBlockState) state;

        this.coverData = eState.getValue(DataProperty.COVERABLE_DATA);
        this.shape = state.getValue(BlockShapeable.PROP_SHAPE);
        this.orientation = state.getValue(BlockShapeable.PROP_ORIENTATION);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return TextureMapPool.getInstance().getTextureMap(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString()).get(EnumFacing.UP);
    }
}
