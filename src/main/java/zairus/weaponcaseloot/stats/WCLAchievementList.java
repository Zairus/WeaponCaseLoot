package zairus.weaponcaseloot.stats;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.item.WCLItems;

public class WCLAchievementList
{
	public static Achievement legendary = new Achievement(WCLConstants.MOD_ID + ":" + "achievement.legendary", "legendary", 0, 0, WCLItems.sword, (Achievement)null).initIndependentStat().registerStat();
	
	public static AchievementPage WCLPage1 = new AchievementPage("Weapon Case Loot", legendary);
	
	public static void initPages()
	{
		AchievementPage.registerAchievementPage(WCLPage1);
	}
}
