package net.swisstech.gradle.elasticdependencies

import static net.swisstech.swissarmyknife.lang.Strings.isNotBlank

import org.slf4j.*

import org.gradle.api.*
import org.gradle.api.plugins.*
import org.gradle.api.artifacts.*

/**
 * Based on/Inspired by <a href="https://github.com/pniederw/elastic-deps">'elastic-deps' by Peter Niederwieser</a>.
 */
class ElasticDependenciesPlugin implements Plugin<Project> {

	static final Logger LOG = LoggerFactory.getLogger(ElasticDependenciesPlugin.class)

	void apply(Project project) {
		project.dependencies.metaClass.elastic = { String projectName, String projectVersion = '+', String projectGroup = null ->
			def dependency = null
			try {
				String notation = ":${projectName}"
				dependency = project.project(notation)
			}
			catch (UnknownProjectException e) {
				String group = project.group;
				if (isNotBlank(projectGroup)) {
					group = projectGroup;
				}

				String notation = "${group}:${projectName}:${projectVersion}"
				LOG.debug("ElasticDependenciesPlugin: ${project.name}: Project with name ${projectName} not found, using external dependency ${notation}")
				dependency = project.dependencies.create(notation)
			}

			LOG.debug("ElasticDependenciesPlugin: ${project.name} Resolved projectName=${projectName} notation=${notation} into ${dependency}")
			return dependency
		}
	}
}
