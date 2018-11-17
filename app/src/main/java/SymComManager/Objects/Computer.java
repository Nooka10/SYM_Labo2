package SymComManager.Objects;

/**
 * Classe définissant un ordinateur, utilisée dans la partie Json et Compressed.
 */
public class Computer {
	private String name;
	private String manufacturer;
	
	/**
	 * Constructeur.
	 * @param name, le nom de l'ordinateur.
	 * @param manufacturer, le constructeur de l'ordinateur.
	 */
	public Computer(String name, String manufacturer) {
		this.name = name;
		this.manufacturer = manufacturer;
	}
	
	/**
	 * Getter du nom de l'ordinateur.
	 * @return name, un String.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter du nom de l'ordinateur.
	 * @param name, le nom à donner à l'ordinateur.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter du constructeur de l'ordinateur.
	 * @return manufacturer, un String.
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * Setter du constructeur de l'ordinateur.
	 * @param manufacturer, le vendeur à donner à l'ordinateur.
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}

