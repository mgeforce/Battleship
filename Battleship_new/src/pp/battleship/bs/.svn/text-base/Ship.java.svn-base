package pp.battleship.bs;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse repraesentiert ein Schiffsobjekt, welches aus einer Liste von
 * Feldern besteht.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Ship {

	/**
	 * Diese Liste enthaelt alle Felder, aus denen ein Schiff besteht.
	 */
	private List<Field> shipFields;

	/**
	 * Der Konstruktor erzeugt anhand der uebergebenen Feldliste ein neues
	 * Schiffsobjekt
	 * 
	 * @param fieldList
	 */
	public Ship(List<Field> fieldList) {
		shipFields = new ArrayList<Field>();
		for (Field field : fieldList) {
			shipFields.add(field);
		}
	}

	/**
	 * Diese Methode gibt die x-Startkoordinate eines Schiffes zurueck
	 * 
	 * @return Integer
	 */
	public int getXStart() {
		if (shipFields.isEmpty())
			return 0;
		else
			return shipFields.get(0).getX();
	}

	/**
	 * Diese Methode gibt die y-Startkoordinate eines Schiffes zurueck
	 * 
	 * @return Integer
	 */
	public int getYStart() {
		if (shipFields.isEmpty())
			return 0;
		else
			return shipFields.get(0).getY();
	}

	/**
	 * Diese Methode gibt die x-Endkoordinate eines Schiffes zurueck
	 * 
	 * @return Integer
	 */
	public int getXEnd() {
		if (shipFields.isEmpty())
			return 0;
		else
			return shipFields.get(shipFields.size() - 1).getX();
	}

	/**
	 * Diese Methode gibt die y-Endkoordinate eines Schiffes zurueck
	 * 
	 * @return Integer
	 */
	public int getYEnd() {
		if (shipFields.isEmpty())
			return 0;
		else
			return shipFields.get(shipFields.size() - 1).getY();
	}

	/**
	 * Diese Methode gibt eine Liste aus Feldern, aus denen ein Schiff besteht,
	 * zurueck
	 * 
	 * @return shipFields
	 */
	public List<Field> getFields() {
		return shipFields;
	}

	/**
	 * Diese Methode loescht das uebergebene Feld aus der Feldliste eines
	 * Schiffes
	 * 
	 * @param field
	 */
	public void removeShipField(Field field) {
		shipFields.remove(field);
	}
}
