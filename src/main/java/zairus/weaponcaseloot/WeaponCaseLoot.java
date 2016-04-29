package zairus.weaponcaseloot;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import zairus.weaponcaseloot.proxy.CommonProxy;
import zairus.weaponcaseloot.states.WCLAchievementList;

@Mod(modid = WCLConstants.MOD_ID, name = WCLConstants.MOD_NAME, version = WCLConstants.MOD_VERSION)
public class WeaponCaseLoot
{
	public static Logger logger;
	
	@SidedProxy(clientSide = WCLConstants.MOD_CLIENT_PROXY, serverSide = WCLConstants.MOD_COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.Instance(WCLConstants.MOD_ID)
	public static WeaponCaseLoot instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		WeaponCaseLoot.proxy.preInit(event);
		
		WCLConfig.init(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WeaponCaseLoot.proxy.init(event);
		
		WCLAchievementList.initPages();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		WeaponCaseLoot.proxy.postInit(event);
	}
	
	public static boolean baublesExist()
	{
		boolean exists = false;
		
		try {
			Class.forName("baubles.common.Baubles");
			exists = true;
		}
		catch (Exception e)
		{
			exists = false;
		}
		
		return exists;
	}
}
