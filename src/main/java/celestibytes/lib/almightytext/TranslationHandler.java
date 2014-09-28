package celestibytes.lib.almightytext;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import celestibytes.lib.resources.LangFileSource;

public class TranslationHandler {

	private static List<Advstr> astrs = new ArrayList<Advstr>();
	private static List<LangFileSource> filesources = new ArrayList<LangFileSource>();
	private static Translation currentTranslation = null; 
	private static Translation englishTranslation = null;
	
	private static File langFileDirectory = null;
	
	private static FilenameFilter langFileNameFilter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".lang");
		}
	};
	
	public static void init(File langFileDir) {
		loadTranslationFileSources(langFileDir);
		englishTranslation = loadTranslation(Translation.LANG_CODE_ENGLISH);
		System.out.println(englishTranslation.toString());
	}
	
	public static void reloadTranslation(/*"I'm here for no reason whatsoever" -Lies*/) {
		for(int i=0;i<filesources.size();i++) {
			if(filesources.get(i).getLangCodeFromFilename().equals(currentTranslation.getLangCode())) {
				currentTranslation.reload(filesources.get(i));
				updateAstrs();
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
	
	public static void loadTranslationFileSources(File langFileDir) {
		langFileDirectory = langFileDir;
		if(langFileDir.exists() && langFileDir.isDirectory()) {
			File[] langFiles = langFileDir.listFiles(langFileNameFilter);
			for(int i=0;i<langFiles.length;i++) {
				filesources.add(new LangFileSource(new File(langFiles[i].getAbsolutePath())));
				System.out.println(langFiles[i].getAbsolutePath());
			}
		}
	}
	
	public static void reloadTranslationFileSources(File langFileDir) {
		File srchDir = langFileDir == null ? langFileDirectory : langFileDir;
		
		if(srchDir.exists() && srchDir.isDirectory()) {
			filesources.clear();
			File[] langFiles = srchDir.listFiles(langFileNameFilter);
			for(int i=0;i<langFiles.length;i++) {
				filesources.add(new LangFileSource(new File(langFiles[i].getAbsolutePath())));
				System.out.println(langFiles[i].getAbsolutePath());
			}
		}
	}
	
	public static String getTranslationFor(String stringCode) {
		String translated = null;
		if(currentTranslation == null) {
			translated = englishTranslation.getTranslationFor(stringCode);
		} else {
			translated = currentTranslation.getTranslationFor(stringCode);
			if(translated == null) {
				translated = englishTranslation.getTranslationFor(stringCode);
			}
		}
		if(translated == null) {
			return stringCode;
		}
		return translated;
	}
	
	/** Updates the translated-string in Advstr objects*/
	private static void updateAstrs() {
		Advstr astr = null;
		for(int i=0;i<astrs.size();i++) {
			astr = astrs.get(i);
			astr.translation = getTranslationFor(astr.getBaseStr());
			astr.onTranslationChanged();
		}
	}
	
	public static int registerAstr(Advstr astr) {
		astr.translation = getTranslationFor(astr.getBaseStr());
		astr.onTranslationChanged();
		astrs.add(astr);
		return astrs.size()-1;
	}
	
	protected static void deleteAdvstr(Advstr astr) {
		int index = astr.translHandlerArrId;
		astrs.remove(index);
		for(int i=index;i<astrs.size();i++) {
			if(astrs.get(i) != null) {
				astrs.get(i).translHandlerArrId--;
			}
		}
	}
	
}
