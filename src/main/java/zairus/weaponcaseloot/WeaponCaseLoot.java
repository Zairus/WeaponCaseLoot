package zairus.weaponcaseloot;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.weaponcaseloot.event.WCLEventHandler;
import zairus.weaponcaseloot.item.WCLItems;
import zairus.weaponcaseloot.proxy.CommonProxy;
import zairus.weaponcaseloot.stats.WCLAchievementList;

@Mod(modid = WCLConstants.MOD_ID, name = WCLConstants.MOD_NAME, version = WCLConstants.MOD_VERSION)
public class WeaponCaseLoot
{
	public static Logger logger;
	
	@SidedProxy(clientSide = WCLConstants.MOD_CLIENT_PROXY, serverSide = WCLConstants.MOD_COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.Instance(WCLConstants.MOD_ID)
	public static WeaponCaseLoot instance;
	
	public static CreativeTabs weaponCaseLootTab = new CreativeTabs("weaponCaseLoot") {
		@Override
		public Item getTabIconItem()
		{
			return WCLItems.weaponcase;
		}
	};
	
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
		
		WCLItems.register();
		WCLItems.addLoot();
		
		WCLEventHandler eventHandler = new WCLEventHandler();
		
		MinecraftForge.EVENT_BUS.register(eventHandler);
		
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
