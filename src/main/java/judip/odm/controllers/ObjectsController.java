package judip.odm.controllers;

import dtos.OdmObjectDto;
import judip.odm.exceptions.InvalidInputException;
import judip.odm.services.OdmObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ObjectsController {

    @Autowired
    private OdmObjectService odmObjectService;

    @GetMapping("/objects")
     public List<OdmObjectDto> all() {
        return odmObjectService.findAll();
    }

    @PostMapping("/add-object")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void addNewObject (@RequestParam String identifier, @RequestParam Long typeId, @RequestParam Long parentId) {
        odmObjectService.addObject(identifier,typeId,parentId);
    }

    @PostMapping("/objects/tree")
    public @ResponseBody List<OdmObjectDto> tree(@RequestParam(required = false) Long id) {
        try {
            return odmObjectService.tree(id);
        } catch (InvalidInputException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not found", e);
        }
    }

    @PostMapping("/object")
    public @ResponseBody OdmObjectDto objectDetails(@RequestParam Long id) {
        try {
            return odmObjectService.objectDetailsWithInheritance(odmObjectService.objectDetails(id, null));
        } catch (InvalidInputException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not found", e);
        }
    }

}
