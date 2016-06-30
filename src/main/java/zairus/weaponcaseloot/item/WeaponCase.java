package zairus.weaponcaseloot.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.sounds.WCLSoundEvents;

public class WeaponCase extends WCLItem
{
	public static int editions = 2;
	private final String name = "weaponcase";
	
	public WeaponCase()
	{
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		setHasSubtypes(true);
		maxStackSize = 16;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage() + 1, 0, editions);
		return super.getUnlocalizedName() + "" + i;
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List list)
    {
		for (int i = 0; i < editions; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		int chance = itemRand.nextInt(100);
		int level;
		
		if (chance < 70)
		{
			level = 0;
		} else if (chance < 95) {
			level = 1;
		} else if (chance < 99) {
			level = 2;
		} else {
			level = 3;
		}
		
		int swordId = getSwordIdFromRarity(stack, level);
		int quality = world.rand.nextInt(5);
		
		ItemStack weapon = ((WeaponSword)WCLItems.sword).getSwordFromId(swordId, quality);
		
		world.playSound(
				(EntityPlayer)null, 
				player.posX, 
				player.posY, 
				player.posZ, 
				WCLSoundEvents.case_open, 
				SoundCategory.NEUTRAL, 
				1.0F, 
				1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
		
		player.inventory.decrStackSize(player.inventory.currentItem, 1);
		
		if (!player.inventory.addItemStackToInventory(weapon))
		{
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, weapon));
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	private int getSwordIdFromRarity(ItemStack stack, int rarity)
	{
		int id = 0;
		
		List<List<Integer>> r = new ArrayList<List<Integer>>();
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		
		int edition = stack.getItemDamage();
		
		//WCLConstants.totalSwords
		for (int i = 0 + (edition * 12); i < 12 * (edition + 1); ++i)
		{
			r.get(WCLConfig.sword_rarity[i]).add(i);
		}
		
		id = r.get(rarity).get(itemRand.nextInt(r.get(rarity).size()));
		
		return id;
	}
}
