package Tasks.Task3;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

public class ConcatenateFilterFactory extends TokenFilterFactory {

    public ConcatenateFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public ConcatenateFilter create(TokenStream input) {
        return new ConcatenateFilter(input, getOriginalArgs());
    }
}
