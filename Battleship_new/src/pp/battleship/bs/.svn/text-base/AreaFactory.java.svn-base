package pp.battleship.bs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * 
 * Diese Klasse erzeugt eine AreaFactory, welche wiederum eine Area erzeugen
 * kann. Alle Operationen die auf die Area zugreifen muessen, werden hier
 * deklariert.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class AreaFactory {

	/**
	 * Diese Variable speichert die Area.
	 */
	private Area area;

	/**
	 * Diese Liste beinhaltet freie Felder auf der Area
	 */
	private List<Field> freeFields;

	/**
	 * Die FieldFactory wird zum Erzeugen von Feldern benoetigt
	 */
	private FieldFactory fieldFactory;

	/**
	 * Dem Konstruktor werden Breite und Hoehe uebergeben. Mit diesen
	 * uebergebenen Werten wird eine Area erzeugt. Es wird eine FieldFactory
	 * angelegt, ueber welche alle freien Felder der Area zu der Liste
	 * freeFields hinzugefuegt werden. Diese Felder werden zuvor noch
	 * geshuffled, also gemixt.
	 * 
	 * @param width
	 * @param height
	 */
	public AreaFactory(int width, int height) {
		this.area = new Area(width, height);
		fieldFactory = new FieldFactory();
		fieldFactory.createShuffleFreeFields();
		this.freeFields = fieldFactory.getFreeFields();

	}

	/**
	 * Diese Methode erzeugt mit uebergebenen Schiffstypen ueber einen
	 * rekursiven Aufruf alle Schiffe auf der Area. Sollte das Setzen eines
	 * Schiffes nicht moeglich sein, wird eine Collision Exception geworfen und
	 * es wird erneut versucht das Schiff korrekt zu platzieren. Gibt die
	 * Methode true zurueck, war das Setzen der Schiffe erfolgreich.
	 * 
	 * @param shipType
	 * @return
	 */
	public boolean generateArea(Stack<Integer> shipType) {

		/*
		 * Wenn es keine Schiffe mehr zu setzen gibt, wird true zurueckgegeben.
		 */
		if (shipType.isEmpty()) {
			return true;
		}

		/*
		 * Es wird das naechste Schiff aus dem Stack entfernt und die
		 * Ausrichtungen werden aus der Enumeration Direction geholt und
		 * geschuffled.
		 */
		int shipTypeTmp = shipType.pop();
		List<Directions> directions = Arrays.asList(Directions.values());
		Collections.shuffle(directions);

		/*
		 * Die for-Schleife laeuft ueber allen freien Felder und ueber die
		 * Directions
		 */
		for (Field field : freeFields) {
			for (Directions direction : directions) {
				Ship ship = null;
				/*
				 * Es wird versucht ein Schiff an momentanen Feld mit momentaner
				 * Ausrichtung zu setzen
				 */
				try {
					ship = addShip(field, direction, shipTypeTmp);

					if (generateArea(shipType)) {
						return true;
					}

					freeFields = new ArrayList<Field>();
					try {
						freeFields = getfreeFields();
					} catch (IllegalFieldException e) {
						e.printStackTrace();
					}
					area.removeShip(ship);
				} catch (IllegalFieldException e1) {
				} catch (CollisionExecption e) {
				}
			}
		}

		/*
		 * Ist der Setzvorgang nicht erfolgreich wird das Schiff dem Stack
		 * wieder hinzugefuegt.
		 */
		shipType.push(shipTypeTmp);
		return false;
	}

	/**
	 * Diese Methode versucht ein Schiff an uebergebem Feld mit uebergebener
	 * Ausrichtung zu setzen. Falls dies erfolgreich war, wird das Schiff
	 * zurueckgegeben, ansonsten null.
	 * 
	 * @param src
	 * @param direction
	 * @param shType
	 * @return ship
	 * @throws IllegalFieldException
	 * @throws CollisionExecption
	 */
	public Ship addShip(Field src, Directions direction, int shType)
			throws IllegalFieldException, CollisionExecption {
		int xStart = src.getX();
		int yStart = src.getY();

		/*
		 * Die Liste fields enthaelt alle Felder des zu setzenden Schiffes
		 */
		List<Field> fields = new ArrayList<Field>();

		/*
		 * Die Liste fields2check enthaelt alle Felder des zu setzenden Schiffes
		 * und der Maske. Es wurde ein Set gewaehlt um doppelte Eintraege zu
		 * vermeiden.
		 */
		Set<Field> fields2check = new HashSet<Field>();
		Ship ship;
		switch (direction) {

		/*
		 * NORTH: Es werden alle Felder des zu setzenden Schiffes der Liste
		 * field hinzugefuegt. Die Methode checkPlace fuegt noch die Felder fuer
		 * die Maske hinzu. Ueber die Methode removeFields wird geprueft ob sich
		 * das Schiff setzen laesst. Falls ja, wird ein neues Schiff angelegt,
		 * welches der Area hinzugefuegt wird. Es wird anschlie§end das
		 * angelegte Schiff zurueckgegeben.
		 */
		case NORTH:
			for (int i = 0; i < shType; i++) {
				Field field = fieldFactory.createField(xStart, yStart - i);
				fields.add(field);

				checkPlace(fields2check, field);
			}
			removeFields(fields, fields2check);
			ship = new Ship(fields);
			area.addShip(ship);
			return ship;

			/*
			 * EAST: Es werden alle Felder des zu setzenden Schiffes der Liste
			 * field hinzugefuegt. Die Methode checkPlace fuegt noch die Felder
			 * fuer die Maske hinzu. Ueber die Methode removeFields wird
			 * geprueft ob sich das Schiff setzen laesst. Falls ja, wird ein
			 * neues Schiff angelegt, welches der Area hinzugefuegt wird. Es
			 * wird anschlie§end das angelegte Schiff zurueckgegeben.
			 */
		case EAST:
			for (int i = 0; i < shType; i++) {
				Field field = fieldFactory.createField(xStart + i, yStart);
				fields.add(field);

				checkPlace(fields2check, field);
			}
			removeFields(fields, fields2check);
			ship = new Ship(fields);
			area.addShip(ship);
			return ship;

			/*
			 * SOUTH: Es werden alle Felder des zu setzenden Schiffes der Liste
			 * field hinzugefuegt. Die Methode checkPlace fuegt noch die Felder
			 * fuer die Maske hinzu. Ueber die Methode removeFields wird
			 * geprueft ob sich das Schiff setzen laesst. Falls ja, wird ein
			 * neues Schiff angelegt, welches der Area hinzugefuegt wird. Es
			 * wird anschlie§end das angelegte Schiff zurueckgegeben.
			 */
		case SOUTH:
			for (int i = 0; i < shType; i++) {
				Field field = fieldFactory.createField(xStart, yStart + i);
				fields.add(field);

				checkPlace(fields2check, field);
			}
			removeFields(fields, fields2check);
			ship = new Ship(fields);
			area.addShip(ship);
			return ship;

			/*
			 * WEST: Es werden alle Felder des zu setzenden Schiffes der Liste
			 * field hinzugefuegt. Die Methode checkPlace fuegt noch die Felder
			 * fuer die Maske hinzu. Ueber die Methode removeFields wird
			 * geprueft ob sich das Schiff setzen laesst. Falls ja, wird ein
			 * neues Schiff angelegt, welches der Area hinzugefuegt wird. Es
			 * wird anschlie§end das angelegte Schiff zurueckgegeben.
			 */
		case WEST:
			for (int i = 0; i < shType; i++) {
				Field field = fieldFactory.createField(xStart - i, yStart);
				fields.add(field);

				checkPlace(fields2check, field);
			}
			removeFields(fields, fields2check);
			ship = new Ship(fields);
			area.addShip(ship);
			return ship;

		}
		return null;
	}

	/**
	 * Diese Methode ueberprueft ob die uebergeben Felder in den freien Feldern
	 * der Area enthalten sind. Falls ja, werden alle Schiffsfelder aus der
	 * Liste freeFields geloescht. Somit kann an dieser Stelle kein Schiff mehr
	 * gesetzt werden. Sollte dies nicht moeglich sein, wird eine Collision
	 * Exception geworfen.
	 * 
	 * @param fieldsDel
	 * @param fields2check
	 * @throws CollisionExecption
	 */
	private void removeFields(List<Field> fieldsDel, Set<Field> fields2check)
			throws CollisionExecption {

		if (freeFields.containsAll(fields2check))
			freeFields.removeAll(fieldsDel);
		else
			throw new CollisionExecption();

	}

	/**
	 * Diese Methode fuegt dem Set die Maske des zu setzenden Schiffes hinzu. Es
	 * wird in jede Richtung ueberprueft ob man sich noch innerhalb des
	 * Spielfeldes befindet.
	 * 
	 * @param fields2remove
	 * @param field
	 * @throws IllegalFieldException
	 */
	private void checkPlace(Set<Field> fields2remove, Field field)
			throws IllegalFieldException {
		fields2remove.add(field);
		if (field.getY() + 1 < area.getHeight())
			fields2remove.add(fieldFactory.createField(field.getX(), field
					.getY() + 1));
		if (field.getY() - 1 >= 0)
			fields2remove.add(fieldFactory.createField(field.getX(), field
					.getY() - 1));
		if (field.getX() + 1 < area.getWidth())
			fields2remove.add(fieldFactory.createField(field.getX() + 1, field
					.getY()));
		if (field.getX() - 1 >= 0)
			fields2remove.add(fieldFactory.createField(field.getX() - 1, field
					.getY()));
	}

	/**
	 * Diese Methode gibt die Area zurueck.
	 * 
	 * @return area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * Diese Methode gibt alle freien Felder der Area zurueck
	 * 
	 * @return freeFields
	 * @throws IllegalFieldException
	 */
	public List<Field> getfreeFields() throws IllegalFieldException {
		return freeFields;
	}

}
