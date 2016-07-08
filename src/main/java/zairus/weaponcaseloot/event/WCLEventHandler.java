package zairus.weaponcaseloot.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.weaponcaseloot.item.WCLItems;
import zairus.weaponcaseloot.item.WeaponBow;

public class WCLEventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFOVUpdate(FOVUpdateEvent event)
	{
		if (!event.getEntity().isHandActive())
			return;
		
		ItemStack stack = event.getEntity().getActiveItemStack();
		
		if (stack != null)
		{
			if (stack.getItem() instanceof WeaponBow)
			{
				WeaponBow item = (WeaponBow)stack.getItem();
				
				if (item.updatesFOV())
				{
					float newfov = event.getFov() / (event.getFov() + (item.getFOVValue(stack) * getItemInUsePercentaje(event.getEntity(), item.getFOVSpeedFactor(stack))));
					
					event.setNewfov(newfov);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private float getItemInUsePercentaje(EntityPlayer player, float speedFactor)
	{
		if (player.getActiveItemStack().getItem() instanceof WeaponBow)
		{
			WeaponBow bow = (WeaponBow)player.getActiveItemStack().getItem();
			
			float maxUse = bow.getFOVDuration(player.getActiveItemStack());
			float curUse = (float)player.getItemInUseCount();
			float percent = 0.0f;
			
			percent = maxUse - (maxUse - (bow.getMaxItemUseDuration(player.getActiveItemStack()) - curUse));
			percent *= (speedFactor * ((percent > 100)? 1.0f : percent / 100.0f));
			percent = percent / maxUse;
			percent *= 1 +  percent;
			
			return (percent > 1.0f)? 1.0f : percent;
		}
		else
		{
			return 1.0f;
		}
	}
	
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event)
	{
		if (
				event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)
				|| event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)
				|| event.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE)
				|| event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST)
				|| event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)
				|| event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)
				|| event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)
				|| event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)
				|| event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH))
		{
			final LootPool main = event.getTable().getPool("main");
			
			if (main != null)
			{
				main.addEntry(
						new LootEntryItem(
								WCLItems.weaponcase, 
								10, 
								0, 
								new LootFunction[] {new SetMetadata(new LootCondition[0], new RandomValueRange(0, 3))}, 
								new LootCondition[0], 
								"weaponcaseloot:weaponcase1"));
			}
		}
	}
}
