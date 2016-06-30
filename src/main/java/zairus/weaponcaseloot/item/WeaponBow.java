package zairus.weaponcaseloot.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.WeaponCaseLoot;

public class WeaponBow extends ItemBow
{
	public static final String[] bowPullIconNameArray = new String[] {"", "_pulling_0", "_pulling_1", "_pulling_2"};
	
	public WeaponBow()
	{
		this.setCreativeTab(WeaponCaseLoot.weaponCaseLootTab);
		this.setFull3D();
		this.setMaxStackSize(1);
	}
	
	public WeaponBow setDurability(int durability)
	{
		this.setMaxDamage(durability);
		return this;
	}
	
	@Override
	public WeaponBow setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		return this;
	}
	
	@Override
	public int getMaxDamage()
	{
		return super.getMaxDamage();
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		int maxDamage = this.getMaxDamage();
		
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(WCLConstants.KEY_WEAPON_DURABILITY))
			{
				maxDamage = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPON_DURABILITY);
			}
		}
		
		return maxDamage;
	}
	
	public static float getDrawSpeed(ItemStack stack)
	{
		float drawSpeed = 1.0f;
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(WCLConstants.KEY_WEAPON_SPEED))
			{
				drawSpeed = tag.getFloat(WCLConstants.KEY_WEAPON_SPEED);
			}
		}
		
		return drawSpeed;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useCount)
	{
		int j = this.getMaxItemUseDuration(stack) - useCount;
		
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
		MinecraftForge.EVENT_BUS.post(event);
		
		if (event.isCanceled())
		{
			return;
		}
		
		j = event.charge;
		
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		
		if (flag || player.inventory.hasItem(Items.arrow))
		{
			float f = (float)j / 25.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			
			float drawSpeed = getDrawSpeed(stack);
			
			f *= drawSpeed;
			
			if ((double)f < 0.1D)
			{
				return;
			}
			
			if (f > 1.0F)
			{
				f = 1.0F;
			}
			
			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			int m = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
			
			int s = 0;
			
			NBTTagCompound tag = stack.getTagCompound();
			
			if (tag.hasKey(WCLConstants.KEY_WEAPON_MULTI))
			{
				s = tag.getInteger(WCLConstants.KEY_WEAPON_MULTI);
			}
			
			if (s == 0)
				s = 1;
			
			for (int i = 0; i < s; ++i)
			{
				EntityArrow entityarrow = new EntityArrow(world, player, f * 2.0F);
				
				entityarrow.setThrowableHeading(
						entityarrow.motionX
						, entityarrow.motionY
						, entityarrow.motionZ
						, (f * 2.0F) + itemRand.nextFloat()
						, 3.0F);
				
				if (f == 1.0F)
					entityarrow.setIsCritical(true);
				if (k > 0)
					entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
				if (l > 0)
					entityarrow.setKnockbackStrength(l + 1);
				if (m > 0)
					entityarrow.setFire(100);
				if (flag || i > 0)
					entityarrow.canBePickedUp = 2;
				
				if (!world.isRemote)
					world.spawnEntityInWorld(entityarrow);
			}
			
			if (!world.isRemote)
				world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			
			if (!flag)
				player.inventory.consumeInventoryItem(Items.arrow);
			
			if (!player.capabilities.isCreativeMode)
				stack.damageItem(1, player);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ArrowNockEvent event = new ArrowNockEvent(player, stack);
		MinecraftForge.EVENT_BUS.post(event);
		
		if (event.isCanceled())
		{
			return event.result;
		}
		
		if(player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow))
		{
			world.playSoundAtEntity(
					player
					, WCLConstants.MOD_ID + ":draw"
					, 1.0F
					, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);
			
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		
		return stack;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
    {
		NBTTagCompound tag = stack.getTagCompound();
		
		if (tag != null && !(tag.hasKey("temp_looping")))
		{
			list.add("Third edition Bow.");
			
			if (tag.hasKey(WCLConstants.KEY_STATE))
				list.add("Quality: " + tag.getString(WCLConstants.KEY_STATE));
			
			if (tag.hasKey(WCLConstants.KEY_RARITY))
				list.add("Rarity: " + tag.getString(WCLConstants.KEY_RARITY));
			
			if (tag.hasKey(WCLConstants.KEY_WEAPON_SPEED))
				list.add("Draw speed: " + tag.getFloat(WCLConstants.KEY_WEAPON_SPEED));
			
			if (tag.hasKey(WCLConstants.KEY_WEAPON_MULTI))
				list.add("Multi: " + tag.getInteger(WCLConstants.KEY_WEAPON_MULTI));
		}
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return Item.ToolMaterial.GOLD.getEnchantability();
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i1, boolean b1)
	{
		stack = WCLItem.correctNameColor(stack);
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(WCLConstants.KEY_LOOPSOUNDTIMER))
				stack = WCLItem.loop(stack, "Weapon Bow", "bow_open", WCLConfig.bow_rarity, world, entity, 0, 12);
		}
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List list)
    {
		for (int i = 0; i < 12; ++i)
		{
			for (int q = 0; q < 5; ++q)
			{
				list.add(getFromId(i, q));
			}
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public net.minecraft.client.resources.model.ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
    {
		ModelResourceLocation resourceLocation = null; //new ModelResourceLocation(WCLConstants.MOD_ID + ":weaponbow_1", "inventory");
		
		return resourceLocation;
    }
	
	public ItemStack getFromId(int id, int quality)
	{
		ItemStack stack = new ItemStack(WCLItems.bow, 1);
		int rarity = WCLConfig.bow_rarity[id];
		NBTTagCompound weaponData = new NBTTagCompound();
		
		weaponData.setString(WCLConstants.KEY_STATE, WCLConstants.weapon_quality[quality]);
		weaponData.setString(WCLConstants.KEY_RARITY, WCLConstants.weapon_rarity[rarity]);
		weaponData.setFloat(WCLConstants.KEY_LOOPSOUNDTIMER, 100.0F);
		weaponData.setInteger(WCLConstants.KEY_WEAPONINDEX, id);
		weaponData.setInteger(WCLConstants.KEY_WEAPON_DURABILITY, WeaponSword.getWeaponDurability(quality, rarity));
		weaponData.setFloat(WCLConstants.KEY_WEAPON_SPEED, WCLConfig.bow_drawspeed[id]);
		
		if (rarity > 1)
		{
			if (itemRand.nextInt(3 + rarity) > 1)
			{
				weaponData.setInteger(WCLConstants.KEY_WEAPON_MULTI, itemRand.nextInt(3) + 2);
			}
		}
		
		stack.setTagCompound(weaponData);
		stack.setStackDisplayName(getNameFromId(id));
		
		return stack;
	}
	
	public String getNameFromId(int id)
	{
		String name;
		
		int rarity = WCLConfig.bow_rarity[id];
		
		String[] levels_colors = new String[4];
		levels_colors[0] = WCLConstants.colorChar + "b";
		levels_colors[1] = WCLConstants.colorChar + "a";
		levels_colors[2] = WCLConstants.colorChar + "e";
		levels_colors[3] = WCLConstants.colorChar + "6";
		
		name = levels_colors[rarity] + WCLConfig.bow_names[id];
		
		return name;
	}
	
	public boolean updatesFOV()
	{
		return true;
	}
	
	public int getFOVDuration(ItemStack stack)
	{
		float drawSpeed = getDrawSpeed(stack);
		
		return (int)(10000.0f / drawSpeed);
	}
	
	public float getFOVValue(ItemStack stack)
	{
		float drawSpeed = 1.0f;
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(WCLConstants.KEY_WEAPON_SPEED))
			{
				drawSpeed = tag.getFloat(WCLConstants.KEY_WEAPON_SPEED);
			}
		}
		
		return 0.4f * (drawSpeed * 1.3f);
	}
	
	public float getFOVSpeedFactor(ItemStack stack)
	{
		return 350.0f;
	}
}
