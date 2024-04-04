package com.elisis.gtnhlanth.common.tileentity;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static gregtech.api.enums.GT_Values.VN;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import java.util.ArrayList;

import com.elisis.gtnhlanth.common.beamline.BeamInformation;
import com.elisis.gtnhlanth.common.beamline.BeamLinePacket;
import com.elisis.gtnhlanth.common.beamline.Particle;
import com.elisis.gtnhlanth.common.hatch.TileHatchOutputBeamline;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.elisis.gtnhlanth.common.tileentity.recipe.beamline.BeamlineRecipeAdder2;
import com.elisis.gtnhlanth.common.tileentity.recipe.beamline.RecipeSC;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class SourceChamber extends GT_MetaTileEntity_EnhancedMultiBlockBase<SourceChamber> implements ISurvivalConstructable {

    private static final IStructureDefinition<SourceChamber> STRUCTURE_DEFINITION;

    private ArrayList<TileHatchOutputBeamline> mOutputBeamline = new ArrayList<>();

    private float outputEnergy;
    private int outputRate;
    private int outputParticle;
    private float outputFocus;

    static {
        STRUCTURE_DEFINITION = StructureDefinition.<SourceChamber>builder()
                .addShape(
                        "sc",
                        new String[][] { { "ccccc", "ckkkc", "ckikc", "ckkkc", "dd~dd" },
                                { "ckkkc", "keeek", "ke-ek", "keeek", "ccocc" },
                                { "ckkkc", "k---k", "k---k", "k---k", "ccccc" },
                                { "ckkkc", "k---k", "k---k", "k---k", "ccccc" },
                                { "ckkkc", "keeek", "ke-ek", "keeek", "ccccc" },
                                { "ccccc", "ckkkc", "ckbkc", "ckkkc", "ccccc" } })
                .addElement('c', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('k', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_GLASS, 0))
                .addElement('e', ofBlock(LanthItemList.ELECTRODE_CASING, 0))
                .addElement('b', ofHatchAdder(SourceChamber::addBeamLineOutputHatch, 49, 1))
                .addElement('i', ofHatchAdder(GT_MetaTileEntity_MultiBlockBase::addInputBusToMachineList, 49, 1))
                .addElement('o', ofHatchAdder(GT_MetaTileEntity_MultiBlockBase::addOutputBusToMachineList, 49, 1))
                .addElement(
                        'd',
                        ofChain(
                                ofHatchAdder(SourceChamber::addMaintenanceToMachineList, 49, 1),
                                ofHatchAdder(SourceChamber::addEnergyInputToMachineList, 49, 1),
                                ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0)))
                .build();
    }

    public SourceChamber(int id, String name, String nameRegional) {
        super(id, name, nameRegional);
    }

    public SourceChamber(String name) {
        super(name);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity te) {
        return new SourceChamber(this.mName);
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Particle Source").addInfo("Controller block for the Source Chamber")
                .toolTipFinisher("GTNH: Lanthanides");;
        return tt;
    }

    private boolean addBeamLineOutputHatch(IGregTechTileEntity te, int casingIndex) {

        if (te == null) return false;

        IMetaTileEntity mte = te.getMetaTileEntity();

        if (mte == null) return false;

        if (mte instanceof TileHatchOutputBeamline) {
            return this.mOutputBeamline.add((TileHatchOutputBeamline) mte);
        }

        return false;
    }
/*
    protected OverclockDescriber createOverclockDescriber() {
        return new EUNoTotalOverclockDescriber((byte) 4, 1);
    }*/
    
    // TODO: Variable recipe duration
    @Override
    public boolean checkRecipe(ItemStack itemStack) {

        // GT_Log.out.print("In checkRecipe");

        // No input particle, so no input quantities

        outputFocus = 0;
        outputEnergy = 0;
        outputParticle = 0;
        outputRate = 0;

        ItemStack[] tItems = this.getStoredInputs().toArray(new ItemStack[0]);
        // GT_Log.out.print(Arrays.toString(tItems));
        long tVoltage = this.getMaxInputVoltage();

        /*
         * for (GT_Recipe stack : BeamlineRecipeAdder.instance.SourceChamberRecipes.mRecipeList) {
         * GT_Log.out.print("Recipe item " + Arrays.toString(stack.mInputs)); }
         */

        RecipeSC tRecipe = (RecipeSC) BeamlineRecipeAdder2.instance.SourceChamberRecipes
                .findRecipe(this.getBaseMetaTileEntity(), false, tVoltage, new FluidStack[] {}, tItems);

        if (tRecipe == null || !tRecipe.isRecipeInputEqual(true, new FluidStack[] {}, tItems)) return false; // Consumes
                                                                                                             // input
                                                                                                             // item

        // GT_Log.out.print("Recipe good!");

        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        this.mMaxProgresstime = tRecipe.mDuration;
        if (mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;

        mEUt = (int) -tVoltage;
        if (this.mEUt > 0) this.mEUt = (-this.mEUt);

        outputParticle = tRecipe.particleId;
        float maxParticleEnergy = Particle.getParticleFromId(outputParticle).maxSourceEnergy(); // The maximum energy a
                                                                                                // particle can possess
                                                                                                // when produced by this
                                                                                                // multiblock
        float maxMaterialEnergy = tRecipe.maxEnergy; // The maximum energy for the recipe processed
        // outputEnergy = (float) ((-maxEnergy) * Math.pow(1.001, -(tRecipe.energyRatio)*(tVoltage-tRecipe.mEUt))) +
        // maxEnergy;
        outputEnergy = (float) Math.min(
                (-maxMaterialEnergy) * Math.pow(1.001, -(tRecipe.energyRatio) * (tVoltage - tRecipe.mEUt))
                        + maxMaterialEnergy,
                maxParticleEnergy);

        // GT_Log.out.print(outputEnergy);

        if (outputEnergy <= 0) {
            stopMachine();
        }

        outputFocus = tRecipe.focus;
        outputRate = tRecipe.rate;

        this.mOutputItems = tRecipe.mOutputs;
        this.updateSlots();

        outputAfterRecipe();

        return true;
    }

    private void outputAfterRecipe() {

        if (!mOutputBeamline.isEmpty()) {

            BeamLinePacket packet = new BeamLinePacket(
                    new BeamInformation(outputEnergy, outputRate, outputParticle, outputFocus));

            for (TileHatchOutputBeamline o : mOutputBeamline) {

                o.q = packet;
            }
        }
    }

    @Override
    public void stopMachine() {
        outputFocus = 0;
        outputEnergy = 0;
        outputParticle = 0;
        outputRate = 0;
        super.stopMachine();
    }

    @Override
    public String[] getInfoData() {
        int mPollutionReduction = 0;
        for (GT_MetaTileEntity_Hatch_Muffler tHatch : mMufflerHatches) {
            if (tHatch.isValid()) {
                mPollutionReduction = Math.max(tHatch.calculatePollutionReduction(100), mPollutionReduction);
            }
        }

        long storedEnergy = 0;
        long maxEnergy = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (tHatch.isValid()) {
                storedEnergy += tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }

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
                EnumChatFormatting.BOLD + StatCollector.translateToLocal("beamline.out_pre")
                        + ": "
                        + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("beamline.particle") + ": "
                        + EnumChatFormatting.GOLD
                        + Particle.getParticleFromId(this.outputParticle).getLocalisedName()
                        + " "
                        + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("beamline.energy") + ": "
                        + EnumChatFormatting.DARK_RED
                        + this.outputEnergy
                        + EnumChatFormatting.RESET
                        + " keV",
                StatCollector.translateToLocal("beamline.focus") + ": "
                        + EnumChatFormatting.BLUE
                        + this.outputFocus
                        + " "
                        + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("beamline.amount") + ": "
                        + EnumChatFormatting.LIGHT_PURPLE
                        + this.outputRate, };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece("sc", stackSize, hintsOnly, 2, 4, 0);
    }

    @Override
    public IStructureDefinition<SourceChamber> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece("sc", 2, 4, 0);
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean active, boolean redstoneLevel) {
        // TODO Auto-generated method stub
        return null;
    }
}
