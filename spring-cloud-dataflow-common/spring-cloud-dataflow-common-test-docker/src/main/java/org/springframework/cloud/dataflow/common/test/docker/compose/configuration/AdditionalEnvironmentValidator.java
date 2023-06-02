/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.dataflow.common.test.docker.compose.configuration;

import static org.springframework.cloud.dataflow.common.test.docker.compose.configuration.EnvironmentVariables.DOCKER_CERT_PATH;
import static org.springframework.cloud.dataflow.common.test.docker.compose.configuration.EnvironmentVariables.DOCKER_HOST;
import static org.springframework.cloud.dataflow.common.test.docker.compose.configuration.EnvironmentVariables.DOCKER_TLS_VERIFY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

public final class AdditionalEnvironmentValidator {

	private static final Set<String> ILLEGAL_VARIABLES = new HashSet<>(Arrays.asList(DOCKER_TLS_VERIFY, DOCKER_HOST, DOCKER_CERT_PATH));

	private AdditionalEnvironmentValidator() {
	}

	public static Map<String, String> validate(Map<String, String> additionalEnvironment) {
		HashSet<String> invalidVariables = new HashSet<>(additionalEnvironment.keySet());
		invalidVariables.retainAll(ILLEGAL_VARIABLES);

		String errorMessage = invalidVariables.stream()
	.collect(Collectors.joining(", ",
"The following variables: ",
" cannot exist in your additional environment variable block as they will interfere with Docker."));
		Assert.state(invalidVariables.isEmpty(), errorMessage);
		return additionalEnvironment;
	}
}
