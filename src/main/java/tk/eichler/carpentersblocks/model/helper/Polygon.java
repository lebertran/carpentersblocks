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

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a planar shape with with four vertices.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Polygon {

    private static final int VERTICES_AMOUNT = 4;

    private final List<Vertex> vertices = new ArrayList<>();

    public static final Polygon POLYGON_EMPTY = new Polygon()
            .putVertex(0, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT)
            .putVertex(1, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT)
            .putVertex(2, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT)
            .putVertex(3, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT);

    public Polygon putVertex(final int index, final Vertex vertex) {
        if (index < 0 || index >= VERTICES_AMOUNT) {
            throw new IllegalArgumentException("Invalid index.");
        }

        this.vertices.add(index, vertex);

        return this;
    }

    public Polygon putVertex(final int index, final EnumCoordinates coords, final EnumTexel texCorner) {
        if (index < 0 || index >= VERTICES_AMOUNT) {
            throw new IllegalArgumentException("Invalid index.");
        }

        this.vertices.add(index, new Vertex(coords.x, coords.y, coords.z, texCorner));

        return this;
    }

    /**
     * Returns an immutable copy of the vertices of the current polygon.
     * @return immutable list of vertices.
     */
    public List<Vertex> getVertices() {
        return ImmutableList.copyOf(this.vertices);
    }

    /**
     * Returns a new polygon with transformed data based on the current polygon.
     *
     * @param transformations applied transformations
     * @return new transformed polygon instance
     */
    public Polygon createWithTransformation(final Transformation... transformations) {
        if (transformations.length <= 0) {
            return this;
        }

        final Polygon newPolygon = new Polygon();

        for (int i = 0, verticesLength = vertices.size(); i < verticesLength; i++) {
            Vertex newVertex = vertices.get(i);
            for (Transformation t : transformations) {
                newVertex = t.createTransformedVertex(newVertex);
            }

            newPolygon.putVertex(i, newVertex);
        }

        return newPolygon;
    }
}
