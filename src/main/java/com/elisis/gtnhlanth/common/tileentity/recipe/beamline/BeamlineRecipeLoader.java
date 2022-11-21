package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class BeamlineRecipeLoader {
	
	public static void load() {
		
		BeamlineRecipeAdder.instance.addSourceChamberRecipe(
				
				new ItemStack[] {
						GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Tungsten, 1)
				}, 
				null, 
				0, 
				20,
				0,
				98, 
				0.5f, 
				2048
				);
		
		GT_Log.out.print("Added recipe");
		
		
	}

}
