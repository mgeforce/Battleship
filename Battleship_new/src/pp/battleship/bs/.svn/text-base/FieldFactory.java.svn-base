package pp.battleship.bs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pp.battleship.gui.OptionsController;

/**
 * Diese Klasse wird zum Erzeugen von Feldern benoetigt. Au§erdem werden in
 * dieser Klasse die freien Felder einer Area erzeugt.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class FieldFactory {

	/**
	 * Diese Liste enthaelt die freien Felder einer Area
	 */
	private List<Field> freeFields;

	/**
	 * Diese Methode erzeugt anhand der uebergebenen Koordinaten ein neues Feld.
	 * Sollte sich eine Koordinate ausserhalb des Spielfeldes befinden, wird
	 * eine IllegalFieldException geworfen.
	 * 
	 * @param x
	 * @param y
	 * @return field
	 * @throws IllegalFieldException
	 */
	public Field createField(int x, int y) throws IllegalFieldException {

		if (x >= OptionsController.getInstance().getWidth())
			throw new IllegalFieldException(String.format(
					"Argument x (%d) is greater than width (%d)", x,
					OptionsController.getInstance().getWidth()));
		if (x < 0)
			throw new IllegalFieldException(String.format(
					"Argument x (%d) is less than 0", x));
		if (y >= OptionsController.getInstance().getHeight()) {
			throw new IllegalFieldException(String.format(
					"Argument y (%d) is greater than length (%d)", y,
					OptionsController.getInstance().getHeight()));
		}
		if (y < 0)
			throw new IllegalFieldException(String.format(
					"Argument y (%d) is less than 0", y));

		return new Field(x, y);
	}

	/**
	 * Diese Methode erzeugt eine Liste, bestehend aus allen Feldern einer Area.
	 * Anschlie§end wird die Liste geshuffled.
	 */
	void createShuffleFreeFields() {
		this.freeFields = new ArrayList<Field>();
		/*
		 * Die for-Schleife laeuft ueber das komplette Spielfeld und erzeugt
		 * fuer jede Zelle ein Feld, welches in einer Liste gespeicher wird.
		 */
		for (int i = 0; i < OptionsController.getInstance().getWidth(); i++) {
			for (int j = 0; j < OptionsController.getInstance().getHeight(); j++) {
				try {
					this.freeFields.add(createField(i, j));
				} catch (IllegalFieldException e) {
					e.printStackTrace();
				}
			}
		}
		Collections.shuffle(this.freeFields);
	}

	/**
	 * Diese Methode gibt eine Liste, bestehend aus freien Feldern, zurueck.
	 * 
	 * @return freeFields
	 */
	public List<Field> getFreeFields() {
		return freeFields;
	}

}
