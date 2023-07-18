package com.elisis.gtnhlanth.common.tileentity;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockAdder;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import java.util.ArrayList;

import com.elisis.gtnhlanth.common.block.AntennaCasing;
import com.elisis.gtnhlanth.common.hatch.TileHatchInputBeamline;
import com.elisis.gtnhlanth.common.hatch.TileHatchOutputBeamline;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class Synchrotron extends GT_MetaTileEntity_EnhancedMultiBlockBase<Synchrotron> implements IConstructable {

    private static final IStructureDefinition<Synchrotron> STRUCTURE_DEFINITION;

    protected static final String STRUCTURE_PIECE_ENTRANCE = "entrance";
    protected static final String STRUCTURE_PIECE_BASE = "base";

    private ArrayList<TileHatchInputBeamline> mInputBeamline = new ArrayList<>();
    private ArrayList<TileHatchOutputBeamline> mOutputBeamline = new ArrayList<>();
    
    public ArrayList<AntennaCasing> mAntennaCasings = new ArrayList<>();
    
    private static final int CASING_INDEX = 49;
    
    private int energyHatchTier;
    
    private int antennaeTier;

    /*
     * c: Shielded accelerator casing v: Vacuum k: Superconducting coil d: Coolant Delivery casing
     */

    // TODO: E > 1200eV for x-ray lithography
    // spotless:off
    static {

        STRUCTURE_DEFINITION = StructureDefinition.<Synchrotron>builder().addShape(
                STRUCTURE_PIECE_ENTRANCE,

                
                
                new String[][] { 
                		{ 
                			"                                    ", 
                			"  ccc                               ",
                			" c---c                              ", 
                        	" c---c                              ",
                        	" c---c                              ", 
                        	"  ccc                               " 
                		} 
                })
                .addShape(
                    STRUCTURE_PIECE_BASE,

                    new String[][] {
                    	{ 
                    		"                                    ", 
                    		"  ccc                               ",
                    		" c---c       ccccccc                ", 
                    		" c---c      cccc~cccc               ",
                    		" c---c       ccccccc                ", 
                    		"  ccc                               ",
                    		"                                    " 
                    	},
                    	{ 
                    		"                                    ", 
                    		"  ccc      ccccccccccc              ",
                    		" c---c    ccc-------ccc             ", 
                    		" c---c    cc---------cc             ",
                    		" c---c    ccc-------ccc             ", 
                    		"  ccc      ccccccccccc              ",
                    		"                                    " 
                    	},
                    	{ 
                    		"           ccccccccccc             ", 
                    		"  ccc    cc-----------cc           ",
                    		" c---c  cc-------------cc          ", 
                    		" c---c  cc-------------cc          ",
                    		" c---c  cc-------------cc          ", 
                    		"  ccc    ccc---------ccc           ",
                    		"           ccccccccccc             " 
                    	},
                    	{
                    		"         ccccccccccccccc           ", 
                    		"  ccc  cc---------------cc         ",
                    		" c---c c-----------------c         ", 
                    		" c---ccc-----------------cc        ",
                    		" c---c c-----------------c         ", 
                    		"  ccc  cc---------------cc         ",
                    		"         ccccccccccccccc           ",
                    		
                    	},
                    	{
                    		"  ccc   ccccccccccccccccc          ", 
                    		" ckkkccc-----------------cc        ",
                    		"ck---kc-------------------cc       ", 
                    		"ck---kc--------------------c       ",
                    		"ck---kc-------------------cc       ", 
                    		" ckkkccc-----------------cc        ",
                    		"  ccc  cccccccccccccccccc          "
                    		
                    	},
                    	{ 
                    		"  cccccccccccc     ccccccc         ", 
                    		" cdddcc-------ccccc-------cc       ",
                    		"cd---d----------------------c      ", 
                    		"cd---d----------------------c      ",
                    		"cd---d----------------------c      ", 
                    		" cdddcc-------ccccc-------cc       ",
                    		"  cccccccccccc     ccccccc         ", 
                    	},
                    	{
                    		"  ccccccccc           ccccc        ",
                    		" ckkkc-----cccc   cccc-----cc      ",
                    		"ck---k-------ccccccc--------c      ",
                    		"ck---k-------ccccccc---------c     ",
                    		"ck---k-------ccccccc--------c      ",
                    		" ckkkc-----cccc   cccc-----cc      ",
                    		"  ccccccccc           ccccc        "
                    	},
                    	{
                    		"  cccccccc             ccccc       ",
                    		" c--------cc         cc-----cc     ",
                    		"c----------cc       cc-------c     ",
                    		"c----------cc       cc-------c     ",
                    		"c----------cc       cc-------c     ",
                    		" c--------cc         cc-----cc     ",
                    		"  cccccccc             ccccc       "
                    		
                    	},
                    	{
                    		"  ccccccc               ccccc      ",
                    		" c-------c             c-----c     ",
                    		"c---------c           c-------c    ",
                    		"c---------c           c-------c    ",
                    		"c---------c           c-------c    ",
                    		" c-------c             c-----c     ",
                    		"  ccccccc               ccccc      "
                    	
                    	},
                    	{
                    		"  cccccc                 ccccc     ",
                    		" c------c               c-----c    ",
                    		"c--------c             c------c    ",
                    		"c--------c             c------c    ",
                    		"c--------c             c------c    ",
                    		" c------c               c-----c    ",
                    		"  cccccc                 ccccc     "
                    	
                    	},
                    	{
                    		"  ccccc                   cccc     ",
                    		" c-----c                 c----c    ",
                    		"c-------c               c------c   ",
                    		"c-------c               c------c   ",
                    		"c-------c               c------c   ",
                    		" c-----c                 c----c    ",
                    		"  ccccc                   cccc     "
                    		
                    	},
                    	{
                    		"  cccc                     ccc     ",
                    		" c----cc                 cc---cc   ",
                    		"c------c                 c-----c   ",
                    		"c------c                 c-----c   ",
                    		"c------c                 c-----c   ",
                    		" c----cc                 cc---cc   ",
                    		"  cccc                     ccc     "
                    		
                    	},
                    	{
                    		"  cccc                     cccc    ",
                    		" c---cc                   c----c   ",
                    		"c------c                 c-----c   ",
                    		"c------c                 c-----cc  ",
                    		"c------c                 c-----c   ",
                    		" c---cc                   cc---c   ",
                    		"  cccc                     cccc    "
                    	
                    	},
                    	{
                    		"  cccc                     cccc    ",
                    		" c---cc                   c----c   ",
                    		"c-----c                   c----cc  ",
                    		"c-----c                   c----cc  ",
                    		"c-----c                   c----cc  ",
                    		" c---cc                   cc---c   ",
                    		"  cccc                     cccc    "
                    	
                    	},
                    	{
                    		"  ccc                       ccc    ",
                    		" ckkkcc                   cckkkc   ",
                    		"ck---kc                   ck---kc  ",
                    		"ck---kc                   ck---kc  ",
                    		"ck---kc                   ck---kc  ",
                    		" ckkkcc                   cckkkc   ",
                    		"  ccc                       ccc    "
                    	
                    	},
                    	{
                    		"  cec                       cec    ",
                    		" cnanc                     cnanc   ",
                    		"cn---nc                   cn---nc  ",
                    		"cn---nc                   cn---nc  ",
                    		"cn---nc                   cn---nc  ",
                    		" cnnnc                     cnnnc   ",
                    		"  ccc                       ccc    "
                    		
                    	},
                    	{
                    		"  cic                       cic    ",
                    		" cndnc                     cndnc   ",
                    		"cn---nc                   cn---nc  ",
                    		"cn---nc                   cn---nc  ",
                    		"cn---nc                   cn---nc  ",
                    		" cndnc                     cndnc   ",
                    		"  coc                       coc    "
                    		
                    	},
                    	{
                    		"  cec                       cec    ",
                    		" cnanc                     cnanc   ",
                    		"cn---nc                   cn---nc  ",
                    		"cn---nc                   cn---nc  ",
                    		"cn---nc                   cn---nc  ",
                    		" cnnnc                     cnnnc   ",
                    		"  ccc                       ccc    "
                    		
                    	},
                    	{
                    		"  ccc                       ccc    ",
                    		" ckkkcc                   cckkkc   ",
                    		"ck---kc                   ck---kc  ",
                    		"ck---kc                   ck---kc  ",
                    		"ck---kc                   ck---kc  ",
                    		" ckkkcc                   cckkkc   ",
                    		"  ccc                       ccc    "
                    		
                    	},
                    	{
                    		"  cccc                     cccc    ",
                    		" c----c                   c----c   ",
                    		"cc----c                   c----cc  ",
                    		"cc----c                   c----cc  ",
                    		"cc----c                   c----cc  ",
                    		" c---cc                   cc---c   ",
                    		"  cccc                     cccc    "
                    		
                    	},
                    	{
                    		"  cccc                     cccc    ",
                    		" c----c                   c----c   ",
                    		" c-----c                 c-----c   ",
                    		"cc-----c                 c-----cc  ",
                    		" c-----c                 c-----c   ",
                    		" c---cc                   cc---c   ",
                    		"  cccc                     cccc    "
                    		
                    	},
                    	{
                    		"   ccc                     ccc     ",
                    		" cc---cc                 cc---cc   ",
                    		" c-----c                 c-----c   ",
                    		" c-----c                 c-----c   ",
                    		" c-----c                 c-----c   ",
                    		" cc---cc                 cc---cc   ",
                    		"   ccc                     ccc     "
                    		
                    	},
                    	{
                    		"   cccc                   cccc     ",
                    		"  c----c                 c----c    ",
                    		" c------c               c------c   ",
                    		" c------c               c------c   ",
                    		" c------c               c------c   ",
                    		"  c----c                 c----c    ",
                    		"   cccc                   cccc     "
                    		
                    	},
                    	{
                    		"   ccccc                 ccccc     ",
                    		"  c-----c               c-----c    ",
                    		"  c------c             c------c    ",
                    		"  c------c             c------c    ",
                    		"  c------c             c------c    ",
                    		"  c-----c               c-----c    ",
                    		"   ccccc                 ccccc     "
                    		
                    	},
                    	{
                    		"    ccccc               ccccc      ",
                    		"   c-----c             c-----c     ",
                    		"  c-------c           c-------c    ",
                    		"  c-------c           c-------c    ",
                    		"  c-------c           c-------c    ",
                    		"   c-----c             c-----c     ",
                    		"    ccccc               ccccc      "
                    		
                    	},
                    	{
                    		"     ccccc             ccccc       ",
                    		"    c-----cc         cc-----c      ",
                    		"   c-------cc       cc-------cc    ",
                    		"   c-------cc       cc-------cc    ",
                    		"   c-------cc       cc-------cc    ",
                    		"    c-----cc         cc------c     ",
                    		"     ccccc             cccccc      "
                    		
                    	},
                    	{
                    		"      ccccc           ccccccc      ",
                    		"    cc-----cccc   cccc-----ccc     ",
                    		"    c--------ccccccc--------cccc   ",
                    		"    c--------ccccccc--------cccc   ",
                    		"    c--------ccccccc--------cccc   ",
                    		"    cc-----cccc   cccc------cc     ",
                    		"      ccccc           cccccc       "
                    		
                    	}

                   }

                ).addElement('c', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('k', ofBlock(GregTech_API.sBlockCasings1, 15)) // Superconducting coils
                .addElement('d', ofBlock(LanthItemList.COOLANT_DELIVERY_CASING, 0))
                .addElement('e', ofHatchAdder(Synchrotron::addEnergyInputToMachineList, CASING_INDEX, 2))
                .addElement('n', ofBlock(GregTech_API.sBlockMetal5, 5)) //Niobium Blocks
                .addElement('a', ofBlockAdder(Synchrotron::addAntenna, LanthItemList.ANTENNA_CASING_T1, 3)) //Antenna Casings

                .build();
        
     

    }
    
    // spotless:on
    
    /*
     * v = pi * lorentz^2 * sfreq

		sfreq = sw / 2pi

		sw = e * B / mass(e) * c

		v = (e * B * l^2) / (2 * mass(e) * c)

  		= 292.718624222 * l^2 * B
     */
    
    public Synchrotron(String aName) {
        super(aName);
    }

    public Synchrotron(int id, String name, String nameRegional) {
        super(id, name, nameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new Synchrotron(this.mName);
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Particle Accelerator").addInfo("Controller block for the Synchrotron")
                .addInfo("Torus-shaped, accelerates electrons to produce high-energy electromagnetic radiation")
                .toolTipFinisher("GTNH: Lanthanides");;
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection aSide, ForgeDirection aFacing,
            int aColorIndex, boolean aActive, boolean aRedstone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_BASE, stackSize, hintsOnly, 16, 3, 0);

    }

    @Override
    public IStructureDefinition<Synchrotron> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }
    
    public boolean addEnergyInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Energy) {
        	
        	GT_MetaTileEntity_Hatch_Energy hatch = (GT_MetaTileEntity_Hatch_Energy) aMetaTileEntity;
        	
        	//First energy hatch added
        	if (this.mEnergyHatches.size() == 0)
        		this.energyHatchTier = hatch.mTier;
        	
        	// Disallow any hatches that don't match the tier of the first hatch added
        	if (hatch.mTier != this.energyHatchTier)
        		return false;
        		
        	
            hatch.updateTexture(aBaseCasingIndex);
            hatch.updateCraftingIcon(this.getMachineCraftingIcon());
            return mEnergyHatches.add(hatch);
        }
        return false;
    }
    
    private boolean addAntenna(Block block, int meta) {
    	
    	if (block == null)
    		return false;
    	
    	if (!(block instanceof AntennaCasing))
    		return false;
    	
    	AntennaCasing antennaBlock = (AntennaCasing) block;
    	
    	int antennaTier = antennaBlock.getTier();
    	
    	//First antenna casing added
    	if (this.mAntennaCasings.size() == 0)
    		this.antennaeTier = antennaTier;
    	
    	if (antennaTier != this.antennaeTier) 
    		return false;
    	
    	return mAntennaCasings.add(antennaBlock);
    	
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        // TODO Auto-generated method stub
        return false;
    }

}
