package zairus.weaponcaseloot.item;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;

public class WCLItems
{
	public static WCLItem weaponcase;
	
	public static WCLItem sword;
	
	static
	{
		weaponcase = new WeaponCase().setUnlocalizedName("weaponcase").setTextureName(WCLConstants.MOD_ID + ":weaponcase");
		
		sword = new WeaponSword().setDurability(WCLConfig.durability_common_broken).setAttackDamage(WCLConfig.damage_common_broken).setUnlocalizedName("weaponsword").setTextureName(WCLConstants.MOD_ID + ":weaponsword_1");
	}
	
	public static final void register()
	{
		GameRegistry.registerItem(weaponcase, weaponcase.getUnlocalizedName());
		GameRegistry.registerItem(sword, ((WeaponSword)sword).getUnlocalizedName());
	}
	
	public static final void addLoot()
	{
		;
	}
	
	public static List<WCLItem> getWeaponLoot()
	{
		List<WCLItem> lootList = new ArrayList<WCLItem>();
		
		lootList.add(sword);
		
		return lootList;
	}
}
