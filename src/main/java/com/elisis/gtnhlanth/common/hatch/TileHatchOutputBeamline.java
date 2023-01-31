package com.elisis.gtnhlanth.common.hatch;

import net.minecraft.nbt.NBTTagCompound;

import com.elisis.gtnhlanth.common.beamline.BeamLinePacket;
import com.elisis.gtnhlanth.common.beamline.IConnectsToBeamline;
import com.elisis.gtnhlanth.common.beamline.TileBeamline;
import com.github.technus.tectech.util.TT_Utility;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;

public class TileHatchOutputBeamline extends TileHatchBeamlineConnector<BeamLinePacket> implements IConnectsToBeamline {

    public TileHatchOutputBeamline(int id, String name, String nameRegional, int tier) {
        super(id, name, nameRegional, tier, "");
        TT_Utility.setTier(tier, this);
    }

    public TileHatchOutputBeamline(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public IConnectsToBeamline getNext(IConnectsToBeamline source) {

        IGregTechTileEntity base = this.getBaseMetaTileEntity();
        IGregTechTileEntity next = base.getIGregTechTileEntityAtSide(base.getFrontFacing());

        if (next == null) {
            return null;
        }

        IMetaTileEntity meta = next.getMetaTileEntity();
        if (meta instanceof TileBeamline) {

            ((TileBeamline) meta).markUsed();
            return (IConnectsToBeamline) meta;

        } else if (meta instanceof TileHatchInputBeamline
                && ((TileHatchInputBeamline) meta).canConnect(GT_Utility.getOppositeSide(base.getFrontFacing()))) {

                    return (IConnectsToBeamline) meta;
                }

        return null;
    }

    @Override
    public void moveAround(IGregTechTileEntity aBaseMetaTileEntity) {
        IConnectsToBeamline current = this, source = this, next;
        int range = 0;
        while ((next = current.getNext(source)) != null && range++ < 100) {
            if (next instanceof TileHatchInputBeamline) {
                ((TileHatchInputBeamline) next).setContents(q);
                break;
            }
            source = current;
            current = next;
        }
        q = null;
    }

    @Override
    protected BeamLinePacket loadPacketFromNBT(NBTTagCompound nbt) {
        return new BeamLinePacket(nbt);
    }

    @Override
    public boolean canConnect(byte side) {
        return this.isOutputFacing(side);
    }

    @Override
    public boolean isDataInputFacing(byte side) {
        return this.isInputFacing(side);
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return false;
    }

    @Override
    public boolean isOutputFacing(byte side) {
        return side == this.getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public String[] getDescription() {
        return null;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
        return new TileHatchOutputBeamline(mName, mTier, mDescription, mTextures);
    }
}
