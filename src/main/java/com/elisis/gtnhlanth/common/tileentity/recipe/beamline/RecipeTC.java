package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;

public class RecipeTC extends GT_Recipe {

	public int particleId;
    public int amount;
    
    public float minEnergy;
    public float maxEnergy;
    
    public float minFocus;
    public float energyRatio;
    
    public ItemStack focusItem;
    
    public RecipeTC(boolean aOptimize, ItemStack aInput, ItemStack aOutput, ItemStack aFocusItem, int particleId, int amount, float minEnergy, float maxEnergy, float minFocus, float energyRatio, int aEUt) {

        super(aOptimize, new ItemStack[] {aInput, aFocusItem}, new ItemStack[] {aOutput}, null, null, null, null, 1, aEUt, 0);

        this.particleId = particleId;
        this.amount = amount;
        
        this.minEnergy = minEnergy;
        this.maxEnergy = maxEnergy;
        
        this.minFocus = minFocus;
        
        this.energyRatio = energyRatio;
        
        this.focusItem = aFocusItem;
    }
	
}
