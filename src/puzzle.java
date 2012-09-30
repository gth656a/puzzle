
public class puzzle{
	
	public static void main(String args[]) {
		final int rows = 3;
		final int cols = 3;

		int[] startNums= {1,2,3,4,0,6,7,5,8};
		int[] goalNums= {1,2,3,4,5,6,7,8,0};

		State goal = new State( rows, cols, goalNums );
		State start = new State( rows, cols, startNums );

		SolveActivity sa = new SolveActivity( start, goal );
		sa.findBestPath();
		}
}
