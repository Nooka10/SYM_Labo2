package SymComManager.Objects;

/**
 * Modèle de données utilisé pour la transmission d'objet JSON
 */
public class Computer {
	private String name;
	private String vendor;
	
	public Computer(String name, String vendor) {
		this.name = name;
		this.vendor = vendor;
	}
	
	public String getVendor() {
		return vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

