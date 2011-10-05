package pp.battleship.bs;

import java.util.ArrayList;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse ist fuer die Kommunikation der KI im Netzwerk. Die Klasse
 * enthaelt die Methoden, die fuer die Kommunikation im Netzwerk zustaendig
 * sind.<br>
 * <br>
 * Ausserdem erbt die Klasse von AbstractPlayer.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 */
public class NetworkKI extends AbstractPlayer {

	/**
	 * Diese Variable speicher das Spielfeld des Gegners
	 */
	CreateTableView tableKI;

	/**
	 * Der Konstruktor erstellt ein neues NetworkKI Objekt. Es wird die
	 * uebergebene Tabelle gesetzt und alle noetigen Listen initialisiert.
	 */
	public NetworkKI(CreateTableView tableKI) {
		this.tableKI = tableKI;

		setSuccessfulShoots(new ArrayList<Field>());
		setFailShoots(new ArrayList<Field>());
		setSunkShoots(new ArrayList<Field>());
	}

	/**
	 * Diese Methodes schreibt die Daten von readQuery in shootField. Sie
	 * ueberprueft, ob ein Wert in der area des KiNetworks steht und markiert
	 * diesen im Spielfeld der Ki mit einem "t", falls ein Wert vorhanden ist.
	 * Auﬂerdem wird ueberprueft, ob ein Schiff gesunken ist. Ist ein Schiff
	 * getroffen, wird an die Query eine 1, bei versenkt eine 2 und bei Miss
	 * eine 0 gesendet.
	 * 
	 * @return Integer
	 */
	@Override
	public int makeTurn() {

		Field shootField = OptionsController.getInstance().getNetwork()
				.readQuery();

		Ship ship;

		if ((ship = shoot(shootField, OptionsController.getInstance()
				.getKiNetwork().getAreaFactory().getArea())) != null) {
			tableKI.setValueAt("t", shootField.getY(), shootField.getX());
			getSuccessfulShoots().add(shootField);
			if (sunk(ship, OptionsController.getInstance().getKiNetwork()
					.getAreaFactory().getArea())) {
				drawSunkShips(tableKI);
				getSunkShoots().addAll(getSuccessfulShoots());
				setSuccessfulShoots(new ArrayList<Field>());
				OptionsController.getInstance().getNetwork()
						.sendReplyToQuery(2);
				return 2;
			} else {
				OptionsController.getInstance().getNetwork()
						.sendReplyToQuery(1);
				return 1;
			}
		}

		else {
			tableKI.setValueAt("m", shootField.getY(), shootField.getX());
			getFailShoots().add(shootField);
			OptionsController.getInstance().getNetwork().sendReplyToQuery(0);
		}

		return 0;
	}

}
