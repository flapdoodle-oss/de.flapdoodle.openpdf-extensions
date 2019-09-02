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
package de.flapdoodle.openpdf.pages;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import de.flapdoodle.openpdf.types.Dimension;

/**
 * | | heigh | left,bottom----width--->
 */
@Immutable
public interface PageBox {
	@Parameter
	float left();

	@Parameter
	float bottom();

	@Parameter
	float width();

	@Parameter
	float height();

	public static ImmutablePageBox of(float left, float top, float width, float height) {
		return ImmutablePageBox.of(left, top, width, height);
	}

	public static ImmutablePageBox of(PagePosition position, Dimension dimension) {
		return of(position.x(), position.y(), dimension.width(), dimension.height());
	}

	public static ImmutablePageBox.Builder builder() {
		return ImmutablePageBox.builder();
	}
}

// fun withMargin(margin: Float): PageBox {
// return PageBox(left + margin, bottom + margin, width - margin * 2, height -
// margin * 2)
// }
//
// fun withMargin(margin: Margin): PageBox {
// return PageBox(left + margin.left, bottom + margin.bottom, width -
// margin.left - margin.right, height - margin.bottom - margin.top)
// }
//
// fun asRectangle(): Rectangle {
// return Rectangle(left, bottom, left + width, bottom + height)
// }
//
// fun dimension(): Dimension {
// return Dimension(width, height)
// }
// }
//
// fun Document.innerBox(): PageBox {
// return PageBox(this.left(), this.bottom(), this.right() - this.left(),
// this.top() - this.bottom())
// }
//
// fun Document.fullPageBox(): PageBox {
// return this.pageSize.asPageBox()
// }
//
// fun Rectangle.asPageBox(): PageBox {
// return PageBox(this.getLeft(), this.getBottom(), this.getRight() -
// this.getLeft(), this.getTop() - this.getBottom())
// }
//
// fun ColumnText.setBox(box: PageBox) {
// this.setSimpleColumn(box.left, box.bottom, box.left + box.width, box.bottom +
// box.height)
// }