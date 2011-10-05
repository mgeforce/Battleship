package pp.battleship.bs;

import java.util.ArrayList;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse repraesentiert einen Netzwerk Spieler. Die Klasse enthaelt die
 * Methoden, die fuer die Ausfuehrung eines Spielzuges im Netzwerk zustaendig
 * sind.<br>
 * <br>
 * Ausserdem erbt die Klasse von der Klasse Human.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class HumanNetwork extends Human {

	/**
	 * Der Konstruktor ruft den Konstruktor der Oberklasse Human auf und
	 * uebergibt diesem die areaFaytory und die tableShoot
	 * 
	 * @param areaFactory
	 * @param tableShoot
	 */
	public HumanNetwork(AreaFactory areaFactory, CreateTableView tableShoot) {

		super(areaFactory, tableShoot);
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

		OptionsController.getInstance().getNetwork().sendQuery(
				getField().getY() + 1, getField().getX() + 1);

		int reply = OptionsController.getInstance().getNetwork().readReply();

		switch (reply) {
		case 0:
			/*
			 * Bei einem verfehlten Schuss wird dies in der Tabelle entsprechend
			 * mit einem grauen Feld dargestellt. Au§erdem wird das Schussfeld
			 * der Liste failShoots hinzugefuegt.
			 */
			tableShoot.setValueAt("m", getField().getY(), getField().getX());
			getFailShoots().add(getField());
			break;
		case 1:
			/*
			 * Bei einem Treffer wird dies in der Tabelle entsprechend mit einem
			 * roten Feld dargestellt. Das Schussfeld wird in der Liste
			 * successfulShoots gespeichert. Au§erdem wird eine entsprechende
			 * Ausgabe in der JTextArea unter dem Spielfeld angezeigt.
			 */
			tableShoot.setValueAt("t", getField().getY(), getField().getX());
			getSuccessfulShoots().add(getField());
			OptionsController.getInstance().getTextArea().append("Treffer!\n");
			OptionsController.getInstance().getTextArea().append(
					"Sie duerfen nochmals schiessen!\n");
			OptionsController.getInstance().getTextArea().append("\n");
			OptionsController.getInstance().bottomScrollPane();
			break;
		case 2:
			/*
			 * Bei einem versenkten Schiff wird dies in der Tabelle entsprechend
			 * durch gelbe Felder dargestellt. Alle Schussfelder, welche das
			 * Schiff zum sinken gebracht haben, werden der Liste sunkShoots
			 * hinzugefuegt. Die Liste successfulShoots wird darafhin neu
			 * initialisiert. Au§erdem wird eine entsprechende Ausgabe in der
			 * JTextArea unter dem Spielfeld angezeigt.
			 */
			tableShoot.setValueAt("t", getField().getY(), getField().getX());
			getSuccessfulShoots().add(getField());
			getSunkShoots().addAll(getSuccessfulShoots());
			setSuccessfulShoots(new ArrayList<Field>());
			drawSunkShips(tableShoot);
			OptionsController.getInstance().setShipCount(
					OptionsController.getInstance().getShipCount() - 1);
			OptionsController.getInstance().getTextArea().append("Versenkt!\n");

			OptionsController.getInstance().getTextArea().append(
					"Sie duerfen nochmals schiessen!\n");
			OptionsController.getInstance().getTextArea().append("\n");
			OptionsController.getInstance().bottomScrollPane();
			break;
		}

		return reply;
	}
}
