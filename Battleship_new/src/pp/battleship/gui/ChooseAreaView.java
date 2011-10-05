package pp.battleship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import pp.battleship.bs.KI;
import pp.battleship.bs.KINetwork;

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
public class ChooseAreaView extends JInternalFrame {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der ein Auswahlfenster
	 * enthaelt, mit dem man die Hoehe und Breite des Spielfeldes festlegen
	 * kann.<br>
	 */
	public ChooseAreaView() {
		super();

		/*
		 * Groesse des Frames: Breite 300px, Hoehe: 200px
		 */
		setSize(300, 200);

		/*
		 * Das Leyout wird auf "null" gesetzt.
		 */
		getContentPane().setLayout(null);

		/*
		 * JLabel mit dem Text "Waehlen Sie die Spielfeldgroesse" wird
		 * initialisiert und mit setBounds() positioniert.
		 */
		JLabel label = new JLabel(" Waehlen Sie die Spielfeldgroesse ");
		label.setBounds(10, 10, 300, 20);
		getContentPane().add(label);

		/*
		 * JLabel mit dem Text "Anzahl der Reihen" wird initialisiert und mit
		 * setBounds() positioniert.
		 */
		label = new JLabel(" Anzahl der Reihen: ");
		label.setBounds(10, 50, 150, 20);
		getContentPane().add(label);

		/*
		 * JSpinner wird initialisiert und mit setBounds() positioniert
		 * Minimalwert: 5 MaximalWert: 30 Stufengroesse: 1
		 */
		SpinnerNumberModel modelReihen = new SpinnerNumberModel(5, 5, 30, 1);
		final JSpinner reihen = new JSpinner(modelReihen);
		reihen.setBounds(170, 50, 50, 20);
		getContentPane().add(reihen);

		/*
		 * JLabel mit dem Text "Anzahl der Spalten" wird initialisiert und mit
		 * setBounds() positioniert.
		 */
		label = new JLabel(" Anzahl der Spalten: ");
		label.setBounds(10, 80, 150, 20);
		getContentPane().add(label);

		/*
		 * JSpinner wird initialisiert und mit setBounds() positioniert
		 * Minimalwert: 5 MaximalWert: 30 Stufengroesse: 1
		 */
		SpinnerNumberModel modelSpalten = new SpinnerNumberModel(5, 5, 30, 1);
		final JSpinner spalten = new JSpinner(modelSpalten);
		spalten.setBounds(170, 80, 50, 20);
		getContentPane().add(spalten);

		/*
		 * JButton mit dem Text "weiter" wird initialisiert und mit setBounds()
		 * positioniert. Es wird ein ActionListener hinzugefuegt. Beim Klicken
		 * des JButtons werden Breite und Hoehe aus den JSpinner ausgelesen und
		 * im OptionsController gesetzt. Es wird eine AreaFactory erzeugt. Im
		 * GameType "Computer gegen Computer" wird eine KINetwork initialisiert
		 * und im OptionsController gespeichert. Im GameType
		 * "Spieler gegen Computer" wird eine KI initialisiert und im
		 * OptionsController gespeichert. Anschließend wird in der MainView das
		 * Objekt ChooseShipView erzeugt.
		 */
		JButton weiterButton = new JButton(" weiter ");
		weiterButton.setBounds(150, 120, 100, 30);
		getContentPane().add(weiterButton);
		weiterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionsController.getInstance().setHeight(
						Integer.parseInt(reihen.getValue().toString()));
				OptionsController.getInstance().setWidth(
						Integer.parseInt(spalten.getValue().toString()));

				OptionsController.getInstance().setAreaFactory();

				if (OptionsController.getInstance().getGameType().equals("cc")) {
					OptionsController.getInstance().setKiNetwork(
							new KINetwork());
				} else if (OptionsController.getInstance().getGameType()
						.equals("sc")) {
					OptionsController.getInstance().setKi(new KI());
				}

				MainView.buildDesktop(new ChooseShipView());
			}
		});

	}
}
