public class State implements Comparable<State> {
	public static final int NUM_TILES = 9;
	public static final int ROWS = ( int ) Math.sqrt( NUM_TILES );
	public static final int COLS = ( int ) Math.sqrt( NUM_TILES );

	private int f;
	private int g;
	private int h;
	private int[][] elements;
	private State parent;

	/* 
	 * State constructor for children States, with link to its parent and calculated distance
	 * from goal State.
	 */
	public State( int[][] tiles, State parent, int g ) {
		this.parent = parent;
		this.elements = new int[ ROWS ][ COLS ];
		
		elements = tiles;
		this.g = g;
		this.h = calculateH();
		this.f = this.g + this.h;
	}
	
	/*
	 * State constructor for initial and goal set
	 */
	public State( int rows, int cols, int[][] startNums ) {
		this.elements = startNums;
		
		this.g = 0;
		this.h = calculateH();
		this.f = this.h;	//	f = 0 + h
	}

	/* Calculate the total Manhattan distance for all tiles on the current board. The Manhattan 
	 * distance for a single tile is found by subtracting the current tile’s position from the 
	 * position it would be on if the tile was in the goal position. 
	 *
	 * For instance, if Tile 4 was currently in position (2,2), the goal position for it would
	 * be in position (1,0). Thus, the Manhattan distance for a tile valued 4 in position (1,3)
	 * is abs( 2 – 1 ) + abs( 2 – 0 ). 
	 */
	public int calculateH() {
		int distance = 0;
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				int cur = elements[ i ][ j ];
				if ( cur != 0 ) {
					int goal_i = ( cur-1 ) / 3;
					int goal_j = ( cur-1 ) % 3;
					distance += Math.abs( i - goal_i ) + Math.abs( j - goal_j );
				}
			}
		}
		return distance;
	}
	
	public State moveLeft() {
		int[][] tmp = new int[ ROWS ][ COLS ];
		copyElements( tmp );
		
		//	Find the empty tile, and determine if a left-move is possible based on whether or not it's on the left edge
		for (int i = 0; i <  ROWS; i++) {
			for (int j = 0; j <  COLS ; j++) {
				if ( tmp[i][j] == 0 ) {
					if (j != 0) {
						tmp[ i ][ j ] = elements[ i ][ j - 1 ];
						tmp[ i ][ j - 1 ] = 0;
						return new State( tmp, this, getG() + 1 );
					}	
				}
			}
		}
		return null;
	}

	public State moveRight() {
		int[][] tmp = new int[ ROWS ][ COLS ];
		copyElements( tmp );
		
		for (int i = 0; i <  ROWS; i++) {
			for (int j = 0; j <  COLS ; j++) {
				if ( tmp[i][j] == 0 ) {
					if (j !=  COLS  - 1) {
						tmp[i][j] = elements[i][j+1];
						tmp[i][j+1] = 0;
						return new State( tmp, this, getG()+1);
					}
				}
			}
		}
		return null;	
	}
	
	public State moveDown() {
		int[][] tmp = new int[ ROWS ][ COLS ];
		copyElements( tmp );
		
		for (int i = 0; i <  ROWS; i++) {
			for (int j = 0; j <  COLS ; j++) {
				if ( tmp[i][j] == 0 ) {
					if (i != 0) {
						tmp[i][j] = elements[i-1][j];
						tmp[i-1][j] = 0;
						return new State( tmp, this, getG()+1);
					}
				}
			}
		}
		return null;	
	}
	
	public State moveUp() {
		int[][] tmp = new int[ ROWS ][ COLS ];
		copyElements( tmp );
		
		for (int i = 0; i <  ROWS; i++) {
			for (int j = 0; j <  COLS ; j++) {
				if ( tmp[i][j] == 0 ) {
					if (i !=  ROWS - 1) {
						tmp[i][j] = elements[i+1][j];
						tmp[i+1][j] = 0;
						return new State( tmp, this, getG()+1);
					}
				}
			}
		}
		return null;
	}
	
	public int compareTo(State a) {
		return this.h - a.getH();
	}
		
	public void draw() {
		for (int i =0; i <  ROWS; i++) {
			for (int j = 0; j <  COLS ; j++) {
				System.out.print(elements[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private void copyElements( int tmp[][]) {
		for (int i = 0; i <  ROWS; i++) {
			for (int j = 0; j <  COLS ; j++) {
				tmp[i][j] = elements[i][j];
			}
		}
	}
	
	public int getF() { return f; }
	
	public int getH() { return h; }
	
	public int getG() { return g; }
	
	public State getParent() { return parent; }
	
	public int[][] getElements() { return this.elements; }
}
