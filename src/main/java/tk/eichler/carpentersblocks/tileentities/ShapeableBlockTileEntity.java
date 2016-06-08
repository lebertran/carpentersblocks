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
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.blocks.BlockCarpentersBlock;
import tk.eichler.carpentersblocks.data.DataProperty;
import tk.eichler.carpentersblocks.data.DataUpdateListener;
import tk.eichler.carpentersblocks.data.ShapeableData;
import tk.eichler.carpentersblocks.util.BlockHelper;
import tk.eichler.carpentersblocks.util.EnumShape;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This tile entity can save an ItemStack and an EnumState
 * and thus can be used for any Carpenter's Block.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShapeableBlockTileEntity extends BaseStateTileEntity implements DataUpdateListener {

    private final ShapeableData shapeableData = ShapeableData.createInstance();

    public ShapeableBlockTileEntity() {
        this.shapeableData.addUpdateListener(this);
    }

    /**
     * Actions
     */

    /**
     * This should be used in order to set the block stack e.g. from an PlayerInteractEvent handler.
     * Do not use this method in this TileEntity.
     */
    public boolean trySetBlockStack(ItemStack stack) {

        if (stack.isItemEqual(this.shapeableData.itemStack)) {
            return false;
        }

        if (BlockHelper.isValidCoverBlock(stack)) {
            if (worldObj == null || !worldObj.isRemote) {
                this.shapeableData.setItemStack(stack);
                this.shapeableData.checkForChanges();
            }

            return true;
        }


        return false;
    }

    /**
     * This should be used in order to remove the block stack e.g. from an PlayerInteractEvent handler.
     * Do not use this method in this TileEntity.
     */
    public boolean removeBlockStack() {

        if (!shapeableData.hasCover()) {
            return false;
        }

        if (worldObj == null || !worldObj.isRemote) {
            this.shapeableData.setItemStack(null);
            this.shapeableData.checkForChanges();
        }

        return true;
    }

    /**
     * Toggles through all available shapes defined by {@link EnumShape}.
     *
     * This should be used e.g. from an PlayerInteractEvent handler.
     * Do not use this method in this TileEntity.
     */
    public boolean setShape(EnumFacing facing) {

        if (getState() == null) {
            FMLLog.severe("Cannot toggle state as there is no default state yet.");
            return false;
        }

        if (worldObj == null || !worldObj.isRemote) {
            if (! this.shapeableData.isShape(EnumShape.FULL_BLOCK)) {
                this.shapeableData.setShape(EnumShape.FULL_BLOCK);
                this.shapeableData.checkForChanges();
                return true;
            }

            switch (facing) {
                case UP:
                    this.shapeableData.setShape(EnumShape.BOTTOM_SLAB);
                    break;
                case DOWN:
                    this.shapeableData.setShape(EnumShape.TOP_SLAB);
            }

            this.shapeableData.checkForChanges();
        }

        return true;
    }

    /**
     * Reads an {@link NBTTagCompound} and writes its values to internal variables. Finally triggers a BlockState update.
     * @param compound NBTTagCompound
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.shapeableData.fromNBT(compound);
    }

    /**
     * Server method, updates given NBTTagCompound with values from internal variables.
     * @param compound unmodified NBTTagCompound
     * @return updated NBTTagCompound
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.shapeableData.toNBT(compound);
        return compound;
    }

    @Override
    public void onDataUpdate() {
        updateState(DataProperty.SHAPEABLE_DATA, this.shapeableData);
        updateState(BlockCarpentersBlock.PROP_SHAPE, this.shapeableData.currentShape); //@workaround, see BlockCarpentersBlock.java

        triggerStateUpdate();
    }

}
