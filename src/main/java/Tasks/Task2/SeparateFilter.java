package Tasks.Task2;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class SeparateFilter extends TokenFilter {

    private PositionIncrementAttribute posAtt = addAttribute(PositionIncrementAttribute.class);
    private CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private Deque<String> separateList = new LinkedList<>();
    private State state = new State();

    SeparateFilter(TokenStream input) {
        super(input);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (separateList.size() > 0) {
            restoreState(state);
            termAtt.setEmpty().append(separateList.pop());
            posAtt.setPositionIncrement(0);
            return true;
        }

        if (!input.incrementToken()) {
            return false;
        }

        for (int i = 1; i < termAtt.toString().length(); i++) {
            separateList.add(termAtt.toString().substring(0, i));
        }

        state = captureState();

        return true;
    }
}
