/**
 * 
 */
package com.iam_vip.fetch_novel.biz.children;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.iam_vip.fetch_novel.biz.Novel;


/**
 * @author Colin
 * 		
 */
public class _1KanShu extends Novel {
	
	private static final int	WRAP_LINE	= 3;
	private static final String	END_SUFFIX	= "/";
											
											
	/**
	 * 
	 */
	public _1KanShu() {}
	
	/* (non-Javadoc)
	 * @see com.iam_vip.fetch_novel.biz.Novel#getWrapLine()
	 */
	@Override
	public int getWrapLine() {
		
		return WRAP_LINE;
	}
	
	/* (non-Javadoc)
	 * @see com.iam_vip.fetch_novel.biz.Novel#fetch()
	 */
	@Override
	public void fetch() throws Exception {
		
		this.fetch( 100000 );
	}
	
	/* (non-Javadoc)
	 * @see com.iam_vip.fetch_novel.biz.Novel#fetch(int)
	 */
	@Override
	public void fetch( int start ) throws Exception {
		
		try {
			
			int c = 0;
			int a = 0;
			
			int num = start + c * novel_group_length;
			
			super.newWriter( super.outFile.getName() + "." + ( num + 1 ) + "-" + ( num + novel_group_length ) + ".txt" );
			
			do {
				
				Document document = this.fetchNovel();
				
				if ( document == null ) break;
				
				String href = document.getElementsByClass( "bottem2" ).get( 0 ).getElementsByTag( "a" ).get( 2 ).attr( "href" );
				super.url = super.base_url + href;
				
				a ++;
				if ( a != 0 && a % novel_group_length == 0 ) {
					super.end();
					a = 0;
					c ++;
					num = start + c * novel_group_length;
					super.newWriter( super.outFile.getName() + "-" + ( num + 1 ) + "-" + ( num + novel_group_length ) + ".txt" );
				}
			}
			while ( !super.url.endsWith( END_SUFFIX ) );
			
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		finally {
			super.end( true );
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
				String href = document.getElementsByClass( "bottem2" ).get( 0 ).getElementsByTag( "a" ).get( 2 ).attr( "href" );
				super.calcBaseUrl( href );
			}
			
			System.out.println( document.title() + "   " + super.url );
			super.write( "\r\n\r\n\r\n------------------------------------\r\n---   " + document.title() + " ---\r\n--- " + super.url + " ---\r\n------------------------------------" );
			
			Element novel = document.getElementById( "content" );
			StringBuffer buffer = new StringBuffer( novel.html() );
			buffer = new StringBuffer( buffer.toString().replace( "&nbsp;", "" ) );
			buffer = new StringBuffer( buffer.toString().replace( "<br />", "" ) );
			buffer = new StringBuffer( buffer.toString().replace( "<br/>", "" ) );
			buffer = new StringBuffer( buffer.toString().replace( "<br>", "" ) );
			super.write( buffer.toString() );
		}
		return document;
	}
	
	/* (non-Javadoc)
	 * @see com.iam_vip.fetch_novel.biz.Novel#fetch2html()
	 */
	@Override
	public void fetch2html() throws Exception {
		
		// TODO Auto-generated method stub
		
	}
	
}
