package liteplus.utils.file;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class GithubReader {
	private static URI url;
	private static URI url1;
	static {
		try {
			url = new URI("https://raw.githubusercontent.com/jo3h4rk3r/LITEPLUS/master/splashes.txt");
			url1 = new URI("https://raw.githubusercontent.com/jo3h4rk3r/LITEPLUS/master/allowedplayers.txt");
		} catch (URISyntaxException ignored) {


		}
	}
	public static List<String> readFileLines(String file) {
		List<String> st = new ArrayList<>();
		try {
			URL fileUrl = url.resolve(file).toURL();
			Scanner sc = new Scanner(fileUrl.openStream());
			while (sc.hasNextLine())
				st.add(sc.nextLine());
			sc.close();
		} catch (IOException ignored) {

		}
		return st;
	}
	public static List<String> readPlayerNames(String file) {
		List<String> st = new ArrayList<>();
		try {
			URL fileUrl = url1.resolve(file).toURL();
			Scanner sc = new Scanner(fileUrl.openStream());
			while (sc.hasNextLine())
				st.add(sc.nextLine());
			sc.close();
		} catch (IOException ignored) {

		}
		return st;
	}
}