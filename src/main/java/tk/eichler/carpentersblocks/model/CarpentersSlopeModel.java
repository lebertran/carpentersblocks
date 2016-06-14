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
import tk.eichler.carpentersblocks.model.helper.BakedQuadBuilder;
import tk.eichler.carpentersblocks.model.helper.ModelHelper;
import tk.eichler.carpentersblocks.model.helper.Transformation;
import tk.eichler.carpentersblocks.model.helper.TransformationHelper;
import tk.eichler.carpentersblocks.registry.TextureRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CarpentersSlopeModel extends BaseModel {

    private final TextureAtlasSprite defaultSprite;

    public CarpentersSlopeModel() {
        this.defaultSprite = Minecraft.getMinecraft().getTextureMapBlocks()
                .getAtlasSprite(TextureRegistry.RESOURCE_QUARTERED_FRAME.toString());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable final IBlockState state, @Nullable final EnumFacing side, final long rand) {
        final List<BakedQuad> ret = new ArrayList<>();

        if (state == null || !(state instanceof IExtendedBlockState)) {
            return ret;
        }

        CoverableData data = ((IExtendedBlockState) state).getValue(DataProperty.COVERABLE_DATA);
        EnumShape shape = state.getValue(BlockShapeable.PROP_SHAPE);
        EnumOrientation orientation = state.getValue(BlockShapeable.PROP_ORIENTATION);

        TextureAtlasSprite sprite = ModelHelper.getSpriteFromItemStack(data.getItemStack(), state, side, rand, this.defaultSprite);

        for (EnumFacing face : EnumFacing.values()) {
            ret.add(getBakedQuad(face, shape, sprite));
        }

        return ret;
    }

    private BakedQuad getBakedQuad(final EnumFacing facing, final EnumShape shape, final TextureAtlasSprite sprite) {
        final Transformation[] transformations = getTransformations(shape);

        return new BakedQuadBuilder(CarpentersSlopeModelData.getVerticesDefaultSlope(facing), facing, sprite, transformations).build();
    }

    private static Transformation[] getTransformations(final EnumShape shape) {
        switch (shape) {
            case NORTH_SLOPE:
                return TransformationHelper.NO_TRANSFORMS;
            case SOUTH_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_180);
            case WEST_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_270);
            case EAST_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_90);
            case NORTH_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_UP);
            case SOUTH_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_180, TransformationHelper.ROTATE_UP);
            case WEST_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_270, TransformationHelper.ROTATE_UP);
            case EAST_TOP_SLOPE:
                return TransformationHelper.get(TransformationHelper.ROTATE_SIDE_90, TransformationHelper.ROTATE_UP);
            default:
                return TransformationHelper.NO_TRANSFORMS;
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.defaultSprite;
    }
}
