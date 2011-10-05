package pp.battleship.saveandload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import pp.battleship.bs.Field;
import pp.battleship.bs.Game;
import pp.battleship.bs.Human;
import pp.battleship.bs.HumanNetwork;
import pp.battleship.bs.KI;
import pp.battleship.bs.NetworkPlayer;
import pp.battleship.bs.Ship;
import pp.battleship.gui.CreateTableView;
import pp.battleship.gui.MainView;
import pp.battleship.gui.OptionsController;
import pp.battleship.gui.PlayingFieldView;
import pp.battleship.network.Network;

/**
 * 
 * Diese Klasse ist zum Laden von Spielstaenden. Ein Objekt dieser Klasse kann
 * alle Spielarten aus einer Datei laden.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Load {

	/**
	 * Diese Methode ist zum Laden von Spielstaenden. Es wird anhand der
	 * Save-Dateien entschieden welcher Fall geladen wird und eine entsprechende
	 * Methode wird aufgerufen.
	 * 
	 * @param filename
	 */
	public void load(String filename) {
		try {

			String[] tmp = filename.split("-");
			String timeStamp = tmp[tmp.length - 1];

			BufferedReader reader = new BufferedReader(new FileReader(filename));

			String str = reader.readLine();
			String[] strArray = str.split(" ");
			OptionsController.getInstance().setGameType(strArray[1]);

			str = reader.readLine();
			strArray = str.split(" ");

			/*
			 * Hier wird entschieden welche Spielart geladen wird.
			 */
			if (strArray[0].equals("Width"))
				loadSC(reader, strArray);
			if (strArray[0].equals("NetworkType")) {
				if (strArray[1].equals("host"))
					loadHost(timeStamp, reader, strArray);
				else if (strArray[1].equals("client"))
					loadClient(timeStamp, reader, strArray);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode laedt den GameType "Spieler gegen Spieler" mit NetworkType
	 * "Client". Es wird der Timestamp, der BufferedReader und die zuletzt
	 * ausgelesene Zeile uebergeben.
	 * 
	 * @param timeStamp
	 * @param reader
	 * @param strArray
	 */
	private void loadClient(String timeStamp, BufferedReader reader,
			String[] strArray) {
		try {
			List<Field> failShoots = new ArrayList<Field>();
			List<Field> successfulShoots = new ArrayList<Field>();
			List<Field> sunkShoots = new ArrayList<Field>();
			List<Field> failShootsNetwork = new ArrayList<Field>();
			List<Field> successfulShootsNetwork = new ArrayList<Field>();
			List<Field> sunkShootsNetwork = new ArrayList<Field>();

			/*
			 * Erste relevante Optionen werden im OptionsController gesetzt.
			 */
			String str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setGameType("ss");
			OptionsController.getInstance().setNetworkType("client");
			OptionsController.getInstance().setNetwork(new Network());
			OptionsController.getInstance().getNetwork()
					.createClientConnection(strArray[1]);

			/*
			 * Der Timestamp der Save-Datei wird vom Netzwerk ausgelesen
			 */
			String hostTimeStamp = OptionsController.getInstance().getNetwork()
					.readTimeStamp();

			/*
			 * Der Timestamp wird ueberprueft und eine Antwort wird ueber das
			 * Netzwerk versendet. Falls der Timestamp nicht gueltig ist, wird
			 * eine Fehlermeldung ausgegeben.
			 */
			boolean equalTimeStamps = hostTimeStamp.equals(timeStamp);
			OptionsController.getInstance().getNetwork().sendReplyToTimeStamp(
					equalTimeStamps);
			if (!equalTimeStamps) {
				OptionsController.getInstance().resetOptionsController();
				OptionsController.getInstance().getNetwork()
						.closeClientConnection();

				JOptionPane
						.showMessageDialog(
								MainView.getShipFrame(),
								"Ungueltiger Timestamp. Spiel kann nicht geladen werden.",
								"Information", JOptionPane.PLAIN_MESSAGE);
				return;
			}

			/*
			 * Die Breite des Spielfeldes wird aus der Datei ausgelesen und im
			 * OptionsController gespeichert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setWidth(
					Integer.parseInt(strArray[1]));

			/*
			 * Die Hoehe des Spielfeldes wird aus der Datei ausgelesen und im
			 * OptionsController gespeichert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setHeight(
					Integer.parseInt(strArray[1]));

			/*
			 * Die AreaFactory wird gesetzt.
			 */
			OptionsController.getInstance().setAreaFactory();

			CreateTableView tableHuman = new CreateTableView();

			/*
			 * Alle Miss-Felder des Clients werden aus der Datei ausgelesen und
			 * im Spielfeld entsprechende markiert. Au§erdem werden die
			 * Miss-Felder zur Liste failShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("m", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				failShoots.add(new Field(Integer.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle Treffer des Clients werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Treffer zur
			 * Liste successfulShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("t", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				successfulShoots.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle SunkShoots des Clients werden aus der Datei ausgelesen und
			 * im Spielfeld entsprechende markiert. Au§erdem werden die
			 * SunkShoots zur Liste sunkShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("v", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				sunkShoots.add(new Field(Integer.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			OptionsController.getInstance().setTable(tableHuman);

			CreateTableView tableNetwork = new CreateTableView();

			/*
			 * Alle Miss-Felder des Hosts werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Miss-Felder
			 * zur Liste failShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableNetwork.setValueAt("m", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				failShootsNetwork.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle Treffer des Hosts werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Treffer zur
			 * Liste successfulShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableNetwork.setValueAt("t", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				successfulShootsNetwork.add(new Field(Integer
						.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle SunkShoots des Hosts werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die SunkShoots
			 * zur Liste sunkShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableNetwork.setValueAt("v", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				sunkShootsNetwork.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Ein neues Game Objekt wird erstellt und im OptionsController
			 * gespeichert.
			 */
			OptionsController.getInstance().setGame(new Game());

			/*
			 * Die setup Methode des Game Objekt wird aufgerufen. Dieser werden
			 * ein neues NetworkPlayer Objekt und ein HumanNetwork Objekt
			 * uebergeben.
			 */
			OptionsController.getInstance().getGame().setup(
					new NetworkPlayer(tableNetwork),
					new HumanNetwork(OptionsController.getInstance()
							.getAreaFactory(), tableHuman));

			/*
			 * Alle Felder der Liste failShoots werden dem Client hinzugefuegt.
			 */
			for (int i = 0; i < failShoots.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getFailShoots().add(failShoots.get(i));
			}

			/*
			 * Alle Felder der Liste successfulShoots werden dem Client
			 * hinzugefuegt.
			 */
			for (int i = 0; i < successfulShoots.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getSuccessfulShoots().add(successfulShoots.get(i));
			}

			/*
			 * Alle Felder der Liste sunkShoots werden dem Client hinzugefuegt.
			 */
			for (int i = 0; i < sunkShoots.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getSunkShoots().add(sunkShoots.get(i));
			}

			/*
			 * Alle Felder der Liste failShoots werden dem Hosts hinzugefuegt.
			 */
			for (int i = 0; i < failShootsNetwork.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer1()
						.getFailShoots().add(failShootsNetwork.get(i));
			}

			/*
			 * Alle Felder der Liste successfulShoots werden dem Hosts
			 * hinzugefuegt.
			 */
			for (int i = 0; i < successfulShootsNetwork.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer1()
						.getSuccessfulShoots().add(
								successfulShootsNetwork.get(i));
			}

			/*
			 * Alle Felder der Liste sunkShoots werden dem Hosts hinzugefuegt.
			 */
			for (int i = 0; i < sunkShootsNetwork.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer1()
						.getSunkShoots().add(sunkShootsNetwork.get(i));
			}

			/*
			 * Alle Schiffe werden aus der Datei ausgelesen und der Area des
			 * Clients hinzugefuegt. Die Schiffe werden auf dem Spielfeld
			 * entsprechend markiert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setShipCount(0);
			while (strArray[0].equals("Ship")) {
				List<Field> fieldList = new ArrayList<Field>();
				for (int i = 1; i < strArray.length; i = i + 2) {
					fieldList.add(new Field(Integer.parseInt(strArray[i]),
							Integer.parseInt(strArray[i + 1])));
					tableNetwork.setValueAt("s", Integer
							.parseInt(strArray[i + 1]), Integer
							.parseInt(strArray[i]));

				}
				Ship ship = new Ship(fieldList);
				OptionsController.getInstance().setShipCount(
						OptionsController.getInstance().getShipCount() + 1);
				OptionsController.getInstance().getGame().getPlayer2()
						.getAreaFactory().getArea().getShList().add(ship);

				str = reader.readLine();
				strArray = str.split(" ");
			}

			/*
			 * Falls der Client nicht der ActivePlayer ist, muss
			 * changeActivePlayer aufgerufen werden, da der Client laut der
			 * Definition der Gameklasse ein Spiel beginnt.
			 */
			if (!strArray[0].equals("ActivePlayer")) {
				OptionsController.getInstance().getGame().changeActivePlayer();
			}

			reader.close();

			MainView
					.buildDesktop(new PlayingFieldView(tableHuman, tableNetwork));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode laedt den GameType "Spieler gegen Spieler" mit NetworkType
	 * "Host". Es wird der Timestamp, der BufferedReader und die zuletzt
	 * ausgelesene Zeile uebergeben.
	 * 
	 * @param timeStamp
	 * @param reader
	 * @param strArray
	 */
	private void loadHost(String timeStamp, BufferedReader reader,
			String[] strArray) {
		try {
			List<Field> failShoots = new ArrayList<Field>();
			List<Field> successfulShoots = new ArrayList<Field>();
			List<Field> sunkShoots = new ArrayList<Field>();
			List<Field> failShootsNetwork = new ArrayList<Field>();
			List<Field> successfulShootsNetwork = new ArrayList<Field>();
			List<Field> sunkShootsNetwork = new ArrayList<Field>();

			/*
			 * Erste relevante Optionen werden im OptionsController gesetzt.
			 */
			OptionsController.getInstance().setGameType("ss");
			OptionsController.getInstance().setNetworkType("host");
			OptionsController.getInstance().setNetwork(new Network());
			OptionsController.getInstance().getNetwork().createHostConnection();

			/*
			 * Der Timestamp (zur Ueberpruefung der Save-Dateien) wird ueber das
			 * Netzwerk versendet. Der Reply wird ausgewertet. Falls der Client
			 * keine passende Save-Datei geladen hat, wird eine Fehlermeldung
			 * ausgegeben.
			 */
			OptionsController.getInstance().getNetwork().sendTimeStamp(
					timeStamp);
			if (!OptionsController.getInstance().getNetwork()
					.readReplyToTimeStamp()) {
				OptionsController.getInstance().resetOptionsController();
				OptionsController.getInstance().getNetwork()
						.closeHostConnection();

				JOptionPane
						.showMessageDialog(
								MainView.getShipFrame(),
								"Ungueltiger Timestamp. Spiel kann nicht geladen werden.",
								"Information", JOptionPane.PLAIN_MESSAGE);
				return;
			}

			/*
			 * Die Breite des Spielfeldes wird aus der Datei ausgelesen und im
			 * OptionsController gespeichert.
			 */
			String str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setWidth(
					Integer.parseInt(strArray[1]));

			/*
			 * Die Hoehe des Spielfeldes wird aus der Datei ausgelesen und im
			 * OptionsController gespeichert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setHeight(
					Integer.parseInt(strArray[1]));

			/*
			 * Eine AreaFactory wird im OptionsController gespeichert.
			 */
			OptionsController.getInstance().setAreaFactory();

			CreateTableView tableHuman = new CreateTableView();

			/*
			 * Alle Miss-Felder des Hosts werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Miss-Felder
			 * zur Liste failShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("m", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				failShoots.add(new Field(Integer.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle Treffer des Hosts werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Treffer zur
			 * Liste successfulShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("t", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				successfulShoots.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle SunkShoots des Hosts werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die SunkShoots
			 * zur Liste sunkShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("v", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				sunkShoots.add(new Field(Integer.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			OptionsController.getInstance().setTable(tableHuman);

			CreateTableView tableNetwork = new CreateTableView();

			/*
			 * Alle Miss-Felder des Clients werden aus der Datei ausgelesen und
			 * im Spielfeld entsprechende markiert. Au§erdem werden die
			 * Miss-Felder zur Liste failShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableNetwork.setValueAt("m", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				failShootsNetwork.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle Treffer des Clients werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Treffer zur
			 * Liste successfulShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableNetwork.setValueAt("t", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				successfulShootsNetwork.add(new Field(Integer
						.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle SunkShoots des Clients werden aus der Datei ausgelesen und
			 * im Spielfeld entsprechende markiert. Au§erdem werden die
			 * SunkShoots zur Liste sunkShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableNetwork.setValueAt("v", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				sunkShootsNetwork.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Ein neues Game Objekt wird erstellt und im OptionsController
			 * gespeichert.
			 */
			OptionsController.getInstance().setGame(new Game());

			/*
			 * Die setup Methode des Game Objekt wird aufgerufen. Dieser werden
			 * ein neues HumanNetwork Objekt und ein NetworkPlayer Objekt
			 * uebergeben.
			 */
			OptionsController.getInstance().getGame().setup(
					new HumanNetwork(OptionsController.getInstance()
							.getAreaFactory(), tableHuman),
					new NetworkPlayer(tableNetwork));

			/*
			 * Alle Felder der Liste failShoots werden dem Hosts hinzugefuegt.
			 */
			for (int i = 0; i < failShoots.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer1()
						.getFailShoots().add(failShoots.get(i));
			}

			/*
			 * Alle Felder der Liste successfulShoots werden dem Hosts
			 * hinzugefuegt.
			 */
			for (int i = 0; i < successfulShoots.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer1()
						.getSuccessfulShoots().add(successfulShoots.get(i));
			}

			/*
			 * Alle Felder der Liste sunkShoots werden dem Hosts hinzugefuegt.
			 */
			for (int i = 0; i < sunkShoots.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer1()
						.getSunkShoots().add(sunkShoots.get(i));
			}

			/*
			 * Alle Felder der Liste failShoots werden dem Clients hinzugefuegt.
			 */
			for (int i = 0; i < failShootsNetwork.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getFailShoots().add(failShootsNetwork.get(i));
			}

			/*
			 * Alle Felder der Liste successfulShoots werden dem Clients
			 * hinzugefuegt.
			 */
			for (int i = 0; i < successfulShootsNetwork.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getSuccessfulShoots().add(
								successfulShootsNetwork.get(i));
			}

			/*
			 * Alle Felder der Liste sunkShoots werden dem Clients hinzugefuegt.
			 */
			for (int i = 0; i < sunkShootsNetwork.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getSunkShoots().add(sunkShootsNetwork.get(i));
			}

			/*
			 * Alle Schiffe werden aus der Datei ausgelesen und der Area des
			 * Hosts hinzugefuegt. Die Schiffe werden auf dem Spielfeld
			 * entsprechend markiert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setShipCount(0);
			while (strArray[0].equals("Ship")) {
				List<Field> fieldList = new ArrayList<Field>();
				for (int i = 1; i < strArray.length; i = i + 2) {
					fieldList.add(new Field(Integer.parseInt(strArray[i]),
							Integer.parseInt(strArray[i + 1])));
					tableNetwork.setValueAt("s", Integer
							.parseInt(strArray[i + 1]), Integer
							.parseInt(strArray[i]));

				}
				Ship ship = new Ship(fieldList);
				OptionsController.getInstance().setShipCount(
						OptionsController.getInstance().getShipCount() + 1);
				System.out.println("shipCount: "
						+ OptionsController.getInstance().getShipCount());
				OptionsController.getInstance().getGame().getPlayer1()
						.getAreaFactory().getArea().getShList().add(ship);

				str = reader.readLine();
				strArray = str.split(" ");
			}

			/*
			 * Falls der Host der ActivePlayer ist, muss changeActivePlayer
			 * aufgerufen werden, da der Host laut der Definition der Gameklasse
			 * ein Spiel nicht beginnt.
			 */
			if (strArray[0].equals("ActivePlayer")) {
				OptionsController.getInstance().getGame().changeActivePlayer();
			}

			reader.close();

			MainView
					.buildDesktop(new PlayingFieldView(tableHuman, tableNetwork));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Diese Methode laedt den GameType "Spieler gegen Computer". Es wird der
	 * BufferedReader und die zuletzt ausgelesene Zeile uebergeben.
	 * 
	 * @param timeStamp
	 * @param reader
	 * @param strArray
	 */
	public void loadSC(BufferedReader reader, String[] strArray) {
		try {

			List<Field> failShootsHuman = new ArrayList<Field>();
			List<Field> successfulShootsHuman = new ArrayList<Field>();
			List<Field> sunkShootsHuman = new ArrayList<Field>();

			/*
			 * Die Breite des Spielfeldes wird aus der Datei ausgelesen und im
			 * OptionsController gespeichert.
			 */
			OptionsController.getInstance().setWidth(
					Integer.parseInt(strArray[1]));

			/*
			 * Die Hoehe des Spielfeldes wird aus der Datei ausgelesen und im
			 * OptionsController gespeichert.
			 */
			String str = reader.readLine();
			strArray = str.split(" ");
			OptionsController.getInstance().setHeight(
					Integer.parseInt(strArray[1]));

			/*
			 * Die AreaFactory wird gesetzt.
			 */
			OptionsController.getInstance().setAreaFactory();

			/*
			 * Es wird ein neues KI Objekt erzeugt und im OptionsController
			 * gespeichert.
			 */
			OptionsController.getInstance().setKi(new KI());

			CreateTableView tableHuman = new CreateTableView();
			CreateTableView tableKI = new CreateTableView();

			/*
			 * Alle Miss-Shoots des Players werden aus der Datei ausgelesen und
			 * im Spielfeld entsprechende markiert. Au§erdem werden die
			 * Miss-Shoots zur Liste failShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("m", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				failShootsHuman.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle Treffer des Players werden aus der Datei ausgelesen und im
			 * Spielfeld entsprechende markiert. Au§erdem werden die Treffer zur
			 * Liste successfulShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("t", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				successfulShootsHuman.add(new Field(Integer
						.parseInt(strArray[i]), Integer
						.parseInt(strArray[i + 1])));
			}

			/*
			 * Alle SunkShoots des Players werden aus der Datei ausgelesen und
			 * im Spielfeld entsprechende markiert. Au§erdem werden die
			 * SunkShoots zur Liste sunkShoots hinzugefuegt.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableHuman.setValueAt("v", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				sunkShootsHuman.add(new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1])));
			}

			OptionsController.getInstance().setTable(tableHuman);

			/*
			 * Ein neues Game Objekt wird erstellt und im OptionsController
			 * gespeichert.
			 */
			OptionsController.getInstance().setGame(new Game());

			/*
			 * Die setup Methode des Game Objekt wird aufgerufen. Dieser werden
			 * das KI Objekt aus dem OptionsController und ein neues Human
			 * Objekt uebergeben.
			 */
			OptionsController.getInstance().getGame().setup(
					OptionsController.getInstance().getKi(),
					new Human(OptionsController.getInstance().getAreaFactory(),
							tableHuman));

			/*
			 * Alle Felder der Liste failShoots werden dem Player hinzugefuegt.
			 */
			for (int i = 0; i < failShootsHuman.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getFailShoots().add(failShootsHuman.get(i));
			}

			/*
			 * Alle Felder der Liste successfulShoots werden dem Player
			 * hinzugefuegt.
			 */
			for (int i = 0; i < successfulShootsHuman.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getSuccessfulShoots()
						.add(successfulShootsHuman.get(i));
			}

			/*
			 * Alle Felder der Liste sunkShoots werden dem Player hinzugefuegt.
			 */
			for (int i = 0; i < sunkShootsHuman.size(); i++) {
				OptionsController.getInstance().getGame().getPlayer2()
						.getSunkShoots().add(sunkShootsHuman.get(i));
			}

			/*
			 * Alle Schiffe werden aus der Datei ausgelesen und der Area des
			 * Players hinzugefuegt. Die Schiffe werden auf dem Spielfeld
			 * entsprechend markiert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			while (strArray[0].equals("Ship")) {
				List<Field> fieldList = new ArrayList<Field>();
				for (int i = 1; i < strArray.length; i = i + 2) {
					fieldList.add(new Field(Integer.parseInt(strArray[i]),
							Integer.parseInt(strArray[i + 1])));
					tableKI.setValueAt("s", Integer.parseInt(strArray[i + 1]),
							Integer.parseInt(strArray[i]));
				}
				Ship ship = new Ship(fieldList);
				OptionsController.getInstance().getGame().getPlayer2()
						.getAreaFactory().getArea().getShList().add(ship);

				str = reader.readLine();
				strArray = str.split(" ");
			}

			/*
			 * Alle Felder der Liste failShoots werden im Spielfeld der KI
			 * entsprechend dargestellt. Au§erdem werden die Felder aus der
			 * Liste freeFields der KI geloescht (damit die KI auf diese nicht
			 * mehr schiesst).
			 */
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableKI.setValueAt("m", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				Field field2remove = new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1]));
				OptionsController.getInstance().getKi().getFreeFields().remove(
						field2remove);
			}

			/*
			 * Alle Felder der Liste successfulShoots werden im Spielfeld der KI
			 * entsprechend dargestellt. Au§erdem werden die Felder aus der
			 * Liste freeFields der KI geloescht (damit die KI auf diese nicht
			 * mehr schiesst).
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableKI.setValueAt("t", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				Field field2remove = new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1]));
				OptionsController.getInstance().getKi().getFreeFields().remove(
						field2remove);
			}

			/*
			 * Alle Felder der Liste sunkShoots werden im Spielfeld der KI
			 * entsprechend dargestellt. Au§erdem werden die Felder aus der
			 * Liste freeFields der KI geloescht (damit die KI auf diese nicht
			 * mehr schiesst).
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			for (int i = 1; i < strArray.length; i = i + 2) {
				tableKI.setValueAt("v", Integer.parseInt(strArray[i + 1]),
						Integer.parseInt(strArray[i]));
				Field field2remove = new Field(Integer.parseInt(strArray[i]),
						Integer.parseInt(strArray[i + 1]));
				OptionsController.getInstance().getKi().getFreeFields().remove(
						field2remove);
			}

			OptionsController.getInstance().getKi().setTable(tableKI);

			/*
			 * Alle Schiffe werden aus der Datei ausgelesen und der Area der KI
			 * hinzugefuegt. Die Schiffe werden auf dem Spielfeld entsprechend
			 * markiert.
			 */
			str = reader.readLine();
			strArray = str.split(" ");
			while (strArray[0].equals("Ship")) {
				List<Field> fieldList = new ArrayList<Field>();
				for (int i = 1; i < strArray.length; i = i + 2) {
					fieldList.add(new Field(Integer.parseInt(strArray[i]),
							Integer.parseInt(strArray[i + 1])));
				}
				Ship ship = new Ship(fieldList);
				OptionsController.getInstance().getGame().getPlayer1()
						.getAreaFactory().getArea().getShList().add(ship);

				str = reader.readLine();
				strArray = str.split(" ");
			}

			reader.close();

			MainView.buildDesktop(new PlayingFieldView(tableHuman, tableKI));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
