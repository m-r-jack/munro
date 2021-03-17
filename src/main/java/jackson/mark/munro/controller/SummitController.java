package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.service.SummitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/summits")
public class SummitController {

    private SummitService summitService;
    private QueryParamValidator paramValidator;

    @Autowired
    public SummitController(SummitService summitService, QueryParamValidator paramValidator) {
        this.summitService = summitService;
        this.paramValidator = paramValidator;
    }

    @GetMapping()
    public Collection<Summit> find(@RequestParam(required = false, defaultValue = "0") int limit,
                                   @RequestParam(required = false) SummitCategory category,
                                   @RequestParam(name="min-height", required = false) Integer minHeightInMetres,
                                   @RequestParam(name="max-height", required = false) Integer maxHeightInMetres) {
        try {
            paramValidator.validate(limit, category, minHeightInMetres, maxHeightInMetres);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(ex.getHttpStatus(), ex.getMessage(), ex);
        }
        return summitService.find(limit, category, minHeightInMetres, maxHeightInMetres);
    }
}
