package Tasks.Task2;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

public class SeparateFilterFactory extends TokenFilterFactory {

    public SeparateFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenStream create(TokenStream input) {
        return new SeparateFilter(input);
    }
}
