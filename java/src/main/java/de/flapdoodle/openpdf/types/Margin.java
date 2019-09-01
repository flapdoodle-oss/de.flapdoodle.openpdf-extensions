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

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
public interface Margin {
	@Default
	default float top() {
		return 0;
	}

	@Default
	default float left() {
		return 0;
	}

	@Default
	default float right() {
		return 0;
	}

	@Default
	default float bottom() {
		return 0;
	}

	public static ImmutableMargin.Builder builder() {
		return ImmutableMargin.builder();
	}
}
