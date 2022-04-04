package SearchEngine;

import SearchEngine.SearchQueryEditDistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;



/*
 * SEARCHING : 
 * IMPLEMENTED BY : Prabhangad Singh
 * This class handles the user seach input queries.
 * 
 * The user search is searched first into the index.
 * If the results are there then it gets returned.
 * Otherwise we try to find the suggestions using the Edit distance Algorithm.
 * 
 * 
 * 
 * */
public class SearchQuery {
	
	private Map<String, HashMap<String, Integer>> WORD_URL_MAP = new HashMap<String, HashMap<String, Integer>>();
	private Map<String, String> URL_MAP = new HashMap<String, String>();
	private  String URL_MAP_DIRECTORY = System.getProperty("user.dir")+"/src/Crawler/Map.txt";
	private String search_keyword ;
	
	public SearchQuery(Map map){
		try {
			File file = new File(URL_MAP_DIRECTORY);
            BufferedReader br = new BufferedReader(new FileReader(file));
  
            String line = null;
  
            
            while ((line = br.readLine()) != null) {
  
               line = line.toString();
                String[] parts = line.split(".url.");
                
  
                
                String key = parts[0].trim();
                String value = parts[1].trim();
  
                
                
                	URL_MAP.put(key, value);
            }
		}
		catch (Exception e) {
			System.out.println("Exception doing this "+e);
		}
		
		
		this.WORD_URL_MAP = map;
		System.out.println("*****************************USER QUERY SEARCH******************************\n");
		Scanner sc = new Scanner(System.in);
		
		SearchQueryEditDistance edit_d_obj = new SearchQueryEditDistance(map);
		
		while (true){
			//TAKE INPUT FROM THE USER
			System.out.println("\nEnter a search keyword\n-----------------------------");
			String keyword = sc.next();
			
			//FIND ITS POSSIBLE SUGGESSTION
			String auto_suggestions[] = edit_d_obj.get_nearest_searches(keyword);
			
			try {
				if (WORD_URL_MAP.get(keyword) == null) {
					System.out.println("Sorry! Your Search Did Not Match Any Documents");
					System.out.println("Suggestions:- \n•Make sure that all words are spelled correctly\n•Try different keywords\n•Try more general keywords\n");
					
					
					
					if (auto_suggestions.length >=1) {
					
						System.out.println("Suggested Search Keywords : "+Arrays.toString(auto_suggestions));
						keyword = auto_suggestions[0];
						keyword = keyword.split(":")[0].replace(" ", "");
						System.out.println("Showing Results For : "+ keyword );
					}
				}
				
				
				if (WORD_URL_MAP.get(keyword) != null) {
					
					Iterator Iterator = WORD_URL_MAP.get(keyword).entrySet().iterator();
					
					while (Iterator.hasNext()) {
						try {
							String url = Iterator.next().toString();
							
							url = url.split(".txt=")[0];

							url = URL_MAP.get(url);
							if (url == null) {
								System.out.println(Iterator.next());
							}
							else System.out.println(url);
						}
						catch (Exception ie) {
							System.out.println(Iterator.next());
						}
						
					}
				}
				
				
			}
			catch (Exception E) {
				System.out.println("Sorry! Your Search Did Not Match Any Documents");
			}
		}
		
	}
	
	

}
