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

package tk.eichler.carpentersblocks.tileentities;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tk.eichler.carpentersblocks.data.CoverableData;
import tk.eichler.carpentersblocks.data.DataUpdateListener;
import tk.eichler.carpentersblocks.data.properties.Properties;
import tk.eichler.carpentersblocks.util.BlockHelper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class CoverableBlockTileEntity extends BaseStateTileEntity<CoverableData> implements DataUpdateListener {

    @Override
    public abstract CoverableData getDataInstance();

    public boolean trySetBlockStack(final ItemStack stack) {

        if (stack.isItemEqual(this.getDataInstance().getItemStack())) {
            return false;
        }

        if (BlockHelper.isValidCoverBlock(stack)) {
            if (worldObj == null || !worldObj.isRemote) {
                this.getDataInstance().setItemStack(stack);
                this.getDataInstance().checkForChanges();
            }

            return true;
        }


        return false;
    }

    public boolean removeBlockStack() {

        if (!getDataInstance().hasCover()) {
            return false;
        }

        if (worldObj == null || !worldObj.isRemote) {
            this.getDataInstance().setItemStack(null);
            this.getDataInstance().checkForChanges();
        }

        return true;
    }

    /**
     * Reads an {@link NBTTagCompound} and writes its values to internal variables. Finally triggers a BlockState update.
     * @param compound NBTTagCompound
     */
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.getDataInstance().fromNBT(compound);
        this.getDataInstance().checkForChanges();
    }

    /**
     * Server method, updates given NBTTagCompound with values from internal variables.
     * @param compound unmodified NBTTagCompound
     * @return updated NBTTagCompound
     */
    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.getDataInstance().toNBT(compound);
        return compound;
    }

    @Override
    public void onDataUpdate() {
        updateState(Properties.COVER_DATA, getDataInstance());

        triggerStateUpdate();
    }

}
