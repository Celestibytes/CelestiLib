package celestibytes.lib.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

public class Downloader {
	
	private int bufferSize = 64;
	
	public Downloader() {
		
	}
	
	public void download(File target, URL tdw) {
		URLConnection ucon = null;
		FileOutputStream fos = null;
		byte[] buf = new byte[bufferSize];
		if(!target.exists()) {
			try {
				ucon = tdw.openConnection();
				target.createNewFile();
				fos = new FileOutputStream(target);
				InputStream in = ucon.getInputStream();
				int bytesRead = -1;
				while(true) {
					bytesRead = in.read(buf, 0, bufferSize);
					if(bytesRead == -1) {
						break;
					}
					fos.write(buf, 0, bytesRead);
				}
				
				fos.close();
				in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
