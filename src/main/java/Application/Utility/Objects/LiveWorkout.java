package Application.Utility.Objects;

public class LiveWorkout extends Workout {
	private Boolean live;
	private String startTime;
	private Integer trainerId;
	
	public LiveWorkout() {
		super();
		live = false;
	}
	
	public LiveWorkout(String name, Integer trainerId, Boolean live, String description, String startTime, int duration) {
		this.name = name;
		this.trainerId = trainerId;
		this.live = live;
		this.description = description;
		this.startTime = startTime;
		this.duration = duration;
	}


    public Boolean getLive() {
		return live;
	}

	public void setLive(Boolean join) {
		this.live = join;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
