package com.elisis.gtnhlanth.common.item;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;


public enum MaskList {

    // There are absolutely better ways of doing this than a GT Materials-esque Enum, some method of automatically
    // scraping the wafer types would be preferable in particular
    // Use Dyes._NULL to indicate a wafer's lack of a dedicated lens instead of null, if the wafer's mask is to be
    // generated
	// Ignore last argument if using all wafers
    ERROR("error", "ERROR", 0, "", null, null),
    BLANK1("blank1", "T1 Blank", 0, "VISIBLE", null, null),
    BLANK2("blank2", "T2 Blank", 0, "UV", null, null),
    BLANK3("blank3", "T3 Blank", 0, "X-RAY", null, null),
    ILC("ilc", "Integrated Logic Circuit", 100, "", BLANK1, Dyes.dyeRed),
    RAM("ram", "Random Access Memory", 200, "", BLANK1, Dyes.dyeCyan, ItemList.Circuit_Silicon_Wafer),
    NAND("nand", "NAND", 200, "", BLANK2, Dyes._NULL, ItemList.Circuit_Silicon_Wafer), // NAND uses only Ender Pearl lens, don't ask me why
    NOR("nor", "NOR", 100, "", BLANK2, Dyes._NULL, ItemList.Circuit_Silicon_Wafer, ItemList.Circuit_Silicon_Wafer2), // Same as above, but with ender eye
    CPU("cpu", "Central Processing Unit", 10, "", BLANK2, Dyes.dyeWhite, ItemList.Circuit_Silicon_Wafer),
    SOC("soc", "SoC", 150, "", BLANK2, Dyes.dyeYellow),
    ASOC("asoc", "Advanced SoC", 120, "", BLANK2, Dyes.dyeGreen),
    PIC("pic", "Power IC", 100, "", BLANK2, Dyes.dyeBlue),
    HPIC("hpic", "High Power IC", 80, "", BLANK3, null), // Different, made in chemical reactor. Figure out something for
                                                        // this later?
    NCPU("ncpu", "NanoCPU", 60, "", BLANK2, null), // Same as above
    QBIT("qbit", "QBit", 50, "", BLANK2, null), // ^
    UHPIC("uhpic", "Ultra High Power IC", 60, "", BLANK3, null), // You get the gist
    SSOC("ssoc", "Simple SoC", 150, "", BLANK1, Dyes.dyeOrange),
    ULPIC("ulpic", "Ultra Low Power IC", 200, "", BLANK1, Dyes.dyeGreen), // Can use green for this as well as asoc, given
                                                                        // the latter uses a different base mask
    LPIC("lpic", "Low Power IC", 150, "", BLANK1, Dyes.dyeYellow), // Same as above, except for yellow
    NPIC("npic", "Nano Power IC", 70, "", BLANK3, Dyes.dyeRed), // Same
    PPIC("ppic", "PPIC", 50, "", BLANK3, null, ItemList.Circuit_Silicon_Wafer, ItemList.Circuit_Silicon_Wafer2), // CR recipe
    QPIC("qpic", "QPIC", 50, "", BLANK3, Dyes.dyeBlue); // Different base mask to PIC

    String name;
    String englishName;
    String spectrum;

    int maxDamage;

    MaskList precursor;
    Dyes lensColour;
    
    ItemList[] forbiddenWafers;

    MaskList(String name, String englishName, int maxDamage, String spectrum, MaskList precursor, Dyes lensColour, ItemList... forbiddenWafers) {
        this.name = name;
        this.englishName = englishName;
        this.spectrum = spectrum;
        this.maxDamage = maxDamage;
        this.precursor = precursor;
        this.lensColour = lensColour;
        this.forbiddenWafers = forbiddenWafers;
    }

    public String getName() {
        return this.name;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public String getSpectrum() {
        return this.spectrum;
    }

    public int getDamage() {
        return this.maxDamage;
    }

    public MaskList getPrecursor() {
        return this.precursor;
    }

    public Dyes getLensColour() {
        return this.lensColour;
    }
    
    public ItemList[] getForbiddenWafers() {
    	return this.forbiddenWafers;
    }

}
