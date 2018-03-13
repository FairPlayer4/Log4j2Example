package utils;

import org.apache.logging.log4j.message.FormattedMessage;

public class FormatUtil
{
    public static String format(String text, Object... params)
    {
        return new FormattedMessage(text, params).getFormattedMessage();
    }
}
