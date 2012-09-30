import java.util.Set;
import java.util.HashSet;

public class State implements Comparable<State> {
	public static final int NUM_TILES = 9;
	
	private int ROWS;
	private int COLS;
	private int f;
	private int g;
	private int h;
	private Square[][] elements;
	private State parent;

	/* 
	 * State constructor for children States with calculated g-score
	 */
	public State(int rows, int cols, int[][] startNums, State parent, int g) {
		this.ROWS = rows;
		this.COLS = cols;
		this.parent = parent;
		this.elements = new Square[ ROWS ][ COLS ];
		
		for (int i = 0; i < this.ROWS; i++) 
			for (int j = 0; j < this.COLS; j++) 
				elements[ i ][ j ] = new Square( i, j, startNums[ i ][ j ]);

		this.g = g;
		this.h = calculateH();
		this.f = this.g + this.h;
	}
	
	/*
	 * State constructor for initial board set
	 */
	public State(int rows, int cols, int[] startNums) {
		this.ROWS = rows;
		this.COLS = cols;
		
		int cur = 0;
		this.elements = new Square[ rows ][ cols ];
		
		for (int i = 0; i < rows; i++) 
			for (int j = 0; j < cols; j++) 
				elements[ i ][ j ] = new Square( i, j, startNums[ cur++ ]);
		
		this.g = 0;
		this.h = calculateH();
		this.f = this.h;	//	f = g + h
	}
	
	public int calculateH() {
		int distance = 0;
		
		for (int i = 0; i < this.ROWS; i++) {
			for (int j = 0; j < 3; j++) {
				int cur = elements[ i ][ j ].getNum();
				if (cur != 0) {
					int target_i = ( cur-1 ) / 3;
					int target_j = ( cur-1 ) % 3;
					distance += Math.abs( i - target_i ) + Math.abs( j - target_j );
				}
			}
		}
		return distance;
	}
	
	public Set<State> getChildren() {
		Set<State> children = new HashSet<State>();
		
		int[][] left_num= new int[getRows()][getCols()];
		int[][] right_num= new int[getRows()][getCols()];
		int[][] down_num= new int[getRows()][getCols()];
		int[][] up_num= new int[getRows()][getCols()];
		
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				left_num[ i ][ j ] = elements[ i ][ j ].getNum();
				right_num[ i ][ j ] = elements[ i ][ j ].getNum();
				down_num[ i ][ j ] = elements[ i ][ j] .getNum();
				up_num[ i ][ j ] = elements[ i ][ j ].getNum();
			}
		}
		
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				int square_num = elements[i][j].getNum();

				if (square_num == 0) {
					if (j != 0) {
						left_num[i][j] = elements[i][j-1].getNum();
						left_num[i][j-1] = 0;
						children.add(new State(getRows(), getCols(), left_num, this, getG()+1));
					}	
					if (j != getCols() - 1) {
						right_num[i][j] = elements[i][j+1].getNum();
						right_num[i][j+1] = 0;
						children.add(new State(getRows(), getCols(), right_num, this, getG()+1));
					}
					if (i != 0) {
						up_num[i][j] = elements[i-1][j].getNum();
						up_num[i-1][j] = 0;
						children.add(new State(getRows(), getCols(), up_num, this, getG()+1));
					}
					if (i != getRows() - 1) {
						down_num[i][j] = elements[i+1][j].getNum();
						down_num[i+1][j] = 0;
						children.add(new State(getRows(), getCols(), down_num, this, getG()+1));
					}
				}
			}
		}
		return children;
	}
	
	public int compareTo(State a) {
		return this.h - a.getH();
	}
		
	public void draw() {
		for (int i =0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				System.out.print(elements[i][j].getNum() + " ");
			}
			System.out.println();
		}
	}
	
	// ***************************************************************************************************************************************************
	// Getter methods
	// ***************************************************************************************************************************************************
	public int getF() {	return f; }
	
	public int getH() { return h; }
	
	public int getG() { return g; }
	
	public int getRows() { return this.ROWS; }
	
	public int getCols() { return this.COLS; }
	
	public State getParent() { return parent; }
	
	public Square[][] getElements() { return this.elements; }
}
