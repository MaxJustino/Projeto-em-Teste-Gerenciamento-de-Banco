package com;
    


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.db.DBConect;
import com.model.Conta;
import com.model.contaCorrente;
import com.model.contaPoupanca;
import java.text.DecimalFormat;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        boolean sair = false;

        
        
        
        DBConect database = new DBConect();
        
        while (!sair) {
            System.out.println("=== BEM VINDO(A) ===");
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
               //ACESSO A CONTA 
                	        	
                    System.out.println("Informe o CPF: ");
                     cpf = scanner.nextLine();

                    System.out.println("Digite a senha para acessar a conta: ");
                    senha = scanner.nextInt();
                    
                    
                    
                   try {
                	   database.conectarBanco();

                	   ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM conta WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");
                	   
                	   if (resultadoConsulta.next()) {
                           System.out.println("Número: " + resultadoConsulta.getInt("numero"));
                           System.out.println("CPF: " + resultadoConsulta.getString("cpf"));
                           System.out.println("Senha: " + resultadoConsulta.getString("senha"));
                       } else {
                           System.out.println("CPF ou senha incorretos.");
                           database.desconectarBanco();
                           System.out.println("Verifique a conta e acesse novamente");
                           break;
                       }
                                     
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                 	
                    System.out.println("1 - Conta Poupança");
                    System.out.println("2 - Conta Corrente");
                    System.out.println("3 - Voltar");

                    int opcaoConta = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada

                    switch (opcaoConta) {
                        case 1:
                        	//SELECIONANDO A OPÇÃO DESEJADA
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

                                    	try {
                                                                        	
                                    	    database.conectarBanco();
                                    	    
                                    	    ResultSet resultadoConsulta = database.executarQuerySql("SELECT saldo FROM conta_poupanca WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                    	    if (resultadoConsulta.next()) {
                                    	        double saldoBanco = resultadoConsulta.getDouble("saldo");
                                    	        System.out.println("Saldo na Conta R$: " + saldoBanco);
                                    	    } else {
                                    	        System.out.println("CPF ou senha incorretos.");
                                    	    }

                                    	    database.desconectarBanco();
                                    	} catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        
                                        break;
                                        
                                    case 2:
                                    	                              
                                                                      
                                        try {
                                        	
                                        	 // Realizar saque da Conta Poupança
                                            System.out.println("Digite o valor do saque (Conta Poupança): ");
                                            float valor = scanner.nextFloat();
                                            database.conectarBanco();

                                            ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM conta_poupanca WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                            if (resultadoConsulta.next()) {
                                                // CPF e senha válidos, realizar o saque
                                                double saldoBanco = resultadoConsulta.getDouble("saldo");

                                                if (saldoBanco >= valor) {
                                                    // Saldo suficiente para o saque
                                                    double novoSaldo = saldoBanco - valor;

                                                    // Atualizar o saldo na tabela conta_poupanca
                                                    boolean statusQuery = database.executarUpdateSql("UPDATE conta_poupanca SET saldo = " + novoSaldo + " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                                    System.out.println("Saque realizado com sucesso.");
                                                    System.out.println("Novo saldo: " + novoSaldo);
                                                } else {
                                                    System.out.println("Saldo insuficiente para o saque.");
                                                }
                                            } else {
                                                System.out.println("CPF ou senha incorretos.");
                                            }

                                            database.desconectarBanco();
                                        }  catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                                                  
                                        break;   
                                        
                                    case 3:
                                    	try {
                                    	    System.out.println("Digite o valor do depósito (Conta Poupança): ");
                                    	    float depositarbanco = scanner.nextFloat();

                                    	    database.conectarBanco();

                                    	    ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM conta_poupanca WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                    	    if (resultadoConsulta.next()) {
                                    	        // CPF e senha válidos, realizar o depósito
                                    	        double saldoPoupanca = resultadoConsulta.getDouble("saldo");
                                    	        double taxa = resultadoConsulta.getFloat("taxa");  // Obtém a taxa de juros da conta poupança

                                    	        // Cálculo do novo saldo considerando a taxa de juros atualizada
                                    	        double novoSaldo = saldoPoupanca + (depositarbanco / (1 + taxa));
                                    	        double novaTaxa = taxa + 0.005;  // Atualiza a taxa de juros

                                    	        // Atualiza o saldo e a taxa de juros na tabela conta_poupanca
                                    	        boolean statusQuery = database.executarUpdateSql("UPDATE conta_poupanca SET saldo = " + novoSaldo + ", taxa = " + novaTaxa + " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                    	        if (statusQuery) {
                                    	            System.out.println("Depósito realizado com sucesso.");
                                    	            DecimalFormat decimalFormat = new DecimalFormat("#.00");


                                    	         System.out.println("Saldo atual Da conta: " + decimalFormat.format(novoSaldo));
                                    	         System.out.println("Taxa de juros atualizada: " + decimalFormat.format(novaTaxa));
                                    	        } else {
                                    	            System.out.println("Falha ao atualizar saldo e taxa de juros.");
                                    	        }
                                    	    } else {
                                    	        System.out.println("CPF ou senha inválidos.");
                                    	    }

                                    	    database.desconectarBanco();
                                    	}  catch (Exception e) {
                                             e.printStackTrace();
                                         }
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
                            System.out.println("3 - Realizar saque com  (Conta Corrente)");
                            System.out.println("0 - Sair");

                            int opcaoCorrente = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer de entrada

                            while (opcaoCorrente != 0) {
                                switch (opcaoCorrente) {
                                    case 1:
                                        
                                      
                                        
                                        try {
                                        	database.conectarBanco();

                                            ResultSet resultadoConsulta = database.executarQuerySql("SELECT saldo FROM conta WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");
                                            
                                            if (resultadoConsulta.next()) {
                                                double saldobanco = resultadoConsulta.getDouble("saldo");
                                                System.out.println("Saldo na Conta R$: " + saldobanco);
                                            } else {
                                                System.out.println("CPF ou senha incorretos.");
                                            }
                                            
                                            database.desconectarBanco();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        
                                        break;
                                        
                                    case 2:
                                    	
                                    	// Realizar saque da Conta Corrente
                                        System.out.println("Digite o valor do saque (Conta Corrente): ");
                                        float valor = scanner.nextFloat();

                                        try {
                                            database.conectarBanco();

                                            ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM conta WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                            if (resultadoConsulta.next()) {
                                                // CPF e senha válidos, realizar o saque
                                                double saldoBanco = resultadoConsulta.getDouble("saldo");

                                                if (saldoBanco >= valor) {
                                                    // Saldo suficiente para o saque
                                                    double novoSaldo = saldoBanco - valor;

                                                    // Atualizar o saldo na tabela conta
                                                    boolean statusQuery = database.executarUpdateSql("UPDATE conta SET saldo = " + novoSaldo + " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");

                                                    System.out.println("Saque realizado com sucesso.");
                                                    System.out.println("Novo saldo: " + novoSaldo);
                                                } else {
                                                    System.out.println("Saldo insuficiente para o saque.");
                                                }
                                            } else {
                                                System.out.println("CPF ou senha incorretos.");
                                            }

                                            database.desconectarBanco();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        
                                        break;
                                        
                                    case 3:
                                    	
                                    	try {
                                    		System.out.println("Opção Indisponível");
                                    		
                                    	   /* System.out.println("Digite o valor do saque com Cheque Especial (Conta Corrente): ");
                                    	    float valorSaque = scanner.nextFloat();
                                    	    database.conectarBanco();

                                    	    double saldoTotal = contaCorrente.getSaldo();
                                    	    
                                    	    // Obter o valor do cheque especial do banco de dados
                                    	    ResultSet resultSet = database.executarQuerySql("SELECT cheque_especial FROM conta_corrente WHERE numero = " + contaCorrente.getNumero());
                                    	    
                                    	    if (resultSet.next()) {
                                    	        float chequeEspecialAtual = resultSet.getFloat("cheque_especial");
                                    	        System.out.println("Digite o valor do saque com Cheque Especial (Conta Corrente): 1");
                                    	        
                                    	        if (valorSaque > saldoTotal + chequeEspecialAtual) {
                                    	            System.out.println("Saldo insuficiente. Cheque especial ultrapassado.");
                                    	        } else {
                                    	            saldoTotal -= valorSaque;
                                    	            chequeEspecialAtual -= valorSaque;
                                    	            System.out.println("Digite o valor do saque com Cheque Especial (Conta Corrente): 2");
                                    	            
                                    	            System.out.println("Saque realizado com sucesso.");
                                    	            
                                    	            boolean statusQuery = database.executarUpdateSql("UPDATE conta_corrente SET saldo = " + saldoTotal + " WHERE numero = " + contaCorrente.getNumero());
                                    	            
                                    	            if (statusQuery) {
                                    	                System.out.println("Saldo atualizado no banco de dados.");
                                    	                
                                    	            } else {
                                    	                System.out.println("Falha ao atualizar o saldo no banco de dados.");
                                    	            }
                                    	            
                                    	            statusQuery = database.executarUpdateSql("UPDATE conta_corrente SET cheque_especial = " + chequeEspecialAtual +
                                    	                " WHERE numero = " + contaCorrente.getNumero());
                                    	            
                                    	            if (statusQuery) {
                                    	                System.out.println("Valor do cheque especial atualizado no banco de dados.");
                                    	            } else {
                                    	                System.out.println("Falha ao atualizar o valor do cheque especial no banco de dados.");
                                    	            }
                                    	        }
                                    	    }
                                    	    
                                    	    database.desconectarBanco();
                                    	    */
                                    	}catch (Exception e)
                                    	 {
                                    	    
                                    	}
                                    	
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

                    try {
                        database.conectarBanco();

                        ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM administrador WHERE nome = '" + nomeAdmin +
                                "' AND senha = '" + senhaAdmin + "' AND cpf = '" + cpfAdmin + "'");

                        if (resultadoConsulta.next()) {
                            System.out.println("Acesso autorizado. Bem-vindo, " + nomeAdmin + "!");

                            exibirMenuAdministrador();

                            while (true) {
                                System.out.println("=== MENU DO ADMINISTRADOR ===");
                                System.out.println("1. Visualizar todas as pessoas cadastradas");
                                System.out.println("2. Mudar senha da conta de um usuário");
                                System.out.println("3. Deletar conta de um usuário");
                                System.out.println("4. Sair");

                                opcao = scanner.nextInt();
                                scanner.nextLine(); // Limpar o buffer de entrada

                                switch (opcao) {
                                    case 1:
                                    	try {
                                    	    database.conectarBanco();
                                    	    ResultSet resultadoConsulta1 = database.executarQuerySql("SELECT * FROM conta");
                                    	    while (resultadoConsulta1.next()) {
                                    	        System.out.println("Numero: " + resultadoConsulta1.getString("numero") +
                                    	            " | Titular: " + resultadoConsulta1.getString("titular") +
                                    	            " | CPF: " + resultadoConsulta1.getString("cpf") +
                                    	            " | SALDO: " + resultadoConsulta1.getString("saldo") +
                                    	            " | CONTA: " + resultadoConsulta1.getString("tipoConta"));
                                    	    }
                                    	    database.desconectarBanco();
                                    	} catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        break;
                                    case 2:
                                    
                                    
                                    	try {
                                    	    System.out.println("Informe o CPF do usuário: ");
                                    	    String cpfUsuario = scanner.nextLine();
                                    	    
                                    	    database.conectarBanco();
                                    	    ResultSet resultadoConsultasql = database.executarQuerySql("SELECT * FROM conta WHERE cpf = '" + cpfUsuario + "'");
                                    	    if (resultadoConsultasql.next()) {
                                    	        System.out.println("CPF encontrado!");
                                    	        // Extrair os dados da consulta
                                    	        String numeroConta = resultadoConsultasql.getString("numero");
                                    	        String titularBANCO = resultadoConsultasql.getString("titular");
                                    	        String cpfBANCO = resultadoConsultasql.getString("cpf");
                                    	        String senhaAtual = resultadoConsultasql.getString("senha");
                                    	       
                                    	        System.out.println("Número da conta: " + numeroConta +
                                    	                "\nTitular: " + titularBANCO +
                                    	                "\nCPF: " + cpfBANCO +
                                    	                "\nSenha atual: " + senhaAtual);
                                    	        
                                    	        System.out.println("Informe a nova senha: ");
                                    	        String novaSenha = scanner.nextLine();
                                    	   
                                    	        String updateQuery = "UPDATE conta SET senha = '" + novaSenha + "' WHERE cpf = '" + cpfBANCO + "'";
                                    	        database.executarUpdateSql(updateQuery);
                                    	        System.out.println("Senha atualizada com sucesso!");
                                    	    } else {
                                    	        System.out.println("CPF não encontrado!");
                                    	    }
                                    	    database.desconectarBanco();
                                    	} catch (Exception e) {
                                    	    System.out.println("Ocorreu um erro: " + e.getMessage());
                                    	} 
                                        break;
                                    case 3:
                                        System.out.println("Digite o CPF do usuário a ser excluído: ");
                                        String cpfExcluir = scanner.nextLine();

                                        try {
                                            database.conectarBanco();

                                            boolean statusExclusaoCorrente = database.executarUpdateSql("DELETE FROM conta_corrente WHERE numero IN (SELECT numero FROM conta WHERE cpf = '" + cpfExcluir + "')");

                                            boolean statusExclusaoPoupanca = database.executarUpdateSql("DELETE FROM conta_poupanca WHERE numero IN (SELECT numero FROM conta WHERE cpf = '" + cpfExcluir + "')");

                                            boolean statusExclusaoConta = database.executarUpdateSql("DELETE FROM conta WHERE cpf = '" + cpfExcluir + "'");

                                            if (statusExclusaoConta) {
                                                System.out.println("Usuário excluído com sucesso.");
                                            } else {
                                                System.out.println("Não foi possível excluir o usuário.");
                                            }

                                            database.desconectarBanco();
                                        } catch (Exception e) {
                                            mensagemStatus("Erro ao excluir o usuário: " + e.getMessage());
                                        }
                                        break;
                                    case 4:
                                        System.out.println("Voltando ao Menu Inicial");
                                        break;
                                    default:
                                        System.out.println("Opção inválida. Digite um número válido.");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Informações do administrador incorretas. Acesso negado.");
                        }

                        database.desconectarBanco();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    sair = true;
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Selecione uma Opção");
                    break;
                }
            
        }
    }





 ///Correções    
private static void exibirMenuAdministrador() {
		// TODO Auto-generated method stub
		
	}

private static void realizaDeposito(float valorDeposito, double taxa) {
		// TODO Auto-generated method stub
		
	}

public static  void mensagemStatus(String string) {
    // TODO Auto-generated method stub

}

}