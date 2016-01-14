/**
 * 
 */
package com.iam_vip.fetch_novel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Colin
 */
public class ReadNovelTxt2Html {
	
	/**
	 * 
	 */
	public ReadNovelTxt2Html() {}
	
	
	private File[]		fs;
	private int			start;
	private int			len;
	private FileWriter	writer;
	private Document	templateDoc;
						
						
	@Before
	public void initData() throws IOException {
		
		start = 100000;
		len = 10 * 2 + 1; // 5
		
		String folder = "D:\\Novel\\****";
		
		fs = new File( folder ).listFiles( f -> {
			return f.isFile() && f.getName().endsWith( "txt" );
		} );
		
		start += new File( folder ).listFiles( f -> {
			return f.isFile() && f.getName().endsWith( "html" );
		} ).length + 1;
		
		InputStream input = this.getClass().getResourceAsStream( "/com/iam_vip/fetch_novel/template.html" );
		templateDoc = Jsoup.parse( input, "UTF-8", "" );
	}
	
	private void write2Html( File f, String txt ) throws IOException {
		
		File pf = f.getParentFile();
		File hf = new File( pf, ( start ++ ) + ".html" );
		writer = new FileWriter( hf );
		
		Document doc = templateDoc.clone();
		
		Elements next = doc.getElementsByClass( "next" );
		next.attr( "href", String.format( "./%s", start + ".html" ) );
		
		Elements previous = doc.getElementsByClass( "previous" );
		previous.attr( "href", String.format( "./%s", ( start - 2 ) + ".html" ) );
		
		doc.getElementById( "wrap-box" );
		Element box = doc.getElementById( "wrap-box" );
		box.append( txt );
		writer.write( doc.outerHtml() );
		
		writer.close();
	}
	
	@Test
	public void read() throws IOException {
		
		for ( File f : fs ) {
			
			StringBuffer buf = new StringBuffer( 32 );
			
			BufferedReader reader = new BufferedReader( new FileReader( f ) );
			
			int c = 0;
			
			String line = null;
			while ( ( line = reader.readLine() ) != null ) {
				
				if ( line.equals( "------------------------------------" ) ) c ++;
				
				if ( c > 0 && c % len == 0 ) {
					c = 1; // line.equals( "------------------------------------" )
					this.write2Html( f, buf.toString() );
					buf = new StringBuffer( 64 );
				}
				
				buf.append( String.format( "<p class='txt-line'> %s </p>\r\n", line ) );
				line = null;
			}
			
			this.write2Html( f, buf.toString() );
			reader.close();
			
		}
	}
	
}
