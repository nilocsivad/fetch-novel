package com.iam_vip.fetch_novel;

import java.io.IOException;

import org.jsoup.Jsoup;

import com.iam_vip.fetch_novel.biz.NovelFactory;

/**
 * Hello world!
 */
public class FetchNovelApp {
	
	/**
	 * 
	 */
	public FetchNovelApp() {}
	
	public static void main( String[] args ) throws Exception {
		
		NovelFactory.setFolder( "D:\\Novel" );
		
		NovelFactory.novel_group_length = 100;
		NovelFactory.createNovel( "", "" ).fetch( 100000 );
		
		// newThead2Fetch( new KV( "", "" ) );
		
		String url = "";
		getHtml( url );
		
	}
	
	/**
	 * @param url
	 * @throws IOException
	 */
	public static void getHtml( String url ) throws IOException {
		
		if ( url == null || "".equals( url ) ) return;
		
		String htm = Jsoup.connect( url ).timeout( 1000 * 60 * 3 ).get().html();
		System.out.println( htm );
	}
	
	public static void newThead2Fetch( KV... arr ) {
		
		for ( KV kv : arr ) {
			new Thread() {
				
				@Override
				public void run() {
					
					try {
						NovelFactory.createNovel( kv.url, kv.name ).fetch2html();
					}
					catch ( Exception e ) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
}
