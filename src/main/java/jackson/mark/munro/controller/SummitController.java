package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.service.SummitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Comparator;

@RestController
@RequestMapping("/summits")
public class SummitController {

    private SummitService summitService;
    private QueryParamValidator paramValidator;
    private SortParamParser sortParamParser;

    @Autowired
    public SummitController(SummitService summitService, QueryParamValidator paramValidator, SortParamParser sortParamParser) {
        this.summitService = summitService;
        this.paramValidator = paramValidator;
        this.sortParamParser = sortParamParser;
    }

    @GetMapping()
    public Collection<Summit> find(@RequestParam(required = false, defaultValue = "0") int limit,
                                   @RequestParam(required = false) String sort,
                                   @RequestParam(required = false) SummitCategory category,
                                   @RequestParam(name="min-height", required = false) Integer minHeightInMetres,
                                   @RequestParam(name="max-height", required = false) Integer maxHeightInMetres) {

        try {
            paramValidator.validate(limit, sort, category, minHeightInMetres, maxHeightInMetres);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(ex.getHttpStatus(), ex.getMessage(), ex);
        }
        Comparator<Summit> comparator = sortParamParser.parseSortParam(sort);

        return summitService.find(limit, comparator, category, minHeightInMetres, maxHeightInMetres);
    }

}
