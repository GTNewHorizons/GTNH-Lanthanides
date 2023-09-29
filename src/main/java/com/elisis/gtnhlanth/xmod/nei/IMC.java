package com.elisis.gtnhlanth.xmod.nei;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInterModComms;

public class IMC {

    public static void IMCSender() {
        sendHandler("gtnhlanth.recipe.digester", "gregtech:gt.blockmachines:10500");
        sendHandler("gtnhlanth.recipe.disstank", "gregtech:gt.blockmachines:10501");
        sendHandler("gtnhlanth.recipe.tc", "gregtech:gt.blockmachines:10508");

        sendCatalyst("gtnhlanth.recipe.digester", "gregtech:gt.blockmachines:10500");
        sendCatalyst("gtnhlanth.recipe.disstank", "gregtech:gt.blockmachines:10501");
        sendCatalyst("gtnhlanth.recipe.tc", "gregtech:gt.blockmachines:10508");
    }

    private static void sendHandler(String aName, String aBlock) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", "GTNH: Lanthanides");
        aNBT.setString("modId", "gtnhlanth");
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", 135);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", 1);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", aStack);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }
}
