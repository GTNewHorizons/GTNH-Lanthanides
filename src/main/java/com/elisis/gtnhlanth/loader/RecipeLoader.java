package com.elisis.gtnhlanth.loader;

import static gregtech.common.items.GT_MetaGenerated_Item_01.registerCauldronCleaningFor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.dreammaster.gthandler.CustomItemList;
import com.elisis.gtnhlanth.Tags;
import com.elisis.gtnhlanth.common.item.MaskList;
import com.elisis.gtnhlanth.common.register.BotWerkstoffMaterialPool;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.elisis.gtnhlanth.common.register.WerkstoffMaterialPool;
import com.github.bartimaeusnek.bartworks.system.material.BW_GT_MaterialReference;
import com.github.bartimaeusnek.bartworks.system.material.GT_Enhancement.LuVTierEnhancer;
import com.github.bartimaeusnek.bartworks.system.material.GT_Enhancement.PlatinumSludgeOverHaul;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;

import cpw.mods.fml.common.Loader;
import goodgenerator.items.MyMaterial;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTPP_Recipe;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import static gregtech.api.enums.OrePrefixes.blockCasingAdvanced;


public class RecipeLoader {

    //private static final Materials[] BLACKLIST = null;

    public static void loadAccelerator() {

        LuVTierEnhancer.addToBlackListForOsmiridiumReplacement(LanthItemList.BEAMLINE_PIPE);

        /*
         * //Permalloy GT_Values.RA.addMixerRecipe( GT_Utility.getIntegratedCircuit(4), Materials.Nickel.getDust(4),
         * Materials.Iron.getDust(1), Materials.Molybdenum.getDust(1), null, null,
         * WerkstoffMaterialPool.Permalloy.get(OrePrefixes.dust, 6), 1920, 200 );
         */
        // Mu-metal
        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(2),
                WerkstoffMaterialPool.Permalloy.get(OrePrefixes.dust, 9),
                Materials.Copper.getDust(1),
                Materials.Chrome.getDust(1),
                null,
                null,
                WerkstoffMaterialPool.MuMetal.get(OrePrefixes.ingot, 9),
                null,
                null,
                null,
                400,
                1920,
                4500);

        // Shielded Accelerator Casing -- Maybe assline recipe
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_RadiationProof.get(1L),
                        WerkstoffMaterialPool.MuMetal.get(OrePrefixes.plateDense, 6),
                        GT_Utility.getIntegratedCircuit(6) },
                Materials.SolderingAlloy.getMolten(144),
                new ItemStack(LanthItemList.SHIELDED_ACCELERATOR_CASING, 1),
                800,
                7980);

        // Accelerator Electrode Casing
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { BW_GT_MaterialReference.Silver.get(blockCasingAdvanced, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Silver, 12),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Gold, 6),
                        GT_Utility.getIntegratedCircuit(6) },
                Materials.SolderingAlloy.getMolten(288),
                new ItemStack(LanthItemList.ELECTRODE_CASING, 1),
                800,
                7680);

        // Coolant Delivery Casing
        GT_Values.RA.addAssemblylineRecipe(
                ItemList.Casing_Pipe_TungstenSteel.get(1L),
                72000,
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 1),
                        Materials.Copper.getPlates(6),
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Naquadah, 2),
                        ItemList.Electric_Pump_LuV.get(3L), new ItemStack(LanthItemList.CAPILLARY_EXCHANGE, 1),
                        new ItemStack(LanthItemList.CAPILLARY_EXCHANGE, 1),
                        new ItemStack(LanthItemList.CAPILLARY_EXCHANGE, 1), CustomItemList.MicaInsulatorSheet.get(2),
                        CustomItemList.MicaInsulatorSheet.get(2), CustomItemList.MicaInsulatorSheet.get(2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Tungsten, 1) },
                new FluidStack[] { Materials.SolderingAlloy.getMolten(288), Materials.Lubricant.getFluid(1152) },
                new ItemStack(LanthItemList.COOLANT_DELIVERY_CASING),
                1000,
                7680);

        // Capillary Exchange
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.pipeTiny, Materials.TungstenSteel, 8),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 2),
                        Materials.Titanium.getPlates(6), CustomItemList.MicaInsulatorFoil.get(4),
                        ItemList.Electric_Pump_LuV.get(1), Materials.Silver.getDust(2) },
                Materials.Silicone.getMolten(288L),
                new ItemStack(LanthItemList.CAPILLARY_EXCHANGE, 1),
                400,
                7680);

        // Mu-metal lattice
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { WerkstoffMaterialPool.MuMetal.get(OrePrefixes.wireFine, 12),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 4) },
                Materials.SolderingAlloy.getMolten(144),
                new ItemStack(LanthItemList.MM_LATTICE, 1),
                300,
                1920);

        // Shielded Accelerator Glass
        GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(LanthItemList.MM_LATTICE, 4),
                Materials.BorosilicateGlass.getMolten(144),
                new ItemStack(LanthItemList.SHIELDED_ACCELERATOR_GLASS, 1),
                500,
                1920);

        // Beamline Pipe
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { WerkstoffMaterialPool.MuMetal.get(OrePrefixes.plate, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenCarbide, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenCarbide, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Osmiridium, 4),
                        GT_Utility.getIntegratedCircuit(7)

                },
                Materials.SolderingAlloy.getMolten(144),
                LanthItemList.BEAMLINE_PIPE,
                400,
                1920);
        
        // Masks
        // Quartz + Fe2O3 T1
        // " + Cr T2
        //
        GT_Values.RA.addAssemblerRecipe(
        		new ItemStack[] {
        				Materials.Quartz.getPlates(2),
        				Materials.Hematite.getDust(1)
        		},
        		Materials.Glass.getMolten(144),
        		new ItemStack(LanthItemList.maskMap.get(MaskList.BLANK1)),
        		400,
        		1920);

    }

    public static void loadGeneral() {

        /* ZIRCONIUM */
        // ZrCl4
        // ZrO2 + 4HCl = ZrCl4 + 2H2O
        GT_Values.RA.addChemicalRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.Zirconia.get(OrePrefixes.dust, 3),
                Materials.HydrochloricAcid.getFluid(4000),
                Materials.Water.getFluid(2000),
                WerkstoffMaterialPool.ZirconiumTetrachloride.get(OrePrefixes.dust, 5),
                300);

        // ZrCl4-H2O
        GT_Values.RA.addChemicalRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.ZirconiumTetrachloride.get(OrePrefixes.dust, 5),
                Materials.Water.getFluid(1000),
                WerkstoffMaterialPool.ZirconiumTetrachlorideSolution.getFluidOrGas(1000),
                null,
                200);

        // Zr
        // ZrCl4·H2O + 2Mg = Zr + 2MgCl2
        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(2),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2),
                WerkstoffMaterialPool.ZirconiumTetrachlorideSolution.getFluidOrGas(1000),
                null, // No fluid output
                // WerkstoffMaterialPool.Zirconium.get(OrePrefixes.ingotHot, 1),
                WerkstoffLoader.Zirconium.get(OrePrefixes.ingotHot, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesiumchloride, 6),
                600,
                1920,
                4500);

        /* HAFNIUM */
        // HfCl4
        // HfO2 + 4HCl = HfCl4 + 2H2O
        GT_Values.RA.addChemicalRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.Hafnia.get(OrePrefixes.dust, 3),
                Materials.HydrochloricAcid.getFluid(4000),
                Materials.Water.getFluid(2000),
                WerkstoffMaterialPool.HafniumTetrachloride.get(OrePrefixes.dust, 5),
                300);

        // HfCl4-H2O
        GT_Values.RA.addChemicalRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.HafniumTetrachloride.get(OrePrefixes.dust, 5),
                Materials.Water.getFluid(1000),
                WerkstoffMaterialPool.HafniumTetrachlorideSolution.getFluidOrGas(1000),
                null,
                200);

        // LP-Hf
        // HfCl4 + 2Mg = ??Hf?? + 2MgCl2
        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(2),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2),
                WerkstoffMaterialPool.HafniumTetrachlorideSolution.getFluidOrGas(1000),
                null, // No fluid output
                WerkstoffMaterialPool.LowPurityHafnium.get(OrePrefixes.dust, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesiumchloride, 6),
                600,
                1920,
                2700);

        // HfI4
        // ??Hf?? + 4I = HfI4
        GT_Values.RA.addChemicalRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.LowPurityHafnium.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.Iodine.getFluidOrGas(4000),
                null,
                WerkstoffMaterialPool.HafniumIodide.get(OrePrefixes.dust, 5),
                300);
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.LowPurityHafnium.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.Iodine.get(OrePrefixes.dust, 4),
                null,
                null,
                WerkstoffMaterialPool.HafniumIodide.get(OrePrefixes.dust, 5),
                300);

        // Hf
        // HfI4 = Hf + 4I
        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(12),
                WerkstoffMaterialPool.HafniumIodide.get(OrePrefixes.dust, 5),
                null,
                WerkstoffMaterialPool.Iodine.getFluidOrGas(4000),
                WerkstoffMaterialPool.Hafnium.get(OrePrefixes.ingotHot, 1),
                WerkstoffMaterialPool.HafniumRunoff.get(OrePrefixes.dustTiny, 1),
                600,
                1920,
                3400);

        // Hf * 9
        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(13),
                WerkstoffMaterialPool.HafniumIodide.get(OrePrefixes.dust, 45),
                null,
                WerkstoffMaterialPool.Iodine.getFluidOrGas(36000),
                WerkstoffMaterialPool.Hafnium.get(OrePrefixes.ingotHot, 9),
                WerkstoffMaterialPool.HafniumRunoff.get(OrePrefixes.dust, 1),
                5400,
                1920,
                3400);

        // Zirconia-Hafnia
        // ??HfZr?? = HfO2 + ZrO2
        GT_Values.RA.addCentrifugeRecipe(
                WerkstoffMaterialPool.HafniaZirconiaBlend.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                WerkstoffMaterialPool.Hafnia.get(OrePrefixes.dust, 3),
                WerkstoffMaterialPool.Zirconia.get(OrePrefixes.dust, 3),
                null,
                null,
                null,
                null,
                new int[] { 10000, 10000 },
                600,
                1920);

        // Ammonium Nitrate
        // HNO3 + NH3 = NH4NO3
        GT_Values.RA.addChemicalRecipeForBasicMachineOnly(
                GT_Utility.getIntegratedCircuit(12),
                Materials.NitricAcid.getCells(1),
                Materials.Ammonia.getGas(1000),
                WerkstoffMaterialPool.AmmoniumNitrate.getFluidOrGas(1000),
                Materials.Empty.getCells(1),
                null,
                30,
                400);

        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(12) },
                new FluidStack[] { Materials.NitricAcid.getFluid(1000), Materials.Ammonia.getGas(1000) },
                new FluidStack[] { WerkstoffMaterialPool.AmmoniumNitrate.getFluidOrGas(1000) },
                new ItemStack[] {},
                30,
                400);

        // IODINE-START
        // SeaweedAsh
        GT_ModHandler.addSmeltingRecipe(
                GT_ModHandler.getModItem("harvestcraft", "seaweedItem", 1),
                WerkstoffMaterialPool.SeaweedAsh.get(OrePrefixes.dustSmall, 1));

        // SeaweedConcentrate
        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.SeaweedAsh.get(OrePrefixes.dust, 2),
                null,
                null,
                null,
                Materials.DilutedSulfuricAcid.getFluid(1200),
                WerkstoffMaterialPool.SeaweedConcentrate.getFluidOrGas(1200),
                Materials.Calcite.getDust(1),
                600,
                240);

        // SeaweedConcentrate * 4
        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.SeaweedAsh.get(OrePrefixes.dust, 4),
                null,
                null,
                null,
                Materials.DilutedSulfuricAcid.getFluid(2400),
                WerkstoffMaterialPool.SeaweedConcentrate.getFluidOrGas(2400),
                Materials.Calcite.getDust(2),
                1200,
                240);

        // Iodine
        GT_Values.RA.addCentrifugeRecipe(
                Materials.Benzene.getCells(1),
                null,
                WerkstoffMaterialPool.SeaweedConcentrate.getFluidOrGas(2000),
                WerkstoffMaterialPool.SeaweedByproducts.getFluidOrGas(200),
                Materials.Empty.getCells(1),
                WerkstoffMaterialPool.Iodine.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                null,
                new int[] { 10000, 10000 },
                760,
                480);

        // IODINE-END

        // 2MnO2 + 2KOH + KClO3 = 2KMnO4 + H2O + KCl
        GT_Values.RA.addBlastRecipe(
                Materials.Pyrolusite.getDust(6),
                GT_ModHandler.getModItem("dreamcraft", "item.PotassiumHydroxideDust", 6),
                WerkstoffMaterialPool.PotassiumChlorate.get(OrePrefixes.dust, 5),
                null,
                null,
                Materials.Water.getFluid(1000),
                WerkstoffMaterialPool.PotassiumPermanganate.get(OrePrefixes.dust, 12),
                Materials.RockSalt.getDust(2),
                null,
                null,
                150,
                480,
                1200);

        // Mn + 2O = MnO2
        GT_Values.RA.addChemicalRecipe(
                Materials.Manganese.getDust(1),
                GT_Utility.getIntegratedCircuit(1),
                Materials.Oxygen.getGas(2000),
                null,
                Materials.Pyrolusite.getDust(3),
                40,
                30);

        // 6KOH + 6Cl = KClO3 + 5KCl + 3H2O
        GT_Values.RA.addChemicalRecipe(
                GT_ModHandler.getModItem("dreamcraft", "item.PotassiumHydroxideDust", 18),
                GT_Utility.getIntegratedCircuit(3),
                Materials.Chlorine.getGas(6000),
                Materials.Water.getFluid(3000),
                Materials.RockSalt.getDust(10),
                WerkstoffMaterialPool.PotassiumChlorate.get(OrePrefixes.dust, 5),
                40,
                30);

        /*
         * //Fluorosilicic Acid GT_Values.RA.addChemicalRecipe( GT_Utility.getIntegratedCircuit(1),
         * Materials.SiliconDioxide.getDust(1), Materials.HydrofluoricAcid.getFluid(6000),
         * WerkstoffMaterialPool.FluorosilicicAcid.getFluidOrGas(1000), null, 300, 600 );
         */
        // Sodium Fluorosilicate
        // 2NaCl + H2SiF6 = 2HCl + Na2SiF6
        GT_Values.RA.addChemicalRecipe(
                Materials.Empty.getCells(2),
                Materials.Salt.getDust(4),
                WerkstoffLoader.HexafluorosilicicAcid.getFluidOrGas(1000),
                WerkstoffMaterialPool.SodiumFluorosilicate.getFluidOrGas(1000),
                Materials.HydrochloricAcid.getCells(2),
                600,
                450);
    }

    public static void loadLanthanideRecipes() {
        // Methanol
        // CH4O + CO + 3O =V2O5= H2C2O4 + H2O
        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { MyMaterial.vanadiumPentoxide.get(OrePrefixes.dustTiny, 1) },
                new FluidStack[] { Materials.Methanol.getFluid(1000), Materials.CarbonMonoxide.getGas(1000),
                        Materials.Oxygen.getGas(3000) },
                new FluidStack[] { MyMaterial.oxalate.getFluidOrGas(1000), Materials.Water.getFluid(1000) },
                null,
                450,
                240);

        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(9),
                        MyMaterial.vanadiumPentoxide.get(OrePrefixes.dust, 1) },
                new FluidStack[] { Materials.Methanol.getFluid(9000), Materials.CarbonMonoxide.getGas(9000),
                        Materials.Oxygen.getGas(27000) },
                new FluidStack[] { MyMaterial.oxalate.getFluidOrGas(9000), Materials.Water.getFluid(9000) },
                null,
                4050,
                240);

        // Ethanol
        // C2H6O + 5O =V2O5= H2C2O4 + 2H2O
        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { MyMaterial.vanadiumPentoxide.get(OrePrefixes.dustTiny, 1) },
                new FluidStack[] { Materials.Ethanol.getFluid(1000), Materials.Oxygen.getGas(5000) },
                new FluidStack[] { MyMaterial.oxalate.getFluidOrGas(1000), Materials.Water.getFluid(2000) },
                null,
                450,
                240);

        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(9),
                        MyMaterial.vanadiumPentoxide.get(OrePrefixes.dust, 1) },
                new FluidStack[] { Materials.Ethanol.getFluid(9000), Materials.Oxygen.getGas(45000) },
                new FluidStack[] { MyMaterial.oxalate.getFluidOrGas(9000), Materials.Water.getFluid(18000) },
                null,
                4050,
                240);

        // GT_Values.RA.addChemicalRecipe(
        // GT_Utility.getIntegratedCircuit(2),
        // WerkstoffMaterialPool.CeriumDioxide
        //
        // )

        // Cerium Oxalate
        // 2CeCl3 + 3H2C2O4 = 6HCl + Ce2(C2O4)3
        GT_Values.RA.addChemicalRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.CeriumChloride.get(OrePrefixes.dust, 8),
                MyMaterial.oxalate.getFluidOrGas(3000),
                Materials.HydrochloricAcid.getFluid(6000),
                WerkstoffMaterialPool.CeriumOxalate.get(OrePrefixes.dust, 5),
                null,
                300,
                450);

        // Cerium
        // Ce2O3 = 2Ce + 3O
        GT_Values.RA.addElectrolyzerRecipe(
                WerkstoffMaterialPool.CeriumIIIOxide.get(OrePrefixes.dust, 5),
                null,
                null,
                Materials.Oxygen.getFluid(3000),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cerium, 2),
                null,
                null,
                null,
                null,
                null,
                new int[] { 10000 },
                150,
                120);

        // CHAIN BEGIN
        // MONAZITE
        RecipeAdder.instance.DigesterRecipes.addDigesterRecipe(
                new FluidStack[] { Materials.NitricAcid.getFluid(700) },
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.crushed, Materials.Monazite, 2) },
                WerkstoffMaterialPool.MuddyRareEarthMonaziteSolution.getFluidOrGas(400),
                new ItemStack[] { Materials.SiliconDioxide.getDust(1) },
                1920,
                400,
                800);

        RecipeAdder.instance.DissolutionTankRecipes.addDissolutionTankRecipe(
                new FluidStack[] { Materials.Water.getFluid(10000),
                        WerkstoffMaterialPool.MuddyRareEarthMonaziteSolution.getFluidOrGas(1000) },
                new ItemStack[] { GT_Utility.getIntegratedCircuit(1), Materials.Saltpeter.getDust(1) },
                WerkstoffMaterialPool.DilutedRareEarthMonaziteMud.getFluidOrGas(11000),
                new ItemStack[] { WerkstoffMaterialPool.HafniaZirconiaBlend.get(OrePrefixes.dustTiny, 4),
                        WerkstoffLoader.Thorianit.get(OrePrefixes.dust, 1), Materials.Monazite.getDustTiny(2) },
                480,
                900,
                10);

        RecipeAdder.instance.DissolutionTankRecipes.addDissolutionTankRecipe(
                new FluidStack[] { Materials.Water.getFluid(90000),
                        WerkstoffMaterialPool.MuddyRareEarthMonaziteSolution.getFluidOrGas(9000) },
                new ItemStack[] { GT_Utility.getIntegratedCircuit(9), Materials.Saltpeter.getDust(9) },
                WerkstoffMaterialPool.DilutedRareEarthMonaziteMud.getFluidOrGas(99000),
                new ItemStack[] { WerkstoffMaterialPool.HafniaZirconiaBlend.get(OrePrefixes.dust, 4),
                        WerkstoffLoader.Thorianit.get(OrePrefixes.dust, 9), Materials.Monazite.getDust(2) },
                480,
                8100,
                10);

        GT_Recipe.GT_Recipe_Map.sSifterRecipes.addRecipe(
                false,
                null,
                new ItemStack[] { WerkstoffMaterialPool.MonaziteSulfate.get(OrePrefixes.dust, 1),
                        Materials.SiliconDioxide.getDust(1), Materials.Rutile.getDust(1),
                        WerkstoffLoader.RedZircon.get(OrePrefixes.dust, 1), Materials.Ilmenite.getDust(1) },
                null,
                new int[] { 9000, 7500, 2000, 500, 2000 },
                new FluidStack[] { WerkstoffMaterialPool.DilutedRareEarthMonaziteMud.getFluidOrGas(1000) },
                null,
                400,
                240,
                0);

        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.MonaziteSulfate.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                null,
                null,
                Materials.Water.getFluid(6000),
                WerkstoffMaterialPool.DilutedMonaziteSulfate.getFluidOrGas(7000),
                null,
                480,
                400);

        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(13) },
                new FluidStack[] { WerkstoffMaterialPool.DilutedMonaziteSulfate.getFluidOrGas(1000),
                        WerkstoffMaterialPool.AmmoniumNitrate.getFluidOrGas(200) },
                null,
                new ItemStack[] { WerkstoffMaterialPool.AcidicMonazitePowder.get(OrePrefixes.dustTiny, 3), },
                480,
                480);

        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(14) },
                new FluidStack[] { WerkstoffMaterialPool.DilutedMonaziteSulfate.getFluidOrGas(9000),
                        WerkstoffMaterialPool.AmmoniumNitrate.getFluidOrGas(1800) },
                null,
                new ItemStack[] { WerkstoffMaterialPool.AcidicMonazitePowder.get(OrePrefixes.dust, 3), },
                4320,
                480);

        GT_Values.RA.addSifterRecipe(
                WerkstoffMaterialPool.AcidicMonazitePowder.get(OrePrefixes.dust, 1),
                new ItemStack[] { WerkstoffMaterialPool.MonaziteRareEarthFiltrate.get(OrePrefixes.dust, 1),
                        WerkstoffMaterialPool.ThoriumPhosphateCake.get(OrePrefixes.dust, 1) },
                new int[] { 9000, 7000 },
                600,
                256);

        GT_Values.RA.addBlastRecipe(
                WerkstoffMaterialPool.ThoriumPhosphateCake.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                WerkstoffMaterialPool.ThoriumPhosphateConcentrate.get(OrePrefixes.dust, 1),
                null,
                300,
                128,
                1500);

        GT_Values.RA.addThermalCentrifugeRecipe(
                WerkstoffMaterialPool.ThoriumPhosphateConcentrate.get(OrePrefixes.dust),
                Materials.Thorium.getDust(1),
                Materials.Phosphate.getDust(1),
                null,
                new int[] { 10000, 10000 },
                200,
                480);

        GT_Values.RA.addChemicalBathRecipe(
                WerkstoffMaterialPool.MonaziteRareEarthFiltrate.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.AmmoniumNitrate.getFluidOrGas(320),
                WerkstoffMaterialPool.NeutralizedMonaziteRareEarthFiltrate.get(OrePrefixes.dust, 1),
                null,
                null,
                new int[] { 10000 },
                120,
                240);

        GT_Values.RA.addSifterRecipe(
                WerkstoffMaterialPool.NeutralizedMonaziteRareEarthFiltrate.get(OrePrefixes.dust, 1),
                new ItemStack[] { WerkstoffMaterialPool.MonaziteRareEarthHydroxideConcentrate.get(OrePrefixes.dust, 1),
                        WerkstoffMaterialPool.UraniumFiltrate.get(OrePrefixes.dust, 1),
                        WerkstoffMaterialPool.UraniumFiltrate.get(OrePrefixes.dust, 1) },
                new int[] { 9000, 5000, 4000 },
                800,
                480);

        GT_Values.RA.addChemicalBathRecipe(
                WerkstoffMaterialPool.UraniumFiltrate.get(OrePrefixes.dust, 1),
                Materials.HydrofluoricAcid.getFluid(100),
                WerkstoffMaterialPool.NeutralizedUraniumFiltrate.get(OrePrefixes.dust, 1),
                null,
                null,
                new int[] { 10000 },
                360,
                120);

        GT_Values.RA.addSifterRecipe(
                WerkstoffMaterialPool.NeutralizedUraniumFiltrate.get(OrePrefixes.dust, 1),
                new ItemStack[] { Materials.Fluorite.getDust(1), Materials.Uranium.getDust(1),
                        Materials.Uranium.getDust(1), Materials.Uranium.getDust(1), Materials.Uranium235.getDust(1),
                        Materials.Uranium235.getDust(1), },
                new int[] { 9500, 4500, 4000, 3000, 3000, 2000 },
                1000,
                30);

        GT_Values.RA.addBlastRecipe(
                WerkstoffMaterialPool.MonaziteRareEarthHydroxideConcentrate.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                WerkstoffMaterialPool.DriedMonaziteRareEarthConcentrate.get(OrePrefixes.dust, 1),
                null,
                300,
                120,
                1200);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.DriedMonaziteRareEarthConcentrate.get(OrePrefixes.dust, 1),
                null,
                Materials.NitricAcid.getFluid(500),
                WerkstoffMaterialPool.NitratedRareEarthMonaziteConcentrate.getFluidOrGas(1000),
                null,
                500,
                480);

        GT_Values.RA.addMixerRecipe(
                Materials.Water.getCells(1),
                null,
                null,
                null,
                WerkstoffMaterialPool.NitratedRareEarthMonaziteConcentrate.getFluidOrGas(1000),
                WerkstoffMaterialPool.NitricLeachedMonaziteMixture.getFluidOrGas(1000),
                Materials.Empty.getCells(1),
                200,
                120);

        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 3),
                null,
                null,
                null,
                WerkstoffMaterialPool.NitratedRareEarthMonaziteConcentrate.getFluidOrGas(1000),
                WerkstoffMaterialPool.NitricLeachedMonaziteMixture.getFluidOrGas(2000),
                null,
                220,
                120);

        GT_Recipe.GT_Recipe_Map.sSifterRecipes.addRecipe(
                false,
                null,
                new ItemStack[] { WerkstoffMaterialPool.CeriumDioxide.get(OrePrefixes.dust, 1) },
                null,
                new int[] { 1111 },
                new FluidStack[] { WerkstoffMaterialPool.NitricLeachedMonaziteMixture.getFluidOrGas(1000) },
                new FluidStack[] { WerkstoffMaterialPool.NitricMonaziteLeachedConcentrate.getFluidOrGas(1000) },
                400,
                240,
                0);

        // BEGIN Cerium
        // Cerium-rich mixture + 3HCl = CeCl3 + Monazite (to allow cerium processing without bastnazite/monazite)
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 15),
                null,
                Materials.HydrochloricAcid.getFluid(750),
                Materials.Water.getFluid(750),
                WerkstoffMaterialPool.CeriumChloride.get(OrePrefixes.dust, 1),
                Materials.Monazite.getDust(1),
                300,
                450);
        // CeO2 + 3NH4Cl + H = 3NH3 + CeCl3 + 2H2O
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.CeriumDioxide.get(OrePrefixes.dust, 3),
                WerkstoffLoader.AmmoniumChloride.get(OrePrefixes.cell, 3),
                Materials.Hydrogen.getGas(1000),
                Materials.Water.getGas(2000),
                WerkstoffMaterialPool.CeriumChloride.get(OrePrefixes.dust, 4),
                Materials.Ammonia.getCells(3),
                300,
                450);

        // Ce2(C2O4)3 + 3C = Ce2O3 + 9CO
        GT_Values.RA.addBlastRecipe(
                WerkstoffMaterialPool.CeriumOxalate.get(OrePrefixes.dust, 5),
                Materials.Carbon.getDust(3),
                null,
                Materials.CarbonMonoxide.getGas(9000),
                WerkstoffMaterialPool.CeriumIIIOxide.get(OrePrefixes.dust, 5),
                null,
                200,
                480,
                800);

        // END Cerium (NMLC)

        GT_Recipe.GT_Recipe_Map.sVacuumRecipes.addRecipe( // Uses fluid, outputs item. Yet another hacky recipe
                false,
                null,
                new ItemStack[] { WerkstoffMaterialPool.CooledMonaziteRareEarthConcentrate.get(OrePrefixes.dust, 1), // TODO:
                                                                                                                     // Perhaps
                                                                                                                     // add
                                                                                                                     // more
                                                                                                                     // shit
                                                                                                                     // on
                                                                                                                     // output
                },
                null,
                new FluidStack[] { WerkstoffMaterialPool.NitricMonaziteLeachedConcentrate.getFluidOrGas(1000) },
                null,
                100,
                240,
                0);

        GT_Values.RA.addElectromagneticSeparatorRecipe(
                WerkstoffMaterialPool.CooledMonaziteRareEarthConcentrate.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.MonaziteRarerEarthSediment.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.EuropiumIIIOxide.get(OrePrefixes.dust, 5), // Maybe also holmium
                null,
                new int[] { 9000, 500 },
                600,
                1920);

        // 5Eu2O3 + Eu = 4EuO
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.EuropiumIIIOxide.get(OrePrefixes.dust, 5),
                Materials.Europium.getDust(1),
                null,
                null,
                WerkstoffMaterialPool.EuropiumOxide.get(OrePrefixes.dust, 4),
                300,
                8400);

        // 4 EuO = 2 Eu + 2O2
        GT_Values.RA.addElectrolyzerRecipe(
                WerkstoffMaterialPool.EuropiumOxide.get(OrePrefixes.dust, 2),
                null,
                null,
                Materials.Oxygen.getGas(1000L),
                Materials.Europium.getDust(1),
                null,
                null,
                null,
                null,
                null,
                new int[] { 10000, 10000 },
                300,
                33000);

        // EuS = Eu + S
        // TODO old recipe. for compat only. remove material and recipe half a year later, i.e. after September 2023.
        GT_Values.RA.addElectrolyzerRecipe(
                WerkstoffMaterialPool.EuropiumSulfide.get(OrePrefixes.dust, 2),
                null,
                null,
                null,
                Materials.Europium.getDust(1),
                Materials.Sulfur.getDust(1),
                null,
                null,
                null,
                null,
                new int[] { 10000, 10000 },
                600,
                33000);

        GT_Values.RA.addBlastRecipe(
                WerkstoffMaterialPool.MonaziteRarerEarthSediment.get(OrePrefixes.dust, 1),
                null,
                Materials.Chlorine.getGas(1000),
                null,
                WerkstoffMaterialPool.MonaziteHeterogenousHalogenicRareEarthMixture.get(OrePrefixes.dust, 1),
                null,
                500,
                480,
                1200);

        GT_Values.RA.addMixerRecipe(
                Materials.Salt.getDust(1),
                WerkstoffMaterialPool.MonaziteHeterogenousHalogenicRareEarthMixture.get(OrePrefixes.dust, 1),
                null,
                null,
                Materials.Acetone.getFluid(1000),
                null,
                WerkstoffMaterialPool.SaturatedMonaziteRareEarthMixture.get(OrePrefixes.dust, 1),
                200,
                240);

        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.MonaziteHeterogenousHalogenicRareEarthMixture.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 2),
                null,
                null,
                Materials.Acetone.getFluid(1000),
                null,
                WerkstoffMaterialPool.SaturatedMonaziteRareEarthMixture.get(OrePrefixes.dust, 3),
                400,
                240);
        /*
         * GT_Values.RA.addCentrifugeRecipe(
         * WerkstoffMaterialPool.SaturatedMonaziteRareEarthMixture.get(OrePrefixes.dust, 2), null, null,
         * Materials.Chloromethane.getGas(200), WerkstoffMaterialPool.SamaricResidue.get(OrePrefixes.dustSmall, 6),
         * null, //TODO null, null, null, null, new int[] { 10000, //10000 }, 700, 1920 );
         */
        GT_Values.RA.addCentrifugeRecipe(
                GT_Utility.getIntegratedCircuit(4),
                WerkstoffMaterialPool.SaturatedMonaziteRareEarthMixture.get(OrePrefixes.dust, 8),
                null,
                Materials.Chloromethane.getGas(800),
                WerkstoffMaterialPool.SamaricResidue.get(OrePrefixes.dust, 6),
                null, // WerkstoffMaterialPool.UnknownBlend.get(OrePrefixes.dust, 2) TODO
                null,
                null,
                null,
                null,
                new int[] { 10000, // 10000
                },
                6300,
                1920);

        GT_Values.RA.addSifterRecipe(
                WerkstoffMaterialPool.SamaricResidue.get(OrePrefixes.dust, 9),
                new ItemStack[] { Materials.Samarium.getDust(6), Materials.Gadolinium.getDust(3) },
                new int[] { 10000, 10000 },
                400,
                1920);

        // BASTNASITE (god help me)
        RecipeAdder.instance.DigesterRecipes.addDigesterRecipe(
                new FluidStack[] { Materials.NitricAcid.getFluid(700) },
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.crushed, Materials.Bastnasite, 2) },
                WerkstoffMaterialPool.MuddyRareEarthBastnasiteSolution.getFluidOrGas(400),
                new ItemStack[] { Materials.SiliconDioxide.getDust(1) },
                1920,
                400,
                800);

        GT_Values.RA.addCrackingRecipe(
                1,
                WerkstoffMaterialPool.MuddyRareEarthBastnasiteSolution.getFluidOrGas(1000),
                GT_ModHandler.getSteam(1000),
                WerkstoffMaterialPool.SteamCrackedBasnasiteSolution.getFluidOrGas(2000),
                600,
                480);

        GT_Values.RA.addMixerRecipe(
                GT_Utility.getIntegratedCircuit(6),
                WerkstoffMaterialPool.SteamCrackedBasnasiteSolution.get(OrePrefixes.cell, 1),
                null,
                null,
                WerkstoffMaterialPool.SodiumFluorosilicate.getFluidOrGas(320),
                WerkstoffMaterialPool.ConditionedBastnasiteMud.getFluidOrGas(1320),
                Materials.Empty.getCells(1),
                800,
                120);

        RecipeAdder.instance.DissolutionTankRecipes.addDissolutionTankRecipe(
                new FluidStack[] { Materials.Water.getFluid(10000),
                        WerkstoffMaterialPool.ConditionedBastnasiteMud.getFluidOrGas(1000) },
                new ItemStack[] { Materials.Saltpeter.getDust(1) },
                WerkstoffMaterialPool.DiltedRareEarthBastnasiteMud.getFluidOrGas(11000),
                new ItemStack[] { WerkstoffMaterialPool.Gangue.get(OrePrefixes.dust, 1) },
                1920,
                1000,
                10);

        GT_Recipe.GT_Recipe_Map.sSifterRecipes.addRecipe(
                false,
                null,
                new ItemStack[] { Materials.SiliconDioxide.getDust(1), Materials.Rutile.getDust(1),
                        WerkstoffLoader.RedZircon.get(OrePrefixes.dust, 1), // TODO:Change outputs to complement
                                                                            // Monazite
                        Materials.Ilmenite.getDust(1) },
                null,
                new int[] { 9000, 7500, 1000, 500, 2000 },
                new FluidStack[] { WerkstoffMaterialPool.DiltedRareEarthBastnasiteMud.getFluidOrGas(1000) },
                new FluidStack[] { WerkstoffMaterialPool.FilteredBastnasiteMud.getFluidOrGas(400) },
                400,
                240,
                0);

        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(1),
                null,
                WerkstoffMaterialPool.FilteredBastnasiteMud.getFluidOrGas(1000),
                null, // TODO: Maybe add some useful shit?
                WerkstoffMaterialPool.BastnasiteRareEarthOxidePowder.get(OrePrefixes.dust, 1),
                null, // See above
                500,
                600,
                1400);

        GT_Values.RA.addChemicalBathRecipe(
                WerkstoffMaterialPool.BastnasiteRareEarthOxidePowder.get(OrePrefixes.dust, 1),
                Materials.HydrochloricAcid.getFluid(500),
                WerkstoffMaterialPool.LeachedBastnasiteRareEarthOxides.get(OrePrefixes.dust, 1),
                null,
                null,
                new int[] { 10000 },
                200,
                30);

        GT_Values.RA.addBlastRecipe(
                GT_Utility.getIntegratedCircuit(1),
                WerkstoffMaterialPool.LeachedBastnasiteRareEarthOxides.get(OrePrefixes.dust, 1),
                Materials.Oxygen.getGas(1000),
                Materials.Fluorine.getGas(13),
                WerkstoffMaterialPool.RoastedRareEarthOxides.get(OrePrefixes.dust, 1),
                null,
                600,
                120,
                1200);

        GT_Values.RA.addMixerRecipe(
                GT_Utility.getIntegratedCircuit(7),
                WerkstoffMaterialPool.RoastedRareEarthOxides.get(OrePrefixes.dust, 1),
                null,
                null,
                Materials.Water.getFluid(200),
                null,
                WerkstoffMaterialPool.WetRareEarthOxides.get(OrePrefixes.dust, 1),
                100,
                30);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.WetRareEarthOxides.get(OrePrefixes.dust, 1),
                null,
                Materials.Fluorine.getGas(4000),
                Materials.HydrofluoricAcid.getFluid(4000),
                WerkstoffMaterialPool.CeriumOxidisedRareEarthOxides.get(OrePrefixes.dust, 1),
                300,
                480);

        GT_Values.RA.addCentrifugeRecipe(
                WerkstoffMaterialPool.CeriumOxidisedRareEarthOxides.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                WerkstoffMaterialPool.BastnasiteRarerEarthOxides.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.CeriumDioxide.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                null,
                new int[] { 10000, 9000 },
                600,
                480);

        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.BastnasiteRarerEarthOxides.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                Materials.NitricAcid.getFluid(400),
                WerkstoffMaterialPool.NitratedBastnasiteRarerEarthOxides.getFluidOrGas(1000),
                null,
                300,
                480);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.NitratedBastnasiteRarerEarthOxides.get(OrePrefixes.cell, 1),
                null,
                Materials.Acetone.getFluid(1000),
                WerkstoffMaterialPool.SaturatedBastnasiteRarerEarthOxides.getFluidOrGas(1000),
                Materials.Empty.getCells(1),
                700,
                480);

        GT_Values.RA.addCentrifugeRecipe(
                null,
                null,
                WerkstoffMaterialPool.SaturatedBastnasiteRarerEarthOxides.getFluidOrGas(1000),
                WerkstoffMaterialPool.DilutedAcetone.getFluidOrGas(750),
                WerkstoffMaterialPool.NeodymicRareEarthConcentrate.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.SamaricRareEarthConcentrate.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                null,
                new int[] { 8000, 5000 },
                900,
                480);

        // Nd RE
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.NeodymicRareEarthConcentrate.get(OrePrefixes.dust, 2),
                null,
                Materials.HydrochloricAcid.getFluid(2000),
                null,
                WerkstoffMaterialPool.LanthaniumChloride.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.NeodymiumOxide.get(OrePrefixes.dust, 1),
                900,
                800);

        // Sm RE
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.SamaricRareEarthConcentrate.get(OrePrefixes.dust, 1),
                null,
                Materials.HydrofluoricAcid.getFluid(2000),
                null,
                WerkstoffMaterialPool.FluorinatedSamaricConcentrate.get(OrePrefixes.dust, 1),
                null,
                300,
                480);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.SamaricRareEarthConcentrate.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1),
                Materials.HydrofluoricAcid.getFluid(2000),
                null,
                WerkstoffMaterialPool.FluorinatedSamaricConcentrate.get(OrePrefixes.dust, 2),
                null,
                350,
                480);

        GT_Values.RA.addBlastRecipe(
                WerkstoffMaterialPool.FluorinatedSamaricConcentrate.get(OrePrefixes.dust, 8),
                Materials.Calcium.getDust(4),
                null,
                WerkstoffMaterialPool.CalciumFluoride.getFluidOrGas(12000),
                Materials.Holmium.getDust(1),
                WerkstoffMaterialPool.SamariumTerbiumMixture.get(OrePrefixes.dust, 4),
                1600,
                1920,
                1200);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.SamariumTerbiumMixture.get(OrePrefixes.dust, 1),
                BotWerkstoffMaterialPool.AmmoniumNitrate.get(OrePrefixes.dust, 9),
                null,
                null,
                WerkstoffMaterialPool.NitratedSamariumTerbiumMixture.get(OrePrefixes.dust, 1),
                null,
                600,
                480);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.NitratedSamariumTerbiumMixture.get(OrePrefixes.dust, 4),
                Materials.Copper.getDust(1),
                null,
                null,
                WerkstoffMaterialPool.TerbiumNitrate.get(OrePrefixes.dust, 2),
                WerkstoffMaterialPool.SamaricResidue.get(OrePrefixes.dust, 2), // Potentially make only Samarium
                3200,
                1920);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 2),
                Materials.Calcium.getDust(3),
                null,
                null,
                WerkstoffMaterialPool.DephosphatedSamariumConcentrate.get(OrePrefixes.dust, 1),
                Materials.TricalciumPhosphate.getDust(5),
                300,
                1920);

        GT_Values.RA.addCentrifugeRecipe(
                WerkstoffMaterialPool.DephosphatedSamariumConcentrate.get(OrePrefixes.dust, 6),
                null,
                null,
                null,
                Materials.Samarium.getDust(1),
                WerkstoffLoader.Thorianit.get(OrePrefixes.dust, 2),
                WerkstoffMaterialPool.Gangue.get(OrePrefixes.dust, 4),
                null,
                null,
                null,
                new int[] { 9000, 8000, 10000 },
                200,
                1920);
    }

    public static void addRandomChemCrafting() {

        // PTMEG Elastomer
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.Butanediol.get(OrePrefixes.cell, 1),
                null,
                WerkstoffMaterialPool.TolueneTetramethylDiisocyanate.getFluidOrGas(4000),
                WerkstoffMaterialPool.PTMEGElastomer.getMolten(4000),
                Materials.Empty.getCells(1),
                1500,
                480);

        // Toluene Tetramethyl Diisocyanate
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.TolueneDiisocyanate.get(OrePrefixes.cell, 3),
                Materials.Hydrogen.getCells(2),
                WerkstoffMaterialPool.Polytetrahydrofuran.getFluidOrGas(1000),
                WerkstoffMaterialPool.TolueneTetramethylDiisocyanate.getFluidOrGas(2000),
                Materials.Empty.getCells(5),
                1200,
                480);

        // PTHF
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.TungstophosphoricAcid.get(OrePrefixes.cell, 1),
                Materials.Oxygen.getCells(1),
                WerkstoffMaterialPool.Tetrahydrofuran.getFluidOrGas(144),
                WerkstoffMaterialPool.Polytetrahydrofuran.getFluidOrGas(432),
                Materials.Empty.getCells(2),
                1000,
                120);

        // THF
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.AcidicButanediol.get(OrePrefixes.cell, 1),
                null,
                Materials.Ethanol.getFluid(1000),
                WerkstoffMaterialPool.Tetrahydrofuran.getFluidOrGas(1000),
                Materials.Empty.getCells(1),
                800,
                480);

        // Acidicised Butanediol
        GT_Values.RA.addMixerRecipe(
                Materials.SulfuricAcid.getCells(1),
                null,
                null,
                null,
                WerkstoffMaterialPool.Butanediol.getFluidOrGas(1000),
                WerkstoffMaterialPool.AcidicButanediol.getFluidOrGas(1000),
                Materials.Water.getCells(1),
                600,
                2000);

        // Butanediol
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.MoTeOCatalyst.get(OrePrefixes.dustTiny, 1),
                null,
                Materials.Butane.getGas(1000),
                WerkstoffMaterialPool.Butanediol.getFluidOrGas(1000),
                null,
                900,
                1920);

        GT_Values.RA.addMultiblockChemicalRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(9),
                        WerkstoffMaterialPool.MoTeOCatalyst.get(OrePrefixes.dust, 1) },
                new FluidStack[] { Materials.Butane.getGas(9000) },
                new FluidStack[] { WerkstoffMaterialPool.Butanediol.getFluidOrGas(9000) },
                null,
                8100,
                1920);

        // Moly-Te-Oxide Catalyst
        GT_Values.RA.addMixerRecipe(
                WerkstoffMaterialPool.MolybdenumIVOxide.get(OrePrefixes.dust, 1),
                WerkstoffMaterialPool.TelluriumIVOxide.get(OrePrefixes.dust, 1),
                null,
                null,
                null,
                null,
                WerkstoffMaterialPool.MoTeOCatalyst.get(OrePrefixes.dust, 2),
                300,
                120);

        GT_Values.RA.addChemicalRecipe(
                Materials.Molybdenite.getDust(1),
                null,
                Materials.Oxygen.getGas(7000),
                Materials.SulfurDioxide.getGas(2000),
                WerkstoffMaterialPool.MolybdenumTrioxide.get(OrePrefixes.dust, 1),
                null,
                480,
                120);

        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.MolybdenumTrioxide.get(OrePrefixes.dust, 1),
                null,
                Materials.Hydrogen.getGas(6000),
                Materials.Water.getFluid(3000),
                WerkstoffMaterialPool.MolybdenumIVOxide.get(OrePrefixes.dust, 1),
                null,
                480,
                120);

        // Tungstophosphoric Acid
        GT_Values.RA.addChemicalRecipe(
                Materials.PhosphoricAcid.getCells(1),
                Materials.HydrochloricAcid.getCells(24),
                BotWerkstoffMaterialPool.SodiumTungstate.getFluidOrGas(12000),
                WerkstoffMaterialPool.TungstophosphoricAcid.getFluidOrGas(1000),
                Materials.Salt.getDust(24),
                Materials.Empty.getCells(25),
                500,
                1024);

        // Toluene Diisocyanate
        GT_Values.RA.addChemicalRecipe(
                WerkstoffMaterialPool.Diaminotoluene.get(OrePrefixes.cell, 1),
                Materials.Empty.getCells(3),
                BotWerkstoffMaterialPool.Phosgene.getFluidOrGas(2000),
                WerkstoffMaterialPool.TolueneDiisocyanate.getFluidOrGas(1000),
                Materials.HydrochloricAcid.getCells(4),
                900,
                480);

        // Diaminotoluene
        GT_Values.RA.addChemicalRecipe(
                Materials.Hydrogen.getCells(4),
                null,
                WerkstoffMaterialPool.Dinitrotoluene.getFluidOrGas(1000),
                WerkstoffMaterialPool.Diaminotoluene.getFluidOrGas(1000),
                Materials.Empty.getCells(4),
                300,
                480);

        // Dinitrotoluene
        GT_Values.RA.addChemicalRecipe(
                Materials.NitricAcid.getCells(2),
                null,
                Materials.Toluene.getFluid(1000),
                WerkstoffMaterialPool.Dinitrotoluene.getFluidOrGas(1000),
                Materials.Empty.getCells(2),
                900,
                480);
        // Digester Control Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Hull_IV.get(1L), ItemList.Super_Tank_EV.get(2L),
                        ItemList.Electric_Motor_IV.get(4L), ItemList.Electric_Pump_IV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Desh, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Polytetrafluoroethylene.getMolten(1440),
                LanthItemList.DIGESTER,
                600,
                4096);

        // Dissolution Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Hull_EV.get(1L), ItemList.Super_Tank_HV.get(2L),
                        ItemList.Electric_Motor_EV.get(4L), ItemList.Electric_Pump_EV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.VibrantAlloy, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4L),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Polytetrafluoroethylene.getMolten(720),
                LanthItemList.DISSOLUTION_TANK,
                400,
                960);

        GT_Values.RA.addMixerRecipe(
                Materials.Nichrome.getDust(4),
                Materials.Aluminium.getDust(2),
                WerkstoffMaterialPool.Hafnium.get(OrePrefixes.dust, 1),
                Materials.Tantalum.getDust(2),
                Materials.Tungsten.getDust(5),
                WerkstoffMaterialPool.Zirconium.get(OrePrefixes.dust, 1),
                Materials.Titanium.getDust(1),
                null,
                null,
                null,
                null,
                WerkstoffMaterialPool.MARM247.get(OrePrefixes.dust, 16),
                null,
                null,
                null,
                800,
                7680);

        GT_Values.RA.addFluidHeaterRecipe(
                null,
                WerkstoffMaterialPool.DilutedAcetone.getFluidOrGas(250),
                Materials.Acetone.getFluid(150),
                120,
                120);

        // PTMEG Manipulation

        GT_Values.RA.addFluidSolidifierRecipe(
                ItemList.Shape_Mold_Ingot.get(0L),
                WerkstoffMaterialPool.PTMEGElastomer.getMolten(144),
                WerkstoffMaterialPool.PTMEGElastomer.get(OrePrefixes.ingot, 1),
                40,
                64);

        GT_Values.RA.addFluidSolidifierRecipe(
                ItemList.Shape_Mold_Plate.get(0L),
                WerkstoffMaterialPool.PTMEGElastomer.getMolten(144),
                WerkstoffMaterialPool.PTMEGElastomer.get(OrePrefixes.plate, 1),
                40,
                64);
    }

    // public static void loadZylon

    public static void removeCeriumSources() {

        GT_Log.out.print(Tags.MODID + ": AAAAAA");

        HashSet<GT_Recipe> remove = new HashSet<>(5000);
        HashSet<GT_Recipe> reAdd = new HashSet<>(5000);

        // For Crusher
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.mRecipeList) {
            ItemStack input = recipe.mInputs[0];
            // GT_Log.out.print("\n" + input.getDisplayName());
            if (GT_Utility.isStackValid(input)) {
                int[] oreDict = OreDictionary.getOreIDs(input);
                for (int oreDictID : oreDict) {
                    if ((OreDictionary.getOreName(oreDictID).startsWith("ore") || OreDictionary.getOreName(oreDictID)
                            .startsWith("crushed")) /* && OreDictionary.getOreName(oreDictID).contains("Cerium") */) {
                        //GT_Log.out.print(OreDictionary.getOreName(oreDictID));
                        GT_Recipe tRecipe = recipe.copy();
                        boolean modified = false;
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                            if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                modified = true;
                            }
                        }
                        if (modified) {
                            reAdd.add(tRecipe);
                            remove.add(recipe);
                        }
                        break;
                    }
                }
            }
        }
        GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Crusher done!\n");

        // For Washer
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sOreWasherRecipes.mRecipeList) {
            ItemStack input = recipe.mInputs[0];
            if (GT_Utility.isStackValid(input)) {
                int[] oreDict = OreDictionary.getOreIDs(input);
                for (int oreDictID : oreDict) {
                    if (OreDictionary.getOreName(oreDictID)
                            .startsWith("crushed") /* && OreDictionary.getOreName(oreDictID).contains("Cerium") */) {
                        GT_Recipe tRecipe = recipe.copy();
                        boolean modified = false;
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                            if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                modified = true;
                            }
                        }
                        if (modified) {
                            reAdd.add(tRecipe);
                            remove.add(recipe);
                        }
                        break;
                    }
                }
            }
        }
        GT_Recipe.GT_Recipe_Map.sOreWasherRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sOreWasherRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sOreWasherRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Washer done!\n");

        // For Thermal Centrifuge
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes.mRecipeList) {
            ItemStack input = recipe.mInputs[0];
            if (GT_Utility.isStackValid(input)) {
                int[] oreDict = OreDictionary.getOreIDs(input);
                for (int oreDictID : oreDict) {
                    if ((OreDictionary.getOreName(oreDictID).startsWith("crushed")
                            || OreDictionary.getOreName(oreDictID).startsWith(
                                    "purified")) /* && OreDictionary.getOreName(oreDictID).contains("Cerium") */) {
                        GT_Recipe tRecipe = recipe.copy();
                        boolean modified = false;
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                            if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                modified = true;
                            }
                        }
                        if (modified) {
                            reAdd.add(tRecipe);
                            remove.add(recipe);
                        }
                        break;
                    }
                }
            }
        }
        GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Thermal Centrifuge done!\n");

        // For Centrifuge
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.mRecipeList) {
            ItemStack input = null;
            FluidStack fluidInput = null;
            if (recipe.mInputs.length > 0) input = recipe.mInputs[0];
            if (recipe.mFluidInputs.length > 0) fluidInput = recipe.mFluidInputs[0];
            if (GT_Utility.isStackValid(input)) {
                int[] oreDict = OreDictionary.getOreIDs(input);
                for (int oreDictID : oreDict) {
                    if (OreDictionary.getOreName(oreDictID).startsWith("dust")
                            && (!OreDictionary.getOreName(oreDictID).contains(
                                    "Dephosphated")) /*
                                                      * OreDictionary.getOreName(oreDictID).startsWith("dustPureCerium")
                                                      * || OreDictionary.getOreName(oreDictID).startsWith(
                                                      * "dustImpureCerium") ||
                                                      * OreDictionary.getOreName(oreDictID).startsWith("dustSpace") ||
                                                      * OreDictionary.getOreName(oreDictID).startsWith("dustCerium")
                                                      */) {
                        GT_Recipe tRecipe = recipe.copy();
                        boolean modified = false;
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                            if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDustTiny(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dustTiny, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDustSmall(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dustSmall, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDustTiny(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dustTiny, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDustSmall(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dustSmall, 1));
                                modified = true;
                            }
                        }
                        if (modified) {
                            reAdd.add(tRecipe);
                            remove.add(recipe);
                        }
                        break;
                    }
                }
            }
            /*
             * GT_Recipe tRecipe = recipe.copy(); if (GT_Utility.isStackValid(fluidInput)) { if
             * (fluidInput.getLocalizedName() == MyMaterial.plutoniumBasedLiquidFuel.getDefaultName()) {
             * tRecipe.mOutputs[1] = GT_Utility.copyAmount(tRecipe.mOutputs[1].stackSize * 2,
             * WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1)); reAdd.add(tRecipe);
             * remove.add(tRecipe); } }
             */
        }
        GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Centrifuge done!\n");

        // For Hammer
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sHammerRecipes.mRecipeList) {
            ItemStack input = recipe.mInputs[0];
            if (GT_Utility.isStackValid(input)) {
                int[] oreDict = OreDictionary.getOreIDs(input);
                for (int oreDictID : oreDict) {
                    if (OreDictionary.getOreName(oreDictID)
                            .startsWith("crushed") /* && OreDictionary.getOreName(oreDictID).contains("Cerium") */) {
                        GT_Recipe tRecipe = recipe.copy();
                        boolean modified = false;
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                            if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                modified = true;
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                        tRecipe.mOutputs[i].stackSize * 2,
                                        WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                modified = true;
                            }
                        }
                        if (modified) {
                            reAdd.add(tRecipe);
                            remove.add(recipe);
                        }
                        break;
                    }
                }
            }
        }
        GT_Recipe.GT_Recipe_Map.sHammerRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sHammerRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sHammerRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Hammer done!\n");

        // Electrolyzer
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes.mRecipeList) {
            for (ItemStack input : recipe.mInputs) {
                //GT_Log.out.print(input.getDisplayName() + "\n");
                if (GT_Utility.isStackValid(input)) {
                    int[] oreDict = OreDictionary.getOreIDs(input);
                    for (int oreDictID : oreDict) {
                        String oreName = OreDictionary.getOreName(oreDictID);
                        if (oreName.equals("dustHibonite") || oreName.equals("dustLanthaniteCe")
                                || oreName.equals("dustZirconolite")
                                || oreName.equals("dustYttrocerite")
                                || oreName.equals("dustXenotime")
                                || oreName.equals("dustBastnasite")
                                || oreName.equals("dustFlorencite")) {
                            GT_Recipe tRecipe = recipe.copy();
                            boolean modified = false;
                            for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                                if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                                if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                    tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                            tRecipe.mOutputs[i].stackSize,
                                            WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                    modified = true;
                                } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                    tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                            tRecipe.mOutputs[i].stackSize,
                                            WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                    modified = true;
                                }
                            }
                            if (modified) {
                                reAdd.add(tRecipe);
                                remove.add(recipe);
                            }
                            break;
                        }
                    }
                }
            }
        }

        GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Electrolyzer done!\n");

        if (Loader.isModLoaded("miscutils")) {
            // For Simple Washer
            for (GT_Recipe recipe : GTPP_Recipe.GTPP_Recipe_Map.sSimpleWasherRecipes.mRecipeList) {
                ItemStack input = recipe.mInputs[0];
                if (GT_Utility.isStackValid(input)) {
                    int[] oreDict = OreDictionary.getOreIDs(input);
                    for (int oreDictID : oreDict) {
                        if (OreDictionary.getOreName(oreDictID).startsWith("dustImpureCerium")
                                || OreDictionary.getOreName(oreDictID).startsWith("dustImpureSamarium")) {
                            GT_Recipe tRecipe = recipe.copy();
                            for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                                if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                                if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                    tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                            tRecipe.mOutputs[i].stackSize,
                                            WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                    tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                            tRecipe.mOutputs[i].stackSize,
                                            WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1));
                                }
                            }
                            if (!tRecipe.equals(recipe)) {
                                reAdd.add(tRecipe);
                                remove.add(recipe);
                            }
                            break;
                        }
                    }
                }
            }
            GTPP_Recipe.GTPP_Recipe_Map.sSimpleWasherRecipes.mRecipeList.removeAll(remove);
            GTPP_Recipe.GTPP_Recipe_Map.sSimpleWasherRecipes.mRecipeList.addAll(reAdd);
            GTPP_Recipe.GTPP_Recipe_Map.sSimpleWasherRecipes.reInit();

            GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

            remove.clear();
            reAdd.clear();

            GT_Log.out.print("Simple Washer done!\n");

            // Dehydrator
            for (GT_Recipe recipe : GTPP_Recipe.GTPP_Recipe_Map.sChemicalDehydratorRecipes.mRecipeList) {
                if (recipe.mInputs.length == 0) {
                    continue;
                }
                ItemStack input = recipe.mInputs[0];

                if (GT_Utility.isStackValid(input)) {
                    int[] oreDict = OreDictionary.getOreIDs(input);
                    for (int oreDictID : oreDict) {
                        String oreName = OreDictionary.getOreName(oreDictID);
                        if (oreName.equals("dustCerite") || oreName.equals("dustFluorcaphite")
                                || oreName.equals("dustZirkelite")
                                || oreName.equals("dustGadoliniteCe")
                                || oreName.equals("dustGadoliniteY")
                                || oreName.equals("dustPolycrase")
                                || oreName.equals("dustBastnasite")) {
                            GT_Recipe tRecipe = recipe.copy();
                            for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                                if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                                if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                    tRecipe.mOutputs[i] = GT_Utility.copyAmount(
                                            tRecipe.mOutputs[i].stackSize,
                                            WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 1));
                                }
                            }
                            if (!tRecipe.equals(recipe)) {
                                reAdd.add(tRecipe);
                                remove.add(recipe);
                            }
                            break;
                        }
                    }
                }
            }

            GTPP_Recipe.GTPP_Recipe_Map.sChemicalDehydratorRecipes.mRecipeList.removeAll(remove);
            GTPP_Recipe.GTPP_Recipe_Map.sChemicalDehydratorRecipes.mRecipeList.addAll(reAdd);
            GTPP_Recipe.GTPP_Recipe_Map.sChemicalDehydratorRecipes.reInit();

            GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

            remove.clear();
            reAdd.clear();

            GT_Log.out.print("Dehydrator done!\n");
        }

        /*
         * DOES NOT WORK, something to do with load times for sifter recipes or some shit //Sifter for (GT_Recipe recipe
         * : GT_Recipe.GT_Recipe_Map.sSifterRecipes.mRecipeList) { if (recipe.mInputs.length == 0) break; ItemStack
         * input = recipe.mInputs[0]; GT_Log.out.print("Sift ore found " + input.getDisplayName() + "\n"); if
         * (GT_Utility.isStackValid(input)) { if (true) { GT_Log.out.print("Sift ore found and iffed " +
         * input.getDisplayName() + "\n"); //GT_Recipe tRecipe = recipe.copy(); remove.add(recipe); break; } } }
         * GT_Recipe.GT_Recipe_Map.sSifterRecipes.mRecipeList.removeAll(remove);
         * GT_Recipe.GT_Recipe_Map.sSifterRecipes.mRecipeList.addAll(reAdd);
         * GT_Recipe.GT_Recipe_Map.sSifterRecipes.reInit(); GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() +
         * "! "); remove.clear(); reAdd.clear(); GT_Log.out.print("Sifter done!\n");
         */
        // Chemical Bath
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes.mRecipeList) {
            // ItemStack input = recipe.mInputs[0];
            for (ItemStack input : recipe.mInputs) {
                //GT_Log.out.print(input.getDisplayName() + "\n");
                if (GT_Utility.isStackValid(input)) {
                    int[] oreDict = OreDictionary.getOreIDs(input);
                    for (int oreDictID : oreDict) {
                        String oreName = OreDictionary.getOreName(oreDictID);
                        if (oreName.equals("dustTin") || oreName.equals("dustRutile")) {
                            GT_Recipe tRecipe = recipe.copy();
                            remove.add(recipe);
                            break;
                        }
                    }
                }
            }
        }

        GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("Chemical Bath done!\n");

        // For ByProduct List
        for (GT_Recipe recipe : GT_Recipe.GT_Recipe_Map.sByProductList.mRecipeList) {
            ItemStack input = recipe.mInputs[0];
            if (GT_Utility.isStackValid(input)) {
                int[] oreDict = OreDictionary.getOreIDs(input);
                for (int oreDictID : oreDict) {
                    if (OreDictionary.getOreName(oreDictID).startsWith("ore")
                            && OreDictionary.getOreName(oreDictID).contains("Cerium")) {
                        GT_Recipe tRecipe = recipe.copy();
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            if (!GT_Utility.isStackValid(tRecipe.mOutputs[i])) continue;
                            if (tRecipe.mOutputs[i].isItemEqual(Materials.Cerium.getDust(1))) {
                                remove.add(tRecipe);
                            } else if (tRecipe.mOutputs[i].isItemEqual(Materials.Samarium.getDust(1))) {
                                remove.add(tRecipe);
                            }
                        }
                        break;
                    }
                }
            }
        }
        GT_Recipe.GT_Recipe_Map.sByProductList.mRecipeList.removeAll(remove);
        GT_Recipe.GT_Recipe_Map.sByProductList.mRecipeList.addAll(reAdd);
        GT_Recipe.GT_Recipe_Map.sByProductList.reInit();

        GT_Log.out.print(Tags.MODID + ": Replace " + remove.size() + "! ");

        remove.clear();
        reAdd.clear();

        GT_Log.out.print("ByProduct List done!\n");

        // For Cauldron Wash
        registerCauldronCleaningFor(Materials.Cerium, WerkstoffMaterialPool.CeriumRichMixture.getBridgeMaterial());
        registerCauldronCleaningFor(
                Materials.Samarium,
                WerkstoffMaterialPool.SamariumOreConcentrate.getBridgeMaterial());
        GT_Log.out.print(Tags.MODID + ": Replace 3! ");
        GT_Log.out.print("Cauldron Wash done!\n");

        // For Crafting Table
        CraftingManager.getInstance().getRecipeList().forEach(RecipeLoader::replaceInCraftTable);

        GT_Log.out.print(Tags.MODID + ": Replace Unknown! ");
        GT_Log.out.print("Crafting Table done!\n");
    }

    // below are taken from GoodGenerator

    // I don't understand. . .
    // I use and copy some private methods in Bartworks because his system runs well.
    // Bartworks is under MIT License
    /*
     * Copyright (c) 2018-2020 bartimaeusnek Permission is hereby granted, free of charge, to any person obtaining a
     * copy of this software and associated documentation files (the "Software"), to deal in the Software without
     * restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute,
     * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
     * subject to the following conditions: The above copyright notice and this permission notice shall be included in
     * all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
     * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
     * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
     * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
     * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
     */
    public static void replaceInCraftTable(Object obj) {

        Constructor<?> cs = null;
        PlatinumSludgeOverHaul BartObj = null;
        try {
            cs = PlatinumSludgeOverHaul.class.getDeclaredConstructor();
            cs.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (cs == null) return;

        try {
            BartObj = (PlatinumSludgeOverHaul) cs.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Method recipeCheck = null;

        try {
            recipeCheck = PlatinumSludgeOverHaul.class.getDeclaredMethod("checkRecipe", Object.class, Materials.class);
            recipeCheck.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String inputName = "output";
        String inputItemName = "input";
        if (!(obj instanceof ShapedOreRecipe || obj instanceof ShapelessOreRecipe)) {
            if (obj instanceof ShapedRecipes || (obj instanceof ShapelessRecipes)) {
                inputName = "recipeOutput";
                inputItemName = "recipeItems";
            }
        }
        IRecipe recipe = (IRecipe) obj;
        ItemStack result = recipe.getRecipeOutput();

        Field out = FieldUtils.getDeclaredField(recipe.getClass(), inputName, true);
        if (out == null) out = FieldUtils.getField(recipe.getClass(), inputName, true);

        Field in = FieldUtils.getDeclaredField(recipe.getClass(), inputItemName, true);
        if (in == null) in = FieldUtils.getField(recipe.getClass(), inputItemName, true);
        if (in == null) return;

        // this part here is NOT MIT LICENSED BUT LICSENSED UNDER THE Apache License, Version 2.0!
        try {
            if (Modifier.isFinal(in.getModifiers())) {
                // Do all JREs implement Field with a private ivar called "modifiers"?
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                boolean doForceAccess = !modifiersField.isAccessible();
                if (doForceAccess) {
                    modifiersField.setAccessible(true);
                }
                try {
                    modifiersField.setInt(in, in.getModifiers() & ~Modifier.FINAL);
                } finally {
                    if (doForceAccess) {
                        modifiersField.setAccessible(false);
                    }
                }
            }
        } catch (NoSuchFieldException ignored) {
            // The field class contains always a modifiers field
        } catch (IllegalAccessException ignored) {
            // The modifiers field is made accessible
        }
        // END OF APACHE COMMONS COLLECTION COPY

        Object input;
        try {
            input = in.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        if (out == null || recipeCheck == null) return;

        if (GT_Utility.areStacksEqual(result, Materials.Cerium.getDust(1), true)) {

            recipeCheck.setAccessible(true);
            boolean isOk = true;

            try {
                isOk = (boolean) recipeCheck.invoke(BartObj, input, Materials.Cerium);
            } catch (InvocationTargetException | IllegalAccessException ignored) {}

            if (isOk) return;
            try {
                out.set(recipe, WerkstoffMaterialPool.CeriumRichMixture.get(OrePrefixes.dust, 2));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (GT_Utility.areStacksEqual(result, Materials.Samarium.getDust(1), true)) {

            recipeCheck.setAccessible(true);
            boolean isOk = true;

            try {
                isOk = (boolean) recipeCheck.invoke(BartObj, input, Materials.Samarium);
            } catch (InvocationTargetException | IllegalAccessException ignored) {}

            if (isOk) return;
            try {
                out.set(recipe, WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 2));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
