/**
 * 
 */
package com.iam_vip.fetch_novel.biz.children;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.fetch_novel.biz.Novel;

/**
 * @author Colin
 */
public class _23wx extends Novel {
	
	private static final int	WRAP_LINE	= 3;
	private static final int	LEN_URL		= 32;
	private static final String	END_SUFFIX	= "/index.html";
											
											
	/**
	 * 
	 */
	public _23wx() {}
	
	@Override
	public int getWrapLine() {
		
		return WRAP_LINE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.fetch_novel.biz.Novel#fetch()
	 */
	@Override
	public void fetch() throws IOException {
		
		try {
			
			do {
				
				Document document = this.fetchNovel();
				if ( document == null ) break;
				
				String href = document.getElementById( "amain" ).getElementsMatchingOwnText( "下一页" ).get( 0 ).attr( "href" );
				super.url = super.base_url + href;
				
			}
			while ( !super.url.endsWith( END_SUFFIX ) );
			
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		finally {
			super.end();
		}
	}
	
	/**
	 * @param document
	 * @throws Exception
	 */
	private Document fetchNovel() throws Exception {
		
		Document document = super.doc();
		if ( document != null ) {
			
			if ( super.base_url == null ) {
				String href = document.getElementById( "amain" ).getElementsMatchingOwnText( "下一页" ).get( 0 ).attr( "href" );
				super.calcBaseUrl( href );
			}
			
			System.out.println( document.title() + "   " + super.url );
			super.write( "\r\n\r\n\r\n------------------------------------\r\n---   " + document.title() + " ---\r\n--- " + super.url + " ---\r\n------------------------------------" );
			
			Element novel = document.getElementById( "contents" );
			StringBuffer buffer = new StringBuffer( novel.html() );
			buffer = new StringBuffer( buffer.toString().replace( "&nbsp;", "" ) );
			buffer = new StringBuffer( buffer.toString().replace( "<br />", "" ) );
			buffer = new StringBuffer( buffer.toString().replace( "<br/>", "" ) );
			buffer = new StringBuffer( buffer.toString().replace( "<br>", "" ) );
			super.write( buffer.toString() );
		}
		return document;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.fetch_novel.biz.Novel#fetch2html()
	 */
	@Override
	public void fetch2html() throws Exception {
		
		InputStream input = this.getClass().getResourceAsStream( "/com/iam_vip/fetch_novel/template.html" );
		Document templateDoc = Jsoup.parse( input, "UTF-8", BASE_URI );
		super.setTemplate( templateDoc.outerHtml() );
		input.close();
		
		try {
			
			do {
				
				Document document = this.fetchNovel2Html();
				if ( document == null ) break;
				
				String href = document.getElementById( "amain" ).getElementsMatchingOwnText( "下一页" ).get( 0 ).attr( "href" );
				super.url = super.base_url + href;
				
			}
			while ( !super.url.endsWith( END_SUFFIX ) );
			
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		finally {
			super.end();
		}
	}
	
	/**
	 * @return
	 */
	private Document fetchNovel2Html() throws Exception {
		
		Document document = super.doc();
		if ( document != null ) {
			
			String down = document.getElementById( "amain" ).getElementsMatchingOwnText( "下一页" ).get( 0 ).attr( "href" );
			super.calcBaseUrl( down );
			String up = document.getElementById( "amain" ).getElementsMatchingOwnText( "上一页" ).get( 0 ).attr( "href" );
			
			Element novel = document.getElementById( "contents" );
			StringBuffer buffer = new StringBuffer( novel.html() );
			buffer = new StringBuffer( buffer.toString().replace( "&nbsp;", "" ) );
			String[] arr = buffer.toString().split( "<br>" );
			
			super.newWriter( super.url2file( LEN_URL ) + ".html" );
			{
				Document templateDoc = Jsoup.parse( super.templateHTML );
				templateDoc.title( document.title() );
				
				templateDoc.getElementById( "title" ).text( document.title() );
				
				Element box = templateDoc.getElementById( "wrap-box" );
				for ( String line : arr ) {
					if ( line.length() > 5 ) box.append( String.format( "<p class='txt-line'> %s </p>\r\n", line ) );
				}
				
				Elements next = templateDoc.getElementsByClass( "next" );
				next.attr( "href", String.format( "./%s.html", super.url2file( super.base_url + down, LEN_URL ) ) );
				
				Elements previous = templateDoc.getElementsByClass( "previous" );
				previous.attr( "href", String.format( "./%s.html", super.url2file( super.base_url + up, LEN_URL ) ) );
				
				super.write( templateDoc.outerHtml() );
				super.end();
			}
			
			System.out.println( document.title() + "   " + super.url );
		}
		return document;
	}
	
}
