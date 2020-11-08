import java.io.*;
import java.net.URL;
import java.nio.file.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Parser {

private static final String path = "pictures/";

  public static void main(String[] args) throws IOException {

    Document document = Jsoup.connect("https://lenta.ru/").userAgent("Safari/532.5").get();
    Elements pngs = document.select("img[src~=\\.(png|jpg|jpeg|gif|bpm)]");

    pngs.forEach(element -> {
      try(InputStream in = new URL(element.absUrl("src")).openStream()) {
        String fullUrlImage = element.absUrl("src");
        int index = fullUrlImage.lastIndexOf("/");
        String nameImage = fullUrlImage.substring(index);
        Files.copy(in, Paths.get(path + nameImage), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

}
