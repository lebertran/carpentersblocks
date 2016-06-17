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

package tk.eichler.carpentersblocks.registry;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.model.texture.TextureMapPool;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Registers custom textures.
 */
@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TextureRegistry implements BaseRegistry {

    public static final ResourceLocation RESOURCE_QUARTERED_FRAME =
            new ResourceLocation("carpentersblocks:blocks/general/quartered_frame");

    private static final ResourceLocation[] TEXTURE_RESOURCES = new ResourceLocation[] {
            RESOURCE_QUARTERED_FRAME
    };

    private static TextureRegistry instance;

    public static TextureRegistry getInstance() {
        if (instance == null) {
            instance = new TextureRegistry();
        }

        return instance;
    }

    @Override public boolean receivesEvents() {
        return true;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onPreTextureStitching(final TextureStitchEvent.Pre event) {
        if (event.getMap() == Minecraft.getMinecraft().getTextureMapBlocks()) {
            for (ResourceLocation location : TEXTURE_RESOURCES) {
                event.getMap().registerSprite(location);
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onPostTextureStitching(final TextureStitchEvent.Post event) {
        for (ResourceLocation location : TEXTURE_RESOURCES) {
            TextureAtlasSprite sprite = event.getMap().getAtlasSprite(location.toString());

            if (sprite.getIconName().equals("missingno")) {
                continue;
            }

            TextureMapPool.getInstance().addTextureMap(
                    location.toString(), event.getMap().getAtlasSprite(location.toString()));
        }
    }
}
