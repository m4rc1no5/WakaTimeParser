package pl.marceen.wakatimeparser.parser.entity;

import org.slf4j.Logger;

/**
 * @author Marcin Zaremba
 */
public class ParserException extends RuntimeException {
    private ParserException(String message) {
        super(message);
    }

    public static ParserException exception(String message, Logger logger) {
        logger.error(message);

        throw new ParserException(message);
    }
}
