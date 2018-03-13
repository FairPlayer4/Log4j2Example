package textpane;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JTextPane appender for log4j2.
 * This class shows how appenders work in log4j2 and shows a useful implementation of an appender for JTextPane.
 * Besides just writing to the JTextPane it also applies Color and different Font Sizes to the log messages depending on the log level.
 * This class can be used to create visual console with Swing.
 * Can be added to the log4j2 configuration and it will append log messages to all JTextPanes in the static list.
 * I simplified the code as much as possible so that it should be understandable by someone who has never worked with log4j2 or JTextPane.
 */
@Plugin(
        name = "TextPaneAppender",
        category = "Core",
        elementType = "appender",
        printObject = true)
public final class TextPaneAppender extends AbstractAppender
{

    private static List<JTextPane> textPaneList = new ArrayList<>();

    /**
     * Set TextArea to append
     *
     * @param textPane TextArea to append
     */
    public static void addTextPane(JTextPane textPane)
    {
        TextPaneAppender.textPaneList.add(textPane);
    }

    private TextPaneAppender(String name, Filter filter,
                             Layout<? extends Serializable> layout,
                             final boolean ignoreExceptions)
    {
        super(name, filter, layout, ignoreExceptions);
    }

    /**
     * Factory method. Log4j will parse the configuration and call this factory
     * method to construct the appender with
     * the configured attributes.
     *
     * @param name   Name of appender
     * @param layout Log layout of appender
     * @param filter Filter for appender
     * @return The TextAreaAppender
     */
    @PluginFactory
    public static TextPaneAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter)
    {
        if (name == null) {
            LOGGER.error("No name provided for TextPaneAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TextPaneAppender(name, filter, layout, true);
    }

    /**
     * This method is where the appender does the work.
     *
     * @param event Log event with log data
     */
    @Override
    public void append(LogEvent event)
    {
        String message = new String(getLayout().toByteArray(event));

        // append log text to TextArea
        try {
            if (event.getLevel().equals(Level.TRACE)) {
                appendOnTextPane(message, Color.GREEN.darker().darker(), 10);
            }
            else if (event.getLevel().equals(Level.DEBUG)) {
                appendOnTextPane(message, Color.CYAN.darker().darker(), 12);
            }
            else if (event.getLevel().equals(Level.INFO)) {
                appendOnTextPane(message, Color.BLACK, 14);
            }
            else if (event.getLevel().equals(Level.WARN)) {
                appendOnTextPane(message, Color.ORANGE.darker().darker(), 16);
            }
            else if (event.getLevel().equals(Level.ERROR)) {
                appendOnTextPane(message, Color.RED.darker().darker(), 18);
            }
            else if (event.getLevel().equals(Level.FATAL)) {
                appendOnTextPane(message, Color.RED, 20);
            }
        } catch (Exception e) {
            // Do not log exceptions that were caused by logging.
            // e.printStackTrace();
        }
    }

    private void appendOnTextPane(String msg, Color color, int fontSize)
    {
        SwingUtilities.invokeLater(() -> {
            for (JTextPane tp : textPaneList) {
                StyledDocument doc = tp.getStyledDocument();

                Style style = tp.addStyle("ConsoleStyle", null);
                StyleConstants.setForeground(style, color);
                StyleConstants.setFontSize(style, fontSize);

                try {
                    doc.insertString(doc.getLength(), msg, style);
                } catch (BadLocationException e) {
                    //e.printStackTrace();
                }
            }
        });
    }
}
