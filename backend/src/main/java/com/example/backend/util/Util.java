package com.example.backend.util;

import com.example.backend.exception.PersonneNotFoundException;
import com.example.backend.model.Personne;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.example.backend.constant.Constant.PERSON_IMAGE_DIR;
import static com.example.backend.util.QrCodeUtil.toQrCode;

public class Util {

    public static boolean isArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    // Load card JRXML template
    public static JasperReport loadTemplate(String card_template) throws JRException {

        final InputStream reportInputStream = Util.class.getResourceAsStream(card_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
        return JasperCompileManager.compileReport(jasperDesign);
    }


    public static String generateQrCode(Personne personne) throws IOException, PersonneNotFoundException {
        File outputfile = new File("images/qrCode/"+ personne.getCIN()+".jpg");
        ImageIO.write(toQrCode(personne.toJSON().toString()), "jpg", outputfile);
        return outputfile.getName();
    }

    public static String createImagePath(String imageName) {
        return PERSON_IMAGE_DIR + File.separator + imageName;
    }

}
