package jackson.mark.munro.controller;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.service.SummitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/summits")
public class SummitController {

    private SummitService summitService;

    @Autowired
    public SummitController(SummitService summitService) {
        this.summitService = summitService;
    }

    @GetMapping()
    public Collection<Summit> find(@RequestParam(required = false) SummitCategory category,
                                   @RequestParam(name="min-height", required = false) Integer minHeightInMetres,
                                   @RequestParam(name="max-height", required = false) Integer maxHeightInMetres) {
            return summitService.find(0, category, minHeightInMetres, maxHeightInMetres);
    }
}
