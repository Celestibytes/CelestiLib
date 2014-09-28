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
			while((line = br.readLine()) != null) {
				line = line.trim();
				if(!line.startsWith("//") && !line.startsWith("#")) {
					if(line.startsWith("meta")) {
						lineParts = line.split(KEY_VALUE_SEPARATOR);
						if(lineParts.length == 2) {
							key = lineParts[0].trim();
							value = lineParts[1].trim();
							if(key.equals("meta.english_language_name")) {
								langNameEng = value;
							}
							
							if(key.equals("meta.native_language_name")) {
								langNameNative = value;
							}
							
							if(key.equals("meta.translator_name")) {
								translatorName = value;
							}
							
							if(key.equals("meta.translator_twitter")) {
								translatorTwitter = value;
							}
							
							if(key.equals("meta.parent_language_code") || key.equals("meta.parent_language")) {
								parentLanguageCode = value;
							}
						}
					} else {
						if(ret == null) {
							 ret = new Translation(langCode);
							 ret.setMetadata(translatorName, translatorTwitter, parentLanguageCode, langNameEng, langNameNative);
						}
						lineParts = line.split(KEY_VALUE_SEPARATOR);
						if(lineParts.length == 2) {
							key = lineParts[0].trim();
							value = lineParts[1].trim();
									
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
			
			String key = null, value = null, line = null;
			String[] lineParts = null;
			boolean metadataSet = false;
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while((line = br.readLine().trim()) != null) {
				if(!line.startsWith("//") && !line.startsWith("#")) {
					if(line.startsWith("meta")) {
						lineParts = line.split(KEY_VALUE_SEPARATOR);
						if(lineParts.length == 2) {
							key = lineParts[0].trim();
							value = lineParts[1].trim();
							if(key.equals("meta.english_language_name")) {
								langNameEng = value;
							}
							
							if(key.equals("meta.native_language_name")) {
								langNameNative = value;
							}
							
							if(key.equals("meta.translator_name")) {
								translatorName = value;
							}
							
							if(key.equals("meta.translator_twitter")) {
								translatorTwitter = value;
							}
							
							if(key.equals("meta.parent_language_code") || key.equals("meta.parent_language")) {
								parentLanguageCode = value;
							}
						}
					} else {
						if(!metadataSet) {
							transl.setMetadata(translatorName, translatorTwitter, parentLanguageCode, langNameEng, langNameNative);
						}
						lineParts = line.split(KEY_VALUE_SEPARATOR);
						if(lineParts.length == 2) {
							key = lineParts[0].trim();
							value = lineParts[1].trim();
									
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
