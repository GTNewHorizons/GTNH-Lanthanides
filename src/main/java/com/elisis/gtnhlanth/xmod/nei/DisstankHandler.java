package com.elisis.gtnhlanth.xmod.nei;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.nei.GT_NEI_DefaultHandler;

public class DisstankHandler extends GT_NEI_DefaultHandler {

    public DisstankHandler(GT_Recipe_Map recipeMap) {
        super(recipeMap);
        if (!NEI_Config.isAdded) {
            FMLInterModComms.sendRuntimeMessage(
                    GT_Values.GT,
                    "NEIPlugins",
                    "register-crafting-handler",
                    "gregtech@" + this.getRecipeName() + "@" + this.getOverlayIdentifier());
            GuiCraftingRecipe.craftinghandlers.add(this);
            GuiUsageRecipe.usagehandlers.add(this);
        }
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return new DisstankHandler(this.mRecipeMap);
    }
}
