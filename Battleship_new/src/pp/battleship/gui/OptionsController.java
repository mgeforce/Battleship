package pp.battleship.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import pp.battleship.bs.AreaFactory;
import pp.battleship.bs.Game;
import pp.battleship.bs.KI;
import pp.battleship.bs.KINetwork;
import pp.battleship.network.Network;

/**
 * Diese Klasse implementiert den OptionsController des Programms mit dem
 * Singelton Pattern. Es ist nicht moeglich mehr als eine Instanz des
 * OptionControllers zu erzeugen. Das liegt daran, dass der Konstruktor als
 * private deklariert ist und es nur ueber die statische Methode getInstance()
 * moeglich ist ein Objekt dieser Klasse zu erzeugen. <br>
 * Beim Aufruf dieser Methode wird geprueft ob ein OptionsController-Objekt
 * bereits erzeugt wurde und falls nicht wird ein solches Objekt erzeugt.
 * Existiert schon ein solches Objekt wird es zurueckgegeben. Auf diese Weise
 * ist es moeglich ueberall im Programm ueber einen statischen Methoden-Aufruf
 * das verwendete Factory Objekt zu bekommen. <br>
 * <br>
 * Die Aufgabe dieses Singleton Objektes ist es alle Optionen und Objekte,
 * welche in mehrere Klassen benoetigt werden, zu speichern.<br>
 * <br>
 * Der Vorteil dieser Klasse ist die Auslagerung aller Optionen und Objekte,
 * welche fuer den Spielablauf wichtig sind.
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class OptionsController {

	/**
	 * Diese Variable enthaelt die einzige Instanz der Klasse OptionsController,
	 * die von diesem Programm verwendet wird. Um eine Instanz dieser Klasse zu
	 * erhalten muss die Methode getInstance() aufgerufen werden. Dadurch soll
	 * verhindert werden, dass verschiedene OptionsController Objekte im
	 * Programm aktiv sind. Das entspricht dem Singleton - Pattern.
	 */
	private static OptionsController instance;

	/**
	 * Diese Variable enthaelt den GameType des Spieles
	 */
	private String gametype;

	/**
	 * Diese Variable enthaelt den ausgewaehlten Netzwerktyp
	 */
	private String networkType;

	/**
	 * Dieser Wert enthaelt die Breite des Spielfeldes
	 */
	private int width;

	/**
	 * Dieser Wert enthaelt die Hoehe des Spielfeldes
	 */
	private int height;

	/**
	 * Dieser Variable enthaelt die AreaFactory eines Spielers
	 */
	private AreaFactory areaFactory = null;

	/**
	 * Diese Variable enthaelt die einzelnen ausgewaehlten Schiffstypen
	 */
	private Stack<Integer> shipType = new Stack<Integer>();

	/**
	 * Diese Variable enthaelt die einzelnen ausgewaehlten Schiffstypen für die
	 * KI
	 */
	private Stack<Integer> shipTypeKI = new Stack<Integer>();

	/**
	 * Diese Variable enthaelt das Spielfeld des Spielers
	 */
	private CreateTableView table = null;

	/**
	 * Dieser Variable enthaelt den Speichern Button des Menus
	 */
	JMenuItem item = null;

	/**
	 * Diese Variable enthaelt das JScrollPane, in welchem auf dem Spielfeld
	 * benoetigte Informationen angezeigt werden
	 */
	private JScrollPane scrollPane = null;

	/**
	 * Diese Variable enthaelt die JTextArea, welche unter den Spielfeldern
	 * angezeigt wird
	 */
	private JTextArea textArea = null;

	/**
	 * Diese Variable enthaelt das Game Objekt
	 */
	private Game game = null;

	/**
	 * Diese Variable enthaelt die KI beim Spieltyp "Spieler gegen Computer"
	 */
	private KI ki = null;

	/**
	 * Diese Variable enthaelt die KI beim Spieltyp "Computer gegen Computer"
	 */
	private KINetwork kiNetwork = null;

	/**
	 * Diese Variable enthaelt das Netzwerk Objekt
	 */
	private Network network = null;

	/**
	 * Dieser Wert enthaelt die IP des Hosts beim Spieltyp
	 * "Spieler gegen Spieler"
	 */
	private String ip;

	/**
	 * Dieser Wert enthaelt die Anzahl Schiffe die noch nicht versenkt wurden.
	 * Fuer unabhaengiges Erkennen ob Spiel vorbei ist (nur bei
	 * Netzwerkspielen).
	 */
	private int shipCount = 0;

	/**
	 * Dieser Wert ist für den Mouselistener im Spielablauf. Der Wert
	 * ueberprueft ob ein Spieler schiessen darf.
	 */
	private boolean allowed = false;

	/**
	 * Der Konstruktor erzeugt ein OptionsController Objekt. Er ist wegen dem
	 * Singelton Pattern als private deklariert und kann deshalb nur von dieser
	 * Klasse aus instantiiert werden. Dazu muss die Methode getInstance()
	 * aufgerufen werden.
	 */
	private OptionsController() {

	}

	/**
	 * Mit dieser statischen Methode wird das Singelton Pattern implementiert.
	 * Die Methode gibt das einzige OptionsController Objekt zurueck das im
	 * Programm verwendet wird.
	 * 
	 * @return OptionsController Object
	 */
	public static OptionsController getInstance() {
		if (instance == null)
			instance = new OptionsController();
		return instance;
	}

	/**
	 * Diese Methode gibt die KI zurueck (wird nur im GameType
	 * "Spieler gegen Computer" benoetigt)
	 * 
	 * @return KI Object
	 */
	public KI getKi() {
		return ki;
	}

	/**
	 * Diese Methode setzt eine KI
	 * 
	 * @param KI
	 */
	public void setKi(KI ki) {
		this.ki = ki;
	}

	/**
	 * Diese Methode gibt das Spielfeld des Spielers zurueck
	 * 
	 * @return Spielfeld des Spielers
	 */
	public CreateTableView getTable() {
		return table;
	}

	/**
	 * Diese Methode setzt das Spielfeld des Spielers
	 * 
	 * @param Spielfeld
	 *            des Spielers
	 */
	public void setTable(CreateTableView table) {
		this.table = table;
	}

	/**
	 * Diese Methode gibt alle ausgewaehlten Schiffstypen zurueck
	 * 
	 * @return Stack mit allen ausgewaehlten Schiffstypen
	 */
	public Stack<Integer> getShipType() {
		return shipType;
	}

	/**
	 * Diese Methode setzt die ausgewaehlten Schiffstypen
	 * 
	 * @param Stack
	 *            mit den ausgewaehlten Schiffstypen
	 */
	public void setShipType(Stack<Integer> shipType) {
		this.shipType = shipType;
	}

	/**
	 * Diese Methode gibt alle ausgewaehlten Schiffstypen der KI zurueck
	 * 
	 * @return Stack mit allen ausgewaehlten Schiffstypen der KI
	 */
	public void setShipTypeKI(Stack<Integer> shipTypeKI) {
		this.shipTypeKI = shipTypeKI;
	}

	/**
	 * Diese Methode setzt die ausgewaehlten Schiffstypen der KI
	 * 
	 * @param Stack
	 *            mit den ausgewaehlten Schiffstypen der KI
	 */
	public Stack<Integer> getShipTypeKI() {
		return shipTypeKI;
	}

	/**
	 * Diese Methode initialisiert die Schiffstypen. Wird benoetigt, falls ein
	 * Spieler zuviele Schiffe auswaehlt.
	 */
	public void initializeShipType() {
		shipType = new Stack<Integer>();
		shipTypeKI = new Stack<Integer>();
	}

	/**
	 * Diese Methode gibt die Breite des Spielfeldes zurueck
	 * 
	 * @return Integer Wert der die Breite enthaelt
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Diese Methode setzt die Breite des Spielfeldes
	 * 
	 * @param Integer
	 *            Wert der die Breite enthaelt
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Diese Methode gibt die Hoehe des Spielfeldes zurueck
	 * 
	 * @return Integer Wert der die Hoehe enthaelt
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Diese Methode setzt die Hoehe des Spielfeldes
	 * 
	 * @param Integer
	 *            Wert der die Hoehe enthaelt
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Diese Methode setzt den ausgewaehlten GameType
	 * 
	 * @param String
	 *            des ausgewaehlten Gamestypes
	 */
	public void setGameType(String gameType) {
		gametype = gameType;
	}

	/**
	 * Diese Methode gibt den ausgewaehlten GameType zurueck
	 * 
	 * @return String der den ausgewaehlten GameType enthaelt
	 */
	public String getGameType() {
		return gametype;
	}

	public boolean setShipTypePush(List<JSpinner> spinners, int belegungsfaktor) {
		Iterator<JSpinner> ships = spinners.iterator();
		int shipLength;
		int shipCounter = 2;

		while (ships.hasNext()) {
			shipLength = Integer.parseInt(ships.next().getValue().toString());

			for (int i = 0; i < shipLength; i++) {
				belegungsfaktor -= shipLength;
				if (belegungsfaktor < 0)
					return false;
				shipType.push(shipCounter);
				shipTypeKI.push(shipCounter);

			}

			shipCounter++;
		}

		return true;
	}

	/**
	 * Diese Methode erzeugt eine neue AreaFactory mit einer zugehoerigen Area
	 */
	public void setAreaFactory() {
		areaFactory = new AreaFactory(width, height);
	}

	/**
	 * Diese Methode gibt die AreaFactory eines Spielers zurueck
	 * 
	 * @return AreaFactory eines Spielers
	 */
	public AreaFactory getAreaFactory() {
		return areaFactory;
	}

	/**
	 * Diese Methode setzt alle benoetigten Werte im OptionsController zurueck.
	 * Diese Methode wird aufgerufen wenn ein neues Spiel gestartet wird.
	 */
	public void resetOptionsController() {
		gametype = null;
		width = 0;
		height = 0;
		areaFactory = null;
		shipType.clear();
		shipTypeKI.clear();
		ki = null;
		table = null;
	}

	/**
	 * Diese Methode gibt des Netzwerktyp zurueck. Wird nur bei Netzwerkspielen
	 * benoetigt.
	 * 
	 * @return String mit Netzwerktyp
	 */
	public String getNetworkType() {
		return networkType;
	}

	/**
	 * Diese Methode setzt den ausgewaehlten Netzwerktyp. Wird nur bei
	 * Netzwerkspielen benoetigt.
	 * 
	 * @param String
	 *            mit Netzwerktyp
	 */
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	/**
	 * Diese Methode setzt das erstellte Netzwerk Objekt
	 * 
	 * @param Network
	 *            Object
	 */
	public void setNetwork(Network network) {
		this.network = network;
	}

	/**
	 * Diese Methode gibt das erstellte Netzwerk Objekt zurueck
	 * 
	 * @return Network Object
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * Diese Methode setzt den Integer Wert, der ueberprueft ob alle Schiffe
	 * versenkt wurden. Wird nur bei Netzwerkspielen benoetigt.
	 * 
	 * @param Interger
	 *            Wert
	 */
	public void setShipCount(int shipCount) {
		this.shipCount = shipCount;
	}

	/**
	 * Diese Methode gibt den Integer Wert, der ueberprueft ob alle Schiffe
	 * versenkt wurden, zurueck. Wird nur bei Netzwerkspielen benoetigt.
	 * 
	 * @return Integer Wert mit der Anzahl uebriger Schiffe
	 */
	public int getShipCount() {
		return shipCount;
	}

	/**
	 * Diese Methode setzt den boolean Wert, der ueberprueft ob der Wert des
	 * Mouselisteners uebergeben wird.
	 * 
	 * @param Boolean
	 */
	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	/**
	 * Diese Methode gibt einen boolean zurueck, der ueberprueft ob der Wert des
	 * Mouselisteners uebergeben wird.
	 * 
	 * @return Boolean
	 */
	public boolean isAllowed() {
		return allowed;
	}

	/**
	 * Diese Methode setzt die KI, welche beim GameType
	 * "Computer gegen Computer" benoetigt wird.
	 * 
	 * @param kiNetwork
	 *            Object
	 */
	public void setKiNetwork(KINetwork kiNetwork) {
		this.kiNetwork = kiNetwork;
	}

	/**
	 * Diese Methode liefert das KI Objekt, welches beim GameType
	 * "Computer gegen Computer" benoetigt wird.
	 * 
	 * @return kiNetwork Object
	 */
	public KINetwork getKiNetwork() {
		return kiNetwork;
	}

	/**
	 * Diese Methode setzt das Game Objekt, welches fuer den Spielablauf
	 * zustaendig ist.
	 * 
	 * @param game
	 *            Object
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Diese Methode liefert das Game Objekt, welches fuer den Spielablauf
	 * zustaendig ist.
	 * 
	 * @return game Object
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Diese Methode setzt die IP, welche der Client bei der Verbindung
	 * auswaehlt.
	 * 
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Diese Methode liefert die IP zurueck, welche der Client bei der
	 * Verbindung ausgewaehlt hat.
	 * 
	 * @return String
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Diese Methode setzt das uebergebene JScrollPane, welches unter den
	 * Spielfeldern angezeigt wird
	 * 
	 * @param scrollPane
	 */
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * Diese Methode gibt das JScrollPane zurueck
	 * 
	 * @return scrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * Diese Methode setzt das JScrollPane auf die unterste Zeile der JTextArea
	 */
	public void bottomScrollPane() {
		scrollPane.getVerticalScrollBar().setValue(
				scrollPane.getVerticalScrollBar().getMaximum());
	}

	/**
	 * Diese Methode setzt die JTextArea, welche unter den Spielfeldern
	 * angezeigt wird
	 * 
	 * @param textArea
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * Diese Methode gibt die JTextArea zurueck, welche unter den Spielfeldern
	 * angezeigt wird
	 * 
	 * @return
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Diese Methode gibt den Speichern Button des Menus zurueck
	 * 
	 * @return item
	 */
	public JMenuItem getItem() {
		return item;
	}

	/**
	 * Diese Methode setzt den Speichern Button des Menus
	 * 
	 * @param item
	 */
	public void setItem(JMenuItem item) {
		this.item = item;
	}
}
