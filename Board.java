package ia;

import java.util.Arrays;

public class Board {

	private int[][] block; // la grille
	private int size; // la taille de la grille
	private int zeroCol; // la case vide (la case zero) se trouve Ã  la position (zeroCol, zeroRow)
	private int zeroRow;

	public Board(int[][] block) {
		this.block = block;
		for (int i = 0; i < this.block[0].length; i++) {
			for (int j = 0; j < this.block[1].length; j++) {
				if (this.block[i][j] == 0) {
					this.zeroCol = j;
					this.zeroRow = i;
				}
			}
		}
		this.size = this.block[0].length;
	}

	public int get(int i, int j) {
		// tester si i ou j dépasse la taille du block
		// if(i>this.block.length || j>this.block[i].length)
		int val;
		try {
			val = this.block[i][j];
			return val;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // dans le cas ou les i ou j dépasse les bornes

	}

	public int[][] getBlock() {
		return block;
	}

	public int getSize() {
		return size;
	}

	public int getZeroCol() {
		return zeroCol;
	}

	public int getZeroRow() {
		return zeroRow;
	}

	public int[][] copy() {
		int[][] copy = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[i][j] = block[i][j];
			}
		}
		return copy;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		if (!Arrays.deepEquals(this.block, other.block)) {
			return false;
		}
		if (this.size != other.size) {
			return false;
		}
		if (this.zeroCol != other.zeroCol) {
			return false;
		}
		if (this.zeroRow != other.zeroRow) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(other.getBlock()[i][j] != block[i][j]) return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
	    for (int i = 0; i < size; i++) {
	        for (int j = 0; j < size; j++) {
	            s.append(String.format("%2d ", block[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
}
