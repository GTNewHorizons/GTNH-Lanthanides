package com.elisis.gtnhlanth.common.item;

import com.elisis.gtnhlanth.Tags;

import net.minecraft.item.Item;

public class LanthItem extends Item {
	
	public LanthItem(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setTextureName(Tags.MODID + ":" + name);
	}
 
}
