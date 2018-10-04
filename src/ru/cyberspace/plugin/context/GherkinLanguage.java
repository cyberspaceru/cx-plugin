package ru.cyberspace.plugin.context;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class GherkinLanguage extends Language {
    public static GherkinLanguage INSTANCE = new GherkinLanguage();

    protected GherkinLanguage() {
        super("Gherkin");
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Gherkin";
    }
}
