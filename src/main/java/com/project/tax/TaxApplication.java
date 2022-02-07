package com.project.tax;

import com.project.tax.Utils.ReadCsv;
import com.project.tax.model.TaxInvoiceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class TaxApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(TaxApplication.class);
    @Autowired
    ReadCsv readCsv;

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(TaxApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");
        System.out.println("++++++++++++ TAX INVOICE ++++++++++++");
        generateTaxReport();
        System.out.println("++++++++++++ TAX INVOICE ++++++++++++");
    }

    private void generateTaxReport() {
        List<TaxInvoiceEntity> taxInvoiceEntityList;
        try {
            taxInvoiceEntityList = readCsv.readTaxInvoiceCsv();
            HashMap<Integer, List<TaxInvoiceEntity>> taxReportHashMap = sortTaxByCustomer(taxInvoiceEntityList);

            for (Integer key : taxReportHashMap.keySet()) {
                HashMap<String, Double> sortedCustomerTax = sortCustomerTaxByType(taxReportHashMap.get(key));
                printTax(taxReportHashMap.get(key).get(0).getCustomerId(), sortedCustomerTax);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Integer, List<TaxInvoiceEntity>> sortTaxByCustomer(List<TaxInvoiceEntity> taxInvoiceEntityList) {
        HashMap<Integer, List<TaxInvoiceEntity>> taxReportHashMap = new HashMap<>();

        for (int i = 0; i < taxInvoiceEntityList.size(); i++) {
            if (!taxReportHashMap.containsKey(taxInvoiceEntityList.get(i).getCustomerId())) {
                List<TaxInvoiceEntity> taxInvoiceEntityList1 = new ArrayList<>();
                taxInvoiceEntityList1.add(taxInvoiceEntityList.get(i));
                taxReportHashMap.put(taxInvoiceEntityList.get(i).getCustomerId(), taxInvoiceEntityList1);
            } else {
                List<TaxInvoiceEntity> taxInvoiceEntityList1 = new ArrayList<>();
                taxInvoiceEntityList1 = taxReportHashMap.get(taxInvoiceEntityList.get(i).getCustomerId());
                taxInvoiceEntityList1.add(taxInvoiceEntityList.get(i));
                taxReportHashMap.put(taxInvoiceEntityList.get(i).getCustomerId(), taxInvoiceEntityList1);
            }
        }
        return taxReportHashMap;

    }

    private HashMap<String, Double> sortCustomerTaxByType(List<TaxInvoiceEntity> taxInvoiceEntityList) {
        HashMap<String, Double> taxReportHashMap = new HashMap<>();

        for (int i = 0; i < taxInvoiceEntityList.size(); i++) {
            if (!taxReportHashMap.containsKey(taxInvoiceEntityList.get(i).getTaxType())) {
                taxReportHashMap.put(taxInvoiceEntityList.get(i).getTaxType(), taxInvoiceEntityList.get(i).getAmount());
            } else {
                Double nwwTaxTotal = Double.sum(taxReportHashMap.get(taxInvoiceEntityList.get(i).getTaxType()), taxInvoiceEntityList.get(i).getAmount());
                taxReportHashMap.put(taxInvoiceEntityList.get(i).getTaxType(), nwwTaxTotal);
            }
        }
        return taxReportHashMap;
    }

    private void printTax(int custNo, HashMap<String, Double> taxes) {
        for (String key : taxes.keySet()) {
            System.out.println("For tax" + key + " customer " + custNo + " declared $" + taxes.get(key) * 0.1);
        }
    }
}
