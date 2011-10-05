package pp.battleship.bs;

import java.util.ArrayList;
import java.util.List;

import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse erzeugt eine Area, welche durch eine Laenge und Breite definiert
 * ist. Au§erdem enthaelt eine Area eine Liste an Schiffen, die sich auf der
 * Area befinden.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Area {

	/**
	 * Dieser Wert speichert die Breite der Area.
	 */
	private int width;

	/**
	 * Dieser Wert speicher die Hoehe der Area.
	 */
	private int height;

	/**
	 * Diese Liste enthaelt alle Schiffe, welche sich auf der Area befinden.
	 */
	private List<Ship> shList;

	/**
	 * Dem Konstruktor werden Breite und Hoehe uebergeben. Anhand dieser Werte
	 * wird eine Area erzeugt.
	 * 
	 * @param width
	 * @param length
	 */
	public Area(int width, int length) {
		this.width = width;
		this.height = length;
		this.shList = new ArrayList<Ship>();
	}

	/**
	 * Dieser Konstruktor enthaelt neben Breite und Hoehe noch eine Liste an
	 * Schiffen.
	 * 
	 * @param width
	 * @param length
	 * @param shList
	 */
	public Area(int width, int length, List<Ship> shList) {
		this(width, length);
		this.shList = shList;
	}

	/**
	 * Diese Methode gibt die Breite der Area zurueck.
	 * 
	 * @return Integer
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Diese Methode gibt die Hoehe der Area zurueck.
	 * 
	 * @return Integer
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Diese Methode gibt die Liste mit Schiffen, welche sich auf der Area
	 * befinden, zurueck.
	 * 
	 * @return shList
	 */
	public List<Ship> getShList() {
		return shList;
	}

	/**
	 * Dieser Methode wird ein Schiff uebergeben. Dieses Schiff wird der shList,
	 * somit der Area hinzugefuegt.
	 * 
	 * @param ship
	 */
	public void addShip(Ship ship) {
		shList.add(ship);
	}

	/**
	 * Diese Methode gibt, das an der uebergebenen Stelle index befindliche
	 * Schiff zurueck
	 * 
	 * @param index
	 * @return ship
	 */
	public Ship getShip(int index) {
		if (!shList.isEmpty())
			return shList.get(index);
		else
			return null;

	}

	/**
	 * Diese Methode ueberprueft, ob sich an den uebergebenen Koordinaten x und
	 * y ein Schiff befindet. Falls ja, wird das Schiff zurueckgegeben.
	 * 
	 * @param y
	 * @param x
	 * @return ship
	 */
	public Ship getShip(int y, int x) {
		for (int i = 0; i < OptionsController.getInstance().getAreaFactory()
				.getArea().getShList().size(); i++) {
			Ship s = getShip(i);
			int xStart = s.getXStart();
			int yStart = s.getYStart();
			int xEnd = s.getXEnd();
			int yEnd = s.getYEnd();
			if (xStart <= x && yStart <= y) {
				if (xEnd >= x && yEnd >= y) {
					return s;
				}
			}
			if (xStart >= x && yStart >= y) {
				if (xEnd <= x && yEnd <= y) {
					return s;
				}
			}
		}
		return null;
	}

	/**
	 * Diese Methode prueft, ob sich an uebergebenem Feld ein Schiff befindet.
	 * Falls ja, wird dieses Schiff zurueckgegeben.
	 * 
	 * @param field
	 * @return ship
	 */
	public Ship getShip(Field field) {

		for (Ship ship : shList) {
			if (ship.getFields().contains(field))
				return ship;
		}

		return null;
	}

	/**
	 * Dieser Methode wird ein Schiff uebergeben. Das Schiff wird aus der
	 * shList, somit aus der Area entfernt.
	 * 
	 * @param ship
	 */
	public void removeShip(Ship ship) {
		if (!shList.isEmpty())
			shList.remove(ship);
	}

}
