package pp.battleship.saveandload;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse ist zum Speichern von Spielstaenden. Ein Objekt dieser Klasse
 * kann alle Spielarten abspeichern..<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Save {

	/**
	 * Die Methode speichert ein laufendes Spiel im GameType
	 * "Spieler gegen Computer" in uebergebene Datei.
	 * 
	 * @param filename
	 */
	public void saveSC(String filename) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			/*
			 * Der GameType wird in die Datei geschrieben.
			 */
			writer.write("Gametype ");
			writer.write(OptionsController.getInstance().getGameType());
			writer.write("\r\n");

			/*
			 * Die Breite des Spielfeldes wird in die Datei geschrieben.
			 */
			writer.write("Width ");
			writer.write("" + OptionsController.getInstance().getWidth());
			writer.write("\r\n");

			/*
			 * Die Hoehe des Spielfeldes wird in die Datei geschrieben.
			 */
			writer.write("Height ");
			writer.write("" + OptionsController.getInstance().getHeight());
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste failShoots des Spielers
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldMissHuman ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getFailShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getFailShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getFailShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste successfulShoots des
			 * Spielers werden in die Datei geschrieben.
			 */
			writer.write("FieldHitHuman ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getSuccessfulShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSuccessfulShoots().get(i)
								.getX() + " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSuccessfulShoots().get(i)
								.getY() + " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste sunkShoots des Spielers
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldSunkHuman ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getSunkShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSunkShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSunkShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der verbleibenden Schiffe des Spielers werden in
			 * die Datei geschrieben.
			 */
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getAreaFactory().getArea().getShList().size(); i++) {
				writer.write("Ship ");
				for (int j = 0; j < OptionsController.getInstance().getGame()
						.getPlayer2().getAreaFactory().getArea().getShList()
						.get(i).getFields().size(); j++) {
					writer.write(""
							+ OptionsController.getInstance().getGame()
									.getPlayer2().getAreaFactory().getArea()
									.getShList().get(i).getFields().get(j)
									.getX() + " ");
					writer.write(""
							+ OptionsController.getInstance().getGame()
									.getPlayer2().getAreaFactory().getArea()
									.getShList().get(i).getFields().get(j)
									.getY() + " ");
				}
				writer.write("\r\n");
			}

			/*
			 * Alle Koordinaten der Felder aus der Liste failShoots der KI
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldMissKI ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getFailShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getFailShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getFailShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste successfulShoots der KI
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldHitKI ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getSuccessfulShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSuccessfulShoots().get(i)
								.getX() + " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSuccessfulShoots().get(i)
								.getY() + " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste sunkShoots der KI
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldSunkKI ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getSunkShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSunkShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSunkShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der verbleibenden Schiffe der KI werden in die
			 * Datei geschrieben.
			 */
			for (int i = 0; i < OptionsController.getInstance().getKi()
					.getAreaFactory().getArea().getShList().size(); i++) {
				writer.write("Ship ");
				for (int j = 0; j < OptionsController.getInstance().getKi()
						.getAreaFactory().getArea().getShList().get(i)
						.getFields().size(); j++) {
					writer.write(""
							+ OptionsController.getInstance().getKi()
									.getAreaFactory().getArea().getShList()
									.get(i).getFields().get(j).getX() + " ");
					writer.write(""
							+ OptionsController.getInstance().getKi()
									.getAreaFactory().getArea().getShList()
									.get(i).getFields().get(j).getY() + " ");
				}
				writer.write("\r\n");
			}

			writer.write("\r\n");
			writer.write("\r\n");

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Die Methode speichert ein laufendes Spiel im GameType
	 * "Spieler gegen Spieler" und im NetworkType "Host" in uebergebene Datei.
	 * 
	 * @param filename
	 */
	public void saveHost(String filename) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			/*
			 * Der GameType wird in die Datei geschrieben.
			 */
			writer.write("Gametype ");
			writer.write(OptionsController.getInstance().getGameType());
			writer.write("\r\n");

			/*
			 * Der NetworkType wird in die Datei geschrieben.
			 */
			writer.write("NetworkType ");
			writer.write(OptionsController.getInstance().getNetworkType());
			writer.write("\r\n");

			/*
			 * Die Breite des Spielfeldes wird in die Datei geschrieben.
			 */
			writer.write("Width ");
			writer.write("" + OptionsController.getInstance().getWidth());
			writer.write("\r\n");

			/*
			 * Die Hoehe des Spielfeldes wird in die Datei geschrieben.
			 */
			writer.write("Height ");
			writer.write("" + OptionsController.getInstance().getHeight());
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste failShoots des Hosts
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldMiss ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getFailShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getFailShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getFailShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste successfulShoots des
			 * Hosts werden in die Datei geschrieben.
			 */
			writer.write("FieldHit ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getSuccessfulShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSuccessfulShoots().get(i)
								.getX() + " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSuccessfulShoots().get(i)
								.getY() + " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste sunkFields des Hosts
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldSunk ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getSunkShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSunkShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSunkShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste failShoots des Clients
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldMissNetwork ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getFailShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getFailShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getFailShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste successfulShoots des
			 * Clients werden in die Datei geschrieben.
			 */
			writer.write("FieldHitNetwork ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getSuccessfulShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSuccessfulShoots().get(i)
								.getX() + " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSuccessfulShoots().get(i)
								.getY() + " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste sunkShoots des Clients
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldSunkNetwork ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getSunkShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSunkShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSunkShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der verbleibenden Schiffe des Hosts werden in
			 * die Datei geschrieben.
			 */
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getAreaFactory().getArea().getShList().size(); i++) {
				writer.write("Ship ");
				for (int j = 0; j < OptionsController.getInstance().getGame()
						.getPlayer1().getAreaFactory().getArea().getShList()
						.get(i).getFields().size(); j++) {
					writer.write(""
							+ OptionsController.getInstance().getGame()
									.getPlayer1().getAreaFactory().getArea()
									.getShList().get(i).getFields().get(j)
									.getX() + " ");
					writer.write(""
							+ OptionsController.getInstance().getGame()
									.getPlayer1().getAreaFactory().getArea()
									.getShList().get(i).getFields().get(j)
									.getY() + " ");
				}
				writer.write("\r\n");
			}

			/*
			 * Falls der Host als letzter an der Reihe war, wird "ActivePlayer"
			 * in die Datei geschrieben.
			 */
			if (OptionsController.getInstance().getGame().getActivePlayer() == OptionsController
					.getInstance().getGame().getPlayer1()) {
				writer.write("ActivePlayer");
			}

			writer.write("\r\n");
			writer.write("\r\n");

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Die Methode speichert ein laufendes Spiel im GameType
	 * "Spieler gegen Spieler" und im NetworkType "Client" in uebergebene Datei.
	 * 
	 * @param filename
	 */
	public void saveClient(String filename) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			/*
			 * Es wird der GameType in die Datei geschrieben.
			 */
			writer.write("Gametype ");
			writer.write(OptionsController.getInstance().getGameType());
			writer.write("\r\n");

			/*
			 * Es wird der NetworkType in die Datei geschrieben.
			 */
			writer.write("NetworkType ");
			writer.write(OptionsController.getInstance().getNetworkType());
			writer.write("\r\n");

			/*
			 * Es wird die HostIP in die Datei geschrieben.
			 */
			writer.write("HostIP ");
			writer.write(OptionsController.getInstance().getIp());
			writer.write("\r\n");

			/*
			 * Es wird die Breite des Spielfeldes in die Datei geschrieben.
			 */
			writer.write("Width ");
			writer.write("" + OptionsController.getInstance().getWidth());
			writer.write("\r\n");

			/*
			 * Es wird die Hoehe des Spielfeldes in die Datei geschrieben.
			 */
			writer.write("Height ");
			writer.write("" + OptionsController.getInstance().getHeight());
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste failShoots des Clients
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldMiss ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getFailShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getFailShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getFailShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste successfulShoots des
			 * Clients werden in die Datei geschrieben.
			 */
			writer.write("FieldHit ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getSuccessfulShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSuccessfulShoots().get(i)
								.getX() + " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSuccessfulShoots().get(i)
								.getY() + " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste sunkShoots des Clients
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldSunk ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getSunkShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSunkShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer2().getSunkShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste failShoots des Hosts
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldMissNetwork ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getFailShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getFailShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getFailShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste successfulShoots des
			 * Hosts werden in die Datei geschrieben.
			 */
			writer.write("FieldHitNetwork ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getSuccessfulShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSuccessfulShoots().get(i)
								.getX() + " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSuccessfulShoots().get(i)
								.getY() + " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der Felder aus der Liste sunkShoots des Hosts
			 * werden in die Datei geschrieben.
			 */
			writer.write("FieldSunkNetwork ");
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer1().getSunkShoots().size(); i++) {
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSunkShoots().get(i).getX()
						+ " ");
				writer.write(""
						+ OptionsController.getInstance().getGame()
								.getPlayer1().getSunkShoots().get(i).getY()
						+ " ");
			}
			writer.write("\r\n");

			/*
			 * Alle Koordinaten der verbleibenden Schiffe des Clients werden in
			 * die Datei geschrieben.
			 */
			for (int i = 0; i < OptionsController.getInstance().getGame()
					.getPlayer2().getAreaFactory().getArea().getShList().size(); i++) {
				writer.write("Ship ");
				for (int j = 0; j < OptionsController.getInstance().getGame()
						.getPlayer2().getAreaFactory().getArea().getShList()
						.get(i).getFields().size(); j++) {
					writer.write(""
							+ OptionsController.getInstance().getGame()
									.getPlayer2().getAreaFactory().getArea()
									.getShList().get(i).getFields().get(j)
									.getX() + " ");
					writer.write(""
							+ OptionsController.getInstance().getGame()
									.getPlayer2().getAreaFactory().getArea()
									.getShList().get(i).getFields().get(j)
									.getY() + " ");
				}
				writer.write("\r\n");
			}

			/*
			 * Falls der Client als letzter an der Reihe war, wird
			 * "ActivePlayer" in die Datei geschrieben.
			 */
			if (OptionsController.getInstance().getGame().getActivePlayer() == OptionsController
					.getInstance().getGame().getPlayer2()) {
				writer.write("ActivePlayer");
			}

			writer.write("\r\n");
			writer.write("\r\n");

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
