package pp.battleship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

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
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class ChooseGameTypeView extends JInternalFrame {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der ein Auswahlfenster
	 * enthaelt, mit dem man den GameType festlegen kann.<br>
	 */
	public ChooseGameTypeView() {
		super();

		/*
		 * Groesse des Frames: Breite 270px, Hoehe: 200px
		 */
		setSize(270, 200);

		/*
		 * Das Leyout wird auf "null" gesetzt.
		 */
		getContentPane().setLayout(null);

		/*
		 * JLabel mit dem Text "Waehlen Sie Ihre Spielvariante" wird
		 * initialisiert und mit setBounds() positioniert.
		 */
		JLabel label = new JLabel(" Waehlen Sie Ihre Spielvariante ");
		label.setBounds(10, 10, 300, 20);
		getContentPane().add(label);

		/*
		 * JRadioButton mit dem Text "Spieler gegen Spieler" wird initialisiert
		 * und mit setBounds() positioniert.
		 */
		JRadioButton ss = new JRadioButton(" Spieler gegen Spieler ");
		ss.setActionCommand("ss");
		ss.setBounds(20, 40, 250, 20);
		getContentPane().add(ss);

		/*
		 * JRadioButton mit dem Text "Spieler gegen Computer" wird initialisiert
		 * und mit setBounds() positioniert.
		 */
		JRadioButton sc = new JRadioButton(" Spieler gegen Computer ");
		sc.setActionCommand("sc");
		sc.setBounds(20, 60, 250, 20);
		getContentPane().add(sc);

		/*
		 * JRadioButton mit dem Text "Computer gegen Computer" wird
		 * initialisiert und mit setBounds() positioniert.
		 */
		JRadioButton cc = new JRadioButton(" Computer gegen Computer ");
		cc.setActionCommand("cc");
		cc.setBounds(20, 80, 250, 20);
		getContentPane().add(cc);

		/*
		 * Die JRadioButtons werden zu einer Auswahlgruppe zusammengefasst
		 */
		ButtonGroup auswahl = new ButtonGroup();
		auswahl.add(ss);
		auswahl.add(sc);
		auswahl.add(cc);

		/*
		 * Für jeden JRadioButton wird ein ActionListener initialisiert. Dieser
		 * setzt bei Auswahl den jeweiligen GameType im OptionsController
		 */
		ss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				OptionsController.getInstance().setGameType(
						event.getActionCommand());
			}
		});
		sc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				OptionsController.getInstance().setGameType(
						event.getActionCommand());
			}
		});
		cc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				OptionsController.getInstance().setGameType(
						event.getActionCommand());
			}
		});

		/*
		 * JButton mit dem Text "weiter" wird initialisiert und mit setBounds()
		 * positioniert. Es wird ein ActionListener hinzugefuegt. Beim Klicken
		 * des JButtons wird der GameType überprüft. Bei
		 * "Spieler gegen Computer" wird in der MainView das Objekt
		 * ChooseAreaView erzeugt. Bei "Spieler gegen Spieler" oder
		 * "Computer gegen Computer" wird in der MainView das Objekt
		 * ChooseNetworkView erzeugt.
		 */
		JButton weiterButton = new JButton(" weiter ");
		weiterButton.setBounds(120, 110, 100, 30);
		getContentPane().add(weiterButton);
		weiterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OptionsController.getInstance().getGameType().equals("sc")) {
					MainView.buildDesktop(new ChooseAreaView());
				}
				if (OptionsController.getInstance().getGameType().equals("ss")
						|| OptionsController.getInstance().getGameType()
								.equals("cc")) {
					MainView.buildDesktop(new ChooseNetworkView());
				}
			}
		});
	}

}
