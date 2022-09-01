package com.elisis.gtnhlanth.common.hatch;

import com.elisis.gtnhlanth.common.beamline.BeamLinePacket;
import com.elisis.gtnhlanth.common.beamline.IConnectsToBeamline;
import com.github.technus.tectech.mechanics.pipe.IConnectsToDataPipe;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector;
import com.github.technus.tectech.util.TT_Utility;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.nbt.NBTTagCompound;

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
		// TODO Auto-generated method stub
		return null;
	}
    
    @Override
    public void moveAround(IGregTechTileEntity aBaseMetaTileEntity) {
        IConnectsToBeamline current = this, source = this, next;
        int range = 0;
        while ((next = current.getNext(source)) != null && range++ < 1000) { //TODO
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
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
		return new TileHatchOutputBeamline(mName, mTier, mDescription, mTextures);
	}

	

	
	
}
