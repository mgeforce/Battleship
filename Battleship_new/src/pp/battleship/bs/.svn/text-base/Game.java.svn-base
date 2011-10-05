package pp.battleship.bs;

import java.text.NumberFormat;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import pp.battleship.gui.MainView;
import pp.battleship.gui.OptionsController;

/**
 * 
 * Diese Klasse repraesentiert das Game Objekt. Die Klassen enthaelt Methode,
 * die fuer die Durchfuehrung eines Spiels zustaendig sind. Die Klasse durch
 * einen Thread dargestellt, um sie im Zweifelsfall warten zu lassen.<br>
 * <br>
 * Außerdem implementiert die Klasse das Interface Runnable.<br>
 * 
 * @author Thomas Aichinger, Matthias Bernloehr
 * 
 */
public class Game implements Runnable {

	/**
	 * Diese Variable repraesentiert einen Spieler
	 */
	private IPlayer player1;

	/**
	 * Diese Variable repraesentiert einen Spieler
	 */
	private IPlayer player2;

	/**
	 * Diese Variable repraesentiert den Spieler der gerade an der Reihe ist
	 */
	private IPlayer activePlayer;

	/**
	 * Diese Variable ueberprueft, ob ein Spiel laeuft
	 */
	private boolean gameIsRunning;

	/**
	 * Diese Variable ueberprueft, ob eine Eingabe erfolgt ist
	 */
	private boolean checkInput;

	/**
	 * Diese Methode erzeigt einen neuen Thread mit normaler Prioritaet und
	 * startet diesen
	 */
	public Game() {
		Thread gameThread = new Thread(this);
		// Priotirät vergeben
		gameThread.setPriority(Thread.NORM_PRIORITY);
		gameThread.start();

	}

	/**
	 * Diese Methode legt fest welcher der uebergebenen IPlayer als Spieler 1
	 * bzw. 2 deklariert wird und welcher Spieler beginnen darf.
	 * 
	 * @param one
	 * @param two
	 */
	public void setup(IPlayer one, IPlayer two) {
		player1 = one;
		player2 = two;
		activePlayer = player2;
		this.gameIsRunning = false;
		this.checkInput = false;
	}

	/**
	 * Diese Methode tauscht abwechselnd die Spieler, die an der Reihe sind
	 */
	public void changeActivePlayer() {
		if (activePlayer == player1) {
			activePlayer = player2;
		} else {
			activePlayer = player1;
		}
		/*
		 * Fuer einen aktiven Spieler im Netzwerkspiel wird hier der
		 * Speicherbutton aktiviert
		 */
		if (OptionsController.getInstance().getGameType().equals("ss")) {
			if (OptionsController.getInstance().getNetworkType().equals("host")) {
				if (activePlayer == player1) {
					OptionsController.getInstance().getItem().setEnabled(true);
				} else
					OptionsController.getInstance().getItem().setEnabled(false);
			} else if (OptionsController.getInstance().getNetworkType().equals(
					"client")) {
				if (activePlayer == player2) {
					OptionsController.getInstance().getItem().setEnabled(true);
				} else
					OptionsController.getInstance().getItem().setEnabled(false);
			}
		}

	}

	/**
	 * Diese Methode ist fuer den Spielablauf zustaendig. Jedesmal, wenn ein
	 * Spieler seinen Schuss/Schuesse beendet hat, wird ueber die Methode
	 * changeActiveplayer der aktive Spieler getauscht
	 */
	public synchronized void play() {
		boolean shootAgain = true;
		boolean gameIsOver = false;

		/*
		 * Diese while-Schleife laeuft so lange, bis das Spiel vorbei ist
		 */
		while (!gameIsOver) {
			/*
			 * Diese while-Schleife laeuft solange, wie ein Spieler an der Reihe
			 * ist
			 */
			while (shootAgain && !gameIsOver) {

				/*
				 * Wenn getActivePlayer ein Human-Spieler ist, dann wird der
				 * Thread ueber sleep stillgelegt, bis eine Eingabe ueber den
				 * Mouselistener erfolgt
				 */
				if (getActivePlayer().getClass() == Human.class
						|| getActivePlayer().getClass() == HumanNetwork.class)
					try {
						OptionsController.getInstance().setAllowed(true);
						while (!checkInput) {
							Thread.sleep(20);
						}
						setCheckInput(false);
						OptionsController.getInstance().setAllowed(false);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				/*
				 * Hier fuehrt ein Spieler einen Zug aus, ueber die Rueckgabe
				 * von makeTurn wird ueberprueft, ob ein Spieler nochmal
				 * schiessen darf
				 */
				shootAgain = getActivePlayer().makeTurn() != 0;
				/*
				 * Hier wird ueberprueft, ob das Spiel zu Ende ist
				 */
				gameIsOver = checkGameIsOver();
			}
			/*
			 * shootAgain wird auf true gesetzt und der aktive Player wird
			 * getauscht
			 */
			shootAgain = true;
			changeActivePlayer();
		}

		OptionsController.getInstance().getItem().setEnabled(false);
		/*
		 * Statistik wird ausgegeben
		 */
		if (OptionsController.getInstance().getGameType().equals("sc")) {
			if (player1.isGamelost()) {
				winFrameView(player2);
			} else if (player2.isGamelost()) {
				lostFrameView(player2);
			}

		} else if (OptionsController.getInstance().getGameType().equals("ss")
				|| OptionsController.getInstance().getGameType().equals("cc")) {
			if (OptionsController.getInstance().getShipCount() == 0) {
				if (OptionsController.getInstance().getNetworkType().equals(
						"host"))
					winFrameView(player1);
				else if (OptionsController.getInstance().getNetworkType()
						.equals("client"))
					winFrameView(player2);
			} else {
				if (OptionsController.getInstance().getNetworkType().equals(
						"host"))
					lostFrameView(player1);
				else if (OptionsController.getInstance().getNetworkType()
						.equals("client"))
					lostFrameView(player2);
			}

			/*
			 * Netzwerkverbindung wird beendet
			 */
			if (OptionsController.getInstance().getNetworkType().equals("host"))
				OptionsController.getInstance().getNetwork()
						.closeHostConnection();
			else if (OptionsController.getInstance().getNetworkType().equals(
					"client"))
				OptionsController.getInstance().getNetwork()
						.closeClientConnection();
		}

		gameIsRunning = false;
	}

	/**
	 * Diese Methode zeigt die Statistik des Verlierers
	 * 
	 * @param playerLost
	 */
	private void lostFrameView(IPlayer playerLost) {
		JInternalFrame frame = new JInternalFrame();
		frame.setSize(200, 200);
		frame.getContentPane().setLayout(null);

		JLabel win = new JLabel("Sie haben verloren!");
		win.setBounds(10, 10, 200, 20);
		frame.getContentPane().add(win);

		JLabel suc = new JLabel("Successful shoots: "
				+ (playerLost.getSuccessfulShoots().size() + playerLost
						.getSunkShoots().size()));
		suc.setBounds(10, 50, 200, 20);
		frame.getContentPane().add(suc);

		JLabel fail = new JLabel("Fail shoots: "
				+ playerLost.getFailShoots().size());
		fail.setBounds(10, 80, 200, 20);
		frame.getContentPane().add(fail);

		double shoots = playerLost.getSuccessfulShoots().size()
				+ playerLost.getFailShoots().size()
				+ playerLost.getSunkShoots().size();
		double quote = ((playerLost.getSuccessfulShoots().size() + playerLost
				.getSunkShoots().size()) / shoots) * 100;

		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(2);

		JLabel trefferquote;
		if (quote == 0) {
			trefferquote = new JLabel("Trefferquote: 0%");
		} else {
			trefferquote = new JLabel("Trefferquote: " + n.format(quote) + "%");
		}

		trefferquote.setBounds(10, 110, 200, 20);
		frame.getContentPane().add(trefferquote);

		MainView.buildDesktop(frame);
	}

	/**
	 * Diese Methode zeigt die Statistik des Gewinners
	 * 
	 * @param playerWin
	 */
	private void winFrameView(IPlayer playerWin) {
		JInternalFrame frame = new JInternalFrame();
		frame.setSize(200, 200);
		frame.getContentPane().setLayout(null);

		JLabel win = new JLabel("Sie haben gewonnen!");
		win.setBounds(10, 10, 200, 20);
		frame.getContentPane().add(win);

		JLabel suc = new JLabel("Successful shoots: "
				+ (playerWin.getSuccessfulShoots().size() + playerWin
						.getSunkShoots().size()));
		suc.setBounds(10, 50, 200, 20);
		frame.getContentPane().add(suc);

		JLabel fail = new JLabel("Fail shoots: "
				+ playerWin.getFailShoots().size());
		fail.setBounds(10, 80, 200, 20);
		frame.getContentPane().add(fail);

		double shoots = playerWin.getSuccessfulShoots().size()
				+ playerWin.getFailShoots().size()
				+ playerWin.getSunkShoots().size();
		double quote = ((playerWin.getSuccessfulShoots().size() + playerWin
				.getSunkShoots().size()) / shoots) * 100;

		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(2);

		JLabel trefferquote;
		if (quote == 0) {
			trefferquote = new JLabel("Trefferquote: 0%");
		} else {
			trefferquote = new JLabel("Trefferquote: " + n.format(quote) + "%");
		}

		trefferquote.setBounds(10, 110, 200, 20);
		frame.getContentPane().add(trefferquote);

		MainView.buildDesktop(frame);
	}

	/**
	 * Diese Methode ueberprueft, ob das SPiel vorbei ist
	 * 
	 * @return boolean
	 */
	private boolean checkGameIsOver() {

		if (OptionsController.getInstance().getGameType().equals("sc")) {
			if (getPlayer1().getAreaFactory().getArea().getShList().isEmpty()
					|| getPlayer2().getAreaFactory().getArea().getShList()
							.isEmpty())
				return true;
		} else if (OptionsController.getInstance().getGameType().equals("ss")
				|| OptionsController.getInstance().getGameType().equals("cc")) {
			if (OptionsController.getInstance().getShipCount() == 0) {
				return true;
			}

			if (OptionsController.getInstance().getNetworkType().equals("host")) {
				if (getPlayer1().getAreaFactory().getArea().getShList()
						.isEmpty()) {
					return true;
				}
			}
			if (OptionsController.getInstance().getNetworkType().equals(
					"client")) {
				if (getPlayer2().getAreaFactory().getArea().getShList()
						.isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Diese Methode gibt player1 zurueck
	 * 
	 * @return player1
	 */
	public IPlayer getPlayer1() {
		return player1;
	}

	/**
	 * Diese Methode gibt player2 zurueck
	 * 
	 * @return player2
	 */
	public IPlayer getPlayer2() {
		return player2;
	}

	/**
	 * Diese Methode gibt den activePlayer zurueck
	 * 
	 * @return activePlayer
	 */
	public IPlayer getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Diese Methode gibt checkInput zurueck
	 * 
	 * @return boolean
	 */
	public boolean isCheckInput() {
		return checkInput;
	}

	/**
	 * Diese Methode setzt die Klassenvariable checkInput auf die uebergebene
	 * Variable checkInput
	 * 
	 * @param checkInput
	 */
	public void setCheckInput(boolean checkInput) {
		this.checkInput = checkInput;
	}

	/**
	 * Diese Methode setzt das, vom MouseListener uebergebene Feld dem
	 * HumanPlayer, damit dieser es n der Methode makeTurn benutzen kann
	 * 
	 * @param shootFieldHuman
	 */
	public void setShootFieldHuman(Field shootFieldHuman) {
		Human humanTemp = (Human) this.activePlayer;
		humanTemp.setField(shootFieldHuman);
	}

	/**
	 * Diese Methode setzt die Klassenvariable gameIsRunning auf true und
	 * startet somit das Spiel
	 */
	public void startAGame() {
		this.gameIsRunning = true;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein game Objekt erzeugt wird und das
	 * Spiel wird gestartet, sobald gameIsRunning auf true gesetzt wird
	 */
	@Override
	public void run() {

		try {
			while (true) {
				if (gameIsRunning) {
					this.play();
				} else {
					Thread.sleep(20);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
