package zairus.weaponcaseloot;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.weaponcaseloot.item.WCLItems;
import zairus.weaponcaseloot.proxy.CommonProxy;

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
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WeaponCaseLoot.proxy.init(event);
		
		WCLItems.register();
		WCLItems.addLoot();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		WeaponCaseLoot.proxy.postInit(event);
	}
}
