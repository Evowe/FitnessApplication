package Application.Utility.Objects;

import java.time.LocalDateTime;

public class LiveWorkout extends Workout {
	private Boolean live;
	private String startTime;
	private Integer trainerId;
	private int Users;
	
	public LiveWorkout() {
		super();
		live = false;
		Users = 0;
	}
	
	public LiveWorkout(String name, Integer trainerId, Boolean live, String description, String startTime, int duration, int users) {
		this.name = name;
		this.trainerId = trainerId;
		this.live = live;
		this.description = description;
		this.startTime = startTime;
		this.duration = duration;
		this.Users = 0;
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

	public Integer getUsers () {
		return Users;
	}

	public void setUsers (Integer users) {
		Users = users;
	}

	public long getDurationMinutes() {
		return duration;
	}

	public LocalDateTime getStartDateTime() {
		return LocalDateTime.parse(startTime);
	}
}
