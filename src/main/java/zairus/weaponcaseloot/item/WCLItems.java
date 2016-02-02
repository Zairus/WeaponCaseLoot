package zairus.weaponcaseloot.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;

public class WCLItems
{
	public static WCLItem weaponcase;
	
	public static WCLItemWeapon sword;
	
	static
	{
		weaponcase = new WeaponCase().setUnlocalizedName("weaponcase").setTextureName(WCLConstants.MOD_ID + ":weaponcase");
		
		sword = new WeaponSword().setDurability(WCLConfig.durability_common_broken).setAttackDamage(WCLConfig.damage_common_broken).setUnlocalizedName("weaponsword").setTextureName(WCLConstants.MOD_ID + ":weaponsword_1");
	}
	
	public static final void register()
	{
		GameRegistry.registerItem(weaponcase, weaponcase.getUnlocalizedName());
		GameRegistry.registerItem(sword, sword.getUnlocalizedName());
	}
	
	public static final void addLoot()
	{
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
		}
	}
}
