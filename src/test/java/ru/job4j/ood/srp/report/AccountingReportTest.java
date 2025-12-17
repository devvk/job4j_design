package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.CurrencyConverter;
import ru.job4j.ood.srp.currency.InMemoryCurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class AccountingReportTest {
    @Test
    public void whenSalaryConvertedToUsd() {
        MemoryStore store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 300000);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(worker);
        Report report = new AccountingReport(store, parser);
        CurrencyConverter currencyConverter = new InMemoryCurrencyConverter();
        String expected = "Name; Hired; Fired; Salary;"
                + System.lineSeparator()
                + worker.getName() + " "
                + parser.parse(worker.getHired()) + " "
                + parser.parse(worker.getFired()) + " "
                + currencyConverter.convert(Currency.RUB, worker.getSalary(), Currency.USD)
                + System.lineSeparator();
        assertThat(report.generate(employee -> true)).isEqualTo(expected);
    }
}
