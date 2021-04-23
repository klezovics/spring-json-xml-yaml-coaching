package com.klezovich.springjsoncoaching.xml;

import com.klezovich.springjsoncoaching.util.FileReader;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Based on https://www.baeldung.com/java-xpath
public class XmlFieldQueryTest {

    private String xml = FileReader.get("xml/person.xml");

    @Test
    void testCanParseFieldsFromXmlDocument() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        var doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));

        doc.getDocumentElement().normalize();

        var xPath = XPathFactory.newInstance().newXPath();

        var expression = "/Person/address/streetName";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
                doc, XPathConstants.NODESET);

        assertEquals("Name1", nodeList.item(0).getTextContent());
        assertEquals("Name2", nodeList.item(1).getTextContent());
    }
}
