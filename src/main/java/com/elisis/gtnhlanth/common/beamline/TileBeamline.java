package com.elisis.gtnhlanth.common.beamline;

import static gregtech.api.enums.Dyes.MACHINE_METAL;

import com.github.bartimaeusnek.bartworks.util.BW_Tooltip_Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.GT_Mod;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.MetaPipeEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Client;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileBeamline extends MetaPipeEntity implements IConnectsToBeamline {

    private static Textures.BlockIcons.CustomIcon pipe;

    private byte connectionCount = 0;

    private boolean active;

    public TileBeamline(int id, String name, String nameRegional) {
        super(id, name, nameRegional, 0);
    }

    public TileBeamline(String name) {
        super(name, 0);
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity arg0, int arg1, byte arg2, ItemStack arg3) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity arg0, int arg1, byte arg2, ItemStack arg3) {
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            if ((aTick & 31) == 31) {
                mConnections = 0;
                connectionCount = 0;

                for (byte b0 = 0, b1; b0 < 6; b0++) {
                    b1 = GT_Utility.getOppositeSide(b0);
                    TileEntity tTileEntity = aBaseMetaTileEntity.getTileEntityAtSide(b0);
                    if (tTileEntity instanceof IConnectsToBeamline) {
                        if (((IConnectsToBeamline) tTileEntity).canConnect(b1)) {
                            mConnections |= 1 << b0;
                            connectionCount++;
                        }
                    } else if (tTileEntity instanceof IGregTechTileEntity) {
                        IMetaTileEntity meta = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
                        if (meta instanceof IConnectsToBeamline) {
                            if (((IConnectsToBeamline) meta).canConnect(b1)) {
                                mConnections |= 1 << b0;
                                connectionCount++;
                            }
                        }
                    }
                }
            }
        } else if (aBaseMetaTileEntity.isClientSide() && GT_Client.changeDetected == 4) {
            aBaseMetaTileEntity.issueTextureUpdate();
        }
    }

    @Override
    public byte getTileEntityBaseType() {
        return 7;
    }

    @Override
    public void loadNBTData(NBTTagCompound arg0) {}

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
        return new TileBeamline(mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound arg0) {}

    @Override
    public float getThickNess() {
        if (GT_Mod.instance.isClientSide() && GT_Client.hideValue == 1) {
            return 0.0625F;
        }
        return 0.5f;
    }

    @Override
    public boolean renderInside(byte arg0) {
        return false;
    }

    @Override
    public boolean canConnect(byte side) {
        return true;
    }

    // Largely taken from Tec's DataPipe

    @Override
    public IConnectsToBeamline getNext(IConnectsToBeamline source) {
        if (connectionCount != 2) {
            return null;
        }

        for (byte b = 0; b < 6; b++) {

            if ((mConnections & 1 << b) == 0) {
                continue;
            }

            TileEntity next = this.getBaseMetaTileEntity().getTileEntityAtSide(b);
            if (next instanceof IConnectsToBeamline && next != source) {

                if (((IConnectsToBeamline) next).isDataInputFacing(GT_Utility.getOppositeSide(b))) {
                    return (IConnectsToBeamline) next;
                }

            } else if (next instanceof IGregTechTileEntity) {

                IMetaTileEntity meta = ((IGregTechTileEntity) next).getMetaTileEntity();
                if (meta instanceof IConnectsToBeamline && meta != source) {

                    if (meta instanceof TileBeamline && (((TileBeamline) meta).connectionCount == 2)) {

                        ((TileBeamline) meta).markUsed();
                        return (IConnectsToBeamline) meta;
                    }

                    if (((IConnectsToBeamline) meta).isDataInputFacing(GT_Utility.getOppositeSide(b))) {

                        return (IConnectsToBeamline) meta;
                    }
                }
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        pipe = new Textures.BlockIcons.CustomIcon("iconsets/pipe");
        super.registerIcons(aBlockIconRegister);
    }

    @Override
    public ITexture[] getTexture(
            IGregTechTileEntity aBaseMetaTileEntity,
            byte aSide,
            byte aConnections,
            byte aColorIndex,
            boolean aConnected,
            boolean aRedstone) {
        return new ITexture[] {
            new GT_RenderedTexture(pipe),
            new GT_RenderedTexture(pipe, Dyes.getModulation(aColorIndex, MACHINE_METAL.getRGBA()))
        };
    }

    public void markUsed() {
        this.active = true;
    }

    @Override
    public boolean isDataInputFacing(byte side) {
        return true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        float tSpace = (1f - 0.375f) / 2;
        float tSide0 = tSpace;
        float tSide1 = 1f - tSpace;
        float tSide2 = tSpace;
        float tSide3 = 1f - tSpace;
        float tSide4 = tSpace;
        float tSide5 = 1f - tSpace;

        if (getBaseMetaTileEntity().getCoverIDAtSide((byte) 0) != 0) {
            tSide0 = tSide2 = tSide4 = 0;
            tSide3 = tSide5 = 1;
        }
        if (getBaseMetaTileEntity().getCoverIDAtSide((byte) 1) != 0) {
            tSide2 = tSide4 = 0;
            tSide1 = tSide3 = tSide5 = 1;
        }
        if (getBaseMetaTileEntity().getCoverIDAtSide((byte) 2) != 0) {
            tSide0 = tSide2 = tSide4 = 0;
            tSide1 = tSide5 = 1;
        }
        if (getBaseMetaTileEntity().getCoverIDAtSide((byte) 3) != 0) {
            tSide0 = tSide4 = 0;
            tSide1 = tSide3 = tSide5 = 1;
        }
        if (getBaseMetaTileEntity().getCoverIDAtSide((byte) 4) != 0) {
            tSide0 = tSide2 = tSide4 = 0;
            tSide1 = tSide3 = 1;
        }
        if (getBaseMetaTileEntity().getCoverIDAtSide((byte) 5) != 0) {
            tSide0 = tSide2 = 0;
            tSide1 = tSide3 = tSide5 = 1;
        }

        byte tConn = ((BaseMetaPipeEntity) getBaseMetaTileEntity()).mConnections;
        if ((tConn & 1 << ForgeDirection.DOWN.ordinal()) != 0) {
            tSide0 = 0f;
        }
        if ((tConn & 1 << ForgeDirection.UP.ordinal()) != 0) {
            tSide1 = 1f;
        }
        if ((tConn & 1 << ForgeDirection.NORTH.ordinal()) != 0) {
            tSide2 = 0f;
        }
        if ((tConn & 1 << ForgeDirection.SOUTH.ordinal()) != 0) {
            tSide3 = 1f;
        }
        if ((tConn & 1 << ForgeDirection.WEST.ordinal()) != 0) {
            tSide4 = 0f;
        }
        if ((tConn & 1 << ForgeDirection.EAST.ordinal()) != 0) {
            tSide5 = 1f;
        }

        return AxisAlignedBB.getBoundingBox(
                aX + tSide4, aY + tSide0, aZ + tSide2, aX + tSide5, aY + tSide1, aZ + tSide3);
    }
    
    @Override
    public String[] getDescription() {
        return new String[] {
            StatCollector.translateToLocal("beamline.pipe.desc.0"), // Beamline pipe
            EnumChatFormatting.AQUA
                    + StatCollector.translateToLocal("beamline.pipe.desc.1"), // Do not cross, split or turn
            "Added by " + EnumChatFormatting.GREEN + "GTNH: Lanthanides"
      
        };
    }
}
