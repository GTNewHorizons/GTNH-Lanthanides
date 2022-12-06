package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gtPlusPlus.core.material.ELEMENT;
import net.minecraft.item.ItemStack;

public class BeamlineRecipeLoader {

    public static void load() {

        /*
         * ELECTRON
         */
        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
                new ItemStack[] {GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Tungsten, 1)},
                null,
                0,
                20,
                5000,
                98,
                0.5f,
                1920);

        /*
         * NEUTRON
         */
        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
                new ItemStack[] {ELEMENT.getInstance().CALIFORNIUM.getDust(1)}, null, 2, 10, 9000, 95, 999, 1920);
    }
}
