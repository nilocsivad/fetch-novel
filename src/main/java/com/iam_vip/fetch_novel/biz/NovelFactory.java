/**
 * 
 */
package com.iam_vip.fetch_novel.biz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.iam_vip.fetch_novel.biz.children._23wx;

/**
 * @author Colin
 */
public final class NovelFactory {
	
	// <novel-children>
	private static final Map< String, Class< ? > > URL2CLASS = new HashMap< >();
	
	
	static {
		URL2CLASS.put( "http://www.23wx.com", _23wx.class );
	}
	// </novel-children>
	
	/**
	 * 
	 */
	private NovelFactory() {}
	
	
	private static File folder;
	
	
	public static void setFolder( String folder ) {
		
		NovelFactory.folder = new File( folder );
		
		if ( !NovelFactory.folder.exists() ) NovelFactory.folder.mkdirs();
	}
	
	public static Novel createNovel( String url, String folder ) throws Exception {
		
		url = url.trim();
		
		String key = getSiteKey( url );
		
		Class< ? > cls = URL2CLASS.get( key );
		Novel instance = ( Novel ) cls.newInstance();
		
		instance.url = url;
		instance.outFile = new File( NovelFactory.folder, folder );
		instance.outFile.mkdirs();
		return instance;
	}
	
	private static String getSiteKey( String url ) {
		
		String[] arr = url.split( "/", 4 );
		return arr[ 0 ] + "/" + arr[ 1 ] + "/" + arr[ 2 ];
	}
	
}
