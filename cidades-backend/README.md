# XPTO Systems - Cidades

Sistema WEB para leitura e manuteção de uma lista de cidades.

# Requisitos

Tem como requisitos do sistema ler um arquivo CSV (cidades.csv) das cidade e inserir os dados em uma base de dados.

# Requisitos da API

1. Ler o arquivo CSV das cidades para a base de dados - OK;
2. Retornar somente as cidades que são capitais ordenadas por nome - OK;
3. Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades - Implementado, porém não validado;
4. Retornar a quantidade de cidades por estado - Implementado, porém não validado;
5. Obter os dados da cidade informando o id do IBGE - Implementado através do método padrão get(http://localhost:9000/swagger-ui.html#/cidade-controller-impl/filterUsingGET);
6. Retornar o nome das cidades baseado em um estado selecionado - Implementado, porém não validado;
7. Permitir adicionar uma nova Cidade - Implementado através do método padrão POST(save);
8. Permitir deletar uma cidade - Implementado através do método padrão DELETE;
9. Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para filtrar. retornar assim todos os objetos que contenham tal string - Não Implementado;
10. Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais - Implementado através do método padrão get(http://localhost:9000/swagger-ui.html#/cidade-controller-impl/filterUsingGET);;
11. Retornar a quantidade de registros total - Implementado, porém não validado;;
12. Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localização (distância em KM em linha reta) - Não Implementado;

## Estrutura do Projeto

 Para importar o projeto, basta realizar o clone via https://github.com/rodrigoExter/cidade-api.git

### Build Automática

 O processo de build automática é realizado por meio da ferramenta [Gradle](http://gradle.org).


 Exemplo - app módulo:

 ```
 buildscript {
    ext {
        springBootVersion = '1.5.3.RELEASE'
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group = 'com.cidades.features'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    compile('org.springframework.boot:spring-boot-starter-web')
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile 'org.springframework.boot:spring-boot-devtools'
    compile('io.springfox:springfox-swagger2:2.7.0')
    compile('io.springfox:springfox-swagger-ui:2.7.0')
    compile('org.apache.commons:commons-lang3:3.5')
    compile 'org.apache.commons:commons-collections4:4.1'
	compile 'org.projectlombok:lombok:1.16.18'
    compile('org.modelmapper:modelmapper:2.3.0')
    runtime('com.h2database:h2:1.0.60')
    compile('com.baidu.unbiz:fluent-validator:1.0.9'){
   		exclude group: 'org.slf4j', module: 'slf4j-log4j12' 
   	}
   	compile('org.apache.commons:commons-csv:1.4')
    compile('org.apache.logging.log4j:log4j-core:2.8.2')
   	compile('org.apache.logging.log4j:log4j-api:2.8.2')
   	compile('cz.jirutka.rsql:rsql-parser:2.0.0')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```

## Banco de dados H2

H2 é um banco de dados escrito em java, muito rápido, free e com codigo fonte incluído.
Para utiliza-lo, deve adicionar na dependência do projeto o seguinte: com.h2database:h2

Após adicionar e baixar as dependências, deve ser configurado o arquivo application:properties, conforme abaixo:

//H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2

//Datasource
spring.datasource.url=jdbc:h2:file:~/testdb
spring.datasource.username=h2sa
spring.datasource.password=admin
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update

Em seguida, para acessar o console, basta abrir o browser e digitar o seguinte caminho:

localhost:8080/h2 e será aberta uma tela para acessar o banco e em seguida, terá o devido acesso.

Importante lembrar que ele será executado na execução da aplicação do projeto.

## Projeto

Foram criadas estruturas padrões para controlar o CRUD do projeto, utilizando springboot, swagger (para documentação das API's) entre outros.
Foram utilizados/criados métodos genericos de classes que desta maneira novos métodos DAO
passar diferentes entidades ao extender esta classe e, assim, reusar os métodos desta classe abstrata.
Por exemplo, se você tem uma entidade Pedido (T), no qual a chave primária (PK) é um objeto do tipo Long e deseja criar um DAO para ela, o código ficaria:

class PedidoDao extends AbstractDao<Long, Pedido> {

}
Após isto, você pode usar na classe PedidoDao os métodos de AbstractDao, e estarão todos aderentes a particularidade da sua entidade Pedido.
Sobre o código específico do construtor:

this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
A primeira parte:

(ParameterizedType) this.getClass().getGenericSuperclass()
É usada para pegar o tipo de classe parametrizável (ParameterizedType), que contém informações de PK e T. Mas como o código deseja pegar a entidade T, ele usa:

getActualTypeArguments()[1]
Pois na posição [0] está a PK.

Esses vão ser tipos declarados pelas classes que extenderão o seu DAO.

Suponha que você crie um DAO concreto para trabalhar com uma entidade Book. Ele deverá ser declarado de foma parecida com:

public class BookDao extends AbstractDao<Long, Book> {
    ...
}
Todas as referências aos tipos PK e T agora serão dos tipos Long e Book, respectivamente. E o seguinte trecho de código será válido:

BookDao bookDao = new BookDao();
Book myBook = bookDao.getByKey(100); // Aqui, getByKey aceita um Long e retorna um Book
É o mesmo conceito utilizado com List e Map, por exemplo. Para criar um ArrayList de String, você deve invocar:

List<String> strings = new ArrayList<String>();
// O metodo add recebera somente strings agora
strings.add("Hello");
Se você buscar a fonte da classe List, verá que ela foi declarada de forma genérica:

public interface List<E> extends Collection<E> {
    ...
}
A linha do construtor é uma forma de buscar, em tempo de execução, a classe do tipo genérico T.
Fonte: https://pt.stackoverflow.com/questions/293792/entender-o-uso-de-generics-em-uma-classe-abstrata-dao-hibernate
