import org.glassfish.grizzly.http.server.HttpServer;
import student.server.AdventureResource;
import student.server.AdventureServer;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    // DungeonGameUI dge = new DungeonGameUI("src/main/resources/dungeongameroomformat.json");
    // dge.runGame();
    HttpServer server = AdventureServer.createServer(AdventureResource.class);
    server.start();
  }
}
