package zairus.weaponcaseloot.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zairus.weaponcaseloot.WCLConstants;

public class WCLSoundEvents
{
	public static SoundEvent weapon_loop;
	public static SoundEvent blade;
	public static SoundEvent power;
	public static SoundEvent draw;
	public static SoundEvent bow_open;
	public static SoundEvent case_open;
	
	private static int lastSoundId = -1;
	
	private static int getLastID()
	{
		int lastId = 0;
		
		for (SoundEvent sound : SoundEvent.REGISTRY)
		{
			lastId = Math.max(lastId, SoundEvent.REGISTRY.getIDForObject(sound));
		}
		
		return lastId;
	}
	
	public static void setLastID()
	{
		lastSoundId = getLastID() + 1;
	}
	
	public static SoundEvent registerSound(ResourceLocation location)
	{
		SoundEvent.REGISTRY.register(lastSoundId, location, new SoundEvent(location));
		
		return SoundEvent.REGISTRY.getObjectById(lastSoundId++);
	}
	
	private static SoundEvent registerSound(String location)
	{
		return registerSound(new ResourceLocation(WCLConstants.MOD_ID, location));
	}
	
	static
	{
		setLastID();
		
		weapon_loop = registerSound("weapon_loop");
		blade = registerSound("blade");
		power = registerSound("power");
		draw = registerSound("draw");
		bow_open = registerSound("bow_open");
		case_open = registerSound("case_open");
	}
}
