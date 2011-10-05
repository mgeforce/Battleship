package pp.battleship.gui;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import pp.battleship.bs.Field;
import pp.battleship.bs.FieldFactory;
import pp.battleship.bs.Game;
import pp.battleship.bs.Human;
import pp.battleship.bs.HumanNetwork;
import pp.battleship.bs.IllegalFieldException;
import pp.battleship.bs.NetworkKI;
import pp.battleship.bs.NetworkPlayer;

/**
 * 
 * Diese Klasse erzeugt ein Objekt, welches das Spielfeld enthaelt. Diese Klasse
 * erbt von der Klasse JInternalFrame wodurch eine Anzeige aller Komponenten
 * moeglich wird.<br>
 * <br>
 * 
 * Es wird kein bestimmtes Layout verwendet. Alle Komponenten werden über die
 * Methode setBounds() an bestimmte Positionen positioniert.<br>
 * Die Komponenten werden anschließend dem ContentPane hinzugefügt.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class PlayingFieldView extends JInternalFrame {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Diese Variable dient zur Speicherung des linken, also des eigenen
	 * Spielfeldes
	 */
	private CreateTableView tablePlayer1;

	/**
	 * Diese Variable dient zur Speicherung des rechten, also des gegenerischen
	 * Spielfeldes
	 */
	private CreateTableView tableShoot;

	/**
	 * Die Fieldfactory dient zur Erzeugung von Feldern
	 */
	private FieldFactory fieldFactory;

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der das Spielfeld enthaelt,
	 * auf dem das Spiel ablaeuft.<br>
	 */
	public PlayingFieldView() {
		super();

		/*
		 * Im GameType "Spieler gegen Computer" wird der Menupunkt Speichern
		 * dauerhaft auf true gesetzt.
		 */
		if (OptionsController.getInstance().getGameType().equals("sc"))
			OptionsController.getInstance().getItem().setEnabled(true);

		/*
		 * Groesse des Frames: Breite und Hoehe werden anhand der Groesse der
		 * Spielfelder berechnet
		 */
		setSize(((OptionsController.getInstance().getWidth() * 15 * 2) + 100),
				((OptionsController.getInstance().getHeight() * 16) + 180));
		getContentPane().setLayout(null);

		/*
		 * Es wird ein neues Gameobjekt erstellt und im OptionsController
		 * gespeichert. Außerdem wird die fieldFactory initialisiert.
		 */
		OptionsController.getInstance().setGame(new Game());
		fieldFactory = new FieldFactory();

		/*
		 * Im GameType "Spieler gegen Computer" und "Spieler gegen Spieler" wird
		 * das linke Spielfeld aus dem OptionsController geholt. Dieses enthaelt
		 * alle relevanten Informationen.
		 */
		if (OptionsController.getInstance().getGameType().equals("sc")) {
			tablePlayer1 = OptionsController.getInstance().getTable();
		} else if (OptionsController.getInstance().getGameType().equals("ss")) {
			tablePlayer1 = OptionsController.getInstance().getTable();
		}

		/*
		 * Im GameType "Computer gegen Computer" wird das linke Spielfeld im
		 * OptionsController aus der KINetwork geholt. Dieses enthaelt alle
		 * relevanten Informationen.
		 */
		else if (OptionsController.getInstance().getGameType().equals("cc")) {
			tablePlayer1 = OptionsController.getInstance().getKiNetwork()
					.getTableKI();
		}

		/*
		 * Fuer das rechte Schussfeld wird eine neue Tabelle angelegt
		 */
		tableShoot = new CreateTableView();

		/*
		 * Beide Tabellen werden auf dem JInternalFrame positioniert. Die
		 * Positionen werden anhand der Groessen der Spielfelder berechnet.
		 */
		tablePlayer1.setBounds(5, 5, (OptionsController.getInstance()
				.getWidth() * 15),
				(OptionsController.getInstance().getHeight() * 16));
		getContentPane().add(tablePlayer1);

		tableShoot.setBounds(
				(OptionsController.getInstance().getWidth() * 15) + 15, 5,
				(OptionsController.getInstance().getWidth() * 15),
				(OptionsController.getInstance().getHeight() * 16));
		getContentPane().add(tableShoot);

		/*
		 * Es wird eine JTextArea mi einem JScrollPane initialisiert. Diese
		 * dienen zur Anzeige von relevanten Informationen. Am Anfang wird
		 * angezeigt, welcher Spieler anfangen darf. Außerdem erhaelt der
		 * Spieler eine Information, ob er einen Treffer gesetzt hat, oder ob
		 * ein Schiff versenkt wurde. Beide Objekte werden im OptionsController
		 * gespeicher, damit die Game Klasse ebenfalls Zugriff hat.
		 */
		JScrollPane scrollPane = new JScrollPane();
		JTextArea textArea = new JTextArea(5, 10);
		textArea.setFont(new Font("Verdana", Font.PLAIN, 10));
		scrollPane.getViewport().add(textArea);
		scrollPane.setBounds(5,
				(OptionsController.getInstance().getHeight() * 16) + 10, 200,
				80);
		getContentPane().add(scrollPane);

		textArea.append("Das linke Feld ist Ihres.\n");
		if (OptionsController.getInstance().getGameType().equals("sc"))
			textArea.append("Sie beginnen das Spiel.\n"
					+ "Geben Sie einen Schuss ab,\n"
					+ "indem Sie in eine Zelle des \n"
					+ "rechten Spielfeldes klicken.\n");
		if (OptionsController.getInstance().getGameType().equals("ss")) {
			if (OptionsController.getInstance().getNetworkType().equals("host"))
				textArea.append("Ihr Gegner beginnt das Spiel.\n");
			else
				textArea.append("Sie beginnen das Spiel.\n"
						+ "Geben Sie einen Schuss ab,\n"
						+ "indem Sie in eine Zelle des \n"
						+ "rechten Spielfeldes klicken.\n");
		}

		OptionsController.getInstance().setTextArea(textArea);
		OptionsController.getInstance().setScrollPane(scrollPane);

		/*
		 * Hier wird die setup Methode der Game Klasse aufgerufen. Diese legt
		 * fest, welche Spieler spielen und welcher Spieler beginnen darf (der
		 * Spieler, welcher als zweiter der Methode uebergeben wird, darf
		 * beginnen).
		 * 
		 * Im GameType "Spieler gegen Computer" wird der setup Methode die im
		 * OptionsController gespeicherte KI und ein neu erzeugtes Human Objekt
		 * uebergeben.
		 */
		if (OptionsController.getInstance().getGameType().equals("sc")) {
			OptionsController.getInstance().getGame().setup(
					OptionsController.getInstance().getKi(),
					new Human(OptionsController.getInstance().getAreaFactory(),
							tableShoot));
			OptionsController.getInstance().getKi().setTable(tablePlayer1);

			/*
			 * Wenn ein Human Player im Spiel beteiligt ist, muss ein
			 * MouseListener initialisiert werden. Dieser dient zum Abgeben von
			 * Schuessen.
			 */
			createMouseListener(tableShoot);
		}

		/*
		 * Im GameType "Spieler gegen Spieler" muss noch zwischen Host und
		 * Client unterschieden werden.
		 * 
		 * Bei Host wird der setup Methode ein neues HumanNetwork Objekt und ein
		 * neues NetworkPlayer Objekt uebergeben.
		 * 
		 * Bei Client wird der setup Methode ein neues NetworkPlayer Objekt und
		 * ein neues HumanNetwork Objekt uebergeben.
		 */
		else if (OptionsController.getInstance().getGameType().equals("ss")) {
			if (OptionsController.getInstance().getNetworkType().equals("host")) {

				OptionsController.getInstance().getGame().setup(
						new HumanNetwork(OptionsController.getInstance()
								.getAreaFactory(), tableShoot),
						new NetworkPlayer(tablePlayer1));
			} else {
				OptionsController.getInstance().getGame().setup(
						new NetworkPlayer(tablePlayer1),
						new HumanNetwork(OptionsController.getInstance()
								.getAreaFactory(), tableShoot));
			}

			/*
			 * Wenn ein Human Player im Spiel beteiligt ist, muss ein
			 * MouseListener initialisiert werden. Dieser dient zum Abgeben von
			 * Schuessen.
			 */
			createMouseListener(tableShoot);
		}

		/*
		 * Im GameType "Computer gegen Computer" muss noch zwischen Host und
		 * Client unterschieden werden.
		 * 
		 * Bei Host wird der setup Methode das im OptionController gespeicherte
		 * KiNetwork Objekt und ein neues NetworkKi Objekt uebergeben.
		 * 
		 * Bei Client wird der setup Methode ein neues NetworkKi Objekt und das
		 * im OptionController gespeicherte KiNetwork Objekt uebergeben.
		 */
		else if (OptionsController.getInstance().getGameType().equals("cc")) {
			// wenn dieses programm host ist faengt der netzwerkspieler an
			if (OptionsController.getInstance().getNetworkType().equals("host")) {
				OptionsController.getInstance().getKiNetwork().setTableShoot(
						tableShoot);
				OptionsController.getInstance().getGame().setup(
						OptionsController.getInstance().getKiNetwork(),
						new NetworkKI(tablePlayer1));
			} else {
				OptionsController.getInstance().getKiNetwork().setTableShoot(
						tableShoot);
				OptionsController.getInstance().getGame().setup(
						new NetworkKI(tablePlayer1),
						OptionsController.getInstance().getKiNetwork());
			}
		}

		OptionsController.getInstance().getGame().startAGame();

	}

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der das Spielfeld enthaelt,
	 * auf dem das Spiel ablaeuft.<br>
	 * Dieser Konstruktor wird beim laden von vorhandnen Spielstaenden
	 * aufgerufen.
	 * 
	 * @param tablePlayer
	 * @param tableKI
	 */
	public PlayingFieldView(CreateTableView tablePlayer, CreateTableView tableKI) {
		super();

		/*
		 * Groesse des Frames: Breite und Hoehe werden anhand der Groesse der
		 * Spielfelder berechnet
		 */
		setSize(((OptionsController.getInstance().getWidth() * 15 * 2) + 50),
				((OptionsController.getInstance().getHeight() * 16) + 180));
		getContentPane().setLayout(null);

		/*
		 * Die fieldFactory wird initialisiert.
		 */
		fieldFactory = new FieldFactory();

		/*
		 * Beide uebergebenen Tabellen werden ueber setBounds() anhand der
		 * Groesse der Spielfelder entsprechend positioniert.
		 */
		tableKI.setBounds(5, 5,
				(OptionsController.getInstance().getWidth() * 15),
				(OptionsController.getInstance().getHeight() * 16));
		getContentPane().add(tableKI);

		tablePlayer.setBounds(
				(OptionsController.getInstance().getWidth() * 15) + 15, 5,
				(OptionsController.getInstance().getWidth() * 15),
				(OptionsController.getInstance().getHeight() * 16));
		getContentPane().add(tablePlayer);

		/*
		 * Es wird eine JTextArea mi einem JScrollPane initialisiert. Diese
		 * dienen zur Anzeige von relevanten Informationen. Am Anfang wird
		 * angezeigt, welcher Spieler anfangen darf. Außerdem erhaelt der
		 * Spieler eine Information, ob er einen Treffer gesetzt hat, oder ob
		 * ein Schiff versenkt wurde. Beide Objekte werden im OptionsController
		 * gespeicher, damit die Game Klasse ebenfalls Zugriff hat.
		 */
		JScrollPane scrollPane = new JScrollPane();
		JTextArea textArea = new JTextArea(5, 10);
		textArea.setFont(new Font("Verdana", Font.PLAIN, 10));
		scrollPane.getViewport().add(textArea);
		scrollPane.setBounds(5,
				(OptionsController.getInstance().getHeight() * 16) + 10, 200,
				80);
		getContentPane().add(scrollPane);

		textArea.append("Das linke Feld ist Ihres.\n");
		if (OptionsController.getInstance().getGameType().equals("sc"))
			textArea.append("Sie beginnen das Spiel.\n"
					+ "Geben Sie einen Schuss ab,\n"
					+ "indem Sie in eine Zelle des \n"
					+ "rechten Spielfeldes klicken.\n");
		if (OptionsController.getInstance().getGameType().equals("ss")) {
			if (OptionsController.getInstance().getNetworkType().equals("host")) {
				if (OptionsController.getInstance().getGame().getActivePlayer() == OptionsController
						.getInstance().getGame().getPlayer1())
					textArea.append("Sie setzen das Spiel fort.\n"
							+ "Geben Sie einen Schuss ab,\n"
							+ "indem Sie in eine Zelle des \n"
							+ "rechten Spielfeldes klicken.\n");
				else
					textArea.append("Ihr Gegner setzt das Spiel fort.\n");
			}

			else {
				if (OptionsController.getInstance().getGame().getActivePlayer() == OptionsController
						.getInstance().getGame().getPlayer2())
					textArea.append("Sie setzen das Spiel fort.\n"
							+ "Geben Sie einen Schuss ab,\n"
							+ "indem Sie in eine Zelle des \n"
							+ "rechten Spielfeldes klicken.\n");
				else
					textArea.append("Ihr Gegner setzt das Spiel fort.\n");
			}
		}

		OptionsController.getInstance().setTextArea(textArea);
		OptionsController.getInstance().setScrollPane(scrollPane);

		/*
		 * Wenn ein Human Player im Spiel beteiligt ist, muss ein MouseListener
		 * initialisiert werden. Dieser dient zum Abgeben von Schuessen.
		 */
		createMouseListener(tablePlayer);

		OptionsController.getInstance().getGame().startAGame();

	}

	/**
	 * Diese Methode initialisiert einen MouseListener fuer die uebergebene
	 * Tabelle
	 * 
	 * @param tableListener
	 */
	private void createMouseListener(final CreateTableView tableListener) {
		tableListener.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				Point p = event.getPoint();
				int row = tableListener.rowAtPoint(p);
				int col = tableListener.columnAtPoint(p);

				if (tableListener.getValueAt(row, col).equals("w")) {
					/*
					 * Es wird ein Feld mit den Koordinaten des MouseListeners
					 * erzeugt.
					 */
					Field shootField = null;
					try {
						shootField = fieldFactory.createField(col, row);
					} catch (IllegalFieldException e) {
						e.printStackTrace();
					}

					/*
					 * Wenn es erlaubt ist und ein Human Player an der Reihe
					 * ist, dann wird das Feld, der Game Klasse uebergeben.
					 */

					if (OptionsController.getInstance().isAllowed()) {
						if (OptionsController.getInstance().getGame()
								.getActivePlayer().getClass() == Human.class
								|| OptionsController.getInstance().getGame()
										.getActivePlayer().getClass() == HumanNetwork.class) {
							OptionsController.getInstance().getGame()
									.setShootFieldHuman(shootField);
							OptionsController.getInstance().getGame()
									.setCheckInput(true);
						}
					}
				}

			}
		});
	}

}
