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

		Vector<Integer> numberSequence = new Vector<Integer>();

		int min_best;
		int min_begin;
		int min_end;

		int max_best;
		int max_begin;
		int max_end;

		// Set up IO system
		Kattio io = new Kattio( System.in, System.out );

		// Read input and store it
		while ( io.hasMoreTokens() ) {
			int number = io.getInt();
			numberSequence.add(number);
		}
		io.println( "Sequence: " + numberSequence );

		// Prepare the tracking variables...
		min_best = numberSequence.get(0).intValue();
		max_best = min_best;
		min_begin = 0;
		min_end = 0;
		max_begin = 0;
		max_end = 0;

		// Naive approach: find all the max and min sums in the given sequence
		for ( int i = 1; i < numberSequence.size(); i++) {

			// Use i as pivot to divide numberSequence and calculate min and max
			// on each side respectively

			// Left part -> Calculate the min sum with Kadane's algorithm
			Vector<Integer> left_subsequence = getMinSum( numberSequence.subList( 0, i ) );
			
			// Right part -> Calculate the max sum with Kadane's algorithm
			Vector<Integer> right_subsequence = getMaxSum( numberSequence.subList( i, numberSequence.size() ) );
			
			
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

		io.close();

	} // main()


	public static Vector<Integer> getMinSum( List<Integer> list ) {
		return getMaxMinSum( list, true );
	} // getMinSum()
	
	public static Vector<Integer> getMaxSum( List<Integer> list ) {
		return getMaxMinSum( list, false );
	} // getMaxSum()
	
	public static Vector<Integer> getMaxMinSum( List<Integer> list, boolean doMinSum ) {

		// Variables initialization
		int max_so_far  = list.get(0).intValue();
		int max_ending_here = max_so_far;

		int min_so_far = -list.get(0).intValue();
		int min_ending_here = min_so_far;

		// Tracking variables initialization
		int max_begin = 0;
		int max_begin_temp = 0;
		int max_end = 0;

		int min_begin = 0;
		int min_begin_temp = 0;
		int min_end = 0;

		// Find sequence by looping through
		for( int i = 1; i < list.size(); i++) {
			// calculate max_ending_here
			if ( max_ending_here < 0 ) {
				max_ending_here = list.get(i).intValue();
				max_begin_temp = i;
			} else {
				max_ending_here += list.get(i).intValue();
			}
			// calculate min_ending_here
			if ( min_ending_here < 0 ) {
				min_ending_here = -list.get(i).intValue();
				min_begin_temp = i;
			} else {
				min_ending_here += -list.get(i).intValue();
			}

			// calculate max_so_far
			if ( max_ending_here >= max_so_far ) {
				max_so_far  = max_ending_here;
				max_begin = max_begin_temp;
				max_end = i;
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
		
		if ( doMinSum ) {
			result.add(-min_so_far);
			result.add(min_begin);
			result.add(min_end);
		} else {
			result.add(max_so_far);
			result.add(max_begin);
			result.add(max_end);
		}
		
		return result;

	} // getMaxMinSum()

} // MaximumDifference{}