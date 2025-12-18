package ru.job4j.ood.srp.report;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

class XmlReportTest {
    @Test
    public void whenGeneratedJsonReport() throws JAXBException {
        Store store = new MemoryStore();
        Calendar now = new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41);
        Employee e1 = new Employee("Ivan", now, now, 150000);
        store.add(e1);
        Employee e2 = new Employee("Alex", now, now, 300000);
        store.add(e2);
        Report report = new XmlReport(store);
        String expected = """
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <employees>
                    <employee>
                        <name>Ivan</name>
                        <hired>08:06:2023 17:41</hired>
                        <fired>08:06:2023 17:41</fired>
                        <salary>150000</salary>
                    </employee>
                    <employee>
                        <name>Alex</name>
                        <hired>08:06:2023 17:41</hired>
                        <fired>08:06:2023 17:41</fired>
                        <salary>300000</salary>
                    </employee>
                </employees>
                """;
        assertThat(report.generate(employee -> true)).isEqualTo(expected);
    }
}
