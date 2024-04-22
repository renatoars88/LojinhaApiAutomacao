package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes Api Rest do módulo de Produto")
public class ProdutoTest {
    private String token;

    @BeforeEach //Tudo que for escrito aqui vai ser escrito antes de um teste
    public void beforeEach(){
        //Configurando os dados da API Rest da lojinha
        baseURI = "http://165.227.93.41";
        //port = 8080;
        basePath = "/lojinha";



        //Obter o token do usuário admin
        this.token = given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdmin())

        .when()
                .post("/v2/login")

        .then()
                .extract()
                    .path("data.token"); //dentro do data pegue o token dentro de data, dentro de token
        //se quisesse extrair a mensagem colocaria "message"

    }

    @Test
    @DisplayName("Validar que o valor do produto igual a 0.00 não é permitido") //Teste 1
    public void testValidarLimitesProibidosValorProduto(){

        //Tentar inserir um produto com valor 0.00 e validar que a mensagem de erro
        // foi apresentada e o status code retorna 422

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token) //mando o header token com a variável de nome token
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00)) //classes acima
        .when()
                .post("/v2/produtos")

        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);

    }
    @Test
    @DisplayName("Validar que o valor do produto maior que 7000.01 não é permitido") //Teste 2
    public void testValidarLimitesMaiorSeteMilValorProduto(){
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token) //mando o header token com a variável de nome token
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
        .when()
                .post("/v2/produtos")

        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);

    }
}
