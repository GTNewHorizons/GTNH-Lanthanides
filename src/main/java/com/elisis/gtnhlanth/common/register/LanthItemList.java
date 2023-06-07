package com.elisis.gtnhlanth.common.register;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.elisis.gtnhlanth.common.beamline.TileBeamline;
import com.elisis.gtnhlanth.common.block.Casing;
import com.elisis.gtnhlanth.common.block.ShieldedAccGlass;
import com.elisis.gtnhlanth.common.hatch.TileHatchInputBeamline;
import com.elisis.gtnhlanth.common.hatch.TileHatchOutputBeamline;
import com.elisis.gtnhlanth.common.item.MaskList;
import com.elisis.gtnhlanth.common.item.PhotolithographicMask;
import com.elisis.gtnhlanth.common.tileentity.Digester;
import com.elisis.gtnhlanth.common.tileentity.DissolutionTank;
import com.elisis.gtnhlanth.common.tileentity.LINAC;
import com.elisis.gtnhlanth.common.tileentity.SourceChamber;
import com.elisis.gtnhlanth.common.tileentity.Synchrotron;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GT_LanguageManager;

public final class LanthItemList {

    public static ItemStack DIGESTER;
    public static ItemStack DISSOLUTION_TANK;

    public static ItemStack LINAC;
    public static ItemStack SOURCE_CHAMBER;

    public static ItemStack SYNCHROTRON;

    public static ItemStack BEAMLINE_PIPE;

    public static ItemStack LUV_BEAMLINE_INPUT_HATCH;
    public static ItemStack LUV_BEAMLINE_OUTPUT_HATCH;

    public static Item CAPILLARY_EXCHANGE = new Item().setUnlocalizedName("capillary_exchange");

    public static Item MM_LATTICE = new Item().setUnlocalizedName("mm_lattice");

    public static Item IRON_COATED_QUARTZ = new Item().setUnlocalizedName("iron_quartz_plate");

    public static Item SUBSTRATE_PRECURSOR = new Item().setUnlocalizedName("substrate_precursor");

    public static Item MASK_SUBSTRATE = new Item().setUnlocalizedName("mask_substrate");

    public static Item MASKED_MASK = new Item().setUnlocalizedName("masked_mask_substrate");

    public static Item ETCHED_MASK_1 = new Item().setUnlocalizedName("etched_mask1");

    public static Item SILICON_NITRIDE_MEMBRANE = new Item().setUnlocalizedName("nitride_gold_membrane");

    public static final Block SHIELDED_ACCELERATOR_CASING = new Casing("shielded_accelerator");
    public static final Block SHIELDED_ACCELERATOR_GLASS = new ShieldedAccGlass();

    public static final Block ELECTRODE_CASING = new Casing("electrode");

    public static final Block COOLANT_DELIVERY_CASING = new Casing("coolant_delivery");

    public static final Block ANTENNA_CASING_T1 = new Casing("antenna_t1");
    public static final Block ANTENNA_CASING_T2 = new Casing("antenna_t2");

    public static HashMap<MaskList, Item> maskMap = new HashMap<>();

    public static void registerGTMTE() {

        DIGESTER = new Digester(10500, "Digester", "Digester").getStackForm(1L);
        DISSOLUTION_TANK = new DissolutionTank(10501, "Dissolution Tank", "Dissolution Tank").getStackForm(1L);

        BEAMLINE_PIPE = new TileBeamline(10502, "Beamline Pipe", "Beamline Pipe").getStackForm(1L);
        LUV_BEAMLINE_INPUT_HATCH = new TileHatchInputBeamline(
                10503,
                "LuV Beamline Input Hatch",
                "LuV Beamline Input Hatch",
                6).getStackForm(1L);
        LUV_BEAMLINE_OUTPUT_HATCH = new TileHatchOutputBeamline(
                10504,
                "LuV Beamline Output Hatch",
                "LuV Beamline Output Hatch",
                6).getStackForm(1L);

        LINAC = new LINAC(10505, "Linear Accelerator", "Linear Accelerator").getStackForm(1L);

        SOURCE_CHAMBER = new SourceChamber(10506, "Source Chamber", "Source Chamber").getStackForm(1L);

        SYNCHROTRON = new Synchrotron(10507, "Synchrotron", "Synchrotron").getStackForm(1L);
    }

    public static void registerTypical() {

        GameRegistry.registerItem(CAPILLARY_EXCHANGE, CAPILLARY_EXCHANGE.getUnlocalizedName());

        GameRegistry.registerItem(MM_LATTICE, MM_LATTICE.getUnlocalizedName());

        GameRegistry.registerItem(IRON_COATED_QUARTZ, IRON_COATED_QUARTZ.getUnlocalizedName());

        GameRegistry.registerItem(MASK_SUBSTRATE, MASK_SUBSTRATE.getUnlocalizedName());

        GameRegistry.registerItem(ETCHED_MASK_1, ETCHED_MASK_1.getUnlocalizedName());

        GameRegistry.registerItem(SUBSTRATE_PRECURSOR, SUBSTRATE_PRECURSOR.getUnlocalizedName());

        GameRegistry.registerItem(MASKED_MASK, MASKED_MASK.getUnlocalizedName());

        GameRegistry.registerBlock(SHIELDED_ACCELERATOR_CASING, SHIELDED_ACCELERATOR_CASING.getUnlocalizedName());

        GameRegistry.registerBlock(ELECTRODE_CASING, ELECTRODE_CASING.getUnlocalizedName());

        GameRegistry.registerBlock(COOLANT_DELIVERY_CASING, COOLANT_DELIVERY_CASING.getUnlocalizedName());

        GameRegistry.registerBlock(SHIELDED_ACCELERATOR_GLASS, SHIELDED_ACCELERATOR_GLASS.getUnlocalizedName());

        GameRegistry.registerBlock(ANTENNA_CASING_T1, ANTENNA_CASING_T1.getUnlocalizedName());

        GameRegistry.registerBlock(ANTENNA_CASING_T2, ANTENNA_CASING_T2.getUnlocalizedName());

        for (MaskList mask : MaskList.values()) {

            String english = mask.getEnglishName();

            String descSpectrum = mask.getSpectrum();

            PhotolithographicMask maskItem = new PhotolithographicMask(mask.getName(), mask.getDamage(), descSpectrum);
            GameRegistry.registerItem(maskItem, maskItem.getUnlocalizedName());

            GT_LanguageManager.addStringLocalization(maskItem.getUnlocalizedName() + ".name", "Mask (" + english + ")");

            maskMap.put(mask, maskItem);

        }

    }
}
