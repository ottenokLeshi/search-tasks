package Tasks.Task_3;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.AbstractAnalysisFactory;
import org.apache.lucene.analysis.util.MultiTermAwareComponent;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

public class ConcatenateFilterFactory extends TokenFilterFactory implements MultiTermAwareComponent {

    public ConcatenateFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public ConcatenateFilter create(TokenStream input) {
        return new ConcatenateFilter(input, getOriginalArgs());
    }

    @Override
    public AbstractAnalysisFactory getMultiTermComponent() {
        return this;
    }
}
