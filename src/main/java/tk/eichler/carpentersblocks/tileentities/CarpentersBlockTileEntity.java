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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.FMLLog;
import tk.eichler.carpentersblocks.blocks.BlockCoverable;
import tk.eichler.carpentersblocks.blocks.BlockCarpentersBlock;
import tk.eichler.carpentersblocks.blocks.EnumShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This tile entity can save an ItemStack and an EnumState
 * and thus can be used for any Carpenter's Block.
 */
public class CarpentersBlockTileEntity extends TileEntity {
    private IExtendedBlockState state;

    private ItemStack blockStack;
    private EnumShape enumShape = EnumShape.FULL_BLOCK;

    public void setBlockStack(@Nullable ItemStack stack) {
        if (stack == null) return;

        this.blockStack = stack;

        updateState();
    }

    private void setEnumShape(EnumShape enumShape) {
        this.enumShape = enumShape;

        updateState();
    }

    @Nullable
    public Block getBlock() {
        if (state == null || state.getValue(BlockCoverable.PROP_COVER_BLOCK) == null) return null;

        return Block.getBlockFromItem(state.getValue(BlockCoverable.PROP_COVER_BLOCK).getItem());
    }

    /**
     * Toggles through all available shapes defined by {@link EnumShape}.
     */
    public void toggleShapes() { //@TODO Only allow certain shapes as defined by the block.
        if (getState() == null) {
            FMLLog.severe("Cannot toggle state as there is no default state yet.");
            return;
        }
        enumShape = getState().cycleProperty(BlockCarpentersBlock.PROP_ENUM_SHAPE).getValue(BlockCarpentersBlock.PROP_ENUM_SHAPE);

        worldObj.setBlockState(getPos(), getBlockType().getDefaultState().withProperty(BlockCarpentersBlock.PROP_ENUM_SHAPE, enumShape));
        updateState();
    }

    /**
     * Reads an {@link NBTTagCompound} and writes its values to internal variables. Finally triggers a BlockState update.
     * @param compound NBTTagCompound
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        final ItemStack stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Block"));
        setBlockStack(stack);

        if (compound.hasKey("State")) {
            enumShape = compound.getString("State").equals("") ? enumShape : EnumShape.valueOf(compound.getString("State"));
            setEnumShape(enumShape);
        }

        updateState();
    }

    /**
     * Server method, updates given NBTTagCompound with values from internal variables.
     * @param compound unmodified NBTTagCompound
     * @return updated NBTTagCompound
     */
    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setString("State", enumShape.name());

        if (blockStack != null) {
            final NBTTagCompound itemStackCompound = new NBTTagCompound();
            blockStack.writeToNBT(itemStackCompound);
            compound.setTag("Block", itemStackCompound);
        }

        return compound;
    }

    /**
     * Used by server to communicate changes to client.
     * @return an NBTTagCompound
     */
    @Override
    @Nonnull
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    /**
     * Used by server to communicate changes to client.
     * @return an SPacketUpdateTileEntity
     */
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        final NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);

        return new SPacketUpdateTileEntity(getPos(), 0, nbt);
    }

    /**
     * Used by client to receive updates from server.
     *
     * @param net NetworkManager
     * @param pkt SPacketUpdateTileEntity
     */
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }



    @Nullable
    public IExtendedBlockState getState() {

        // getBlockType can become null, as the function is client-only
        if (state == null && getBlockType() != null) {
            state = (IExtendedBlockState) getBlockType().getDefaultState();
        }

        return state;
    }


    private void setState(IExtendedBlockState state) {
        this.state = state;
    }

    /**
     * Updates BlockState with data from internal variables and triggers a render update as well as a chunk save.
     */
    private void updateState() {
        if (getState() == null) {
            return;
        }

        setState(getState().withProperty(BlockCoverable.PROP_COVER_BLOCK, blockStack));
        setState((IExtendedBlockState) getState().withProperty(BlockCarpentersBlock.PROP_ENUM_SHAPE, enumShape));


        if (worldObj != null) {
            worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
        }
        markDirty();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
