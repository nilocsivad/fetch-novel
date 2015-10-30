/**
 * 
 */
package com.iam_vip.fetch_novel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Colin
 */
public class SplitFileApp {

	private static final String WRAP_LINE = "\r\n";
	public static final long WRAP_LEN = WRAP_LINE.getBytes().length;
	public static final long MAX_KB = 1024 * 511; // 512k

	/**
	 * 
	 */
	public SplitFileApp() {
	}

	public static void main(String[] args) throws IOException {

		split4folder("D:\\fetch-novel\\");
	}

	public static void split4folder(String folder) throws IOException {

		File dir = new File(folder);
		File[] files = dir.listFiles(f -> f.isFile());
		split(files);
	}

	public static void split(String... files) throws IOException {

		List<File> list = new ArrayList<>(files.length);
		for (String file : files)
			list.add(new File(file));

		split(list.toArray(new File[0]));
	}

	public static void split(File... files) throws IOException {

		for (File file : files)
			doSplit(file);
	}

	private static String suffix(String fName) {
		File file = new File(fName);
		int idx = file.getName().lastIndexOf(".");
		String suffix = idx > 0 ? (file.getName().substring(idx)) : ".txt";
		return suffix;
	}

	private static String fname(String fName) {
		File file = new File(fName);
		int idx = file.getName().lastIndexOf(".");
		String suffix = idx > 0 ? (file.getName().substring(0, idx)) : fName;
		return suffix;
	}

	private static void doSplit(File file) throws IOException {

		String fn = fname(file.getName());
		File dir = new File(file.getParentFile(), fn);
		if (!dir.exists())
			dir.mkdirs();

		String suffix = suffix(file.getName());
		String fmt = "%s.%d%s";

		BufferedReader reader = new BufferedReader(new FileReader(file));

		int index = 10001;
		long len = 0;
		String data = null;
		File outFile = new File(dir, String.format(fmt, fn, index, suffix));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

		System.out.println(outFile.getAbsolutePath());

		while ((data = reader.readLine()) != null) {
			if (len >= MAX_KB) {
				writer.flush();
				writer.close();
				index++;
				len = 0;

				outFile = new File(dir, String.format(fmt, fn, index, suffix));
				writer = new BufferedWriter(new FileWriter(outFile));

				System.out.println(outFile.getAbsolutePath());
			}
			writer.write(data);
			writer.write(WRAP_LINE);
			len += data.getBytes().length + WRAP_LEN;
			data = null;
		}
		reader.close();
		writer.close();
	}

}
