/**
 * 
 */
package com.iam_vip.fetch_novel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Colin
 * 		
 */
public class ExecAddIDProp {
	
	/**
	 * 
	 */
	public ExecAddIDProp() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main( String[] args ) throws IOException {
		
		String folder = "/Users/Colin/Documents/fetch-novel/";
		File[] files = new File( folder ).listFiles( ( f ) -> f.getName().endsWith( ".html" ) );
		
		for ( File f : files ) {
			
			System.out.println( f.getAbsolutePath() );
			
			Document doc = Jsoup.parse( f, "UTF-8" );
			Elements ps = doc.getElementsByClass( "op-line" );
			int i = 0;
			for ( Element p : ps ) {
				p.getElementsByClass( "next" ).get( 0 ).attr( "id", "next" + i );
				p.getElementsByClass( "previous" ).get( 0 ).attr( "id", "previous" + i );
			}
			FileOutputStream output = new FileOutputStream( f );
			OutputStreamWriter writer = new OutputStreamWriter( output, "UTF-8" );
			writer.write( doc.html() );
			writer.close();
			output.close();
		}
		
	}
	
}
