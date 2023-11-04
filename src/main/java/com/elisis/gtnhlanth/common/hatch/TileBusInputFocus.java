package com.elisis.gtnhlanth.common.hatch;

import com.elisis.gtnhlanth.common.item.ICanFocus;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.nbthandlers.GT_MetaTileEntity_Hatch_NbtConsumable;
import net.minecraft.item.ItemStack;

public class TileBusInputFocus extends GT_MetaTileEntity_Hatch_NbtConsumable {

	private static final int INPUT_SLOTS = 16;
	
	public TileBusInputFocus(int id, String name, String nameRegional) {
		super(id, name, nameRegional, 0, INPUT_SLOTS, "Input Bus for Foci", false);	
	}

	public TileBusInputFocus(String name, String[] descriptionArray, ITexture[][][] textures) {
		super(name, 0, INPUT_SLOTS, descriptionArray, false, textures);
	}

	@Override
	public int getInputSlotCount() {
		return INPUT_SLOTS;
	}

	@Override
	public AutoMap<ItemStack> getItemsValidForUsageSlots() {
		return new AutoMap<>();
	}
	
	@Override
    public boolean isItemValidForUsageSlot(ItemStack aStack) {
        return aStack.getItem() instanceof ICanFocus;
    }

	@Override
	public String getNameGUI() {
		return "Focus Input Bus";
	}

	@Override
	public ITexture[] getTexturesActive(ITexture arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITexture[] getTexturesInactive(ITexture arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new TileBusInputFocus(mName, mDescriptionArray, mTextures);
	}

}
