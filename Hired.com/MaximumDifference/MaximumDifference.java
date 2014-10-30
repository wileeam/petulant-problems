import java.util.List;
import java.util.Vector;

import utils.Kattio;

/*
 * Given an array of integer elements, a subsequence of this array is a set of 
 * consecutive elements from the array (i.e: given the array v: [7, 8, -3, 5, -1], 
 * a subsequence of v is 8, -3, 5).
 * Write a function that finds a left and a right subsequence of the array that 
 * satisfy the following conditions: 
 *  - the two subsequences are unique (they don't have shared elements)
 *  - the difference between the sum of the elements in the right subsequence and 
 *    the sum of the elements in the left subsequence is maximum
 * 
 * Print the difference to the standard output (stdout)
 * The function will receive the following arguments:
 *  - v
 * which is the array of integers
 * 
 * Data constraints:
 *  - the array has at least 2 and at most 1,000,000 numbers
 *  - all the elements in the array are integer numbers in the following range: 
 *    [-1000, 1000]
 * 
 * Efficiency constraints
 *  - the function is expected to print the result in less than 2 seconds
 * 
 * Example
 * Input
 *  - v: 3, -5, 1, -2, 8, -2, 3, -2, 1
 * Output
 *  - 15
 * 
 * Explanation
 * The left sequence is : -5, 1, -2 and the right sequence is: 8, -2, 3.
 */
class MaximumDifference {
	
	public static void main( String args[] ) {

		// Control variable for debugging
		boolean VERBOSE = false;

		// Variable where to save the input
		Vector<Integer> numberSequence = new Vector<Integer>();

		// Variables tracking the best result for each subsequence so far
		Vector<Integer> left_best_subsequence = new Vector<Integer>();
		Vector<Integer> right_best_subsequence = new Vector<Integer>();
		
		// Set up IO system
		Kattio io = new Kattio( System.in, System.out );

		// Read input and store it
		while ( io.hasMoreTokens() ) {
			int number = io.getInt();
			numberSequence.add(number);
		}
		
		if (VERBOSE) {
			io.println( "Sequence: " + numberSequence );
		}
		
		// Prepare tracking variables
		// For the left subsequence, the first best sum is just the first number in the input sequence (indexes don't matter)
		left_best_subsequence.add(numberSequence.get(0));
		left_best_subsequence.add(0);
		left_best_subsequence.add(0);
		// For the right subsequence, the first best sum is just the last number in the input sequence (indexes don't matter)
		right_best_subsequence.add(numberSequence.get(numberSequence.size()-1));
		right_best_subsequence.add(numberSequence.size()-1);
		right_best_subsequence.add(numberSequence.size()-1);

		// Naive approach: find all the max and min sums in the given sequence
		for ( int i = 1; i < numberSequence.size(); i++) {
			// Use i as pivot to divide numberSequence and calculate min and max
			// on each side respectively

			// Left part -> Calculate the min sum with Kadane's algorithm
			Vector<Integer> left_subsequence = getMinSum( numberSequence.subList( 0, i ) );
			
			// Right part -> Calculate the max sum with Kadane's algorithm
			Vector<Integer> right_subsequence = getMaxSum( numberSequence.subList( i, numberSequence.size() ) );
			
			// Now check whether the current subsequences are any better than what we already have
			if ( left_subsequence.get(0).compareTo( left_best_subsequence.get(0) ) < 0 ) {
				left_best_subsequence = left_subsequence;
			}
			if ( right_subsequence.get(0).compareTo( right_best_subsequence.get(0) ) > 0 ) {
				right_best_subsequence = right_subsequence;
			}
			
			if (VERBOSE) {
				io.println( i
					+ ".\tCurrent results - Min: "
					+ left_subsequence.get(0)
					+ " :: "
					+ numberSequence.subList( 0, i ).subList(left_subsequence.get(1), left_subsequence.get(2)+1)
				);
				io.println( "\tCurrent results - Max: "
					+ right_subsequence.get(0)
					+ " :: "
					+ numberSequence.subList( i, numberSequence.size() ).subList(right_subsequence.get(1), right_subsequence.get(2)+1)
				);
				io.println( "\tCurrent difference: "
					+ (right_subsequence.get(0)-left_subsequence.get(0))
				);
			}
		}

		if (VERBOSE) {
			io.println();
			io.println("\tMin: "
				+ left_best_subsequence.get(0)
				+ " :: "
				+ numberSequence.subList(left_best_subsequence.get(1), left_best_subsequence.get(2)+1)
			);
			io.println("\tMax: "
				+ right_best_subsequence.get(0)
				+ " :: "
				+ numberSequence.subList(right_best_subsequence.get(1), right_best_subsequence.get(2)+1)
			);
		}
		
		// Output maximal difference...
		io.println(right_best_subsequence.get(0)-left_best_subsequence.get(0));

		io.close();

	} // main()


	public static Vector<Integer> getMinSum( List<Integer> list ) {

		// Variables initialization
		int min_so_far = -list.get(0).intValue();
		int min_ending_here = min_so_far;

		// Tracking variables initialization
		int min_begin = 0;
		int min_begin_temp = 0;
		int min_end = 0;

		// Find sequence by looping through
		for( int i = 1; i < list.size(); i++) {
			// calculate min_ending_here
			if ( min_ending_here < 0 ) {
				min_ending_here = -list.get(i).intValue();
				min_begin_temp = i;
			} else {
				min_ending_here += -list.get(i).intValue();
			}

			// calculate min_so_far
			if ( min_ending_here >= min_so_far ) {
				min_so_far = min_ending_here;
				min_begin = min_begin_temp;
				min_end = i;
			}
		}

		// Create and fill vector with results
		Vector<Integer> result = new Vector<Integer>();
		result.add(-min_so_far);
		result.add(min_begin);
		result.add(min_end);
		
		return result;

	} // getMinSum()
	
	public static Vector<Integer> getMaxSum( List<Integer> list ) {

		// Variables initialization
		int max_so_far  = list.get(0).intValue();
		int max_ending_here = max_so_far;

		// Tracking variables initialization
		int max_begin = 0;
		int max_begin_temp = 0;
		int max_end = 0;

		// Find sequence by looping through
		for( int i = 1; i < list.size(); i++) {
			// calculate max_ending_here
			if ( max_ending_here < 0 ) {
				max_ending_here = list.get(i).intValue();
				max_begin_temp = i;
			} else {
				max_ending_here += list.get(i).intValue();
			}

			// calculate max_so_far
			if ( max_ending_here >= max_so_far ) {
				max_so_far  = max_ending_here;
				max_begin = max_begin_temp;
				max_end = i;
			}
		}

		// Create and fill vector with results
		Vector<Integer> result = new Vector<Integer>();
		result.add(max_so_far);
		result.add(max_begin);
		result.add(max_end);
		
		return result;

	} // getMaxSum()

} // MaximumDifference{}

