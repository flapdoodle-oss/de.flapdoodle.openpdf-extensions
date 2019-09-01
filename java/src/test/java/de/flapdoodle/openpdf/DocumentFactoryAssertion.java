/**
 * Copyright (C) 2019
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
