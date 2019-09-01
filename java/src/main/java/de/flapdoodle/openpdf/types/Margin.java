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
