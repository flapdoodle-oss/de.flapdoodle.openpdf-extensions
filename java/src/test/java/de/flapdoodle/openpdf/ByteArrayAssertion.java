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

import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import org.assertj.core.api.AbstractByteArrayAssert;

public class ByteArrayAssertion extends AbstractByteArrayAssert<ByteArrayAssertion> {

	public ByteArrayAssertion(byte[] actual) {
		super(actual, ByteArrayAssertion.class);
	}


	public ByteArrayAssertion matchesResource(Class<?> baseClass, String name) {
		Function<String, String> onError = msg -> {
			return msg + "\n" + persistToTempFile(actual, name, baseClass.getResource(name));
		};

		InputStream resourceStream = baseClass.getResourceAsStream(name);
		if (resourceStream == null) {
			fail(onError.apply("resource stream for " + baseClass + " / " + name + " not found"));
		}

		byte[] expected = readAllBytes(resourceStream);
		if (expected == null) {
			fail(onError.apply("could not load matching resource for " + baseClass + " / " + name + " not found"));
		}

		return super.containsExactly(expected);
	}

	private byte[] readAllBytes(InputStream inputStream) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			byte[] byteArray = buffer.toByteArray();
			return byteArray;
		} catch (IOException iox) {
			throw new RuntimeException(iox);
		}
	}


	private String persistToTempFile(byte[] data, String fileName, URL resourceURL) {
		try {
			Path tempFile = Files.createTempFile("match", "--$fileName");
			Files.write(tempFile, data);
			String resourceName = resourceURL != null ? resourceURL.toString()
					.replace("/build/resources/test/", "/src/test/resources/")
					.replace("file:/", "/") : "";

			return "\n"
					+ ",you can find the actual value in\n"
					+ "   " + tempFile + " \n"
					+ "for\n"
					+ "   " + resourceName + "\n\n"
					+ "--> " + tempFile + " " + resourceName + "\n\n";
		} catch (IOException iox) {
			throw new RuntimeException(iox);
		}
	}



	public static ByteArrayAssertion assertThat(byte[] actual) {
		return new ByteArrayAssertion(actual);
	}

	/*
	 * 
	 * fun Assertion.Builder<ByteArray>.matchesResource(baseClass: KClass<out Any>,
	 * name: String): Assertion.Builder<ByteArray> { var onError: (String) -> String
	 * = { it }
	 * 
	 * return compose("expect binary match") { subject -> onError = { it +
	 * persistToTempFile(subject, name, baseClass.java.getResource(name)) }
	 * 
	 * val resourceStream = baseClass.java.getResourceAsStream(name) ?:
	 * fail(onError("resource stream for $baseClass / $name not found"))
	 * 
	 * val expected = resourceStream.readAllBytes() ?:
	 * fail(onError("could not load matching resource for $baseClass / $name"))
	 * 
	 * if (!subject.contentEquals(expected)) {
	 * fail(onError("content does not match")) } } then { if (allPassed) pass() else
	 * fail(onError("not all passed")) } }
	 * 
	 * private fun persistToTempFile(data: ByteArray, fileName: String, resourceURL:
	 * URL?): String { val tempFile = Files.createTempFile("match", "--$fileName")
	 * Files.write(tempFile, data) val resourceName = resourceURL?.let {
	 * it.toString() .replace("/build/resources/test/", "/src/test/resources/")
	 * .replace("file:/", "/") } return
	 * "\n,you can find the actual value in\n   $tempFile \nfor\n   $resourceName\n\n--> $tempFile $resourceName\n\n"
	 * }
	 */
}
