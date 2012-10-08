import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ListIterator;

public class SolveActivity {
	State start;
	State goal;
	
	PriorityQueue<State> open = new PriorityQueue<State>();
	Queue<State> closed = new LinkedList<State>();
	LinkedList<State> reconstructed = new LinkedList<State>();
	
	public SolveActivity(State start, State goal) {
		this.start = start;
		this.goal = goal;
	}
	
	public void reconstruct(State current) {
		do {
			reconstructed.add(current);
			current = current.getParent();
		} while (current != null);
	}
	
	public void draw_reconstruct() {
		int state_count = 0;
		ListIterator<State> iter = reconstructed.listIterator(reconstructed.size());
		while (iter.hasPrevious()) {
			System.out.println("State " + state_count++);
			((State)iter.previous()).draw();
			System.out.println();
		}
		
	}
	
	public void findBestPath() {
		open.add(start);		

		while (open.size() > 0) {
			State current = open.poll();
			closed.add(current);
			
			if (current.calculateH() == 0) {
				reconstruct(current);
				draw_reconstruct();
				break;
			}

			State moveUp = current.moveUp();
			State moveDown = current.moveDown();
			State moveRight = current.moveRight();
			State moveLeft = current.moveLeft();
			
			if ( moveUp != null && !closed.contains( moveUp ) )
				open.add( moveUp );
			if ( moveDown != null && !closed.contains( moveDown ))
				open.add ( moveDown );
			if ( moveRight != null && !closed.contains( moveRight ))
				open.add ( moveRight );
			if ( moveLeft != null && !closed.contains( moveLeft ))
				open.add ( moveLeft );
			
		}
	}
}
