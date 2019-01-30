package pl.marceen.wakatimeparser.parser.control;

import okhttp3.HttpUrl;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Marcin Zaremba
 */
public class UrlBuilder {

    private static final String SCHEME = "https";
    private static final String HOST = "wakatime.com";
    private static final int LAST_DAYS = 14;

    @Inject
    private TimeMachine timeMachine;

    public String urlForReferer() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .build()
                .toString();
    }

    public String urlForLogin() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegment("login")
                .build()
                .toString();
    }

    public String urlForSummary() {
        LocalDate now = timeMachine.now();

        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("users")
                .addPathSegment("current")
                .addPathSegment("summaries")
                .addQueryParameter("start", now.minusDays(LAST_DAYS).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addQueryParameter("end", now.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build()
                .toString();
    }
}
