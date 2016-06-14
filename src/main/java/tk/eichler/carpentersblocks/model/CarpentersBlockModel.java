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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import tk.eichler.carpentersblocks.blocks.BlockShapeable;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.EnumOrientation;
import tk.eichler.carpentersblocks.data.EnumShape;
import tk.eichler.carpentersblocks.model.helper.ModelHelper;
import tk.eichler.carpentersblocks.registry.TextureRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CarpentersBlockModel extends BaseModel {

    private final TextureAtlasSprite defaultSprite;

    public CarpentersBlockModel() {
        this.defaultSprite = Minecraft.getMinecraft()
                .getTextureMapBlocks().getAtlasSprite(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable final IBlockState state,
                                    @Nullable final EnumFacing facing, final long rand) {
        final List<BakedQuad> quads = new ArrayList<>();

        if (state == null || !(state instanceof IExtendedBlockState)) {
            return quads;
        }

        CoverableData coverData = ((IExtendedBlockState) state).getValue(DataProperty.COVERABLE_DATA);
        if (coverData == null) {
            coverData = CoverableData.createInstance();
        }

        final EnumShape enumShape = state.getValue(BlockShapeable.PROP_SHAPE);
        final EnumOrientation enumOrientation = state.getValue(BlockShapeable.PROP_ORIENTATION);

        for (EnumFacing f : EnumFacing.values()) {
            final TextureAtlasSprite sprite =
                    ModelHelper.getSpriteFromItemStack(coverData.getItemStack(), state, f, rand, this.defaultSprite);

            quads.add(CarpentersBlockModelData.getBakedQuad(enumShape, enumOrientation, f, sprite));
        }

        return quads;
    }


    @Override
    public TextureAtlasSprite getParticleTexture() {
        //@TODO Make particle texture depend on current IBlockState.
        // Waiting for https://github.com/MinecraftForge/MinecraftForge/issues/2939.
        return this.defaultSprite;
    }
}
