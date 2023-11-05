package com.elisis.gtnhlanth.common.tileentity.recipe.beamline;

import java.util.Arrays;
import java.util.HashMap;

import com.elisis.gtnhlanth.common.beamline.Particle;
import com.elisis.gtnhlanth.common.item.MaskList;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.elisis.gtnhlanth.common.register.WerkstoffMaterialPool;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import gtPlusPlus.core.material.ELEMENT;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BeamlineRecipeLoader {

    public static final HashMap<Fluid, Fluid> coolantMap = new HashMap<>();
    
    private static final ItemList[] VIABLE_WAFERS = new ItemList[] {
    		ItemList.Circuit_Silicon_Wafer, ItemList.Circuit_Silicon_Wafer2, ItemList.Circuit_Silicon_Wafer3, ItemList.Circuit_Silicon_Wafer4, ItemList.Circuit_Silicon_Wafer5, ItemList.Circuit_Silicon_Wafer6, ItemList.Circuit_Silicon_Wafer7
    };
	
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
        
        
        
        
        /*
         * TARGET CHAMBER
         */
        
        int index = 0;
        for (ItemList wafer : VIABLE_WAFERS) {
        		
        	index++;
        	
        	/*
        	 * CPU
        	 */
        	
    		if (!Arrays.asList(MaskList.CPU.getForbiddenWafers()).contains(wafer)) {
    			
    			BeamlineRecipeAdder.instance.addTargetChamberRecipe(
                		wafer.get(1), 
                		ItemList.Circuit_Wafer_CPU.get((int) Math.pow(2, index + 2)), //Varies
                		new ItemStack(LanthItemList.maskMap.get(MaskList.CPU), 1), // Varies 
                		0, 
                		10 * (int) Math.pow(2, index - 1),  // Varies
                		1, //Varies
                		10000000, //Varies
                		50, //Varies
                		1, 
                		1920
                	);
    		}
    		
    		/*
    		 * PPIC
    		 */
    		
    		
    		if (!Arrays.asList(MaskList.PPIC.getForbiddenWafers()).contains(wafer)) {
    			
    			GT_Log.out.print("Adding recipe for PPIC with " + wafer.get(1).getUnlocalizedName() + " amount: " + 40 * (int) Math.pow(2, index - 1));
    			
    			BeamlineRecipeAdder.instance.addTargetChamberRecipe(
                		wafer.get(1), 
                		ItemList.Circuit_Wafer_PPIC.get((int) Math.pow(2, index + 2)), //Varies
                		new ItemStack(LanthItemList.maskMap.get(MaskList.PPIC), 1), // Varies 
                		0, 
                		40 * (int) Math.pow(2, index - 1),  // Varies
                		1, //Varies
                		10000000, //Varies
                		50, //Varies
                		1, 
                		1920
                	);
    		}
    		
    		
    		
        		
        	
        	
        	
        	
        }
        /*
        BeamlineRecipeAdder.instance.addTargetChamberRecipe(
        		ItemList.Circuit_Silicon_Wafer2.get(1), 
        		ItemList.Circuit_Wafer_CPU.get(16), 
        		new ItemStack(LanthItemList.maskMap.get(MaskList.CPU), 1), 
        		0, 
        		10, 
        		1, 
        		10000000, 
        		50, 
        		1, 
        		1920
        	);
        
        BeamlineRecipeAdder.instance.addTargetChamberRecipe(
        		ItemList.Circuit_Silicon_Wafer3.get(1), 
        		ItemList.Circuit_Wafer_CPU.get(32), 
        		new ItemStack(LanthItemList.maskMap.get(MaskList.CPU), 1), 
        		0, 
        		20, 
        		1, 
        		10000000, 
        		50, 
        		1, 
        		1920
        	);
        
        BeamlineRecipeAdder.instance.addTargetChamberRecipe(
        		ItemList.Circuit_Silicon_Wafer4.get(1), 
        		ItemList.Circuit_Wafer_CPU.get(64), 
        		new ItemStack(LanthItemList.maskMap.get(MaskList.CPU), 1), 
        		0, 
        		40, 
        		1, 
        		10000000, 
        		50, 
        		1, 
        		1920
        	);
        
        BeamlineRecipeAdder.instance.addTargetChamberRecipe(
        		ItemList.Circuit_Silicon_Wafer5.get(1), 
        		ItemList.Circuit_Wafer_CPU.get(128), 
        		new ItemStack(LanthItemList.maskMap.get(MaskList.CPU), 1), 
        		0, 
        		80, 
        		1, 
        		10000000, 
        		50, 
        		1, 
        		1920
        	);*/
        
        
    }
}
