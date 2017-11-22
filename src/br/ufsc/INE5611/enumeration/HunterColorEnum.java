package br.ufsc.INE5611.enumeration;

public enum HunterColorEnum {

	YELLOW("Amarelo"), GREEN("Verde"), BLUE("Azul");
	
	private String description;
	
	HunterColorEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
