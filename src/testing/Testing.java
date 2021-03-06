package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Testing {

	public static void main(String[] args) {
		test("src\\lospinares\\Bdatoa.java");
	}

	public static void test(String filename) {

		try {
			Scanner sc = new Scanner(new File(filename));
			int lineCount = 0;
			int charCount = 0;
			int codeLineCount = 0;
			int codeCharCount = 0;
			int commentLineCount = 0;
			int commentCharCount = 0;
			int emptyLineCount = 0;

			boolean multiLineOpen = false;

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				lineCount++;

				line = line.replace("\t", "");
				if (line.equals("") || line.equals("\n")) {
					emptyLineCount++;
					continue;
				}

				int lineCharCount = line.toCharArray().length;
				charCount += lineCharCount;

				String subString = "";

				if (line.length() >= 2) {
					subString = line.substring(0, 2);
				}

				if (subString.equals("/*")) {
					multiLineOpen = true;
				}

				if (multiLineOpen || subString.equals("//")) {
					commentLineCount++;
					commentCharCount += lineCharCount;
				} else {
					codeLineCount++;
					codeCharCount += lineCharCount;
				}

				if (line.endsWith("*/")) {
					multiLineOpen = false;
				}

			}

			sc.close();
			String title;
			String[] files = filename.split("\\\\");
			title = files[files.length - 1];

			title = title.replace(".java", ".txt");

			PrintWriter pw = new PrintWriter(new File("Testing " + title));

			pw.println("Test de mantenibilidad: " + title.replace(".txt", "") + "\n");
			pw.println("Cantidad de lineas total = " + String.valueOf(lineCount) + " - "
					+ String.format("%5.2f", 1.0 * 100) + "%");
			pw.println("Cantidad de caracteres total = " + String.valueOf(charCount) + " - "
					+ String.format("%5.2f", 1.0 * 100) + "%");
			pw.println("Cantidad de lineas de codigo = " + String.valueOf(codeLineCount) + " - "
					+ String.format("%5.2f", (float) codeLineCount / lineCount * 100) + "%");
			pw.println("Cantidad de caracteres de codigo = " + String.valueOf(codeCharCount) + " - "
					+ String.format("%5.2f", (float) codeCharCount / charCount * 100) + "%");
			pw.println("Cantidad de lineas de comentario = " + String.valueOf(commentLineCount) + " - "
					+ String.format("%5.2f", (float) commentLineCount / lineCount * 100) + "%");
			pw.println("Cantidad de caracteres de comentario = " + String.valueOf(commentCharCount) + " - "
					+ String.format("%5.2f", (float) commentCharCount / charCount * 100) + "%");
			pw.println("Cantidad de lineas vacias = " + String.valueOf(emptyLineCount) + " - "
					+ String.format("%5.2f", (float) emptyLineCount / lineCount * 100) + "%");

			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}