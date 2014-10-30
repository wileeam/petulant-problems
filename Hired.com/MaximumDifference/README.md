# Maximum Difference #

Given an array of integer elements, a subsequence of this array is a set of  consecutive elements from the array (i.e: given the array v: [7, 8, -3, 5, -1], a subsequence of v is 8, -3, 5).

Write a function that finds a left and a right subsequence of the array that  satisfy the following conditions:

 * the two subsequences are unique (they don't have shared elements)
 * the difference between the sum of the elements in the right subsequence and the sum of the elements in the left subsequence is maximum

The function will receive the following arguments:

 * v (which is the array of integers)

Print the difference to the standard output (stdout)

### Data constraints ###

 * the array has at least 2 and at most 1,000,000 numbers
 * all the elements in the array are integer numbers in the following range:  [-1000, 1000]
 
### Efficiency constraints ###

 * the function is expected to print the result in less than 2 seconds
 
## Example ##
#### Input ####

 * v: 3, -5, 1, -2, 8, -2, 3, -2, 1

#### Output ####

 * 15
 
#### Explanation ####
The left sequence is: -5, 1, -2 and the right sequence is: 8, -2, 3.