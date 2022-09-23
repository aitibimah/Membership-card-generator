package com.example.backend.service;



import com.example.backend.exception.PersonneNotFoundException;
import com.example.backend.model.Personne;
import com.example.backend.util.Util;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class PersonneCarteService {


    private static final String path="images/";


    private static final String  signature= "images/signature.png";

    @Value("${card.template.path}")
    private String card_template;

    public File generateCardFor(Personne personne, Locale locale) throws IOException {

        File pdfFile = File.createTempFile("my-card", ".pdf");

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load card JRXML template.
            final JasperReport report = loadTemplate();

            // Fill parameters map.
            final Map<String, Object> parameters = parameters(personne, locale);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("card"));

            // Render the card as a PDF file.
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

            // return file.
            return pdfFile;
        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    // Fill template personne params
    private Map<String, Object> parameters(Personne personne, Locale locale) throws IOException, PersonneNotFoundException {
        final Map<String, Object> parameters = new HashMap<>();
        String qrCode = Util.generateQrCode(personne);

        parameters.put("photo", new FileInputStream(path+"photo/"+personne.getImage()));
        parameters.put("QrCode", new FileInputStream(path+"qrCode/"+qrCode));
        parameters.put("signature",new FileInputStream(signature));
        parameters.put("date", LocalDateTime.now().plusYears(2));
        parameters.put("personne",  personne);
        parameters.put("REPORT_LOCALE", locale);
        System.out.println(new FileInputStream(signature));
        return parameters;
    }

    // Load card JRXML template
    private JasperReport loadTemplate() throws JRException {
        final InputStream reportInputStream = getClass().getResourceAsStream(card_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
        return JasperCompileManager.compileReport(jasperDesign);
    }

}