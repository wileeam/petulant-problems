import java.util.List;
import java.util.Vector;
import java.util.ArrayDeque;

import utils.Kattio;

class Deviation {
	
	public static void main( String args[] ) {
		
		// Control variable for debugging
		boolean VERBOSE = false;
		
		// Variables where to save the input
		Vector<Integer> sequence = new Vector<Integer>();
		Integer d;
		
		// Set up IO system
		Kattio io = new Kattio( System.in, System.out );
		
		// Read input and store it
		while ( io.hasMoreTokens() ) {
			int number = io.getInt();
			sequence.add(number);
		}
		// Last read number corresponds to d and not to the sequence...
		d = sequence.remove(sequence.size() - 1);
		
		if (VERBOSE) {
			io.println( "v: " + sequence );
			io.println( "d: " + d );
		}

		// Deviation tracking variable
		Integer deviation = 0;
		
		// And let's get started with the analysis
		//
		// The total number of subsequences will be n - (d - 1)
		for (int start = 0, end = ( d - 1 ); end < sequence.size(); start++, end++) {
			// Tracking variables
			Integer max = 1;
			Integer min = 231 - 1;
			for (Integer j = start; j <= end; j++) {
				if ( sequence.get(j) > max ) {
					max = sequence.get(j);
				} else if ( sequence.get(j) < min ) {
					min = sequence.get(j);
				}
			}
			if ( ( max - min ) > deviation ) {
				deviation = max - min;
			}
			if (VERBOSE) {
				io.println( "Subsequence[" + start + ".." + end + "]" );
				io.println( "  MAX: " + max );
				io.println( "  MIN: " + min );
				io.println( "  DEV: " + ( max - min ) );
			}
		}
		
		// Output maximum deviation...
		io.println(deviation);
		
		io.close();
		
	} // main()

} // Deviation{}
