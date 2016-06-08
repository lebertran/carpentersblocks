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
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.model.helper.BakedQuadHelper;
import tk.eichler.carpentersblocks.registry.TextureRegistry;
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CarpentersBlockModel extends BaseModel {

    private final TextureAtlasSprite base;
    private IBakedModel coveringModel = null;


    public CarpentersBlockModel() {
        this.base = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing facing, long rand) {
        final List<BakedQuad> ret = new ArrayList<>();

        if (state == null || ! (state instanceof IExtendedBlockState)) {
            return ret;
        }

        ShapeableData data = ((IExtendedBlockState) state).getValue(DataProperty.SHAPEABLE_DATA);

        if (data == null) {
            data = ShapeableData.createInstance();
        }

        final EnumShape enumShape = data.currentShape;
        final ItemStack coverBlock = data.itemStack;

        if (coverBlock != null) {
            this.coveringModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(coverBlock);

            final List<BakedQuad> originalQuads = coveringModel.getQuads(state, facing, rand);

            for (BakedQuad quad : originalQuads) {
                if (facing == null) {
                    facing = EnumFacing.UP;

                    FMLLog.severe("Facing is null.");
                }

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

    private BakedQuad getBakedQuad(EnumShape shape, EnumFacing facing, @Nullable BakedQuad bakedQuad) {
        switch (shape) {
            case FULL_BLOCK:
                if (bakedQuad == null)
                    return BakedQuadHelper.createBakedQuadFull(base, facing);

                return BakedQuadHelper.createBakedQuadFull(bakedQuad);
            case BOTTOM_SLAB:
                if (bakedQuad == null)
                    return BakedQuadHelper.createBakedQuadBottomSlab(base, facing);

                return BakedQuadHelper.createBakedQuadBottomSlab(bakedQuad);
            case TOP_SLAB:
                if (bakedQuad == null)
                    return BakedQuadHelper.createBakedQuadTopSlab(base, facing);
                return BakedQuadHelper.createBakedQuadTopSlab(bakedQuad);
            default:
                assert false : "Invalid shape.";
                return null;
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        if (coveringModel != null) {
            return coveringModel.getParticleTexture();
        }

        return this.base;
    }
}
