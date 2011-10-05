package pp.battleship.bs;

import java.util.ArrayList;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * Diese Klasse repraesentiert einen Spieler. Die Klasse enthaelt die Methoden,
 * die fuer die Ausfuehrung eines Spielzuges zustaendig sind.<br>
 * <br>
 * Ausserdem erbt die Klasse von der abstrakten Klasse AbstractPlayer.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Human extends AbstractPlayer {

	/**
	 * Diese Variable enthaelt die Tabelle, auf die der Human-Player schiessen
	 * muss
	 */
	protected CreateTableView tableShoot;

	/**
	 * Diese Variable enthaelt das Feld, auf das ein Spieler schiesst
	 */
	protected Field field = null;

	/**
	 * Der Konstruktor erzeugt mit der uebergebenen AreaFactory und der Tabelle,
	 * auf welche geschossen wird, ein neues Human Objekt.<br>
	 * <br>
	 * Die Listen successfulShoot, failShoots und sunkShoots werden
	 * initialisiert.<br>
	 * 
	 * @param areaFactory
	 * @param tableShoot
	 */
	public Human(AreaFactory areaFactory, CreateTableView tableShoot) {
		setSuccessfulShoots(new ArrayList<Field>());
		setFailShoots(new ArrayList<Field>());
		setSunkShoots(new ArrayList<Field>());

		this.areaFactory = areaFactory;
		this.tableShoot = tableShoot;

	}

	/**
	 * Diese Methode fuehrt einen Spielzug des Human-Player aus. Es wird
	 * ueberprueft, ob sich an der Stelle, auf welche geschossen wird, ein
	 * Schiff befindet. Falls sich dort ein Schiff befindet, wird eine 1
	 * zurueckgegeben. Ist an der Stelle ein Schiff gesunken, wird eine 2
	 * zurueckgegeben. Falls sich an der Stelle kein Schiff befindet, wird eine
	 * 0 zurueckgegeben.
	 * 
	 * @return Integer
	 */
	@Override
	public int makeTurn() {
		Ship ship;

		/*
		 * Die Methode shoot ueberprueft, ob sich an uebergebener Stelle (Feld)
		 * und der Area der KI ein Schiff befindet. Falls ja, wird dies in der
		 * tableShoot entsprechend markiert. Außerdem wird das Feld der Liste
		 * successfulShoots hinzugefuegt.
		 */
		if ((ship = shoot(getField(), OptionsController.getInstance().getKi()
				.getAreaKI().getArea())) != null) {
			tableShoot.setValueAt("t", getField().getY(), getField().getX());
			getSuccessfulShoots().add(getField());

			/*
			 * Falls ein Schiff gesunken ist, werden alle Treffer in der
			 * tableShoot entsprechend markiert und das Feld wird der Liste
			 * sunkShoots hinzugefuegt.
			 */
			if (sunk(ship, OptionsController.getInstance().getKi().getAreaKI()
					.getArea())) {
				drawSunkShips(tableShoot);
				getSunkShoots().addAll(getSuccessfulShoots());
				setSuccessfulShoots(new ArrayList<Field>());
				OptionsController.getInstance().getTextArea().append(
						"Versenkt!\n");

				OptionsController.getInstance().getTextArea().append(
						"Sie duerfen nochmals schiessen!\n");
				OptionsController.getInstance().getTextArea().append("\n");
				OptionsController.getInstance().bottomScrollPane();
				return 2;
			}

			OptionsController.getInstance().getTextArea().append("Treffer!\n");
			OptionsController.getInstance().getTextArea().append(
					"Sie duerfen nochmals schiessen!\n");
			OptionsController.getInstance().getTextArea().append("\n");
			OptionsController.getInstance().bottomScrollPane();
			return 1;
		}

		/**
		 * Falls sich an der Stelle kein Schiff befindet, wird dies in der
		 * tableShoot entsprechend markiert und das Feld wird der Liste
		 * failShoots hinzugefuegt.
		 */
		else {
			tableShoot.setValueAt("m", getField().getY(), getField().getX());
			getFailShoots().add(getField());
		}

		/**
		 * temporäres Schussfeld wird auf null gesetzt
		 */
		setField(null);
		return 0;
	}

	/**
	 * Diese Methode gibt das Schussfeld zurueck
	 * 
	 * @return field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Diese Methode setzt das Schussfeld des Human-Players
	 * 
	 * @param field
	 */
	public void setField(Field field) {
		this.field = field;
	}
}
