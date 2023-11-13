package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.elisis.gtnhlanth.common.beamline.Particle;
import com.elisis.gtnhlanth.util.Util;
import com.gtnewhorizons.modularui.api.math.Pos2d;

import gregtech.GT_Mod;
import gregtech.api.gui.modularui.GT_UITextures;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.modularui.UIHelper;
import gregtech.common.power.Power;
import gregtech.common.power.UnspecifiedEUPower;
import gregtech.nei.NEIRecipeInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BeamlineRecipeAdder {

    public static final BeamlineRecipeAdder instance = new BeamlineRecipeAdder();

    public final LanthRecipeMap SourceChamberRecipes = new LanthRecipeMap(
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
            true);
    
    public final LanthRecipeMap TargetChamberRecipes = (LanthRecipeMap) new LanthRecipeMap(
    		new HashSet<>(500),
    		"gtnhlanth.recipe.tc",
    		StatCollector.translateToLocal("tile.recipe.sc"),
    		null,
    		"gtnhlanth:textures/gui/Digester",
    		3,
    		4,
    		0,
    		0,
    		1,
    		null,
    		1,
    		null,
    		false,
    		true) {
    	
	    		@Override
	    		public void drawNEIDescription(NEIRecipeInfo recipeInfo) {
		            drawNEIEnergyInfo(recipeInfo);
		            //drawNEIDurationInfo(recipeInfo);
		            drawNEISpecialInfo(recipeInfo);
		            drawNEIRecipeOwnerInfo(recipeInfo);
		        }
	    		
	    		@Override
	            public List<Pos2d> getItemInputPositions(int itemInputCount) {
	                
	    			/*
	    			Pos2d posParticle = new Pos2d(8, 28); // Particle item
	                
	                ArrayList<Pos2d> posList = new ArrayList<>();
	                posList.add(posParticle);
	                posList.addAll(UIHelper.getGridPositions(itemInputCount - 1, 36, 28, 3));
	                
	    			*/
	    			
	    			List<Pos2d> posList = Util.getGridPositions(itemInputCount, 8, 20, 3, 1, 20);
	    			return posList;
	            }

	    		@Override
	            public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
	                return UIHelper.getGridPositions(itemOutputCount, 128, 24, 1, 3); // Make output items display vertically, not in a square
	            }
	    		
	    		// Literally only removed the total power value
	    		@Override
	    		protected void drawNEIEnergyInfo(NEIRecipeInfo recipeInfo) {
	                GT_Recipe recipe = recipeInfo.recipe;
	                Power power = recipeInfo.power;
	                if (power.getEuPerTick() > 0) {

	                    String amperage = power.getAmperageString();
	                    String powerUsage = power.getPowerUsageString();
	                    if (amperage == null || amperage.equals("unspecified") || powerUsage.contains("(OC)")) {
	                        drawNEIText(recipeInfo, GT_Utility.trans("153", "Usage: ") + powerUsage);
	                        if (GT_Mod.gregtechproxy.mNEIOriginalVoltage) {
	                            Power originalPower = getPowerFromRecipeMap();
	                            if (!(originalPower instanceof UnspecifiedEUPower)) {
	                                originalPower.computePowerUsageAndDuration(recipe.mEUt, recipe.mDuration);
	                                drawNEIText(
	                                    recipeInfo,
	                                    GT_Utility.trans("275", "Original voltage: ") + originalPower.getVoltageString());
	                            }
	                        }
	                        if (amperage != null && !amperage.equals("unspecified") && !amperage.equals("1")) {
	                            drawNEIText(recipeInfo, GT_Utility.trans("155", "Amperage: ") + amperage);
	                        }
	                    } else if (amperage.equals("1")) {
	                        drawNEIText(recipeInfo, GT_Utility.trans("154", "Voltage: ") + power.getVoltageString());
	                    } else {
	                        drawNEIText(recipeInfo, GT_Utility.trans("153", "Usage: ") + powerUsage);
	                        drawNEIText(recipeInfo, GT_Utility.trans("154", "Voltage: ") + power.getVoltageString());
	                        drawNEIText(recipeInfo, GT_Utility.trans("155", "Amperage: ") + amperage);
	                    }
	                }
	            }
	    	}
    		.setProgressBar(GT_UITextures.PROGRESSBAR_ASSEMBLY_LINE_1)
    		.setProgressBarPos(108, 22)
    		.setNEITransferRect(new Rectangle(
                    100,
                    22,
                    28,
                    18))
    		.setNEISpecialInfoFormatter((recipeInfo, applyPrefixAndSuffix) -> {
    			
    			RecipeTC recipe = (RecipeTC) recipeInfo.recipe;
    			
    			float minEnergy = recipe.minEnergy;
    			float maxEnergy = recipe.maxEnergy;
    			
    			float minFocus = recipe.minFocus;
    			
    			float amount = recipe.amount;
    			
    			Particle particle = Particle.getParticleFromId(recipe.particleId);
    			
    			return Arrays.asList(
    					
    					StatCollector.translateToLocal("beamline.particle") + ": " + particle.getLocalisedName(),
    					
    					StatCollector.translateToLocal("beamline.energy") + ": " + GT_Utility.formatNumbers(minEnergy * 1000) + "-" + GT_Utility.formatNumbers(maxEnergy * 1000) + " eV", //Note the eV unit
    					
    					StatCollector.translateToLocal("beamline.focus") + ": >= " + GT_Utility.formatNumbers(minFocus),
    					
    					StatCollector.translateToLocal("beamline.amount") + ": " + GT_Utility.formatNumbers(amount)
    								
    				);
    			}
    		);

    /***
     *
     * @param itemInputs  - duh
     * @param itemOutputs - duh
     * @param particleId  - The ID of the {@link com.elisis.gtnhlanth.common.beamline.Particle} generated by the recipe.
     *                    It is recommended to use Particle#ordinal()
     * @param rate        - The rate/amount of particles generated
     * @param maxEnergy   - The maximum energy particles generated by this recipe can possess (keV). Set this value >=
     *                    max particle energy to limit it to the latter
     * @param focus       - Focus of the particle generated
     * @param energyRatio - Set high for little-to-no EUt energy scaling, low for the opposite
     * @param minEUt      - Minimum EUt required for the recipe. ! May not output if input energy is equal to minimum !
     */
    public boolean addSourceChamberRecipe(ItemStack[] itemInputs, ItemStack[] itemOutputs, int particleId, int rate,
            float maxEnergy, float focus, float energyRatio, int minEUt) {

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
                        maxEnergy,
                        focus,
                        energyRatio))
                != null);
    }
    
    /***
    *
    * @param itemInput  - The item to be used as a target
    * @param itemOutput - duh
    * @param particleId  - The ID of the {@link com.elisis.gtnhlanth.common.beamline.Particle} used by the recipe.
    *                    It is recommended to use Particle#ordinal()
    * @param amount        - The total amount of particles required for the recipe to come to completion. The duration of the recipe will be determined by this and the input particle rate.
    * @param minEnergy   - The minimum energy amount required by this recipe
    * @param maxEnergy	 - The maximum energy amount allowed by this recipe    
    * @param minFocus       - Minimum focus allowed by the recipe
    * @param energyRatio - Set high for little-to-no EUt energy scaling, low for the opposite
    * @param minEUt      - Minimum EUt required for the recipe to start
    */
    
    public boolean addTargetChamberRecipe(ItemStack itemInput, ItemStack itemOutput, ItemStack itemFocus, int particleId, int amount, 
    		float minEnergy, float maxEnergy, float minFocus, float energyRatio, int minEUt) {
    	
    	return (TargetChamberRecipes.addRecipe(
    			new RecipeTC(
    					false, 
    					itemInput, 
    					itemOutput, 
    					itemFocus, 
    					particleId, 
    					amount, 
    					minEnergy, 
    					maxEnergy, 
    					minFocus, 
    					energyRatio, 
    					minEUt), false, false, false
    			) != null);
    	
    }
    
    
}
