package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import java.util.HashSet;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BeamlineRecipeAdder {
	
	public static final BeamlineRecipeAdder instance = new BeamlineRecipeAdder();
	
	public final SCRecipeMap SourceChamberRecipes = new SCRecipeMap(
			new HashSet<>(200),
			"gtnhlanth.recipe.sc",
			StatCollector.translateToLocal("tile.recipe.sc"),
			null,
			"",
			1,
			1,
			0,
			0,
			1,
			null,
			1,
			null,
			false,
			false
			
			);
	
	
	public boolean addSourceChamberRecipe(
			ItemStack[] itemInputs,
			ItemStack[] itemOutputs,
			int particleId,
			int rate,
			float baseEnergy,
			float focus,
			float energyRatio,
			int minEUt) {
		
				return (SourceChamberRecipes.addRecipe(
						new RecipeSC(
								false,
								itemInputs,
								itemOutputs,
								null,
								new int[] {},
								null,
								null,
								20,
								minEUt,
								particleId,
								rate,
								baseEnergy,
								focus,
								energyRatio))
						!= null);
		
	}

}
