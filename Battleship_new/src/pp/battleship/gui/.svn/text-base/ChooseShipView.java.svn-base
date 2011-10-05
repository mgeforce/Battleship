package pp.battleship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pp.battleship.network.Network;

/**
 * 
 * Diese Klasse erzeugt ein Objekt das ein Auswahlfenster enthaelt. Diese Klasse
 * erbt von der Klasse JInternalFrame wodurch eine Anzeige aller Komponenten
 * moeglich wird.<br>
 * <br>
 * 
 * Es wird kein bestimmtes Layout verwendet. Alle Komponenten werden über die
 * Methode setBounds() an bestimmte Positionen positioniert.<br>
 * Die Komponenten werden anschließend dem ContentPane hinzugefügt.<br>
 * <br>
 * 
 * Alle Klassenvariablen sind als private deklariert.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class ChooseShipView extends JInternalFrame {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * In dieser Liste werden alle erzeugten JSpinner gespeichert
	 */
	private List<JSpinner> spinners = new ArrayList<JSpinner>();

	/**
	 * Diese Variable ist für maximale Auswahl an Schiffen.
	 */
	private int belegungsfaktor;

	/**
	 * Dieser JButton ist für die Bestaetigung des Auswahlfensters
	 */
	private JButton weiterButton;

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der ein Auswahlfenster
	 * enthaelt, mit dem man den GameType festlegen kann.<br>
	 */
	public ChooseShipView() {
		super();

		/*
		 * Der Belegungsfaktor bestimmt, wieviele Schiffe gesetzt werden duerfen
		 */
		belegungsfaktor = (int) (OptionsController.getInstance().getHeight()
				* OptionsController.getInstance().getWidth() * 0.3);

		/*
		 * Hier wird berechnet wie lang das groesste Schiff sein darf
		 */
		double max = OptionsController.getInstance().getHeight() < OptionsController
				.getInstance().getWidth() ? OptionsController.getInstance()
				.getWidth() : OptionsController.getInstance().getHeight();
		int maxLengthShip = (int) Math.ceil(max / 2.0);

		/*
		 * Groesse des Frames: Breite 300px, Hoehe: wird anhand der größten
		 * Schiffes berechnet
		 */
		setSize(300, ((maxLengthShip * 30) + 120));

		/*
		 * Das Leyout wird auf "null" gesetzt.
		 */
		getContentPane().setLayout(null);

		/*
		 * JLabel mit dem Text "Waehlen Sie die Anzahl Ihrer Schiffe" wird
		 * initialisiert und mit setBounds() positioniert.
		 */
		JLabel label = new JLabel(" Waehlen Sie die Anzahl Ihrer Schiffe ");
		label.setBounds(10, 10, 300, 20);
		getContentPane().add(label);

		/*
		 * Die for-Schleife laeuft von 2 bis zur maximalen Laenge eines Schiffes
		 */
		for (int i = 2; i <= maxLengthShip; i++) {
			/*
			 * JLabel mit dem Text "Schiffe der Laenge ..." wird initialisiert
			 * und mit setBounds() positioniert.
			 */
			label = new JLabel(" Schiffe der Laenge " + i + " :");
			label.setBounds(20, (((i - 1) * 30) + 20), 180, 20);
			getContentPane().add(label);

			/*
			 * JSpinner wird initialisiert und mit setBounds() positioniert
			 * Minimalwert: 0 MaximalWert: 9 Stufengroesse: 1
			 * 
			 * Der JSpinner wird mit einem changeListener initialisiert. Dieser
			 * aktiviert den weiterButton sobald ein Schiff ausgewaehlt wurde
			 */
			final SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 9, 1);
			final JSpinner anzahl = new JSpinner(model);
			anzahl.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					if (Integer.parseInt(model.getValue().toString()) > 0)
						weiterButton.setEnabled(true);
				}
			});
			anzahl.setBounds(200, (((i - 1) * 30) + 20), 40, 20);
			getContentPane().add(anzahl);

			/*
			 * Der JSpinner wird zur Liste spinners hinzugefuegt
			 */
			spinners.add(anzahl);
		}

		/*
		 * JButton mit dem Text "weiter" wird initialisiert und mit setBounds()
		 * positioniert. Es wird ein ActionListener hinzugefuegt. Beim Klicken
		 * wird anhand des Belegungsfaktor überprüft ob nicht zuviele Schiffe
		 * ausgewaehlt wurde.
		 * 
		 * Bei GameType "Spieler gegen Spieler wird im OptionsController eine
		 * neues Netzwerkobjekt erzeugt. Über die Methode createHostConnection()
		 * wird eine Host Connection erzeugt. Anschließend wird auf eine
		 * Clientverbindung gewartet. Es werden die StartUpDaten übertragen. In
		 * der MainView wird ein neues Objekt ShipSetView erzeugt.
		 * 
		 * Bei GameType "Spieler gegen Computer" wird in der MainView ein neues
		 * Objekt ShipSetView erzeugt.
		 * 
		 * Bei GameType "Computer gegen Computer" wird im OptionsController eine
		 * neues Netzwerkobjekt erzeugt. Über die Methode createHostConnection()
		 * wird eine Host Connection erzeugt. Anschließend wird auf eine
		 * Clientverbindung gewartet. Es werden die StartUpDaten übertragen. Die
		 * im OptionsController gespeicherte KI setzt über die Methode
		 * setShips() ihre Schiffe. Anschließend wird in der MainView ein neues
		 * Objekt PlayingFieldView erzeugt.
		 */
		weiterButton = new JButton("weiter");
		weiterButton.setEnabled(false);
		weiterButton.setBounds(150, ((maxLengthShip * 30) + 30), 100, 30);
		getContentPane().add(weiterButton);
		weiterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OptionsController.getInstance().setShipTypePush(spinners,
						belegungsfaktor)) {
					if (OptionsController.getInstance().getGameType().equals(
							"ss")) {

						
						OptionsController.getInstance().setNetwork(
								new Network());
						OptionsController.getInstance().getNetwork()
								.createHostConnection();
						OptionsController.getInstance().getNetwork()
								.submitStartUpData(
										OptionsController.getInstance()
												.getWidth(),
										OptionsController.getInstance()
												.getHeight(),
										OptionsController.getInstance()
												.getShipType());
						OptionsController.getInstance().setShipCount(
								OptionsController.getInstance().getShipType()
										.size());
						MainView.buildDesktop(new ShipSetView());
					}
					if (OptionsController.getInstance().getGameType().equals(
							"sc")) {
						MainView.buildDesktop(new ShipSetView());
					}

					if (OptionsController.getInstance().getGameType().equals(
							"cc")) {
						OptionsController.getInstance().setNetwork(
								new Network());
						OptionsController.getInstance().getNetwork()
								.createHostConnection();
						OptionsController.getInstance().getNetwork()
								.submitStartUpData(
										OptionsController.getInstance()
												.getWidth(),
										OptionsController.getInstance()
												.getHeight(),
										OptionsController.getInstance()
												.getShipType());
						OptionsController.getInstance().setShipCount(
								OptionsController.getInstance().getShipTypeKI()
										.size());
						OptionsController.getInstance().getKiNetwork()
								.setShips();

						MainView.buildDesktop(new PlayingFieldView());
					}

				} else
					OptionsController.getInstance().initializeShipType();
			}
		});

	}
}
