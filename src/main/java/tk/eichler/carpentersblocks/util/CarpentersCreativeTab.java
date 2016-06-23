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

package tk.eichler.carpentersblocks.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.blocks.variants.BlockCuboid;

import javax.annotation.Nonnull;

public final class CarpentersCreativeTab extends CreativeTabs {

    private static CarpentersCreativeTab instance = new CarpentersCreativeTab();

    private CarpentersCreativeTab() {
        super(Constants.MOD_ID);
    }

    @Nonnull
    public static CarpentersCreativeTab get() {
        return instance;
    }

    @Override
    @Nonnull
    public Item getTabIconItem() {
        return BlockCuboid.getInstance().getItemBlock(); //@TODO: replace by carpenter's hammer.
    }
}
