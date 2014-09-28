package celestibytes.lib.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import celestibytes.lib.almightytext.StringUtil;
import celestibytes.lib.almightytext.Translation;

/**
 * The meta part of the translation file must be in the beginning!
 */
public class LangFileSource extends FileSource {
	
	public static final String KEY_VALUE_SEPARATOR = "=";

	public LangFileSource(File file) {
		super(file);
	}
	
	public String getLangCodeFromFilename() {
		if(this.exists()) {
			String fname = this.getFile().getName();
			return fname.substring(0, fname.indexOf("."));
		}
		return null;
	}
	
	public Translation createTranslation() {
		FileInputStream fis = this.getInputStream();
		if(fis == null) {
			return null;
		}
		try {
			Translation ret = null;
			// lang file metadata
			String langNameEng = "null";
			String langNameNative = "null";
			String translatorName = "";
			String translatorTwitter = "";
			String parentLanguageCode = "";
			String langCode = getLangCodeFromFilename();			
			
			String key = null, value = null, line = null;
			String[] lineParts = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while((line = br.readLine().trim()) != null) {
				if(!line.startsWith("//") && !line.startsWith("#")) {
					if(line.startsWith("meta")) {
						if(line.equals("meta.english_language_name")) {
							langNameEng = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.native_language_name")) {
							langNameNative = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.translator_name")) {
							translatorName = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.translator_twitter")) {
							translatorTwitter = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.parent_language_code") || line.equals("meta.parent_language")) {
							parentLanguageCode = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
					} else {
						if(ret == null) {
							 ret = new Translation(langCode, langNameEng, langNameNative);
						}
						lineParts = line.split(KEY_VALUE_SEPARATOR);
						if(lineParts.length == 2) {
							key = lineParts[0];
							value = lineParts[1];
									
							if(key != null && value != null) {
								ret.addTranslation(key, value);
							}
						}
					}
				}
			}
			
			br.close();
			return ret;
		} catch(Exception e) {
			System.err.println("[LangFileSource] [Error]: Failed to read translation file!");
			e.printStackTrace();
		}
		return null;

	}
	
	public void reloadTranslations(Translation transl) {
		FileInputStream fis = this.getInputStream();
		if(fis == null) {
			return;
		}
		try {
			transl.clear();
			
			// lang file metadata
			String langNameEng = "null";
			String langNameNative = "null";
			String translatorName = "";
			String translatorTwitter = "";
			String parentLanguageCode = "";
//			String langCode = getLangCodeFromFilename(); not used here			
			
			String key = null, value = null, line = null;
			String[] lineParts = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while((line = br.readLine().trim()) != null) {
				if(!line.startsWith("//") && !line.startsWith("#")) {
					if(line.startsWith("meta")) {
						if(line.equals("meta.english_language_name")) {
							langNameEng = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.native_language_name")) {
							langNameNative = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.translator_name")) {
							translatorName = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.translator_twitter")) {
							translatorTwitter = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
						
						if(line.equals("meta.parent_language_code") || line.equals("meta.parent_language")) {
							parentLanguageCode = StringUtil.getCfgStrValue(line, KEY_VALUE_SEPARATOR);
						}
					} else {
						lineParts = line.split(KEY_VALUE_SEPARATOR);
						if(lineParts.length == 2) {
							key = lineParts[0];
							value = lineParts[1];
									
							if(key != null && value != null) {
								transl.addTranslation(key, value);
							}
						}
					}
				}
			}
			
			br.close();
		} catch(Exception e) {
			System.err.println("[LangFileSource] [Error]: Failed to read translation file!");
			e.printStackTrace();
		}
	}
	
	
	
}
