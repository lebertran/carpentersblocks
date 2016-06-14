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

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Vector4f;

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
     * Exact amount of vertices the builder can hold.
     */
    private static final int VERTICES_AMOUNT = 4;

    /**
     * Shape side.
     */
    private final EnumFacing facing;

    /**
     * All transformations.
     */
    private final Transformation[] transformations;

    /**
     * Are there TRSRTransformations.
     */
    private final boolean hasTransformation;

    /**
     * Texture sprite.
     */
    private final TextureAtlasSprite sprite;


    /**
     * @param vertices Vertices defining a planar shape.
     * @param facing Facing of the shape.
     * @param sprite Texture of the shape.
     * @param transformations Optional transformations
     */
    public BakedQuadBuilder(final VertexBuilder[] vertices, final EnumFacing facing,
                            final TextureAtlasSprite sprite, final Transformation... transformations) {
        super(DEFAULT_FORMAT);

        if (vertices.length != VERTICES_AMOUNT) {
            throw new UnsupportedOperationException("Invalid vertices");
        }

        this.transformations = transformations;
        this.hasTransformation = transformations.length > 0;

        this.sprite = sprite;
        this.facing = getTransformedFacing(facing);

        setQuadOrientation(this.facing);
        setTexture(this.sprite);
        setApplyDiffuseLighting(true);

        for (VertexBuilder vertex : vertices) {
            this.putVertex(vertex);
        }
    }

    public EnumFacing getTransformedFacing(final EnumFacing untransformedFacing) {
        if (!this.hasTransformation) {
            return untransformedFacing;
        }

        EnumFacing result = untransformedFacing;

        for (Transformation t : this.transformations) {
            result = t.transformFacing(result);
        }

        return result;
    }


    /**
     * Puts a vertex into the builder.
     * Refer to {@link net.minecraftforge.client.model.ItemTextureQuadConverter}
     * and {@link net.minecraftforge.client.model.ItemLayerModel}
     *
     * @param vertex a Vertex
     */
    private void putVertex(final VertexBuilder vertex) {
        Vector4f transformVec = new Vector4f();
        for (int e = 0; e < DEFAULT_FORMAT.getElementCount(); e++) {
            switch (DEFAULT_FORMAT.getElement(e).getUsage()) {
                case POSITION:
                    transformVec.set(vertex.getVector4f());

                    if (!hasTransformation) {
                        put(e, transformVec.x, transformVec.y, transformVec.z, 1.0f);
                    } else {
                        for (Transformation t : transformations) {
                            t.get().getMatrix().transform(transformVec);
                        }

                        put(e, transformVec.x, transformVec.y, transformVec.z, transformVec.w);
                    }
                    break;
                case COLOR:
                    put(e, 1, 1, 1, 1);
                    break;
                case UV:
                    if (DEFAULT_FORMAT.getElement(e).getIndex() == 0) {
                        float u = sprite.getInterpolatedU(vertex.getCorner().getU());
                        float v = sprite.getInterpolatedV(vertex.getCorner().getV());
                        put(e, u, v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    put(e, (float) facing.getFrontOffsetX(),
                            (float) facing.getFrontOffsetY(),
                            (float) facing.getFrontOffsetZ(), 0f);
                    break;
                default:
                    put(e);
                    break;
            }
        }
    }
}
