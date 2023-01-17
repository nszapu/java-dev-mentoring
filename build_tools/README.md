## Maven (use command promt on windows)
## create admin
mvn archetype:generate -DgroupId=com.epam -DartifactId=admin -DinteractiveMode=false  
add <packaging>pom</packaging> to parent pom
cd admin
mvn archetype:generate -DgroupId=com.epam -DartifactId=services -DinteractiveMode=false  
mvn archetype:generate -DgroupId=com.epam -DartifactId=utils -DinteractiveMode=false

## create jar
mvn package

## create web
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DgroupId=com.epam.app -DartifactId=web -DinteractiveMode=false
## create war
cd web
mvn package

## Gradle