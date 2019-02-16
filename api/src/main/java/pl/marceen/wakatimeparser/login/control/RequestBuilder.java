package pl.marceen.wakatimeparser.login.control;

import okhttp3.MultipartBody;
import okhttp3.Request;
import pl.marceen.wakatimeparser.parser.control.UrlBuilder;

import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
public class RequestBuilder {

    @Inject
    private UrlBuilder urlBuilder;

    Request buildCsrfRequest() {
        return new Request.Builder()
                .url(urlBuilder.urlForLogin())
                .get()
                .build();
    }

    Request buildLoginRequest(String csrfToken, String email, String password) {
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("csrftoken", csrfToken)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();

        return new Request.Builder()
                .url(urlBuilder.urlForLogin())
                .post(multipartBody)
                .header("referer", urlBuilder.urlForReferer())
                .build();
    }

    public void setUrlBuilder(UrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }
}
