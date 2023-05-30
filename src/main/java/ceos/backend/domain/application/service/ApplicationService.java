package ceos.backend.domain.application.service;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.*;
import ceos.backend.domain.application.dto.response.*;
import ceos.backend.domain.application.helper.ApplicationHelper;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.event.Event;
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

    private final ApplicationMapper applicationMapper;
    private final ApplicationHelper applicationHelper;

    @Transactional(readOnly = true)
    public GetApplications getApplications(int pageNum, int limit) {
        //페이징 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit);

        Page<Application> pageManagements = applicationRepository.findAll(pageRequest);

        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, pageManagements.getTotalPages(), pageManagements.getTotalElements());

        return applicationMapper.toGetApplications(pageManagements, pageInfo);
    }

    @Transactional
    public void createApplication(CreateApplicationRequest createApplicationRequest) {
        // 제출 기간, 기수 검사
        applicationHelper.validateRecruitOption(createApplicationRequest.getApplicationDetailVo().getGeneration());

        // 중복 검사
        applicationHelper.validateFirstApplication(createApplicationRequest.getApplicantInfoVo());

        // 엔티티 생성 및 저장
        final String UUID = applicationHelper.generateUUID();
        final Application application = applicationMapper.toEntity(createApplicationRequest, UUID);
        applicationRepository.save(application);

        final List<ApplicationQuestion> applicationQuestions = applicationQuestionRepository.findAll();
        final List<ApplicationAnswer> applicationAnswers
                = applicationMapper.toAnswerList(createApplicationRequest, application, applicationQuestions);
        applicationAnswerRepository.saveAll(applicationAnswers);

        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationMapper.toApplicationInterviewList(createApplicationRequest.getUnableTimes(),
                application, interviews);
        applicationInterviewRepository.saveAll(applicationInterviews);

        // 이메일 전송
        applicationHelper.sendEmail(createApplicationRequest, UUID);
    }

    @Transactional(readOnly = true)
    public GetApplicationQuestion getApplicationQuestion() {
        // dto
        final List<ApplicationQuestion> applicationQuestions
                = applicationQuestionRepository.findAll();
        final List<Interview> interviews = interviewRepository.findAll();
        return applicationMapper.toGetApplicationQuestion(applicationQuestions, interviews);
    }

    @Transactional
    public void updateApplicationQuestion(UpdateApplicationQuestion updateApplicationQuestion) {
        // 기간 확인
        applicationHelper.validateBeforeStartDateDoc();

        // 남은 응답 확인
        applicationHelper.validateRemainApplications();

        // 변경
        applicationQuestionRepository.deleteAll();
        interviewRepository.deleteAll();

        final List<ApplicationQuestion> questions = applicationMapper.toQuestionList(updateApplicationQuestion);
        applicationQuestionRepository.saveAll(questions);

        final List<Interview> interviews = applicationMapper.toInterviewList(updateApplicationQuestion);
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
        final List<ApplicationAnswer> applicationAnswers
                = applicationAnswerRepository.findAllByApplication(application);
        return applicationMapper.toGetApplication(application, interviews, applicationInterviews,
                applicationQuestions, applicationAnswers);
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
        applicationHelper.validateInterviewTime(interviews, updateInterviewTime.getInterviewTime());

         // status 변경
        application.updateInterviewTime(updateInterviewTime.getInterviewTime());
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
    public void createExcelFile() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Header
        List<String> headers = new ArrayList<>(List.of("", "파트", "이름", "성별", "생년월일", "email", "전화번호",
                "대학교", "전공", "남은 학기 수", "OT", "데모데이", "다른 활동"));

        // Header : 지원서 질문
        List<ApplicationQuestion> questionList = applicationQuestionRepository.findAll();

        // 공통질문, 기획, 디자인, 프론트, 백엔드로 정렬

        // Map : Key (question_id), Value (colIndex)
        Map<Long, Integer> questionIndex = new HashMap<>();

        questionList.sort(Comparator.comparing(ApplicationQuestion::getCategory)
                .thenComparing(ApplicationQuestion::getNumber));

        int colIndex = headers.size();
        for (ApplicationQuestion applicationQuestion : questionList) {
            headers.add(applicationQuestion.getQuestion());
            questionIndex.put(applicationQuestion.getId(), colIndex++);
        }

        System.out.println("questionIndex = " + questionIndex);

        // Header
        headers.addAll(List.of("면접 가능한 시간", "서류 합격 여부", "면접 시간"));

        // Header 입력
        colIndex = 0;
        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);

        for (String header : headers) {
            row.createCell(colIndex++).setCellValue(header);
        }

        // Date Style
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd")); // 날짜 형식 설정

        // Application
        List<Application> applicationList = applicationRepository.findAll();
        Cell cell;

        for (Application application : applicationList) {

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
            List<ApplicationAnswer> applicationAnswers = application.getApplicationAnswers();

            for (ApplicationAnswer answer : applicationAnswers) {
                Long id = answer.getApplicationQuestion().getId();
                int index = (int) questionIndex.get(id);
                row.createCell(index).setCellValue(answer.getAnswer());
            }

            colIndex += questionList.size();

            // 면접 가능한 시간
            List<ApplicationInterview> applicationInterviews = application.getApplicationInterviews();

            String possibleInterview = "";
            for (ApplicationInterview interview : applicationInterviews) {
                possibleInterview += interview.getInterview().getId() + " ";
            }

            row.createCell(colIndex++).setCellValue(possibleInterview);

            // 서류 합격 여부
            row.createCell(colIndex++).setCellValue(application.getDocumentPass().getResult());

            // 면접 시간
            row.createCell(colIndex++).setCellValue(application.getInterviewDatetime());

            rowIndex++;
        }

        // 파일로 저장
        FileOutputStream fileOutputStream = new FileOutputStream("example.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        // 워크북 및 자원 해제
        workbook.close();
    }
}
