## Maven (using cmd on Windows)
- from builders folder run:
  - mvn clean install
  - cd admin/target
  - java -jar admin-1.0-jar-with-dependencies.jar hello
- tests are run with package command, or you can use mvn verify/test
## Gradle (using cmd on Windows)
- from builders folder run:
  - gradle build
  - cd admin/build/libs
  - java -jar fat-1.0.0.jar hello
- tests are run with build command, or you can use gradle test