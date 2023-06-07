package com.elisis.gtnhlanth.common.item;

import gregtech.api.enums.Dyes;

public enum MaskList {

    // There are absolutely better ways of doing this than a GT Materials-esque Enum, some method of automatically
    // scraping the wafer types would be preferable in particular
    // Use Dyes._NULL to indicate a wafer's lack of a dedicated lens instead of null, if the wafer's mask is to be
    // generated
    ERROR("error", "ERROR", 0, "", null, null),
    BLANK1("blank1", "T1 Blank", 0, "VISIBLE", null, null),
    BLANK2("blank2", "T2 Blank", 0, "UV", null, null),
    BLANK3("blank3", "T3 Blank", 0, "X-RAY", null, null),
    ILC("ilc", "Integrated Logic Circuit", 100, "", BLANK1, Dyes.dyeRed),
    RAM("ram", "Random Access Memory", 200, "", BLANK1, Dyes.dyeCyan),
    NAND("nand", "NAND", 200, "", BLANK2, Dyes._NULL), // NAND uses only Ender Pearl lens, don't ask me why
    NOR("nor", "NOR", 100, "", BLANK2, Dyes._NULL), // Same as above, but with ender eye
    CPU("cpu", "Central Processing Unit", 80, "", BLANK2, Dyes.dyeWhite),
    SOC("soc", "SoC", 150, "", BLANK2, Dyes.dyeYellow),
    ASOC("asoc", "Advanced SoC", 0, "", BLANK2, Dyes.dyeGreen),
    PIC("pic", "Power IC", 0, "", BLANK2, Dyes.dyeBlue),
    HPIC("hpic", "High Power IC", 0, "", BLANK3, null), // Different, made in chemical reactor. Figure out something for
                                                        // this later?
    NCPU("ncpu", "NanoCPU", 0, "", BLANK2, null), // Same as above
    QBIT("qbit", "QBit", 0, "", BLANK2, null), // ^
    UHPIC("uhpic", "Ultra High Power IC", 0, "", BLANK3, null), // You get the gist
    SSOC("ssoc", "Simple SoC", 0, "", BLANK1, Dyes.dyeOrange),
    ULPIC("ulpic", "Ultra Low Power IC", 0, "", BLANK1, Dyes.dyeGreen), // Can use green for this as well as asoc, given
                                                                        // the latter uses a different base mask
    LPIC("lpic", "Low Power IC", 0, "", BLANK1, Dyes.dyeYellow), // Same as above, except for yellow
    NPIC("npic", "Nano Power IC", 0, "", BLANK3, Dyes.dyeRed), // Same
    PPIC("ppic", "PPIC", 0, "", BLANK3, null), // CR recipe
    QPIC("qpic", "QPIC", 0, "", BLANK3, Dyes.dyeBlue); // Different base mask to PIC

    String name;
    String englishName;
    String spectrum;

    int maxDamage;

    MaskList precursor;
    Dyes lensColour;

    MaskList(String name, String englishName, int maxDamage, String spectrum, MaskList precursor, Dyes lensColour) {
        this.name = name;
        this.englishName = englishName;
        this.spectrum = spectrum;
        this.maxDamage = maxDamage;
        this.precursor = precursor;
        this.lensColour = lensColour;
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

}
