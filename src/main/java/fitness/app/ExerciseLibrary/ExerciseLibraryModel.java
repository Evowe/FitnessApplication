package fitness.app.ExerciseLibrary;

import fitness.app.Objects.Databases.ExerciseDB;
import fitness.app.Objects.Exercise;

import java.sql.SQLException;
import java.util.List;

public class ExerciseLibraryModel {

    private static ExerciseDB exerciseDB = new ExerciseDB("exercise");


    public static Object [][] getExerciseData(){
        List<Exercise> exercises = null;
        try{
            exercises = exerciseDB.getAllExercises();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[exercises.size()][5];
        for(int i = 0; i < exercises.size(); i++){
            data[i] = exercises.get(i).getString();
        }
        return data;
    }

    public static Object [] getExerciseColums(){
        Object[] colums = new Object[5];

        colums[0] = "Name";
        colums[1] = "Description";
        colums[2] = "Sets";
        colums[3] = "Reps";
        colums[4] = "Weight";

        return colums;
    }

}




