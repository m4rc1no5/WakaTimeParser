package pl.marceen.wakatimeparser.service.entity;

/**
 * @author Marcin Zaremba
 */
public enum Page {
    LOGIN("login");

    public static final String HOST = "https://wakatime.com/";

    private String endpoint;

    Page(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUrl() {
        return HOST + endpoint;
    }
}
