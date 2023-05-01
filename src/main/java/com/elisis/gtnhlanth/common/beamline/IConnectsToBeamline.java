package com.elisis.gtnhlanth.common.beamline;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface IConnectsToBeamline extends IMetaTileEntity {

    boolean canConnect(ForgeDirection side);

    IConnectsToBeamline getNext(IConnectsToBeamline source);

    boolean isDataInputFacing(ForgeDirection side);
}
