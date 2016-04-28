package com.example.josh.iat;

import android.widget.ImageView;

public class Image {

    protected ImageView image;
    protected String name;
    protected boolean black_man_gun;
    protected boolean white_man_gun;

    Image(ImageView image, String name, boolean black_man_gun, boolean white_man_gun) {
        this.image = image;
        this.name = name;
        this.black_man_gun = black_man_gun;
        this.white_man_gun = white_man_gun;
    }
}
