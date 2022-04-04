package Crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/* 
 * MAIN CRAWLER
 * 
 * IMPLEMENTED BY : 
 * WARID & DHARANI
 * 
 * */
public class MainCrawler {
	
	
	private String SEED_URL ;
	private HashSet <String> SCRAPED_URLS;
	private HashMap<String, String> URL_FILE_MAP = new HashMap<String,String>();
	private String REGEX_URL_FILTER ;
	private String CRAWLED_DB_DIRECTORY = System.getProperty("user.dir")+"/src/Crawler/Crawled DB/";
	private  String URL_MAP_DIRECTORY = System.getProperty("user.dir")+"/src/Crawler/Map.txt";
	private static int CRAWL_DEPTH = 0;
	
	public MainCrawler(String seed_url) {
		System.out.println("******************************CRAWLING STARTED******************************");
		
		SCRAPED_URLS = new HashSet<String> ();
		REGEX_URL_FILTER = "((http|https)://)(www.)?"
	              + "[a-zA-Z0-9@:%._\\+~#?&//=]"
	              + "{2,256}\\.[a-z]"
	              + "{2,6}\\b([-a-zA-Z0-9@:%"
	              + "._\\+~#?&//=]*)";
		SEED_URL = seed_url;
		
		Path path = Paths.get(CRAWLED_DB_DIRECTORY);
		if (!Files.exists(path)) {
			
			File dir_path = new File(CRAWLED_DB_DIRECTORY);
			dir_path.mkdir();
			
			File html_dir_path = new File(URL_MAP_DIRECTORY);
			html_dir_path.mkdir();
			System.out.println("Crawled DB Directory Created");
		}
		 main();

		
	}
	//MAIN SCRAPE METHOD
	public void scrape (String url) {
		
		//CRAWL DEPTH CHECK
		if (CRAWL_DEPTH ==60)return;

		//IF THE URL IS ALREADY SCRAPED ..DONT SCRAPE IT AGAIN
		if (SCRAPED_URLS.contains(url))return;
				
		try {
			//SCRAPING USING JSOUP
			Document html_doc = Jsoup.connect(url).get();
			Elements links = html_doc.select("a[href]");
			SCRAPED_URLS.add(url);
			try {
				System.out.println("Crawled URL -"+url);
					String html = html_doc.html();
					//HTML TO TEXT PARSE
					String html_doc_text = html_doc.text();
					String file_name = url.replaceAll("[^a-zA-Z0-9]+","-");
					
					URL_FILE_MAP.put(file_name,url);
					//WRITING THE FILE BACK INTO THE SYSTEM DIRECTORY
					file_name = CRAWLED_DB_DIRECTORY+file_name+".txt";
					BufferedWriter writer = new BufferedWriter (new FileWriter(file_name));
					writer.write(html_doc_text);
					writer.close();	
					CRAWL_DEPTH +=1;
					
				
				}
				catch (Exception E2) {
					System.out.println("Exception Writing File CrawledDB: "+url+ " ::::Exception Msg -"+E2);
				}
				
				for (Element page : links) {
					String new_link = page.attr("abs:href").toString();
					Pattern p = Pattern.compile(REGEX_URL_FILTER);
					Matcher m = p.matcher(new_link);
					if (m.find()) scrape(new_link);
	        		//else System.out.println("URL Filtered Out ::"+new_link);
				}
	
			}
			catch (Exception e) {
					//System.err.println("Exception Scrape :" + url + ": Error MSG : " + e.getMessage());	
				}
	}
	
	public void main() {
		
		scrape(SEED_URL);
		try {
		
		 File file = new File(URL_MAP_DIRECTORY);
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<String, String> entry :
        	URL_FILE_MAP.entrySet()) {

           
           bf.write(entry.getKey() + ".url."
                    + entry.getValue());

          
           bf.newLine();
       }

       bf.flush();
       bf.close();
		}
		catch (Exception E) {}
		System.out.println("*****************************CRAWLING DONE******************************\n");
		 
	}	

}
