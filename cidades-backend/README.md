# XPTO Systems - Cidades

Sistema WEB para leitura e manute��o de uma lista de cidades.

# Requisitos

Tem como requisitos do sistema ler um arquivo CSV (cidades.csv) das cidade e inserir os dados em uma base de dados.

# Requisitos da API

1. Ler o arquivo CSV das cidades para a base de dados - OK;
2. Retornar somente as cidades que s�o capitais ordenadas por nome - OK;
3. Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades - Implementado, por�m n�o validado;
4. Retornar a quantidade de cidades por estado - Implementado, por�m n�o validado;
5. Obter os dados da cidade informando o id do IBGE - Implementado atrav�s do m�todo padr�o get(http://localhost:9000/swagger-ui.html#/cidade-controller-impl/filterUsingGET);
6. Retornar o nome das cidades baseado em um estado selecionado - Implementado, por�m n�o validado;
7. Permitir adicionar uma nova Cidade - Implementado atrav�s do m�todo padr�o POST(save);
8. Permitir deletar uma cidade - Implementado atrav�s do m�todo padr�o DELETE;
9. Permitir selecionar uma coluna (do CSV) e atrav�s dela entrar com uma string para filtrar. retornar assim todos os objetos que contenham tal string - N�o Implementado;
10. Retornar a quantidade de registro baseado em uma coluna. N�o deve contar itens iguais - Implementado atrav�s do m�todo padr�o get(http://localhost:9000/swagger-ui.html#/cidade-controller-impl/filterUsingGET);;
11. Retornar a quantidade de registros total - Implementado, por�m n�o validado;;
12. Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localiza��o (dist�ncia em KM em linha reta) - N�o Implementado;

## Estrutura do Projeto

 Para importar o projeto, basta realizar o clone via https://github.com/rodrigoExter/cidade-api.git

### Build Autom�tica

 O processo de build autom�tica � realizado por meio da ferramenta [Gradle](http://gradle.org).


 Exemplo - app m�dulo:

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

H2 � um banco de dados escrito em java, muito r�pido, free e com codigo fonte inclu�do.
Para utiliza-lo, deve adicionar na depend�ncia do projeto o seguinte: com.h2database:h2

Ap�s adicionar e baixar as depend�ncias, deve ser configurado o arquivo application:properties, conforme abaixo:

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

localhost:8080/h2 e ser� aberta uma tela para acessar o banco e em seguida, ter� o devido acesso.

Importante lembrar que ele ser� executado na execu��o da aplica��o do projeto.

## Projeto

Foram criadas estruturas padr�es para controlar o CRUD do projeto, utilizando springboot, swagger (para documenta��o das API's) entre outros.
Foram utilizados/criados m�todos genericos de classes que desta maneira novos m�todos DAO
passar diferentes entidades ao extender esta classe e, assim, reusar os m�todos desta classe abstrata.
Por exemplo, se voc� tem uma entidade Pedido (T), no qual a chave prim�ria (PK) � um objeto do tipo Long e deseja criar um DAO para ela, o c�digo ficaria:

class PedidoDao extends AbstractDao<Long, Pedido> {

}
Ap�s isto, voc� pode usar na classe PedidoDao os m�todos de AbstractDao, e estar�o todos aderentes a particularidade da sua entidade Pedido.
Sobre o c�digo espec�fico do construtor:

this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
A primeira parte:

(ParameterizedType) this.getClass().getGenericSuperclass()
� usada para pegar o tipo de classe parametriz�vel (ParameterizedType), que cont�m informa��es de PK e T. Mas como o c�digo deseja pegar a entidade T, ele usa:

getActualTypeArguments()[1]
Pois na posi��o [0] est� a PK.

Esses v�o ser tipos declarados pelas classes que extender�o o seu DAO.

Suponha que voc� crie um DAO concreto para trabalhar com uma entidade Book. Ele dever� ser declarado de foma parecida com:

public class BookDao extends AbstractDao<Long, Book> {
    ...
}
Todas as refer�ncias aos tipos PK e T agora ser�o dos tipos Long e Book, respectivamente. E o seguinte trecho de c�digo ser� v�lido:

BookDao bookDao = new BookDao();
Book myBook = bookDao.getByKey(100); // Aqui, getByKey aceita um Long e retorna um Book
� o mesmo conceito utilizado com List e Map, por exemplo. Para criar um ArrayList de String, voc� deve invocar:

List<String> strings = new ArrayList<String>();
// O metodo add recebera somente strings agora
strings.add("Hello");
Se voc� buscar a fonte da classe List, ver� que ela foi declarada de forma gen�rica:

public interface List<E> extends Collection<E> {
    ...
}
A linha do construtor � uma forma de buscar, em tempo de execu��o, a classe do tipo gen�rico T.
Fonte: https://pt.stackoverflow.com/questions/293792/entender-o-uso-de-generics-em-uma-classe-abstrata-dao-hibernate
