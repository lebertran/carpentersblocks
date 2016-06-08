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

package tk.eichler.carpentersblocks.blocks;

import mcp.MethodsReturnNonnullByDefault;
import tk.eichler.carpentersblocks.model.BaseModel;
import tk.eichler.carpentersblocks.model.CarpentersSlopeModel;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCarpentersSlope extends BlockShapeable {
    private static final String registerName = "carpentersslope";

    // Instance
    public static final BlockCarpentersSlope INSTANCE = new BlockCarpentersSlope();

    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Override
    public BaseModel getModel() {
        return new CarpentersSlopeModel();
    }
}