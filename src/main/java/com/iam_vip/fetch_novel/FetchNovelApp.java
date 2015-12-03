package com.iam_vip.fetch_novel;

import com.iam_vip.fetch_novel.biz.NovelFactory;

/**
 * Hello world!
 */
public class FetchNovelApp {

	/**
	 * 
	 */
	public FetchNovelApp() {
	}

	public static void main( String[] args ) throws Exception {

		NovelFactory.setFolder( "/Users/Colin/Documents/fetch-novel" );

		NovelFactory.createNovel( "http://www.23wx.com/html/51/51514/22464681.html", "造化之门2" ).fetch2html();
		//		newThead2Fetch( new KV( "", "" ) );

	}

	public static void newThead2Fetch( KV... arr ) {

		for ( KV kv : arr ) {
			new Thread() {

				@Override
				public void run() {

					try {
						NovelFactory.createNovel( kv.url, kv.name ).fetch2html();
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

}
