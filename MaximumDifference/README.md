Maximum Difference

Given an array of integer elements, a subsequence of this array is a set of consecutive elements from the array (i.e: given the array v: [7, 8, -3, 5, -1], a subsequence of v is 8, -3, 5)
Your task is to
write a function that finds a left and a right subsequence of the array that satisfy the following conditions
the two subsequences are unique (they don't have shared elements)
the difference between the sum of the elements in the right subsequence and the sum of the elements in the left subsequence is maximum
print the difference to the standard output (stdout)
Note that your function will receive the following arguments:
v
which is the array of integers
Data constraints
the array has at least 2 and at most 1,000,000 numbers
all the elements in the array are integer numbers in the following range: [-1000, 1000]
Efficiency constraints
your function is expected to print the result in less than 2 seconds
Example
Input	Output
v: 3, -5, 1, -2, 8, -2, 3, -2, 1
15
Explanation
The left sequence is : -5, 1, -2 and the right sequence is: 8, -2, 3.
