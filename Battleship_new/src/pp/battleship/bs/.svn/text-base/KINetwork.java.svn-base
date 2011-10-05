package pp.battleship.bs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse repraesentiert eine KI im Netzwerk. Die Klasse enthaelt die
 * Methoden, die fuer die Ausfuehrung eines Spielzuges im Netzwerk zustaendig
 * sind.<br>
 * <br>
 * Ausserdem erbt die Klasse von KI und implementiert Runnable.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class KINetwork extends KI implements Runnable {

	/**
	 * Diese Variable speicher das Spielfeld des Gegners
	 */
	private CreateTableView tableShoot;

	/**
	 * Der Konstruktor erstellt ein neues KINetwork Objekt. Es wird der
	 * Konstruktor der Oberklasse KI aufgerufen, durch welchen alle noetigen
	 * Variablen initialisiert werden. Es wird ein neuer Thread erzeugt, welcher
	 * die Verzoegerung der Schuesse der KI im Netzwerk ermoeglicht.
	 */
	public KINetwork() {
		super();

		Thread KIThread = new Thread(this);
		KIThread.setPriority(Thread.NORM_PRIORITY);
		KIThread.start();
	}

	/**
	 * Diese Methode schickt den Thread fuer 2000ms in den Schlaf. Falls ein
	 * Schiff getroffen, aber noch nicht versenkt wurde, wird ueberprueft, ob in
	 * eine Richtung nach einem Treffer schon einmal geschossen wurde und
	 * loescht die Richtung aus usedShootDirection, damit diese nicht noch
	 * einmal verwendet wird. Anhand der Variable reply, die die Werte von
	 * readReply aus der Klasse Network annimmt wird entschieden, ob ein
	 * Treffer, ein Miss oder ein Versenkt erfolgt ist. Reply, was zur
	 * Uebertragung von Daten im Netzwerk erforderlich ist, wird zurueckgegeben.
	 */
	@Override
	public int makeTurn() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

			// TODO: abprüfen, ob shootFields leer
			Field shoot = shootFields.get(0);

			shootFields.remove(0);
			usedShootDirections.clear();

			Iterator<Directions> it = Arrays.asList(Directions.values())
					.iterator();
			while (it.hasNext()) {
				usedShootDirections.add(it.next());
			}

			OptionsController.getInstance().getNetwork().sendQuery(
					shoot.getY() + 1, shoot.getX() + 1);

			int reply = OptionsController.getInstance().getNetwork()
					.readReply();

			switch (reply) {
			case 0:
				tableShoot.setValueAt("m", shoot.getY(), shoot.getX());
				shootFieldAfterHit = null;
				currentShootingDirection = null;
				getFailShoots().add(shoot);
				break;
			case 1:
				tableShoot.setValueAt("t", shoot.getY(), shoot.getX());
				shootFields.remove(shoot);
				hitShipNotSunk = true;
				setShootFieldTemp(shoot);
				shootFieldAfterHit = null;
				getSuccessfulShoots().add(shoot);
				break;
			case 2:
				tableShoot.setValueAt("t", shoot.getY(), shoot.getX());
				hitShipNotSunk = false;
				shootFieldAfterHit = null;
				drawSunkShips(table);
				getSuccessfulShoots().add(shoot);
				getSunkShoots().addAll(getSuccessfulShoots());
				setSuccessfulShoots(new ArrayList<Field>());
				OptionsController.getInstance().setShipCount(
						OptionsController.getInstance().getShipCount() - 1);
				break;
			}

			return reply;

		}

	}

	/**
	 * Diese Methode bekommt das Field des letzten Treffers uebergeben und sorgt
	 * dafuer, dass in die 4 verschiedenen Richtungen ausgehen vom letzten
	 * Treffer geschossen wird. Hierzu wird ueberprueft, ob in eine Richtung
	 * geschossen werden darf oder ob das Spielfeld endet, also der letzte
	 * Treffer am Rand des Spielfelds stattfand. Falls dieser Treffer in
	 * shootFields enthalten ist wird anhand der Variablen reply ausgewaehlt, ob
	 * es sich um einen Treffer, einen Miss oder um ein Versenkt handelt. Reply,
	 * was zur Uebertragung von Daten im Netzwerk erforderlich ist, wird
	 * zurueckgegeben.
	 */
	public int makeTurnAfterSuccessfulShoot(Directions direction, Field shoot) {
		Field shootField = null;
		int reply = 0;

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

					OptionsController.getInstance().getNetwork().sendQuery(
							shootField.getY() + 1, shootField.getX() + 1);

					reply = OptionsController.getInstance().getNetwork()
							.readReply();

					switch (reply) {
					case 0:
						tableShoot.setValueAt("m", shootField.getY(),
								shootField.getX());
						shootFieldAfterHit = null;
						currentShootingDirection = null;
						getFailShoots().add(shootField);
						break;
					case 1:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;
						getSuccessfulShoots().add(shootField);
						break;
					case 2:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shipHasSunk();
						drawSunkShips(table);
						getSuccessfulShoots().add(shoot);
						getSunkShoots().addAll(getSuccessfulShoots());
						setSuccessfulShoots(new ArrayList<Field>());
						OptionsController.getInstance()
								.setShipCount(
										OptionsController.getInstance()
												.getShipCount() - 1);
						break;
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
					OptionsController.getInstance().getNetwork().sendQuery(
							shootField.getY() + 1, shootField.getX() + 1);

					reply = OptionsController.getInstance().getNetwork()
							.readReply();

					switch (reply) {
					case 0:
						tableShoot.setValueAt("m", shootField.getY(),
								shootField.getX());
						shootFieldAfterHit = null;
						currentShootingDirection = null;
						getFailShoots().add(shootField);
						break;
					case 1:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;
						getSuccessfulShoots().add(shootField);
						break;
					case 2:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shipHasSunk();
						drawSunkShips(table);
						getSuccessfulShoots().add(shoot);
						getSunkShoots().addAll(getSuccessfulShoots());
						setSuccessfulShoots(new ArrayList<Field>());
						OptionsController.getInstance()
								.setShipCount(
										OptionsController.getInstance()
												.getShipCount() - 1);
						break;
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
					OptionsController.getInstance().getNetwork().sendQuery(
							shootField.getY() + 1, shootField.getX() + 1);

					reply = OptionsController.getInstance().getNetwork()
							.readReply();

					switch (reply) {
					case 0:
						tableShoot.setValueAt("m", shootField.getY(),
								shootField.getX());
						shootFieldAfterHit = null;
						currentShootingDirection = null;
						getFailShoots().add(shootField);
						break;
					case 1:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;
						getSuccessfulShoots().add(shootField);
						break;
					case 2:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shipHasSunk();
						drawSunkShips(table);
						getSuccessfulShoots().add(shoot);
						getSunkShoots().addAll(getSuccessfulShoots());
						setSuccessfulShoots(new ArrayList<Field>());
						OptionsController.getInstance()
								.setShipCount(
										OptionsController.getInstance()
												.getShipCount() - 1);
						break;
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
					OptionsController.getInstance().getNetwork().sendQuery(
							shootField.getY() + 1, shootField.getX() + 1);

					reply = OptionsController.getInstance().getNetwork()
							.readReply();

					switch (reply) {
					case 0:
						tableShoot.setValueAt("m", shootField.getY(),
								shootField.getX());
						shootFieldAfterHit = null;
						currentShootingDirection = null;
						getFailShoots().add(shootField);
						break;
					case 1:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shootFieldAfterHit = shootField;
						hitShipNotSunk = true;
						getSuccessfulShoots().add(shootField);
						break;
					case 2:
						tableShoot.setValueAt("t", shootField.getY(),
								shootField.getX());
						shootFields.remove(shootField);
						shipHasSunk();
						drawSunkShips(table);
						getSuccessfulShoots().add(shoot);
						getSunkShoots().addAll(getSuccessfulShoots());
						setSuccessfulShoots(new ArrayList<Field>());
						OptionsController.getInstance()
								.setShipCount(
										OptionsController.getInstance()
												.getShipCount() - 1);
						break;
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

		return reply;
	}

	/**
	 * Diese Methode markiert in dem Spielfeld die verunkenen Schiffe und setzt
	 * shootFieldAfterHit auf null, sowie hitShipNotSunk auf false
	 */
	protected void shipHasSunk() {
		drawSunkShips(tableShoot);
		shootFieldAfterHit = null;
		hitShipNotSunk = false;
	}

	/**
	 * Diese Methode setzt das Spielfeld, auf welches geschossen wird
	 * 
	 * @param tableShoot
	 */
	public void setTableShoot(CreateTableView tableShoot) {
		this.tableShoot = tableShoot;
	}

	@Override
	public void run() {

	}
}
