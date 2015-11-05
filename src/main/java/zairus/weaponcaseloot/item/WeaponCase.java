package zairus.weaponcaseloot.item;

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
		int lootPos = world.rand.nextInt(WCLConstants.totalSwords);
		
		NBTTagCompound weaponData = new NBTTagCompound();
		
		int state_value = world.rand.nextInt(5);
		int rarity_value = 0; //world.rand.nextInt(4); // 0 common, 1 uncommon, 2 rare, 3 legendary
		
		weaponData.setString(WCLConstants.KEY_STATE, states[state_value]);
		weaponData.setString(WCLConstants.KEY_RARITY, rarity[rarity_value]);
		weaponData.setInteger(WCLConstants.KEY_WEAPONINDEX, lootPos);
		weaponData.setInteger(WCLConstants.KEY_WEAPON_DURABILITY, getWeaponDurability(state_value, rarity_value));
		weaponData.setFloat(WCLConstants.KEY_WEAPON_ATTACKDAMAGE, getWeaponDamage(state_value, rarity_value));
		//ContainerEnchantment
		weapon.setTagCompound(weaponData);
		weapon.setStackDisplayName(states[state_value] + " " + rarity[rarity_value] + " " + WCLConfig.sword_names[lootPos]);
		
		player.inventory.decrStackSize(player.inventory.currentItem, 1);
		
		if (!player.inventory.addItemStackToInventory(weapon))
		{
			world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, weapon));
		}
		
		return stack;
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
