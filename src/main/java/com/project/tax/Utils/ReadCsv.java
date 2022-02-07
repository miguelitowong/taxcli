package com.project.tax.Utils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.tax.model.TaxInvoiceEntity;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static com.project.tax.Properties.FILE_NAME;

@Component
public class ReadCsv {

    public List<TaxInvoiceEntity> readTaxInvoiceCsv() throws FileNotFoundException {
        List taxInvoiceEntityList = new CsvToBeanBuilder(new FileReader(FILE_NAME))
                .withType(TaxInvoiceEntity.class)
                .build()
                .parse();
        return taxInvoiceEntityList;
    }
}
