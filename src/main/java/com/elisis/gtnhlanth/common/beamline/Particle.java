package com.elisis.gtnhlanth.common.beamline;

public enum Particle {
	
	ELECTRON(0, 0.511f),
	PHOTON(1, 0)
	
	;
	
	private float restMass;
	
	private Particle(int id, float restMass) { //ID is symbolic only
		this.restMass = restMass;
	}
	
	public float getMass() {
		return this.restMass;
	}
}
