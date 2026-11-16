
package ie.dbs.advancedwebdemo1.course;

import jakarta.validation.constraints.*;

public class CourseForm {

    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 100)
    private String name;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Minimum capacity is 1")
    @Max(value = 500, message = "Maximum capacity is 500")
    private Integer capacity;

    @NotNull(message = "Please select a trainer")
    private Long trainerId;

    public CourseForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
}
