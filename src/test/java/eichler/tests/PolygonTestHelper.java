/**
 * This file is part of Carpenter's Blocks.
 * <p>
 * Carpenter's Blocks is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * <p>
 * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with Foobar.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package eichler.tests;

import tk.eichler.carpentersblocks.model.helper.Polygon;
import tk.eichler.carpentersblocks.model.helper.Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PolygonTestHelper {
    public static void testPolygon(final Polygon polygon) {
        assertEquals(polygon.getVertices().size(), 4);

        for (final Vertex vertex : polygon.getVertices()) {
            assertNotNull(vertex.getTexCorner());
        }

        final boolean samePlane = new Plane(polygon.getVertices().get(0).getVec3d(),
                polygon.getVertices().get(1).getVec3d(),
                polygon.getVertices().get(2).getVec3d())
                .isInPlane(polygon.getVertices().get(3).getVec3d());

        assertTrue(samePlane);
    }
}
