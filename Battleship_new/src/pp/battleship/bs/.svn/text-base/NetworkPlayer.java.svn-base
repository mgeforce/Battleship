package pp.battleship.bs;

import java.util.ArrayList;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse repraesentiert einen Netzwerk Spieler. Die Klasse enthaelt die
 * Methoden, Kommunikation im Netzwerk zustaendig sind.<br>
 * <br>
 * Ausserdem erbt die Klasse von AbstractPlayer.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class NetworkPlayer extends AbstractPlayer {

	/**
	 * Diese Variable dient zur Speicherung des Spielfeldes
	 */
	CreateTableView tablePlayer;

	/**
	 * Der Konstruktor erzeugt mit der uebergebenen Tabelle ein neues
	 * NetworkPlayer Objekt.<br>
	 * <br>
	 * Die Listen successfulShoot, failShoots und sunkShoots werden
	 * initialisiert.<br>
	 * 
	 * @param tableShoot
	 */
	public NetworkPlayer(CreateTableView tablePlayer) {
		this.tablePlayer = tablePlayer;
		setSuccessfulShoots(new ArrayList<Field>());
		setFailShoots(new ArrayList<Field>());
		setSunkShoots(new ArrayList<Field>());
	}

	/**
	 * Diese Methode fuehrt einen Spielzug des Human-Player aus. Ueber die
	 * Methode sendQuery wird eine Anfrage ueber das Netzwerk gesendet. Das
	 * Programm erhaelt ueber die Methode readReply die Antwort des Gegner, ob
	 * ein Schiff verfehlt, getroffen oder versenkt wurde. Der Rueckgabewert
	 * reply ist 0 fuer einen verfehlten Schuss, 1 fuer einen Treffer und 2 fuer
	 * ein versenktes Schiff.
	 * 
	 * @return reply
	 */
	@Override
	public int makeTurn() {

		Field shootField = OptionsController.getInstance().getNetwork()
				.readQuery();
		Ship ship;

		if ((ship = shoot(shootField, OptionsController.getInstance()
				.getAreaFactory().getArea())) != null) {
			getSuccessfulShoots().add(shootField);
			tablePlayer.setValueAt("t", shootField.getY(), shootField.getX());
			if (sunk(ship, OptionsController.getInstance().getAreaFactory()
					.getArea())) {
				drawSunkShips(tablePlayer);
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
			tablePlayer.setValueAt("m", shootField.getY(), shootField.getX());
			OptionsController.getInstance().getNetwork().sendReplyToQuery(0);
			getFailShoots().add(shootField);
		}

		return 0;
	}
}
