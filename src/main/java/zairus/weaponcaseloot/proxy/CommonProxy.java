package zairus.weaponcaseloot.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.weaponcaseloot.item.WCLItemBauble;
import zairus.weaponcaseloot.item.WCLItemWeapon;
import zairus.weaponcaseloot.item.WeaponBow;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	public void init(FMLInitializationEvent e)
	{
		;
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	public void registerItem(Item item, String name, int meta, boolean model)
	{
		if (meta == 0)
			GameRegistry.register(item);
	}
	
	public void registerSwordItem(WCLItemWeapon sword, String name)
	{
		GameRegistry.register(sword);
	}
	
	public void registerBowItem(WeaponBow bow, String name)
	{
		GameRegistry.register(bow);
	}
	
	public void registerBaubleItem(WCLItemBauble bauble, String name)
	{
		GameRegistry.register(bauble);
	}
}
