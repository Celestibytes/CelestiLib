package celestibytes.lib.almightytext;

/** Translated string */
public class Advstr {
	private String text;
	protected int translHandlerArrId = -1;
	protected String translation;
	
	public Advstr(String text) {
		
		this.text = text;
		
		
		
		translHandlerArrId = TranslationHandler.registerAstr(this);
	}
	
	public String[] getTranslatedWords() {
		return this.translation != null ? this.translation.split(" ") : null;
	}
	
	public String getBaseStr() {
		return this.text;
	}
	
	public String getTranslated() {
		return this.translation;
	}
	
	/** Mostly for debugging purposes */
	public int getTranslHandlerArrId() {
		return this.translHandlerArrId;
	}
	
	public int length() {
		return text.length();
	}
	
	public void delete() {
		this.preAdvstrDelete();
		TranslationHandler.deleteAdvstr(this);
	}
	
	/** Called after the translated string is changed */
	protected void onTranslationChanged() {
		
	}
	
	protected void preAdvstrDelete() {}
}
