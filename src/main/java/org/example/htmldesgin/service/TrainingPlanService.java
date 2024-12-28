package org.example.htmldesgin.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.example.htmldesgin.dao.mapper.TrainingPlanMapper;
import org.example.htmldesgin.utils.Project;
import org.example.htmldesgin.utils.TrainingPlan;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {

    private static final Logger log = LoggerFactory.getLogger(TrainingPlanService.class);

    private final TrainingPlanMapper trainingPlanMapper;

    public List<TrainingPlan> getTrainingPlans(
            LocalDate startDate, 
            LocalDate endDate, 
            Boolean includeWeekend,
            String projectNo) {
        return trainingPlanMapper.selectByDateRange(
            startDate, 
            endDate, 
            includeWeekend != null ? includeWeekend : true,
            projectNo
        );
    }

    @Transactional
    public boolean createTrainingPlan(TrainingPlan trainingPlan) {
        return trainingPlanMapper.add(trainingPlan) == 1;
    }

    @Transactional
    public boolean updateTrainingPlan(TrainingPlan trainingPlan) {
        log.info("更新培训计划: {}", trainingPlan);
        int result = trainingPlanMapper.update(trainingPlan);
        log.info("更新结果: {}", result);
        return result == 1;
    }

    @Transactional
    public void deleteTrainingPlan(Long id) {
        trainingPlanMapper.deleteById(id);
    }

    public void deleteTrainingPlan(String projectNo) {
        trainingPlanMapper.deleteByProjectNo(projectNo);
    }

    public TrainingPlan getTrainingPlanById(Long id) {
        return trainingPlanMapper.selectById(id);
    }

    public List<TrainingPlan> getTrainingPlanProjectNosAndMore(){
        return trainingPlanMapper.selectProjectNosAndMore();
    }

    public TrainingPlan createTrainingPlan(Project project) {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setProjectNo(project.getProjectNo());
        trainingPlan.setGroupName(project.getGroupName());
        trainingPlan.setCompanyName(project.getCompanyName());
        trainingPlan.setSystem(project.getSystem());
        return trainingPlan;
    }

    private List<TrainingPlan> findByDateRange(String startDate, String endDate) {
        try {
            return trainingPlanMapper.getTrainingPlansByDateRange(startDate, endDate);
        } catch (Exception e) {
            log.error("日期解析错误: ", e);
            return new ArrayList<>();
        }
    }

    public ByteArrayOutputStream exportToExcel(
            String startDate, 
            String endDate,
            Boolean includeWeekend,
            String projectNo) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("培训计划");
            
            // 创建标题行样式
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            // 创建标题行
            Row headerRow = sheet.createRow(0);
            String[] headers = {"集团", "公司", "系统", "培训内容", "培训时间", "培训老师", "培训日期"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // 获取数据
            List<TrainingPlan> plans = getTrainingPlans(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                includeWeekend,
                projectNo
            );
            
            // 填充数据
            int rowNum = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (TrainingPlan plan : plans) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(plan.getGroupName());
                row.createCell(1).setCellValue(plan.getCompanyName());
                row.createCell(2).setCellValue(plan.getSystem());
                row.createCell(3).setCellValue(plan.getTrainingContext());
                row.createCell(4).setCellValue(plan.getTrainingTime());
                row.createCell(5).setCellValue(plan.getTrainer());
                
                // 添加培训日期
                if (plan.getTrainingDate() != null) {
                    row.createCell(6).setCellValue(
                        plan.getTrainingDate().format(formatter)
                    );
                }
            }
            
            // 自动调整列宽
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    public List<String> getProjectNosByGroupAndCompany(String groupName, String companyName) {
        return trainingPlanMapper.selectProjectNosByGroupAndCompany(groupName, companyName);
    }

}