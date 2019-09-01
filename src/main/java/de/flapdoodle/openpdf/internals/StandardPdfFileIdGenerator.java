package de.flapdoodle.openpdf.internals;

import com.lowagie.text.pdf.PdfEncryption;
import com.lowagie.text.pdf.PdfObject;

public class StandardPdfFileIdGenerator implements PdfFileIdGenerator{

	@Override
	public PdfObject generate() {
		return PdfEncryption.createInfoId(PdfEncryption.createDocumentId());
	}
}
