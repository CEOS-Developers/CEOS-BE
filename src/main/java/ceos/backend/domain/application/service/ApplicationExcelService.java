package ceos.backend.domain.application.service;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationAnswer;
import ceos.backend.domain.application.domain.ApplicationInterview;
import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.dto.response.GetCreationTime;
import ceos.backend.domain.application.exception.FileCreationFailed;
import ceos.backend.domain.application.helper.ApplicationExcelHelper;
import ceos.backend.domain.application.repository.ApplicationQuestionRepository;
import ceos.backend.domain.application.repository.ApplicationRepository;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import ceos.backend.global.common.entity.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationExcelService {
    private final RecruitmentRepository recruitmentRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationExcelHelper applicationExcelHelper;

    @Transactional
    public Path getApplicationExcel() {
        return Paths.get("ApplicationList.xlsx");
    }

    @Transactional
    public GetCreationTime createApplicationExcel() {
        try {
            // 지원서 엑셀 생성
            createApplicationExcelFile();
        } catch (IOException e) {
            throw FileCreationFailed.EXCEPTION;
        }

        LocalDateTime dateTime = LocalDateTime.now();

        Recruitment recruitment = recruitmentRepository.findAll().get(0);
        recruitment.updateApplicationExcelCreatedAt(dateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return GetCreationTime.from(dateTime.format(formatter));
    }

    @Transactional(readOnly = true)
    public void createApplicationExcelFile() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Header
        List<String> headers =
                new ArrayList<>(
                        List.of(
                                "", "파트", "이름", "성별", "생년월일", "email", "전화번호", "대학교", "전공",
                                "남은 학기 수", "OT", "데모데이", "다른 활동"));

        // 지원서 질문
        List<ApplicationQuestion> questionList = applicationQuestionRepository.findAll();
        questionList.sort(
                Comparator.comparing(ApplicationQuestion::getCategory)
                        .thenComparing(ApplicationQuestion::getNumber));

        List<Application> applicationList = applicationRepository.findAll();

        Map<Long, String> interviewTimeMap = applicationExcelHelper.getInterviewTimeMap();
        Map<Long, Integer> questionIndexMap =
                applicationExcelHelper.getQuestionIndexMap(headers, questionList);

        // Header
        headers.addAll(List.of("면접 가능한 시간", "서류 합격 여부", "면접 시간"));

        int colIndex = 0;
        int rowIndex = 0;

        Row row = sheet.createRow(rowIndex++);

        for (String header : headers) {
            row.createCell(colIndex++).setCellValue(header);
        }

        // 날짜 형식 설정
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));

        Cell cell;

        // 지원서
        for (Application application : applicationList) {

            List<ApplicationAnswer> applicationAnswers = application.getApplicationAnswers();
            List<ApplicationInterview> applicationInterviews =
                    application.getApplicationInterviews();

            Part part = application.getApplicationDetail().getPart();

            colIndex = 0;
            row = sheet.createRow(rowIndex);
            row.createCell(colIndex++).setCellValue(rowIndex);
            row.createCell(colIndex++)
                    .setCellValue(application.getApplicationDetail().getPart().getPart());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getName());
            row.createCell(colIndex++)
                    .setCellValue(application.getApplicantInfo().getGender().getGender());

            cell = row.createCell(colIndex++);
            cell.setCellValue(application.getApplicantInfo().getBirth());
            cell.setCellStyle(cellStyle);

            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getEmail());
            row.createCell(colIndex++)
                    .setCellValue(application.getApplicantInfo().getPhoneNumber());
            row.createCell(colIndex++)
                    .setCellValue(application.getApplicantInfo().getUniversity().getUniversity());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getMajor());
            row.createCell(colIndex++)
                    .setCellValue(application.getApplicantInfo().getSemestersLeftNumber());

            cell = row.createCell(colIndex++);
            cell.setCellValue(application.getApplicationDetail().getOtDate());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(colIndex++);
            cell.setCellValue(application.getApplicationDetail().getDemodayDate());
            cell.setCellStyle(cellStyle);

            row.createCell(colIndex++)
                    .setCellValue((application.getApplicationDetail().getOtherActivities()));

            // 질문 답변 입력
            for (ApplicationAnswer answer : applicationAnswers) {
                int index = (int) questionIndexMap.get(answer.getApplicationQuestion().getId());
                row.createCell(index).setCellValue(answer.getAnswer());
            }
            colIndex += questionList.size();

            row.createCell(colIndex++)
                    .setCellValue(
                            applicationExcelHelper.getPossibleInterview(
                                    interviewTimeMap, applicationInterviews)); // 면접 가능한 시간
            row.createCell(colIndex++).setCellValue(application.getDocumentPass().getResult());
            row.createCell(colIndex++).setCellValue(application.getInterviewDatetime());

            rowIndex++;
        }

        // 파일로 저장
        FileOutputStream fileOutputStream = new FileOutputStream("ApplicationList.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        // 워크북 및 자원 해제
        workbook.close();
    }
}
