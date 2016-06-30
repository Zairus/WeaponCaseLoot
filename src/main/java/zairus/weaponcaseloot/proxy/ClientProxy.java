package zairus.weaponcaseloot.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.weaponcaseloot.WCLConstants;
import zairus.weaponcaseloot.item.WCLItemWeapon;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
	
	@Override
	public void registerItem(Item item, String name, int meta, boolean model)
	{
		super.registerItem(item, name, meta, model);
		
		if (model)
		{
			registerModel(item, meta, name);
		}
	}
	
	public void registerModel(Item item, int meta, String name)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(WCLConstants.MOD_ID + ":" + name, "inventory");
		
		renderItem.getItemModelMesher().register(item, meta, modelResourceLocation);
		
		ModelBakery.registerItemVariants(item, new ResourceLocation(WCLConstants.MOD_ID, name));
	}
	
	@Override
	public void registerSwordItem(WCLItemWeapon sword, String name)
	{
		super.registerSwordItem(sword, name);
		
		registerSwordModel(sword);
	}
	
	//Thanks to Vorquel
	public void registerSwordModel(WCLItemWeapon sword)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(sword, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				int meta = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPONINDEX);
				
				return new ModelResourceLocation(WCLConstants.MOD_ID + ":weaponsword_" + (meta + 1), "inventory");
			}
		});
		
		for (int i = 0; i < 24; ++i)
			ModelBakery.registerItemVariants(sword, new ResourceLocation(WCLConstants.MOD_ID, "weaponsword_" + (i + 1)));
	}
}
