package pp.battleship.gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import pp.battleship.saveandload.Load;
import pp.battleship.saveandload.Save;

/**
 * 
 * Diese Klasse erzeugt ein Objekt das die GUI enthaelt.<br>
 * <br>
 * 
 * Es wird ein MainFrame erzeugt, welcher ein JDesktopPane enthaelt. In diesem
 * werden alle weiteren JInternalFrames dargestellt. <br>
 * <br>
 * 
 * Alle Klassenvariablen sind als private static deklariert.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloexhr
 * 
 */
public class MainView {

	/**
	 * Diese Variable ist für den momentan angezeigten JInternalFrame, welcher
	 * beim naechsten Fenster über dispose() geschlossen wird.
	 */
	private static JInternalFrame temp = null;

	/**
	 * Diese Variable ist das JDesktopPane welches dem MainFrame hinzugefuegt
	 * wird.
	 */
	private static JDesktopPane desktop;

	/**
	 * Diese Variable ist der MainFrame
	 */
	private static JFrame shipFrame;

	/**
	 * Der Konstruktor erzeugt ein JFrame, der JDesktopPane enthaelt, mit dem
	 * alle weiteren JInternalFrame dargestellt werden.<br>
	 */
	public MainView() {
		/*
		 * Hintergrundbild
		 */
		final ImageIcon icon = createImageIcon(
				"/pp/battleship/img/battleship.jpg", "Hintergrund");

		desktop = new JDesktopPane() {
			/**
			 * Serial Version User ID
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Diese Methode fuegt das Hintergrundbild hinzu.<br>
			 * 
			 * @param g
			 */
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(), 0, 0, this);
			}
		};

		/*
		 * JFrame mit dem Titel "Schiffe versenken" wird erzeugt.
		 */
		JFrame shipFrame = new JFrame("Schiffe versenken");
		shipFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * JMenuBar wird erzeugt und ueber die Methode buildMenuBar() mit den
		 * benoetigten Komponenten initialisiert.
		 */
		JMenuBar bar = buildMenuBar();
		shipFrame.setJMenuBar(bar);

		/*
		 * JDesktopPane desktop wird dem JFrame shipFrame hinzugefuegt
		 */
		shipFrame.add(desktop);

		/*
		 * Groesse des Frames: Breite 1000px, Hoehe: 700px
		 */
		shipFrame.setSize(1000, 700);
		shipFrame.setLocationByPlatform(true);
		shipFrame.setVisible(true);
	}

	/**
	 * Diese Methode erzeugt eine JMenuBar und initialisiert sie mit den
	 * benoetigten Komponenten (Neues Spiel, Speichern, Laden, Beenden).<br>
	 * 
	 * @return bar
	 */
	public static JMenuBar buildMenuBar() {
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Datei");

		/*
		 * Menueintrag "Neues Spiel": Beim ersten Aufruf wird ueber
		 * getInstance() ein neues Singleton des OptionControllers
		 * initialisiert, ansonsten werden vorhandene Werte ueber die
		 * reset-Methode zurueckgesetzt.
		 */
		JMenuItem item = new JMenuItem("Neues Spiel");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionsController.getInstance().resetOptionsController();
				buildDesktop(new ChooseGameTypeView());
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke('N',
				InputEvent.CTRL_DOWN_MASK));
		menu.add(item);

		/*
		 * Menueintrag "Laden": Hier wird beim Klick ein JFileChooser geoeffnet,
		 * indem sich die Save-Datei auswaehlen laesst. Es wird ein neues Load
		 * Objekt angelegt und ueber die Methode load, welcher der Pfad und
		 * Dateiname uebergeben wird, wird das Laden ausgefuehrt.
		 */
		item = new JMenuItem("Laden");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Load load = new Load();

				JFileChooser fcLaden = new JFileChooser();

				FileFilter filter = new FileFilter() {
					public boolean accept(File f) {
						return f.isDirectory()
								|| f.getName().toLowerCase().endsWith(".txt");
					}

					public String getDescription() {
						return "TXT";
					}

				};
				fcLaden.setFileFilter(filter);
				int state = fcLaden.showOpenDialog(null);

				if (state == JFileChooser.APPROVE_OPTION) {
					File file = fcLaden.getSelectedFile();
					load.load(file.getAbsolutePath());
				}
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke('L',
				InputEvent.CTRL_DOWN_MASK));
		menu.add(item);

		/*
		 * Menueintrag "Speichern": Hier wird ein JFileChooser geoeffnet, indem
		 * die zu speichernde Datei gesetzt werden kann. Der Menueintrag wird
		 * standardmaessig deaktiviert. Er wird nur aktiviert, falls ein Spieler
		 * auch speichern darf. Dazu wird er im OptionsController hinterlegt.
		 * 
		 * Es wird ein Save Objekt angelegt. Es wird der GameType und evtl. der
		 * NetworkType ueberprueft. Anhand dieser Ueberpruefung wird die
		 * jeweilige speichern-Methode aufgerufen.
		 */
		item = new JMenuItem("Speichern");
		item.setEnabled(false);
		OptionsController.getInstance().setItem(item);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Save save = new Save();

				JFileChooser fcSave = new JFileChooser();

				FileFilter filter = new FileFilter() {
					public boolean accept(File f) {
						return f.isDirectory()
								|| f.getName().toLowerCase().endsWith(".txt");
					}

					public String getDescription() {
						return "TXT";
					}

				};
				fcSave.setFileFilter(filter);
				int state = fcSave.showSaveDialog(null);

				if (state == JFileChooser.APPROVE_OPTION) {
					File file = fcSave.getSelectedFile();
					long timestamp = System.currentTimeMillis();
					String filename = file.getAbsolutePath() + "-" + timestamp
							+ ".txt";
					if (OptionsController.getInstance().getGameType().equals(
							"sc"))
						save.saveSC(filename);
					if (OptionsController.getInstance().getGameType().equals(
							"ss")) {
						OptionsController.getInstance().getNetwork().sendSave(
								timestamp);
						if (OptionsController.getInstance().getNetworkType()
								.equals("host"))
							save.saveHost(filename);
						else if (OptionsController.getInstance()
								.getNetworkType().equals("client"))
							save.saveClient(filename);
					}
				}

			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_DOWN_MASK));
		menu.add(item);

		/*
		 * Menueintrag "Beenden": Beendet das Spiel
		 */
		item = new JMenuItem("Beenden");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shutdown();
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke('B',
				InputEvent.CTRL_DOWN_MASK));
		menu.add(item);

		bar.add(menu);

		return bar;
	}

	/**
	 * Diese Methode schließt den momentan angezeigten JInternalFrame ueber
	 * dispose() und fuegt den uebergebeben JInternalFrame dem JDesktopPane
	 * hinzu.<br>
	 * 
	 * @param frame
	 */
	public static void buildDesktop(JInternalFrame frame) {
		if (temp != null)
			temp.dispose();
		desktop.add(frame);
		frame.setVisible(true);
		temp = frame;
	}

	/**
	 * Diese Methode zeigt eine Information, dass auf eine Client Verbindung
	 * gewartet werden muss
	 */
	public static void buildWaitingFrame() {

		JOptionPane
				.showMessageDialog(
						shipFrame,
						"Waiting for Client Connection...\nSie muessen mit OK bestaetigen",
						"Information", JOptionPane.PLAIN_MESSAGE);

	}

	/**
	 * Diese Methode gibt den MainFrame zurueck
	 * 
	 * @return shipFrame
	 */
	public static JFrame getShipFrame() {
		return shipFrame;
	}

	/**
	 * Diese Methode schliesst das komplette Programm.<br>
	 */
	public static void shutdown() {
		System.exit(0);
	}

	/**
	 * Diese Methode gibt eine Warnung aus, wenn keine gueltigen Savedateien
	 * geladen wurden
	 */
	public static void showFileErrorBox() {
		JOptionPane.showMessageDialog(shipFrame,
				"Sie haben keine gueltige Save Datei ausgewaehlt!!!",
				"File Error", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Diese Methoden erstellt anhand uebergebenem Pfad und Beschreibung ein
	 * neues ImageIcon
	 * 
	 * @param path
	 * @param description
	 * @return
	 */
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}
