package pl.marceen.wakatimeparser.service.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Marcin Zaremba
 */
class PageTest {

    @Test
    void getUrl() {
        Assertions.assertEquals("https://wakatime.com/login", Page.LOGIN.getUrl());
    }
}