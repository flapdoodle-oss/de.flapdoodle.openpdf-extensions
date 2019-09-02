package de.flapdoodle.openpdf.types

object Margins {
	fun of(top: Float, left: Float, right: Float, bottom: Float): ImmutableMargin {
		return Margin.of(top,left,right,bottom)
	}
}