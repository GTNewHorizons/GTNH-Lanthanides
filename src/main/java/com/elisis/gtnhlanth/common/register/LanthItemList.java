package com.elisis.gtnhlanth.common.register;

import com.elisis.gtnhlanth.common.beamline.TileBeamline;
import com.elisis.gtnhlanth.common.block.Casing;
import com.elisis.gtnhlanth.common.hatch.TileHatchInputBeamline;
import com.elisis.gtnhlanth.common.hatch.TileHatchOutputBeamline;
import com.elisis.gtnhlanth.common.tileentity.Digester;
import com.elisis.gtnhlanth.common.tileentity.DissolutionTank;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class LanthItemList {

    public static ItemStack DIGESTER;
    public static ItemStack DISSOLUTION_TANK;
    
    public static ItemStack BEAMLINE_PIPE;
    
    public static ItemStack LUV_BEAMLINE_INPUT_HATCH;
    public static ItemStack LUV_BEAMLINE_OUTPUT_HATCH;
    
    
    
    public static final Block SHIELDED_ACCELERATOR_CASING = new Casing("shielded_accelerator");;

    public static void registerGTMTE() {

        DIGESTER = new Digester(10500, "Digester", "Digester").getStackForm(1L);
        DISSOLUTION_TANK =
            new DissolutionTank(10501, "Dissolution Tank", "Dissolution Tank").getStackForm(1L);
        
        BEAMLINE_PIPE = new TileBeamline(10502, "Beamline Pipe", "Beamline Pipe").getStackForm(1L);
        LUV_BEAMLINE_INPUT_HATCH = new TileHatchInputBeamline(10503, "LuV Beamline Input Hatch", "LuV Beamline Input Hatch", 6).getStackForm(1L);
        LUV_BEAMLINE_OUTPUT_HATCH = new TileHatchOutputBeamline(10504, "LuV Beamline Output Hatch", "LuV Beamline Output Hatch", 6).getStackForm(1L);
        
    }
    
    public static void registerTypical() {
    	GameRegistry.registerBlock(SHIELDED_ACCELERATOR_CASING, SHIELDED_ACCELERATOR_CASING.getUnlocalizedName());
    	
    	
    }
}
