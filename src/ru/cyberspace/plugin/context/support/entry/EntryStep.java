package ru.cyberspace.plugin.context.support.entry;

/**
 * Represents some step where can be pasted an entry by keyword.
 * Support only one entry.
 */
public class EntryStep {
    private final String body;
    private final String keyword;
    private final int startIndex;
    private final int endIndex;
    private final String start;
    private final String end;

    public EntryStep(String body, String keyword) {
        this.body = body;
        this.keyword = keyword;
        this.startIndex = this.body.indexOf(keyword);
        if (this.startIndex == -1) {
            throw new RuntimeException("Keyword not found");
        }
        this.endIndex = this.startIndex + this.keyword.length();
        this.start = this.body.substring(0, startIndex);
        this.end = this.body.substring(this.endIndex);
    }

    public boolean match(String step) {
        return step.startsWith(this.start) && step.endsWith(this.end);
    }

    public String extractEntry(String step) {
        return step.substring(this.startIndex, step.length() - this.end.length());
    }

    public String paste(String entry) {
        return start + entry + end;
    }

    public String getBody() {
        return body;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
