package pp.battleship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import pp.battleship.bs.KINetwork;
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
public class ChooseNetworkView extends JInternalFrame {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Diese JTextArea ist für die Eingabe der IP-Adresse
	 */
	private JTextArea ipInput = new JTextArea("localhost");

	/**
	 * Dieser JButton ist für die Bestaetigung des Auswahlfensters
	 */
	private JButton weiterButton;

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der ein Auswahlfenster
	 * enthaelt, mit dem man den GameType festlegen kann.<br>
	 */
	public ChooseNetworkView() {
		super();

		/*
		 * Groesse des Frames: Breite 300px, Hoehe: 230px
		 */
		setSize(300, 230);

		/*
		 * Das Leyout wird auf "null" gesetzt.
		 */
		getContentPane().setLayout(null);

		/*
		 * JLabel mit dem Text "Waehlen Sie Ihren Netzwerktyp" wird
		 * initialisiert und mit setBounds() positioniert.
		 */
		JLabel label = new JLabel(" Waehlen Sie Ihren Netzwerktyp: ");
		label.setBounds(10, 10, 300, 20);
		getContentPane().add(label);

		/*
		 * JRadioButton mit dem Text "Host" wird initialisiert und mit
		 * setBounds() positioniert.
		 */
		JRadioButton host = new JRadioButton(" Host ");
		host.setActionCommand("host");
		host.setBounds(40, 40, 100, 20);
		getContentPane().add(host);

		/*
		 * JRadioButton mit dem Text "Client" wird initialisiert und mit
		 * setBounds() positioniert.
		 */
		JRadioButton client = new JRadioButton(" Client ");
		client.setActionCommand("client");
		client.setBounds(150, 40, 100, 20);
		getContentPane().add(client);

		/*
		 * Die JRadioButtons werden zu einer Auswahlgruppe zusammengefasst
		 */
		ButtonGroup auswahl = new ButtonGroup();
		auswahl.add(host);
		auswahl.add(client);

		/*
		 * Für jeden JRadioButton wird ein ActionListener initialisiert. Dieser
		 * setzt bei Auswahl den jeweiligen Netzwerktyp im OptionsController und
		 * aktiviert den weiterButton
		 */
		host.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				OptionsController.getInstance().setNetworkType("host");
				weiterButton.setEnabled(true);
			}
		});
		client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				OptionsController.getInstance().setNetworkType("client");
				weiterButton.setEnabled(true);
			}
		});

		/*
		 * JLabel mit dem Text "Host-IP (nur bei Client erforderlich):" wird
		 * initialisiert und mit setBounds() positioniert.
		 */
		JLabel ipLabel = new JLabel(" Host-IP (nur bei Client erforderlich): ");
		ipLabel.setBounds(10, 80, 250, 20);
		getContentPane().add(ipLabel);

		/*
		 * Die JTextArea wird auf editierbar geschalten und mit setBounds()
		 * positioniert.
		 */
		ipInput.setEditable(true);
		ipInput.setBounds(10, 110, 230, 20);
		getContentPane().add(ipInput);

		/*
		 * JButton mit dem Text "weiter" wird initialisiert und mit setBounds()
		 * positioniert. Es wird ein ActionListener hinzugefuegt. Beim Klicken
		 * des JButtons wird der Netzwerktyp überprüft.
		 * 
		 * Bei "host" wird in der MainView das Objekt ChooseAreaView erzeugt.
		 * 
		 * Bei "client" wird ein neues Netzwerkobjekt erzeugt und im
		 * OptionsController gesetzt. Über dieses Netzwerkobjekt wird eine
		 * Client Connection erzeugt (Host-Ip wird aus der JTextArea
		 * ausgelesen). Über die Methode getStartUpData() werden die vom Host
		 * übergebenen Daten ausgelesen und im OptionsController gesetzt. Bei
		 * "Spieler gegen Spieler" wird in der MainView das Objekt ShipSetView
		 * erzeugt. Bei "Computer gegen Computer" wird im OptionsController eine
		 * neue KINetwork erzeugt, welche über setShips() ihre Schiffe
		 * platziert. Anschließend wird in der MainView das Objekt
		 * PlayingFieldView erzeugt.
		 */
		weiterButton = new JButton("weiter");
		weiterButton.setBounds(150, 140, 100, 30);
		weiterButton.setEnabled(false);
		getContentPane().add(weiterButton);
		weiterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OptionsController.getInstance().getNetworkType().equals(
						"host")) {
					MainView.buildDesktop(new ChooseAreaView());
				} else if (OptionsController.getInstance().getNetworkType()
						.equals("client")) {
					OptionsController.getInstance().setNetwork(new Network());
					OptionsController.getInstance().getNetwork()
							.createClientConnection(ipInput.getText());
					OptionsController.getInstance().setIp(ipInput.getText());
					OptionsController.getInstance().getNetwork()
							.getStartUpData();

					OptionsController.getInstance().setShipCount(
							OptionsController.getInstance().getShipType()
									.size());
					OptionsController.getInstance().setAreaFactory();

					if (OptionsController.getInstance().getGameType().equals(
							"ss"))
						MainView.buildDesktop(new ShipSetView());
					if (OptionsController.getInstance().getGameType().equals(
							"cc")) {
						OptionsController.getInstance().setKiNetwork(
								new KINetwork());

						OptionsController.getInstance().setShipCount(
								OptionsController.getInstance().getShipTypeKI()
										.size());

						OptionsController.getInstance().getKiNetwork()
								.setShips();

						MainView.buildDesktop(new PlayingFieldView());
					}

				}
			}
		});

	}

}
