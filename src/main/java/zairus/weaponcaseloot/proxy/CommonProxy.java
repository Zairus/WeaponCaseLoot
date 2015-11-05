package zairus.weaponcaseloot.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import zairus.weaponcaseloot.item.WCLItems;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	public void init(FMLInitializationEvent e)
	{
		WCLItems.register();
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
}
