package ru.job4j.ood.srp.report;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.model.Employees;
import ru.job4j.ood.srp.store.Store;

import java.io.StringWriter;
import java.util.List;
import java.util.function.Predicate;

public class XmlReport implements Report {

    private final Store store;
    private final JAXBContext context;

    public XmlReport(Store store) throws JAXBException {
        this.store = store;
        this.context = JAXBContext.newInstance(Employees.class, Employee.class);
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        try (StringWriter writer = new StringWriter()) {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            List<Employee> employees = store.findBy(filter);
            marshaller.marshal(new Employees(employees), writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
