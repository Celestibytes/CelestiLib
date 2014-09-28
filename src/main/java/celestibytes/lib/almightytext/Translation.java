package celestibytes.lib.almightytext;

import java.util.HashMap;
import java.util.Map;

import celestibytes.lib.resources.LangFileSource;

public class Translation {
	// OkkapeL liked how translations work in Redirection so he just copied the idea
	
	public static final String LANG_CODE_FINNISH = "fi";
	public static final String LANG_CODE_ENGLISH = "en"; // TODO: us/br
	
	private final String langCode;
	private final String langNameEng;
	private final String langNameNative;
	
	private Map<String, String> translations = new HashMap<String, String>();
	
	public Translation(String langCode, String langNameEng, String langNameNative) {
		this.langCode = langCode;
		this.langNameEng = langNameEng;
		this.langNameNative = langNameNative;
	}
	
	public void addTranslation(String stringCode, String translation) {
		this.translations.put(stringCode, translation); // Overwriting is intentional
	}
	
	public void addTranslations(String[] stringCodes, String[] translations) {
		for(int i=0;i<stringCodes.length;i++) {
			if(i>=translations.length) {
				return;
			}
			addTranslation(stringCodes[i], translations[i]);
		}
	}
	
	public String getLangCode() {
		return this.langCode;
	}
	
	public String getLangNameEng() {
		return this.langNameEng;
	}
	
	public String getLangNameNative() {
		return this.langNameNative;
	}
	
	/** Used to clear the map for a reload */
	public void clear() {
		this.translations.clear();
	}
	
	public void reload(LangFileSource lfs) {
		lfs.reloadTranslations(this);
	}
	
}
