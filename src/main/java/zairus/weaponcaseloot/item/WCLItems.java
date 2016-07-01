package zairus.weaponcaseloot.item;

import net.minecraft.item.Item;
import zairus.weaponcaseloot.WeaponCaseLoot;

public class WCLItems
{
	public static Item weaponcase;
	public static Item sword;
	public static Item bow;
	
	public static Item bauble;
	
	static
	{
		weaponcase = new WeaponCase().setRegistryName("weaponcase").setUnlocalizedName("weaponcase");
		sword = new WeaponSword().setRegistryName("weaponsword").setUnlocalizedName("weaponsword");
		bow = new WeaponBow().setRegistryName("weaponbow").setUnlocalizedName("weaponbow");
		
		if (WeaponCaseLoot.baublesExist())
		{
			bauble = new WCLItemBauble().setRegistryName("baublering").setUnlocalizedName("baublering");
		}
	}
	
	public static final void register()
	{
		WeaponCaseLoot.proxy.registerItem(weaponcase, "weaponcase1", 0, true);
		WeaponCaseLoot.proxy.registerItem(weaponcase, "weaponcase2", 1, true);
		WeaponCaseLoot.proxy.registerItem(weaponcase, "weaponcase3", 2, true);
		WeaponCaseLoot.proxy.registerItem(weaponcase, "weaponcase4", 3, true);
		
		WeaponCaseLoot.proxy.registerSwordItem((WCLItemWeapon)sword, "weaponsword");
		WeaponCaseLoot.proxy.registerBowItem((WeaponBow)bow, "weaponbow");
		
		if (WeaponCaseLoot.baublesExist())
		{
			WeaponCaseLoot.proxy.registerBaubleItem((WCLItemBauble)bauble, "baublering");
		}
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
