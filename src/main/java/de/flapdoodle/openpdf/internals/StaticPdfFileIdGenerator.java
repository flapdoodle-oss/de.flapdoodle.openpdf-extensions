package de.flapdoodle.openpdf.internals;

import java.io.IOException;
import java.security.MessageDigest;

import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.pdf.ByteBuffer;
import com.lowagie.text.pdf.PdfLiteral;
import com.lowagie.text.pdf.PdfObject;

public class StaticPdfFileIdGenerator implements PdfFileIdGenerator {

	@Override
	public PdfObject generate() {
		return createInfoId(createDocumentId());
	}

	/**
	 * @see PdfEncryption.createInfoId
	 */
	public static PdfObject createInfoId(byte[] id) {
		try (ByteBuffer buf = new ByteBuffer(90)) {
			buf.append('[').append('<');
			for (int k = 0; k < 16; ++k)
				buf.appendHex(id[k]);
			buf.append('>').append('<');
			id = createDocumentId();
			for (int k = 0; k < 16; ++k)
				buf.appendHex(id[k]);
			buf.append('>').append(']');
			return new PdfLiteral(buf.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see PdfEncryption.createDocumentId()
	 */
	public static byte[] createDocumentId() {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
		long time = 0;
		long mem = 0;
		String s = time + "+" + mem + "+" + (1);
		return md5.digest(s.getBytes());
	}
}
