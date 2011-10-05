package pp.battleship.bs;

import java.util.List;

/**
 * 
 * Dieses Interface bietet Methoden fuer einen Spielzug, zur Ueberpruefung ob
 * ein Schuss getroffen hat, ob ein Schiff gesunken ist. Au§erdem sind Methode
 * vorhanden, die die Felder der einzelnen Schuesse speichert.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public interface IPlayer {

	/*
	 * Alle folgenden Methoden sind in der Klasse AbstractPlayer naeher
	 * erlaeutert.
	 */
	public int makeTurn();

	public boolean sunk(Ship ship, Area area);

	public boolean isGamelost();

	public void setSuccessfulShoots(List<Field> successfulShoots);

	public List<Field> getSuccessfulShoots();

	public void setFailShoots(List<Field> failShoots);

	public List<Field> getFailShoots();

	public AreaFactory getAreaFactory();

	public List<Field> getSunkShoots();

}
