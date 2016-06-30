package zairus.weaponcaseloot.item;

import zairus.weaponcaseloot.WeaponCaseLoot;

public class WCLItems
{
	public static WCLItem weaponcase;
	public static WCLItemWeapon sword;
	
	static
	{
		weaponcase = new WeaponCase();
		sword = new WeaponSword();
	}
	
	public static final void register()
	{
		WeaponCaseLoot.proxy.registerItem(weaponcase, "weaponcase1", 0, true);
		WeaponCaseLoot.proxy.registerItem(weaponcase, "weaponcase2", 1, true);
		
		WeaponCaseLoot.proxy.registerSwordItem(sword, "weaponsword");
	}
	
	public static final void addLoot()
	{
		/*
		for (int i = 0; i < WeaponCase.editions; ++i)
		{
			ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.NETHER_FORTRESS, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
		}
		*/
	}
}
