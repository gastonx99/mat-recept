package se.dandel.recipe.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            return (T) createUnmarshaller(clazz).unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXml(Object xml, Class<?> clazz) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(clazz).marshal(xml, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Unmarshaller createUnmarshaller(Class<T> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz).createUnmarshaller();
    }

    private static Marshaller createMarshaller(Class<?> clazz) throws JAXBException, PropertyException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return m;
    }
}
