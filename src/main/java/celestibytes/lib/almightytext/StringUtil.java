package celestibytes.lib.almightytext;

public class StringUtil {
	
	public static String getCfgStrValue(String text, String regex) {
		String[] parts = text.split(regex);
		return parts.length > 1 ? parts[1] : null;
	}
	
	public static String getCfgStrKey(String text, String regex) {
		String[] parts = text.split(regex);
		return parts.length > 0 ? parts[0] : null;
	}
}
