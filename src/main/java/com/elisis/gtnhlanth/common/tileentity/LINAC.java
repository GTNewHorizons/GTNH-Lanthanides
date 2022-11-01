package com.elisis.gtnhlanth.common.tileentity;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockAdder;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import com.elisis.gtnhlanth.common.beamline.BeamInformation;
import com.elisis.gtnhlanth.common.hatch.TileHatchInputBeamline;
import com.elisis.gtnhlanth.common.hatch.TileHatchOutputBeamline;
import com.elisis.gtnhlanth.common.register.LanthItemList;
import com.github.bartimaeusnek.bartworks.common.loaders.ItemRegistry;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LINAC extends GT_MetaTileEntity_EnhancedMultiBlockBase<LINAC> implements IConstructable {

    private static final IStructureDefinition<LINAC> STRUCTURE_DEFINITION;

    protected static final String STRUCTURE_PIECE_BASE = "base";
    protected static final String STRUCTURE_PIECE_LAYER = "layer";
    protected static final String STRUCTURE_PIECE_END = "end";

    private ArrayList<TileHatchInputBeamline> mInputBeamline = new ArrayList<>();
    private ArrayList<TileHatchOutputBeamline> mOutputBeamline = new ArrayList<>();

    private static final int CASING_INDEX = 49;

    /*
     * g: Grate Machine Casing
     * b: Borosilicate glass
     * c: Shielded accelerator casing
     * v: Vacuum
     * k: Shielded glass
     * d: Coolant Delivery casing
     * y: Superconducting coil
     */

    static {
        STRUCTURE_DEFINITION = StructureDefinition.<LINAC>builder()
                .addShape(STRUCTURE_PIECE_BASE, transpose(new String[][] {
                    {"ggggggg", "gbbbbbg", "gbbbbbg", "gbbibbg", "gbbbbbg", "gbbbbbg", "ggg~ggg"},
                    {"ggggggg", "gcccccg", "gcccccg", "gcc-ccg", "gcccccg", "gcccccg", "ggggggg"},
                    {"ccccccc", "cvvvvvc", "kvvvvvk", "kvv-vvk", "kvvvvvk", "cvvvvvc", "jcccccj"},
                    {"cckkkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "jcccccj"},
                    {"cckkkcc", "cdvvvdc", "kvvvvvk", "kdv-vdk", "kvvvvvk", "cdvvvdc", "jcccccj"},
                    {"cckkkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "jcccccj"},
                    {"cckkkcc", "cdvvvdc", "kvvvvvk", "kdv-vdk", "kvvvvvk", "cdvvvdc", "jcccccj"},
                    {"cckhkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "jcccccj"},
                }))
                .addShape(STRUCTURE_PIECE_LAYER, transpose(new String[][] {
                    {"cckkkcc", "cdvvvdc", "kvvvvvk", "kdv-vdk", "kvvvvvk", "cdvvvdc", "ccccccc"},
                    {"cckkkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "ccccccc"}
                }))
                .addShape(STRUCTURE_PIECE_END, transpose(new String[][] {
                    {"cckhkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "ccccccc"},
                    {"cckkkcc", "cdvvvdc", "kvvvvvk", "kdv-vdk", "kvvvvvk", "cdvvvdc", "ccccccc"},
                    {"cckkkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "ccccccc"},
                    {"cckkkcc", "cdvvvdc", "kvvvvvk", "kdv-vdk", "kvvvvvk", "cdvvvdc", "ccccccc"},
                    {"cckkkcc", "cdddddc", "kdyyydk", "kdy-ydk", "kdyyydk", "cdddddc", "ccccccc"},
                    {"ccccccc", "cvvvvvc", "kvvvvvk", "kvv-vvk", "kvvvvvk", "cvvvvvc", "ccccccc"},
                    {"ccccccc", "ccccccc", "ccccccc", "ccc-ccc", "ccccccc", "ccccccc", "ccccccc"},
                    {"ccccccc", "cbbbbbc", "cbbbbbc", "cbbobbc", "cbbbbbc", "cbbbbbc", "ccccccc"}
                }))
                .addElement('c', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('g', ofBlock(GregTech_API.sBlockCasings3, 10))
                .addElement('b', ofBlockAdder(LINAC::addGlass, ItemRegistry.bw_glasses[0], 1))
                .addElement('i', ofHatchAdder(LINAC::addBeamLineInputHatch, CASING_INDEX, 1))
                .addElement('o', ofHatchAdder(LINAC::addBeamLineOutputHatch, CASING_INDEX, 1))
                .addElement('v', ofBlock(LanthItemList.ELECTRODE_CASING, 0))
                .addElement('k', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_GLASS, 0))
                .addElement('d', ofBlock(LanthItemList.COOLANT_DELIVERY_CASING, 0))
                .addElement('y', ofBlock(GregTech_API.sBlockCasings1, 15))
                .addElement(
                        'h',
                        ofChain(
                                ofHatchAdder(LINAC::addInputHatchToMachineList, CASING_INDEX, 1),
                                ofHatchAdder(LINAC::addOutputHatchToMachineList, CASING_INDEX, 1)))
                .addElement(
                        'j',
                        ofChain(
                                ofHatchAdder(LINAC::addMaintenanceToMachineList, CASING_INDEX, 1),
                                ofHatchAdder(LINAC::addEnergyInputToMachineList, CASING_INDEX, 1)))
                .build();
    }

    private float outputEnergy;
    private int outputRate;
    private int outputParticle;
    private float outputFocus;

    private int length;

    public LINAC(int id, String name, String nameRegional) {
        super(id, name, nameRegional);
    }

    public LINAC(String name) {
        super(name);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity mte, ItemStack stack) {

        mInputBeamline.clear();
        mOutputBeamline.clear();

        this.outputEnergy = 0;
        this.outputRate = 0;
        this.outputParticle = 0;
        this.outputFocus = 0;

        int length = 8; // Base piece length

        if (!checkPiece(STRUCTURE_PIECE_BASE, 3, 0, 0)) return false;

        while (length < 128) {

            if (!checkPiece(STRUCTURE_PIECE_BASE, 3, 0, length)) return false;

            length += 2;
        }

        if (!checkPiece(STRUCTURE_PIECE_END, 2, 0, length)) return false;

        length += 8;

        return this.mInputBeamline.size() == 1
                && this.mOutputBeamline.size() == 1
                && this.mMaintenanceHatches.size() == 1
                && this.mEnergyHatches.size() <= 2;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity te) {
        return new LINAC(this.mName);
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Particle Accelerator")
                .addInfo("Controller block for the LINAC")
                .addInfo("Extendable, with a minimum length of 18 blocks")
                .toolTipFinisher("GTNH: Lanthanides");
        ;
        return tt;
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

    private boolean addBeamLineOutputHatch(IGregTechTileEntity te, int casingIndex) {

        if (te == null) return false;

        IMetaTileEntity mte = te.getMetaTileEntity();

        if (mte == null) return false;

        if (mte instanceof TileHatchOutputBeamline) {
            return this.mOutputBeamline.add((TileHatchOutputBeamline) mte);
        }

        return false;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {

        List<FluidStack> tFluidInputs = this.getStoredFluids();
        FluidStack primFluid = tFluidInputs.get(0);

        final int fluidConsumed = 1000 * length;

        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        float tempRatio = calculateTemperatureRatio(primFluid.getFluid().getTemperature());

        if (primFluid.amount < fluidConsumed || primFluid.getFluid().getTemperature() > 200) {

            stopMachine();
            return false;
        }

        primFluid.amount -= fluidConsumed;

        FluidStack fluidOutput =
                Materials.getGtMaterialFromFluid(primFluid.getFluid()).getGas(fluidConsumed);

        if (Objects.isNull(fluidOutput)) return false;

        this.addFluidOutputs(new FluidStack[] {fluidOutput});
        return true;
    }

    private BeamInformation getInputInformation() {

        for (TileHatchInputBeamline in : this.mInputBeamline) {

            if (in.q == null) return null;

            return in.q.getContent();
        }
        return null;
    }

    private static float calculateTemperatureRatio(int fluidTemp) {

        return 1; // TODO
    }

    private boolean addGlass(Block block, int meta) {
        return block == ItemRegistry.bw_glasses[0];
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {

        buildPiece(STRUCTURE_PIECE_BASE, stackSize, hintsOnly, 3, 0, 3);

        int lLength = Math.max(stackSize.stackSize - 16, 2);

        for (int i = 9; i < lLength - 1; i += 2) {

            buildPiece(STRUCTURE_PIECE_LAYER, stackSize, hintsOnly, 3, 0, i);
        }

        buildPiece(STRUCTURE_PIECE_END, stackSize, hintsOnly, 3, 0, lLength);
    }

    @Override
    public ITexture[] getTexture(
            IGregTechTileEntity aBaseMetaTileEntity,
            byte aSide,
            byte aFacing,
            byte aColorIndex,
            boolean aActive,
            boolean aRedstone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IStructureDefinition<LINAC> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
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
}
