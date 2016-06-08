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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
abstract class BaseStateTileEntity extends TileEntity {
    private IExtendedBlockState state;

    protected  <V>void updateState(IUnlistedProperty<V> property, V value) {
        if (getState() == null) {
            FMLLog.severe("State is null, cannot update state");

            return;
        }

        this.state = getState().withProperty(property, value);
    }

    protected <T extends Comparable<T>, V extends T>void updateState(IProperty<T> property, V value) {
        if (getState() == null) {
            FMLLog.severe("State is null, cannot update state");

            return;
        }

        this.state = (IExtendedBlockState) getState().withProperty(property, value);
    }

    protected void triggerStateUpdate() {
        if (this.worldObj != null) {
            this.worldObj.setBlockState(getPos(), this.state, 3);
            this.worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());

            this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, getPos());
        }

        markDirty();
    }

    @Nullable
    @SuppressWarnings("ConstantConditions")
    public IExtendedBlockState getState() {
        // getBlockType can become null, as the function is client-only
        if (state == null && getBlockType() != null) {
            state = (IExtendedBlockState) getBlockType().getDefaultState();
        }

        return state;
    }


    /**
     * Used by server to communicate changes to client.
     * @return an NBTTagCompound
     */
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    /**
     * Used by server to communicate changes to client.
     * @return an SPacketUpdateTileEntity
     */
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

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
