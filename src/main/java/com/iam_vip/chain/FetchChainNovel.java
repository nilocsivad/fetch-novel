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

		String url = "";
		String toFolder = "";

		ChainNovel.getInstance(url).start(toFolder);


	}

}
