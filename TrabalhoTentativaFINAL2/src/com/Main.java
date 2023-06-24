package com;
    


import java.sql.ResultSet;
import java.util.Scanner;
import com.db.DBConect;
import com.model.Conta;
import com.model.contaCorrente;
import com.model.contaPoupanca;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        boolean sair = false;

        
        
        
        DBConect database = new DBConect();
        
        while (!sair) {
            System.out.println("=== MENU ===");
            System.out.println("1. Cadastrar Conta");
            System.out.println("2. Acessar Conta");
            System.out.println("3. Acessar Administrador");
            System.out.println("4. Sair");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada
            
            

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do titular da conta: ");
                    String titular = scanner.nextLine();

                    System.out.println("Informe o CPF: ");
                    String cpf = scanner.nextLine();

                    System.out.println("Informe o número da conta: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada

                    System.out.println("Digite a senha para o Cadastro da conta: ");
                    int senha = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
                    
                    System.out.println("Digite a Quantidade de Saldo Inicial");
                    int saldo = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
                    
                    System.out.println("Informe o tipo de conta (1 - Conta Corrente, 2 - Conta Poupança): ");
                    int tipoConta = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada

                    Conta novaConta;

                    if (tipoConta == 1) {
                        novaConta = new contaCorrente(titular, cpf, numero, senha);
                    } else if (tipoConta == 2) {
                        novaConta = new contaPoupanca(titular, cpf, numero, senha);
                    } else {
                        mensagemStatus("Tipo de conta inválido!");
                        return;
                    }

                    try {
                        database.conectarBanco();

                        boolean statusQuery = database.executarUpdateSql("INSERT INTO `javatrab`.`conta`(numero, titular, cpf, senha, saldo, tipoConta) VALUES ('" + numero + "', '" + titular + "', '" + cpf + "', '" + senha + "', " + saldo + ", " + tipoConta + ")");

                        System.out.println("Usuário Cadastrado com Sucesso");
                    
                        database.desconectarBanco();
                    } catch (Exception e) {
                        mensagemStatus("Erro ao cadastrar a conta: " + e.getMessage());
                    }
                		
                    break;
                case 2:
               
                	        	
                    System.out.println("Informe o CPF: ");
                     cpf = scanner.nextLine();

                    System.out.println("Digite a senha para acessar a conta: ");
                    senha = scanner.nextInt();
                    
                   try {
                	   database.conectarBanco();

                       ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM conta");

                       {
                           System.out.println
                           ("numero - " + resultadoConsulta.getString("numero") + " | - " +  resultadoConsulta.getString("cpf") + resultadoConsulta.getString("senha"));
                           
                           database.desconectarBanco();
                       }
                    }
                	catch (Exception e) {
                		
                		
                	}
                	
                
                	
                    System.out.println("1 - Conta Poupança");
                    System.out.println("2 - Conta Corrente");
                    System.out.println("3 - Voltar");

                    int opcaoConta = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada

                    switch (opcaoConta) {
                        case 1:
                            System.out.println("Digite a opção desejada:");
                            System.out.println("1 - Consultar saldo (Conta Poupança)");
                            System.out.println("2 - Realizar saque (Conta Poupança)");
                            System.out.println("3 - Realizar depósito (Conta Poupança)");
                            System.out.println("0 - Sair");

                            int opcaoPoupanca = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer de entrada

                            while (opcaoPoupanca != 0) {
                                switch (opcaoPoupanca) {
                                    case 1:
                                        // Consultar saldo da Conta Poupança
                                        System.out.println("Saldo Conta Poupança: R$ " + contaPoupanca.getSaldo());
                                        break;
                                    case 2:
                                        // Realizar saque da Conta Poupança
                                        System.out.println("Digite o valor do saque (Conta Poupança): ");
                                        float valor = scanner.nextFloat();
                                        contaPoupanca.sacar(valor);
                                        break;
                                    case 3:
                                        // Realizar depósito na Conta Poupança
                                        System.out.println("Digite o valor do depósito (Conta Poupança): ");
                                        valor = scanner.nextFloat();
                                        contaPoupanca.realizaDeposito(valor);                                     
                                        break;

                                    default:
                                        System.out.println("Opção inválida.");
                                        break;
                                }

                                System.out.println("1 - Consultar saldo (Conta Poupança)");
                                System.out.println("2 - Realizar saque (Conta Poupança)");
                                System.out.println("3 - Realizar depósito (Conta Poupança)");
                                System.out.println("0 - Sair");

                                opcaoPoupanca = scanner.nextInt();
                                scanner.nextLine(); // Limpar o buffer de entrada
                            }

                            break;
                        case 2:
                            System.out.println("Digite a opção desejada:");
                            System.out.println("1 - Consultar saldo (Conta Corrente)");
                            System.out.println("2 - Realizar saque (Conta Corrente)");
                            System.out.println("3 - Realizar saque com Cheque Especial (Conta Corrente)");
                            System.out.println("0 - Sair");

                            int opcaoCorrente = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer de entrada

                            while (opcaoCorrente != 0) {
                                switch (opcaoCorrente) {
                                    case 1:
                                        // Consultar saldo da Conta Corrente
                                        System.out.println("Saldo Conta Corrente: R$ " + contaCorrente.getSaldo());
                                        break;
                                    case 2:
                                        // Realizar saque da Conta Corrente
                                        System.out.println("Digite o valor do saque (Conta Corrente): ");
                                        float valor = scanner.nextFloat();
                                        contaCorrente.sacar(valor);
                                        break;
                                    case 3:
                                        // Realizar saque com Cheque Especial da Conta Corrente
                                        System.out.println("Digite o valor do saque com Cheque Especial (Conta Corrente): ");
                                        valor = scanner.nextFloat();
                                        contaCorrente.sacarComChequeEspecial(valor);
                                        break;
                                    default:
                                        System.out.println("Opção inválida.");
                                        break;
                                }

                                System.out.println("1 - Consultar saldo (Conta Corrente)");
                                System.out.println("2 - Realizar saque (Conta Corrente)");
                                System.out.println("3 - Realizar saque com Cheque Especial (Conta Corrente)");
                                System.out.println("0 - Sair");

                                opcaoCorrente = scanner.nextInt();
                                scanner.nextLine(); // Limpar o buffer de entrada
                            }

                            break;
                        case 3:
                            // Voltar para o menu principal
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }

                    break;
                case 3:
                    System.out.println("Informe o nome do administrador: ");
                    String nomeAdmin = scanner.nextLine();

                    System.out.println("Informe a senha do administrador: ");
                    String senhaAdmin = scanner.nextLine();

                    System.out.println("Informe o CPF do administrador: ");
                    String cpfAdmin = scanner.nextLine();

                    // Verificar no banco de dados se as informações do administrador estão corretas

                    // Se as informações estiverem corretas, acessar o menu do administrador
                    while (true) {
                        System.out.println("=== MENU DO ADMINISTRADOR ===");
                        System.out.println("1. Visualizar todas as pessoas cadastradas");
                        System.out.println("2. Mudar senha da conta de um usuário");
                        System.out.println("3. Sair");

                        opcao = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer de entrada

                        switch (opcao) {
                        case 1:

                                try {
                                    database.conectarBanco();

                                    ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM pessoa");

                                    while (resultadoConsulta.next()) {
                                        System.out.println
                                        ("ID - " + resultadoConsulta.getString("id") + " | NOME - " + 
                                        resultadoConsulta.getString("nome") +
                                        resultadoConsulta.getString("cpf") + 
                                        resultadoConsulta.getString("conta") +
                                        resultadoConsulta.getString("senha") +
                                        resultadoConsulta.getString("saldo"));
                                    }

                                    database.desconectarBanco();

                                }
                                catch (Exception e) {


                            }
                                
                        
                            break;
                        case 2:
                            System.out.println("Informe o CPF do usuário: ");
                            String cpfUsuario = scanner.nextLine();

                            // Verificar se o CPF existe no banco de dados

                            // Se existir, permitir a alteração da senha da conta do usuário

                            break;
                        case 3:
                            System.out.println("Saindo do menu do administrador...");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }

                    if (opcao == 3) {
                        break;
                    }
                }

                break;
            case 4:
                sair = true;
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    scanner.close();
}

public static  void mensagemStatus(String string) {
    // TODO Auto-generated method stub

}

}