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

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModelHelper {
    private ModelHelper() {
        // do not instantiate
    }

    private static TextureAtlasSprite getSpriteWithFacing(final List<BakedQuad> quads, @Nullable final EnumFacing facing) {
        for (BakedQuad quad : quads) {
            if (quad.getFace() == facing) {
                return quad.getSprite();
            }
        }

        return quads.get(0).getSprite();
    }

    public static Map<EnumFacing, TextureAtlasSprite> createTextureMap(final IBakedModel model, final IBlockState state) {
        final Map<EnumFacing, TextureAtlasSprite> textureMap = new HashMap<>();

        for (EnumFacing facing : EnumFacing.values()) {
            final List<BakedQuad> quads = model.getQuads(state, facing, 0);

            if (quads.size() == 0) {
                continue;
            }

            for (BakedQuad quad : quads) {
                if (quad.getFace() == facing) {
                    textureMap.put(facing, quad.getSprite());
                    break;
                }
            }

            if (!textureMap.containsKey(facing)) {
                textureMap.put(facing, quads.get(0).getSprite());
            }
        }

        return textureMap;
    }

    public static Map<EnumFacing, TextureAtlasSprite> createTextureMap(final TextureAtlasSprite sprite) {
        final Map<EnumFacing, TextureAtlasSprite> textureMap = new HashMap<>();
        for (EnumFacing facing : EnumFacing.values()) {
            textureMap.put(facing, sprite);
        }

        return textureMap;
    }

    public static TextureAtlasSprite getSpriteFromItemStack(@Nullable final ItemStack itemStack, final IBlockState state,
                                                            @Nullable final EnumFacing facing, final long random, final TextureAtlasSprite defaultSprite) {
        if (itemStack == null) {
            return defaultSprite;
        }

        final IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemStack);
        final List<BakedQuad> quads = model.getQuads(state, facing, random);

        if (quads.size() <= 0) {
            return defaultSprite;
        }

        return getSpriteWithFacing(quads, facing);

    }

    public static IBakedModel getModel(final ItemStack itemStack) {
        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemStack);
    }
}
