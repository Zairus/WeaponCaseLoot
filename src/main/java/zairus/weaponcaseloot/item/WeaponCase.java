package zairus.weaponcaseloot.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;

public class WeaponCase extends WCLItem
{
	public WeaponCase()
	{
		this.maxStackSize = 16;
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
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
		
		int swordId = getSwordIdFromRarity(level);
		int quality = world.rand.nextInt(5);
		
		ItemStack weapon = ((WeaponSword)WCLItems.sword).getSwordFromId(swordId, quality);
		
		world.playSoundAtEntity(player, "weaponcaseloot:case_open", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
		
		player.inventory.decrStackSize(player.inventory.currentItem, 1);
		
		if (!player.inventory.addItemStackToInventory(weapon))
		{
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, weapon));
		}
		
		return stack;
	}
	
	private int getSwordIdFromRarity(int rarity)
	{
		int id = 0;
		
		List<List<Integer>> r = new ArrayList<List<Integer>>();
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		
		for (int i = 0; i < WCLConstants.totalSwords; ++i)
		{
			r.get(WCLConfig.sword_rarity[i]).add(i);
		}
		
		id = r.get(rarity).get(itemRand.nextInt(r.get(rarity).size()));
		
		return id;
	}
}
