package de.flapdoodle.openpdf.internals;

import com.lowagie.text.pdf.PdfObject;

public interface PdfFileIdGenerator {
	PdfObject generate(); 
}
