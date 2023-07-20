package org.pjp.museum;

import java.io.File;
import java.io.IOException;

import org.pjp.museum.ui.util.FileUtils;
import org.pjp.museum.ui.util.QrCodeUtils;

public class QrCodeGenerator {
	private static final String TMPDIR = System.getProperty("java.io.tmpdir");
	
	public static void main(String[] args) throws IOException {
		File workDir = new File(TMPDIR, "work");
		workDir.mkdir();
		
		System.out.println("tmpdir = " + TMPDIR);
		
		QrCodeUtils.createAndWriteQR("XA231", workDir, "grasshopper-qrcode-400.png", 400, 40);
		QrCodeUtils.createAndWriteQR("XR770", workDir, "lightning-qrcode-400.png", 400, 40);
		
		File workZip = new File(TMPDIR, "qrcode-400.zip");
		workZip.delete();

		FileUtils.pack(workDir.getAbsolutePath(), workZip);

		FileUtils.deleteDirectory(workDir);
	}

}
