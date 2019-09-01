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
