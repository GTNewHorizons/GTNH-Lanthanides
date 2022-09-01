package com.elisis.gtnhlanth.common.beamline;

import gregtech.GT_Mod;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaPipeEntity;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Client;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBeamline extends MetaPipeEntity implements IConnectsToBeamline {

	private byte connectionCount = 0;
	
	private boolean active;
	
	public TileBeamline(int id, String name, String nameRegional) {
		super(id, name, nameRegional, 0);
	}
	
	public TileBeamline(String name) {
		super(name, 0);
	}

	@Override
	public boolean allowPullStack(IGregTechTileEntity arg0, int arg1, byte arg2, ItemStack arg3) {
		return false;
	}

	@Override
	public boolean allowPutStack(IGregTechTileEntity arg0, int arg1, byte arg2, ItemStack arg3) {
		return false;
	}

	@Override
	public String[] getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITexture[] getTexture(IGregTechTileEntity arg0, byte arg1, byte arg2, byte arg3, boolean arg4,
			boolean arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getTileEntityBaseType() {
		return 7;
	}

	@Override
	public void loadNBTData(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
		// TODO Auto-generated method stub
		return new TileBeamline(mName);
	}

	@Override
	public void saveNBTData(NBTTagCompound arg0) {
		
	}

	@Override
	public float getThickNess() {
		if (GT_Mod.instance.isClientSide() && GT_Client.hideValue == 1) {
            return 0.0625F;
        }
        return 0.5f;
	}

	@Override
	public boolean renderInside(byte arg0) {
		return false;
	}

	@Override
	public boolean canConnect(byte side) {
		return true;
	}

	//Largely taken from Tec's DataPipe
	
	@Override
	public IConnectsToBeamline getNext(IConnectsToBeamline source) {
		if (connectionCount != 2) {
			return null;
		}
		
		for (byte b = 0; b < 6; b++) {
			
			if ((mConnections & 1 << b) == 0) {
				continue;
			}
		
			TileEntity next = this.getBaseMetaTileEntity().getTileEntityAtSide(b);
			if (next instanceof IConnectsToBeamline && next != source) {
				
				if (((IConnectsToBeamline) next).isInputFacing(GT_Utility.getOppositeSide(b))) {
					return (IConnectsToBeamline) next;
				}
			
			} else if (next instanceof IGregTechTileEntity) {
				
				IMetaTileEntity meta = ((IGregTechTileEntity) next).getMetaTileEntity();
				if (meta instanceof IConnectsToBeamline && meta != source) {
					
					if (meta instanceof TileBeamline && (((TileBeamline) meta).connectionCount == 2)) {
						
						((TileBeamline) meta).markUsed();
						return (IConnectsToBeamline) meta;
					
					}
					
					if (((IConnectsToBeamline) meta).isInputFacing(GT_Utility.getOppositeSide(b))) {
						
						return (IConnectsToBeamline) meta;
						
					}
					
				}
			
			}
		
		}
		
		return null;
	}

	public void markUsed() {
		this.active = true;
	}
	
	@Override
	public boolean isInputFacing(byte side) {
		// TODO Auto-generated method stub
		return false;
	}

}
