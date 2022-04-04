package Crawler;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sorting {
	private Map<String, HashMap<String, Integer>> WORD_URL_MAP = new HashMap<String, HashMap<String, Integer>>();
	
	public Sorting(Map WORD_URL_MAP){
		this.WORD_URL_MAP = WORD_URL_MAP;
		
	}
	
	public Map sort_map() {
		for (String key : WORD_URL_MAP.keySet()) {
			List<Map.Entry<String, Integer> > list =
		               new LinkedList<Map.Entry<String, Integer> >(WORD_URL_MAP.get(key).entrySet());
			
			Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
	            public int compare(Map.Entry<String, Integer> o2,
	                               Map.Entry<String, Integer> o1)
	            {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });
			
			
			HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> aa : list) {
	            temp.put(aa.getKey(), aa.getValue());
	        }
	        WORD_URL_MAP.put(key, temp);
			
		}
		
	 
	      
		return WORD_URL_MAP;
	}
	
}
