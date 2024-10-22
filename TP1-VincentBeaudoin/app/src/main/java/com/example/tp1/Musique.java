package com.example.tp1;

public class Musique {

    private String id;
    private String title;
    private String album;
    private String artist;
    private String genre;
    private String source;
    private String image;
    private String trackNumber;
    private String totalTrackCount;
    private int duration;
    private String site;

    public Musique(Builder builder){
        this.genre = builder.genre;
        this.album = builder.nomAlbum;
        this.artist = builder.nomArtiste;
        this.title = builder.nomChanson;
        this.duration = Integer.parseInt(builder.temps);
        this.image = builder.image;
        this.source = builder.chanson;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getSource() {
        return source;
    }

    public String getImage() {
        return image;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public String getTotalTrackCount() {
        return totalTrackCount;
    }

    public int getDuration() {
        return duration;
    }

    public String getSite() {
        return site;
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
