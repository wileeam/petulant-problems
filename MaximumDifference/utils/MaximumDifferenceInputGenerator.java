package utils;

import java.util.Random;

/*
 * Just a random input generator for the MaximumDifference problem
 */
class MaximumDifferenceInputGenerator {

	public static void main( String args[] ) {

		Kattio io = new Kattio(System.in, System.out);

		// Hopefully you are sufficiently random...
		Random rnd = new Random();
		
		// The problem limits...
		int min_array_size = 2;
		int max_array_size = 1000000;
		max_array_size = 17;
		
		int lower_bound = -1000;
		int upper_bound = 1000;
		
		// Let's actually generate the random numbers
		int size_array = rnd.nextInt( ( max_array_size - min_array_size ) + 1 ) + min_array_size;
		
		for (int i = 0; i < size_array - 1; i++) {
			io.print( ( rnd.nextInt( ( upper_bound - lower_bound ) + 1 ) + lower_bound ) );
			io.print(", ");
		}
		io.println( ( rnd.nextInt( ( upper_bound - lower_bound ) + 1 ) + lower_bound ) );

		io.close();
	
	} // main()

} // MaximumDifferenceInputGenerator[]