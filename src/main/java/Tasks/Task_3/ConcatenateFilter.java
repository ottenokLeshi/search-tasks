package Tasks.Task_3;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.util.Map;

public class ConcatenateFilter extends TokenFilter {
    private CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private OffsetAttribute offSetAtt = addAttribute(OffsetAttribute.class);
    private String delimiter = " + ";
    private boolean done = false;

    protected ConcatenateFilter(TokenStream input) {
        super(input);
    }

    protected ConcatenateFilter(TokenStream input, Map<String, String> args) {
        super(input);
        this.delimiter = args.get("delimiter");
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (done) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;

        while (input.incrementToken()) {
            if (!first) {
                stringBuilder.append(delimiter);
            }
            stringBuilder.append(termAtt.toString());

            first = false;
        }

        termAtt.append(stringBuilder.toString());
        termAtt.setLength(stringBuilder.length());
        offSetAtt.setOffset(0, stringBuilder.length());

        done = true;
        return true;
    }

    @Override
    public void end() {
        done = false;
    }
}