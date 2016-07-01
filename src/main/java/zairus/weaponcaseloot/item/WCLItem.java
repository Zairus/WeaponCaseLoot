package zairus.weaponcaseloot.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.sound.WCLSoundEvents;

public class WCLItem extends Item
{
	@Override
	public WCLItem setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		return this;
	}
	
	public static ItemStack correctNameColor(ItemStack stack)
	{
		if (stack.hasDisplayName())
		{
			String name = stack.getDisplayName();
			
			char fChar = name.charAt(0);
			if (fChar != WCLConstants.colorChar && (fChar == 'b' || fChar == 'a' || fChar == 'e' || fChar == '6'))
				stack.setStackDisplayName(WCLConstants.colorChar + name);
		}
		
		return stack;
	}
	
	public static ItemStack loop(ItemStack stack, String loopName, SoundEvent soundEvent, int[] rarity_array, World world, Entity entity, int iconMin, int iconMax)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			
			if (tag.hasKey(WCLConstants.KEY_LOOPSOUNDTIMER))
			{
				tag.setInteger("temp_looping", 1);
				
				float t = tag.getFloat(WCLConstants.KEY_LOOPSOUNDTIMER);
				int iconIndex = tag.getInteger(WCLConstants.KEY_WEAPONINDEX);
				
				if (!tag.hasKey("temp_index"))
				{
					tag.setInteger("temp_index", iconIndex);
					tag.setString("temp_name", stack.getDisplayName());
					stack.setStackDisplayName(loopName);
				}
				
				if (t < 95.0F)
					world.playSound(
							(EntityPlayer)null, 
							entity.posX, 
							entity.posY, 
							entity.posZ, 
							WCLSoundEvents.weapon_loop, 
							SoundCategory.NEUTRAL, 
							1.0F, 
							1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
				
				--t;
				
				tag.setFloat(WCLConstants.KEY_LOOPSOUNDTIMER, t);
				tag.setInteger(WCLConstants.KEY_WEAPONINDEX, iconMin + itemRand.nextInt(iconMax - iconMin));
				
				if (t <= 10.0F)
				{
					/*
					int rarity = rarity_array[tag.getInteger("temp_index")];
					
					if (rarity == 3)
						((EntityPlayer)entity).triggerAchievement(WCLAchievementList.legendary);
					*/
					tag.removeTag(WCLConstants.KEY_LOOPSOUNDTIMER);
					tag.setInteger(WCLConstants.KEY_WEAPONINDEX, tag.getInteger("temp_index"));
					tag.removeTag("temp_index");
					stack.setStackDisplayName(tag.getString("temp_name"));
					tag.removeTag("temp_name");
					tag.removeTag("temp_looping");
					world.playSound(
							(EntityPlayer)null, 
							entity.posX, 
							entity.posY, 
							entity.posZ, 
							soundEvent, 
							SoundCategory.NEUTRAL, 
							1.0F, 
							1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
				}
			}
		}
		
		return stack;
	}
}
