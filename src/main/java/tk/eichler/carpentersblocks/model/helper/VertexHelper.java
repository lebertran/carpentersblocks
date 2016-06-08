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

import com.google.common.primitives.Ints;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VertexHelper {
    public static VertexBuilder[] verticesToFacing(VertexBuilder[] vertices, EnumFacing facing) {
        final List<VertexBuilder> newVertices = new ArrayList<>();

        for (VertexBuilder vertex : vertices) {
            newVertices.add(vertex.withNewFacing(facing));
        }

        return newVertices.toArray(new VertexBuilder[newVertices.size()]);
    }

    //@TODO: move
    public static int[] verticesToInts(TextureAtlasSprite sprite, VertexBuilder[] vertices) {
        if (vertices.length != 4) {
            FMLLog.severe("Invalid amount of vertices.");
            return new int[] {};
        }

        return Ints.concat(
                vertices[0].toIntArray(sprite),
                vertices[1].toIntArray(sprite),
                vertices[2].toIntArray(sprite),
                vertices[3].toIntArray(sprite)
        );
    }
}
