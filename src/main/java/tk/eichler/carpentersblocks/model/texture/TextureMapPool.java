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

package tk.eichler.carpentersblocks.model.texture;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.model.helper.ModelHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TextureMapPool {

    private static TextureMapPool instance;

    private final Map<String, Map<EnumFacing, TextureAtlasSprite>> textureMaps = new HashMap<>();


    public static TextureMapPool getInstance() {
        if (instance == null) {
            instance = new TextureMapPool();
        }

        return instance;
    }

    public Map<EnumFacing, TextureAtlasSprite> getTextureMap(final String key) {
        return textureMaps.get(key);
    }

    public void addTextureMap(final CoverableData data, final IBlockState state) {
        if (textureMaps.containsKey(data.getBlockId())) {
            return;
        }

        final IBakedModel model = ModelHelper.getModel(data.getItemStack());
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

        textureMaps.put(data.getBlockId(), textureMap);
    }

    public void addTextureMap(final String key, final TextureAtlasSprite sprite) {
        if (textureMaps.containsKey(key)) {
            return;
        }

        final Map<EnumFacing, TextureAtlasSprite> textureMap = new HashMap<>();
        for (EnumFacing facing : EnumFacing.values()) {
            textureMap.put(facing, sprite);
        }

        textureMaps.put(key, textureMap);
    }
}
