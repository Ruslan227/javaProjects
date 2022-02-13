package detector.annotations;

public class FormalAxiomAnnotation implements Annotation {
    private final String axiomNumber;

    public FormalAxiomAnnotation(String axiomNumber) {
        this.axiomNumber = axiomNumber;
    }

    @Override
    public String annotate(int row) {
        return String.format("[%d. Ax. %s]", row, axiomNumber);
    }
}
