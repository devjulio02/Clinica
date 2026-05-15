import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProntuarioRepository { //SRP: Classe isolada para cuidar da persistência de dados;

    private boolean b = false; //Flag para ignorar o cabeçalho do csv na leitura;
    private List<String> l = new ArrayList<>(); //Lista temporária para montar as linhas do arquivo;

    public Prontuario carregueProntuario(String arquivoCsv) throws IOException {
            Prontuario prontuario = new Prontuario(null);
            Path path = Paths.get(arquivoCsv);
            try (Stream<String> linhas = Files.lines(path)) { //Garante que o arquivo será fechado após o uso;

            linhas.forEach((str) -> {
                if (b == false) {
                    b = true;
                } else {
                    System.out.println(str);
                    String[] dados = str.split(",");
                    String nomePaciente = dados[0].trim();
                    //conversão de Strings do csv para objetos Enum do sistema;
                    TipoLeito tipoLeito = dados[1] != null && !dados[1].trim().isEmpty() 
                        ? TipoLeito.valueOf(dados[1].trim()) : null;

                    int qtdeDiasInternacao = dados[2] != null && !dados[2].trim().isEmpty() 
                        ? Integer.parseInt(dados[2].trim()) : -1;

                    TipoProcedimento tipoProcedimento = dados[3] != null && !dados[3].trim().isEmpty() 
                        ? TipoProcedimento.valueOf(dados[3].trim()) : null;

                    int qtdeProcedimentos = dados.length == 5 && dados[4] != null && !dados[4].trim().isEmpty() 
                        ? Integer.parseInt(dados[4].trim()) : -1;

                    prontuario.setNomePaciente(nomePaciente);
                    //Reconstrução dos objetos de domínio a partir dos dados brutos;
                    if (tipoLeito != null && qtdeDiasInternacao > 0) {
                        prontuario.setInternacao(new Internacao(tipoLeito, qtdeDiasInternacao));
                    }

                    if (tipoProcedimento != null && qtdeProcedimentos > 0) {
                        while (qtdeProcedimentos > 0) {
                            prontuario.addProcedimento(new Procedimento(tipoProcedimento));
                            qtdeProcedimentos--;
                        }
                    }
                }
            });
        }
        return prontuario;
    }

    public String salveProntuario(Prontuario prontuario) throws IOException {
        l.clear();
        l.add("nome_paciente,tipo_leito,qtde_dias_internacao,tipo_procedimento,qtde_procedimentos");

        String l1 = prontuario.getNomePaciente() + ",";
        //O repositório extrai os dados do objeto Prontuario para o csv;
        if (prontuario.getInternacao() != null) {
            l1 += prontuario.getInternacao().getTipoLeito() + "," + prontuario.getInternacao().getQtdeDias() + ",,";
            l.add(l1);
        }

        if (prontuario.getProcedimentos().size() > 0) {
            //Agrupamento para salvar no csv de forma organizada por tipo;
            Map<TipoProcedimento, Long> procedimentosAgrupados = prontuario.getProcedimentos().stream().collect(
                    Collectors.groupingBy(Procedimento::getTipoProcedimento, Collectors.counting()));

            List<TipoProcedimento> procedimentosOrdenados = new ArrayList<>(procedimentosAgrupados.keySet());
            Collections.sort(procedimentosOrdenados);

            for (TipoProcedimento chave : procedimentosOrdenados) {
                String l2 = prontuario.getNomePaciente() + ",,," + chave + "," + procedimentosAgrupados.get(chave);
                l.add(l2);
            }
        }

        if (l.size() == 1) {
            l1 += ",,,";
            l.add(l1);
        }
        //geração de nome de arquivo único usando timestamp;
        Path path = Paths.get(prontuario.getNomePaciente().replaceAll(" ", "_")
                .concat(String.valueOf(System.currentTimeMillis())).concat(".csv"));

        Files.write(path, l);

        return path.toString();
    }
}