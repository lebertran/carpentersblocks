/*
 * *
 *  * This file is part of Carpenter's Blocks.
 *  * <p>
 *  * Carpenter's Blocks is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 *  * option) any later version.
 *  * <p>
 *  * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *  * <p>
 *  * You should have received a copy of the GNU General Public License along with Foobar.  If not, see
 *  * <http://www.gnu.org/licenses/>.
 *  
 */

package tk.eichler.carpentersblocks.data.properties;

import org.junit.Assert;
import org.junit.Test;

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
public class EnumShapeTest {
    @Test
    public void testEnumShapes() {
        for (final EnumShape shape : EnumShape.values()) {
            Assert.assertNotEquals(shape.getValidOrientations().size(), 0);
        }
    }
}