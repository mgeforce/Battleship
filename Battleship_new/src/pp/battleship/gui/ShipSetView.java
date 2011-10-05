package pp.battleship.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import pp.battleship.bs.Directions;
import pp.battleship.bs.Field;
import pp.battleship.bs.FieldFactory;
import pp.battleship.bs.IllegalFieldException;
import pp.battleship.bs.Ship;

/**
 * 
 * Diese Klasse erzeugt ein Objekt, welches das Fenster zum Setzen der Schiffe
 * enthaelt. Diese Klasse erbt von der Klasse JInternalFrame wodurch eine
 * Anzeige aller Komponenten moeglich wird.<br>
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
public class ShipSetView extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Diese Variable enthaelt den Punkt, auf den der Spieler in der JTable
	 * geklickt hat um seine Schiffe zu setzen.
	 */
	private Point firstPoint = null;

	/**
	 * Diese Variable enthaelt die Tabelle in die die Schiffe gesetzt werden.
	 */
	private CreateTableView table;

	/**
	 * Diese JTextArea wird benoetigt um die noch zu setzenden Schiffe
	 * anzuzeigen.
	 */
	private JTextArea textArea;

	/**
	 * Der JButton dient zur Bestaetigung der gesetzten Schiffe
	 */
	private JButton weiter;

	/**
	 * In dieser Liste werden alle Felder gespeichert, die in einem zu setzenden
	 * Schiff enthalten sind
	 */
	private List<Field> fieldList;

	/**
	 * Die FieldFactory dient zur Erzeugung von Feldern
	 */
	private FieldFactory fieldFactory;

	/**
	 * Der Konstruktor erzeugt ein JInternalFrame, der ein Auswahlfenster
	 * enthaelt, in dem man seine zuvor ausgewaehlten Schiffe setzen kann.<br>
	 */
	public ShipSetView() {

		/*
		 * Groesse des Frames: Breite und Hoehe werden anhand der Groesse des
		 * Spielfeldes berechnet
		 */
		setSize(((OptionsController.getInstance().getWidth() * 15) + 350),
				((OptionsController.getInstance().getHeight() * 16) + 90));
		getContentPane().setLayout(null);

		fieldFactory = new FieldFactory();

		Iterator<Integer> ships = OptionsController.getInstance().getShipType()
				.iterator();

		table = new CreateTableView();

		/*
		 * Es wird ein MouseListener für die table initialisiert. Beim Mausklick
		 * wird die Methode AreaPlayerMouseClicked aufgerufen. Wird die
		 * Maustaste wieder losgelassen, wird ueberprueft ob alle Schiffe
		 * gesetzt wurden. Falls ja, wird der weiter Button aktiviert.
		 */
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				AreaPlayerMouseClicked(event);
			}

			public void mouseReleased(MouseEvent envent) {
				if (OptionsController.getInstance().getShipType().size() == 0) {
					weiter.setEnabled(true);
				} else {
					weiter.setEnabled(false);
				}
			}
		});

		/*
		 * Die table wird mit setBounds() positioniert.
		 */
		table.setBounds(0, 5, OptionsController.getInstance().getWidth() * 15,
				OptionsController.getInstance().getHeight() * 16);
		getContentPane().add(table);

		/*
		 * Die textArea wird auf nicht editierbar geaendert.
		 */
		textArea = new JTextArea(OptionsController.getInstance().getShipType()
				.size(), 3);
		textArea.setEditable(false);

		/*
		 * Es werden alle ausgewaehlten Schiffstypen zur textArea hinzugefuegt.
		 */
		while (ships.hasNext()) {
			textArea.append(ships.next().toString() + "\n");
		}

		/*
		 * Die textArea wird ueber setBounds() positioniert.
		 */
		textArea.setBounds(
				(OptionsController.getInstance().getWidth() * 15) + 10, 5, 30,
				OptionsController.getInstance().getHeight() * 16);
		getContentPane().add(textArea);

		/*
		 * JLabel wird initialisiert und mit setBounds() positioniert.
		 */
		JLabel setzen = new JLabel(
				"<html>Setzen: Klicken auf den Startpunkt, anschließend auf den Endpunkt</html>");
		setzen.setBounds(
				(OptionsController.getInstance().getWidth() * 15) + 50, 0, 270,
				40);
		getContentPane().add(setzen);

		/*
		 * JLabel wird initialisiert und mit setBounds() positioniert.
		 */
		JLabel loeschen = new JLabel(
				"<html>Loeschen: Klicken mit rechter Maustaste auf ein Schiff</html>");
		loeschen.setBounds(
				(OptionsController.getInstance().getWidth() * 15) + 50, 50,
				270, 40);
		getContentPane().add(loeschen);

		/*
		 * JButton mit dem Text "weiter" wird initialisiert und mit setBounds()
		 * positioniert. Es wird ein ActionListener hinzugefuegt. Beim Klicken
		 * des JButtons wird die Maske der Schiffe ueber die Methode
		 * removeMask() entfernt. Dem OptionsController wird die Tabelle mit den
		 * gesetzten Schiffen uebergeben. Wurde der GameType
		 * "Spieler gegen Computer" ausgewaehlt, dann setzt die KI ueber den
		 * Methodenaufruf setShips() ihre Schiffe. Anschließend wird in der
		 * MainView das Objekt PlayingFieldView erzeugt.
		 */
		weiter = new JButton("Fertig");
		weiter.setEnabled(false);
		weiter.setBounds(
				((OptionsController.getInstance().getWidth() * 15) + 180),
				((OptionsController.getInstance().getHeight() * 16) + 10), 100,
				30);
		getContentPane().add(weiter);
		weiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeMask();

				OptionsController.getInstance().setTable(table);

				if (OptionsController.getInstance().getGameType().equals("sc")) {
					OptionsController.getInstance().getKi().setShips();
				}

				MainView.buildDesktop(new PlayingFieldView());
			}
		});

	}

	/**
	 * Diese Methode ist fuer das Setzen der Schiffe verantwortlich. Ihr wird
	 * das Mouseevent des MouseListeners uebergeben.
	 * 
	 * @param evt
	 */
	private void AreaPlayerMouseClicked(MouseEvent evt) {
		Point p = evt.getPoint();
		int row = table.rowAtPoint(p);
		int col = table.columnAtPoint(p);

		/*
		 * Wenn linke Maustaste gedrueckt wird
		 */
		if (evt.getButton() == MouseEvent.BUTTON1) {
			/*
			 * Falls keine Schiffe mehr zu setzen sind wird die Methode
			 * abgebrochen.
			 */
			if (OptionsController.getInstance().getShipType().isEmpty()) {
				return;
			}
			/*
			 * firstpoint == null entspricht dem ersten Klick eines Setzvorgangs
			 * Beim ersten Klick werden ueber die Methode showPossibilities alle
			 * moeglichen Endpunkte angezeigt,
			 */
			if (firstPoint == null) {
				firstPoint = p;
				showPossibilities(col, row);
			} else {
				/*
				 * Der Spieler hat auf einen Endpunkt geklickt
				 */
				if (table.getValueAt(row, col) != null
						&& table.getValueAt(row, col).equals("p")) {

					/*
					 * Die Startkoordinaten entsprechen dem zuerst geklickten
					 * Punkt
					 */
					int yStart = table.rowAtPoint(firstPoint);
					int xStart = table.columnAtPoint(firstPoint);

					/*
					 * Die Endkoordinaten entsprechen dem zweiten Punkt
					 */
					int yEnd = table.rowAtPoint(p);
					int xEnd = table.columnAtPoint(p);

					fieldList = new ArrayList<Field>();

					/*
					 * Die Methode getFields liefert alle Felder innerhalb der
					 * Koordinaten des Schiffes
					 */
					getFields(xStart, yStart, xEnd, yEnd);

					/*
					 * Es wird ein neues Schiff mit zuvor festgelegter
					 * Fieldliste angelegt und der Area des Spielers
					 * hinzugefuegt.
					 */
					Ship ship = new Ship(fieldList);
					OptionsController.getInstance().getAreaFactory().getArea()
							.addShip(ship);

					/*
					 * Ueber die Methode hidePossibilities werden alle
					 * moeglichen Endpunkte wieder versteckt.
					 */
					hidePossibilities(yStart, xStart);

					/*
					 * Nach Setzen eines Schiffes wird es aus dem
					 * OptionsController entfernt.
					 */
					OptionsController.getInstance().getShipType().pop();

					/*
					 * Ueber die Methode drawShip wird das gesetzte Schiff in
					 * die Tabelle gezeichnet.
					 */
					drawShip(yStart, xStart, col, row);

					/*
					 * Um jedes Schiff wird eine entsprechende Maske gezeichnet,
					 * welche dem Benutzer verdeutlichen soll, dass dort kein
					 * Schiff gesetzt werden kann.
					 */
					drawMask();

					/*
					 * Das gesetzte Schiff wird aus der JTextArea entfernt.
					 */
					delTextArea();

					/*
					 * Falls keine Schiffe mehr zu setzen sind, wird die Methode
					 * abgebrochen.
					 */
					if (OptionsController.getInstance().getShipType().isEmpty()) {
						return;
					}
					firstPoint = null;
				} else {
					/*
					 * Falls der Spieler keine moeglichen Endpunkt anklickt,
					 * werden alle Endpunkte ueber hidePosibilities wieder
					 * versteckt und es werden die neuen Endpunkte angezeigt.
					 */
					hidePossibilities(table.rowAtPoint(firstPoint), table
							.columnAtPoint(firstPoint));
					showPossibilities(col, row);
					firstPoint = p;
				}
			}
			/*
			 * Der Spieler drueckt die rechte Maustaste zum loeschen eines
			 * Schiffes
			 */
		} else {
			/*
			 * Es wird ueberprueft, ob der Spieler aus ein Schiff klickt
			 */
			if (table.getValueAt(row, col).equals("s")) {
				/*
				 * Aus dem OptionsController wird das entsprechende Schiff in
				 * einer Variable gespeichter.
				 */
				Ship s = OptionsController.getInstance().getAreaFactory()
						.getArea().getShip(row, col);

				int xStart = s.getXStart();
				int yStart = s.getYStart();
				int xEnd = s.getXEnd();
				int yEnd = s.getYEnd();
				if (firstPoint != null
						&& !OptionsController.getInstance().getShipType()
								.isEmpty()) {
					hidePossibilities(table.rowAtPoint(firstPoint), table
							.columnAtPoint(firstPoint));
				}

				/*
				 * Das Schiff wird in der Tabelle und im OptionsController
				 * geloescht. Außerdem wird die Maske um das Schiff entfernt.
				 */
				removeShip(xStart, yStart, xEnd, yEnd);
				OptionsController.getInstance().getAreaFactory().getArea()
						.removeShip(s);
				drawMask();
			}
		}
	}

	/**
	 * Dieser Methode werden die Start- und Endkoordinaten uebergeben. Anhand
	 * dieser Koordinaten werden die fuer ein Schiff benoetigten Felder erstellt
	 * und in der fieldList gespeichert.
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 */
	private void getFields(int xStart, int yStart, int xEnd, int yEnd) {

		try {
			/*
			 * NORTH
			 */
			if (xStart == xEnd && yStart > yEnd) {
				for (int i = yEnd; i <= yStart; i++) {
					fieldList.add(fieldFactory.createField(xStart, i));
				}
			}

			/*
			 * EAST
			 */
			else if (yStart == yEnd && xStart < xEnd) {
				for (int i = xStart; i <= xEnd; i++) {
					fieldList.add(fieldFactory.createField(i, yStart));
				}
			}

			/*
			 * SOUTH
			 */
			else if (xStart == xEnd && yStart < yEnd) {
				for (int i = yEnd; i >= yStart; i--) {
					fieldList.add(fieldFactory.createField(xStart, i));
				}
			}

			/*
			 * WEST
			 */
			else if (yStart == yEnd && xStart > xEnd) {
				for (int i = xStart; i >= xEnd; i--) {
					fieldList.add(fieldFactory.createField(i, yStart));
				}
			}
		} catch (IllegalFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Dieser Methode werden die Start- und Endkoordinaten uebergeben. Anhand
	 * dieser Koordinaten wird ein Schiff aus der Tabelle wieder geloescht. Alle
	 * Werte werden mit "w" (Wasser) ersetzt.
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 */
	private void removeShip(int xStart, int yStart, int xEnd, int yEnd) {
		/*
		 * Diese Variable enthaelt die Schiffslaenge des zu loeschenden Schiffes
		 */
		int shipLength = 0;

		/*
		 * NORTH
		 */
		if (xStart == xEnd && yStart > yEnd) {
			for (int i = yEnd; i <= yStart; i++) {
				table.setValueAt("w", i, xStart);
				shipLength++;
			}
		}

		/*
		 * SOUTH
		 */
		else if (xStart == xEnd && yStart < yEnd) {
			for (int i = yEnd; i >= yStart; i--) {
				table.setValueAt("w", i, xStart);
				shipLength++;
			}
		}

		/*
		 * EAST
		 */
		else if (yStart == yEnd && xStart < xEnd) {
			for (int i = xStart; i <= xEnd; i++) {
				table.setValueAt("w", yStart, i);
				shipLength++;
			}
		}

		/*
		 * WEST
		 */
		else if (yStart == yEnd && xStart > xEnd) {
			for (int i = xStart; i >= xEnd; i--) {
				table.setValueAt("w", yStart, i);
				shipLength++;
			}
		}

		/*
		 * Das geloeschte Schiff wird im OptionsController und in der JTextArea
		 * zu den noch setztenden Schiffen hinzugefuegt.
		 */
		OptionsController.getInstance().getShipType().push(shipLength);
		textArea.append(shipLength + "\n");
	}

	/**
	 * Dieser Methode zeichnet die Maske um die einzelnen Schiffe
	 */
	public void drawMask() {
		/*
		 * Die for-Schleife laeuft das komplette Spielfeld ab. Wenn eine Zelle
		 * kein "s" (Schiff) enthaelt, wird es mit einem "w" (Wasser) ersetzt.
		 */
		for (int row = 0; row < OptionsController.getInstance().getHeight(); row++) {
			for (int col = 0; col < OptionsController.getInstance().getWidth(); col++) {
				if (!table.getValueAt(row, col).equals("s"))
					table.setValueAt("w", row, col);
			}
		}

		/*
		 * Die for-Schleife laeuft das komplette Spielfeld ab. Es wird um jedes
		 * Schiff die benoetigte Maske gezeichnet.
		 */
		for (int row = 0; row < OptionsController.getInstance().getHeight(); row++) {
			for (int col = 0; col < OptionsController.getInstance().getWidth(); col++) {
				try {
					// TODO: Exception Klasse schreiben
					if (table.getValueAt(row, col).equals("s")) {
						try {
							if (!table.getValueAt(row - 1, col).equals("s"))
								table.setValueAt("m", row - 1, col);
						} catch (ArrayIndexOutOfBoundsException e) {
						}
						try {
							if (!table.getValueAt(row + 1, col).equals("s"))
								table.setValueAt("m", row + 1, col);
						} catch (ArrayIndexOutOfBoundsException e) {
						}
						try {
							if (!table.getValueAt(row, col - 1).equals("s"))
								table.setValueAt("m", row, col - 1);
						} catch (ArrayIndexOutOfBoundsException e) {
						}
						try {
							if (!table.getValueAt(row, col + 1).equals("s"))
								table.setValueAt("m", row, col + 1);
						} catch (ArrayIndexOutOfBoundsException e) {
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
	}

	/**
	 * Diese Methode geht jedes Feld der JTable durch und ueberprueft den Wert
	 * an dieser Stelle. Befindet sich an einer Stelle der Wert "m", welcher
	 * fuer die Maske steht, wird dieser durch ein "w" (Wasser) ersetzt.
	 */
	public void removeMask() {
		for (int row = 0; row < OptionsController.getInstance().getHeight(); row++) {
			for (int col = 0; col < OptionsController.getInstance().getWidth(); col++) {
				if (table.getValueAt(row, col).equals("m"))
					table.setValueAt("w", row, col);
			}
		}
	}

	/**
	 * Dieser Methode werden die Koordinaten des Schiffes uebergeben. Anhand
	 * dieser Koordinaten wird das Schiff im Spielfeld eingezeichnet
	 * 
	 * @param yStart
	 * @param xStart
	 * @param col
	 * @param row
	 */
	public void drawShip(int yStart, int xStart, int col, int row) {

		/*
		 * NORTH: Die for-Schleife laeuft vom Start- bis zum Endpunkt des
		 * Schiffes und aendert an jeder Stelle den Wert der JTable
		 */
		if (xStart == col && yStart > row) {
			for (int i = yStart; i >= row; i--) {
				table.setValueAt("s", i, col);
			}
		}

		/*
		 * EAST: Die for-Schleife laeuft vom Start- bis zum Endpunkt des
		 * Schiffes und aendert an jeder Stelle den Wert der JTable
		 */
		else if (yStart == row && xStart > col) {
			for (int i = xStart; i >= col; i--) {
				table.setValueAt("s", row, i);
			}
		}

		/*
		 * SOUTH: Die for-Schleife laeuft vom Start- bis zum Endpunkt des
		 * Schiffes und aendert an jeder Stelle den Wert der JTable
		 */
		else if (xStart == col && yStart < row) {
			for (int i = yStart; i <= row; i++) {
				table.setValueAt("s", i, col);
			}
		}

		/*
		 * WEST: Die for-Schleife laeuft vom Start- bis zum Endpunkt des
		 * Schiffes und aendert an jeder Stelle den Wert der JTable
		 */
		else if (yStart == row && xStart < col) {
			for (int i = xStart; i <= col; i++) {
				table.setValueAt("s", row, i);
			}
		}

	}

	/**
	 * Diese Methode zeigt nach dem ersten Klick (es werden die Koordinaten des
	 * Klicks uebergeben) auf das Spielfeld alle moeglichen Endpunkte des zu
	 * setzenden Schiffes an
	 * 
	 * @param col
	 * @param row
	 */
	public void showPossibilities(int col, int row) {

		/*
		 * In dieser Variable wird die Laenge des letzten Schiffes gespeichert
		 */
		int size = OptionsController.getInstance().getShipType().lastElement();

		/*
		 * Diese Variable prueft, ob sich innerhalb der Setzlinie ein Schiff
		 * befindet
		 */
		boolean set = true;

		/*
		 * Die for-Schleife laueft alle 4 Directions ab
		 */
		for (Directions directions : Directions.values()) {
			switch (directions) {
			case NORTH:
				set = true;
				/*
				 * NORTH: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich dort ein Schiff
				 * befindet. Falls nicht, wird der Wert der JTable auf "p"
				 * geaendert.
				 */
				if (row - size + 1 >= 0) {
					for (int j = row; j >= row - size + 1; j--) {
						if (table.getValueAt(j, col).equals("s")
								|| table.getValueAt(j, col).equals("m")) {
							set = false;
						}
					}
					if (set)
						table.setValueAt("p", row - size + 1, col);
				}
				break;
			case EAST:
				set = true;
				/*
				 * EAST: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich dort ein Schiff
				 * befindet. Falls nicht, wird der Wert der JTable auf "p"
				 * geaendert.
				 */
				if (col + size <= OptionsController.getInstance().getWidth()) {
					for (int j = col; j <= col + size - 1; j++) {
						if (table.getValueAt(row, j).equals("s")
								|| table.getValueAt(row, j).equals("m")) {
							set = false;
						}
					}
					if (set)
						table.setValueAt("p", row, col + size - 1);
				}
				break;
			case SOUTH:
				set = true;
				/*
				 * SOUTH: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich dort ein Schiff
				 * befindet. Falls nicht, wird der Wert der JTable auf "p"
				 * geaendert.
				 */
				if (row + size <= OptionsController.getInstance().getHeight()) {
					for (int j = row; j <= row + size - 1; j++) {
						if (table.getValueAt(j, col).equals("s")
								|| table.getValueAt(j, col).equals("m")) {
							set = false;
						}
					}
					if (set)
						table.setValueAt("p", row + size - 1, col);
				}
				break;
			case WEST:
				set = true;
				/*
				 * WEST: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich dort ein Schiff
				 * befindet. Falls nicht, wird der Wert der JTable auf "p"
				 * geaendert.
				 */
				if (col - size + 1 >= 0) {
					for (int j = col; j >= col - size + 1; j--) {
						if (table.getValueAt(row, j).equals("s")
								|| table.getValueAt(row, j).equals("m")) {
							set = false;
						}
					}
					if (set)
						table.setValueAt("p", row, col - size + 1);
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Diese Methode loescht nach dem zweiten Klick (es werden die Koordinaten
	 * des Klicks uebergeben) alle vorhandenen moeglichen Endpunkte wieder aus
	 * dem Spielfeld.
	 * 
	 * @param y
	 * @param x
	 */
	public void hidePossibilities(int y, int x) {

		/*
		 * In dieser Variable wird die Laenge des letzten Schiffes gespeichert
		 */
		int size = OptionsController.getInstance().getShipType().lastElement();

		/*
		 * Die for-Schleife laueft alle 4 Directions ab
		 */
		for (Directions directions : Directions.values()) {
			switch (directions) {
			case NORTH:
				/*
				 * NORTH: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich an einer Stelle ein
				 * "p" (Possibility) befindet. Ist dies der Fall, wird das "p"
				 * durch ein "w" (Wasser) ersetzt.
				 */
				if (y - size + 1 >= 0) {
					if (table.getValueAt(y - size + 1, x).equals("p"))
						table.setValueAt("w", y - size + 1, x);
				}
				break;
			case EAST:
				/*
				 * EAST: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich an einer Stelle ein
				 * "p" (Possibility) befindet. Ist dies der Fall, wird das "p"
				 * durch ein "w" (Wasser) ersetzt.
				 */
				if (x + size <= OptionsController.getInstance().getWidth()) {
					if (table.getValueAt(y, x + size - 1).equals("p"))
						table.setValueAt("w", y, x + size - 1);
				}
				break;
			case SOUTH:
				/*
				 * SOUTH: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich an einer Stelle ein
				 * "p" (Possibility) befindet. Ist dies der Fall, wird das "p"
				 * durch ein "w" (Wasser) ersetzt.
				 */
				if (y + size <= OptionsController.getInstance().getHeight()) {
					if (table.getValueAt(y + size - 1, x).equals("p"))
						table.setValueAt("w", y + size - 1, x);
				}
				break;
			case WEST:
				/*
				 * WEST: Wenn man sich innerhalb des Spielfeldes befindet, wird
				 * entlang der Richtung ueberprueft, ob sich an einer Stelle ein
				 * "p" (Possibility) befindet. Ist dies der Fall, wird das "p"
				 * durch ein "w" (Wasser) ersetzt.
				 */
				if (x - size + 1 >= 0) {
					if (table.getValueAt(y, x - size + 1).equals("p"))
						table.setValueAt("w", y, x - size + 1);
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Diese Methode loescht aus der JTextArea die letzte Zeile
	 */
	public void delTextArea() {
		int start;
		int end;
		int count = -1;
		try {
			count = textArea.getLineCount();
			if (count > 0) {
				start = textArea.getLineStartOffset(count - 2);
				end = textArea.getLineEndOffset(count - 1);
				textArea.replaceRange(null, start, end);
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
