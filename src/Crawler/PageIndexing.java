package Crawler;

import Crawler.Sorting;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PageIndexing {

	private String CRAWLED_DB_DRIECTORY = System.getProperty("user.dir") + "/src/Crawler/Crawled DB/";

	private ArrayList<File> TEXT_FILES = new ArrayList<File>();
	public Map<String, HashMap<String, Integer>> WORD_URL_MAP = new HashMap<String, HashMap<String, Integer>>();
	

	public PageIndexing() {
		System.out.println("*****************************RESULTS INDEXING STARTED******************************");
		File folder = new File(CRAWLED_DB_DRIECTORY);
		File[] text_files = folder.listFiles();

		for (File file : text_files) {
			TEXT_FILES.add(file);
		}
		index();
		
		Sorting s_obj = new Sorting(WORD_URL_MAP);
		
		this.WORD_URL_MAP = s_obj.sort_map();
		System.out.println("*****************************RESULTS INDEXING DONE******************************\n");
	}

	public void index() {

		for (int i = 0; i < TEXT_FILES.size(); i++) {
			Map<String, Integer> WORD_URL_OCCURRENCE = new HashMap<String, Integer>();
			
			String file_path = TEXT_FILES.get(i).getPath();
			String url_key = file_path.split("Crawled DB/")[1];
			if (file_path.contains("Crawled DB/MAP")) {
				continue;
			}
			String str = read_file(file_path);

			StringTokenizer st = new StringTokenizer(str, " ");
			while (st.hasMoreTokens()) {
				String word = st.nextToken().toLowerCase();
				if (WORD_URL_OCCURRENCE.get(word) == null) {
					WORD_URL_OCCURRENCE.put(word, 1);
					
				}
				else {
					WORD_URL_OCCURRENCE.put(word,WORD_URL_OCCURRENCE.get(word)+1);
				}

			}
			List<String> l = new ArrayList<String>(WORD_URL_OCCURRENCE.keySet());
			
			
			for (String word : l) {
				
				if (WORD_URL_MAP.get(word)==null) {
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					map.put(url_key,WORD_URL_OCCURRENCE.get(word));
					WORD_URL_MAP.put(word,map);
				}
				else {
					if (WORD_URL_MAP.get(word).get(url_key) == null) {
						WORD_URL_MAP.get(word).put(url_key,WORD_URL_OCCURRENCE.get(word) );
					}
					else {
						int occur = WORD_URL_MAP.get(word).get(url_key);
						occur += WORD_URL_OCCURRENCE.get(word);
						WORD_URL_MAP.get(word).put(url_key,occur );
					}
				}
				}
			}
		
	}

	public String read_file(String pathname) {

		StringBuilder builder = new StringBuilder();

		try (BufferedReader buffer = new BufferedReader(new FileReader(pathname))) {

			String str;

			while ((str = buffer.readLine()) != null) {

				builder.append(str).append("\n");
			}
		}

		catch (IOException e) {

			e.printStackTrace();
		}
		try {
			String str = builder.toString().replaceAll("[^a-zA-Z0-9\\s+]", "");
			return str;
		} catch (Exception e) {
			return builder.toString();
		}
	}

	public static void main(String... args) {

		PageIndexing obj = new PageIndexing();
		
		
	}
	

}
