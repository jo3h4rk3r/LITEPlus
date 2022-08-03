package liteplus.utils.file;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.MinecraftClient;

public class FileMang {

	private static Path dir;

	public static void init() {
		dir = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "liteplus/");
		if (!dir.toFile().exists())
			dir.toFile().mkdirs();
	}



	public static Path getDir() {
		return dir;
	}


	public static List<String> readFileLines(String... file) {
		try {
			return Files.readAllLines(stringsToPath(file));
		} catch (NoSuchFileException ignored) {

		} catch (Exception e) {
			System.out.println("Error Reading File: " + stringsToPath(file));
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	public static void createFile(String... file) {
		try {
			if (fileExists(file))
				return;
			dir.toFile().mkdirs();
			Files.createFile(stringsToPath(file));
		} catch (Exception e) {
			System.out.println("Error Creating File: " + Arrays.toString(file));
			e.printStackTrace();
		}
	}


	public static void createEmptyFile(String... file) {
		try {
			createFile(file);

			FileWriter writer = new FileWriter(stringsToPath(file).toFile());
			writer.write("");
			writer.close();
		} catch (Exception e) {
			System.out.println("Error Clearing/Creating File: " + Arrays.toString(file));
			e.printStackTrace();
		}
	}


	public static void appendFile(String content, String... file) {
		try {
			String fileContent = new String(Files.readAllBytes(stringsToPath(file)));
			FileWriter writer = new FileWriter(stringsToPath(file).toFile(), true);
			writer.write(
					(fileContent.endsWith("\n") || !fileContent.contains("\n") ? "" : "\n")
							+ content
							+ (content.endsWith("\n") ? "" : "\n"));
			writer.close();
		} catch (Exception e) {
			System.out.println("Error Appending File: " + Arrays.toString(file));
			e.printStackTrace();
		}
	}


	public static boolean fileExists(String... file) {
		try {
			return stringsToPath(file).toFile().exists();
		} catch (Exception e) {
			return false;
		}
	}


	public static void deleteFile(String... file) {
		try {
			Files.deleteIfExists(stringsToPath(file));
		} catch (Exception e) {
			System.out.println("Error Deleting File: " + Arrays.toString(file));
			e.printStackTrace();
		}
	}




	public static Path stringsToPath(String... strings) {
		Path path = dir;
		for (String s : strings)
			path = path.resolve(s);
		return path;
	}






}
