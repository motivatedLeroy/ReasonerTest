package RDFCases;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class RDFexample2{


    // some definitions
    static String personURI    = "http://somewhere/FelixH.";
    static String given = "Felix ";
    static String familyName = "Hambrecht";
    static String fullName     = given+familyName;

    // create an empty Model
    Model model = ModelFactory.createDefaultModel();

    // create the resource
    Resource felixHambrecht = model.createResource(personURI);
    Resource emptyNode = model.createResource();

    public void test(){
        felixHambrecht.addProperty(VCARD.FN, fullName);
        emptyNode.addProperty(VCARD.Given, given);
        emptyNode.addProperty(VCARD.Family, familyName);
        felixHambrecht.addProperty(VCARD.N, emptyNode);

    }
}
