package zairus.weaponcaseloot.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.weaponcaseloot.WCLConstants;

public class WCLSoundEvents
{
	public static SoundEvent weapon_loop;
	public static SoundEvent blade;
	public static SoundEvent power;
	public static SoundEvent draw;
	public static SoundEvent bow_open;
	public static SoundEvent case_open;
	
	public static SoundEvent registerSound(ResourceLocation location)
	{
		SoundEvent sound = new SoundEvent(location).setRegistryName(location);
		GameRegistry.register(sound);
		
		return sound;
	}
	
	private static SoundEvent registerSound(String location)
	{
		return registerSound(new ResourceLocation(WCLConstants.MOD_ID, location));
	}
	
	static
	{
		weapon_loop = registerSound("weapon_loop");
		blade = registerSound("blade");
		power = registerSound("power");
		draw = registerSound("draw");
		bow_open = registerSound("bow_open");
		case_open = registerSound("case_open");
	}
}
