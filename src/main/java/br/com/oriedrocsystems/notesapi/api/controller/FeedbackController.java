package br.com.oriedrocsystems.notesapi.api.controller;

import br.com.oriedrocsystems.notesapi.api.dto.FeedbackDTO;
import br.com.oriedrocsystems.notesapi.mail.FeedbackSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
public class FeedbackController {
    private FeedbackSender feedbackSender;

    public FeedbackController(FeedbackSender feedbackSender) {
        this.feedbackSender = feedbackSender;
    }

    @PostMapping
    public void sendFeedback(@RequestBody FeedbackDTO feedbackDTO,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException("Feedback has errors; Can not send feedback;");
        }

        this.feedbackSender.sendFeedback(
                feedbackDTO.getEmail(),
                feedbackDTO.getName(),
                feedbackDTO.getFeedback());
    }
}
