package zairus.weaponcaseloot.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class WCLItemWeapon extends ItemSword
{
	public WCLItemWeapon()
	{
		super(Item.ToolMaterial.DIAMOND);
	}
	
	@Override
	public WCLItemWeapon setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		return this;
	}
}
