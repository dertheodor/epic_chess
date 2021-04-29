// Purpose.  Command design pattern - decoupling producer from consumer

import java.util.*;

/**
 * simple test with command queue pattern
 * modified by ptp
 *
 */
public class CommandQueue {

    interface Command { void execute(); }

    static class DomesticEngineer implements Command {
	public void execute() {
	    System.out.println( "take out the trash" );
	}  }

    static class Politician implements Command {
	public void execute() {
	    System.out.println( "take money from the rich, take votes from the poor" );
	}  }

    static class Programmer implements Command {
	public void execute() {
	    System.out.println( "sell the bugs, charge extra for the fixes" );
	}  }

    static class Mathematics implements Command {
	public void execute() {
	    for (int i=0; i<5; i++) {
		System.out.println("sin ("+i+") = "+Math.sin(i*3.14));
	    }
	    System.out.println( "finish" );}
    }

    public static List<Command> produceRequests() {
	List<Command> queue = new ArrayList<Command>();
	queue.add( new DomesticEngineer() );
	queue.add( new Politician() );
	queue.add( new Programmer() );
	queue.add( new Mathematics() );

	return queue;
    }

    public static void workOffRequests( List<Command> queue ) {
	for (Iterator<Command> it = queue.iterator(); it.hasNext(); )
	    it.next().execute();
    }

    public static void main( String[] args ) {
	List<Command> queue = produceRequests();
	workOffRequests( queue );
    }}

// take out the trash
// take money from the rich, take votes from the poor
// sell the bugs, charge extra for the fixes

