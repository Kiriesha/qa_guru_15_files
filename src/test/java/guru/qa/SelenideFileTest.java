package guru.qa;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import guru.qa.model.Myself;
import org.junit.jupiter.api.Test;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFileTest () {

    ClassLoader cl = SelenideFileTest.class.getClassLoader();

    @Test
    void zipPdf() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/qa_guru.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("qa_guru.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) !=null) {
            if (entry.getName().contains("pdf.pdf")) {
                try (InputStream inputStream = zip.getInputStream(entry)) {
                    PDF pdf = new PDF(inputStream);
                    assertThat(pdf.text).contains("Логические ошибки");
                }
            }
        }

    }

    @Test
    void zipXls() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/qa_guru.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("qa_guru.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) !=null) {
            if (entry.getName().contains("Книга1.xls")) {
                try (InputStream inputStream = zip.getInputStream(entry)) {
                    XLS xls = new XLS(inputStream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(2)
                            .getCell(1)
                            .getStringCellValue())
                            .isEqualTo("Татаров");
                }
            }
        }
    }


    @Test
    void zipCsv() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/qa_guru.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("qa_guru.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            if (entry.getName().contains("csv.csv")) {
                try (InputStream inputStream = zip.getInputStream(entry)) {
                    CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                    List<String[]> content = reader.readAll();
                    String[] row = content.get(0);
                    assertThat(row[0]).isEqualTo("Dollars");
                }
            }
        }
    }


        @Test
        void jsonTest(){
            InputStream is = cl.getResourceAsStream("myself.json");
            Gson json = new Gson();
            Myself myself = json.fromJson(new InputStreamReader(is), Myself.class);
            assertThat(myself.name).isEqualTo("Anastasiia");
            assertThat(myself.lastname).isEqualTo("Kireeva");
            assertThat(myself.age).isEqualTo("24");
            assertThat(myself.work).isTrue();
            assertThat(myself.passport.number).isEqualTo(1234567890);

        }
}