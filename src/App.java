import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;


public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);
        
        // pegar só os dados que interessam (titutlo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // exibir e manipular os dados
        for (Map<String,String> filme: listaDeFilmes){

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            String nomeArquivo = titulo + ".png";

            InputStream inputStream = new URL(urlImagem).openStream();
            
            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);
            
            System.out.println(filme.get("title"));
            System.out.println();
        }
    }
}
