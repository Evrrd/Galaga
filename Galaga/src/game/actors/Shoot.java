package game.actors;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import engine.StdDraw;

public class Shoot {


    private double x;
    private double y;
    private double length;


    public Shoot(double xx, double yy, double len){
        x = xx;
        y = yy;
        length = len;
    }

    public void draw(){
        Path cheminT1 = Paths.get("Galaga/ressources/sprites/shoot.spr");
        try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
            String line = null;
            double i = 0.0;
            double j = 0.0;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case 'R':
                            StdDraw.setPenColor(StdDraw.RED);
                            break;
                        case 'B':
                            StdDraw.setPenColor(StdDraw.BLUE);
                            break;
                        case 'G':
                            StdDraw.setPenColor(StdDraw.GREEN);
                            break;
                        case 'Y':
                            StdDraw.setPenColor(StdDraw.YELLOW);
                            break;
                        case 'W':
                            StdDraw.setPenColor(StdDraw.WHITE);
                            break;
                        case 'N':
                            StdDraw.setPenColor(StdDraw.BLACK);
                            break;
                    }
                    StdDraw.filledSquare(x + i, y - j, 0.006);
                    i += 0.006;
                }
                i = 0.0;
                j += 0.006;
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }
    }
    

}
