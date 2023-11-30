package dev.promoclock;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dev.promoclock.gif.GifGenerator;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@ResponseBody
public class GifController {

    @GetMapping(value = "/generate-gif.gif", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] generateGif() throws IOException {
        GifGenerator gifGenerator = new GifGenerator();

        // frames - lista tekstów do wyświetlenia na poszczególnych klatkach pliku GIF
        // Tu robie odliczanie 0 - 10, ty chcesz tu wrzucić odliczanie czasu, wiec formatujesz sobie pozostałe godziny / minut / sekundy
        List<String> frames = IntStream.range(0,10).boxed().map(i->i.toString()).collect(Collectors.toList());

        // duration - czas trwania każdej klatki (w milisekundach)
        int duration = 1000; // ms = 1s
        byte[] bytes = gifGenerator.generateGifFrames(frames, duration);
        return bytes;
    }


    @GetMapping(value = "/generate-gif/{data}.gif", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] generateGifWithParam(@PathVariable("data") String data) throws IOException {
        GifGenerator gifGenerator = new GifGenerator();

        List<String> frames = tworzKlatkiZSekundami(data);

        // duration - czas trwania każdej klatki (w milisekundach)
        int duration = 1000; // ms = 1s
        byte[] bytes = gifGenerator.generateGifFrames(frames, duration);
        return bytes;
    }

    private List<String> tworzKlatkiZSekundami(String data) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime ldt = LocalDateTime.parse(data, format);
        LocalDateTime now = LocalDateTime.now();

        List<String> frames;

        if(now.toEpochSecond(ZoneOffset.UTC) > ldt.toEpochSecond(ZoneOffset.UTC)) {
            // Czas minal (sprawdzam po unix timestampie czy czas teraz ma wiekszy timestamp niz docelowy)
             frames = List.of("Czas minął!");

        } else {
            // Czas nie minal, tworzymy ramki z odliczaniem

            // obliczamy roznice czasu
            Duration period = Duration.between(now, ldt);

            // tworze napis ile dni, godzin i munt zostało, sekundy zawsze dopisze odliczanie 60 ostatnich
            String odliczData = period.toDays() + " dni " +  String.format("%02d", period.toHoursPart()) + ":" +  String.format("%02d", period.toMinutesPart());

            // ostatnie 60 sekund: 59, 58, ... , 1, 0
             frames = IntStream.range(0, 59)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .map(sekunda -> odliczData + ":" + String.format("%02d", sekunda))
                    .collect(Collectors.toList());
        }
        return frames;
    }

}
