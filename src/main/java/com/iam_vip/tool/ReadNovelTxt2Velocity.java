/**
 * 
 */
package com.iam_vip.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.iam_vip.chain.ChainNovel;

/**
 * @author Colin
 */
public class ReadNovelTxt2Velocity {

	private static final String TEMPLATE_PATH = "com/iam_vip/tool/html.vm.html";

	/**
	 * 
	 */
	public ReadNovelTxt2Velocity() {
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		/// 模板文件 ///
		resourcePath(TEMPLATE_PATH);

		// readFile2("D:\\Novel\\", 5);

		readFolder2("", 5); /// D:\\十万个为什么   

		// String folder = ""; /// D:\\十万个为什么 ///
		// for (File f : new File(folder).listFiles())
		// readFolder2(folder, f.getName(), 5);

	}

	private static void resourcePath(String res) {

		URL url = ReadNovelTxt2Velocity.class.getClassLoader().getResource(res);
		String path = new File(url.getPath()).getAbsolutePath();

		Properties properties = new Properties();
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path.substring(0, path.indexOf(res.replace("/", File.separator))));
		Velocity.init(properties);
	}

	private static void writerTo(File file, String title, List<String> list) throws Exception {

		if (list.size() > 0) {

			int len = file.getParentFile().list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".html");
				}
			}).length + 10000;

			File txtFile = new File(file.getParentFile(), (len + 1) + ".html");
			FileWriter writer = new FileWriter(txtFile);

			System.out.println(txtFile.getAbsolutePath());

			Template template = Velocity.getTemplate(TEMPLATE_PATH, "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("title", title);
			context.put("previous", (len) + ".html");
			context.put("next", (len + 2) + ".html");
			context.put("list", list);
			template.merge(context, writer);

			writer.close();

		}

	}

	protected static void readFile2(String path, int len) throws Exception {
		readFile2(new File(path), len);
	}

	protected static void readFile2(File txtFile, int len) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(txtFile));

		String line = null;
		String title = null;
		List<String> list = new ArrayList<>();

		int counter = 0;

		while ((line = reader.readLine()) != null) {

			if (line.startsWith(ChainNovel.START)) {

				counter++;
				if (counter % len == 1 && list.size() > 5) {
					writerTo(txtFile, title, list);
					title = null;
					list = new ArrayList<>();
					counter = 1;
				}

				title = reader.readLine().replace("---", "").trim();

				list.add(ChainNovel.START);
				list.add(ChainNovel.START);
				list.add(ChainNovel.START);
				list.add(title);
				list.add(reader.readLine());

				line = reader.readLine();

			}

			if (line.trim().length() > 0) {
				list.add(line);
			}

			line = null;
		}

		if (counter > 0) {
			writerTo(txtFile, title, list);
		}

		reader.close();

	}

	protected static void readFolder2(String folder, int len) throws Exception {
		File[] files = new File(folder).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		if (files != null && files.length > 0) {
			List<File> list = Arrays.asList(files);
			Collections.sort(list, new Comparator<File>() {
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (File txtFile : files) {
				System.out.println(txtFile.getAbsolutePath());
				readFile2(txtFile, len);
			}
		}
	}

}
