package celestibytes.lib.almightytext;

import java.util.HashMap;
import java.util.Map;

import celestibytes.lib.resources.LangFileSource;

public class Translation {
	// OkkapeL liked how translations work in Redirection so he just copied the idea
	
	public static final String LANG_CODE_FINNISH = "fi";
	public static final String LANG_CODE_ENGLISH = "en"; // TODO: us/br
	
	private final String langCode;
	private String langNameEng;
	private String langNameNative;
	
	private String translatorName = null;
	private String translatorTwitter = null;
	private String parentLanguageCode = "en";
	
	private Map<String, String> translations = new HashMap<String, String>();
	
	public Translation(String langCode) {
		this.langCode = langCode;
	}
	
	// Please, don't ask me to write separate setters for each of these.
	public void setMetadata(String translatorName, String translatorTwitter, String parentLanguageCode, String langNameEng, String langNameNative) {
		this.translatorName = translatorName;
		this.translatorTwitter = translatorTwitter;
		if(parentLanguageCode != null) {
			this.parentLanguageCode = parentLanguageCode;
		}
		this.langNameEng = langNameEng;
		this.langNameNative = langNameNative;
	}
	
	public void addTranslation(String stringCode, String translation) {
		this.translations.put(stringCode, translation); // Overwriting is intentional
		System.out.println("added translation: " + stringCode + " - " + translation);
	}
	
	public void addTranslations(String[] stringCodes, String[] translations) {
		for(int i=0;i<stringCodes.length;i++) {
			if(i>=translations.length) {
				return;
			}
			addTranslation(stringCodes[i], translations[i]);
		}
	}
	
	public String getTranslationFor(String stringCode) {
		return this.translations.get(stringCode);
	}
	
	// These are so much fun to write...
	public String getLangCode() {
		return this.langCode;
	}
	
	public String getLangNameEng() {
		return this.langNameEng;
	}
	
	public String getLangNameNative() {
		return this.langNameNative;
	}
	
	public String getTranslatorName() {
		return this.translatorName;
	}
	
	public String getTranslatorTwitter() {
		return this.translatorTwitter;
	}
	
	public String getParentLangCode() {
		return this.parentLanguageCode;
	}
	// ...so ...much ...fun
	
	/** Used to clear the map for a reload */
	public void clear() {
		this.translations.clear();
	}
	
	public void reload(LangFileSource lfs) {
		lfs.reloadTranslations(this);
	}
	
	@Override
	public String toString() {
		return "Translation["+this.langNameEng+" by " + this.translatorName + "(" + this.translatorTwitter + ")] - " + super.toString();
	}
	
}
