package com.project.tax;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.tax.model.TaxInvoiceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static com.project.tax.Properties.FILE_NAME;

@SpringBootApplication
public class TaxApplication implements CommandLineRunner {



    private static Logger LOG = LoggerFactory
            .getLogger(TaxApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(TaxApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");

        generateTaxReport();
    }

    private void generateTaxReport() {
        List<TaxInvoiceEntity> taxInvoiceEntityList;
        try {
            taxInvoiceEntityList = readCsv();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<TaxInvoiceEntity> readCsv() throws FileNotFoundException {
        List taxInvoiceEntityList = new CsvToBeanBuilder(new FileReader(FILE_NAME))
                .withType(TaxInvoiceEntity.class)
                .build()
                .parse();

        taxInvoiceEntityList.forEach(System.out::println);
        return taxInvoiceEntityList;
    }

}
