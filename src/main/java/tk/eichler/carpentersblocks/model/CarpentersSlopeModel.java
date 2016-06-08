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
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.eichler.carpentersblocks.model.helper.VertexBuilder;
import tk.eichler.carpentersblocks.model.helper.VertexHelper;
import tk.eichler.carpentersblocks.registry.TextureRegistry;
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static tk.eichler.carpentersblocks.model.CarpentersBlockModelData.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CarpentersSlopeModel extends BaseModel {

    private final TextureAtlasSprite defaultSprite;

    public CarpentersSlopeModel() {
        this.defaultSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        final List<BakedQuad> ret = new ArrayList<>();

        for (EnumFacing face : EnumFacing.values()) {
            ret.add(getBakedQuad(null, face));
        }

        return ret;
    }

    private BakedQuad getBakedQuad(@Nullable EnumShape shape, EnumFacing facing) {
        return new BakedQuad(getVertices(facing), -1, facing, this.defaultSprite, false, DefaultVertexFormats.ITEM);
    }

    private int[] getVertices(EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return VertexHelper.verticesToInts(defaultSprite, VERTICES_DOWN);
            case WEST:
                return VertexHelper.verticesToInts(defaultSprite, CarpentersSlopeModelData.VERTICES_SLOPE_SIDE_WEST);
            case EAST:
                return VertexHelper.verticesToInts(defaultSprite, CarpentersSlopeModelData.VERTICES_SLOPE_SIDE_EAST);
                //return VertexHelper.verticesToInts(defaultSprite,
                        //VertexHelper.verticesToFacing(CarpentersSlopeModelData.VERTICES_SLOPE_SIDE_WEST, EnumFacing.EAST));
            case NORTH:
                return VertexHelper.verticesToInts(defaultSprite, CarpentersSlopeModelData.VERTICES_SLOPE_FRONT_NORTH);
            case SOUTH:
                return VertexHelper.verticesToInts(defaultSprite, VERTICES_FULL_SOUTH);
            case UP:
            default:
                return VertexBuilder.EMPTY;
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.defaultSprite;
    }
}
