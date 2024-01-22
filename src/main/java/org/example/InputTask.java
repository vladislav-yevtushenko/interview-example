package org.example;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.example.repository.InputDto;
import org.example.repository.InputRepository;
import org.example.util.FileUtils;

import static org.example.util.RegexpUtil.getByRegexp;

public class InputTask {

    private static final String CUSTOMER_NAME_PATTERN = "(?<=Customer's Name: )[\\p{L} .'-]+";
    private static final String EMAIL_PATTERN = "(?<=Customer's Email: )[A-Za-z0-9+_.-]+@(.+)$";
    private static final String INVOICE_DATE_PATTERN = "(?<=Invoice Date: )\\b\\d{1,2}/\\d{1,2}/\\d{4}\\b";
    private static final String INVOICE_ID_PATTERN = "(?<=Invoice ID: )[A-Z\\d]+";
    private static final String INVOICE_TOTAL_SUM_PATTERN = "(?<=Total Sum: )\\$\\d+\\.\\d{2}";
    private static final String ORGANIZATION_ID_PATTERN = "(?<=Organization's ID: )\\b[A-Z\\d]{10}\\b";
    private static final String ORGANIZATION_NAME_PATTERN = "(?<=Organization's Name: )[\\p{L} .'-]+";
    private static final String PAYMENT_METHOD_PATTERN = "(?<=Payment Method: )(cash|credit|debit)";
    private Parser parser;
    private InputRepository inputRepository;

    public InputTask(InputRepository inputRepository) {
        this.inputRepository = inputRepository;
        parser = new Parser();
    }

    public void process(InputType method) {

        switch (method) {
            case FILE_SYSTEM:
                getInputFromFileSystem();
                break;
            case EMAIL:
                getInputFromEmail();
                break;
            case URL:
                getInputFromUrl();
                break;
        }

    }

    private void getInputFromEmail() {
        throw new IllegalArgumentException("NOT IMPLEMENTED YET"); //will be implemented later
    }

    private void getInputFromUrl() {
        throw new IllegalArgumentException("NOT IMPLEMENTED YET"); //will be implemented later
    }

    private void getInputFromFileSystem() {
        try {
            List<Path> list = FileUtils.getListOfFiles();
            for (Path path : list) {
                File file = path.toFile();
                parser.setFile(file);
                InputDto dto = convertToInputDto(parser.getContent());
                inputRepository.save(dto);
            }
        } catch (Throwable e) {
            throw new RuntimeException("Input Task error", e);
        }
    }

    private InputDto convertToInputDto(String content) {

        return InputDto.builder()
                .customerEmail(getByRegexp(content, EMAIL_PATTERN))
                .customerName(getByRegexp(content, CUSTOMER_NAME_PATTERN))
                .invoiceDate(getByRegexp(content, INVOICE_DATE_PATTERN))
                .invoiceNumber(getByRegexp(content, INVOICE_ID_PATTERN))
                .organizationId(getByRegexp(content, ORGANIZATION_ID_PATTERN))
                .organizationName(getByRegexp(content, ORGANIZATION_NAME_PATTERN))
                .invoiceTotal(getByRegexp(content, INVOICE_TOTAL_SUM_PATTERN))
                .paymentMethod(getByRegexp(content, PAYMENT_METHOD_PATTERN))
                .build();

    }

}