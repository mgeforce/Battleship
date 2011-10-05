package pp.battleship.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * 
 * Diese Klasse erzeugt ein Objekt das ein Spielfeld enthaelt. Diese Klasse erbt
 * von der Klasse JTable.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class CreateTableView extends JTable {

	/**
	 * Serial Version User ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Der Konstruktor erzeugt ein JTable, das ein Spielfeld enthaelt.<br>
	 */
	public CreateTableView() {
		super();
		/*
		 * Das eindimensionale Objektarray wird Ÿber die im OptionsController
		 * gespeicherte Breite initialisiert.
		 */
		Object[] names = new String[OptionsController.getInstance().getWidth()];

		/*
		 * Das zweidimensionale Objektarray wird Ÿber die im OptionsController
		 * gespeicherte Breite und Hoehe initialisiert.
		 */
		Object[][] data = new Object[OptionsController.getInstance()
				.getHeight()][OptionsController.getInstance().getWidth()];

		/*
		 * Die for-Schleife laeuft von 0 bis zur Breite des Spielfeldes
		 */
		for (int i = 0; i < OptionsController.getInstance().getWidth(); i++) {
			names[i] = "" + i;
		}

		/*
		 * Die for-Schleife laeuft von 0 bis zur Hoehe des Spielfeldes
		 */
		for (int i = 0; i < OptionsController.getInstance().getHeight(); i++) {
			/*
			 * Die for-Schleife laeuft von 0 bis zur Breite des Spielfeldes
			 */
			for (int j = 0; j < OptionsController.getInstance().getWidth(); j++) {
				data[i][j] = "w";
			}
		}

		/*
		 * Durch das TableModel wird die Methode isCellEditable ueberschrieben,
		 * damit die einzelnen Zellen der Tabelle nicht editierbar sind
		 */
		this.setModel(new DefaultTableModel(data, names) {
			/**
			 * Serial Version User ID
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		/*
		 * Es wird ein eigener TableCellRenderer verwendet
		 */
		TableCellRenderer renderer = new MyTableCellRenderer();

		/*
		 * Das Gitternetz der JTable soll angezeigt werden
		 */
		setShowGrid(true);
		setDefaultRenderer(Object.class, renderer);

		/*
		 * Jedes Feld der JTable wird mit einer bestimmten Hoehe und Breite
		 * initialisiert
		 */
		TableColumnModel columnModel = getColumnModel();
		for (int i = 0; i < getColumnCount(); i++) {
			columnModel.getColumn(i).setPreferredWidth(5);
		}

	}
}
