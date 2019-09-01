package de.flapdoodle.openpdf;

import static de.flapdoodle.openpdf.DocumentFactoryAssertion.assertThat;

import org.junit.jupiter.api.Test;

import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

class DocumentFactoryTest {

	@Test
	void renderSimplePage() {
		ImmutableDocumentFactory testee = DocumentFactory.builder()
				.pageSize(PageSize.A4)
				.addBlocks((document, pdfDirectSupplier) -> {
					document.add(new Phrase("test"));
				})
				.build();

		assertThat(testee)
				.expectRendering()
				.matchesResource(getClass(), "emptyPdf.pdf");
	}

}
