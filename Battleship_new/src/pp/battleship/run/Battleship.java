package pp.battleship.run;

import javax.swing.SwingUtilities;

import pp.battleship.gui.MainView;

/**
 * Diese Klasse dient lediglich zum Starten des Programms.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Battleship {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainView();
			}
		});
	}
}
