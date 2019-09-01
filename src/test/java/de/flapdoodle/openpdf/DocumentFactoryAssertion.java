package de.flapdoodle.openpdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.assertj.core.api.AbstractAssert;

public class DocumentFactoryAssertion extends AbstractAssert<DocumentFactoryAssertion, DocumentFactory> {

	public DocumentFactoryAssertion(DocumentFactory actual) {
		super(actual, DocumentFactoryAssertion.class);
	}

	public ByteArrayAssertion expectRendering() {
		return expectRendering(null);
	}

	public ByteArrayAssertion expectRendering(String writeToFileName) {
		try (ByteArrayOutputStream it = new ByteArrayOutputStream()) {
			actual.render(it);

			byte[] content = it.toByteArray();

			if (writeToFileName != null) {
				try (FileOutputStream file = new FileOutputStream(writeToFileName)) {
					file.write(content);
				}
			}

			return ByteArrayAssertion.assertThat(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static DocumentFactoryAssertion assertThat(DocumentFactory actual) {
		return new DocumentFactoryAssertion(actual);
	}
}
