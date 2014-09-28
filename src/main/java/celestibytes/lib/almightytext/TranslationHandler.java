package celestibytes.lib.almightytext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import celestibytes.lib.resources.LangFileSource;

public class TranslationHandler {

	private static List<Advstr> astrs = new ArrayList<Advstr>();
	private static List<LangFileSource> filesources = new ArrayList<LangFileSource>();
	private static Translation currentTranslation = null; 
	
	public static void init(File langFileDir) {
		
	}
	
	public static void reloadTranslation(/*"I'm here for no reason whatsoever" -Lies*/) {
		for(int i=0;i<filesources.size();i++) {
			if(filesources.get(i).getLangCodeFromFilename().equals(currentTranslation.getLangCode())) {
				currentTranslation.reload(filesources.get(i));
				return;
			}
		}
	}
	
	private static Translation loadTranslation(String langCode) {
		for(int i=0;i<filesources.size();i++) {
			if(filesources.get(i).getLangCodeFromFilename().equals(langCode)) {
				return filesources.get(i).createTranslation();
			}
		}
		return null;
	}
	
	public static void reloadTranslationSources() {
		
	}
	
}
