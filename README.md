
```shell script
docker-compose up -d
./mvnw.cmd -f common/pom.xml clean install
./mvnw.cmd -f refs/pom.xml quarkus:dev
./mvnw.cmd -f gl/pom.xml quarkus:dev
./mvnw.cmd -f gate/pom.xml quarkus:dev
```
[classes.puml](./doc/uml/classes.puml)
![PlantUML model](http://www.plantuml.com/plantuml/svg/5Sox3G9130JGd2gWWNM8H7Ijc5d3dhIVmnwBzZcYbxqjgCuS_NBR9tfMI-_NHXxn477zblVEbiyCSwn9conOGzJHxZHGFYy5bylGeBdK1JaxGTS8YtByxyrcFVq1)

[GraphQL UI](http://localhost:9090/q/graphql-ui)
```
{
  getPartyList {
    id
    name
    accounts {
      number
      rests {
        amount
        currency {
          code
        }
      }
    }
  }
}
```
