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

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import tk.eichler.carpentersblocks.blocks.BlockCoverable;
import tk.eichler.carpentersblocks.blocks.BlockCarpentersBlock;
import tk.eichler.carpentersblocks.blocks.EnumShape;
import tk.eichler.carpentersblocks.model.helper.BakedQuadHelper;
import tk.eichler.carpentersblocks.registry.TextureRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CarpentersCoverableModel implements IBakedModel {

    /**
     * Default texture
     */
    private final TextureAtlasSprite base;

    /**
     * Covering block model
     */
    private IBakedModel coveringModel = null;

    public CarpentersCoverableModel() {
        this.base = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing facing, long rand) {
        final List<BakedQuad> ret = new ArrayList<>();

        if (state == null || ! (state instanceof IExtendedBlockState)) {
            return ret;
        }

        final EnumShape enumShape = state.getValue(BlockCarpentersBlock.PROP_ENUM_SHAPE);
        final ItemStack coverBlock = ((IExtendedBlockState) state).getValue(BlockCoverable.PROP_COVER_BLOCK);

        if (coverBlock != null) {
            this.coveringModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(coverBlock);

            final List<BakedQuad> originalQuads = coveringModel.getQuads(state, facing, rand);

            for (BakedQuad quad : originalQuads) {
                ret.add(getBakedQuad(enumShape, facing, quad));
            }
        } else {
            this.coveringModel = null;

            for (EnumFacing side : EnumFacing.values()) {
                ret.add(getBakedQuad(enumShape, side, null));
            }
        }

        return ret;
    }

    /**
     * Returns a {@link BakedQuad} object, depending on the given shape, facing and quad.
     *
     * @param shape Shape of the block, as defined in {@link EnumShape}
     * @param bakedQuad A BakedQuad of a covering block, or null which yields a BakedQuad with the default texture.
     * @return A {@link BakedQuad}
     */
    @Nonnull
    private BakedQuad getBakedQuad(@Nonnull EnumShape shape, @Nonnull EnumFacing facing, @Nullable BakedQuad bakedQuad) {
        switch (shape) {
            case FULL_BLOCK:
                if (bakedQuad == null)
                    return BakedQuadHelper.createBakedQuadFull(base, facing);

                return BakedQuadHelper.createBakedQuadFull(bakedQuad);
            case BOTTOM_SLAB:
                if (bakedQuad == null)
                    return BakedQuadHelper.createBakedQuadBottomSlab(base, facing);

                return BakedQuadHelper.createBakedQuadBottomSlab(bakedQuad);
            default:
                assert false : "Invalid shape.";
                return null;
        }
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() { return false; }

    @Override
    @Nonnull
    public TextureAtlasSprite getParticleTexture() {
        if (coveringModel != null) {
            return coveringModel.getParticleTexture();
        }
        return this.base;
    }

    @Override
    @Nonnull
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    @Nonnull
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

}
