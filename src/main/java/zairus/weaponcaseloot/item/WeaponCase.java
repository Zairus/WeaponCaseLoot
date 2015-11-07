package zairus.weaponcaseloot.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;

public class WeaponCase extends WCLItem
{
	private final String[] states = {"Broken", "Crude", "Good", "Flawless", "Perfect"};
	private final String[] rarity = {"Common", "Uncommon", "Rare", "Legendary"};
	
	public WeaponCase()
	{
		this.maxStackSize = 16;
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ItemStack weapon = new ItemStack(WCLItems.sword, 1);
		
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
		
		int swordId = getSwordIdFromRarity(level); // itemRand.nextInt(WCLConstants.totalSwords);
		
		String[] levels_colors = new String[4];
		levels_colors[0] = WCLConstants.colorChar + "b";
		levels_colors[1] = WCLConstants.colorChar + "a";
		levels_colors[2] = WCLConstants.colorChar + "e";
		levels_colors[3] = WCLConstants.colorChar + "6";
		
		world.playSoundAtEntity(player, "weaponcaseloot:case_open", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
		
		NBTTagCompound weaponData = new NBTTagCompound();
		
		int state_value = world.rand.nextInt(5);
		int rarity_value = WCLConfig.sword_rarity[swordId];
		
		weaponData.setString(WCLConstants.KEY_STATE, states[state_value]);
		weaponData.setString(WCLConstants.KEY_RARITY, rarity[rarity_value]);
		weaponData.setFloat(WCLConstants.KEY_LOOPSOUNDTIMER, 100.0F);
		weaponData.setInteger(WCLConstants.KEY_WEAPONINDEX, swordId);
		weaponData.setInteger(WCLConstants.KEY_WEAPON_DURABILITY, getWeaponDurability(state_value, rarity_value));
		weaponData.setFloat(WCLConstants.KEY_WEAPON_ATTACKDAMAGE, getWeaponDamage(state_value, rarity_value));
		
		weapon.setTagCompound(weaponData);
		weapon.setStackDisplayName(levels_colors[rarity_value] + WCLConfig.sword_names[swordId]);
		
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
	
	private int getWeaponDurability(int wState, int wRarity)
	{
		int durability = 0;
		
		if (wRarity == 0)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_common_broken;
				break;
			case 1:
				durability = WCLConfig.durability_common_crude;
				break;
			case 2:
				durability = WCLConfig.durability_common_good;
				break;
			case 3:
				durability = WCLConfig.durability_common_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_common_perfect;
				break;
			}
		} else if (wRarity == 1)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_uncommon_broken;
				break;
			case 1:
				durability = WCLConfig.durability_uncommon_crude;
				break;
			case 2:
				durability = WCLConfig.durability_uncommon_good;
				break;
			case 3:
				durability = WCLConfig.durability_uncommon_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_uncommon_perfect;
				break;
			}
		} else if (wRarity == 2)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_rare_broken;
				break;
			case 1:
				durability = WCLConfig.durability_rare_crude;
				break;
			case 2:
				durability = WCLConfig.durability_rare_good;
				break;
			case 3:
				durability = WCLConfig.durability_rare_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_rare_perfect;
				break;
			}
		} else if (wRarity == 3)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_legendary_broken;
				break;
			case 1:
				durability = WCLConfig.durability_legendary_crude;
				break;
			case 2:
				durability = WCLConfig.durability_legendary_good;
				break;
			case 3:
				durability = WCLConfig.durability_legendary_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_legendary_perfect;
				break;
			}
		}
		
		return durability;
	}
	
	private float getWeaponDamage(int wState, int wRarity)
	{
		float damage = 0;
		
		if (wRarity == 0)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_common_broken;
				break;
			case 1:
				damage = WCLConfig.damage_common_crude;
				break;
			case 2:
				damage = WCLConfig.damage_common_good;
				break;
			case 3:
				damage = WCLConfig.damage_common_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_common_perfect;
				break;
			}
		} else if (wRarity == 1)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_uncommon_broken;
				break;
			case 1:
				damage = WCLConfig.damage_uncommon_crude;
				break;
			case 2:
				damage = WCLConfig.damage_uncommon_good;
				break;
			case 3:
				damage = WCLConfig.damage_uncommon_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_uncommon_perfect;
				break;
			}
		} else if (wRarity == 2)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_rare_broken;
				break;
			case 1:
				damage = WCLConfig.damage_rare_crude;
				break;
			case 2:
				damage = WCLConfig.damage_rare_good;
				break;
			case 3:
				damage = WCLConfig.damage_rare_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_rare_perfect;
				break;
			}
		} else if (wRarity == 3)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_legendary_broken;
				break;
			case 1:
				damage = WCLConfig.damage_legendary_crude;
				break;
			case 2:
				damage = WCLConfig.damage_legendary_good;
				break;
			case 3:
				damage = WCLConfig.damage_legendary_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_legendary_perfect;
				break;
			}
		}
		
		return damage;
	}
}
