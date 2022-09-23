package com.example.backend.resource;

import com.example.backend.constant.Constant;
import com.example.backend.exception.CinAlreadyExistsException;
import com.example.backend.exception.PersonneNotFoundException;
import com.example.backend.model.Personne;
import com.example.backend.model.Response;
import com.example.backend.service.PersonneCarteService;
import com.example.backend.service.PersonneService;
import com.example.backend.service.mapValidationErrorService;
import com.example.backend.util.FileUtil;
import com.example.backend.util.Util;
import com.example.backend.validator.PersonneValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


import java.io.FileInputStream;
import java.util.Locale;
import static java.lang.String.format;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins="*")
public class PersonneController {
    private static final String uploadDir = "images/photo";

    private final PersonneService personneService;
    private final PersonneCarteService personneCarteService;

    private final PersonneValidator personneValidator;
    public PersonneController(PersonneService personneService, PersonneCarteService personneCarteService, PersonneValidator personneValidator) {
        this.personneService = personneService;
        this.personneCarteService = personneCarteService;
        this.personneValidator = personneValidator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPersonById(@PathVariable Long id) throws PersonneNotFoundException {


        Optional<Personne> personne = personneService.getPersonById(id);

        return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("personne",personne))
                            .message("Personne Récupérée")
                            .status(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                            .build());

    }


    @PostMapping(name="/", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> registerPerson(@Valid @RequestBody Personne person, BindingResult result) throws CinAlreadyExistsException, IOException, PersonneNotFoundException {
        // Validate
        personneValidator.validate(person,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        // set category image name
        String base64Image = person.getBase64Image();
        String ImageName = (base64Image == null) ?
                Constant.NOT_FOUND_IMAGE_NAME : FileUtil.getUniqueFileName() + ".jpg";
        person.setImage(ImageName);
        Personne NPerson =  personneService.saveOrUpdate(person);
        if (base64Image != null) {
            String path = Util.createImagePath(NPerson.getImage());
            FileUtil.saveImageToFileSystem(path, base64Image);
        }

        File cardPdf = personneCarteService.generateCardFor(NPerson, Locale.forLanguageTag("en"));
        final HttpHeaders httpHeaders = getHttpHeaders(NPerson.getCIN(), "en", cardPdf);

        //return new ResponseEntity<>(new InputStreamResource(new FileInputStream(cardPdf)), httpHeaders, OK);
        return ResponseEntity.ok().contentLength(cardPdf.length())
                .contentType(APPLICATION_PDF)
                .body(new InputStreamResource(new FileInputStream(cardPdf)));
    }

    private HttpHeaders getHttpHeaders(String code, String lang, File cardPdf) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(APPLICATION_PDF);
        respHeaders.setContentLength(cardPdf.length());
        respHeaders.setContentDispositionFormData("attachment", format("%s-%s.pdf", code, lang));
        return respHeaders;
    }
}