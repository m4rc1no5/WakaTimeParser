package pl.marceen.wakatimeparser.parser.control;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Marcin Zaremba
 */
@ExtendWith(MockitoExtension.class)
class UrlBuilderTest {

    @Mock
    private TimeMachine timeMachine;

    @InjectMocks
    private UrlBuilder sut;

    @Test
    void urlForReferer() {
        // given

        // when
        String url = sut.urlForReferer();

        // then
        assertEquals("https://wakatime.com/", url);
    }

    @Test
    void urlForLogin() {
        // when
        String url = sut.urlForLogin();

        // then
        assertEquals("https://wakatime.com/login", url);
    }

    @Test
    void urlForSummary() {
        // given
        when(timeMachine.now()).thenReturn(LocalDate.parse("2019-01-30"));

        // when
        String url = sut.urlForSummary();

        // then
        assertEquals("https://wakatime.com/api/v1/users/current/summaries?start=2019-01-16&end=2019-01-30", url);
    }
}