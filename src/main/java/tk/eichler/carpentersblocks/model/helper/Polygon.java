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

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Polygon {

    private static final int VERTICES_AMOUNT = 4;

    private final Vertex[] vertices = new Vertex[VERTICES_AMOUNT];

    public static final Polygon POLYGON_EMPTY = new Polygon()
            .putVertex(0, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT)
            .putVertex(1, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT)
            .putVertex(2, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT)
            .putVertex(3, EnumCoordinates.BOTTOM_BOTTOMLEFT, EnumTexel.UPPER_LEFT);

    public Polygon putVertex(final int index, final Vertex vertex) {
        if (index < 0 || index >= VERTICES_AMOUNT) {
            throw new IllegalArgumentException("Invalid index.");
        }

        this.vertices[index] = vertex;

        return this;
    }

    public Polygon putVertex(final int index, final EnumCoordinates coords, final EnumTexel texCorner) {
        if (index < 0 || index >= VERTICES_AMOUNT) {
            throw new IllegalArgumentException("Invalid index.");
        }

        this.vertices[index] = new Vertex(coords.x, coords.y, coords.z, texCorner);

        return this;
    }

    public Vertex[] getVertices() {
        return this.vertices.clone();
    }

    public Polygon createWithTransformation(final Transformation... transformations) {
        if (transformations.length <= 0) {
            return this;
        }

        final Polygon newPolygon = new Polygon();

        for (int i = 0, verticesLength = vertices.length; i < verticesLength; i++) {
            Vertex newVertex = vertices[i];
            for (Transformation t : transformations) {
                newVertex = t.createTransformedVertex(newVertex);
            }

            newPolygon.putVertex(i, newVertex);
        }

        return newPolygon;
    }

}
