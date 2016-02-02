package zairus.weaponcaseloot;

public class WCLConstants
{
	public static final String MOD_ID = "weaponcaseloot";
	public static final String MOD_NAME = "Weapon Case Loot";
	public static final String MOD_VERSION = "1.1.8";
	
	public static final String MOD_COMMON_PROXY = "zairus.weaponcaseloot.proxy.CommonProxy";
	public static final String MOD_CLIENT_PROXY = "zairus.weaponcaseloot.proxy.ClientProxy";
	
	public static final String KEY_WEAPON_DURABILITY = "weapon_durability";
	public static final String KEY_WEAPON_ATTACKDAMAGE = "weapon_damage";
	public static final String KEY_RARITY = "weapon_rarity";
	public static final String KEY_STATE = "weapon_state";
	public static final String KEY_WEAPONINDEX = "weapon_index";
	public static final String KEY_LOOPSOUNDTIMER = "weapon_looptimer";
	
	public static final int totalSwords = 24;
	
	public static final char colorChar = '\u00A7';
	
	public static final String[] weapon_rarity = {"Common", "Uncommon", "Rare", "Legendary"};
	public static final String[] weapon_quality = {"Broken", "Crude", "Good", "Flawless", "Perfect"};
}
