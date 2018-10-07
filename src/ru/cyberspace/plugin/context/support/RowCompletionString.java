package ru.cyberspace.plugin.context.support;

import org.jetbrains.annotations.NotNull;

import static ru.cyberspace.plugin.context.PluginUtil.RULEZZZZ;

/**
 * Remove the special mark from a row completion text.
 */
public class RowCompletionString {
    private final String text; // Text without the special mark
    private final int cursor; // Start of the cursor on the text
    private final boolean isEnd; // True if cursor is on the end of the text
    private final String start;
    private final String end;

    public RowCompletionString(@NotNull String text) {
        this.cursor = text.indexOf(RULEZZZZ);
        this.isEnd = text.endsWith(RULEZZZZ);
        this.text = text.replace(RULEZZZZ + (this.isEnd ? "" : " "), "");
        this.start = this.text.substring(0, cursor);
        this.end = this.text.substring(cursor);
    }

    /**
     * Removes a duplication on the end of the text.
     */
    public String adjustCompletion(String completion) {
        if (!this.isEnd && completion.endsWith(this.end)) {
            return completion.substring(0, completion.lastIndexOf(this.end));
        }
        return completion;
    }

    public String getText() {
        return text;
    }

    public int getCursor() {
        return cursor;
    }

    public boolean isEnd() {
        return isEnd;
    }
}
