package ru.yastrebov.agreement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;
import ru.yastrebov.agreement.exception.handler.MethodNotAllowedException;
import ru.yastrebov.agreement.kafka.KafkaProducer;
import ru.yastrebov.agreement.mapstruct.AgreementMapper;
import ru.yastrebov.agreement.mapstruct.ProcessedRequestMapper;
import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreement.model.ProcessedRequestDTO;
import ru.yastrebov.agreement.model.ResultToMongo;
import ru.yastrebov.agreement.model.enums.Status;
import ru.yastrebov.agreement.repository.ProcessedRequestRepository;
import ru.yastrebov.agreement.service.AgreementService;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
@Slf4j
public class AgreementServiceImpl implements AgreementService {

    private final ProcessedRequestRepository processedRequestRepository;
    private final KafkaProducer kafkaProducer;
    private final ProcessedRequestMapper mapper;

    private final AgreementMapper agreementMapper;

    @Override
    public ProcessedRequestDTO getRequestById(Long id) throws MethodNotAllowedException, EntityNotFoundException {

        log.debug("getRequestById started, id = {}", id);

        ProcessedRequest requestForAgreement = new ProcessedRequest();

        try {
            requestForAgreement = processedRequestRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

        } catch (EntityNotFoundException exception) {
            AgreementStatistics badRequest = AgreementStatistics.builder()
                    .id(id)
                    .date(ZonedDateTime.from(LocalDateTime.now()))
                    .status(ru.yastrebov.agreementStatisticsLib.model.enums.Status.NOT_FOUND)
                    .build();
            kafkaProducer.sendMessage(badRequest);
            throw new EntityNotFoundException();
        }
        if (requestForAgreement.getStatus().equals(Status.APPROVED)) {
            makeAgreement(requestForAgreement);
            kafkaProducer.sendMessage(createMessageToMongo(requestForAgreement));

        } else if (requestForAgreement.getStatus().equals(Status.NOT_APPROVED)) {
            kafkaProducer.sendMessage(createMessageToMongo(requestForAgreement));
            throw new MethodNotAllowedException();
        }
        ProcessedRequestDTO processedRequestDTO = mapper.entityToDto(requestForAgreement);
        log.debug("getRequestById ended, request = {}", createMessageToMongo(requestForAgreement));
        return processedRequestDTO;
    }

    public AgreementStatistics createMessageToMongo(ProcessedRequest processedRequest) {

        return AgreementStatistics
                .builder()
                .id(processedRequest.getId())
                .date(ZonedDateTime.from(LocalDateTime.now()))
                .status(agreementMapper.requestToAgreement(processedRequest).getStatus())
                .build();
    }

    public void makeAgreement(ProcessedRequest requestForAgreement) {

        try {
            String fileName = "Agreement.pdf";
            PDDocument newAgreement = new PDDocument();
            PDPage newPage = new PDPage();

            newAgreement.addPage(newPage);
            PDFont font = PDType0Font.load(newAgreement, new File("C:\\Users\\aiastrebov\\Desktop\\final\\agreement\\fonts\\Times_New_Roman\\Times_New_Roman.ttf"));
            PDPageContentStream content = new PDPageContentStream(newAgreement, newPage);

            content.beginText();
            content.setFont(font, 26);
            content.newLineAtOffset(200, 700);
            content.showText("Договор кредитования");
            content.endText();

            content.beginText();
            content.setFont(font, 16);
            content.newLineAtOffset(25, 650);
            content.showText("Request Id: " + requestForAgreement.getId().toString());
            content.newLineAtOffset(0, -15);
            content.newLine();
            content.showText("Name: " + requestForAgreement.getName());
            content.newLineAtOffset(0, -15);
            content.newLine();
            content.showText("Date of birth: " + requestForAgreement.getBirthDate().toString());
            content.newLineAtOffset(0, -15);
            content.newLine();
            content.showText("Amount: " + requestForAgreement.getAmount().toString());
            content.newLineAtOffset(0, -15);
            content.newLine();
            content.showText("Term: " + requestForAgreement.getTerm().toString());
            content.endText();

            content.close();
            newAgreement.save(fileName);
            newAgreement.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
