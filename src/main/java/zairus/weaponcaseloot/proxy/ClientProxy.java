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
import zairus.weaponcaseloot.item.WCLItemBauble;
import zairus.weaponcaseloot.item.WCLItemWeapon;
import zairus.weaponcaseloot.item.WeaponBow;

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
	
	@Override
	public void registerBowItem(WeaponBow bow, String name)
	{
		super.registerBowItem(bow, name);
		
		registerBowModel(bow);
	}
	
	@Override
	public void registerBaubleItem(WCLItemBauble bauble, String name)
	{
		super.registerBaubleItem(bauble, name);
		
		registerBaubleModel(bauble);
	}
	
	//Thanks to Vorquel
	public void registerSwordModel(WCLItemWeapon sword)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(sword, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				if (stack == null || !stack.hasTagCompound())
				{
					return new ModelResourceLocation(WCLConstants.MOD_ID + ":weaponsword_1", "inventory");
				}
				else
				{
					int meta = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPONINDEX);
					
					return new ModelResourceLocation(WCLConstants.MOD_ID + ":weaponsword_" + (meta + 1), "inventory");
				}
			}
		});
		
		for (int i = 0; i < 24; ++i)
			ModelBakery.registerItemVariants(sword, new ResourceLocation(WCLConstants.MOD_ID, "weaponsword_" + (i + 1)));
	}
	
	public void registerBowModel(WeaponBow bow)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(bow, new ItemMeshDefinition(){
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				if (stack == null || !stack.hasTagCompound())
				{
					return new ModelResourceLocation(WCLConstants.MOD_ID + ":weaponbow_1", "inventory");
				}
				else
				{
					int meta = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPONINDEX);
					
					String pullingString = "";
					
					if (Minecraft.getMinecraft().thePlayer.getActiveItemStack() == stack)
					{
						int pulling = stack.getMaxItemUseDuration() - (Minecraft.getMinecraft().thePlayer.getItemInUseCount());
						pulling = (int)((float)pulling * (1.0f + (WeaponBow.getDrawSpeed(stack) / 1.0f)));
						
						if (pulling >= 25)
						{
							pullingString = "_pulling_2";
						}
						else if (pulling > 15)
						{
							pullingString = "_pulling_1";
						}
						else if (pulling > 2)
						{
							pullingString = "_pulling_0";
						}
					}
					
					return new ModelResourceLocation(WCLConstants.MOD_ID + ":weaponbow_" + (meta + 1) + pullingString, "inventory");
				}
			}
		});
		
		for (int i = 0; i < 12; ++i)
		{
			ModelBakery.registerItemVariants(bow, new ResourceLocation(WCLConstants.MOD_ID, "weaponbow_" + (i + 1)));
			ModelBakery.registerItemVariants(bow, new ResourceLocation(WCLConstants.MOD_ID, "weaponbow_" + (i + 1) + "_pulling_0"));
			ModelBakery.registerItemVariants(bow, new ResourceLocation(WCLConstants.MOD_ID, "weaponbow_" + (i + 1) + "_pulling_1"));
			ModelBakery.registerItemVariants(bow, new ResourceLocation(WCLConstants.MOD_ID, "weaponbow_" + (i + 1) + "_pulling_2"));
		}
	}
	
	public void registerBaubleModel(WCLItemBauble bauble)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(bauble, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				if (stack == null || !stack.hasTagCompound())
				{
					return new ModelResourceLocation(WCLConstants.MOD_ID + ":baublering_1", "inventory");
				}
				else
				{
					int meta = stack.getTagCompound().getInteger(WCLConstants.KEY_WEAPONINDEX);
					
					return new ModelResourceLocation(WCLConstants.MOD_ID + ":baublering_" + (meta + 1), "inventory");
				}
			}
		});
		
		for (int i = 0; i < 12; ++i)
			ModelBakery.registerItemVariants(bauble, new ResourceLocation(WCLConstants.MOD_ID, "baublering_" + (i + 1)));
	}
}