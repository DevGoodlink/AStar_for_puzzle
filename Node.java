package ia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Node implements Comparable<Node> {
	/*
	 * la grille du jeu
	 */

	private Board board;
	/*
	 * une référence vers le noeud père dans l'arbre
	 */
	private Node previous;
	/*
	 * la valeur de la fonction g(n) = le nombre de mouvements dÃ©jÃ  effectuÃ©s
	 */
	private int g;
	/*
	 * la valeur de la fonction h(n) = estimation du nombre de mouvements restants
	 */
	private int h;

	// m étant le nombre de mouvement depuis le dÃ©but de la partie
	public Node(Board board, Node prev, int m) {
		this.previous = prev;
		this.board = board;
		this.g = m;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public Board getBoard() {
		return this.board;
	}

	public Node getPrevious() {
		return this.previous;
	}

	public int f() {
		return h() + this.g;
	}

	public int h() {

		return this.h2();
	}

	private int h1() {
		// nombre de cases mal placées
		int malplacees = 0;
		int compt = 1;
		for (int i = 0; i < this.board.getSize(); i++) {
			for (int j = 0; j < this.board.getSize(); j++) {
				if (this.board.get(i, j) != compt)
					malplacees++;
				compt++;
			}
		}
		return malplacees - 1;
	}

	private int h2() {
		// calcul de la distance euclidienne entre la case et son emplacement
		/*
		 * int distance = 0; int ip = 0, jp = 0, a = 0; int compt = 1; for (int i = 0; i
		 * < this.board.getSize(); ++i) { for (int j = 0; j < this.board.getSize(); ++j)
		 * { a = this.board.get(i, j); if (a == compt) break; jp = (a - 1) /
		 * this.board.getSize(); ip = (a - 1) % this.board.getSize(); distance +=
		 * Math.abs(ip - i) + Math.abs(jp - j); compt++; } } return distance;
		 */
		int block;
		int total = 0;
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				block = board.get(i,j);
				if (block != 0)
					total += manhattanDistance(block, i, j);
			}
		}
		return total;
	}

	private int manhattanDistance(int block, int i, int j) {
		int goalRow = (block - 1) / board.getSize();
		int goalCol = (block - 1) % board.getSize();
		return Math.abs(goalRow - i) + Math.abs(goalCol - j);
	}

	public boolean isGoal() {
		// Test si une board est triée
		/*
		 * int compt = 1; for (int i = 0; i < this.board.getSize(); i++) { for (int j =
		 * 0; j < this.board.getSize(); j++) { if (this.board.get(i, j) != compt) return
		 * false; compt++; } }
		 */
		if (this.h() == 0)
			return true;
		else
			return false;
	}

	public Stack<Board> neighbors() {
		// retourn une pile de voisins aprés déplacement de la case 0 dans les 4 sens
		Stack<Board> NoeudsVoisins = new Stack<>();

		Board b = new Board(this.board.copy());
		if (b.getZeroRow() < b.getBlock().length - 1)
			NoeudsVoisins.push(versLeBas(b.getBlock(), b.getZeroRow(), b.getZeroCol()));

		b = new Board(this.board.copy());
		if (b.getZeroRow() > 0)
			NoeudsVoisins.push(versLeHaut(b.getBlock(), b.getZeroRow(), b.getZeroCol()));

		b = new Board(this.board.copy());
		if (b.getZeroCol() < b.getBlock()[0].length - 1)
			NoeudsVoisins.push(versLaDroite(b.getBlock(), b.getZeroRow(), b.getZeroCol()));

		b = new Board(this.board.copy());
		if (b.getZeroCol() > 0)
			NoeudsVoisins.push(versLaGauche(b.getBlock(), b.getZeroRow(), b.getZeroCol()));

		return NoeudsVoisins;
	}

	// Fonctions permettant le déplacement de la case 0
	private Board versLeBas(int[][] block, int j, int i) {
		// System.out.println("row : "+j+ " col : "+i+" val :"+block[j][i]+" size
		// "+block.length);
		// System.out.println("Déplacement zero vers le bas");
		int temp = block[j + 1][i];
		block[j][i] = temp;
		block[j + 1][i] = 0;
		Board board1 = new Board(block);
		// System.out.println(board1);
		return board1;
	}

	private Board versLeHaut(int[][] block, int j, int i) {
		// System.out.println("row : "+j+ " col : "+i+" val :"+block[j][i]);
		// System.out.println("Déplacement zero vers le haut");

		int temp = block[j - 1][i];
		block[j][i] = temp;
		block[j - 1][i] = 0;
		Board board1 = new Board(block);
		// System.out.println(board1);
		return board1;
	}

	private Board versLaDroite(int[][] block, int j, int i) {
		// System.out.println("Déplacement zero vers la droite");

		// System.out.println("row : "+j+ " col : "+i+" val :"+block[j][i]);
		int temp = block[j][i + 1];
		block[j][i] = temp;
		block[j][i + 1] = 0;
		Board board1 = new Board(block);
		// System.out.println(board1);
		return board1;
	}

	private Board versLaGauche(int[][] block, int j, int i) {
		// System.out.println("row : "+j+ " col : "+i+" val :"+block[j][i]);
		// System.out.println("Déplacement zero vers la gauche");
		int temp = block[j][i - 1];
		block[j][i] = temp;
		block[j][i - 1] = 0;
		Board board1 = new Board(block);
		// System.out.println(board1);
		return board1;
	}

	@Override
	public int compareTo(Node arg0) {
		// TODO Auto-generated method stub
		return this.f() - arg0.f();
	}

}
