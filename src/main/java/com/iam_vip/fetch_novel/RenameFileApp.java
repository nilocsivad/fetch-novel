/**
 * 
 */
package com.iam_vip.fetch_novel;

import java.io.File;

/**
 * @author Colin
 */
public class RenameFileApp {
	
	/**
	 * 
	 */
	public RenameFileApp() {}
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		
		String folder = "D:\\fetch-novel\\";
		File[] files = new File( folder ).listFiles( ( f ) -> f.getName().endsWith( ".html" ) );
		
		for ( File f : files ) {
			File newF = new File( f.getParent(), f.getName() + ".html" );
			f.renameTo( newF );
		}
		
	}
	
}
