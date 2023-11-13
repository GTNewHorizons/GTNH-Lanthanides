package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import java.util.Collection;

import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;

public class LanthRecipeMap extends GT_Recipe_Map {

    public LanthRecipeMap(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName,
            String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems,
            int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier,
            String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
        super(
                aRecipeList,
                aUnlocalizedName,
                aLocalName,
                aNEIName,
                aNEIGUIPath,
                aUsualInputCount,
                aUsualOutputCount,
                aMinimalInputItems,
                aMinimalInputFluids,
                aAmperage,
                aNEISpecialValuePre,
                aNEISpecialValueMultiplier,
                aNEISpecialValuePost,
                aShowVoltageAmperageInNEI,
                aNEIAllowed);
    }
    
    public GT_Recipe addRecipe(GT_Recipe aRecipe, boolean aCheckForCollisions, boolean aFakeRecipe,
            boolean aHidden) {
            aRecipe.mHidden = aHidden;
            aRecipe.mFakeRecipe = aFakeRecipe;
            if (aRecipe.mFluidInputs.length < mMinimalInputFluids && aRecipe.mInputs.length < mMinimalInputItems)
                return null;
            if (aCheckForCollisions
                && findRecipe(null, false, true, Long.MAX_VALUE, aRecipe.mFluidInputs, aRecipe.mInputs) != null)
                return null;
            return add(aRecipe);
        }
}
