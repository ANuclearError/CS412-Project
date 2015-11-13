package com.group15.java_ebook_search.model;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.UppercaseSentenceStartRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aidan O'Grady
 * @since 0.1
 */
public class SpellCheck {

    private JLanguageTool tool;

    public SpellCheck() {
        tool = new JLanguageTool(new BritishEnglish());

    }

    public List<String> getSuggestions(String term) throws IOException {
        List<String> list = new ArrayList<String>();

        List<RuleMatch> matches = tool.check(term);

        for (RuleMatch match : matches) {

            if(!(match.getRule() instanceof UppercaseSentenceStartRule)) {
                for(String suggested : match.getSuggestedReplacements()) {
                    String string = "";
                    if (match.getFromPos() > 0)
                        string += term.substring(0, match.getFromPos());
                    string += suggested;
                    string += term.substring(match.getToPos(), term.length());
                    list.add(string);
                }
            }
        }
        return list;
    }
}
