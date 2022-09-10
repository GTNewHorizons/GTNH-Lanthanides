package com.elisis.gtnhlanth.common.beamline;

import com.github.technus.tectech.mechanics.dataTransport.DataPacket;
import net.minecraft.nbt.NBTTagCompound;

public class BeamLinePacket extends DataPacket<BeamInformation> {

    public BeamLinePacket(BeamInformation content) {
        super(content);
    }

    public BeamLinePacket(NBTTagCompound compound) {
        super(compound);
    }

    @Override
    protected BeamInformation contentFromNBT(NBTTagCompound nbt) {
        /*
        NBTTagCompound compound = nbt.getCompoundTag("beamline");
        */
        return new BeamInformation(nbt.getFloat("energy"), nbt.getInteger("rate"), nbt.getInteger("particleId"));
    }

    @Override
    protected NBTTagCompound contentToNBT() {

        NBTTagCompound compound = new NBTTagCompound();

        compound.setFloat("energy", content.getEnergy());
        compound.setInteger("rate", content.getRate());
        compound.setInteger("particleId", content.getParticleId());

        return compound;
    }

    @Override
    public boolean extraCheck() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected BeamInformation unifyContentWith(BeamInformation arg0) {
        throw new NoSuchMethodError("Unavailable to unify beam info data packet");
    }
}
