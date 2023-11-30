package dev.promoclock.gif;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

public class GifGenerator {

    public byte[] generateGifFrames(List<String> frames, int frameDuration) {
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        gifEncoder.start(baos);
        gifEncoder.setDelay(frameDuration); // Ustawienie czasu trwania klatki (w milisekundach)

        for (String frameText : frames) {
            BufferedImage frameImage = createFrameImage(frameText); // Tworzenie obrazka dla danej klatki
            gifEncoder.addFrame(frameImage); // Dodawanie klatki do pliku GIF
        }

        gifEncoder.finish(); // Zakończenie procesu kodowania pliku GIF
        return baos.toByteArray();
    }

    private BufferedImage createFrameImage(String text) {
        // Tutaj tworzysz obrazek o podanych wymiarach
        int width = 400;
        int height = 100;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Ustawienia dla tekstu (np. kolor, czcionka)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 24));

        // Rysowanie tekstu na obrazku
        g.drawString(text, width / 2 - g.getFontMetrics().stringWidth(text) / 2, height / 2);

        g.dispose();
        return image;
    }
}
