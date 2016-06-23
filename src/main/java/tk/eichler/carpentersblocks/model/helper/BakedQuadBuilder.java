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

package tk.eichler.carpentersblocks.model.helper;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

/**
 * Builds a {@link net.minecraft.client.renderer.block.model.BakedQuad},
 * a two dimensional shape defined by four vertices.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BakedQuadBuilder extends UnpackedBakedQuad.Builder {

    /**
     * Default vertex format.
     */
    private static final VertexFormat DEFAULT_FORMAT = DefaultVertexFormats.ITEM;

    /**
     * Shape side.
     */
    private final EnumFacing facing;

    /**
     * Texture sprite.
     */
    private final TextureAtlasSprite sprite;

    /**
     * Transformed polygon.
     */
    private final Polygon polygon;

    /**
     * @param polygon Polygon defining a planar shape.
     * @param untransformedFacing Facing of the shape.
     * @param textureMap Texture map of the shape.
     * @param transformations Optional transformations
     */
    public BakedQuadBuilder(final Polygon polygon, final EnumFacing untransformedFacing, final EnumFacing coverFacing,
                            final Map<EnumFacing, TextureAtlasSprite> textureMap, final Transformation... transformations) {
        super(DEFAULT_FORMAT);

        this.facing = TransformationHelper.getTransformedFacing(untransformedFacing, transformations);
        this.sprite = textureMap.get(getSpriteFacing(coverFacing, this.facing));


        setQuadOrientation(this.facing);
        setTexture(this.sprite);
        setApplyDiffuseLighting(true);

        this.polygon = polygon.createWithTransformation(transformations);
    }

    //@TODO: Add log rotation
    private static EnumFacing getSpriteFacing(final EnumFacing coverFacing, final EnumFacing side) {
        if (side.getAxis() == EnumFacing.Axis.Y) {
            return side;
        }

        switch (coverFacing) {
            case NORTH:
                return side;
            case EAST:
                return side.rotateYCCW();
            case SOUTH:
                return side.rotateY().rotateY();
            case WEST:
                return side.rotateY();
            default:
                return side;
        }
    }


    /**
     * Puts a vertex into the builder.
     * Refer to {@link net.minecraftforge.client.model.ItemTextureQuadConverter}
     * and {@link net.minecraftforge.client.model.ItemLayerModel}
     *
     * @param vertex a Vertex
     */
    private void putVertex(final Vertex vertex) {
        for (int e = 0; e < DEFAULT_FORMAT.getElementCount(); e++) {
            switch (DEFAULT_FORMAT.getElement(e).getUsage()) {
                case POSITION:
                        put(e, vertex.getX(), vertex.getY(), vertex.getZ(), 1.0f);
                    break;
                case COLOR:
                    put(e, 1, 1, 1, 1);
                    break;
                case UV:
                    if (DEFAULT_FORMAT.getElement(e).getIndex() == 0) {
                        float u = sprite.getInterpolatedU(vertex.getTexCorner().getU());
                        float v = sprite.getInterpolatedV(vertex.getTexCorner().getV());
                        put(e, u, v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    put(e,  (float) facing.getFrontOffsetX(),
                            (float) facing.getFrontOffsetY(),
                            (float) facing.getFrontOffsetZ(), 0f);
                    break;
                default:
                    put(e);
                    break;
            }
        }
    }

    @Override
    public UnpackedBakedQuad build() {
        for (final Vertex vertex : this.polygon.getVertices()) {
            this.putVertex(vertex);
        }

        return super.build();
    }
}
