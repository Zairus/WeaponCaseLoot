package zairus.weaponcaseloot.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class WCLItemWeapon extends ItemSword
{
	public WCLItemWeapon()
	{
		super(Item.ToolMaterial.EMERALD);
	}
	
	@Override
	public WCLItemWeapon setTextureName(String texture)
	{
		super.setTextureName(texture);
		return this;
	}
	
	@Override
	public WCLItemWeapon setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		return this;
	}
}
