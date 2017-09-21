import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.VCARD;

public class RDFexample3{


    // some definitions
    static String personURI    = "eg:";
    static String given = "Felix ";
    static String familyName = "Hambrecht";
    static String fullName     = given+familyName;

    // create an empty Model
    Model model = ModelFactory.createDefaultModel();

    // create the resource
    Resource A = model.createResource("eg:r");
    Resource p = model.createResource("eg:p");
    Resource q = model.createResource("eg:q");

    Resource emptyNode = model.createResource();

    public void test(){
        A.addProperty(model.createProperty(personURI, "concatFirst"), p);
        A.addProperty(model.createProperty(personURI, "concatSecond"), q);
        /*emptyNode.addProperty(VCARD.Given, given);
        emptyNode.addProperty(VCARD.Family, familyName);
        A.addProperty(VCARD.N, emptyNode);*/
    }

    public void statementIterator(){
        StmtIterator iterator = model.listStatements();

        while (iterator.hasNext()){
            Statement stmt      = iterator.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");

        }
    }

    public void writeModel(){
        model.write(System.out,"N-TRIPLES");
    }


    public static void main(String [] args){
        RDFexample3 rdFexample3 = new RDFexample3();

        rdFexample3.test();
        rdFexample3.statementIterator();
        rdFexample3.writeModel();
    }

}
