/*
 * Copyright 2012-2020 the original author or authors.
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

package custom;

import java.util.List;

import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.InitializrProperties;
import io.spring.initializr.web.project.ArchetypeProcessor;
import io.spring.initializr.web.support.DefaultInitializrMetadataProvider;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;
import io.spring.start.site.project.Archetype;
import io.spring.start.site.project.DefaultArchetypeProcessor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ CustomConfiguration.CustomInitializrProperties.class,
		CustomConfiguration.ArchetypesProperties.class })
public class CustomConfiguration {

	@Bean
	InitializrMetadataProvider customInitializrMetadataProvider(InitializrProperties initializrProperties,
			CustomInitializrProperties customInitializrProperties,
			InitializrMetadataUpdateStrategy initializrMetadataUpdateStrategy) {

		return new DefaultInitializrMetadataProvider(
				InitializrMetadataBuilder.fromInitializrProperties(customInitializrProperties.getInitializr())
						.withInitializrProperties(initializrProperties, true).build(),

				initializrMetadataUpdateStrategy);
	}

	@Bean
	ArchetypeProcessor archetypeProcessor(ArchetypesProperties archetypesProperties) {
		return new DefaultArchetypeProcessor(archetypesProperties.getItems());
	}

	@ConfigurationProperties("custom")
	static class CustomInitializrProperties {

		@NestedConfigurationProperty
		private InitializrProperties initializr;

		InitializrProperties getInitializr() {
			return this.initializr;
		}

		void setInitializr(InitializrProperties initializr) {
			this.initializr = initializr;
		}

	}

	@ConfigurationProperties("archetypes")
	public static class ArchetypesProperties {

		List<Archetype> items;

		public List<Archetype> getItems() {
			return this.items;
		}

		public void setItems(List<Archetype> items) {
			this.items = items;
		}

	}

}
