package pl.marceen.wakatimeparser.login.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.marceen.wakatimeparser.worker.FileReader.read;

/**
 * @author Marcin Zaremba
 */
class LoginFormParserTest {

    private LoginFormParser sut;

    @BeforeEach
    void setUp() {
        sut = new LoginFormParser();
    }

    @Test
    void findCsrfToken() throws Exception {
        // given
        String html = read(getClass(), "response/loginForm.html");

        // when
        String csrfToken = sut.findCsrfToken(html);

        // then
        assertEquals("1a18c07a2173758d241d5126dc489222dd615208881546d71a0f8171165cae80", csrfToken);
    }
}