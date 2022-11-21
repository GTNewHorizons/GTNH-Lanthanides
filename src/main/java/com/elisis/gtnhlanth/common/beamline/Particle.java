package com.elisis.gtnhlanth.common.beamline;

import net.minecraft.util.StatCollector;

public enum Particle {
    ELECTRON(true, 0, 0.511f, 5000, "electron", "e\u207B"),
    PHOTON(false, 1, 0, 0, "photon", "\u03B3");

    private boolean canAcc;

    private float restMass;
    
    private float maxSourceEnergy;

    private String name;
    private String shortName;

    private Particle(boolean canAcc, int id, float restMass, float maxSourceEnergy, String name, String shortName) { // ID is symbolic only
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
}
