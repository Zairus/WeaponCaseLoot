package zairus.weaponcaseloot.item;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.WeaponCaseLoot;

public class WeaponSword extends WCLItemWeapon
{
	private float swordDamage;
	private final Item.ToolMaterial swordMaterial;
	
	private IIcon[] icons;
	
	public WeaponSword()
	{
		this.swordMaterial = Item.ToolMaterial.EMERALD;
		this.maxStackSize = 1;
		
		this.setCreativeTab(WeaponCaseLoot.creativeTab);
	}
	
	public WeaponSword setDurability(int durability)
	{
		this.setMaxDamage(durability);
		return this;
	}
	
	public WeaponSword setAttackDamage(float damage)
	{
		this.swordDamage = damage;
		return this;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName();
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
	
	public float func_150931_i()
	{
		return this.swordMaterial.getDamageVsEntity();
	}
	
	public float func_150893_a(ItemStack stack, Block block)
	{
		if (block == Blocks.web)
		{
			return 15.0F;
		}
		else
		{
			Material material = block.getMaterial();
			return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
		}
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase enemy, EntityLivingBase holder)
	{
		float attackDamage = this.swordDamage;
		
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(WCLConstants.KEY_WEAPON_ATTACKDAMAGE))
			{
				attackDamage = stack.getTagCompound().getFloat(WCLConstants.KEY_WEAPON_ATTACKDAMAGE);
			}
		}
		
		boolean isCrit = (holder.fallDistance > 0.2F);
		attackDamage += EnchantmentHelper.getEnchantmentModifierLiving(holder, (EntityLivingBase) enemy);
		
		if (isCrit)
			attackDamage *= 1.2;
		
		int i = 0;
		i += EnchantmentHelper.getKnockbackModifier(holder, (EntityLivingBase) enemy);
		
		boolean flag = enemy.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)holder), attackDamage);
		
		if (i > 0)
		{
			enemy.addVelocity(
					(double) (-MathHelper.sin(holder.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 
					0.1D, 
					(double) (MathHelper.cos(holder.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
		}
		
		int j = EnchantmentHelper.getFireAspectModifier(holder);
		
		if (j > 0)
			enemy.setFire(j * 4);
		
		EnchantmentHelper.func_151384_a(enemy, holder);
		EnchantmentHelper.func_151385_b(holder, enemy);
		
		stack.damageItem(1, enemy);
		
		return flag;
	}
	
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase player)
	{
		if ((double) block.getBlockHardness(world, x, y, z) != 0.0D)
		{
			stack.damageItem(2, player);
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.block;
	}
	
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
	
	public boolean func_150897_b(Block block)
	{
		return block == Blocks.web;
	}
	
	public int getItemEnchantability()
	{
		return Item.ToolMaterial.GOLD.getEnchantability();
	}
	
	public String getToolMaterialName()
	{
		return this.swordMaterial.toString();
	}
	
	public boolean getIsRepairable(ItemStack stack, ItemStack source)
	{
		ItemStack mat = this.swordMaterial.getRepairItemStack();
		
		if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, source, false))
			return true;
		
		return super.getIsRepairable(stack, source);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i1, boolean b1)
	{
		stack = WCLItem.correctNameColor(stack);
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(WCLConstants.KEY_LOOPSOUNDTIMER))
			{
				int id = tag.getInteger(WCLConstants.KEY_WEAPONINDEX);
				stack = WCLItem.loop(stack, "Weapon Sword", "blade", WCLConfig.sword_rarity, world, entity, (id > 11)? 12 : 0, (id > 11)? 24 : 12);
			}
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		;
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List list)
    {
		for (int i = 0; i < WCLConstants.totalSwords; ++i)
		{
			for (int q = 0; q < 5; ++q)
			{
				list.add(getSwordFromId(i, q));
			}
		}
    }
	
	public ItemStack getSwordFromId(int swordId, int quality)
	{
		ItemStack sword = new ItemStack(WCLItems.sword, 1);
		int rarity = WCLConfig.sword_rarity[swordId];
		NBTTagCompound weaponData = new NBTTagCompound();
		
		weaponData.setString(WCLConstants.KEY_STATE, WCLConstants.weapon_quality[quality]);
		weaponData.setString(WCLConstants.KEY_RARITY, WCLConstants.weapon_rarity[rarity]);
		weaponData.setFloat(WCLConstants.KEY_LOOPSOUNDTIMER, 100.0F);
		weaponData.setInteger(WCLConstants.KEY_WEAPONINDEX, swordId);
		weaponData.setInteger(WCLConstants.KEY_WEAPON_DURABILITY, getWeaponDurability(quality, rarity));
		weaponData.setFloat(WCLConstants.KEY_WEAPON_ATTACKDAMAGE, getWeaponDamage(quality, rarity));
		
		sword.setTagCompound(weaponData);
		sword.setStackDisplayName(getNameFromSwordId(swordId));
		
		return sword;
	}
	
	public String getNameFromSwordId(int swordId)
	{
		String name;
		
		int rarity = WCLConfig.sword_rarity[swordId];
		
		String[] levels_colors = new String[4];
		levels_colors[0] = WCLConstants.colorChar + "b";
		levels_colors[1] = WCLConstants.colorChar + "a";
		levels_colors[2] = WCLConstants.colorChar + "e";
		levels_colors[3] = WCLConstants.colorChar + "6";
		
		name = levels_colors[rarity] + WCLConfig.sword_names[swordId];
		
		return name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		icons = new IIcon[WCLConstants.totalSwords];
		
		for (int i = 0; i < WCLConstants.totalSwords; ++i)
		{
			icons[i] = iconRegister.registerIcon(WCLConstants.MOD_ID + ":weaponsword_" + (i + 1));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
		return icons[0];
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		int iconIndex = 0;
		
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(WCLConstants.KEY_WEAPONINDEX))
			{
				iconIndex = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPONINDEX);
			}
		}
		
		return icons[iconIndex];
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
		return this.getIconFromDamage(damage);
    }
	
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		return this.getIcon(stack, renderPass);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return this.getIconIndex(stack);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
    {
		NBTTagCompound tag = stack.getTagCompound();
		
		if (tag != null && !(tag.hasKey("temp_looping")))
		{
			if (tag.hasKey(WCLConstants.KEY_WEAPONINDEX))
			{
				int wIndex = tag.getInteger(WCLConstants.KEY_WEAPONINDEX);
				
				if (wIndex < 12)
					list.add("First edition sword.");
				else
					list.add("Second edition sword.");
			}
			
			if (tag.hasKey(WCLConstants.KEY_STATE))
				list.add("Quality: " + tag.getString(WCLConstants.KEY_STATE));
			
			if (tag.hasKey(WCLConstants.KEY_RARITY))
				list.add("Rarity: " + tag.getString(WCLConstants.KEY_RARITY));
			
			if (tag.hasKey(WCLConstants.KEY_WEAPON_ATTACKDAMAGE))
			{
				float attackDamage = tag.getFloat(WCLConstants.KEY_WEAPON_ATTACKDAMAGE);
				int sLev = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
				
				if (sLev > 0)
					attackDamage += sLev + 1;
				
				list.add("Attack Damage: " + attackDamage);
			}
		}
    }
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}
	
	public static int getWeaponDurability(int wState, int wRarity)
	{
		int durability = 0;
		
		if (wRarity == 0)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_common_broken;
				break;
			case 1:
				durability = WCLConfig.durability_common_crude;
				break;
			case 2:
				durability = WCLConfig.durability_common_good;
				break;
			case 3:
				durability = WCLConfig.durability_common_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_common_perfect;
				break;
			}
		} else if (wRarity == 1)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_uncommon_broken;
				break;
			case 1:
				durability = WCLConfig.durability_uncommon_crude;
				break;
			case 2:
				durability = WCLConfig.durability_uncommon_good;
				break;
			case 3:
				durability = WCLConfig.durability_uncommon_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_uncommon_perfect;
				break;
			}
		} else if (wRarity == 2)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_rare_broken;
				break;
			case 1:
				durability = WCLConfig.durability_rare_crude;
				break;
			case 2:
				durability = WCLConfig.durability_rare_good;
				break;
			case 3:
				durability = WCLConfig.durability_rare_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_rare_perfect;
				break;
			}
		} else if (wRarity == 3)
		{
			switch (wState)
			{
			case 0:
				durability = WCLConfig.durability_legendary_broken;
				break;
			case 1:
				durability = WCLConfig.durability_legendary_crude;
				break;
			case 2:
				durability = WCLConfig.durability_legendary_good;
				break;
			case 3:
				durability = WCLConfig.durability_legendary_flawless;
				break;
			case 4:
				durability = WCLConfig.durability_legendary_perfect;
				break;
			}
		}
		
		return durability;
	}
	
	public static float getWeaponDamage(int wState, int wRarity)
	{
		float damage = 0;
		
		if (wRarity == 0)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_common_broken;
				break;
			case 1:
				damage = WCLConfig.damage_common_crude;
				break;
			case 2:
				damage = WCLConfig.damage_common_good;
				break;
			case 3:
				damage = WCLConfig.damage_common_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_common_perfect;
				break;
			}
		} else if (wRarity == 1)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_uncommon_broken;
				break;
			case 1:
				damage = WCLConfig.damage_uncommon_crude;
				break;
			case 2:
				damage = WCLConfig.damage_uncommon_good;
				break;
			case 3:
				damage = WCLConfig.damage_uncommon_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_uncommon_perfect;
				break;
			}
		} else if (wRarity == 2)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_rare_broken;
				break;
			case 1:
				damage = WCLConfig.damage_rare_crude;
				break;
			case 2:
				damage = WCLConfig.damage_rare_good;
				break;
			case 3:
				damage = WCLConfig.damage_rare_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_rare_perfect;
				break;
			}
		} else if (wRarity == 3)
		{
			switch (wState)
			{
			case 0:
				damage = WCLConfig.damage_legendary_broken;
				break;
			case 1:
				damage = WCLConfig.damage_legendary_crude;
				break;
			case 2:
				damage = WCLConfig.damage_legendary_good;
				break;
			case 3:
				damage = WCLConfig.damage_legendary_flawless;
				break;
			case 4:
				damage = WCLConfig.damage_legendary_perfect;
				break;
			}
		}
		
		return damage;
	}
}
