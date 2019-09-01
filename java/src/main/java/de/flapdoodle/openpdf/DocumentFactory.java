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

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;

import de.flapdoodle.openpdf.blocks.Block;
import de.flapdoodle.openpdf.internals.PdfFileIdGenerator;
import de.flapdoodle.openpdf.internals.StaticPdfFileIdGenerator;
import de.flapdoodle.openpdf.pages.OnDocumentInit;
import de.flapdoodle.openpdf.types.ImmutableMargin;
import de.flapdoodle.openpdf.types.Margin;

@Immutable
public abstract class DocumentFactory {

	public static Margin DEFAULT_PAGE_MARGINS = ImmutableMargin.builder()
			.top(50).left(50).right(50).bottom(50)
			.build();

	protected abstract Rectangle pageSize();

	protected abstract Optional<PdfPageEvent> onPageEvents();

	protected abstract Optional<OnDocumentInit> onDocumentInit();

	@Default
	protected PdfFileIdGenerator pdfFileIdGenerator() {
		return new StaticPdfFileIdGenerator();
	}

	@Default
	protected Margin pageMargin() {
		return DEFAULT_PAGE_MARGINS;
	}

	protected abstract List<Block> blocks();

	public void render(OutputStream outputStream) {
		Document document = new Document(pageSize(), pageMargin().left(), pageMargin().right(), pageMargin().top(),
				pageMargin().bottom());
		PdfWriter writer = pdfWriter(document, outputStream);

		writer.setPageEvent(onPageEvents().orElse(null));

		onDocumentInit().ifPresent(it -> it.onInit(document));

		document.open();

		blocks().forEach(it -> {
			it.render(document, () -> writer.getDirectContent());
		});

		document.close();
	}

	private PdfWriter pdfWriter(Document document, OutputStream outputStream) {
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		writer.setFullCompression();
		writer.getInfo().put(PdfName.FILEID, pdfFileIdGenerator().generate());
		return writer;
	}

	public static ImmutableDocumentFactory.Builder builder() {
		return ImmutableDocumentFactory.builder();
	}
}
