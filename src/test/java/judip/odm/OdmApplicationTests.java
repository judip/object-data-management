package judip.odm;

import dtos.OdmObjectDto;
import judip.odm.services.OdmObjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = OdmApplication.class)
class OdmApplicationTests {

	@Autowired
	OdmObjectService odmObjectService;

	@Test
	void contextLoads() {
	}

	@Test
	void checkListValues() {

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		//parent
		Map<String,String> val1 = new HashMap<>();
		val1.put("key1","value1");
		val1.put("key2","value2");
		val1.put("key3","value3");
		val1.put("key4","value4");

		OdmObjectDto o1 = OdmObjectDto.builder()
				.id(10l)
				.creationDate(timestamp)
				.identifier("rodzic")
				.values(val1)
				.build();

		//child
		Map<String,String> val2 = new HashMap<>();
		val2.put("key3","value3");
		val2.put("key4","value4");

		OdmObjectDto o2 = OdmObjectDto.builder()
				.id(10l)
				.creationDate(timestamp)
				.identifier("rodzic")
				.values(val1)
				.build();

		List<OdmObjectDto> objWithParents = List.of(o1, o2);

		//check val
		Map<String,String> expected = new HashMap<>();
		expected.put("key1","value1");
		expected.put("key2","value2");
		expected.put("key3","value3");
		expected.put("key4","value4");


		Map<String, String> actual = odmObjectService.objectDetailsWithInheritance(objWithParents).getValues();
		assertEquals(expected, actual);
	}

}
