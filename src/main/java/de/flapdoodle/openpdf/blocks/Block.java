package de.flapdoodle.openpdf.blocks;

import java.util.function.Supplier;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;

public interface Block {
	void render(Document document, Supplier<PdfContentByte> directContent);
}
