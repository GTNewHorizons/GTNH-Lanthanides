package com.elisis.gtnhlanth.common.beamline;

public interface IConnectsToBeamline {
	
	boolean canConnect(byte side);
	
	IConnectsToBeamline getNext(IConnectsToBeamline source);
	
	boolean isInputFacing(byte side);

}
