package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

class HrReportTest {

    @Test
    public void whenGeneratedHrReportThenSortedBySalaryDesc() {
        Store store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee e1 = new Employee("Ivan", now, now, 150000);
        store.add(e1);
        Employee e2 = new Employee("Alex", now, now, 300000);
        store.add(e2);
        Employee e3 = new Employee("Anna", now, now, 400000);
        store.add(e3);
        Report report = new HrReport(store);
        String expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Name; Salary;")
                .add(e3.getName() + " " + e3.getSalary())
                .add(e2.getName() + " " + e2.getSalary())
                .add(e1.getName() + " " + e1.getSalary())
                .toString();
        assertThat(report.generate(employee -> true)).isEqualTo(expected);
    }
}
