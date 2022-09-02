package com.elisis.gtnhlanth.common.beamline;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;

public interface IConnectsToBeamline extends IMetaTileEntity {

    boolean canConnect(byte side);

    IConnectsToBeamline getNext(IConnectsToBeamline source);

    boolean isDataInputFacing(byte side);
}
