# Example Elasticsearch plugin with fixture for integration testing

Elasticsearch [plugins](https://www.elastic.co/guide/en/elasticsearch/plugins/current/intro.html) are a great way to add additional functionality to an existing cluster. The number of plugins provided by both Elastic and the community has increased throughout the years, but the build process has overgone changes as well. Elasticsearch provides [gradle tools](https://github.com/elastic/elasticsearch/tree/master/buildSrc/src/main/groovy/org/elasticsearch/gradle/plugin) to help facilitate plugin development which includes tasks such as proper bundling and testing.

Elasticsearch has an [example plugin](https://github.com/elastic/elasticsearch/tree/master/plugins/jvm-example) to help guide plugin authors to develop plugins, but the example is tied to the 
overall Elasticsearch build process. Gradle's top down build process means many of the plugin settings are defined higher
up in the build chain. This simple plugin shows the bare minimum needed to get running, without the rest of the complexity of the Elasticsearch build.

## Versions

The minimum version built in this example is Elasticsearch 5.5. The [build tools](https://github.com/elastic/elasticsearch/tree/master/buildSrc/src/main/groovy/org/elasticsearch/gradle) have evolved quickly since the transition to Gradle from Maven and 
the recent versions have many fixes.  Elasticsearch versions below 5.5 that require some minor syntax changes, which I will ignore. It is best to use recent versions for ease of plugin development.

Master is based off of 6.0

## Changes

Elasticversion version | changes
-----------|-----------
5.5 | Initial version
5.6 | License and notice files are required
6.0 | projectSubstitutions bug was fixed
6.1 | No changes

## The hidden cookbook: main takeaways for what is needed for plugin development.
* Each external process run during the gradle test phase requires both a pid and ports file. The test cluster provides 
said files, but any custom fixtures created most support these files. [ExampleFixture](test/fixtures/example-fixture/src/main/java/example/ExampleFixture.java) shows an example.
* Both an unit test and integration test (ESTestCase) are required for the build to complete. For this example, there is [ExampleTest](src/test/java/org/elasticsearch/plugin/example/ExampleTest.java) and [ExampleIT](src/test/java/org/elasticsearch/plugin/example/ExampleIT.java).
* Using the elasticsearch plugin plugin enables numerous bootstrap checks. It is best to disable as many as possible. Even Elasticsearch's official plugins disable these checks.
* Testing has shown that the integration test cluster does not clean up after itself nicely. Always run the 'clean' task before a build. 'gradle clean build'

Need help? Feel free to open an issue.