package com.elisis.gtnhlanth.common.hatch;

import com.elisis.gtnhlanth.common.beamline.BeamLinePacket;
import com.github.technus.tectech.mechanics.pipe.IConnectsToDataPipe;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector;
import com.github.technus.tectech.util.TT_Utility;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class TileHatchOutputBeamline extends GT_MetaTileEntity_Hatch_DataConnector<BeamLinePacket> {

	public TileHatchOutputBeamline(int id, String name, String nameRegional, int tier) {
        super(id, name, nameRegional, tier, "");
        TT_Utility.setTier(tier, this);
    }

    public TileHatchOutputBeamline(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

	@Override
	public boolean canConnectData(byte arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConnectsToDataPipe getNext(IConnectsToDataPipe arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataInputFacing(byte arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BeamLinePacket loadPacketFromNBT(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveAround(IGregTechTileEntity arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
