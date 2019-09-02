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
package de.flapdoodle.openpdf.types;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import de.flapdoodle.openpdf.pages.ImmutablePageBox;
import de.flapdoodle.openpdf.pages.PageBox;

/**
 * | | heigh | left,bottom----width--->
 */
@Immutable
public interface Box {
	@Parameter
	float left();

	@Parameter
	float top();

	@Parameter
	float width();

	@Parameter
	float height();

	default boolean contains(Box inner) {
		if (left() <= inner.left() && top() <= inner.top()) {
			if (left() + width() >= inner.left() + inner.width()) {
				if (top() + height() >= inner.top() + inner.height()) {
					return true;
				}
			}
		}
		return false;
	}

	default ImmutableBox translate(float deltaX, float deltaY) {
		return ImmutableBox.of(left() + deltaX, top() + deltaY, width(), height());
	}

	default ImmutablePageBox asPageBox(PageBox base) {
		return PageBox.of(base.left() + left(), base.bottom() + base.height() - top() - height(), width(),
				height());
	}

	public static ImmutableBox of(float left, float top, float width, float height) {
		return ImmutableBox.of(left, top, width, height);
	}

	public static ImmutableBox of(Position position, Dimension dimension) {
		return of(position.x(), position.y(), dimension.width(), dimension.height());
	}

	public static ImmutableBox.Builder builder() {
		return ImmutableBox.builder();
	}
}