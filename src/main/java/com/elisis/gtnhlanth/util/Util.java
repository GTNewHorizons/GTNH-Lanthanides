package com.elisis.gtnhlanth.util;

import net.minecraft.item.ItemStack;

public class Util {
	
	public static void depleteDurabilityOfStack(ItemStack stack, int damage) {
		
		if (stack == null)
			return;
		
		if (stack.stackSize == 0) //Might happen, who knows
			return;
		
		if (damage + stack.getItemDamage() > stack.getMaxDamage()) {
			stack.stackSize--;
		} else {
			
			stack.setItemDamage(stack.getItemDamage() + damage);
			
		}
		
		if (stack.stackSize < 0) {
			stack.stackSize = 0;
		}
		
	}
}
