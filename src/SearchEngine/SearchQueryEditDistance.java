package SearchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class SearchQueryEditDistance {
	
	private Map<String, HashMap<String, Integer>> WORD_URL_MAP = new HashMap<String, HashMap<String, Integer>>();
	
	public SearchQueryEditDistance(Map map) {
		this.WORD_URL_MAP = map;
	}
	public String[] get_nearest_searches(String keyword) {
		Map <String,Integer> suggestions = new HashMap<String,Integer>();
		
		
		for (String word: WORD_URL_MAP.keySet()) {
			suggestions.put(word,edit_distance(word,keyword));	
		}
		
		//COPY THE ELEMENTS OF HASHMAP INTO THE LIST
		List<Map.Entry<String, Integer> > list =
	               new LinkedList<Map.Entry<String, Integer> >(suggestions.entrySet());
		
		//SORT THE LIST USING MERGE SORT
		Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
		
	
		HashMap<String, Integer> suggestions_sorted = new LinkedHashMap<String, Integer>();
		
		//COPY THE SORTED ITEMS OF LIST INTO THE HASHMAP
		//THIS SORTED HASHMAP WILL BE USED FOR RESULTS SEARCHING
        for (Map.Entry<String, Integer> aa : list) {
        	suggestions_sorted.put(aa.getKey(), aa.getValue());
        }
        
        
		int i = 0;
		String[] arr = new String[5];
		
		
		for (String word: suggestions_sorted.keySet()) {
			if (i==5) {
				break;
			}
			if (suggestions_sorted.get(word)>5) {
				break;
			}
			if (word.length()<keyword.length()) {
				if (keyword.length()-word.length()>2) {
					break;
				}
			}
			arr[i] = word+" : "+String.valueOf(suggestions_sorted.get(word));
			i+=1;
		}
		
		if (i==0) {
			arr[0] = keyword + " : 0";;
			return Arrays.copyOfRange(arr, 0, 0);
		}
		return Arrays.copyOfRange(arr, 0, i);
		
		
	}
	public int edit_distance(String s1, String s2)
	{
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
	    int len1 = s1.length();
	    int len2 = s2.length();
	 
	    int [][]DP = new int[2][len1 + 1];
	 
	
	    for (int i = 0; i <= len1; i++)
	        DP[0][i] = i;
	 
	   
	    for (int i = 1; i <= len2; i++)
	    {
	       
	        
	        for (int j = 0; j <= len1; j++)
	        {
	           
	            
	            if (j == 0)
	                DP[i % 2][j] = i;
	 
	            
	            else if (s1.charAt(j - 1) == s2.charAt(i - 1)) {
	                DP[i % 2][j] = DP[(i - 1) % 2][j - 1];
	            }
	 
	         
	            else {
	                DP[i % 2][j] = 1 + Math.min(DP[(i - 1) % 2][j],
	                                       Math.min(DP[i % 2][j - 1],
	                                           DP[(i - 1) % 2][j - 1]));
	            }
	        }
	    }
	 
	   
	    return(DP[len2 % 2][len1]);
	}

}
