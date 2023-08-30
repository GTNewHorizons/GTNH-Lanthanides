package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;

import com.elisis.gtnhlanth.common.beamline.Particle;
import com.elisis.gtnhlanth.common.register.WerkstoffMaterialPool;
import com.elisis.gtnhlanth.common.tileentity.LINAC;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gtPlusPlus.core.material.ELEMENT;

public class BeamlineRecipeLoader {

    public static final HashMap<Fluid, Fluid> coolantMap = new HashMap<>();
	
    public static void load() {

        /*
         * Coolant list
         */

        coolantMap.put(Materials.LiquidNitrogen.getGas(1L).getFluid(), Materials.Nitrogen.getGas(1L).getFluid());
        coolantMap.put(Materials.LiquidOxygen.getGas(1L).getFluid(), Materials.Oxygen.getGas(1L).getFluid());
        coolantMap.put(FluidRegistry.getFluid("ic2coolant"), FluidRegistry.getFluid("ic2hotcoolant"));

        /*
         * ELECTRON
         */
        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Tungsten, 1) },
                null,
                Particle.ELECTRON.ordinal(),
                20,
                5000,
                98,
                0.5f,
                1920);

        /*
         * NEUTRON
         */
        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
                new ItemStack[] { ELEMENT.getInstance().CALIFORNIUM.getDust(1) },
                null,
                Particle.NEUTRON.ordinal(),
                10,
                9000,
                95,
                999,
                1920);

        /*
         * ALPHA
         */
        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
                new ItemStack[] { Materials.Uranium.getDustTiny(1) },
                new ItemStack[] { WerkstoffMaterialPool.Thorium234.get(OrePrefixes.dustTiny, 1) },
                Particle.ALPHA.ordinal(),
                1,
                4270,
                90,
                999,
                512);
    }
}
