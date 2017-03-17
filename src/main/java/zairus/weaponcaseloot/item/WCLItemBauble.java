package zairus.weaponcaseloot.item;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.WeaponCaseLoot;
import zairus.weaponcaseloot.effects.WCLEffectHandler;
import zairus.weaponcaseloot.effects.WCLEffectHandler.effectType;
import zairus.weaponcaseloot.sound.WCLSoundEvents;

@Optional.Interface( modid = "Baubles", iface = "baubles.api.IBauble" )
public class WCLItemBauble extends WCLItem implements IBauble
{
	public WCLItemBauble()
	{
		this.maxStackSize = 1;
		this.setUnlocalizedName("baublering");
		this.setCreativeTab(WeaponCaseLoot.weaponCaseLootTab);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i1, boolean b1)
	{
		stack = WCLItem.correctNameColor(stack);
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(WCLConstants.KEY_LOOPSOUNDTIMER))
				stack = WCLItem.loop(stack, "Case Ring", WCLSoundEvents.power, WCLConfig.ring_rarity, world, entity, 0, 12);
		}
		
		if (entity instanceof EntityLivingBase)
			wornTick(stack, (EntityLivingBase)entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, NonNullList<ItemStack> list)
    {
		for (int i = 0; i < 12; ++i)
		{
			list.add(getRingFromId(i, 4));
		}
    }
	
	public ItemStack getRingFromId(int ringId, int quality)
	{
		ItemStack ring = new ItemStack(WCLItems.bauble, 1, 0);
		int rarity = WCLConfig.ring_rarity[ringId];
		NBTTagCompound weaponData = new NBTTagCompound();
		
		weaponData.setString(WCLConstants.KEY_STATE, WCLConstants.weapon_quality[quality]);
		weaponData.setString(WCLConstants.KEY_RARITY, WCLConstants.weapon_rarity[rarity]);
		weaponData.setFloat(WCLConstants.KEY_LOOPSOUNDTIMER, 100.0F);
		weaponData.setInteger(WCLConstants.KEY_WEAPONINDEX, ringId);
		
		ring.setTagCompound(weaponData);
		ring.setStackDisplayName(getNameFromId(ringId));
		
		return ring;
	}
	
	public String getNameFromId(int ringId)
	{
		String name;
		
		int rarity = WCLConfig.ring_rarity[ringId];
		
		String[] levels_colors = new String[4];
		levels_colors[0] = WCLConstants.colorChar + "b";
		levels_colors[1] = WCLConstants.colorChar + "a";
		levels_colors[2] = WCLConstants.colorChar + "e";
		levels_colors[3] = WCLConstants.colorChar + "6";
		
		name = levels_colors[rarity] + WCLConfig.ring_names[ringId];
		
		return name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
    {
		NBTTagCompound tag = stack.getTagCompound();
		
		if (tag != null && !(tag.hasKey("temp_looping")))
		{
			list.add("Fourth edition case loot.");
			
			if (tag.hasKey(WCLConstants.KEY_RARITY))
				list.add("Rarity: " + tag.getString(WCLConstants.KEY_RARITY));
			
			list.add("Provides:");
			
			int id = tag.getInteger(WCLConstants.KEY_WEAPONINDEX);
			
			effectType[] effects = WCLConfig.ring_effects.get(id);
			
			for (int i = 0; i < effects.length; ++i)
			{
				list.add(effects[i].getDisplayName());
			}
		}
    }
	
	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase entity)
	{
		return true;
	}
	
	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase entity)
	{
		return true;
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack stack)
	{
		return BaubleType.RING;
	}
	
	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase entity)
	{
		;
	}
	
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase entity)
	{
		;
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity)
	{
		wornTick(stack, entity);
	}
	
	private void wornTick(ItemStack stack, EntityLivingBase entity)
	{
		if (entity instanceof EntityPlayer)
		{
			this.setEffect(stack, (EntityPlayer)entity, true);
		}
	}
	
	private void setEffect(ItemStack stack, EntityPlayer entity, boolean add)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(WCLConstants.KEY_WEAPONINDEX))
			{
				int id = tag.getInteger(WCLConstants.KEY_WEAPONINDEX);
				
				for (int i = 0; i < WCLConfig.ring_effects.get(id).length; ++i)
					WCLEffectHandler.instance.applyEffect((EntityPlayer)entity, WCLConfig.ring_effects.get(id)[i], add);
			}
		}
	}
}
