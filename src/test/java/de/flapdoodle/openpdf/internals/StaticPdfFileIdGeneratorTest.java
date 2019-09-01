package de.flapdoodle.openpdf.internals;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.lowagie.text.pdf.PdfObject;

class StaticPdfFileIdGeneratorTest {

	@Test
	void test() throws InterruptedException {
		PdfObject result = new StaticPdfFileIdGenerator().generate();
		Thread.sleep(100);
		PdfObject secondResult = new StaticPdfFileIdGenerator().generate();

		assertThat(result.getBytes())
				.isEqualTo(secondResult.getBytes());
	}
}
