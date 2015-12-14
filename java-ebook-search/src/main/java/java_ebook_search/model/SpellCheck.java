package java_ebook_search.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.UppercaseSentenceStartRule;

/**
 * The SpellCheck class is responsible for handling the dynamic spell check and
 * suggestions for the system.
 *
 * @author Aidan O'Grady
 * @since 0.2
 */
public class SpellCheck {

	/**
	 * LanguageTool that handles the spell checking.
	 */
	private static JLanguageTool tool;

	/**
	 * Constructor.
	 */
	public SpellCheck() {
		tool = new JLanguageTool(new BritishEnglish());
	}

	/**
	 * Given a search term, returns a list of potential suggestions for the
	 * user.
	 * 
	 * @param term
	 *            The search query entered.
	 * @return Suggested terms.
	 * @throws IOException
	 *
	 *             TODO: Implement a better system.
	 */
	public static List<String> getSuggestions(String term) throws IOException {
		List<String> list = new ArrayList<String>();

		List<RuleMatch> matches = tool.check(term);

		for (RuleMatch match : matches) {

			if (!(match.getRule() instanceof UppercaseSentenceStartRule)) {
				for (String suggested : match.getSuggestedReplacements()) {
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
