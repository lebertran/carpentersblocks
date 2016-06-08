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

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TextureRegistry extends BaseRegistry {

    public static final ResourceLocation RESOURCE_QUARTERED_FRAME = new ResourceLocation("carpentersblocks:blocks/general/quartered_frame");

    public static final ResourceLocation[] TEXTURE_RESOURCES = new ResourceLocation[] {
            RESOURCE_QUARTERED_FRAME
    };

    public static final TextureRegistry INSTANCE = new TextureRegistry();

    @Override
    public boolean receivesEvents() {
        return true;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unused")
    public void onPreTextureStitching(TextureStitchEvent.Pre event) {
        if (event.getMap() == Minecraft.getMinecraft().getTextureMapBlocks()) {
            for (ResourceLocation location : TEXTURE_RESOURCES) {
                event.getMap().registerSprite(location);
            }
        }
    }
}
