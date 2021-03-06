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

package tk.eichler.carpentersblocks.registry;

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import tk.eichler.carpentersblocks.blocks.BlockWrapper;
import tk.eichler.carpentersblocks.blocks.variants.BlockCuboid;
import tk.eichler.carpentersblocks.blocks.variants.BlockSlope;
import tk.eichler.carpentersblocks.registry.helper.RegistryHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Registers all mod blocks, their ItemBlocks and their TileEntities
 * in {@link net.minecraftforge.fml.common.registry.GameRegistry}.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockRegistry implements BaseRegistry {

    static final List<BlockWrapper> ALL_BLOCKS = ImmutableList.of(
            BlockCuboid.getInstance(),
            BlockSlope.getInstance()
    );
    public static final BlockRegistry INSTANCE = new BlockRegistry();


    @Override
    public boolean receivesEvents() {
        return true;
    }

    @Override
    public void onPreInit() {
        registerBlocks();
        registerTileEntities();
    }

    private void registerBlocks() {
        for (BlockWrapper block : ALL_BLOCKS) {
            RegistryHelper.registerBlock(block);
        }
    }

    private void registerTileEntities() {
        for (BlockWrapper block : BlockRegistry.ALL_BLOCKS) {
            RegistryHelper.registerTileEntity(block);
        }
    }
}
