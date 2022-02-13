package detector.annotations;

public class PredicateAxiomAnnotation implements Annotation {
    protected final String axiomNumber;

    public PredicateAxiomAnnotation(String axiomNumber) {
        this.axiomNumber = axiomNumber;
    }

    @Override
    public String annotate(int row) {
        return String.format("[%d. Ax. sch. %s]", row, axiomNumber);
    }
}
