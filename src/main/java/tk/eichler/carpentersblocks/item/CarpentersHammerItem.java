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

package tk.eichler.carpentersblocks.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.eichler.carpentersblocks.Constants;
import tk.eichler.carpentersblocks.util.BlockHelper;
import tk.eichler.carpentersblocks.util.CarpentersCreativeTab;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CarpentersHammerItem extends Item implements CarpentersItem {

    private static final String REGISTER_NAME = "hammer";
    private static final CarpentersHammerItem INSTANCE = new CarpentersHammerItem();

    public CarpentersHammerItem() {
        setCreativeTab(CarpentersCreativeTab.get());
        setRegistryName(REGISTER_NAME);
        setUnlocalizedName(REGISTER_NAME);

        setMaxStackSize(1);
        setMaxDamage(300);
    }

    public static CarpentersHammerItem get() {
        return INSTANCE;
    }

    @Override
    public String getUnlocalizedName() {
        return MessageFormat.format("{0}.{1}", Constants.MOD_ID, super.getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(final ItemStack stack) {
        return getUnlocalizedName();
    }

    /**
     * Returns strength, which is 0 for carpenter's blocks. Part of a @workaround.
     *
     * @param stack Current item stack.
     * @param state IBlockState of the block this item is used on.
     * @return Nearly instant block breaking strength, if used on a {@link tk.eichler.carpentersblocks.blocks.BlockWrapper},
     * else returns the default strength.
     */
    @Override
    public float getStrVsBlock(final ItemStack stack, final IBlockState state) {
        if (BlockHelper.isCarpentersBlock(state.getBlock())) {
            return 0;
        }

        return super.getStrVsBlock(stack, state);
    }

    @Override
    public EnumActionResult onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final Block usedBlock = worldIn.getBlockState(pos).getBlock();

        if (BlockHelper.isCarpentersBlock(usedBlock)) {
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public void onItemActuallyUsed(final ItemStack stack, final EntityLivingBase entity) {
        stack.attemptDamageItem(1, entity.getEntityWorld().rand);

        if ((entity instanceof EntityPlayer) && (stack.getItemDamage() >= this.getMaxDamage())) {
            entity.setHeldItem(EnumHand.MAIN_HAND, null);

            entity.getEntityWorld().playSound(
                    (EntityPlayer) entity,
                    entity.getPosition(),
                    SoundEvents.ENTITY_ITEM_BREAK,
                    SoundCategory.PLAYERS,
                    0.3F, 0.6F
                    );
        }
    }
}
