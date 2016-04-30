package zairus.weaponcaseloot.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.WeaponCaseLoot;

public class WCLItems
{
	public static WCLItem weaponcase;
	
	public static WCLItemWeapon sword;
	public static WeaponBow bow;
	
	public static WCLItem bauble;
	
	static
	{
		weaponcase = new WeaponCase().setUnlocalizedName("weaponcase").setTextureName(WCLConstants.MOD_ID + ":weaponcase");
		
		sword = new WeaponSword().setDurability(WCLConfig.durability_common_broken).setAttackDamage(WCLConfig.damage_common_broken).setUnlocalizedName("weaponsword").setTextureName(WCLConstants.MOD_ID + ":weaponsword_1");
		bow = new WeaponBow().setDurability(WCLConfig.durability_common_broken).setUnlocalizedName("weaponbow").setTextureName(WCLConstants.MOD_ID + ":weaponbow_1");
		
		if (WeaponCaseLoot.baublesExist())
		{
			bauble = new WCLItemBauble().setUnlocalizedName("bauble").setTextureName(WCLConstants.MOD_ID + ":baublering_1");
		}
	}
	
	public static final void register()
	{
		GameRegistry.registerItem(weaponcase, weaponcase.getUnlocalizedName());
		GameRegistry.registerItem(sword, sword.getUnlocalizedName());
		GameRegistry.registerItem(bow, bow.getUnlocalizedName());
		
		if (bauble != null)
		{
			GameRegistry.registerItem(bauble, bauble.getUnlocalizedName());
		}
	}
	
	public static final void addLoot()
	{
		for (int i = 0; i < WeaponCase.editions; ++i)
		{
			if (i == 3 && !WeaponCaseLoot.baublesExist())
				continue;
			
			ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
			ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(weaponcase, 1, i), 1, 5, 3));
		}
	}
}
