package fitness.app.Objects;

public class Exercise {
    private String name;
    private String description;
    private int sets;
    private int reps;
    private double weight;

    public Exercise(String name, String description, int sets, int reps, double weight) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getSets() {
        return sets;
    }
    public void setSets(int sets) {
        this.sets = sets;
    }
    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public Object [] getString(){
        Object [] str = new Object [5];
        str[0] = this.name;
        str[1] = this.description;
        str[2] = String.valueOf(this.sets);
        str[3] = String.valueOf(this.reps);
        str[4] = String.valueOf(this.weight);

        return str;
        //Name,Description,Type,Sets,Reps,Weight
    }
}


