import Crawler.*;


/*
 * INDEX CLASS
 * 
 * It runs the project.
 * It initialise the class objects for crawl, index, search
 * 
 * */
import SearchEngine.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import SearchEngine.*;
public class Index {
	
	private static String CRAWLED_DB_DIRECTORY = System.getProperty("user.dir")+"/src/Crawler/Crawled DB/";
	private static String URL_MAP_DIRECTORY = System.getProperty("user.dir")+"/src/Crawler/Map.txt";
	
	public static void check_folders() {
		try {
			File crawl_db = new File(CRAWLED_DB_DIRECTORY);
			String[]entries = crawl_db.list();
			for(String s: entries){
			    File currentFile = new File(crawl_db.getPath(),s);
			    currentFile.delete();
			}
			
		}
		catch (Exception e) {
			//pass
		}
		
	}
	
	public static void main(String[] args) {
		Index obj =new Index();
		obj.check_folders();
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("******************************STARTING THE ENGINE******************************");
		System.out.println("Enter The Seed Url");

		
		String seed_url = sc.next();
		
		System.out.println("\nSeed Url:- "+seed_url);
		
		//SEED URL IS GIVEN TO THE MAIN CRAWLER
		MainCrawler crawl_obj = new MainCrawler(seed_url);
		
		
		
		//START THE INDEXING
		PageIndexing page_index_obj = new PageIndexing();

		System.out.println("*****************************ENGINE IS READY******************************\n");

		
		//START THE USER SEARCH
		SearchQuery search_obj = new SearchQuery (page_index_obj.WORD_URL_MAP);
		

		

	}

}
