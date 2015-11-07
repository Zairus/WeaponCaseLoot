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
import zairus.weaponcaseloot.WCLConstants;

public class WeaponSword extends WCLItemWeapon
{
	private float swordDamage;
	private final Item.ToolMaterial swordMaterial;
	
	private IIcon[] icons;
	
	public WeaponSword()
	{
		this.swordMaterial = Item.ToolMaterial.EMERALD;
		this.maxStackSize = 1;
		
		this.setCreativeTab(CreativeTabs.tabCombat);
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
					world.playSoundAtEntity(entity, "weaponcaseloot:weapon_loop", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
				
				--t;
				
				tag.setFloat(WCLConstants.KEY_LOOPSOUNDTIMER, t);
				tag.setInteger(WCLConstants.KEY_WEAPONINDEX, itemRand.nextInt(WCLConstants.totalSwords));
				
				if (t <= 10.0F)
				{
					tag.removeTag(WCLConstants.KEY_LOOPSOUNDTIMER);
					tag.setInteger(WCLConstants.KEY_WEAPONINDEX, tag.getInteger("temp_index"));
					tag.removeTag("temp_index");
					stack.setStackDisplayName(tag.getString("temp_name"));
					tag.removeTag("temp_name");
					tag.removeTag("temp_looping");
					world.playSoundAtEntity(entity, "weaponcaseloot:blade", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
				}
			}
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		;
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
		//return super.getItemAttributeModifiers();
		return HashMultimap.create();
	}
}
