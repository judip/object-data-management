package judip.odm.services;

import dtos.OdmObjectDto;
import judip.odm.exceptions.InvalidInputException;
import judip.odm.model.*;
import judip.odm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OdmObjectService {

    private final OdmObjectRepository odmObjectRepository;
    private final OdmObjectTypeRepository odmObjectTypeRepository;

    @Autowired
    public OdmObjectService(OdmObjectRepository odmObjectRepository, OdmObjectTypeRepository odmObjectTypeRepository) {
        this.odmObjectRepository = odmObjectRepository;
        this.odmObjectTypeRepository = odmObjectTypeRepository;
    }

    public List<OdmObjectDto> findAll() {
        return odmObjectRepository.findAll().stream()
                .map(o -> mapped(o, null))
                .collect(Collectors.toList());
    }

    public void addObject(String identifier, Long typeId, Long parentId) {
        OdmObject obj = new OdmObject();
        obj.setIdentifier(identifier);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        obj.setCreationDate(timestamp);
        OdmObject objParent = odmObjectRepository.getById(parentId);
        obj.setParent(objParent);
        OdmObjectType typeO = odmObjectTypeRepository.getById(typeId);
        obj.setType(typeO);
        odmObjectRepository.save(obj);
    }

    public List<OdmObjectDto> tree(Long id) {
        OdmObject objParent = null;

        if (id != null) {
            objParent = odmObjectRepository.findById(id).orElseThrow(() -> new InvalidInputException(id));
        }

        List<OdmObject> children = odmObjectRepository.findAllByParent(objParent);

        return children.stream()
                .map(c -> mapped(c, tree(c.getId())))
                .collect(Collectors.toList());
    }

    public List<OdmObjectDto> objectDetails(Long id, List<OdmObjectDto> objWithParents) {

        OdmObject obj = odmObjectRepository.findById(id).orElseThrow(() -> new InvalidInputException(id));
        OdmObject parent = obj.getParent();

        if (objWithParents == null) {
            objWithParents = new ArrayList<>();
        }
        objWithParents.add(0, mapped(obj, null));

        if (parent == null) {
            return objWithParents;
        }

        return objectDetails(parent.getId(), objWithParents);
    }

    public OdmObjectDto objectDetailsWithInheritance(List<OdmObjectDto> objWithParents) {

        Map<String, String> objectDetVal = new HashMap<>();

        objWithParents.forEach(obj -> obj.getValues().keySet().forEach(key -> objectDetVal.put(key, obj.getValues().get(key))));

        OdmObjectDto odmObjectDto = objWithParents.get(objWithParents.size() - 1);
        odmObjectDto.setValues(objectDetVal);

        return odmObjectDto;
    }

    private static OdmObjectDto mapped(OdmObject odmObject, List<OdmObjectDto> childrens) {
        OdmObjectDto.OdmObjectDtoBuilder builder = OdmObjectDto.builder();
        builder.id(odmObject.getId());
        builder.creationDate(odmObject.getCreationDate());
        builder.identifier(odmObject.getIdentifier());
        builder.type(odmObject.getType().getName());

        builder.values(odmObject.getValues().stream().collect(
                Collectors.toMap(o -> o.getAttribute().getName(), Value::getValue)));
        builder.children(childrens);
        return builder.build();
    }
}
