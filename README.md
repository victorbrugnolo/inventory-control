## Buildings microservice 🏘🏢
### Descrição 📃
> API para controle de estoque. Permite manter produtos e realizar movimentações.

### ⭐ Tecnologias utilizadas 
- [JDK 11](https://jdk.java.net/11/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data](https://spring.io/projects/spring-data)
- [Springfox Swagger](https://springfox.github.io/springfox/docs/current/)
- [JUnit 5](https://junit.org/junit5/)
- [Maven](https://maven.apache.org)

### ⏩ Executando o projeto
#### 🧪 Para executar os testes:
##### Pré-requisitos:
```
openjdk 11
maven
```
##### Executar:
```
mvn clean test
```
---

#### 👨🏾‍💻 Para executar em desenvolvimento:
##### Pré-requisitos:
```
openjdk 11
maven
```

##### Executar:
```
mvn clean install -DskipTests && mvn spring-boot:run -Dspring-boot.run.profiles=dev 
```
---

#### 🐳 Para executar em produção:
##### Pré-requisitos:
```
docker
docker-compose
```
##### Executar:
```
docker-compose up 
```
---

### Documentação 🍃
Tanto para desenvolvimento quanto para produção o swagger ficará disponível no seguinte endereço:
```
http://localhost:8085/swagger-ui.html
```


