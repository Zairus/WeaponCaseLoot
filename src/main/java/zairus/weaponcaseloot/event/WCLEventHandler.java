package zairus.weaponcaseloot.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import zairus.weaponcaseloot.item.WeaponBow;

public class WCLEventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFOVUpdate(FOVUpdateEvent event)
	{
		if (!event.entity.isUsingItem())
			return;
		
		ItemStack stack = event.entity.getItemInUse();
		
		if (stack.getItem() instanceof WeaponBow)
		{
			WeaponBow item = (WeaponBow)stack.getItem();
			
			if (item.updatesFOV())
			{
				event.newfov = event.fov / (event.fov + (item.getFOVValue(stack) * getItemInUsePercentaje(event.entity, item.getFOVSpeedFactor(stack))));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private float getItemInUsePercentaje(EntityPlayerSP player, float speedFactor)
	{
		if (player.getItemInUse().getItem() instanceof WeaponBow)
		{
			WeaponBow bow = (WeaponBow)player.getItemInUse().getItem();
			
			float maxUse = bow.getFOVDuration(player.getItemInUse());
			float curUse = (float)player.getItemInUseCount();
			float percent = 0.0f;
			
			percent = maxUse - (maxUse - (bow.getMaxItemUseDuration(player.getItemInUse()) - curUse));
			percent *= (speedFactor * ((percent > 100)? 1.0f : percent / 100.0f));
			percent = percent / maxUse;
			percent *= 1 +  percent;
			
			return (percent > 1.0f)? 1.0f : percent;
		}
		else
		{
			return 1.0f;
		}
	}
}
