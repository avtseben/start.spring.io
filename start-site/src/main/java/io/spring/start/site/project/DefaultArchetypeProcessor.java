/*
 * Copyright 2012-2019 the original author or authors.
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

package io.spring.start.site.project;

import java.util.List;

import io.spring.initializr.web.project.ArchetypeProcessor;
import io.spring.initializr.web.project.ProjectRequest;

public class DefaultArchetypeProcessor implements ArchetypeProcessor {

	private final List<Archetype> archetypes;

	public DefaultArchetypeProcessor(List<Archetype> archetypes) {
		this.archetypes = archetypes;
	}

	@Override
	public void process(ProjectRequest request) {
		if (null == request.getArchetypeName()) {
			return;
		}

		for (Archetype archetype : this.archetypes) {
			if (archetype.getArchetypeName().equals(request.getArchetypeName())) {
				enrichRequest(request, archetype);
			}
		}
	}

	private void enrichRequest(ProjectRequest projectRequest, Archetype archetype) {
		projectRequest.setDependencies(archetype.getDependencies());
		projectRequest.setType(archetype.getType());
		projectRequest.setLanguage(archetype.getLanguage());
		projectRequest.setBootVersion(archetype.getPlatformVersion());
		projectRequest.setPackaging(archetype.getPackaging());
		projectRequest.setJavaVersion(archetype.getJvmVersion());
		projectRequest.setArtifactId(projectRequest.getApplicationName());
		projectRequest.setGroupId(archetype.getGroupId());
	}

}
