package ceos.backend.domain.application.service;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.*;
import ceos.backend.domain.application.dto.response.*;
import ceos.backend.domain.application.enums.SortPartType;
import ceos.backend.domain.application.enums.SortPassType;
import ceos.backend.domain.application.exception.FileCreationFailed;
import ceos.backend.domain.application.helper.ApplicationExcelHelper;
import ceos.backend.domain.application.helper.ApplicationHelper;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.QuestionListVo;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.event.Event;
import ceos.backend.global.util.DurationFormatter;
import ceos.backend.global.util.ParsingDuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final InterviewRepository interviewRepository;
    private final ApplicationInterviewRepository applicationInterviewRepository;
    private final ApplicationQuestionDetailRepository applicationQuestionDetailRepository;

    private final RecruitmentHelper recruitmentHelper;
    private final RecruitmentRepository recruitmentRepository;

    private final ApplicationMapper applicationMapper;
    private final ApplicationHelper applicationHelper;

    private final ApplicationExcelHelper applicationExcelHelper;


    @Transactional(readOnly = true)
    public GetApplications getApplications(int pageNum, int limit, SortPartType sortType,
                                           SortPassType docPass, SortPassType finalPass) {
        //페이징 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit);

        Page<Application> pageManagements = null;
        Part part = applicationMapper.toPart(sortType);
        if (docPass == SortPassType.ALL && finalPass == SortPassType.ALL) {
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAll(pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPart(applicationMapper.toPart(sortType), pageRequest);
            }
        } else if (docPass != SortPassType.ALL && finalPass == SortPassType.ALL) {
            Pass pass = applicationMapper.toPass(docPass);
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAllByDocumentPass(pass, pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPartAndDocumentPass(applicationMapper.toPart(sortType), pass, pageRequest);
            }
        } else if (docPass == SortPassType.ALL && finalPass != SortPassType.ALL){
            Pass pass = applicationMapper.toPass(finalPass);
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAllByFinalPass(pass, pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPartAndFinalPass(applicationMapper.toPart(sortType), pass, pageRequest);
            }
        } else {
            Pass convertedDocPass = applicationMapper.toPass(docPass);
            Pass convertedFinalPass = applicationMapper.toPass(finalPass);
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository
                        .findAllByDocumentPassAndFinalPass(convertedDocPass, convertedFinalPass, pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPartAndDocumentPassAndFinalPass(applicationMapper.toPart(sortType),
                                convertedDocPass,
                                convertedFinalPass,
                                pageRequest);
            }
        }

        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, pageManagements.getTotalPages(), pageManagements.getTotalElements());

        return applicationMapper.toGetApplications(pageManagements, pageInfo);
    }

    @Transactional
    public void createApplication(CreateApplicationRequest createApplicationRequest) {
        // 제출 기간, 기수 검사
        applicationHelper.validateRecruitOption();

        // 중복 검사
        applicationHelper.validateFirstApplication(createApplicationRequest.getApplicantInfoVo());

        // 질문 다 채웠나 검사
        final List<ApplicationQuestion> applicationQuestions = applicationQuestionRepository.findAll();
        applicationHelper.validateQAMatching(applicationQuestions, createApplicationRequest);

        // 엔티티 생성 및 저장
        final String UUID = applicationHelper.generateUUID();
        final int generation = recruitmentHelper.takeRecruitment().getGeneration();
        final Application application = applicationMapper.toEntity(createApplicationRequest, generation, UUID);
        final List<Interview> interviews = interviewRepository.findAll();

        final List<ApplicationAnswer> applicationAnswers
                = applicationMapper.toAnswerList(createApplicationRequest, application, applicationQuestions);
        applicationAnswerRepository.saveAll(applicationAnswers);

        final List<String> unableTimes = DurationFormatter
                .toStringDuration(createApplicationRequest.getUnableTimes());
        final List<ApplicationInterview> applicationInterviews
                = applicationMapper.toApplicationInterviewList(unableTimes, application, interviews);
        applicationInterviewRepository.saveAll(applicationInterviews);

        application.addApplicationAnswerList(applicationAnswers);
        application.addApplicationInterviewList(applicationInterviews);
        applicationRepository.save(application);

        // 이메일 전송
        applicationHelper.sendEmail(createApplicationRequest, generation, UUID);
    }

    @Transactional(readOnly = true)
    public GetApplicationQuestion getApplicationQuestion() {
        // dto
        final List<ApplicationQuestion> applicationQuestions
                = applicationQuestionRepository.findAll();
        final List<ApplicationQuestionDetail> applicationQuestionDetails
                = applicationQuestionDetailRepository.findAll();
        final List<Interview> interviews = interviewRepository.findAll();
        return applicationMapper.toGetApplicationQuestion(applicationQuestions,
                applicationQuestionDetails, interviews);
    }

    @Transactional
    public void updateApplicationQuestion(UpdateApplicationQuestion updateApplicationQuestion) {
        // 기간 확인
        applicationHelper.validateBeforeStartDateDoc();

        // 남은 응답 확인
        applicationHelper.validateRemainApplications();

        // 변경
        applicationQuestionRepository.deleteAll();
        applicationQuestionDetailRepository.deleteAll();
        interviewRepository.deleteAll();

        final QuestionListVo questionListVo = applicationMapper.toQuestionList(updateApplicationQuestion);
        applicationQuestionRepository.saveAll(questionListVo.getApplicationQuestions());
        applicationQuestionDetailRepository.saveAll(questionListVo.getApplicationQuestionDetails());

        List<String> times = DurationFormatter.toStringDuration(updateApplicationQuestion.getTimes());
        final List<Interview> interviews = applicationMapper.toInterviewList(times);
        interviewRepository.saveAll(interviews);
    }

    @Transactional(readOnly = true)
    public GetResultResponse getDocumentResult(String uuid, String email) {
        // 서류 합격 기간 검증
        applicationHelper.validateDocumentResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessible(uuid, email);

        // dto
        return applicationMapper.toGetResultResponse(application, true);
    }

    @Transactional
    public void updateInterviewAttendance(String uuid, String email, UpdateAttendanceRequest request) {
        // 서류 합격 기간 검증
        applicationHelper.validateDocumentResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessible(uuid, email);

        // 유저 확인 여부 검증
        applicationHelper.validateApplicantInterviewCheckStatus(application);

        // 슬랙
        if (request.isAvailable()) {
            application.updateInterviewCheck(true);
            applicationRepository.save(application);
        } else {
            final SlackUnavailableReason reason =
                    SlackUnavailableReason.of(application, request.getReason(), false);
            Event.raise(reason);
        }
    }

    @Transactional(readOnly = true)
    public GetResultResponse getFinalResult(String uuid, String email) {
        // 최종 합격 기간 검증
        applicationHelper.validateFinalResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessible(uuid, email);

        // 유저 서류 합격 여부 검증
        applicationHelper.validateApplicantDocumentPass(application);

        // dto 생성
        return applicationMapper.toGetResultResponse(application, false);
    }

    @Transactional
    public void updateActivityAvailability(String uuid, String email, UpdateAttendanceRequest request) {
        // 최종 합격 기간 검증
        applicationHelper.validateFinalResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessible(uuid, email);

        // 유저 확인 여부 검증
        applicationHelper.validateApplicantActivityCheckStatus(application);

        // 슬랙
        if (request.isAvailable()) {
            application.updateFinalCheck(true);
            applicationRepository.save(application);
        } else {
            final SlackUnavailableReason reason
                    = SlackUnavailableReason.of(application, request.getReason(), true);
            Event.raise(reason);
        }
    }

    @Transactional(readOnly = true)
    public GetApplication getApplication(Long applicationId) {
        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // dto
        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationInterviewRepository.findAllByApplication(application);
        final List<ApplicationQuestion> applicationQuestions
                = applicationQuestionRepository.findAll();
        final List<ApplicationQuestionDetail> applicationQuestionDetails
                = applicationQuestionDetailRepository.findAll();
        final List<ApplicationAnswer> applicationAnswers
                = applicationAnswerRepository.findAllByApplication(application);
        return applicationMapper.toGetApplication(application, interviews, applicationInterviews,
                applicationQuestions, applicationQuestionDetails, applicationAnswers);
    }

    @Transactional(readOnly = true)
    public GetInterviewTime getInterviewTime(Long applicationId) {
        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // 서류 통과 검증
        applicationHelper.validateDocumentPassStatus(application);

        // dto
        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationInterviewRepository.findAllByApplication(application);
        return applicationMapper.toGetInterviewTime(interviews, applicationInterviews);
    }

    @Transactional
    public void updateInterviewTime(Long applicationId, UpdateInterviewTime updateInterviewTime) {
        // 기간 검증
        applicationHelper.validateDocumentPassDuration();

        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // 서류 통과 검증
        applicationHelper.validateDocumentPassStatus(application);

        // 인터뷰 시간 검증
        final List<Interview> interviews = interviewRepository.findAll();
        final String duration = ParsingDuration.toStringDuration(updateInterviewTime.getParsedDuration());
        applicationHelper.validateInterviewTime(interviews, duration);

         // status 변경
        application.updateInterviewTime(duration);
    }

    @Transactional
    public void updateDocumentPassStatus(Long applicationId, UpdatePassStatus updatePassStatus) {
        // 기간 검증
        applicationHelper.validateDocumentPassDuration();

        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // status 변경
        application.updateDocumentPass(updatePassStatus.getPass());
    }

    @Transactional
    public void updateFinalPassStatus(Long applicationId, UpdatePassStatus updatePassStatus) {
        // 기간 검증
        applicationHelper.validateFinalPassDuration();

        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // 서류 통과 검증
        applicationHelper.validateDocumentPassStatus(application);

        // status 변경
        application.updateFinalPass(updatePassStatus.getPass());
    }

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
        List<String> headers = new ArrayList<>(List.of("", "파트", "이름", "성별", "생년월일", "email", "전화번호",
                "대학교", "전공", "남은 학기 수", "OT", "데모데이", "다른 활동"));

        // 지원서 질문
        List<ApplicationQuestion> questionList = applicationQuestionRepository.findAll();
        questionList.sort(Comparator.comparing(ApplicationQuestion::getCategory)
                .thenComparing(ApplicationQuestion::getNumber));

        List<Application> applicationList = applicationRepository.findAll();

        Map<Long, String> interviewTimeMap = applicationExcelHelper.getInterviewTimeMap();
        Map<Long, Integer> questionIndexMap = applicationExcelHelper.getQuestionIndexMap(headers, questionList);

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
            List<ApplicationInterview> applicationInterviews = application.getApplicationInterviews();

            Part part = application.getApplicationDetail().getPart();

            colIndex = 0;
            row = sheet.createRow(rowIndex);
            row.createCell(colIndex++).setCellValue(rowIndex);
            row.createCell(colIndex++).setCellValue(application.getApplicationDetail().getPart().getPart());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getName());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getGender().getGender());

            cell = row.createCell(colIndex++);
            cell.setCellValue(application.getApplicantInfo().getBirth());
            cell.setCellStyle(cellStyle);

            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getEmail());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getPhoneNumber());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getUniversity().getUniversity());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getMajor());
            row.createCell(colIndex++).setCellValue(application.getApplicantInfo().getSemestersLeftNumber());

            cell = row.createCell(colIndex++);
            cell.setCellValue(application.getApplicationDetail().getOtDate());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(colIndex++);
            cell.setCellValue(application.getApplicationDetail().getDemodayDate());
            cell.setCellStyle(cellStyle);

            row.createCell(colIndex++).setCellValue((application.getApplicationDetail().getOtherActivities()));

            // 질문 답변 입력
            for (ApplicationAnswer answer : applicationAnswers) {
                int index = (int) questionIndexMap.get(answer.getApplicationQuestion().getId());
                row.createCell(index).setCellValue(answer.getAnswer());
            }
            colIndex += questionList.size();

            row.createCell(colIndex++).setCellValue(applicationExcelHelper.getPossibleInterview(interviewTimeMap, applicationInterviews)); // 면접 가능한 시간
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
