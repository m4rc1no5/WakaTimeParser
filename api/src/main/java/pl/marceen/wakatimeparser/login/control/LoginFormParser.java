package pl.marceen.wakatimeparser.login.control;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.parser.entity.ParserException;

import java.util.Optional;

/**
 * @author Marcin Zaremba
 */
public class LoginFormParser {
    private static final Logger logger = LoggerFactory.getLogger(LoginFormParser.class);

    public String findCsrfToken(String html) {
        logger.info("Finding CSRF token");

        Document document = Jsoup.parse(html);

        String csrfToken = Optional.of(document.select("input[name=csrftoken]"))
                .orElseThrow(() -> ParserException.exception("CSRF token not found", logger))
                .val();

        logger.info("CSRF token: {}", csrfToken);

        return csrfToken;
    }
}
