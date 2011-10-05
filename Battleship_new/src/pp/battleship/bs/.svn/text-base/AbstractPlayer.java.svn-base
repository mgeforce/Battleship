package pp.battleship.bs;

import java.util.List;

import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse implementiert das IPlayer Interface. Es werden die meisten im
 * Interface deklarierten Methoden genau definiert.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public abstract class AbstractPlayer implements IPlayer {

	/**
	 * Diese Variable enthaelt die AreaFactory des jeweiligen Spielers
	 */
	protected AreaFactory areaFactory;

	/**
	 * Diese Liste enthaelt alle getroffenen Schuesse des jeweiligen Spielers
	 */
	private List<Field> successfulShoots;

	/**
	 * Diese Liste enthaelt Schuesse die ein Schiff zum sinken gebracht haben.
	 */
	private List<Field> sunkShoots;

	/**
	 * Diese Liste enthaelt alle Schuesse die daneben gingen
	 */
	private List<Field> failShoots;

	/**
	 * Dieser Methode wird eine Tabelle uebergeben. In dieser Tabelle werden
	 * alle getroffenen Schiffsteile als versenkt markiert.
	 * 
	 * @param table
	 */
	protected void drawSunkShips(CreateTableView table) {
		for (int i = 0; i < OptionsController.getInstance().getWidth(); i++) {
			for (int j = 0; j < OptionsController.getInstance().getHeight(); j++) {
				if (table.getValueAt(j, i).equals("t"))
					table.setValueAt("v", j, i);
			}
		}
	}

	/**
	 * Diese Methode prueft ob sich am uebergebenen Feld in uebergebener Area
	 * ein Schiff befindet. Falls ja, wird das Schiff zurueckgegeben.
	 * 
	 * @param shootField
	 * @param area
	 * 
	 * @return ship
	 */
	protected Ship shoot(Field shootField, Area area) {
		Ship ship;
		if ((ship = area.getShip(shootField)) != null) {
			ship.removeShipField(shootField);
			return ship;
		}

		return null;
	}

	/**
	 * Diese Methode prueft ob das uebergebene Schiff in uebergebener Area
	 * gesunken ist. Falls ja, wird true zurueckgegeben.
	 * 
	 * @return boolean
	 */
	public boolean sunk(Ship ship, Area area) {
		if (ship.getFields().isEmpty()) {
			area.getShList().remove(ship);
			return true;
		}
		return false;
	}

	/**
	 * Diese Methode prueft, ob alle Schiffe gesunken sind. Falls ja, wird true
	 * zurueckgegeben.
	 */
	public boolean isGamelost() {
		if (areaFactory.getArea().getShList().isEmpty())
			return true;
		return false;
	}

	/**
	 * Diese Methode setzt die Liste, welche die Treffer eines Spielers
	 * enthaelt.
	 */
	public void setSuccessfulShoots(List<Field> successfulShoots) {
		this.successfulShoots = successfulShoots;
	}

	/**
	 * Diese Methode gibt die Liste zurueck, welche die Treffer eines Spielers
	 * enthaelt.
	 * 
	 * @return fieldList
	 */
	public List<Field> getSuccessfulShoots() {
		return successfulShoots;
	}

	/**
	 * Diese Methode setzt die Liste, welche alle Failshoots einer Spielers
	 * enthaelt.
	 */
	public void setFailShoots(List<Field> failShoots) {
		this.failShoots = failShoots;
	}

	/**
	 * Diese Methode gibt die Liste zurueck, welche die Failshoots eines
	 * Spielers enthaelt.
	 * 
	 * @return fieldList
	 */
	public List<Field> getFailShoots() {
		return failShoots;
	}

	/**
	 * Diese Methode setzt die Liste, welche alle Felder mit dem ein Schiff
	 * versenkt wurde, enthaelt.
	 */
	public void setSunkShoots(List<Field> sunkShoots) {
		this.sunkShoots = sunkShoots;
	}

	/**
	 * Diese Methode gibt die Liste zurueck, welche alle Felder mit dem ein
	 * Schiff versenkt wurde, enthaelt.
	 * 
	 * @return fieldList
	 */
	public List<Field> getSunkShoots() {
		return sunkShoots;
	}

	/**
	 * Diese Methode liefert die AreaFactory eines Spielers
	 * 
	 * @return areaFactory
	 */
	public AreaFactory getAreaFactory() {
		return areaFactory;
	}

}
