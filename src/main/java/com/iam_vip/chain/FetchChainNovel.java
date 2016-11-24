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

		String url = ""; /// http://www.baidu.com/十万个为什么 ///
		String toFolder = ""; /// 十万个为什么 ///

		ChainNovel.getInstance(url).start(toFolder);


	}

}
