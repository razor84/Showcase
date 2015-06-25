package com.lovejoy777.showcase;

/**
 * Created by lovejoy777 on 23/06/15.
 */
public class Themes {
    private String title;
    private String description;
    private String author;
    private String link;
    private String screenshot_1;
    private String screenshot_2;
    private String screenshot_3;
    private String google_plus;
    private String icon;

        public Themes() {
            // TODO Auto-generated constructor stub
        }

        public Themes(String title, String description, String author, String link, String screenshot_1, String screenshot_2, String screenshot_3, String google_plus, String icon) {
            super();
            this.title = title;
            this.description = description;
            this.author = author;
            this.link = link;
            this.screenshot_1 = screenshot_1;
            this.screenshot_2 = screenshot_2;
            this.screenshot_3 = screenshot_3;
            this.google_plus = google_plus;
            this.icon = icon;
        }


        public String gettitle() {
            return title;
        }

        public void settitle(String title) {
            this.title = title;
        }

        public String getdescription() {
            return description;
        }

        public void setdescription(String description) {
            this.description = description;
        }

        public String getauthor() {
            return author;
        }

        public void setauthor(String author) {
            this.author = author;
        }

        public String getlink() {
            return link;
        }

        public void setlink(String link) {
            this.link = link;
        }


    public void getgoogle_plus(String google_plus) {
        this.google_plus = google_plus;
    }

    public void setgoogle_plus(String google_plus) {
        this.google_plus = google_plus;
    }

    public String geticon() {
        return icon;
    }

    public void seticon(String icon) {
        this.icon = icon;
    }

        public String getscreenshot_1() {
            return screenshot_1;
        }

        public void setscreenshot_1(String screenshot_1) {
            this.screenshot_1 = screenshot_1;
        }

    public String getscreenshot_2() {
        return screenshot_2;
    }

    public void setscreenshot_2(String screenshot_2) {
        this.screenshot_2 = screenshot_2;
    }

    public String getscreenshot_3() {
        return screenshot_3;
    }

    public void setscreenshot_3(String screenshot_3) {
        this.screenshot_3 = screenshot_3;
    }

}
