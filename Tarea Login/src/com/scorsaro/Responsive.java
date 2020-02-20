package com.scorsaro;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Responsive {

    float screenHeight;
    float screenWidth;
    int unitHeight;
    int unitWidth;
    Font responsiveFont;
    Font arcadeMedium;
    Font arcadeLarge;


    public Responsive() throws IOException, FontFormatException {
        responsiveScreenUnit();
    }

    	public void responsiveScreenUnit() throws IOException, FontFormatException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        unitHeight = (int) screenHeight / 100;
        unitWidth = (int) screenWidth / 100;
        setArcadeFont();
        responsiveFont = new Font(Font.SANS_SERIF, Font.PLAIN, (int)screenHeight/55);
    }

    private void setArcadeFont() throws FontFormatException, IOException {
        arcadeMedium = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade.TTF")).deriveFont(screenHeight/35);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade.TTF")));
        arcadeLarge = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade.TTF")).deriveFont(screenHeight/20);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade.TTF")));
    }


}