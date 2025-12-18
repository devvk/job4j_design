package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

class JsonReportTest {
    @Test
    public void whenGeneratedJsonReport() {
        Store store = new MemoryStore();
        Calendar now = new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41);
        Employee e1 = new Employee("Ivan", now, now, 150000);
        store.add(e1);
        Employee e2 = new Employee("Alex", now, now, 300000);
        store.add(e2);
        Report report = new JsonReport(store);
        String expected = """
                [
                  {
                    "name": "Ivan",
                    "hired": {
                      "year": 2023,
                      "month": 5,
                      "dayOfMonth": 8,
                      "hourOfDay": 17,
                      "minute": 41,
                      "second": 0
                    },
                    "fired": {
                      "year": 2023,
                      "month": 5,
                      "dayOfMonth": 8,
                      "hourOfDay": 17,
                      "minute": 41,
                      "second": 0
                    },
                    "salary": 150000
                  },
                  {
                    "name": "Alex",
                    "hired": {
                      "year": 2023,
                      "month": 5,
                      "dayOfMonth": 8,
                      "hourOfDay": 17,
                      "minute": 41,
                      "second": 0
                    },
                    "fired": {
                      "year": 2023,
                      "month": 5,
                      "dayOfMonth": 8,
                      "hourOfDay": 17,
                      "minute": 41,
                      "second": 0
                    },
                    "salary": 300000
                  }
                ]""";
        assertThat(report.generate(employee -> true)).isEqualTo(expected);
    }
}
