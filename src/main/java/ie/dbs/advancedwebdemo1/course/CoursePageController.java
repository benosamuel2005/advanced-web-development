
package ie.dbs.advancedwebdemo1.course;

import ie.dbs.advancedwebdemo1.trainer.TrainerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course-management")
public class CoursePageController {

    private final CourseService courseService;
    private final TrainerRepository trainerRepository;

    public CoursePageController(
            CourseService courseService,
            TrainerRepository trainerRepository) {

        this.courseService = courseService;
        this.trainerRepository = trainerRepository;
    }

    // Display all courses
    @GetMapping
    public String listCourses(Model model) {

        model.addAttribute(
                "courses",
                courseService.getCourses()
        );

        return "course/list";
    }

    // Display Create Course form
    @GetMapping("/new")
    public String newCourse(Model model) {

        model.addAttribute("courseForm", new CourseForm());
        model.addAttribute("trainers", trainerRepository.findAll());

        return "course/form";
    }

    // Create a new course
    @PostMapping
    public String createCourse(
            @Valid @ModelAttribute("courseForm") CourseForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "trainers",
                    trainerRepository.findAll()
            );

            return "course/form";
        }

        Course saved = courseService.createCourse(form);

        if (saved == null) {

            result.rejectValue(
                    "trainerId",
                    "invalid",
                    "Please select a valid trainer"
            );

            model.addAttribute(
                    "trainers",
                    trainerRepository.findAll()
            );

            return "course/form";
        }

        return "redirect:/course-management";
    }

    // Display Edit Course form
    @GetMapping("/{id}/edit")
    public String editCourse(
            @PathVariable Long id,
            Model model) {

        Course course = courseService.getCourseById(id);

        if (course == null) {
            return "redirect:/course-management";
        }

        CourseForm form = new CourseForm();

        form.setName(course.getName());
        form.setCapacity(course.getCapacity());

        if (course.getTrainer() != null) {
            form.setTrainerId(course.getTrainer().getId());
        }

        model.addAttribute("courseForm", form);
        model.addAttribute("courseId", id);
        model.addAttribute("trainers", trainerRepository.findAll());

        // FIXED: Open Edit Course page
        return "course/edit";
    }

    // Update existing course
    @PostMapping("/{id}")
    public String updateCourse(
            @PathVariable Long id,
            @Valid @ModelAttribute("courseForm") CourseForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute("courseId", id);
            model.addAttribute(
                    "trainers",
                    trainerRepository.findAll()
            );

            // FIXED: Return to Edit Course page
            return "course/edit";
        }

        Course updated = courseService.updateCourse(id, form);

        if (updated == null) {
            return "redirect:/course-management";
        }

        return "redirect:/course-management";
    }

    // Delete course
    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return "redirect:/course-management";
    }
}
