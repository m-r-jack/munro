package jackson.mark.munro.controller;

import jackson.mark.munro.model.Munro;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/munros")
public class MunroController {

    @GetMapping()
    public Collection<Munro> listAll() {
        return Collections.EMPTY_LIST;
    }
}
