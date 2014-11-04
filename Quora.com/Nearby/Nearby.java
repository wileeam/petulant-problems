import java.io.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/*
 * Nearby problem constraints
 *
 * Topic    T        : 1 <= T <= 10000
 * Question Q        : 1 <= Q <= 1000
 * Query    N        : 1 <= N <= 10000
 * Coordinates (x,y) : 0.0 <= x,y <= 1000000.0 (10^6)
 *
 * Integer ids are between 0 and 100000 inclusive.
 * Number of topics associated with a question is not more than 10.
 * The number of results required for a query is not more than 100.
 * For the large testcases, all topic x,y co-ordinates will be approximately
 * uniformly distributed over the bounds.
 */

// TODO: Clean up
// TODO: Test with corner cases (specially non-existant ids; ie, 0)
// TODO; Document properly

public class Nearby {
	
	public static void main( String args[] ) throws Exception {
		
		// Variables where to save the input
		Grid planeGrid = new Grid();
		
		// Set up IO system
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

		// Parameter settings set up
		String[] parameters = br.readLine().split("\\s");
		Integer t = Integer.valueOf(parameters[0]);
		Integer q = Integer.valueOf(parameters[1]);
		Integer n = Integer.valueOf(parameters[2]);
		
		// Topics set up
		for (int i = 0; i < t; i++) {
			String[] topic = br.readLine().split("\\s");
			planeGrid.addTopic( Integer.valueOf(topic[0]), 
								Double.valueOf(topic[1]),
								Double.valueOf(topic[2])
							  );
		}
		
		// Questions set up
		for (int j = 0; j < q; j++ ) {
			String[] question = br.readLine().split("\\s");
			Integer[] topicsForQuestion = new Integer[question.length - 1];
			for (int jj = 0; jj < question.length - 1; jj++) {
				topicsForQuestion[jj] = Integer.valueOf(question[jj+1]);
			}
			planeGrid.addQuestion( Integer.valueOf(question[0]),
								   topicsForQuestion
								 );
		}
		
		// Queries set up, execution and output
		for (int k = 0; k < n; k++) {
			String[] query = br.readLine().split("\\s");
			
			Character queryType = Character.valueOf( query[0].charAt(0) );
			Integer queryResults = Integer.valueOf( query[1] );
			Double queryXCoordinate = Double.valueOf( query[2] );
			Double queryYCoordinate = Double.valueOf( query[3] );
			
			if ( queryType == 't') { // Topic query
				
				// Retrieve the closest topics up to queryResults
				// Retrieval in concentric squares, so we have a guarantee
				// that the right ones will be in the set (we will likely get
				// a few more than needed though, but not too many as the 
				// distribution of topics in the plane is uniform... if not we 
				// should use another strategy to create the subgrids and the 
				// query process)
				// So in the end, we have at least an upper boundary :)
				HashMap<Topic, Double> resultTopics = planeGrid.getNearbyTopics( queryXCoordinate, queryYCoordinate, queryResults );
				
				ArrayList<Map.Entry<Topic, Double>> sortedTopics = sortTopicsByDistance( resultTopics );
				for (int i = 0; i < Math.min( sortedTopics.size(), queryResults) ; i++) {
					//System.out.println( sortedTopics.get(i).getKey() + " - " + sortedTopics.get(i).getValue());
					System.out.print( sortedTopics.get(i).getKey().getId() );
					System.out.print( " " );
				}
				System.out.println("");
			} else { // Question query instead
				// Query (we get candidate questions and distances)
				HashMap<Integer, Double> resultQuestions = planeGrid.getNearbyQuestions( queryXCoordinate, queryYCoordinate, queryResults );
				ArrayList<Map.Entry<Integer, Double>> sortedQuestions = sortQuestionsByDistance( resultQuestions );
				for (int i = 0; i < Math.min( sortedQuestions.size(), queryResults ); i++) {
					//System.out.println( sortedQuestions.get(i).getKey() + " - " + sortedQuestions.get(i).getValue());
					System.out.print( sortedQuestions.get(i).getKey() );
					System.out.print( " " );
				}
				System.out.println("");
				
			}
		}
		
		
	}
	
	public static ArrayList<Map.Entry<Topic, Double>> sortTopicsByDistance( HashMap<Topic, Double> unsortedMap ) {
		
		// First convert the map to a list
		ArrayList<Map.Entry<Topic, Double>> mapList = new ArrayList<Map.Entry<Topic, Double>>( unsortedMap.entrySet() );
		
		// Sort list with comparator, to compare the Map values
		Collections.sort( mapList, new Comparator<Map.Entry<Topic, Double>>() {
			public int compare( Map.Entry<Topic, Double> o1, Map.Entry<Topic, Double> o2) {
				
				// Threshold for distance's topic comparison is 0.001
				Double d1 = o1.getValue() * 1000;
				Double d2 = o2.getValue() * 1000;
				
				// Pseudo-comparison (see next if-else)
				int numericalComparison = Integer.compare( d1.intValue(), d2.intValue() );
				
				// If distances between two topics and query topic are equal
				// then the largest topic id wins the battle
				if ( numericalComparison == 0 ) {
					Integer t1 = o1.getKey().getId();
					Integer t2 = o2.getKey().getId();
					
					return -( t1.compareTo( t2 ) );
				} else {
					return numericalComparison;
				}
			}
		});
 		
		return mapList;
		
	}
	
	public static ArrayList<Map.Entry<Integer, Double>> sortQuestionsByDistance( HashMap<Integer, Double> unsortedMap ) {
		
		// First convert the map to a list
		ArrayList<Map.Entry<Integer, Double>> mapList = new ArrayList<Map.Entry<Integer, Double>>( unsortedMap.entrySet() );
		
		// Sort list with comparator, to compare the Map values
		Collections.sort( mapList, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare( Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				
				// Threshold for distance's question comparison is 0.001
				Double d1 = o1.getValue() * 1000;
				Double d2 = o2.getValue() * 1000;
				
				// Pseudo-comparison (see next if-else)
				int numericalComparison = Integer.compare( d1.intValue(), d2.intValue() );
				
				// If distances between two questions and query question are equal
				// then the largest question id wins the battle
				if ( numericalComparison == 0 ) {
					Integer q1 = o1.getKey();
					Integer q2 = o2.getKey();
					
					return -( q1.compareTo( q2 ) );
				} else {
					return numericalComparison;
				}
			}
		});
 		
		return mapList;
		
	}
	
}

class Topic {
	
	Integer id;
	Double x;
	Double y;
	ArrayList<Integer> questionsList;
	
	public Topic(Integer id, Double x, Double y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.questionsList = new ArrayList<Integer>();
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public ArrayList<Integer> getQuestionsList() {
		return this.questionsList;
	}
	
	public void addQuestion( Integer id ) {
		this.questionsList.add( id );
	}
	
	public Double getDistance( Double x, Double y ) {
		
		Double xDistance = x - this.x;
		Double yDistance = y - this.y;
		
		return Math.sqrt( ( xDistance * xDistance ) + ( yDistance * yDistance ) );
	}
	
	public String toString() {
		return "T - id: " + this.id + " | (" + this.x + "," + this.y + ") | " + this.questionsList;
	}
	
}


class Grid {
	
	HashMap<Integer, HashMap<Integer, ArrayList<Topic>>> grid;
	
	// For convenience... and efficiency too
	HashMap<Integer, Topic> topicsList;
	
	static Integer subGridLength;
	static Integer maxGridLength;
	static Integer totalSubGrids;
	
	
	public Grid() {
		
		this.grid = new HashMap<Integer, HashMap<Integer, ArrayList<Topic>>>();
		
		this.topicsList = new HashMap<Integer, Topic>();
		
		// Subgrid size 20000 x 20000 (allocating 4 topics in average)		
		this.subGridLength = 20000;
		this.maxGridLength = 1000000;
		
		this.totalSubGrids = this.maxGridLength / this.subGridLength;
		
	}
	
	public void addTopic( Integer id, Double x, Double y ) {
		
		Integer xIndex = x.intValue() / this.subGridLength;
		Integer yIndex = y.intValue() / this.subGridLength;
		
		// Create topic and add it to list of topics
		Topic topic = new Topic( id, x, y );
		this.topicsList.put( topic.getId(), topic );

		if ( this.grid.containsKey( xIndex ) ) {
			HashMap<Integer, ArrayList<Topic>> helperGrid = this.grid.get( xIndex );
			if ( helperGrid.containsKey( yIndex ) ) {
				helperGrid.get( yIndex ).add( topic );
			} else {
				ArrayList<Topic> topicList = new ArrayList<Topic>();
				topicList.add( topic );
				helperGrid.put( yIndex, topicList );
			}
		} else {
			ArrayList<Topic> topicList = new ArrayList<Topic>();
			topicList.add( topic );
			
			HashMap<Integer, ArrayList<Topic>> helperGrid = new HashMap<Integer, ArrayList<Topic>>();
			helperGrid.put( yIndex, topicList );
			
			this.grid.put( xIndex, helperGrid );
		}
	}
	
	public boolean addQuestion( Integer id, Integer[] topics ) {
		
		// Check that the there are no topics associated with the question
		if ( topics.length == 1 && topics[0] == 0 ) {
			return false;
		}
		
		// Now... a simple thing, create the question and add it to each topic
		for ( int i = 0; i < topics.length; i++ ) {
			this.topicsList.get( topics[i] ).addQuestion( id );
		}
		return true;
		
	}
	
	public ArrayList<Topic> getNearbyTopicsInLevel( Integer level, Double x, Double y ) {

		if ( level < 0 || level >= this.totalSubGrids ) {
			return null;
		}
		
		ArrayList<Topic> nearbyTopicList = new ArrayList<Topic>();
		
		Integer xIndex = x.intValue() / this.subGridLength;
		Integer yIndex = y.intValue() / this.subGridLength;
		
		// Get upper and lower row
		int startXInterval = Math.max( 0, xIndex - level );
		int endXInterval = Math.min( this.totalSubGrids, xIndex + level );
		
		for ( int i = startXInterval; i <= endXInterval; i++ ) {
			// Make sure the X point exists
			if ( this.grid.containsKey( i ) ) {
				// Get northern subgrid (y)
				if ( this.grid.get( i ).containsKey( yIndex + level ) ) {
					nearbyTopicList.addAll( this.grid.get( i ).get( yIndex + level ) );
				}
				// Get southern subgrid (-y)
				if ( this.grid.get( i ).containsKey( yIndex - level ) ) {
					nearbyTopicList.addAll( this.grid.get( i ).get( yIndex - level ) );
				}
			}
		}
			
		int startYInterval = Math.max( 0, yIndex - ( level - 1 ) );
		int endYInterval = Math.min( this.totalSubGrids, yIndex + ( level - 1 ) );
		
		for ( int j = startYInterval; j <= endYInterval; j++ ) {
			// Make sure the X point exists
			if ( this.grid.containsKey( xIndex + level ) ) {
				// Get eastern subgrid (y)
				if ( this.grid.get( xIndex + level ).containsKey( j ) ) {
					nearbyTopicList.addAll( this.grid.get( xIndex + level ).get( j ) );
				}
			}
			
			if ( this.grid.containsKey( xIndex - level ) ) {
				// Get western subgrid (-y)
				if ( this.grid.get( xIndex - level ).containsKey( j ) ) {
					nearbyTopicList.addAll( this.grid.get( xIndex - level ).get( j ) );
				}
			}
		}
		
		return nearbyTopicList;
		
	}
	
	public HashMap<Topic, Double> getNearbyTopics( Double x, Double y, Integer maxResults ) {
		
		HashMap<Topic, Double> nearbyTopicList = new HashMap<Topic, Double>();
		
		int level = 0;
		while ( nearbyTopicList.size() < maxResults && level < this.totalSubGrids ) {
		
			ArrayList<Topic> candidateTopicList = this.getNearbyTopicsInLevel( level, x, y );
			for ( int i = 0; i < candidateTopicList.size(); i++ ) {
				Topic candidateTopic = candidateTopicList.get(i);
				Double distance = candidateTopic.getDistance( x, y );
				
				nearbyTopicList.put( candidateTopic, distance );
			}
			
			level++;
		}
			
		return nearbyTopicList;
		
	}
	
	public HashMap<Integer, Double> getNearbyQuestions( Double x, Double y, Integer maxResults ) {
		
		HashMap<Integer, Double> nearbyQuestionList = new HashMap<Integer, Double>();
		
		int level = 0;
		while ( nearbyQuestionList.size() < maxResults && level < this.totalSubGrids ) {
		
			ArrayList<Topic> candidateTopicList = this.getNearbyTopicsInLevel( level, x, y );
			for ( int i = 0; i < candidateTopicList.size(); i++ ) {
				Double distance = candidateTopicList.get(i).getDistance( x, y );
				ArrayList<Integer> candidateQuestionsPerTopic = candidateTopicList.get(i).getQuestionsList();
				for ( int j = 0; j < candidateQuestionsPerTopic.size(); j++ ) {
					Integer candidateQuestion = candidateQuestionsPerTopic.get(j);
					// First check if the question is already a candidate
					if ( nearbyQuestionList.containsKey( candidateQuestion ) ) {
						// Question is stored already... best distance?
						if ( nearbyQuestionList.get( candidateQuestion ) > distance ) {
							nearbyQuestionList.put( candidateQuestion, distance );
						}
					} else {
						// Candidate question is not there... add it
						nearbyQuestionList.put( candidateQuestion, distance );
					}
				}
			}
			
			level++;
			
		}
		
		// At this point we have a list of tuples of ids and distances
		// We don't care about linking back to the topics because it is not
		// needed, but it could be implemented easily
		return nearbyQuestionList;
		
	}
	
	public String topicsListToString() {
		
		String res = "";
		
		
		Iterator<Topic> it = this.topicsList.values().iterator();
		while ( it.hasNext() ) {
			res += it.next().toString();
		}
		
		return res;
		
	}
	
	public String toString() {
		
		String res = "";
		
		//	HashMap<Integer, HashMap<Integer, ArrayList<Topic>>> grid;
		Iterator it = this.grid.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry) it.next();
			res += pairs.getKey() + " = " + pairs.getValue() + "\n";
		}
		
		return res;
	} 
	
}

