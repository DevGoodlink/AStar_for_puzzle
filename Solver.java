package ia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solver {

	private Node goalNode; // noeud but
	private List<Node> openList; // liste des noeuds ouverts (Ã  explorer)
	private List<Node> closedList; // liste des noeuds fermÃ©s (explorÃ©s)

	/*
	 * constructeur implémente l'algorithme A star
	 */
	public Solver(Board initial) {
		openList = new ArrayList<Node>();
		closedList = new ArrayList<Node>();

		Node currentNode = new Node(initial, null, 0);
		openList.add(currentNode);

		while (!currentNode.isGoal() && !openList.isEmpty() ) 
		{
			currentNode = Collections.min((Collection<Node>) openList);
			openList.remove(currentNode);
			closedList.add(currentNode);
			for(Board b : currentNode.neighbors()) {
				if(inTheClosedList(b)==-1) {
					Node n1=new Node(b,currentNode,currentNode.getG()+1);
					int i = inTheOpenList(b);
					if( i!=-1 && openList.get(i).f()>=n1.f()) {
						openList.add(i,n1);
					}else {
						openList.add(n1);
					}
				}
			}
		}
		goalNode=currentNode;
		
		

	}

	/**
	 * Cherche si le noeud en argument est dans la liste openList
	 * 
	 * @param b
	 * @return true si oui false si non
	 */
	public int inTheOpenList(Board b) {
		int i=0;
		for (Node n : openList) {
			if (n.getBoard().equals(b)) return i;
			i++;
		}
		return -1;
	}

	/**
	 * Cherche si le noeud en argument est dans la liste closedList
	 * 
	 * @param b
	 * @return
	 */
	public int inTheClosedList(Board b) {
		int i=0;
		for (Node n : closedList) {
			if (n.getBoard().equals(b)) return i;
			i++;
		}
		return -1;
	}

	public void moveFromClosedToOpen(Node node) {
		Node nToMove = null;
		boolean found = false;
		for (Node n : closedList) {
			if (n.getBoard().equals(node.getBoard())) {
				nToMove = n;
				found = true;
			}
		}
		if (found)
			closedList.remove(nToMove);
		else
			System.out.println("Noeud introuvable");

	}

	/*
	 * recontruit le chemin de la configuration initiale Ã  la configuration but
	 */
	public List<Board> getPathSolution() {
		List<Board> lst = new ArrayList<Board>() ;
		for(Node n : closedList) {
			lst.add(n.getBoard());
		}
		return lst;
	}

	/*
	 * recharge un fichier puzzle
	 */
	public static int[][] loadFile(String fileName) {
		int[][] block = null;
		try {
			File f = new File(fileName);
			Scanner scanner = new Scanner(f);
			// la première ligne : taille du puzzle
			Scanner sc = new Scanner(scanner.nextLine());
			int N = sc.nextInt();
			block = new int[N][N];
			int i = 0;
			while (scanner.hasNext()) {
				sc = new Scanner(scanner.nextLine());
				int j = 0;
				while (sc.hasNext()) {
					block[i][j] = sc.nextInt();
					j++;
				}
				i++;
			}
			// les autres lignes la grille du puzzle
			sc.close();
		} catch (FileNotFoundException exception) {
			System.out.println("File not found");
		}
		return block;
	}

public static void main(String[] args) {

		// créer un nouveau board à  partir d'un fichier
		int[][] block = loadFile("./puzzles/puzzle29.txt");//{{1,0,3},{4,8,5},{7,2,6}};//
		Board initial = new Board(block);
		System.out.println("initial = \n "+initial);
		Node n = new Node(initial,null,0);
		//solve the puzzle
        Solver solver = new Solver(initial);
        List<Board> path = solver.getPathSolution();
        
        // print solution to standard output
        for (Board board : path)
            	System.out.println(board); 
        
	}
}
