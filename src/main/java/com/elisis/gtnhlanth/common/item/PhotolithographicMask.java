package com.elisis.gtnhlanth.common.item;

import net.minecraft.item.Item;

public class PhotolithographicMask extends Item {

    private String name;

    public PhotolithographicMask(String name, int maxDamage) {
        super();
        this.name = name;
        this.setUnlocalizedName("photomask." + name);
        this.setMaxStackSize(1);
        this.setMaxDamage(maxDamage);
    }

    @Override
    public String getUnlocalizedName() {
        return "item.photomask." + this.name;
    }

}
