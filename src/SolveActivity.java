import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
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
			if (current.calculateH() == 0) {
				reconstruct(current);
				draw_reconstruct();
				break;
			}

			Set<State> children = current.getChildren();

			for (State cur : children) {
				if (closed.contains(current))
					continue;
				open.add(cur);
			}
			
		}
	}
}
