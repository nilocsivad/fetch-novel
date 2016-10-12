/**
 * 
 */
package com.iam_vip.chain;

/**
 * @author Colin
 */
public class FetchChainNovel {

	/**
	 * 
	 */
	public FetchChainNovel() {
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String url = "http://www.23wx.com/html/26/26811/26724111.html";
		String toFolder = "盅真人";

		ChainNovel.getInstance(url).start(toFolder);


	}

}
