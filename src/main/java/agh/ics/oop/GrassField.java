package agh.ics.oop;

public class GrassField extends AbstractWorldMap{
    final private int grassAmount;
    final private   MapBoundary mapBoundary = new MapBoundary();

    private void createGrass() {
        boolean created = false;
        while (!created) {
            Vector2d randomVector = new Vector2d((int) (Math.random() * (Math.sqrt(grassAmount * 10) + 1)),
                    (int) (Math.random() * (Math.sqrt(grassAmount * 10) + 1)));

            if (this.objectAt(randomVector) == null) {
                this.mapElements.put(randomVector, new Grass(randomVector));
                this.mapBoundary.addPosition(randomVector);
                created = true;
            }
        }
    }
    public GrassField(int grassAmount) {
        this.grassAmount = grassAmount;
        for(int i=0; i < grassAmount; ++i)
            createGrass();
    }
    @Override
    public Vector2d getLowerLeft() {
        return this.mapBoundary.getLowerLeft();
    }
    @Override
    public Vector2d getUpperRight() {
        return this.mapBoundary.getUpperRight();
    }

    @Override
    protected boolean isInScope(Vector2d position) {
        return position.follows(new Vector2d(0, 0));
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        boolean createAfter = this.mapElements.get(animal.getPosition()) instanceof Grass;

        if (super.place(animal)) {
            this.mapBoundary.addPosition(animal.getPosition());
            if (createAfter)    createGrass();
            return true;
        }
        return false;
    }



    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        boolean createAfter = this.mapElements.get(newPosition) instanceof Grass;
        this.mapBoundary.positionChanged(oldPosition, newPosition);
        super.positionChanged(oldPosition, newPosition);

        if(createAfter)
            createGrass();
    }
}
