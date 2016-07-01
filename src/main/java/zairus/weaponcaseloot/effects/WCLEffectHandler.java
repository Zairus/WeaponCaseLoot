package zairus.weaponcaseloot.effects;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class WCLEffectHandler
{
public static final WCLEffectHandler instance = new WCLEffectHandler();
	
	public void applyEffect(EntityPlayer player, effectType effect, boolean add)
	{
		if (player.worldObj.isRemote)
			return;
		
		if (add && effect.isPotion())
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(effect.potionId), 2, effect.getLevel(), true, false));
		}
	}
	
	public static enum effectType
	{
		SPEED_1("generic.movementSpeed", "Speed 1", 0.3, true, MobEffects.SPEED, 0),
		SPEED_2("generic.movementSpeed", "Speed 2", 0.6, true, MobEffects.SPEED, 1),
		DAMAGE_1("generic.attackDamage", "Strength 1", 0.3, true, MobEffects.STRENGTH, 0),
		DAMAGE_2("generic.attackDamage", "Strength 2", 0.6, true, MobEffects.STRENGTH, 1),
		
		JUMP_1("potion.jump", "Jump Boost 1", 0.3, true, MobEffects.JUMP_BOOST, 0),
		JUMP_2("potion.jump", "Jump Boost 2", 0.6, true, MobEffects.JUMP_BOOST, 1),
		RESISTANCE_1("potion.resistance", "Resistance 1", 0.3, true, MobEffects.RESISTANCE, 0),
		RESISTANCE_2("potion.resistance", "Resistance 2", 0.6, true, MobEffects.RESISTANCE, 1),
		HASTE_1("potion.digSpeed", "Haste 1", 0.3, true, MobEffects.HASTE, 0),
		HASTE_2("potion.digSpeed", "Haste 2", 0.6, true, MobEffects.HASTE, 1);
		
		private String attributeString;
		private String displayName;
		private double levelModifier;
		private boolean potion = false;
		private int potionId = 0;
		private int level = 0;
		
		private effectType(String name, String display, double modifier, boolean isPotion, Potion potion, int potionLevel)
		{
			this(name, display, modifier);
			this.potion = isPotion;
			this.potionId = Potion.getIdFromPotion(potion);
			this.level = potionLevel;
		}
		
		private effectType(String name, String display, double modifier)
		{
			this.attributeString = name;
			this.displayName = display;
			this.levelModifier = modifier;
		}
		
		public boolean isPotion()
		{
			return this.potion;
		}
		
		public int getPotionId()
		{
			return this.potionId;
		}
		
		public int getLevel()
		{
			return this.level;
		}
		
		public AttributeModifier getModifier()
		{
			AttributeModifier effect;
			
			effect = new AttributeModifier(new UUID(12879874982l, 320981923), attributeString, levelModifier, 1);
			
			return effect;
		}
		
		public String getName()
		{
			return this.attributeString;
		}
		
		public String getDisplayName()
		{
			return this.displayName;
		}
	}
}
