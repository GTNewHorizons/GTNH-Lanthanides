package com.elisis.gtnhlanth.common.beamline;

public class BeamInformation {

	private float energy;
	private int rate;
	
	private Particle particle;
	private int particleId;

	public BeamInformation(float energy, int rate, int particleId) {
		this.energy = energy;
		this.rate = rate;
		this.particleId = particleId;
		this.particle = Particle.values()[particleId];
	}
	
	public float getEnergy() {
		return this.energy;
	}
	
	public int getRate() {
		return this.rate;
	}
	
	public Particle getParticle() {
		return this.particle;
	}
	
	public int getParticleId() {
		return this.particleId;
	}

	
}
