package ru.cyberspace.plugin.context.completions;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import ru.cyberspace.plugin.context.support.RowCompletionString;
import ru.cyberspace.plugin.context.support.entry.EntryStep;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static ru.cyberspace.plugin.context.project.ContextProject.findStep;

public abstract class EntryCompletion extends CompletionProvider<CompletionParameters> {
    protected static List<String> compile(@NotNull String stepText, @NotNull Project project,
                                          @NotNull List<EntryStep> steps, @NotNull Function<Project, List<String>> entriesProvider) {
        List<String> result = new ArrayList<>();
        RowCompletionString row = new RowCompletionString(stepText);
        EntryStep step = findStep(steps, row.getText());
        if (step != null) {
            entriesProvider.apply(project).forEach(x -> {
                String completion = step.paste(x);
                String adjusted = row.adjustCompletion(completion);
                if (adjusted != null) {
                    result.add(adjusted);
                }
            });
        }
        return result;
    }
}
