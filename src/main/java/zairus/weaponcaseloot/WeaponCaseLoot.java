package zairus.weaponcaseloot;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = WCLConstants.MOD_ID, name = WCLConstants.MOD_NAME, version = WCLConstants.MOD_VERSION)
public class WeaponCaseLoot
{
	public static Logger logger;
	
	@Mod.Instance(WCLConstants.MOD_ID)
	public static WeaponCaseLoot instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		;
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		;
	}
}
