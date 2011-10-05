package pp.battleship.bs;

/**
 * Diese Klasse repraesentiert ein Feld, welches aus einer x- und y-Koordinate
 * besteht.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Field {

	/**
	 * Diese Werte sind fuer die Koordinaten eines Feldes
	 */
	int x, y;

	/**
	 * Der Konstruktor erzeugt ein Field Objekt aus den uebergebenen Koordinaten
	 * 
	 * @param x
	 * @param y
	 */
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Diese Methode gibt die x-Koordinate des Feldes zurueck
	 * 
	 * @return Integer
	 */
	public int getX() {
		return x;
	}

	/**
	 * Diese Methode gibt die y-Koordinate des Feldes zurueck
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Diese Methode ueberprueft, ob - das uebergebene Object gleich dem
	 * aktuellen Object ist - das uebergebene Object gleich null ist - die
	 * Klasse des Objects gleich der Klasse des übergebenem Objects ist - der
	 * x-Wert gleich einem anderen x-Wert ist - der y-Wert gleich einem anderen
	 * y-Wert ist
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
