package pp.battleship.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

/**
 * 
 * Diese Klasse ist zur Gestaltung des Spielfeldes.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class MyTableCellRenderer extends
		javax.swing.table.DefaultTableCellRenderer {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		comp.setVisible(false);
		try {
			/*
			 * Falls der Wert ein Element von String ist, wird anhand des Wertes
			 * in der Zelle ueberprueft welche Farbe gesetzt werden soll
			 */
			if (value instanceof String) {
				if (value.equals("w")) {
					comp.setBackground(Color.BLUE);
					comp.setForeground(Color.BLUE);
				} else if (value.equals("s")) {
					comp.setBackground(Color.DARK_GRAY);
					comp.setForeground(Color.DARK_GRAY);
				} else if (value.equals("m")) {
					comp.setBackground(Color.LIGHT_GRAY);
					comp.setForeground(Color.LIGHT_GRAY);
				} else if (value.equals("p")) {
					comp.setBackground(Color.YELLOW);
					comp.setForeground(Color.YELLOW);
				} else if (value.equals("t")) {
					comp.setBackground(Color.RED);
					comp.setForeground(Color.RED);
				} else if (value.equals("v")) {
					comp.setBackground(Color.ORANGE);
					comp.setForeground(Color.ORANGE);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return comp;
	}
}