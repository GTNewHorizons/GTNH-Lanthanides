package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import java.util.Collection;

public class SCRecipeMap extends GT_Recipe_Map {

    public SCRecipeMap(
            Collection<GT_Recipe> aRecipeList,
            String aUnlocalizedName,
            String aLocalName,
            String aNEIName,
            String aNEIGUIPath,
            int aUsualInputCount,
            int aUsualOutputCount,
            int aMinimalInputItems,
            int aMinimalInputFluids,
            int aAmperage,
            String aNEISpecialValuePre,
            int aNEISpecialValueMultiplier,
            String aNEISpecialValuePost,
            boolean aShowVoltageAmperageInNEI,
            boolean aNEIAllowed) {
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
}
