package com.example.tp1;

public class Musique {
    private String nomArtiste;
    private String nomChanson;
    private String nomAlbum;
    private String genre;
    private int temps;
    private String image;
    private String chanson;

    public Musique(Builder builder){
        this.genre = builder.genre;
        this.nomAlbum = builder.nomAlbum;
        this.nomArtiste = builder.nomArtiste;
        this.nomChanson = builder.nomChanson;
        this.temps = Integer.parseInt(builder.temps);
        this.image = builder.image;
        this.chanson = builder.chanson;
    }

    public String getNomArtiste() {
        return nomArtiste;
    }

    public String getChanson() {
        return chanson;
    }

    public String getNomChanson() {
        return nomChanson;
    }

    public String getNomAlbum() {
        return nomAlbum;
    }

    public String getGenre() {
        return genre;
    }

    public int getTemps() {
        return temps;
    }

    public String getImage() {
        return image;
    }


    public static class Builder {
        private String nomArtiste;
        private String nomChanson;
        private String nomAlbum;
        private String genre;
        private String temps;
        private int date;
        private String image;
        private String chanson;

        public Builder setChanson(String chanson){
            this.chanson = chanson;
            return this;
        }
        public Builder setImage(String image){
            this.image = image;
            return this;
        }
        public Builder setNomArtiste(String nomArtiste){
            this.nomArtiste = nomArtiste;
            return this;
        }

        public Builder setNomChanson(String nomChanson){
            this.nomChanson = nomChanson;
            return this;
        }

        public Builder setNomAlbum(String nomAlbum){
            this.nomAlbum = nomAlbum;
            return this;
        }

        public Builder setGenre(String genre){
            this.genre = genre;
            return this;
        }
        public Builder setTemps(int temps){
            this.temps = String.valueOf(temps);
            return this;
        }

        public Musique build(){
            return new Musique(this);
        }
    }
}
