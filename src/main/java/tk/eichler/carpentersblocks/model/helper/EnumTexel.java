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

public enum EnumTexel {
    UPPER_LEFT(0, 0),
    UPPER_RIGHT(16, 0),
    MIDDLE_LEFT(0, 8),
    MIDDLE_RIGHT(16, 8),
    CENTER(8, 8),
    BOTTOM_LEFT(0, 16),
    BOTTOM_RIGHT(16, 16);

    private final int u;
    private final int v;

    EnumTexel(final int u, final int v) {
        this.u = u;
        this.v = v;
    }

    public int getU() {
        return this.u;
    }

    public int getV() {
        return this.v;
    }
}
