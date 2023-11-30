package com.elisis.gtnhlanth.loader;

import gregtech.api.gui.modularui.GT_UITextures;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;
import gregtech.nei.formatter.HeatingCoilSpecialValueFormatter;
import gregtech.nei.formatter.SimpleSpecialValueFormatter;

public class RecipeAdder {

    public static final RecipeAdder instance = new RecipeAdder();

    public final RecipeMap<RecipeMapBackend> DigesterRecipes = RecipeMapBuilder.of("gtnhlanth.recipe.digester")
            .maxIO(1, 1, 1, 1).minInputs(1, 1).progressBar(GT_UITextures.PROGRESSBAR_ARROW_MULTIPLE)
            .neiSpecialInfoFormatter(HeatingCoilSpecialValueFormatter.INSTANCE).build();
    public final RecipeMap<RecipeMapBackend> DissolutionTankRecipes = RecipeMapBuilder.of("gtnhlanth.recipe.disstank")
            .maxIO(2, 3, 2, 1).minInputs(1, 1).progressBar(GT_UITextures.PROGRESSBAR_ARROW_MULTIPLE)
            .neiSpecialInfoFormatter(new SimpleSpecialValueFormatter("value.disstank")).build();
}
