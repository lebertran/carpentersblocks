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

package tk.eichler.carpentersblocks.tileentities;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.data.properties.EnumOrientation;
import tk.eichler.carpentersblocks.data.properties.EnumShape;
import tk.eichler.carpentersblocks.data.properties.Properties;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ShapeableBlockTileEntity extends CoverableBlockTileEntity {

    public static final IProperty[] PROPERTIES = new IProperty[] {
            Properties.ORIENTATION, Properties.SHAPE, Properties.COVER_FACING
    };

    public static final IUnlistedProperty[] UNLISTED_PROPERTIES = new IUnlistedProperty[] {
            Properties.COVER_DATA
    };

    @Override
    public abstract ShapeableData getDataInstance();

    public void setShapeableData(final EnumShape shape, final EnumOrientation orientation) {

        if (shape.isOrientationValid(orientation)) {
            getDataInstance().setShapeData(shape, orientation);

            this.getDataInstance().checkForChanges();
        } else {
            throw new IllegalArgumentException("Invalid orientation for shape.");
        }

    }

    @Override
    public IExtendedBlockState createBlockState(final IBlockState state) {
        IExtendedBlockState newState = (IExtendedBlockState) state;
        newState = (IExtendedBlockState) newState
                .withProperty(Properties.COVER_DATA, this.getDataInstance())
                .withProperty(Properties.SHAPE, this.getDataInstance().getShape())
                .withProperty(Properties.ORIENTATION, this.getDataInstance().getOrientation())
                .withProperty(Properties.COVER_FACING, this.getDataInstance().getCoverFacing());

        return newState;
    }

    @Override
    public void onDataUpdate() {
        updateState(Properties.COVER_DATA, getDataInstance());
        updateState(Properties.COVER_FACING, getDataInstance().getCoverFacing());
        updateState(Properties.SHAPE, getDataInstance().getShape());
        updateState(Properties.ORIENTATION, getDataInstance().getOrientation());

        triggerStateUpdate();
    }
}
