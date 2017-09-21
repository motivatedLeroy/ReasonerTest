import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class RDFexample1 {


    // some definitions
    static String personURI    = "http://somewhere/FelixH.";
    static String fullName     = "Felix Hambrecht";

    // create an empty Model
    Model model = ModelFactory.createDefaultModel();

    // create the resource
    Resource felixHambrecht = model.createResource(personURI);

    public void test(){
        felixHambrecht.addProperty(VCARD.FN, fullName);
    }
}
