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

		NovelFactory.setFolder( "D:\\fetch-novel\\" );

		// NovelFactory.createNovel( "", "" ).fetch2html();
		newThead2Fetch( new KV( "", "" ) );

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
