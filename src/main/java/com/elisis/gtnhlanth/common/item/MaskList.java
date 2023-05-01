package com.elisis.gtnhlanth.common.item;

public enum MaskList {
	
	ERROR("error", "ERROR", 0),
	BLANK1("blank1", "Blank Tier 1", 0),
	BLANK2("blank2", "Blank Tier 2", 0),
	BLANK3("blank3", "Blank Tier 3", 0),
	ILC("ilc", "Integrated Logic Circuit", 100),
	RAM("ram", "Random Access Memory", 200),
	NAND("nand", "NAND", 200),
	NOR("nor", "NOR", 100),
	CPU("cpu", "Central Processing Unit", 80),
	SOC("soc", "SoC", 150),
	ASOC("asoc", "Advanced SoC", 0),
	PIC("pic", "Power IC", 0),
	HPIC("hpic", "High Power IC", 0),
	NCPU("ncpu", "NanoCPU", 0),
	QBIT("qbit", "QBit", 0),
	UHPIC("uhpic", "Ultra High Power IC", 0),
	SSOC("ssoc", "Simple SoC", 0),
	ULPIC("ulpic", "Ultra Low Power IC", 0),
	LPIC("lpic", "Low Power IC", 0),
	NPIC("npic", "Nano Power IC", 0),
	PPIC("ppic", "PPIC", 0),
	QPIC("qpic", "QPIC", 0)
	;
	
	String name;
	String englishName;
	int maxDamage;
	
	
	MaskList(String name, String englishName, int maxDamage) {
		this.name = name;
		this.englishName = englishName;
		this.maxDamage = maxDamage;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEnglishName() {
		return this.englishName;
	}
	
	public int getDamage() {
		return this.maxDamage;
	}
	
}
