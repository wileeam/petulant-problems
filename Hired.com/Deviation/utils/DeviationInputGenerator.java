package utils;

import java.util.Random;

/*
 * Just a random input generator for the Deviation problem
 */
class DeviationInputGenerator {

	public static void main( String args[] ) {

		// Set up IO system
		Kattio io = new Kattio(System.in, System.out);

		// Hopefully you are sufficiently random...
		Random rnd = new Random();
		
		// The problem limits...
		int min_array_size = 2;
		int max_array_size = 100000;
		// Used for debugging if the maximum size of the generated array is to be limited
		if ( args.length == 1 && Integer.parseInt(args[0]) > 2 ) {
			max_array_size = Integer.parseInt(args[0]);
		}
		
		
		int lower_bound = 1;
		int upper_bound = 231-1;
		
		// Let's actually generate the random numbers
		int size_array = rnd.nextInt( ( max_array_size - min_array_size ) + 1 ) + min_array_size;
		
		for (int i = 0; i < size_array - 1; i++) {
			io.print( ( rnd.nextInt( ( upper_bound - lower_bound ) + 1 ) + lower_bound ) );
			io.print(", ");
		}
		io.println( ( rnd.nextInt( ( upper_bound - lower_bound ) + 1 ) + lower_bound ) );
		
		// We still need to provide the length of the subsequences...
		io.println( rnd.nextInt( ( size_array - min_array_size ) + 1 ) + min_array_size );
		
		io.close();
	
	} // main()

} // MaximumDifferenceInputGenerator[]
