package com.elisis.gtnhlanth.common.tileentity;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

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
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;

public class Synchrotron extends GT_MetaTileEntity_EnhancedMultiBlockBase<Synchrotron> implements IConstructable {

    private static final IStructureDefinition<Synchrotron> STRUCTURE_DEFINITION;

    protected static final String STRUCTURE_PIECE_ENTRANCE = "entrance";
    protected static final String STRUCTURE_PIECE_BASE = "base";

    private ArrayList<TileHatchInputBeamline> mInputBeamline = new ArrayList<>();
    private ArrayList<TileHatchOutputBeamline> mOutputBeamline = new ArrayList<>();

    /*
     * c: Shielded accelerator casing v: Vacuum k: Superconducting coil d: Coolant Delivery casing
     */

    // TODO: E > 1200eV for x-ray lithography
    static {

        STRUCTURE_DEFINITION = StructureDefinition.<Synchrotron>builder().addShape(
                STRUCTURE_PIECE_ENTRANCE,

                new String[][] { { "                                    ", "  ccc                               ",
                        " c---c                              ", " c---c                              ",
                        " c---c                              ", "  ccc                               " } })
                .addShape(
                        STRUCTURE_PIECE_BASE,

                        new String[][] {
                                { "                                    ", "  ccc                               ",
                                        " c---c       ccccccc                ", " c---c      cccc~cccc               ",
                                        " c---c       ccccccc                ", "  ccc                               ",
                                        "                                    " },
                                { "                                    ", "  ccc      ccccccccccc              ",
                                        " c---c    ccc-------ccc             ", " c---c    cc---------cc             ",
                                        " c---c    ccc-------ccc             ", "  ccc      ccccccccccc              ",
                                        "                                    " },
                                { "           ccccccccccc             ", "  ccc    cc-----------cc           ",
                                        " c---c  cc-------------cc          ", " c---c  cc-------------cc          ",
                                        " c---c  cc-------------cc          ", "  ccc    ccc---------ccc           ",
                                        "           ccccccccccc             " },
                                { "         ccccccccccccccc           ", "  ccc  cc---------------cc         ",
                                        " c---c c-----------------c         ", " c---ccc-----------------cc        ",
                                        " c---c c-----------------c         ", "  ccc  cc---------------cc         ",
                                        "         ccccccccccccccc           ",

                                },
                                { "  ccc   ccccccccccccccccc          ", " ckkkccc-----------------cc        ",
                                        "ck---kc-------------------cc       ", "ck---kc--------------------c       ",
                                        "ck---kc-------------------cc       ", " ckkkccc-----------------cc        ",
                                        "  ccc  cccccccccccccccccc          "

                                },
                                { "  cccccccccccc     ccccccc         ", " cdddcc-------ccccc-------cc       ",
                                        "cd---d----------------------c      ", "cd---d----------------------c      ",
                                        "cd---d----------------------c      ", " cdddcc-------ccccc-------cc       ",
                                        "  cccccccccccc     ccccccc         ", },
                                {

                                }

                        }

                ).addElement('c', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('k', ofBlock(GregTech_API.sBlockCasings1, 15)) // Superconducting coils
                .addElement('d', ofBlock(LanthItemList.COOLANT_DELIVERY_CASING, 0))

                .build();

    }

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
