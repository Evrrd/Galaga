package game;

public final class Parameter {
    public static final int canvasHeigth = 750;
    public static final int canvasWidth = 700;

    public static final double proportion_player = 0.004;
    public static final int player_square_len = 15;

    public static final double line_top_zone = 0.9; // % of canvasHeigth * 10 / 100 = 75
    public static final double line_bottom_zone = 0.1; // % of canvasHeigth * 10 / 100 = 75

    public static final int shoot_heigth = 5;
    public static final int shoot_width = 3;
    public static int shoot_number;
    public static boolean shoot_one_go;
    public static boolean shoot_two_go;
    public static boolean shoot_three_go;
    public static final double shoot_speed = 0.01;

}
