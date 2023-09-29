package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import java.util.HashMap;

import com.elisis.gtnhlanth.common.beamline.Particle;
import com.elisis.gtnhlanth.common.item.MaskList;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.elisis.gtnhlanth.common.register.WerkstoffMaterialPool;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.material.ELEMENT;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BeamlineRecipeLoader {

    public static final HashMap<Fluid, Fluid> coolantMap = new HashMap<>();
	
    public static void load() {

        /*
         * Coolant list
         */

        coolantMap.put(Materials.LiquidNitrogen.getGas(1L).getFluid(), Materials.Nitrogen.getGas(1L).getFluid());
        coolantMap.put(Materials.LiquidOxygen.getGas(1L).getFluid(), Materials.Oxygen.getGas(1L).getFluid());
        coolantMap.put(FluidRegistry.getFluid("ic2coolant"), FluidRegistry.getFluid("ic2hotcoolant"));
        coolantMap.put(Materials.SuperCoolant.getFluid(1L).getFluid(), Materials.Water.getFluid(1L).getFluid());

        /*
         * ELECTRON
         */
        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Tungsten, 1) },
                null,
                Particle.ELECTRON.ordinal(),
                20,
                1000,
                98,
                0.1f,
                7680);

        BeamlineRecipeAdder.instance.addSourceChamberRecipe(
        		new ItemStack[] {WerkstoffMaterialPool.LanthanumHexaboride.get(OrePrefixes.stickLong, 1)}, 
        		null, 
        		Particle.ELECTRON.ordinal(), 
        		60, 
        		5000,
        		99, 
        		0.3f, 
        		7680
        		);
        
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
        
        
        BeamlineRecipeAdder.instance.addTargetChamberRecipe(
        		ItemList.Circuit_Silicon_Wafer3.get(1), 
        		ItemList.Circuit_Wafer_CPU.get(1), 
        		GT_Utility.copyAmount(0, new ItemStack(LanthItemList.maskMap.get(MaskList.CPU))), 
        		0, 
        		200, 
        		1, 
        		100, 
        		50, 
        		1, 
        		1920
        	);
    }
}
