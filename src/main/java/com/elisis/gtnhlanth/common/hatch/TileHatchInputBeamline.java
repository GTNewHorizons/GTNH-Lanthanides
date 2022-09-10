package com.elisis.gtnhlanth.common.hatch;

import com.elisis.gtnhlanth.common.beamline.BeamLinePacket;
import com.elisis.gtnhlanth.common.beamline.IConnectsToBeamline;
import com.github.technus.tectech.util.TT_Utility;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class TileHatchInputBeamline extends TileHatchBeamlineConnector<BeamLinePacket> {

    private boolean delay = true;

    public TileHatchInputBeamline(int id, String name, String nameRegional, int tier) {

        super(id, name, nameRegional, tier, "");
        TT_Utility.setTier(tier, this);
    }

    public TileHatchInputBeamline(String name, int tier, String desc, ITexture[][][] textures) {
        super(name, tier, desc, textures);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity tile) {
        return new TileHatchInputBeamline(mName, mTier, mDescription, mTextures);
    }

    @Override
    protected BeamLinePacket loadPacketFromNBT(NBTTagCompound tag) {
        return new BeamLinePacket(tag);
    }

    @Override
    public boolean isInputFacing(byte side) {
        return side == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isDataInputFacing(byte side) {
        return isInputFacing(side);
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return false;
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean canConnect(byte side) {
        return isInputFacing(side);
    }

    @Override
    public IConnectsToBeamline getNext(IConnectsToBeamline source) {
        return null;
    }

    @Override
    public String[] getDescription() {
        return null;
    }

    public void setContents(BeamLinePacket in) {
        if (in == null) {
            this.q = null;
        } else {
            if (in.getContent().getRate() > 0) {
                this.q = in;
                delay = true;
            } else {
                this.q = null;
            }
        }
    }

    @Override
    public void moveAround(IGregTechTileEntity tile) {
        if (delay) {
            delay = false;
        } else {
            this.setContents(null);
        }
    }
}
