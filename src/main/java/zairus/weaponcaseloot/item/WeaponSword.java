package zairus.weaponcaseloot.item;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.sounds.WCLSoundEvents;

public class WeaponSword extends WCLItemWeapon
{
	private float swordDamage;
	private final Item.ToolMaterial swordMaterial;
	
	private final String name = "weaponsword";
	
	public WeaponSword()
	{
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		this.swordMaterial = Item.ToolMaterial.DIAMOND;
		this.maxStackSize = 1;
	}
	
	public String getName()
	{
		return name;
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
	public int getMetadata(ItemStack stack)
	{
		int meta = 0;
		
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(WCLConstants.KEY_WEAPONINDEX))
				meta = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPONINDEX);
		}
		
		return meta;
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
	
	@Override
	public float getDamageVsEntity()
	{
		return this.swordMaterial.getDamageVsEntity();
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		if (state == Blocks.WEB.getDefaultState())
		{
			return 15.0F;
		}
		else
		{
			Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
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
		attackDamage += EnchantmentHelper.getModifierForCreature(stack, ((EntityLivingBase)enemy).getCreatureAttribute());
		
		if (isCrit)
			attackDamage *= 1.2;
		
		int i = 0;
		i += EnchantmentHelper.getKnockbackModifier(holder);
		
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
		
		stack.damageItem(1, enemy);
		
		return flag;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase player)
	{
		if ((double) state.getBlockHardness(world, pos) != 0.0D)
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
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState state)
	{
		return state.getBlock() == Blocks.WEB;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return Item.ToolMaterial.GOLD.getEnchantability();
	}
	
	@Override
	public String getToolMaterialName()
	{
		return this.swordMaterial.toString();
	}
	
	@Override
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
		if (stack.hasDisplayName())
		{
			String name = stack.getDisplayName();
			
			char fChar = name.charAt(0);
			if (fChar != WCLConstants.colorChar && (fChar == 'b' || fChar == 'a' || fChar == 'e' || fChar == '6'))
				stack.setStackDisplayName(WCLConstants.colorChar + name);
		}
		
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
					stack.setStackDisplayName("Weapon Sword");
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
				tag.setInteger(WCLConstants.KEY_WEAPONINDEX, itemRand.nextInt(WCLConstants.totalSwords));
				
				if (t <= 10.0F)
				{
					//int rarity = WCLConfig.sword_rarity[tag.getInteger("temp_index")];
					/*
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
							WCLSoundEvents.blade, 
							SoundCategory.NEUTRAL, 
							1.0F, 
							1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
				}
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
    {
		NBTTagCompound tag = stack.getTagCompound();
		
		if (tag != null && !(tag.hasKey("temp_looping")))
		{
			if (tag.hasKey(WCLConstants.KEY_STATE))
				list.add("Quality: " + tag.getString(WCLConstants.KEY_STATE));
			
			if (tag.hasKey(WCLConstants.KEY_RARITY))
				list.add("Rarity: " + tag.getString(WCLConstants.KEY_RARITY));
			
			if (tag.hasKey(WCLConstants.KEY_WEAPON_ATTACKDAMAGE))
			{
				float attackDamage = tag.getFloat(WCLConstants.KEY_WEAPON_ATTACKDAMAGE);
				int sLev = 1;
				
				if (sLev > 0)
					attackDamage += sLev + 1;
				
				list.add("Attack Damage: " + attackDamage);
			}
		}
    }
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			float attackDamage = this.swordDamage;
			
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
		}
		
		return multimap;
	}
	
	private int getWeaponDurability(int wState, int wRarity)
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
	
	private float getWeaponDamage(int wState, int wRarity)
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
