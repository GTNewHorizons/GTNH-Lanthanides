package com.elisis.gtnhlanth.common.tileentity;



import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockAdder;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static gregtech.api.enums.GT_Values.VN;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import java.util.ArrayList;

import com.elisis.gtnhlanth.common.beamline.BeamInformation;
import com.elisis.gtnhlanth.common.beamline.Particle;
import com.elisis.gtnhlanth.common.hatch.TileBusInputFocus;
import com.elisis.gtnhlanth.common.hatch.TileHatchInputBeamline;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.elisis.gtnhlanth.common.tileentity.recipe.beamline.BeamlineRecipeAdder;
import com.elisis.gtnhlanth.common.tileentity.recipe.beamline.RecipeTC;
import com.github.bartimaeusnek.bartworks.common.loaders.ItemRegistry;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TargetChamber extends GT_MetaTileEntity_EnhancedMultiBlockBase<TargetChamber> implements IConstructable {

	private static final IStructureDefinition<TargetChamber> STRUCTURE_DEFINITION;

    private ArrayList<TileHatchInputBeamline> mInputBeamline = new ArrayList<>();
    
    private ArrayList<TileBusInputFocus> mInputFocus = new ArrayList<>();

    private static final int CASING_INDEX = 49;
    
    private float inputEnergy;
	private float inputRate;
	private int inputParticle;
	private float inputFocus;
    
	
	//spotless:off
    static {
    	STRUCTURE_DEFINITION = StructureDefinition.<TargetChamber>builder()
    			.addShape(
    					"base",
    					new String[][] {
    						{"ggggg", "gjjjg", "gjbjg", "gjjjg", "ff~ff"},
    						{"cslsc", "s-r-s", "srhrs", "s-r-s", "ccccc"},
    						{"csssc", "s---s", "s---s", "s---s", "ccccc"},
    						{"csssc", "s---s", "s---s", "s---s", "ccccc"},
    						{"cstsc", "s-u-s", "suius", "s-u-s", "ccccc"},
    						{"ggggg", "gjjjg", "gjojg", "gjjjg", "ggggg"}})
    			
    			.addElement('g', ofBlock(GregTech_API.sBlockCasings3, 10)) //Grate casing
    			.addElement(
    					'f', 
    					ofChain(
    							ofHatchAdder(TargetChamber::addMaintenanceToMachineList, 49, 1),
    							ofHatchAdder(TargetChamber::addEnergyInputToMachineList, 49, 1),
    							ofBlock(GregTech_API.sBlockCasings3, 10)
    					))
    			.addElement('j', ofBlockAdder(TargetChamber::addGlass, ItemRegistry.bw_glasses[0], 1))
    			.addElement('b', ofHatchAdder(TargetChamber::addBeamLineInputHatch, CASING_INDEX, 1))
    			.addElement('c', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
    			
    			.addElement('l', ofHatchAdder(TargetChamber::addFocusInputHatch, CASING_INDEX, 1))
    			
    			.addElement('t', ofHatchAdder(GT_MetaTileEntity_MultiBlockBase::addInputBusToMachineList, CASING_INDEX, 1))
    			.addElement('s', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_GLASS, 0))
    			.addElement('r', ofBlock(LanthItemList.FOCUS_MANIPULATION_CASING, 0))
    			.addElement('h', ofBlock(LanthItemList.FOCUS_HOLDER, 0))
    			.addElement('u', ofBlock(LanthItemList.TARGET_RECEPTACLE_CASING, 0))
    			.addElement('i', ofBlock(LanthItemList.TARGET_HOLDER, 0))
    			.addElement('o', ofHatchAdder(GT_MetaTileEntity_MultiBlockBase::addOutputBusToMachineList, CASING_INDEX, 1))
    			
    			.build();
    }
    //spotless:on
    
    
    private boolean addGlass(Block block, int meta) {
        return block == ItemRegistry.bw_glasses[0];
    }
    
    private boolean addBeamLineInputHatch(IGregTechTileEntity te, int casingIndex) {

        if (te == null) return false;

        IMetaTileEntity mte = te.getMetaTileEntity();

        if (mte == null) return false;

        if (mte instanceof TileHatchInputBeamline) {
            return this.mInputBeamline.add((TileHatchInputBeamline) mte);
        }

        return false;
    }
    
    private boolean addFocusInputHatch(IGregTechTileEntity te, int casingIndex) {

        if (te == null) return false;

        IMetaTileEntity mte = te.getMetaTileEntity();

        if (mte == null) return false;

        if (mte instanceof TileBusInputFocus) {
            return this.mInputFocus.add((TileBusInputFocus) mte);
        }

        return false;
    }
    
	public TargetChamber(int id, String name, String nameRegional) {
		super(id, name, nameRegional);
	}
	
	public TargetChamber(String name) {
		super(name);
	}
	
	@Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity te) {
        return new TargetChamber(this.mName);
    }

	@Override
	public ITexture[] getTexture(IGregTechTileEntity arg0, ForgeDirection arg1, ForgeDirection arg2, int arg3,
			boolean arg4, boolean arg5) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Collision Chamber").addInfo("Controller block for the Target Chamber\nHitting things with other things")
                .toolTipFinisher("GTNH: Lanthanides");;
        return tt;
    }

	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece("base", stackSize, hintsOnly, 2, 4, 0);
		
	}

	@Override
	public IStructureDefinition<TargetChamber> getStructureDefinition() {
		return STRUCTURE_DEFINITION;
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		
		inputEnergy = 0;
		inputRate = 0;
		inputParticle = 0;
		inputFocus = 0;
		
		
		ArrayList<ItemStack> tItems = this.getStoredInputs();
		ItemStack tFocusItem = this.getFocusItemStack();
		
		if (tFocusItem == null)
			return false;
		
		ItemStack tFocusItemZeroDamage = tFocusItem.copy();
		tFocusItemZeroDamage.setItemDamage(0);
		
		ArrayList<ItemStack> tItemsWithFocusItem = new ArrayList<>();
		tItemsWithFocusItem.add(tFocusItemZeroDamage);
		tItemsWithFocusItem.addAll(tItems);
		
		
        
		// GT_Log.out.print(Arrays.toString(tItems));
        long tVoltage = this.getMaxInputVoltage();
        
        //tItems.add(tFocusItem);
        
        ItemStack[] tItemsArray = tItems.toArray(new ItemStack[0]);
        
        ItemStack[] tItemsWithFocusItemArray = tItemsWithFocusItem.toArray(new ItemStack[0]);        
        //GT_Log.out.print("tItemsArray: " +  Arrays.toString(tItemsArray));
        
        /*
        for (GT_Recipe tRecipe : BeamlineRecipeAdder.instance.TargetChamberRecipes.mRecipeList) {
        	GT_Log.out.print(Arrays.toString(tRecipe.mInputs) + "\n");
        }
        */
        
        RecipeTC tRecipe = (RecipeTC) BeamlineRecipeAdder.instance.TargetChamberRecipes.findRecipe(
        		this.getBaseMetaTileEntity(), false, tVoltage, null, tItemsWithFocusItemArray);
        /*
        if (tRecipe == null) {
        	GT_Log.out.print("Recipe null!");
        }
        */
        //GT_Log.out.print("Focus in machine " + tFocusItem.getItem().getUnlocalizedName());
        //GT_Log.out.print("Focus in recipe " + tRecipe.focusItem.getItem().getUnlocalizedName());
        
        
           
        if (tRecipe == null || !tRecipe.isRecipeInputEqual(true, new FluidStack[] {}, tItemsWithFocusItemArray)) return false;
        
        if (tRecipe.focusItem.getItem() != tFocusItem.getItem())
        	return false;
        
        //GT_Log.out.print("Items are the same!");
        
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        
        BeamInformation inputInfo = this.getInputInformation();
        
        if (inputInfo == null)
            return false;
        
        inputEnergy = inputInfo.getEnergy();
        inputRate = inputInfo.getRate();
        inputParticle = inputInfo.getParticleId();
        inputFocus = inputInfo.getFocus();

        //GT_Log.out.print("Min energy: " + tRecipe.minEnergy + " Max energy: " + tRecipe.maxEnergy);
        
        if (inputEnergy < tRecipe.minEnergy || inputEnergy > tRecipe.maxEnergy)
            return false;
        
        //GT_Log.out.print("This far!");
        
        if (inputFocus < tRecipe.minFocus)
        	return false;
        
        //GT_Log.out.print("This far too!");
        
        if (inputParticle != tRecipe.particleId)
        	return false;
        
        //GT_Log.out.print("Passed beamline requirements");
        
        this.mMaxProgresstime =  Math.round((tRecipe.amount / inputRate * 10 * 20)); // 10 seconds per integer multiple over the rate. E.g., 100a, 10r would equal 100 seconds
        if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;
        
        mEUt = (int) -tVoltage;
        if (this.mEUt > 0) this.mEUt = (-this.mEUt);
        
        
        this.mOutputItems = tRecipe.mOutputs;
        
        mInputFocus.get(0).depleteFocusDurability(1);
        
        this.updateSlots();
        
        return true;
	}
	
	private BeamInformation getInputInformation() {

        for (TileHatchInputBeamline in : this.mInputBeamline) {

            //if (in.q == null) return new BeamInformation(0, 0, 0, 0);
            if (in.q == null) return new BeamInformation(10000, 10, 0, 90); // TODO temporary for testing purposes

            return in.q.getContent();
        }
        return null;
    }
	
	private ItemStack getFocusItemStack() {
		
		for (TileBusInputFocus hatch : this.mInputFocus) {
			return hatch.getContentUsageSlots().get(0);
		}
		
		return null;
		
	}
	

	@Override
	public boolean checkMachine(IGregTechTileEntity arg0, ItemStack arg1) {
		
		mInputBeamline.clear();
		mInputFocus.clear();
		
		return checkPiece("base", 2, 4, 0);
	}

	@Override
	public boolean explodesOnComponentBreak(ItemStack arg0) {
		return false;
	}

	@Override
	public int getDamageToComponent(ItemStack arg0) {
		return 0;
	}

	@Override
	public int getMaxEfficiency(ItemStack arg0) {
		return 10000;
	}

	@Override
	public boolean isCorrectMachinePart(ItemStack arg0) {
		return true;
	}
	
	@Override
    public String[] getInfoData() {
        int mPollutionReduction = 0;
        for (GT_MetaTileEntity_Hatch_Muffler tHatch : mMufflerHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                mPollutionReduction = Math.max(tHatch.calculatePollutionReduction(100), mPollutionReduction);
            }
        }

        long storedEnergy = 0;
        long maxEnergy = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy += tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }

        BeamInformation information = this.getInputInformation();
        
        return new String[] {
                /* 1 */ StatCollector.translateToLocal("GT5U.multiblock.Progress") + ": "
                        + EnumChatFormatting.GREEN
                        + GT_Utility.formatNumbers(mProgresstime / 20)
                        + EnumChatFormatting.RESET
                        + " s / "
                        + EnumChatFormatting.YELLOW
                        + GT_Utility.formatNumbers(mMaxProgresstime / 20)
                        + EnumChatFormatting.RESET
                        + " s",
                /* 2 */ StatCollector.translateToLocal("GT5U.multiblock.energy") + ": "
                        + EnumChatFormatting.GREEN
                        + GT_Utility.formatNumbers(storedEnergy)
                        + EnumChatFormatting.RESET
                        + " EU / "
                        + EnumChatFormatting.YELLOW
                        + GT_Utility.formatNumbers(maxEnergy)
                        + EnumChatFormatting.RESET
                        + " EU",
                /* 3 */ StatCollector.translateToLocal("GT5U.multiblock.usage") + ": "
                        + EnumChatFormatting.RED
                        + GT_Utility.formatNumbers(getActualEnergyUsage())
                        + EnumChatFormatting.RESET
                        + " EU/t",
                /* 4 */ StatCollector.translateToLocal("GT5U.multiblock.mei") + ": "
                        + EnumChatFormatting.YELLOW
                        + GT_Utility.formatNumbers(getMaxInputVoltage())
                        + EnumChatFormatting.RESET
                        + " EU/t(*2A) "
                        + StatCollector.translateToLocal("GT5U.machines.tier")
                        + ": "
                        + EnumChatFormatting.YELLOW
                        + VN[GT_Utility.getTier(getMaxInputVoltage())]
                        + EnumChatFormatting.RESET,
                /* 5 */ StatCollector.translateToLocal("GT5U.multiblock.problems") + ": "
                        + EnumChatFormatting.RED
                        + (getIdealStatus() - getRepairStatus())
                        + EnumChatFormatting.RESET
                        + " "
                        + StatCollector.translateToLocal("GT5U.multiblock.efficiency")
                        + ": "
                        + EnumChatFormatting.YELLOW
                        + Float.toString(mEfficiency / 100.0F)
                        + EnumChatFormatting.RESET
                        + " %",
                /* 6 */ StatCollector.translateToLocal("GT5U.multiblock.pollution") + ": "
                        + EnumChatFormatting.GREEN
                        + mPollutionReduction
                        + EnumChatFormatting.RESET
                        + " %",

                /* 7 */ EnumChatFormatting.BOLD + StatCollector.translateToLocal("beamline.in_pre")
                        + ": "
                        + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("beamline.particle") + ": " // "Multiblock Beamline Input:"
                        + EnumChatFormatting.GOLD
                        + Particle.getParticleFromId(information.getParticleId()).getLocalisedName() // e.g. "Electron
                                                                                                     // (e-)"
                        + " "
                        + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("beamline.energy") + ": " // "Energy:"
                        + EnumChatFormatting.DARK_RED
                        + information.getEnergy() * 1000 // In line with the synchrotron's output
                        + EnumChatFormatting.RESET
                        + " eV", // e.g. "10240 eV"
                StatCollector.translateToLocal("beamline.focus") + ": " // "Focus:"
                        + EnumChatFormatting.BLUE
                        + information.getFocus()
                        + " "
                        + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("beamline.amount") + ": " // "Amount:"
                        + EnumChatFormatting.LIGHT_PURPLE
                        + information.getRate(),
                };
    }

}
