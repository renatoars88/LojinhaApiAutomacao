package dataFactory;

import pojo.ComponentePojo;
import pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {
    public static ProdutoPojo criarProdutoComumComOValorIgualA(double valor){

        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Play Station");
        produto.setProdutoValor(0.00);

        List<String> cores = new ArrayList<>();
        cores.add("preto");
        cores.add("branco");
        produto.setProdutoCores(cores);

        produto.setProdutoUrlMock("");

        List<ComponentePojo> componentes = new ArrayList<>();

        ComponentePojo primeirocomponente = new ComponentePojo();
        primeirocomponente.setComponenteNome("Controle");
        primeirocomponente.setComponenteQuantidade(1);
        componentes.add(primeirocomponente);

        produto.setComponentes(componentes);

        return produto;
    }
}
