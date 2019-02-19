package main;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class Main {
	public static void main(String args[]) {
		System.setProperty("file.encoding", "utf-8");
		try {
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainFrame();
	}
}
