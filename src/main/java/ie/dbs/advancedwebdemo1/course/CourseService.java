
package ie.dbs.advancedwebdemo1.course;

import ie.dbs.advancedwebdemo1.trainer.Trainer;
import ie.dbs.advancedwebdemo1.trainer.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;

    public CourseService(
            CourseRepository courseRepository,
            TrainerRepository trainerRepository) {
        this.courseRepository = courseRepository;
        this.trainerRepository = trainerRepository;
    }

    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Transactional
    public Course createCourse(CourseForm form) {
        Trainer trainer = trainerRepository
                .findById(form.getTrainerId()).orElse(null);

        if (trainer == null) return null;

        Course course = new Course(
                form.getName(),
                form.getCapacity(),
                trainer
        );

        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, CourseForm form) {
        Course course = courseRepository
                .findById(id).orElse(null);

        if (course == null) return null;

        Trainer trainer = trainerRepository
                .findById(form.getTrainerId()).orElse(null);

        if (trainer == null) return null;

        course.setName(form.getName());
        course.setCapacity(form.getCapacity());
        course.setTrainer(trainer);

        return courseRepository.save(course);
    }

    @Transactional
    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Course> searchCourses(String name) {
        return courseRepository
                .findByNameContainingIgnoreCase(name);
    }
}
