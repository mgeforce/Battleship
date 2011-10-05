package pp.battleship.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import pp.battleship.bs.Field;
import pp.battleship.bs.FieldFactory;
import pp.battleship.bs.IllegalFieldException;
import pp.battleship.gui.MainView;
import pp.battleship.gui.OptionsController;
import pp.battleship.saveandload.Save;

/**
 * 
 * Diese Klasse ist fuer die Kommunikation im Netzwerk zustaendig. Ein Objekt
 * dieser Klasse kann sowohl Host, als auch Client sein. In dieser Klasse
 * befinden sich alle dafuer benoetigten Methoden.<br>
 * <br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Network {

	/**
	 * Dieser Wert enthaelt den Port, welcher fuer eine Netzwerkverbindung
	 * benutzt wird.
	 */
	private final int port = 50029;

	/**
	 * Dieser Variable enthaelt den ServerSocket des Hosts
	 */
	private ServerSocket ss;

	/**
	 * Diese Variable enthaelt den Socket des Clients
	 */
	private Socket s;

	/**
	 * Diese Variable enthaelt den BufferedReader, welcher zum Lesen der ueber
	 * das Netzwerk verschickten Nachrichten dient.
	 */
	private BufferedReader in;

	/**
	 * Diese Variable enthaelt den Writer, welcher Nachrichten ueber das
	 * Netzwerk verschickt.
	 */
	private Writer out;

	/**
	 * Diese Methode baut eine Hostverbindung auf
	 */
	public void createHostConnection() {
		try {
			/*
			 * Es wird ein neuen Serversocket mit uebergebenem Port erstellt.
			 */
			ss = new ServerSocket(port);

			/*
			 * Dem Benutzer wird ein Informationsfenster angezeigt, dass er auf
			 * eine Clientverbindung warten muss.
			 */
			MainView.buildWaitingFrame();
			s = ss.accept();

			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new OutputStreamWriter(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode beendet die Hostverbindung
	 */
	public void closeHostConnection() {
		try {
			s.shutdownOutput();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode sendet Breite und Hoehe des Spielfeldes und alle zu
	 * setzenden Schiffe ueber das Netzwerk an den Client.
	 * 
	 * @param width
	 * @param height
	 * @param shType
	 */
	public void submitStartUpData(int width, int height, Stack<Integer> shType) {
		StringBuffer line = new StringBuffer();
		Iterator<Integer> it = shType.iterator();

		/*
		 * (Befehle size width height, ships n und n-mal ship length).
		 */
		line.append("size " + width + " " + height + ", ");
		line.append("ships " + shType.size());
		while (it.hasNext()) {
			line.append(", ship " + it.next());
		}

		try {
			out.write(String.format("%s%n", line));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode baut mit uebergebener Ip eine Clientverbindung auf
	 * 
	 * @param ip
	 */
	public void createClientConnection(String ip) {
		try {
			/*
			 * Es wird ein neuer Socket mit uebergeber Ip und Port erstellt
			 */
			s = new Socket(ip, port);

			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new OutputStreamWriter(s.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Diese Methode beendet die Clientverbindung
	 */
	public void closeClientConnection() {
		try {
			s.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Diese Methode liest beim Client die StartUpDaten aus
	 */
	public void getStartUpData() {
		String line = "";

		try {
			line = in.readLine();
		} catch (SocketException e) {
			MainView.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Der String wird an den Leerzeilen getrennt und die Werte werden im
		 * OptionsController gespeichert.
		 */
		String[] startUpData = line.split(", ");

		String[] size = startUpData[0].split(" ");

		OptionsController.getInstance().setWidth(Integer.parseInt(size[1]));

		OptionsController.getInstance().setHeight(Integer.parseInt(size[2]));

		Stack<Integer> shipStack = new Stack<Integer>();
		for (int i = 2; i < startUpData.length; i++) {
			String[] ship = startUpData[i].split(" ");
			shipStack.push(Integer.parseInt(ship[1]));
		}
		OptionsController.getInstance().setShipType(shipStack);
		OptionsController.getInstance().setShipTypeKI(shipStack);

	}

	/**
	 * Diese Methode schickt eine Schussanfrage ueber das Netzwerk. (entspricht
	 * einem Schuss auf ein Feld).
	 * 
	 * @param row
	 * @param column
	 */
	public void sendQuery(int row, int column) {
		String line = "query " + row + " " + column;

		try {
			out.write(String.format("%s%n", line));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode liest die "Schussanfrage" aus. Es wird ein Feld mit den
	 * ausgelesenen Koordinaten erstellt und zurueckgegeben.
	 * 
	 * @return Field
	 */
	public Field readQuery() {
		String line = "";
		FieldFactory fieldFactory = new FieldFactory();
		String[] temp = null;

		/*
		 * Falls der ausgelesene String "save" entspricht wird eine
		 * entsprechende Methode fuers Speichenr aufgerufen.
		 */
		try {
			do {
				line = in.readLine();

				if (line == null)
					MainView.shutdown();

				temp = line.split(" ");
				if (temp[0].equals("save"))
					readSave(line);
			} while (temp[0].equals("save"));
		} catch (SocketException e) {
			MainView.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (line == null)
			MainView.shutdown();

		String[] shootFields = line.split(" ");

		Field shootField = null;
		try {
			shootField = fieldFactory.createField(Integer
					.parseInt(shootFields[2]) - 1, Integer
					.parseInt(shootFields[1]) - 1);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalFieldException e) {
			e.printStackTrace();
		}

		return shootField;
	}

	/**
	 * Diese Methode schickt auf die Schussanfrage eine Antwort zurueck, ob
	 * Schuss daneben ging, getroffen hat, oder ob ein Schiff versenkt wurde.
	 * 
	 * @param Integer
	 */
	public void sendReplyToQuery(int k) {
		String line = "reply " + k;

		try {
			out.write(String.format("%s%n", line));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode liest die Antwort der Schussabfrage aus und gibt sie an den
	 * jeweiligen Spieler zurueck.
	 * 
	 * @return Integer
	 */
	public int readReply() {
		String line = "";
		String[] temp = null;

		try {
			do {
				line = in.readLine();

				if (line == null)
					MainView.shutdown();

				temp = line.split(" ");
				if (temp[0].equals("save"))
					readSave(line);
			} while (temp[0].equals("save"));
		} catch (SocketException e) {
			MainView.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (line == null)
			MainView.shutdown();

		String[] reply = line.split(" ");

		return Integer.parseInt(reply[1]);
	}

	/**
	 * Diese Methode schickt den Timestamp, welcher beim Speichernaufruf
	 * angelegt wird ueber das Netzwerk. Sowohl Host als auch Client benoetigen
	 * denselben Timestamp um gespeicherte Dateien wieder laden zu koennen.
	 * 
	 * @param timestamp
	 */
	public void sendTimeStamp(String timestamp) {
		String line = "timestamp " + timestamp;

		try {
			out.write(String.format("%s%n", line));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode liest den ueber das Netzwerk gesendeten Timestamp aus und
	 * gibt ihn an den jeweiligen Spieler zurueck.
	 * 
	 * @return String
	 */
	public String readTimeStamp() {

		String line = "";

		try {
			line = in.readLine();
		} catch (SocketException e) {
			MainView.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (line == null)
			MainView.shutdown();

		String[] reply = line.split(" ");
		return reply[1];
	}

	/**
	 * Diese Methode sendet eine Antwort zurueck, ob der Timestamp angenommen
	 * wird.
	 * 
	 * @param equalTimeStamps
	 */
	public void sendReplyToTimeStamp(boolean equalTimeStamps) {

		String line = "replyToTimeStamp " + equalTimeStamps;

		try {
			out.write(String.format("%s%n", line));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode liest die Antwort, ob der Timestamp der anderen Seite
	 * angenommen wurde.
	 * 
	 * @return boolean
	 */
	public boolean readReplyToTimeStamp() {
		String line = "";

		try {
			line = in.readLine();
		} catch (SocketException e) {
			MainView.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (line == null)
			MainView.shutdown();

		String[] reply = line.split(" ");

		if (reply[1].equals("true"))
			return true;
		else
			return false;
	}

	/**
	 * Diese Methode sendet den Save-Befehl ueber das Netzwerk. Wird aufgerufen,
	 * sobald ein Spieler im Netzwerk auf Speichern klickt.
	 * 
	 * @param timestamp
	 */
	public void sendSave(long timestamp) {
		String line = "save " + timestamp;

		try {
			out.write(String.format("%s%n", line));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode liest den Save-Befehl, der ueber das Netzwerk versendet
	 * wurde, aus. Es oeffnet sich ein JFileChooser in dem man seine zu
	 * speichernde Datei festlegen kann. Der Timestamp wird automatisch
	 * angehaengt.
	 * 
	 * @param line
	 */
	public void readSave(String line) {
		Save save = new Save();

		String[] saveStr = line.split(" ");

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
			String filename = file.getAbsolutePath() + "-" + saveStr[1]
					+ ".txt";
			if (OptionsController.getInstance().getGameType().equals("sc"))
				save.saveSC(filename);
			if (OptionsController.getInstance().getGameType().equals("ss")) {
				if (OptionsController.getInstance().getNetworkType().equals(
						"host"))
					save.saveHost(filename);
				else if (OptionsController.getInstance().getNetworkType()
						.equals("client"))
					save.saveClient(filename);
			}
		}

	}

}
