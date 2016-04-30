package zairus.weaponcaseloot.item;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.weaponcaseloot.WCLConfig;
import zairus.weaponcaseloot.WeaponCaseLoot;

public class WeaponCase extends WCLItem
{
	public static int editions = 4;
	private IIcon[] icons;
	
	public WeaponCase()
	{
		this.maxStackSize = 64;
		this.setCreativeTab(CreativeTabs.tabCombat);
		
		this.setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
		int j = MathHelper.clamp_int(damage, 0, editions - 1);
		
		return icons[j];
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage() + 1, 0, editions);
		return super.getUnlocalizedName() + ((i > 1)? ("." + i) : "");
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List list)
    {
		for (int i = 0; i < editions; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
		icons = new IIcon[editions];
		
		for (int i = 0; i < editions; ++i)
		{
			this.icons[i] = iconRegister.registerIcon(this.getIconString() + ((i > 0)? ("." + (i + 1)) : ""));
		}
    }
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		int chance = itemRand.nextInt(100);
		int level;
		
		if (chance < 70)
		{
			level = 0;
		} else if (chance < 95) {
			level = 1;
		} else if (chance < 99) {
			level = 2;
		} else {
			level = 3;
		}
		
		int quality = world.rand.nextInt(5);
		
		if (stack.getItemDamage() < 2)
		{
			int swordId = getSwordIdFromRarity(stack, level);
			
			ItemStack weapon = ((WeaponSword)WCLItems.sword).getSwordFromId(swordId, quality);
			
			world.playSoundAtEntity(player, "weaponcaseloot:case_open", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			
			if (!player.inventory.addItemStackToInventory(weapon))
			{
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, weapon));
			}
		}
		
		if (stack.getItemDamage() == 2)
		{
			int bowId = getSwordIdFromRarity(new ItemStack(WCLItems.weaponcase, 1, 0), level);
			
			ItemStack bow = WCLItems.bow.getFromId(bowId, quality);
			
			world.playSoundAtEntity(player, "weaponcaseloot:case_open", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			
			if (!player.inventory.addItemStackToInventory(bow))
			{
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, bow));
			}
		}
		
		if (stack.getItemDamage() == 3 && WeaponCaseLoot.baublesExist())
		{
			int ringId = getSwordIdFromRarity(new ItemStack(WCLItems.weaponcase, 1, 0), level);
			
			ItemStack ring = ((WCLItemBauble)WCLItems.bauble).getRingFromId(ringId, quality);
			
			world.playSoundAtEntity(player, "weaponcaseloot:case_open", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			
			if (!player.inventory.addItemStackToInventory(ring))
			{
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, ring));
			}
		}
		
		return stack;
	}
	
	private int getSwordIdFromRarity(ItemStack stack, int rarity)
	{
		int id = 0;
		
		List<List<Integer>> r = new ArrayList<List<Integer>>();
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		r.add(new ArrayList<Integer>());
		
		int edition = stack.getItemDamage();
		
		for (int i = 0 + (edition * 12); i < 12 * (edition + 1); ++i)
		{
			r.get(WCLConfig.sword_rarity[i]).add(i);
		}
		
		id = r.get(rarity).get(itemRand.nextInt(r.get(rarity).size()));
		
		return id;
	}
}
