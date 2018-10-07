package ru.cyberspace.plugin.context;

import ru.cyberspace.plugin.context.support.entry.ActionEntryStep;
import ru.cyberspace.plugin.context.support.entry.EntryStep;
import ru.cyberspace.plugin.context.support.entry.PageEntryStep;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    public static List<EntryStep> pageSteps  = new ArrayList<>();
    public static List<EntryStep> actionSteps  = new ArrayList<>();

    static {
        pageSteps.add(new PageEntryStep("пользователь находится на странице \"${page-entry}\""));
        actionSteps.add(new ActionEntryStep("он (${action-entry})"));
    }

    private Settings() {
    }


}
