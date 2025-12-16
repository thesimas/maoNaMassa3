package br.edu.ifsc.fln.maonamassa3.mainapp;
import br.edu.ifsc.fln.maonamassa3.domain.*;
import br.edu.ifsc.fln.maonamassa3.exceptions.*;
import br.edu.ifsc.fln.maonamassa3.report.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    /* Declarando as listas aqui para eles se tornarem atributos da classe
    main app e assim eu poder acessar dentro do main string args.
    E como está privado isso significa que o apenas o main app pode usar eles, ja que não tem gettrs e setters*/
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Modelo> modelos = new ArrayList<>();
    private static List<Marca> marcas = new ArrayList<>();
    private static List<Servico> servicos = new ArrayList<>();
    private static List<Veiculo> veiculos = new ArrayList<>();
    private static List<Cor> cores = new ArrayList<>();
    private static List<OrdemServico> ordensDeServicos = new ArrayList<>();
    private static Scanner leia = new Scanner(System.in);

    public static void main(String[] args) throws ExceptionLavacao {
        inicializarDados();
        System.out.println();
        int opcao = 0;
//        Servico.setPontos(80); se eu der setPontos aqui, cada serviço irá valer 80 pontos.
        while (true) {
            menu();
            System.out.println("Qual operação você deseja realizar?");
            opcao = leia.nextInt();
            System.out.println("\n\n");
            switch (opcao) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    listarVeiculos();
                    break;
                case 3:
                    criarNovaOs();
                    break;
                case 4:
                    listarOS();
                    break;
                case 5:
                    testarErros();
                    break;
                case 0:
                    break;
            }
            if (opcao == 0){
                System.out.println("Você escolheu sair...");
                break;
            }
            limparTela();
        }
    }

    private static void limparTela(){
        for(int i = 0; i < 2; i++){
            System.out.println();
        }
    }

    private static void menu(){

        System.out.println("------------- Menu ---------------");
        System.out.println("1  -  Listar clientes");
        System.out.println("2  -  Listar Veículos");
        System.out.println("3  -  Criar Ordem de Servico");
        System.out.println("4  -  Listar Ordens de Serviços");
        System.out.println("5  -  Testar Exceções da Orden de Serviço");
        System.out.println("0  -  Sair");
    }

    private static void criarNovaOs() {

        System.out.println("Listando veículos: ");
        int x = 0;
        for(Veiculo veiculo : veiculos) {
            System.out.println((x+1) + "° Veículo: " + veiculo.getModelo().getDescricao() + " - " + veiculo.getPlaca());
            x ++;
        }
        System.out.println("Qual veículo você deseja lavar?");
        int escolhaVeiculo = leia.nextInt();
        Veiculo veiculoSelecionado = veiculos.get((escolhaVeiculo -1));
        leia.nextLine();
        String opcao2 = "";
        List<Servico> servicosSelecionados = new ArrayList<>(); // Arraylist para adcionar servicos.
        do {
            for(Servico servico : servicos) {
                System.out.println(servico.getId() + "° Serviço: "+ servico.getDescricao());
            }
            System.out.println("Qual serviço você quer selecionar? ");
            int servicoEscolhido = leia.nextInt();
            servicosSelecionados.add(servicos.get((servicoEscolhido - 1)));
            // adcionando serviços que o usuario selecionar com base no indice[numerico] do array associativo de objetos do tipo SERVICO.
            leia.nextLine();
            System.out.println("Deseja adicionar mais?\nCaso não queira adcionar mais digite: 'N' para sair");
            opcao2 = leia.nextLine();
        }while (!opcao2.equalsIgnoreCase("n"));

        System.out.println("Quantos por cento deseja receber de desconto? ");
        double desconto = leia.nextDouble();
        long idGerado = ordensDeServicos.size() + 1;
        OrdemServico osNova = new OrdemServico(idGerado, LocalDate.now(), desconto, EStatus.ABERTO, veiculoSelecionado);
        for (int index = 0; index < servicosSelecionados.size(); index++) {
            try {
                osNova.addItemOS("teste", servicosSelecionados.get(index));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        osNova.setStatus(EStatus.FECHADA);
        System.out.println("Ordem de serviço finalizada, imprimindo ela...");
        try {
            limparTela();
            System.out.println(ImpressaoOS.imprimirOS(osNova));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        ordensDeServicos.add(osNova);
        // adcionando a os nova para ficar registrada nas listas em tempo de execução do programa.
    }

    private static void listarVeiculos(){
        Relatorio relatorio = new Relatorio();
        for(Veiculo veiculo : veiculos) {
            System.out.println(relatorio.imprimir(veiculo));
        }
    }

    // como o metodo imprimir não é estático, precisa ser instanciado o objeto relatorio, para poder chamar o metodo.
    private static void listarClientes() {
        Relatorio relatorio = new Relatorio();
        for (Cliente cliente : clientes) {
            System.out.println(relatorio.imprimir(cliente));
        }
    }

    private static void testarErros() {
        OrdemServico osTeste = new OrdemServico(666, LocalDate.now(), 0, EStatus.ABERTO, veiculos.get(1));
        // TESTE 1: indice invalido
        try {
            System.out.println("\t\t\tOrdem de serviço para teste de Execeções");
            osTeste.addItemOS("Item 1", servicos.get(2));
            System.out.println(ImpressaoOS.imprimirOS(osTeste)); // Print 1

            System.out.println("Tentando remover índice inválido...");
            osTeste.removeItemOS(50);
        } catch (ExceptionLavacao e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // TESTE 2: esvaziar lista e tentar calcular
        try {
            System.out.println("Removendo o item existente...");
            osTeste.removeItemOS(0);
            System.out.println(ImpressaoOS.imprimirOS(osTeste));
        } catch (ExceptionLavacao e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // TESTE 3: adicionar em OS fechada
        try {
            osTeste.setStatus(EStatus.FECHADA);
            System.out.println("Tentando adicionar em OS Fechada...");
            osTeste.addItemOS("Item Proibido", servicos.get(1));
        } catch (ExceptionLavacao erro){
            System.out.println("Erro: " + erro.getMessage());
        }

        // TESTE 4: imprimir OS fechada (mas precisa ter itens, senão da erro de lista vazia)
        try {
            osTeste.setStatus(EStatus.ABERTO);
            osTeste.addItemOS("Item Válido", servicos.get(0));
            osTeste.setStatus(EStatus.FECHADA);

            System.out.println(ImpressaoOS.imprimirOS(osTeste));
        } catch (ExceptionLavacao erro){
            System.out.println(erro.getMessage());
        }

    }
    // Pecorrer o array de os e printa usando o metodo estatico da classe ImpressaoOS
    private static void listarOS() {
        for(OrdemServico ordemServico : ordensDeServicos){
            try{
                System.out.println(ImpressaoOS.imprimirOS(ordemServico));
            }catch(ExceptionLavacao ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    // Metodo para instanciar os objetos.
    private static void inicializarDados(){
        Cor azul = new Cor("Azul");
        cores.add(azul);
        Cor prata = new Cor("Prata");
        cores.add(prata);
        Cor vermelho = new Cor("Vermelho");
        cores.add(vermelho);

        Marca renault = new Marca(01, "Renault");
        marcas.add(renault);
        Marca fiat = new Marca(02, "Fiat");
        marcas.add(fiat);
        Marca volkswagen = new Marca(03, "Volkswagen");
        marcas.add(volkswagen);

        Modelo modelo1 = new Modelo(01, "Jetta", 1200, ETipoCombustivel.FLEX, ECategoria.MEDIO, marcas.get(2));
        modelos.add(modelo1);
        Modelo modelo2 = new Modelo(02, "Toro", 1300, ETipoCombustivel.GASOLINA, ECategoria.GRANDE, marcas.get(1));
        modelos.add(modelo2);
        Modelo modelo3 = new Modelo(03, "Kwid", 600, ETipoCombustivel.GNV, ECategoria.PEQUENO, marcas.get(0));
        modelos.add(modelo3);


        Cliente pf1 = new PessoaFisica(
                01,
                "Luciano",
                "48 991670093",
                "luciano@gmail.com",
                LocalDate.now(),
                "000.111.333-44",
                LocalDate.of(2000,12,10)
        );
        clientes.add(pf1);

        Cliente pf2 = new PessoaFisica(
                02,
                "João",
                "48 991033448",
                "joao@gmail.com",
                LocalDate.now(),
                "222.333.555-44",
                LocalDate.of(2003,07,21)
        );
        clientes.add(pf2);

        Cliente pj1 = new PessoaJuridica(
                01,
                "Conselho Federal de Técnicos - CFT",
                "0800 016 1515",
                "ouvidoria@cft.org.br",
                LocalDate.now(),
                "11.333.555/0001-44",
                "448-555-666-9"
        );
        clientes.add(pj1);

        Veiculo veiculo1 = new Veiculo(01, "RX49-A6F", "56 mil KM rodado!", modelo1, cores.get(0), pf1);
        veiculos.add(veiculo1);
        pf1.addVeiculo(veiculo1);
        Veiculo veiculo2 = new Veiculo(02, "FWA6-S56", "39 mil KM rodado!", modelo2, cores.get(1), pf2);
        veiculos.add(veiculo2);
        pf2.addVeiculo(veiculo2);
        Veiculo veiculo3 = new Veiculo(03, "TR35-LK0", "70 mil KM rodado!", modelo3, cores.get(2), pj1);
        veiculos.add(veiculo3);
        pj1.addVeiculo(veiculo3);


        Servico lavagemSimples = new Servico(1, "Lavação Simples", 50.00f, ECategoria.PADRAO);
        servicos.add(lavagemSimples);
        Servico lavagemCompleta = new Servico(2, "Lavação Completa", 80.00f, ECategoria.PADRAO);
        servicos.add(lavagemCompleta);
        Servico cera = new Servico(3, "Enceramento", 30.00f, ECategoria.PADRAO);
        servicos.add(cera);
        Servico polimento = new Servico(4, "Polimento", 90.00f, ECategoria.PADRAO);
        servicos.add(polimento);

        OrdemServico os1 = new OrdemServico(1, LocalDate.now(), 10.00, EStatus.ABERTO, veiculo1);
        ordensDeServicos.add(os1);
        try {
            os1.addItemOS("Quero que passe pretinho nas rodas!", lavagemCompleta);
            os1.addItemOS("Cuidado para não arranhar a pintura!", cera);
            os1.addItemOS("Quero que dê uma polida na porta lateral!", polimento);
            os1.setStatus(EStatus.FECHADA);
        }catch(ExceptionLavacao erro){
            System.err.println("Erro ao adcionar servico na Ordem de Servico!");
        }
        OrdemServico os2 = new OrdemServico(2, LocalDate.now(), 5.00, EStatus.ABERTO, veiculo3);
        ordensDeServicos.add(os2);
        try {
            os2.addItemOS(45,"Lava bem esse carro!", lavagemCompleta);
            os2.addItemOS("Se possivel passe silicone no interior do carro!", cera);
            os2.setStatus(EStatus.FECHADA);
        }catch (ExceptionLavacao erro){
            System.err.println("Erro ao adcionar servico na Ordem de Servico!");
        }
    }
}