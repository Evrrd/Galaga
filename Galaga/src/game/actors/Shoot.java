package game.actors;

import game.Parameter;
import engine.StdDraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;




public class Shoot {


    private double x;
    

    private double y;
    public boolean drew;


    public Shoot(double xx, double yy){
        x = xx;
        y = yy;
    }
    public double getY() {
        return y;
    }

    public void draw(){
        drew = true;
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
                    StdDraw.filledSquare(x + i, y - j, Parameter.proportion_player);
                    i += Parameter.proportion_player;
                }
                i = 0.0;
                j += Parameter.proportion_player;
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }
    }

    public void undraw(){
        drew = false;
        StdDraw.setPenColor(StdDraw.BLACK);
        for (double i = 0.0; i < Parameter.shoot_width * Parameter.proportion_player; i = i + Parameter.proportion_player){
            for (double j = 0.0 ; j < Parameter.shoot_heigth * Parameter.proportion_player; j = j + Parameter.proportion_player){
                StdDraw.filledSquare(x + i, y - j, Parameter.proportion_player);
                j += Parameter.proportion_player;
            }
        }
    }
    
    public void moving(double k){
        y += k;
    }

}
