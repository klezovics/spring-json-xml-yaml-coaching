package com.klezovich.springjsoncoaching.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//Based on https://www.baeldung.com/jackson-xml-serialization-and-deserialization
public class SimpleXmlTest {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class SimpleBean {
      String name;
      String age;
  }

  @Test
  void testCanSerializeXml() throws JsonProcessingException {
      XmlMapper mapper = new XmlMapper();
      var bean = new SimpleBean("AK", "31");

      var xml = mapper.writeValueAsString(bean);
      assertThat(xml, CompareMatcher.isIdenticalTo("<SimpleBean><name>AK</name><age>31</age></SimpleBean>"));
  }

    @Test
    void testCanDeserializeXml() throws JsonProcessingException {
        XmlMapper mapper = new XmlMapper();

        var bean = mapper.readValue("<SimpleBean><name>AK</name><age>31</age></SimpleBean>", SimpleBean.class);
        assertThat(bean.getName(), is("AK"));
        assertThat(bean.getAge(), is("31"));
    }
}
