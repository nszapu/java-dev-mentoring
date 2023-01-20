## Maven (using cmd on Windows)
- from builders folder run:
  - mvn install
  - mvn package
  - cd admin/target
  - java -jar admin-1.0-jar-with-dependencies.jar hello
- tests are run with package command, or you can use mvn verify/test