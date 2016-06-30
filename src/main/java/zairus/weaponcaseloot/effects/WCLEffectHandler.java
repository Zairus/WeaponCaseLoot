package zairus.weaponcaseloot.effects;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
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
			player.addPotionEffect(new PotionEffect(effect.getPotionId(), 2, effect.getLevel(), true, false));
		}
	}
	
	public static enum effectType
	{
		SPEED_1("generic.movementSpeed", "Speed 1", 0.3, true, Potion.moveSpeed.id, 0),
		SPEED_2("generic.movementSpeed", "Speed 2", 0.6, true, Potion.moveSpeed.id, 1),
		DAMAGE_1("generic.attackDamage", "Strength 1", 0.3, true, Potion.damageBoost.id, 0),
		DAMAGE_2("generic.attackDamage", "Strength 2", 0.6, true, Potion.damageBoost.id, 1),
		
		JUMP_1("potion.jump", "Jump Boost 1", 0.3, true, Potion.jump.id, 0),
		JUMP_2("potion.jump", "Jump Boost 2", 0.6, true, Potion.jump.id, 1),
		RESISTANCE_1("potion.resistance", "Resistance 1", 0.3, true, Potion.resistance.id, 0),
		RESISTANCE_2("potion.resistance", "Resistance 2", 0.6, true, Potion.resistance.id, 1),
		HASTE_1("potion.digSpeed", "Haste 1", 0.3, true, Potion.digSpeed.id, 0),
		HASTE_2("potion.digSpeed", "Haste 2", 0.6, true, Potion.digSpeed.id, 1);
		
		private String attributeString;
		private String displayName;
		private double levelModifier;
		private boolean potion = false;
		private int potionId = 0;
		private int level = 0;
		
		private effectType(String name, String display, double modifier, boolean potion, int potionId, int potionLevel)
		{
			this(name, display, modifier);
			this.potion = potion;
			this.potionId = potionId;
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
