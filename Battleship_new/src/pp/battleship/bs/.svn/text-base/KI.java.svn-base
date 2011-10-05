package pp.battleship.bs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse repraesentiert eine KI. Die Klasse enthaelt die Methoden, die
 * fuer die Ausfuehrung eines Spielzuges zustaendig sind.<br>
 * <br>
 * Ausserdem erbt die Klasse von der abstrakten Klasse AbstractPlayer.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class KI extends AbstractPlayer {

	/**
	 * Diese Variable ist zum Speichern des Spielfeldes
	 */
	protected CreateTableView table;

	/**
	 * Diese Liste repraesentiert freie Felder, auf die die KI noch Schiffe
	 * setzen kann
	 */
	protected List<Field> freeFields;

	/**
	 * Die fieldFactory benoetigt man zum Erzeugen von neuen Feldern
	 */
	protected FieldFactory fieldFactory;

	/**
	 * Diese Liste repraesentiert freie Felder, auf die die KI noch schiessen
	 * kann
	 */
	protected List<Field> shootFields;

	/**
	 * Diese Variable ist zur Speicherung der bereits benutzten Schussrichtungen
	 */
	protected List<Directions> usedShootDirections;

	/**
	 * Diese Variable speichert das Spielfeld des Spielers
	 */
	protected CreateTableView tablePlayer;

	/**
	 * Diese Variable ist zur temporaeren Speicherung des Schussfeldes
	 */
	protected Field shootFieldTemp;

	/**
	 * Diese Variable speichert das Schussfeld, nachdem die KI schon getroffen
	 * hat
	 */
	protected Field shootFieldAfterHit;

	/**
	 * Dieser Wert dient zur Ueberpruefung, ob ein Schiff getroffen wurde, aber
	 * noch nicht gesunken ist
	 */
	protected boolean hitShipNotSunk;

	/**
	 * Diese Variable ist zur temporaeren Speicherung der Schussrichtung
	 */
	protected Directions currentShootingDirection;

	/**
	 * Der Konstruktor erzeugt ein neues KI Objekt. Alle benoetigten Variablen
	 * werden initialisiert.
	 */
	public KI() {
		table = new CreateTableView();
		areaFactory = new AreaFactory(OptionsController.getInstance()
				.getWidth(), OptionsController.getInstance().getWidth());
		try {
			setFreeFields(areaFactory.getfreeFields());
		} catch (IllegalFieldException e) {
			e.printStackTrace();
		}
		initializeVariables();

	}

	/**
	 * Diese Methode dient zur initialisieren alle benoetigen Variablen beim
	 * Erzeugen eines neuen KI Objekts.
	 */
	protected void initializeVariables() {
		setSuccessfulShoots(new ArrayList<Field>());
		setFailShoots(new ArrayList<Field>());
		setSunkShoots(new ArrayList<Field>());
		fieldFactory = new FieldFactory();
		fieldFactory.createShuffleFreeFields();
		shootFields = new ArrayList<Field>();
		shootFields = fieldFactory.getFreeFields();
		usedShootDirections = new ArrayList<Directions>();
		hitShipNotSunk = false;
		currentShootingDirection = null;
		shootFieldAfterHit = null;
	}

	/**
	 * Diese Methode setzt die Schiffe der KI, welche vom Spieler zuvor
	 * ausgewaehlt wurden. Die Schiffe werden aus dem OptionsController geholt.
	 */
	public void setShips() {

		Stack<Integer> shType = OptionsController.getInstance().getShipTypeKI();

		if (areaFactory.generateArea(shType)) {

			for (Ship ship : areaFactory.getArea().getShList()) {
				drawShip(ship.getXStart(), ship.getYStart(), ship.getXEnd(),
						ship.getYEnd());
			}
		}

	}

	/**
	 * Diese Methode stellt ein Schiff anhand der uebergebenen Koordinaten auf
	 * dem Spielfeld dar.
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 */
	public void drawShip(int xStart, int yStart, int xEnd, int yEnd) {

		/*
		 * NORTH
		 */
		if (xStart == xEnd && yStart > yEnd) {
			for (int i = yStart; i >= yEnd; i--) {
				try {
					table.setValueAt("s", i, xEnd);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		/*
		 * EAST
		 */
		else if (yStart == yEnd && xStart > xEnd) {
			for (int i = xStart; i >= xEnd; i--) {
				try {
					table.setValueAt("s", yEnd, i);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		/*
		 * SOUTH
		 */
		else if (xStart == xEnd && yStart < yEnd) {
			for (int i = yStart; i <= yEnd; i++) {
				try {
					table.setValueAt("s", i, xEnd);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		/*
		 * WEST
		 */
		else if (yStart == yEnd && xStart < xEnd) {
			for (int i = xStart; i <= xEnd; i++) {
				try {
					table.setValueAt("s", yEnd, i);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

	}

	/**
	 * Diese Methode ueberprueft, ob ein Schiff getroffen, aber noch nicht
	 * versenkt wurde, ob in eine Richtung nach einem Treffer schon einmal
	 * geschossen wurde und loescht die Richtung aus usedShootDirection, damit
	 * diese nicht noch einmal verwendet wird. Falls ein Schiff getroffen wird
	 * wird an die Stelle der Koordinaten im Feld ein "t" geschrieben und
	 * ueberprueft ob das Schiff gesunken ist.Ist dies der Fall, wird es durch
	 * die Methode drawSunkShips im Spielfeld markiert und alle Treffer werden
	 * der Liste SuccessfullShoots hinzugefuegt. Wurde ein Schiff getroffen aber
	 * noch nicht versenkt wird die Variable hitShipNotSunk auf true gesetzt.
	 * Wurde kein Treffer erzielt wird ein "m" in das Spielfeld an die stelle
	 * der Koordinaten des Schusses geschrieben.
	 */
	@Override
	public int makeTurn() {

		if (hitShipNotSunk) {
			if (currentShootingDirection == null) {
				currentShootingDirection = usedShootDirections.get(0);
				usedShootDirections.remove(0);
			}

			Field nextShot = shootFieldAfterHit;
			if (shootFieldAfterHit == null) {
				nextShot = getShootFieldTemp();
			}

			return makeTurnAfterSuccessfulShoot(currentShootingDirection,
					nextShot);
		} else {

			Ship ship;
			Field shoot = shootFields.get(0);

			shootFields.remove(0);
			usedShootDirections.clear();

			Iterator<Directions> it = Arrays.asList(Directions.values())
					.iterator();
			while (it.hasNext()) {
				usedShootDirections.add(it.next());
			}

			if ((ship = shoot(shoot, OptionsController.getInstance()
					.getAreaFactory().getArea())) != null) {
				tablePlayer.setValueAt("t", shoot.getY(), shoot.getX());
				getSuccessfulShoots().add(shoot);
				if (sunk(ship, OptionsController.getInstance().getAreaFactory()
						.getArea())) {
					drawSunkShips(tablePlayer);
					getSunkShoots().addAll(getSuccessfulShoots());
					setSuccessfulShoots(new ArrayList<Field>());
					hitShipNotSunk = false;
					shootFieldAfterHit = null;

					return 2;
				}
				hitShipNotSunk = true;
				setShootFieldTemp(shoot);
				shootFieldAfterHit = null;

				return 1;
			}

			else {
				getFailShoots().add(shoot);
				shootFieldAfterHit = null;
				tablePlayer.setValueAt("m", shoot.getY(), shoot.getX());
				currentShootingDirection = null;
			}
		}

		return 0;
	}

	/**
	 * Diese Methode bekommt das Feld des letzten Treffers uebergeben und sorgt
	 * dafuer, das in die 4 verschiedenen Richtungen ausgehen vom letzten
	 * Treffer geschossen wird.Hierzu wird ueberprueft, ob in eine Richtung
	 * geschossen werden darf oder ob das Spielfeld endet, also der letzte
	 * Treffer am Rand des Spielfelds stattfand. Falls dieser Treffer in
	 * shootFields enthalten ist wird er daraus entfernt, zu SuccessfulShoots
	 * hinzugefuegt und mit einem "t" im Spielfeld markiert. Ausserdem wird
	 * ueberprueft, ob das Schiff gesunken ist.
	 * 
	 * @param direction
	 * @param shoot
	 * @return Integer
	 */
	public int makeTurnAfterSuccessfulShoot(Directions direction, Field shoot) {
		Ship ship;
		Field shootField = null;

		switch (direction) {
		case NORTH:
			if (shoot.getY() - 1 >= 0) {
				try {
					shootField = fieldFactory.createField(shoot.getX(), shoot
							.getY() - 1);
				} catch (IllegalFieldException e) {
					e.printStackTrace();
				}
				if (shootFields.contains(shootField)) {
					if ((ship = shoot(shootField, OptionsController
							.getInstance().getAreaFactory().getArea())) != null) {
						shootFields.remove(shootField);
						getSuccessfulShoots().add(shootField);
						tablePlayer.setValueAt("t", shootField.getY(),
								shootField.getX());
						if (sunk(ship, OptionsController.getInstance()
								.getAreaFactory().getArea())) {
							shipHasSunk();
							getSunkShoots().addAll(getSuccessfulShoots());
							setSuccessfulShoots(new ArrayList<Field>());
							return 2;
						}

						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;

						return 1;
					} else {
						getFailShoots().add(shootField);
						shootFieldAfterHit = null;
						tablePlayer.setValueAt("m", shootField.getY(),
								shootField.getX());
						currentShootingDirection = null;
					}
				} else {
					setFieldsNull();
					return makeTurn();
				}

			} else {
				setFieldsNull();
				return makeTurn();
			}
			break;
		case EAST:
			if (shoot.getX() < OptionsController.getInstance().getWidth()) {
				try {
					shootField = fieldFactory.createField(shoot.getX() + 1,
							shoot.getY());
				} catch (IllegalFieldException e) {
					e.printStackTrace();
				}
				if (shootFields.contains(shootField)) {
					if ((ship = shoot(shootField, OptionsController
							.getInstance().getAreaFactory().getArea())) != null) {
						shootFields.remove(shootField);
						getSuccessfulShoots().add(shootField);
						tablePlayer.setValueAt("t", shootField.getY(),
								shootField.getX());
						if (sunk(ship, OptionsController.getInstance()
								.getAreaFactory().getArea())) {
							shipHasSunk();
							getSunkShoots().addAll(getSuccessfulShoots());
							setSuccessfulShoots(new ArrayList<Field>());
							return 2;
						}

						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;

						return 1;
					} else {
						getFailShoots().add(shootField);
						tablePlayer.setValueAt("m", shootField.getY(),
								shootField.getX());
						currentShootingDirection = null;
						shootFieldAfterHit = null;
					}
				} else {
					setFieldsNull();
					return makeTurn();
				}
			} else {
				setFieldsNull();
				return makeTurn();
			}
			break;
		case SOUTH:
			if (shoot.getY() < OptionsController.getInstance().getHeight()) {
				try {
					shootField = fieldFactory.createField(shoot.getX(), shoot
							.getY() + 1);
				} catch (IllegalFieldException e) {
					e.printStackTrace();
				}
				if (shootFields.contains(shootField)) {
					if ((ship = shoot(shootField, OptionsController
							.getInstance().getAreaFactory().getArea())) != null) {
						shootFields.remove(shootField);
						getSuccessfulShoots().add(shootField);
						tablePlayer.setValueAt("t", shootField.getY(),
								shootField.getX());
						if (sunk(ship, OptionsController.getInstance()
								.getAreaFactory().getArea())) {
							shipHasSunk();
							getSunkShoots().addAll(getSuccessfulShoots());
							setSuccessfulShoots(new ArrayList<Field>());
							return 2;
						}

						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;

						return 1;
					} else {
						getFailShoots().add(shootField);
						shootFieldAfterHit = null;
						tablePlayer.setValueAt("m", shootField.getY(),
								shootField.getX());
						currentShootingDirection = null;
					}
				} else {
					setFieldsNull();
					return makeTurn();
				}
			} else {
				setFieldsNull();
				return makeTurn();
			}
			break;
		case WEST:
			if (shoot.getX() > 0) {
				try {
					shootField = fieldFactory.createField(shoot.getX() - 1,
							shoot.getY());
				} catch (IllegalFieldException e) {
					e.printStackTrace();
				}
				if (shootFields.contains(shootField)) {
					if ((ship = shoot(shootField, OptionsController
							.getInstance().getAreaFactory().getArea())) != null) {
						shootFields.remove(shootField);
						getSuccessfulShoots().add(shootField);
						tablePlayer.setValueAt("t", shootField.getY(),
								shootField.getX());
						if (sunk(ship, OptionsController.getInstance()
								.getAreaFactory().getArea())) {
							shipHasSunk();
							getSunkShoots().addAll(getSuccessfulShoots());
							setSuccessfulShoots(new ArrayList<Field>());
							return 2;
						}

						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;

						return 1;
					} else {
						getFailShoots().add(shootField);
						shootFieldAfterHit = null;
						tablePlayer.setValueAt("m", shoot.getY(), shoot.getX());
						currentShootingDirection = null;
					}
				} else {
					setFieldsNull();
					return makeTurn();
				}
			} else {
				setFieldsNull();
				return makeTurn();
			}
			break;
		}

		return 0;
	}

	/**
	 * Diese Methode setzt shootFieldAfterHit und currentShootingDirection auf
	 * null
	 */
	protected void setFieldsNull() {
		shootFieldAfterHit = null;
		currentShootingDirection = null;
	}

	/**
	 * Diese Methode markiert in dem Spielfeld des Spielers die versunkenen
	 * Schiffe und setzt shootFieldAfterHit auf null, sowie hitShipNotSunk auf
	 * false
	 */
	protected void shipHasSunk() {
		drawSunkShips(tablePlayer);
		shootFieldAfterHit = null;
		hitShipNotSunk = false;
	}

	/**
	 * Diese Methode setzt das Spielfeld fuer den Spieler
	 * 
	 * @param tablePlayer2
	 */
	public void setTable(CreateTableView tablePlayer2) {
		this.tablePlayer = tablePlayer2;

	}

	/**
	 * Diese Methode setzt shootFieldTemp
	 * 
	 * @param shootFieldTemp
	 */
	public void setShootFieldTemp(Field shootFieldTemp) {
		this.shootFieldTemp = shootFieldTemp;
	}

	/**
	 * Diese Methode gibt shootFieldTemp zurueck
	 * 
	 * @return shootFieldTemp
	 */
	public Field getShootFieldTemp() {
		return shootFieldTemp;
	}

	/**
	 * Diese Methode gibt den table der Ki zurueck
	 * 
	 * @return table
	 */
	public CreateTableView getTableKI() {
		return table;
	}

	/**
	 * Diese Methode gibt die area der Ki zurueck
	 * 
	 * @return areaFactory
	 */
	public AreaFactory getAreaKI() {
		return areaFactory;
	}

	/**
	 * Diese Methode setzt die Liste FreeFields
	 * 
	 * @param freeFields
	 */
	public void setFreeFields(List<Field> freeFields) {
		this.freeFields = freeFields;
	}

	/**
	 * Diese Methode gibt freeFields zurueck
	 * 
	 * @return freeFields
	 */
	public List<Field> getFreeFields() {
		return freeFields;
	}

}
