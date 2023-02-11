package com.elisis.gtnhlanth.common.beamline;

import net.minecraft.util.StatCollector;

public enum Particle {

    ELECTRON(true, 0, 0.511f, 5000, "electron", "e\u207B"),
    PHOTON(false, 1, 0, 0, "photon", "\u03B3"),
    NEUTRON(false, 2, 939.57f, 15000, "neutron", "n\u2070"),
    PROTON(true, 3, 938.27f, 15000, "proton", "p\u207A"),
    ALPHA(true, 4, 3727.38f, 8000, "alpha", "\u03B1");

    private boolean canAcc;

    private float restMass; // in MeV

    private float maxSourceEnergy; // in keV

    private String name;
    private String shortName;

    private Particle(boolean canAcc, int id, float restMass, float maxSourceEnergy, String name, String shortName) { // ID
                                                                                                                     // is
                                                                                                                     // symbolic
                                                                                                                     // only
        this.canAcc = canAcc;
        this.restMass = restMass;
        this.maxSourceEnergy = maxSourceEnergy;
        this.name = name;
        this.shortName = shortName;
    }

    public float getMass() {
        return this.restMass;
    }

    public boolean canAccelerate() {
        return this.canAcc;
    }

    public float maxSourceEnergy() {
        return this.maxSourceEnergy;
    }

    public String getLocalisedName() {
        return StatCollector.translateToLocal("particle." + this.name) + " (" + this.shortName + ")";
    }
    
    public static Particle getParticleFromId(int id) {
    	return Particle.values()[id];
    }
}
